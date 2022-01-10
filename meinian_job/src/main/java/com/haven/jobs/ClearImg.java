package com.haven.jobs;

import com.haven.constant.RedisConstant;
import com.haven.utils.QiniuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Set;

public class ClearImg {

    @Autowired
    private JedisPool jedisPool;
    public void clear(){
        Set<String> sdiff = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        sdiff.forEach((s)->{
//在云端删除垃圾图片
            QiniuUtil.delete(s);
            System.out.println("delete picture="+s);
//在redis删除垃圾图片名称
            jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,s);
        });

    }
}
