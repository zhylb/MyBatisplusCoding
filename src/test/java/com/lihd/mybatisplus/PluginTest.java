package com.lihd.mybatisplus;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lihd.mybatisplus.enums.SexEnum;
import com.lihd.mybatisplus.mapper.ProductMapper;
import com.lihd.mybatisplus.mapper.UserMapper;
import com.lihd.mybatisplus.pojo.Product;
import com.lihd.mybatisplus.pojo.User;
import com.sun.scenario.effect.impl.prism.PrReflectionPeer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author : 葬花吟留别1851053336@qq.com
 * &#064;description : TODO
 * &#064;date : 2022/7/28 10:58
 */
@SpringBootTest
public class PluginTest {
    @Autowired
    private UserMapper mapper;

    @Autowired
    private ProductMapper productMapper;


    /**
     * Test01 : 测试基础的分页功能
     * SELECT uid,user_name AS name,age,email,is_delete FROM t_user
     * WHERE is_delete=0 LIMIT ?,?
     */
    @Test
    public void test01 (){
        Page<User> page = new Page<>(2, 5);

        //似乎不用写返回，返回值就在page之中
        mapper.selectPage(page, null);
        List<User> records = page.getRecords();
        long pages = page.getPages();
        long total = page.getTotal();
        long current = page.getCurrent();
        long size = page.getSize();
        boolean hasPrevious = page.hasPrevious();
        boolean hasNext = page.hasNext();
        System.out.println("records = " + records);
        System.out.println("pages = " + pages);
        System.out.println("total = " + total);
        System.out.println("current = " + current);
        System.out.println("size = " + size);
        System.out.println("hasPrevious = " + hasPrevious);
        System.out.println("hasNext = " + hasNext);

    }


    /**
     * Test02 : 测试自动方法的分页功能
     * 不知道为什么不让用 > < >= <= 这种，用=就不会报错
     */
    @Test
    public void test02 (){
        Page<User> page = new Page<>(2, 5);

        mapper.selectPageMy(page, 10);

        List<User> records = page.getRecords();
        long pages = page.getPages();
        long total = page.getTotal();
        long current = page.getCurrent();
        long size = page.getSize();
        boolean hasPrevious = page.hasPrevious();
        boolean hasNext = page.hasNext();
        System.out.println("records = " + records);
        System.out.println("pages = " + pages);
        System.out.println("total = " + total);
        System.out.println("current = " + current);
        System.out.println("size = " + size);
        System.out.println("hasPrevious = " + hasPrevious);
        System.out.println("hasNext = " + hasNext);
    }

    /**
     * Test03 : 演示 问题
     * 我们想要达到的效果是 修改后结果是120 而不是70
     *
     * 在mybatis中实现乐观锁比较简单
     * 提供version字段 和属性
     * 属性上 标注 @Version注解
     * 在MyBatisplus配置类中 添加 interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
     *
     */
    @Test
    public void test03 (){
        //100
        Product productA = productMapper.selectById(1);
        Product productB = productMapper.selectById(1);
        //100 + 50
        productA.setPrice(productA.getPrice() + 50);
        //100 - 30
        productB.setPrice(productB.getPrice() - 30);

        productMapper.updateById(productA);
        productMapper.updateById(productB);

        Product product = productMapper.selectById(1);
        //70
        System.out.println(product.getPrice());
    }

    /**
     * Test04 : 修改后方法
     * 我好像发现一个问题
     * 如果 连续执行两次update似乎会强制修改成功
     */
    @Test
    public void test04 (){
        //100
        Product productA = productMapper.selectById(1);
        Product productB = productMapper.selectById(1);
        //100 + 50
        productA.setPrice(productA.getPrice() + 50);
        //100 - 30
        productB.setPrice(productB.getPrice() - 30);
        productMapper.updateById(productA);
//        productMapper.updateById(productB);
        // 修改失败
        int update = productMapper.updateById(productB);
        while (update == 0) {
            //150
            productB = productMapper.selectById(1);
//            productB.setPrice(productB.getPrice() - 30);
            //120
            update = productMapper.updateById(productB);
        }

        Product product = productMapper.selectById(1);
        //120
        System.out.println(product.getPrice());
    }



    /**
     * Test05 : 开启通用枚举
     * 1 在枚举类中标注 @EnumValue
     * 2 在application.yml中  配置包级别 扫描通用枚举
     *   type-enums-package: com.lihd.mybatisplus.enums
     */
    @Test
    public void test05 (){
        User user = new User();
        user.setName("hello");
        user.setSex(SexEnum.FEMALE);
        mapper.insert(user);
    }


}
