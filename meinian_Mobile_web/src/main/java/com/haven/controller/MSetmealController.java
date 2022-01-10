package com.haven.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.Page;
import com.haven.constant.MessageConstant;
import com.haven.entry.Result;
import com.haven.pojo.Setmeal;
import com.haven.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

@RestController
@RequestMapping("/m_setmeal")
public class MSetmealController {
    @Autowired
    JedisPool jedisPool;
    @Reference
    SetmealService setmealService;
    @RequestMapping("/getSetmealm.do")
    public Result getSetmealm(){
        try {
            Page page=setmealService.getSetmeal();
            return new Result(true, MessageConstant.GET_SETMEAL_LIST_SUCCESS,page);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS);
        }
    }

    @RequestMapping("/findById.do")
    public Result findByIdAll(Integer id){
        Setmeal setmeal=setmealService.findById(id);
        return new Result(true,"查询成功",setmeal);
    }

}
