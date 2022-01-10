package com.haven.utils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

public class QiniuUtil {
//    上传文件
    public static String  upload(byte[] bytes,String filename){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Zone.zone2());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = "6Zpn8W-I61u1uguPf36BHpj9f3bJsRR6Z96RpOQZ";
        String secretKey = "ENysaCmXswpkTNSJBHBVOMK8emuZTxSxvXuXWSz7";
        String bucket = "meinianhaven";
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        int i = filename.lastIndexOf(".");
        String substring = filename.substring(i);
        String key = UUID.randomUUID().toString()+substring;
        try {

            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            try {
                Response response = uploadManager.put(bytes, key, upToken);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return key;
    }


//    删除文件
    public static  void delete(String filename){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Zone.zone2());
        //...其他参数参考类注释
        String accessKey = "6Zpn8W-I61u1uguPf36BHpj9f3bJsRR6Z96RpOQZ";
        String secretKey = "ENysaCmXswpkTNSJBHBVOMK8emuZTxSxvXuXWSz7";
        String bucket = "meinianhaven";
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, filename);
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }

    }

 }


