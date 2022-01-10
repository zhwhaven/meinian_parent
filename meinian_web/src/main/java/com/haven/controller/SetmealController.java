package com.haven.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.Page;
import com.haven.constant.MessageConstant;
import com.haven.constant.RedisConstant;
import com.haven.constant.RedisMessageConstant;
import com.haven.entry.PageResult;
import com.haven.entry.QueryPageBean;
import com.haven.entry.Result;
import com.haven.pojo.Setmeal;
import com.haven.service.SetmealService;
import com.haven.utils.QiniuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {
  @Reference
    SetmealService setmealService;
  @Autowired
  JedisPool jedisPool;
  @RequestMapping("/upload.do")
  public Result upload(MultipartFile imgFile){
    String originalFilename = imgFile.getOriginalFilename();
    try {
      String upload = QiniuUtil.upload(imgFile.getBytes(), originalFilename);
      jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,upload);
      return new Result(true, MessageConstant.UPLOAD_SUCCESS,upload);
    } catch (IOException e) {
      e.printStackTrace();
      return new Result(false,MessageConstant.PIC_UPLOAD_FAIL);
    }
  }
  @RequestMapping("/add.do")
  public Result add(Integer[] travelgroupIds, @RequestBody Setmeal setmeal){
    List<Integer> list = Arrays.stream(travelgroupIds).collect(Collectors.toList());
    try {
      setmealService.add(list,setmeal);
      jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,setmeal.getImg());
      return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
    } catch (Exception e) {
      e.printStackTrace();
      return new Result(true,MessageConstant.ADD_SETMEAL_FAIL);
    }

  }

  @RequestMapping("/findPage.do")
  public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
       Page page=setmealService.findPage(queryPageBean);
       return new PageResult(page.getTotal(),page.getResult());
  }
}
