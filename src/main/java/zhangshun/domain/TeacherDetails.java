package zhangshun.domain;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@ExcelIgnoreUnannotated
public class TeacherDetails {
    @ExcelProperty("教工号")
    @NotBlank
    @Length(max = 7)
    @Pattern(regexp = "^[^\\u4e00-\\u9fa5]*$", message = "账号不能包括中文")
    private String username;
    @ExcelProperty("密码")
    @Length(max = 10)
    @Pattern(regexp = "^[^\\u4e00-\\u9fa5]*$", message = "密码不能包括中文")
    private String password;
    @ExcelProperty("姓名")
    @NotBlank
    @Length(max = 4)
    private String name;
    @ExcelProperty("性别")
    @NotBlank
    @Length(max = 1)
    private String sex;
    @ExcelProperty("手机")
    @Pattern(regexp = "^1(3|4|5|7|8)\\d{9}$", message = "手机号码格式错误")
    private String phone;
    @ExcelProperty("地址")
    private String address;
    @ExcelProperty("头像")
    private String profile;
    private String oldusername;

}
