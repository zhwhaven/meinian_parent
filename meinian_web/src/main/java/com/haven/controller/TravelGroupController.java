package com.haven.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.Page;
import com.haven.constant.MessageConstant;
import com.haven.entry.PageResult;
import com.haven.entry.QueryPageBean;
import com.haven.entry.Result;
import com.haven.pojo.TravelGroup;
import com.haven.service.TravelGroupService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/travelGroup")
public class TravelGroupController {
    @Reference
    TravelGroupService travelGroupService;
    @RequestMapping("/add.do")
    public Result add(Integer []travelItemIds, @RequestBody TravelGroup travelGroup){
//        List<Integer> collect = Arrays.stream(travelItemIds).collect(Collectors.toList());
        try {
            travelGroupService.add(travelItemIds,travelGroup);
            return new Result(true, MessageConstant.ADD_TRAVELGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.ADD_TRAVELGROUP_FAIL);
        }

    }
    @RequestMapping("/findPage.do")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){

        Page page=travelGroupService.findPage(queryPageBean);
        return new PageResult(page.getTotal(),page.getResult());
    }
    @RequestMapping("/getAll.do")
    public Result findAll(){

        List<TravelGroup> list=travelGroupService.findAll();
        return new Result(true,"success",list);
    }
}
