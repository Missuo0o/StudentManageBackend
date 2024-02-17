package zhangshun.service;

import zhangshun.domain.Sms;
import zhangshun.domain.User;

public interface SmsService {
    //发送短信验证码
    String sendCode(String phone);

    //验证短信验证码
    boolean checkCode(Sms sms);

    //查询用户密码
    boolean UpdatePwd(Sms sms, int identity);

    //查询用户身份
    User selectIdentity(String username);
}
