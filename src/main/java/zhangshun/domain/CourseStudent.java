package zhangshun.domain;

import lombok.Data;

import java.util.List;

@Data
public class CourseStudent {
    private int courseid;
    private List<String> usernameNotNull;
    private List<String> usernameNull;
}
