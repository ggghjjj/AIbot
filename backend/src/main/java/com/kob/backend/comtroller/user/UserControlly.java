package com.kob.backend.comtroller.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kob.backend.mapper.UserMapper;
import com.kob.backend.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserControlly {

    @Autowired
    UserMapper userMapper;

    @GetMapping("/user/all/")
    public List<User> getAll() {
        return userMapper.selectList(null);
    }

    @GetMapping("/user/{userId}/")
    public List<User> getUser(@PathVariable int userId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("id",2).le("id",3);
        return userMapper.selectList(queryWrapper);
    }

    @GetMapping("/user/add/{userId}/{username}/{password}/")
    public String addUser(@PathVariable int userId, @PathVariable String username, @PathVariable String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String encoder = passwordEncoder.encode(password);

       // User user = new User(userId,username,encoder);
       // userMapper.insert(user);
        return "Add user successful";
    }

    @GetMapping("/user/delete/{userId}/")
    public String deleteUser(@PathVariable int userId) {
        userMapper.deleteById(userId);
        return "delete user successful";
    }
}
