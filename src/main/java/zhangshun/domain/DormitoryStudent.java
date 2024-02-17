package zhangshun.domain;

import lombok.Data;

import java.util.List;

@Data
public class DormitoryStudent {
    private int dormitoryid;
    private List<String> usernameNotNull;
    private List<String> usernameNull;
}
