package com.haven.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.haven.entry.Result;
import com.haven.service.OrderService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Reference
    OrderService orderService;

    @RequestMapping("/submit.do")
    public Result submit(@RequestBody Map map){
        Result result=orderService.submit(map);
        return result;
    }
}
