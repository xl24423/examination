package cn.xl.examination.constBag;

import org.springframework.beans.factory.annotation.Value;

public class OssConst {

    public static String accessKeyId;
    public static String secret = "";
    @Value("${aliyun.oss.access-key-id}")
    public void setAccessKeyId(String accessKeyId){
        OssConst.accessKeyId = accessKeyId;
    }
    @Value("${aliyun.oss.access-key-secret}")
    public void setSecret(String secret){
        OssConst.secret = secret;
    }


    public static final String bucketName = "xionglei-bucket";
    public static final String regionId = "cn-beijing";
    public static final String roleArn = "acs:ram::1927946583376631:role/xiongleioss";
    public static final String endPoint = "oss-cn-beijing.aliyuncs.com";
}
