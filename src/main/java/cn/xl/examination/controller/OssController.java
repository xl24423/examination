package cn.xl.examination.controller;

import cn.xl.examination.constBag.OssConst;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class OssController {


    @GetMapping("/api/sts-token")
    public ResponseEntity<?> getStsToken() throws ClientException {
        try {
            // 1. 初始化STS客户端
            DefaultProfile profile = DefaultProfile.getProfile(
                    OssConst.regionId,
                    OssConst.accessKeyId,
                    OssConst.secret
            );
            IAcsClient client = new DefaultAcsClient(profile);

            // 2. 构造请求
            AssumeRoleRequest request = new AssumeRoleRequest();
            request.setRoleArn(OssConst.roleArn); // RAM角色ARN
            request.setRoleSessionName("xiongleioss");
            request.setDurationSeconds(3600L); // 有效期1小时
            // 3. 获取临时凭证
            AssumeRoleResponse response = client.getAcsResponse(request);
            Map<String, String> result = new HashMap<>();
            result.put("AccessKeyId", response.getCredentials().getAccessKeyId());
            result.put("AccessKeySecret", response.getCredentials().getAccessKeySecret());
            result.put("SecurityToken", response.getCredentials().getSecurityToken());

            return ResponseEntity.ok(result);
        } catch (ClientException e) {
            Map<String, Object> errorDetail = new LinkedHashMap<>();
            errorDetail.put("timestamp", new Date());
            errorDetail.put("status", 403);
            errorDetail.put("errorCode", e.getErrCode());  // 重点获取的错误代码
            errorDetail.put("errorMessage", e.getErrMsg()); // 详细的错误消息
            errorDetail.put("requestId", e.getRequestId()); // 阿里云请求ID

            // 根据错误代码提供诊断建议
            switch(e.getErrCode()) {
                case "EntityNotExist.Role":
                    errorDetail.put("diagnosis", "角色不存在或ARN格式错误");
                    break;
                case "AccessDenied":
                    errorDetail.put("diagnosis", "权限不足，请检查信任策略和用户权限");
                    break;
                case "InvalidAccessKeyId.NotFound":
                    errorDetail.put("diagnosis", "AccessKey无效或已禁用");
                    break;
                default:
                    errorDetail.put("diagnosis", "请根据错误代码查阅阿里云文档");
            }

            return ResponseEntity.status(403).body(errorDetail);
        }
    }
}