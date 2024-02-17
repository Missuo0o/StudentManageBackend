package zhangshun.service;

import org.springframework.transaction.annotation.Transactional;
import zhangshun.domain.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public interface CourseService {

    //学生选课
    @Transactional(rollbackFor = Exception.class)
    boolean AddStudentCourse(int id, Course_Student course_student);

    //学生退课
    @Transactional(rollbackFor = Exception.class)
    boolean DeleteStudentCourse(int id, Course_Student course_student);

    //分页查询所有课程
    PageBean<Course> SelectCourseByPageAndCondition(int currentPage, int size, Course course);

    //分页查询已选课程
    PageBean<Course> SelectAlreadyCourseByPageAndCondition(int currentPage, int size, Course course, String username);


    //查询学生所有已选课程
    List<Course> SelectAlreadyCourse(Course_Student course_student);

    //批量删除
    boolean DeleteCourses(int[] ids);

    //删除
    boolean DeleteById(int id);

    //管理员分页查询所有课程
    PageBean<Course> SelectAllCourseByPageAndCondition(int currentPage, int size, Course course);

    //更新
    boolean UpdateCourse(Course course) throws SQLIntegrityConstraintViolationException;

    //新增课程
    boolean AddCourse(Course course) throws SQLIntegrityConstraintViolationException;

    //按ID查
    Course SelectById(int id);

    //更新状态
    boolean updateStatus(int id);

    //Excel上传
    void ExcelAddCourse(List<Course> courseList);

    //查询该课程的学生
    List<StuDetails> SelectStudentCourse(int id);

    //查询无课程的学生
    List<StuDetails> SelectStudentNull(int id);

    //更新课程名下学生
    @Transactional(rollbackFor = Exception.class)
    boolean CourseStudentUpdate(CourseStudent courseStudent);

}
