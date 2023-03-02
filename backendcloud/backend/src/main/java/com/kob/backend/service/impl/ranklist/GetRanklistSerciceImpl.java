package com.kob.backend.service.impl.ranklist;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kob.backend.mapper.UserMapper;
import com.kob.backend.pojo.Bot;
import com.kob.backend.pojo.Record;
import com.kob.backend.pojo.User;
import com.kob.backend.service.ranklist.GetRanklistService;
import com.kob.backend.service.user.bot.GetListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Service
public class GetRanklistSerciceImpl implements GetRanklistService {

    @Autowired
    UserMapper userMapper;

    @Override
    public JSONObject getList(Integer page) {
        IPage<User> userIpage = new Page<>(page,10);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.orderByDesc("rating");
        List<User> users = userMapper.selectPage(userIpage,queryWrapper).getRecords();
        for(User user : users) {
            user.setPassword("");
        }
        JSONObject resp = new JSONObject();
        resp.put("users",users);
        resp.put("users_count",userMapper.selectCount(null));
        return resp;
    }
}
