package zhangshun.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class User {
    @NotBlank
    @Length(max = 7)
    @Pattern(regexp = "^[^\\u4e00-\\u9fa5]*$", message = "账号不能包括中文")
    private String username;
    @NotBlank
    @Length(max = 10)
    @Pattern(regexp = "^[^\\u4e00-\\u9fa5]*$", message = "密码不能包括中文")
    private String password;
    private int identity;
    private String name;
    private int deleted;

}
