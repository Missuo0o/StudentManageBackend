package zhangshun.domain;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@ExcelIgnoreUnannotated
public class Dormitory {
    private int id;
    @ExcelProperty("单元")
    @Positive
    private int building;
    @ExcelProperty("楼层")
    @Positive
    private int floor;
    @ExcelProperty("室")
    @Positive
    private int room;
    @ExcelProperty("价格")
    @DecimalMax("2000")
    private BigDecimal price;
    @ExcelProperty("上限人数")
    @Positive
    private int limited;
    private int current;
    private int version;
    private int deleted;
}

