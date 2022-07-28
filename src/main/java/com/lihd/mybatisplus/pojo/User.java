package com.lihd.mybatisplus.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.lihd.mybatisplus.enums.SexEnum;
import lombok.*;
import org.springframework.stereotype.Repository;
import sun.plugin2.os.windows.SECURITY_ATTRIBUTES;

/**
 * @author : 葬花吟留别1851053336@qq.com
 * &#064;description : TODO
 * &#064;date : 2022/7/27 15:52
 */
@Data
//@TableName("t_user")
public class User {
    //
    //
    /*
     * tableId 标签 : 映射属性为主键所对应的字段 ，
     * 因为主键名不是id的话 ，有了这个标签，主键属性的属性名可以随便取了（最好还是见名知意）
     * value : 映射属性值为 value的值
     *
     */
//    @TableId(value = "uid",type = IdType.AUTO)
    @TableId(value = "uid")
    private Long uid;
    @TableField("user_name")
    private String name;
    private Integer age;
    private String email;

    private SexEnum sex;


    /*
     * 有了这个标签后
     * 每次删除本质上就是一个update
     * 查询时会忽略is_delete = 1的值
     * 这些都是自动实现的，我们会使用即可。
     */
    @TableLogic
    private Boolean isDelete;
}
