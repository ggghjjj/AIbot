package com.kob.backend.comtroller.ranklist;

import com.alibaba.fastjson.JSONObject;
import com.kob.backend.service.ranklist.GetRanklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GrtRanklistController {

    @Autowired
    private GetRanklistService getRanklistService;

    @GetMapping("/api/ranklist/getlist/")
    public JSONObject getList(@RequestParam Map<String,String> data) {
        Integer page = Integer.parseInt(data.get("page"));
        return getRanklistService.getList(page);
    }
}
