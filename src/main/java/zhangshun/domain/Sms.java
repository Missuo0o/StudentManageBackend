package zhangshun.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class Sms {
    @NotBlank
    @Length(max = 7)
    @Pattern(regexp = "^[^\\u4e00-\\u9fa5]*$", message = "账号不能包括中文")
    private String username;
    @Pattern(regexp = "^1(3|4|5|7|8)\\d{9}$", message = "手机号码格式错误")
    private String phone;
    private String code;
    private int identity;
    @NotBlank
    @Length(max = 10)
    @Pattern(regexp = "^[^\\u4e00-\\u9fa5]*$", message = "密码不能包括中文")
    private String password;
    @NotBlank
    @Length(max = 10)
    @Pattern(regexp = "^[^\\u4e00-\\u9fa5]*$", message = "密码不能包括中文")
    private String checkPass;

}
