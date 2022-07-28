package com.lihd.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.Mapper;
import com.lihd.mybatisplus.mapper.UserMapper;
import com.lihd.mybatisplus.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

/**
 * @author : 葬花吟留别1851053336@qq.com
 * &#064;description : TODO
 * &#064;date : 2022/7/27 16:02
 */
@SpringBootTest
public class UserMapperTest {

    /*
     *
     */
    @Autowired
    private UserMapper mapper;

    /*
     * select id, name, age, email from user where id = ?
     * 自定义的方法， 由此可以看出 ，只做增强不做修改
     */
    @Test
    void selectMapById(){
        Map<String, Object> map = mapper.selectMapById(1L);
        System.out.println(map);
    }


    /*
     * INSERT INTO user ( id, name, age, email ) VALUES ( ?, ?, ?, ? )
     */
    @Test
    void insert() {
        User user = new User();
        user.setAge(18);
        user.setEmail("JPA@gmail.com");
        user.setName("JPA");
        mapper.insert(user);
        // 此时 id 已经有值
        // User(id=1552213449500450818, name=JPA, age=18, email=JPA@gmail.com)
        System.out.println(user);
    }

    /*
     * DELETE FROM user WHERE id=?
     */
    @Test
    void deleteById() {
        int update = mapper.deleteById(1552213449500450818L);
    }

    /*
     * DELETE FROM user WHERE name = ? AND age = ?
     */
    @Test
    void deleteByMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name","JPA");
        map.put("age", 18);
        mapper.deleteByMap(map);
    }


    /*
     * ==>  UPDATE t_user SET is_delete=1 WHERE is_delete=0 AND (email IS NULL)
     */
    @Test
    void delete() {
        //删除邮箱为null的用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.isNull("email");
        int i = mapper.delete(wrapper);
        System.out.println("i = " + i);


    }
    /*
     * DELETE FROM user WHERE id IN ( ? , ? , ? )
     */
    @Test
    void deleteBatchIds() {
        List<Integer> list = Arrays.asList(1,2,3);
        mapper.deleteBatchIds(list);
    }


    /*
     * UPDATE user SET name=?, email=? WHERE id=?
     * 类似于 mybatis阶段自动生成的 包含selective字眼的 方法
     */
    @Test
    void updateById() {
        User user = new User();
        user.setUid(9L);
        user.setName("SSSP");
        user.setEmail("one@day.com");
        mapper.updateById(user);
    }

    /*
     * UPDATE t_user SET user_name=?, email=? WHERE is_delete=0 AND (age > ? AND user_name LIKE ? OR email IS NULL)
     * UPDATE t_user SET user_name=?, email=? WHERE is_delete=0 AND (user_name LIKE ? AND (age > ? OR email IS NULL))
     */
    @Test
    void update() {
        //将（年龄大于20并且用户名中包含有a）或邮箱为null的用户信息修改
        /*QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.gt("age", 20)
                .like("user_name", "a")
                .or()
                .isNull("email");
        User user = new User();
        user.setName("painful");
        user.setEmail("painful@you.cn");
        mapper.update(user, wrapper);
*/
        //将用户名中包含有a并且（年龄大于20或邮箱为null）的用户信息修改
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.like("user_name", "a")
                .and(user -> user.gt("age", 20).or().isNull("email"));
        User user = new User();
        user.setName("forget");
        user.setEmail("forget@pain.com");
        mapper.update(user, wrapper);

    }
    /*
     * SELECT id,name,age,email FROM user WHERE id=?
     */
    @Test
    void selectById() {
        User user = mapper.selectById(5);
        System.out.println(user);
    }

    /*
     * SELECT id,name,age,email FROM user WHERE id IN ( ? , ? , ? )
     */
    @Test
    void selectBatchIds() {
        List<Long> list = Arrays.asList(2L, 3L, 4L);
        List<User> users = mapper.selectBatchIds(list);
        users.forEach(System.out::println);
    }
    /*
     * SELECT id,name,age,email FROM user WHERE name = ? AND age = ?
     */
    @Test
    void selectByMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name","Jack");
        map.put("age",20);
        List<User> list = mapper.selectByMap(map);
        list.forEach(System.out::println);
    }

    @Test
    void selectOne() {

    }

    @Test
    void exists() {
    }

    @Test
    void selectCount() {

    }

    /*
     * SELECT id,name,age,email FROM user
     * SELECT uid,user_name AS name,age,email,is_delete FROM t_user WHERE is_delete=0 AND (user_name LIKE ? AND age BETWEEN ? AND ? AND email IS NOT NULL)
     */
    @Test
    void selectList() {
//        List<User> list = mapper.selectList(null);
//        list.forEach(System.out::println);

        //查询用户名包含a，年龄在20到30之间，并且邮箱不为null的用户信息

        /*QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.like("user_name", "a").between("age", 20, 30)
                .isNotNull("email");
        List<User> users = mapper.selectList(wrapper);
        users.forEach(System.out::println);*/

        //按年龄降序查询用户，如果年龄相同则按id升序排列
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("age").orderByAsc("uid");
        List<User> users = mapper.selectList(wrapper);
        users.forEach(System.out::println);

    }

    @Test
    void selectMaps() {

    }

    @Test
    void selectObjs() {
    }

    @Test
    void selectPage() {
    }

    @Test
    void selectMapsPage() {
    }
}
