package com.lihd.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.lihd.mybatisplus.mapper.UserMapper;
import com.lihd.mybatisplus.pojo.User;
import com.sun.org.apache.xerces.internal.impl.xs.SchemaSymbols;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

/**
 * @author : 葬花吟留别1851053336@qq.com
 * &#064;description : TODO
 * &#064;date : 2022/7/28 9:51
 */
@SpringBootTest
public class UserWrapperTest {
    @Autowired
    private UserMapper mapper;

    /**
     * Test01 : 组装select语句 ：查询部分数据 方便使用二级索引，提高效率
     * SELECT user_name,age FROM t_user WHERE is_delete=0 AND (uid <= ?)
     */
    @Test
    public void test01 (){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.le("uid",20).select("user_name","age");
        for (Map<String, Object> map : mapper.selectMaps(wrapper)) {
            System.out.println(map);
        }
    }

    /**
     * Test02 : 实现子查询 ：尽管这个子查询没有实际意义，只是为了演示而已
     * 我们并没有设置 逻辑删除，结果中有删除的语句
     * SELECT uid,user_name AS name,age,email,is_delete FROM t_user WHERE is_delete=0
     * AND (age >= ? AND uid IN (select uid from t_user where uid < 20))
     */
    @Test
    public void test02 (){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.ge("age", 20).inSql("uid", "select uid from t_user where uid < 20");
        List<User> users = mapper.selectList(wrapper);
        users.forEach(System.out::println);
    }

    /**
     * Test03 : updateWrapper
     * UPDATE t_user SET user_name=?,age=? WHERE is_delete=0
     * AND ((age > ? OR email IS NULL) AND user_name LIKE ?)
     */
    @Test
    public void test03 (){
        //将（年龄大于20或邮箱为null）并且用户名中包含有a的用户信息修改
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.and(user -> user.gt("age","20").or().isNull("email"))
                .like("user_name","a")
                .set("user_name","forget")
                .set("age",199);
        mapper.update(null, wrapper);
    }

    /**
     * Test04 : 组装条件 ：原始
     * SELECT uid,user_name AS name,age,email,is_delete FROM t_user
     * WHERE is_delete=0 AND (user_name LIKE ? AND age <= ?)
     */
    @Test
    public void test04 (){
        String name = "aaa";
        Integer ageBegin = null;
        Integer ageEnd = 34;
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(name)) {
            wrapper.like("user_name",name);
        }
        if (ageBegin != null) {
            wrapper.ge("age", ageBegin);
        }

        if (ageEnd != null) {
            wrapper.le("age", ageEnd);
        }

        List<User> users = mapper.selectList(wrapper);
        users.forEach(System.out::println);


    }

    /**
     * Test05 : 组装条件 ：condition
     * SELECT uid,user_name AS name,age,email,is_delete FROM t_user
     * WHERE is_delete=0 AND (user_name LIKE ? AND age <= ?)
     */
    @Test
    public void test05 (){
        String name = "aaa";
        Integer ageBegin = null;
        Integer ageEnd = 34;
        QueryWrapper<User> wrapper = new QueryWrapper<>();

        wrapper.like(StringUtils.isNotBlank(name), "user_name", name)
                .ge(ageBegin != null, "age", ageBegin)
                .le(ageEnd != null, "age", ageEnd);
        List<User> users = mapper.selectList(wrapper);
        users.forEach(System.out::println);
    }


    /**
     * Test06 : LambdaQueryWrapper
     * ELECT uid,user_name AS name,age,email,is_delete FROM t_user
     * WHERE is_delete=0 AND (user_name LIKE ? AND age <= ?)
     */
    @Test
    public void test06 (){
        String name = "aaa";
        Integer ageBegin = null;
        Integer ageEnd = 34;
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(name), User::getName, name)
                .ge(ageBegin != null, User::getAge, ageBegin)
                .le(ageEnd != null, User::getAge, ageEnd);
        List<User> users = mapper.selectList(wrapper);
        users.forEach(System.out::println);
    }

    /**
     * Test07 : LambdaUpdateWrapper
     * UPDATE t_user SET user_name=?,age=? WHERE is_delete=0
     * AND ((age > ? OR email IS NULL) AND user_name LIKE ?)
     */
    @Test
    public void test07 (){
        //将（年龄大于20或邮箱为null）并且用户名中包含有a的用户信息修改
        LambdaUpdateWrapper<User> wrapper = new LambdaUpdateWrapper<>();
        wrapper.and(user -> user.gt(User::getAge,20).or().isNull(User::getEmail))
                .like(User::getName, "a")
                .set(User::getName,"Never")
                .set(User::getAge,198);
        mapper.update(null, wrapper);

    }

}
