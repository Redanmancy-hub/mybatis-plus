package com.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mybatisplus.mapper.UserMapper;
import com.mybatisplus.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
class MybatisPlusApplicationTests {
    @Autowired
    private UserMapper userMapper;

    @Test
    void testFindAll() {
        List<User> userList = userMapper.selectList(null);
        System.out.println(userList);
    }

    @Test
    void testGetPage(){
        IPage page = new Page(1,5);
        userMapper.selectPage(page,null);
        System.out.println("当前页码值"+page.getCurrent());
        System.out.println("每页显示"+page.getSize());
        System.out.println("总页数"+page.getPages());
        System.out.println("总条数"+page.getTotal());
        System.out.println("数据"+page.getRecords());
    }

    // lambda格式的条件查询
    @Test
    void testGetDate(){
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 多条件查询，年龄在1到41
//        lambdaQueryWrapper.lt(User::getAge,"41").gt(User::getAge,"1");
        // 或者关系、
        lambdaQueryWrapper.lt(User::getAge,"19").or().gt(User::getAge,"40");
        List<User> userList = userMapper.selectList(lambdaQueryWrapper);
        System.out.println(userList);
    }

    //查询投影
    @Test
    void testSelect(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("count(*) as count,name,age");
        // 进行分组
        queryWrapper.groupBy("age");
        List<Map<String, Object>> mapList = userMapper.selectMaps(queryWrapper);
        System.out.println(mapList);
    }

    //模拟登录
    @Test
    void loginUser(){
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getName,"hyq").eq(User::getPassword,"145");
        User loginUser = userMapper.selectOne(lqw);
        System.out.println(loginUser);
    }

    //测试字段映射和表名映射
    @Test
    void selectUser(){
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        List<User> userList = userMapper.selectList(lambdaQueryWrapper);
        System.out.println(userList);
    }
}
