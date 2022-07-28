package com.lihd.mybatisplus.pojo;

import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

/**
 * @author : 葬花吟留别1851053336@qq.com
 * &#064;description : TODO
 * &#064;date : 2022/7/28 13:06
 */
@Data
public class Product {
    private Long id;
    private String name;
    private Integer price;

    @Version
    private Integer version;
}
