package com.lihd.mybatisplus;

import com.lihd.mybatisplus.pojo.User;
import com.lihd.mybatisplus.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class UserServiceImplTest {


    @Autowired
    private UserService userService;

    /*
     * SELECT COUNT( * ) FROM user
     */
    @Test
    public void count(){
        long count = userService.count();
        System.out.println(count);
    }


    /**
     * INSERT INTO user ( id, name, age, email ) VALUES ( ?, ?, ?, ? )
     * 通过sql语句可以看出 批量增加的操作实质上是 循环 + 单条插入
     */
    @Test
    public void test (){
        List<User> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            String s = UUID.randomUUID().toString().substring(1, 5);
            user.setName(s);
            user.setEmail(s + "@gmail.com");
            user.setAge((int) (Math.random() * 100));
            list.add(user);
        }
        userService.saveBatch(list);
    }




}