package zhangshun.domain;

import lombok.Data;

import java.util.List;

@Data
public class TeacherStudent {
    private String teachername;
    private String teacherid;
    private List<String> usernameNotNull;
    private List<String> usernameNull;
}
