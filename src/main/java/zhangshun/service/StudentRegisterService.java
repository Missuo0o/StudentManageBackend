package zhangshun.service;

import zhangshun.domain.StuDetails;

public interface StudentRegisterService {
    //更新学生在校状态以及入校地址
    boolean UpdateStudentStatus(StuDetails stuDetails);

    //查询学生在校状态
    String SelectStudentStatus(String username);
}
