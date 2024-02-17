package zhangshun.domain;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@ExcelIgnoreUnannotated

public class StuDetails {
    @ExcelProperty("学号")
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
    @ExcelProperty("学院")
    private String college;
    @ExcelProperty("专业")
    private String major;
    @ExcelProperty("班级")
    private String classname;
    @ExcelProperty("手机")
    @Pattern(regexp = "^1(3|4|5|7|8)\\d{9}$", message = "手机号码格式错误")
    private String phone;
    @ExcelProperty("家庭地址")
    private String address;
    @ExcelProperty("辅导员姓名")
    @Length(max = 4)
    private String teachername;
    @ExcelProperty("辅导员教工号")
    @Length(max = 10)
    private String teacherid;
    @ExcelProperty("头像地址")
    private String profile;
    @ExcelProperty("在校状态")
    @NotBlank
    @Length(max = 1)
    private String status;
    @ExcelProperty("入校地址")
    private String schooladdress;
    private String oldusername;
    private int dormitoryid;
    private int paied;
}
