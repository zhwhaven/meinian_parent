package com.haven.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.haven.constant.MessageConstant;
import com.haven.constant.RedisConstant;
import com.haven.constant.RedisMessageConstant;
import com.haven.entry.Result;
import com.haven.utils.SMSUtils;
import com.haven.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {
  @Autowired
    JedisPool jedisPool;
  @RequestMapping("/send4Order.do")
    public Result send4Order(String telephone){
      System.out.println("进入验证点");
      String code4String = ValidateCodeUtils.generateValidateCode4String(6);
      try {
          SMSUtils.sendShortMessage(telephone,code4String);
          jedisPool.getResource().setex(RedisMessageConstant.SENDTYPE_ORDER+telephone,1000,code4String);
          System.out.println("code==>"+code4String);
          return new Result(true, "返回验证码成功");
      } catch (Exception e) {
          e.printStackTrace();
          return new Result(false, "返回验证码失败");
      }

  }

}
