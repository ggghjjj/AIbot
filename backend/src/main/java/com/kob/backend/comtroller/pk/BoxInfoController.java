package com.kob.backend.comtroller.pk;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/pk/")
public class BoxInfoController {
    @RequestMapping("boxinfo")
    public Map<String,String> getBox() {
        Map<String,String> ad = new HashMap<>();
        ad.put("name","ggghj");
        ad.put("age","18");
        ad.put("sex","男");
        return ad;
    }
}
