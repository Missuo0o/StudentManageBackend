package zhangshun.service;

import org.springframework.transaction.annotation.Transactional;
import zhangshun.domain.PageBean;
import zhangshun.domain.StuDetails;
import zhangshun.domain.TeacherDetails;
import zhangshun.domain.TeacherStudent;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public interface TeacherManageService {
    //新增辅导员
    @Transactional(rollbackFor = Exception.class)
    boolean AddTeacher(TeacherDetails teacherDetails) throws SQLIntegrityConstraintViolationException;

    //删除辅导员
    boolean DeleteTeacher(String username);

    //批量删除辅导员
    boolean DeleteTeachers(String[] ids);

    //分页条件查询
    PageBean<TeacherDetails> SelectByPageAndCondition(int currentPage, int size, TeacherDetails teacherDetails);

    //查找单个辅导员
    TeacherDetails SelectByUsername(String username);

    //修改辅导员
    boolean UpdateTeacher(TeacherDetails teacherDetails) throws SQLIntegrityConstraintViolationException;

    //查询辅导员名下的学生
    List<StuDetails> SelectStudent(String username);

    //查询没有辅导员的学生
    List<StuDetails> SelectStudentNull();

    @Transactional(rollbackFor = Exception.class)
        //更新辅导员名下学生
    boolean StudentUpdate(TeacherStudent teacherStudent);

    //Excel新增老师
    @Transactional(rollbackFor = Exception.class)
    void ExcelAddTeacher(List<TeacherDetails> teacherDetails);

}
