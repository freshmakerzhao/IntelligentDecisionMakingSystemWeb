package com.example.demo.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.entity.Travel;
import com.example.demo.service.ITravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Description TODO
 * @Author ZhaoShuai
 * @Date 2023/6/5 1:11
 */
@Component
public class TaskUtil {

    @Autowired
    private ITravelService travelService;

    public static TaskUtil taskUtil;

    @PostConstruct
    public void init() {
        taskUtil = this;
    }

    // 五分钟检查一次，并删除超过出发时间的出行帖子
    @Scheduled(cron = "* 0/5 * * * ?")
    private static void process(){
        taskUtil.travelService.remove(new QueryWrapper<Travel>().apply("from_time <= DATE_SUB(NOW(), INTERVAL 5 MINUTE)"));
    }
}
