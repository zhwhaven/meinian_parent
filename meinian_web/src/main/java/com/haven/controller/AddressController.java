package com.haven.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.Page;
import com.haven.constant.MessageConstant;
import com.haven.entry.PageResult;
import com.haven.entry.QueryPageBean;
import com.haven.entry.Result;
import com.haven.service.AddressService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/address")
public class AddressController {
    @Reference
    AddressService addressService;
    @RequestMapping("/addAddress.do")
    public Result addAddress(@RequestBody Map Param){
        try {
            addressService.addAddress(Param);
            return new Result(true, "新增地址成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "新增地址失败");

        }

    }
    @RequestMapping("/findPage.do")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){

       Page page= addressService.findPage(queryPageBean);
       return new PageResult(page.getTotal(),page.getResult());
    }
    @RequestMapping("/findAllMaps.do")
    public Result findAllMaps(){
        Result result=addressService.findAllMaps();
        return result;
    }
}
