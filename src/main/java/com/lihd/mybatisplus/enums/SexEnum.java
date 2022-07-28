package com.lihd.mybatisplus.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * @author : 葬花吟留别1851053336@qq.com
 * &#064;description : TODO
 * &#064;date : 2022/7/28 13:41
 */
public enum SexEnum {
    MALE(1, "男"),
    FEMALE(2, "女"),
    ;
    @EnumValue
    private Integer sex;
    private String val;

    SexEnum(Integer sex, String val) {
        this.sex = sex;
        this.val = val;
    }
}
