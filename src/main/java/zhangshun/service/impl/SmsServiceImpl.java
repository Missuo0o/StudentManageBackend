package zhangshun.service.impl;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import zhangshun.dao.SmsDao;
import zhangshun.domain.Sms;
import zhangshun.domain.User;
import zhangshun.service.SmsService;
import zhangshun.utils.CodeUtils;

@Service
public class SmsServiceImpl implements SmsService {

    @Autowired
    CodeUtils codeUtils;

    @Autowired
    SmsDao smsDao;

    @Override
    @CachePut(value = "smsCode", key = "#phone")
    public String sendCode(String phone) {
        try {
            // 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey,此处还需注意密钥对的保密
            // 密钥可前往https://console.cloud.tencent.com/cam/capi网站进行获取
            Credential cred = new Credential("", "");
            // 实例化一个http选项，可选的，没有特殊需求可以跳过
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("sms.tencentcloudapi.com");
            // 实例化一个client选项，可选的，没有特殊需求可以跳过
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            // 实例化要请求产品的client对象,clientProfile是可选的
            SmsClient client = new SmsClient(cred, "ap-nanjing", clientProfile);
            // 实例化一个请求对象,每个接口都会对应一个request对象
            SendSmsRequest req = new SendSmsRequest();
            String[] phoneNumberSet1 = {"+86" + phone};
            req.setPhoneNumberSet(phoneNumberSet1);

            req.setSmsSdkAppId("");
            req.setSignName("");
            req.setTemplateId("");

            String code = codeUtils.generator(phone);

            String[] templateParamSet1 = {code, "1"};
            req.setTemplateParamSet(templateParamSet1);

            // 返回的resp是一个SendSmsResponse的实例，与请求对象对应
            SendSmsResponse resp = client.SendSms(req);
            // 输出json格式的字符串回包
            System.out.println(SendSmsResponse.toJsonString(resp));
            return code;
        } catch (TencentCloudSDKException e) {
            return null;
        }
    }

    @Override
    public boolean checkCode(Sms sms) {
        String code = sms.getCode();
        String cacheCode = codeUtils.getCode(sms.getPhone());
        return code.equals(cacheCode);
    }

    @Override
    public boolean UpdatePwd(Sms sms, int identity) {
        sms.setPassword(Md5Crypt.md5Crypt(sms.getPassword().trim().getBytes(), "$1$ShunZhang"));
        if (identity == 1) {
            return smsDao.updatePwd("stuDetails", sms) > 0;
        } else if (identity == 2) {
            return smsDao.updatePwd("teacherDetails", sms) > 0;
        } else if (identity == 3) {
            return smsDao.updatePwd("adminDetails", sms) > 0;
        }
        return false;
    }

    @Override
    public User selectIdentity(String username) {
        return smsDao.selectIdentity(username);
    }

}
