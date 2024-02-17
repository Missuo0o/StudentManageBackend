package zhangshun.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;

@Data
public class LeaveRecord {
    private int id;
    @NotBlank
    @Length(max = 7)
    @Pattern(regexp = "^[^\\u4e00-\\u9fa5]*$", message = "账号不能包括中文")
    private String username;
    @NotBlank
    @Length(max = 4)
    private String name;
    @Pattern(regexp = "^1(3|4|5|7|8)\\d{9}$", message = "手机号码格式错误")
    private String phone;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @FutureOrPresent
    private Timestamp outtime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @FutureOrPresent
    private Timestamp intime;
    @NotBlank
    @Length(max = 2)
    private String typename;
    private String remark;
    private int deleted;
    private int status;
}
