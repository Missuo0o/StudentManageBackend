package zhangshun.service;

import zhangshun.domain.AdminDetails;
import zhangshun.domain.StuDetails;
import zhangshun.domain.TeacherDetails;
import zhangshun.domain.User;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;

public interface DetailsService {
    //查询学生个人详情
    StuDetails SelectStudentDetailsData(String username);

    //查询老师个人详情
    TeacherDetails SelectTeacherDetailsData(String username);

    //查询管理员个人详情
    AdminDetails SelectAdminDetailsData(String username);

    //上传个人头像
    boolean FileUpload(String username, String profile, int identity) throws IOException;

    //查询用户原密码
    String SelectPwd(String username);

    //更新用户密码
    boolean UpdatePwd(User user);

    //更新学生个人详情
    boolean UpdateStuDetails(StuDetails stuDetails) throws SQLIntegrityConstraintViolationException;

    //更新老师个人详情
    boolean UpdateTeaDetails(TeacherDetails teaDetails) throws SQLIntegrityConstraintViolationException;

    //更新管理员个人详情
    boolean UpdateAdmDetails(AdminDetails admDetails) throws SQLIntegrityConstraintViolationException;
}
