package zhangshun.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zhangshun.domain.Code;
import zhangshun.domain.Result;
import zhangshun.domain.Sms;
import zhangshun.domain.User;
import zhangshun.service.SmsService;

@RestController
//@CrossOrigin(allowCredentials = "true", originPatterns = "*")
@RequestMapping("/sms")
public class SmsController {
    @Autowired
    private SmsService smsService;

    @PostMapping("/sendcode")
    public Result getCode(@RequestBody Sms sms) {
        String code = smsService.sendCode(sms.getPhone());
        return new Result(code != null ? Code.CREATED_OK : Code.CREATED_ERR);
    }

    @PostMapping("/checkcode")
    public Result checkCode(@RequestBody @Validated Sms sms) {
        boolean flag = smsService.checkCode(sms);
        if (flag) {
            User userIdentity = smsService.selectIdentity(sms.getUsername());
            int identityCode = userIdentity != null ? Code.GET_OK : Code.GET_ERR;
            //如果查询到该账号
            if (identityCode == Code.GET_OK) {
                if (!sms.getPassword().equals(sms.getCheckPass())) {
                    return new Result(null, Code.GET_ERR, "两次密码输入不一致");
                }
                boolean updateFlag = smsService.UpdatePwd(sms, userIdentity.getIdentity());
                return new Result(updateFlag, updateFlag ? Code.GET_OK : Code.GET_ERR, "更新成功");
            } else {
                return new Result(null, Code.GET_ERR, "账号错误");
            }
        } else {
            return new Result(null, Code.GET_ERR, "验证码有误");
        }
    }
}

