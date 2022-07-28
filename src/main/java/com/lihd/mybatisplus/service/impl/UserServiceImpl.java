package com.lihd.mybatisplus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lihd.mybatisplus.mapper.UserMapper;
import com.lihd.mybatisplus.pojo.User;
import com.lihd.mybatisplus.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author : 葬花吟留别1851053336@qq.com
 * &#064;description : TODO
 * &#064;date : 2022/7/27 19:53
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
