
import java.net.URL;
import java.util.Date;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/*
新建RAM账户，授权：AliyunOSSReadOnlyAccess，AliyunSTSAssumeRoleAccess
新建RAM角色，授权：AliyunOSSReadOnlyAccess
*/

public class MainTest {
	public static void main(String[] args) throws Exception {
		String endpoint = "sts.aliyuncs.com";
        String accessKeyId = "LTAI4GEya";
        String accessKeySecret = "3Di5yzv38";
        String roleArn = "acs:ram::17765";
        String roleSessionName = "myapp";
        
        JsonObject policy = new JsonObject();
        JsonArray statement = new JsonArray();
        policy.addProperty("Version", "1");
        
        JsonObject item = new JsonObject();
        item.addProperty("Effect", "Allow");
        item.addProperty("Action", "oss:GetObject");
        
        JsonArray resource = new JsonArray();
        resource.add("acs:oss:*:*:javaweb-community");
        
        item.add("Resource", resource);
        
        statement.add(item);
        policy.add("Statement", statement);
        
        try {
            DefaultProfile.addEndpoint("", "Sts", endpoint);
            IClientProfile profile = DefaultProfile.getProfile("", accessKeyId, accessKeySecret);
            DefaultAcsClient client = new DefaultAcsClient(profile);
            final AssumeRoleRequest request = new AssumeRoleRequest();
            request.setSysMethod(MethodType.POST);
            request.setRoleArn(roleArn);
            request.setRoleSessionName(roleSessionName);
            request.setPolicy(policy.toString()); 	// 若policy为空，则用户将获得该角色下所有权限
            request.setDurationSeconds(1000L); 		// 设置凭证有效时间
            final AssumeRoleResponse response = client.getAcsResponse(request);
            
            System.out.println("Expiration: " + response.getCredentials().getExpiration());
            System.out.println("Access Key Id: " + response.getCredentials().getAccessKeyId());
            System.out.println("Access Key Secret: " + response.getCredentials().getAccessKeySecret());
            System.out.println("Security Token: " + response.getCredentials().getSecurityToken());
            System.out.println("RequestId: " + response.getRequestId());
            
            OSS ossClient = new OSSClientBuilder().build("oss-cn-beijing.aliyuncs.com", accessKeyId, accessKeySecret);

            // 设置URL过期时间为1小时。
            Date expiration = new Date(new Date().getTime() + (3600 * 1000));
            
            // 生成以GET方法访问的签名URL，访客可以直接通过浏览器访问相关内容。
            URL url = ossClient.generatePresignedUrl("javaweb-community", "2020/07/06/36b91863ced64cce953cb312bce9e99d.jpg", expiration);

            // 关闭OSSClient。
            ossClient.shutdown();
            
            System.out.println(url);
            
        } catch (ClientException e) {
            System.out.println("Failed：");
            System.out.println("Error code: " + e.getErrorCode());
            System.out.println("Error message: " + e.getErrorCode());
            System.out.println("RequestId: " + e.getRequestId());
        }
	}
}



    	

    	