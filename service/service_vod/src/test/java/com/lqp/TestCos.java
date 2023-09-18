package com.lqp;

import com.alibaba.fastjson.JSON;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;

import java.io.File;

/**
 * @author liqiuping
 * @version v1.0.0
 * @ClassName TestCos
 * @Package : com.lqp
 * @Description :
 * @Create on : 2023/8/22 22:46
 */
public class TestCos {

    public static void main(String[] args) {
        String secretId = "AKID3yhEbkivxpV1OaZXPHVghWBdLxaH9tcG";
        String secretKey = "Yu7kNyb6bxLZb2Wq01rbCoSwEI9qpbBa";
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        Region region = new Region("ap-nanjing");
        ClientConfig clientConfig = new ClientConfig(region);
        clientConfig.setHttpProtocol(HttpProtocol.https);
        COSClient cosClient = new COSClient(cred, clientConfig);


        File localFile = new File("/Volumes/Macintosh HD 1/Users/liqiuping/Pictures/wallhaven-wq778r.jpeg");
        String bucketName = "sizuru-1302583246";
        String key = "/picture/wallhaven-wq778r.jpeg";
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        System.out.println("JSON.toJSONString(putObjectResult) = " + JSON.toJSONString(putObjectResult));
    }
}
