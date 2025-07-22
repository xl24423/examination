package cn.xl.examination.constBag;

import org.springframework.beans.factory.annotation.Value;

public class OssConst {

    public static String accessKeyId = System.getenv("OSS_ACCESS_KEY_ID");;
    public static String secret = System.getenv("OSS_ACCESS_KEY_SECRET");;

    public static final String bucketName = "xionglei-bucket";
    public static final String regionId = "cn-beijing";
    public static final String roleArn = "acs:ram::1927946583376631:role/xiongleioss";
    public static final String endPoint = "oss-cn-beijing.aliyuncs.com";
}
