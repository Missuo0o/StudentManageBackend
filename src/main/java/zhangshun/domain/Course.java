package zhangshun.domain;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
@ExcelIgnoreUnannotated
public class Course {
    private int id;
    @ExcelProperty("课程名")
    @NotBlank
    private String name;
    @ExcelProperty("上课时间")
    @Positive
    private int start;
    @ExcelProperty("下课时间")
    @Positive
    private int end;
    @ExcelProperty("上课星期")
    @Positive
    private int week;
    @ExcelProperty("上限人数")
    @Positive
    private int limit;
    private int current;
    private int version;
    private int status;
}
