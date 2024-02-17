package zhangshun.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Data
public class InformationRecord {
    private int id;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp createtime;
    private String adminid;
    private String adminname;
    private int deleted;
    private int status;
}
