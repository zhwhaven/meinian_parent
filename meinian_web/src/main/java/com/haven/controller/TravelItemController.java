package com.haven.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.haven.constant.MessageConstant;
import com.haven.entry.PageResult;
import com.haven.entry.QueryPageBean;
import com.haven.entry.Result;
import com.haven.pojo.TravelItem;
import com.haven.service.TravelItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
//Contrller+Reposebody
//travelItem/add.do
@RequestMapping("/travelItem")
public class TravelItemController {
    @Reference
    TravelItemService travelItemService;
    @RequestMapping("/add.do")
    @PreAuthorize("hasAuthority('add')")
    public Result add(@RequestBody TravelItem travelItem){
        System.out.println("进入添加自由行界面");
        try {
            travelItemService.add(travelItem);
            return new Result(true, MessageConstant.ADD_TRAVELITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_TRAVELITEM_FAIL);
        }
    }
    @RequestMapping("/findPage.do")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult page=travelItemService.findPage(queryPageBean);
        return page;
    }

    @RequestMapping("/findAll.do")
    public Result findAll(){
        System.out.println("进入查找自由行全部信息");
        List<TravelItem> list=travelItemService.findAll();
        System.out.println(list);
        return new Result(true,"返回成功",list);
    }

}
