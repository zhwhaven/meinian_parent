import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.BatchStatus;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class testQiNiuYun {

    @Test
    public void test01(){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Zone.zone2());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = "6Zpn8W-I61u1uguPf36BHpj9f3bJsRR6Z96RpOQZ";
        String secretKey = "ENysaCmXswpkTNSJBHBVOMK8emuZTxSxvXuXWSz7";
        String bucket = "meinianhaven";
        //如果是Windows情况下，格式是 D:\\qiniu\\test.png
        String localFilePath = "C:\\Users\\Administrator\\Pictures\\Camera Roll\\u.jpg";
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        int lastIndexOf = localFilePath.lastIndexOf(".");
        String substring = localFilePath.substring(lastIndexOf);
        String key = UUID.randomUUID().toString()+substring;
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
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
    }
    @Test
    public void test02(){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Zone.zone2());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = "6Zpn8W-I61u1uguPf36BHpj9f3bJsRR6Z96RpOQZ";
        String secretKey = "ENysaCmXswpkTNSJBHBVOMK8emuZTxSxvXuXWSz7";
        String bucket = "meinianhaven";
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        FileInputStream inputStream=null;
        try {
            inputStream=new FileInputStream("C:\\Users\\Administrator\\Pictures\\Camera Roll\\u.jpg");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String key = UUID.randomUUID().toString()+".jpg";
        try {
//            byte[] uploadBytes = "hello qiniu cloud".getBytes("utf-8");
            byte[] uploadBytes=new byte[inputStream.available()];
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            try {
                Response response = uploadManager.put(uploadBytes, key, upToken);
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
        } catch (UnsupportedEncodingException ex) {
            //ignore
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test03(){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Zone.zone2());
        //...其他参数参考类注释
        String accessKey = "6Zpn8W-I61u1uguPf36BHpj9f3bJsRR6Z96RpOQZ";
        String secretKey = "ENysaCmXswpkTNSJBHBVOMK8emuZTxSxvXuXWSz7";
        String bucket = "meinianhaven";
        String key = "0c6302aa-f4cd-43f4-aa3f-6cc32f2ddba0.jpg";
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, key);
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }

    }

}
