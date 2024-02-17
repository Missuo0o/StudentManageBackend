package zhangshun.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import zhangshun.domain.*;
import zhangshun.service.CourseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
//@CrossOrigin(allowCredentials = "true", originPatterns = "*")
public class CourseController {
    @Autowired
    private CourseService courseService;

    //学生选课
    @PostMapping("/student/addCourse/{id}")
    public Result AddStudentCourse(@PathVariable int id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("username");
        Course_Student course_student = new Course_Student();
        course_student.setCourseid(id);
        course_student.setStudentid(user.getUsername());
        boolean flag = courseService.AddStudentCourse(id, course_student);
        return new Result(flag, flag ? Code.CREATED_OK : Code.CREATED_ERR);
    }

    //学生退课
    @PostMapping("/student/deleteCourse/{id}")
    public Result DeleteStudentCourse(@PathVariable int id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("username");
        Course_Student course_student = new Course_Student();
        course_student.setCourseid(id);
        course_student.setStudentid(user.getUsername());
        boolean flag = courseService.DeleteStudentCourse(id, course_student);
        return new Result(flag, flag ? Code.CREATED_OK : Code.CREATED_ERR);
    }

    @PostMapping("/student/course/selectAllByPageAndCondition/{currentPage}/{pageSize}")
    public Result SelectAllByPageAndCondition(@PathVariable int currentPage, @PathVariable int pageSize, @RequestBody Course course) {
        PageBean<Course> coursePageBean = courseService.SelectCourseByPageAndCondition(currentPage, pageSize, course);
        int code = coursePageBean != null ? Code.GET_OK : Code.GET_ERR;
        return new Result(coursePageBean, code);
    }

    //学生分页查询课程
    @PostMapping("/student/course/selectAlreadyByPageAndCondition/{currentPage}/{pageSize}")
    public Result SelectAlreadyByPageAndCondition(@PathVariable int currentPage, @PathVariable int pageSize, @RequestBody Course course, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("username");
        PageBean<Course> coursePageBean = courseService.SelectAlreadyCourseByPageAndCondition(currentPage, pageSize, course, user.getUsername());
        int code = coursePageBean != null ? Code.GET_OK : Code.GET_ERR;
        return new Result(coursePageBean, code);
    }

    //学生课程表
    @GetMapping("/student/course/selectAlready")
    public Result SelectAlreadyByPageAndCondition(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("username");
        Course_Student course_student = new Course_Student();
        course_student.setStudentid(user.getUsername());
        List<Course> course = courseService.SelectAlreadyCourse(course_student);

        String[][] arr = new String[5][12];
        //code by Leo
        for (Course c : course) {
            for (int i = c.getStart(); i <= c.getEnd(); i++) {
                arr[c.getWeek() - 1][i - 1] = c.getName();
            }
        }

        int code = course.size() > 0 ? Code.GET_OK : Code.GET_ERR;
        return new Result(arr, code);
    }

    //批量删除
    @DeleteMapping("/admin/course/deleteCourses")
    public Result DeleteRecords(@RequestBody int[] ids) {
        boolean flag = courseService.DeleteCourses(ids);
        return new Result(flag, flag ? Code.CREATED_OK : Code.CREATED_ERR);
    }

    //删除
    @DeleteMapping("/admin/course/{id}")
    public Result DeleteById(@PathVariable int id) {
        boolean flag = courseService.DeleteById(id);
        return new Result(flag, flag ? Code.CREATED_OK : Code.CREATED_ERR);
    }

    //管理员分页查询课程
    @PostMapping("/admin/course/selectAllByPageAndCondition/{currentPage}/{pageSize}")
    public Result SelectAdminAllByPageAndCondition(@PathVariable int currentPage, @PathVariable int pageSize, @RequestBody Course course) {
        PageBean<Course> coursePageBean = courseService.SelectAllCourseByPageAndCondition(currentPage, pageSize, course);
        int code = coursePageBean != null ? Code.GET_OK : Code.GET_ERR;
        return new Result(coursePageBean, code);
    }

    //更新课程
    @PutMapping("/admin/course")
    public Result UpdateById(@RequestBody @Validated Course course) throws SQLIntegrityConstraintViolationException {
        boolean flag = courseService.UpdateCourse(course);
        return new Result(flag, flag ? Code.CREATED_OK : Code.CREATED_ERR);
    }

    //新增课程
    @PostMapping("/admin/course")
    public Result AddCourse(@RequestBody @Validated Course course) throws SQLIntegrityConstraintViolationException {
        boolean flag = courseService.AddCourse(course);
        return new Result(flag, flag ? Code.CREATED_OK : Code.CREATED_ERR);
    }

    //回显数据
    @GetMapping("/admin/course/{id}")
    public Result SelectById(@PathVariable int id) {
        Course course = courseService.SelectById(id);
        int code = course != null ? Code.GET_OK : Code.GET_ERR;
        return new Result(course, code);
    }

    //更新课程状态
    @PutMapping("/admin/courseBegin")
    public Result UpdateBeginStatus() {
        boolean flag = courseService.updateStatus(1);
        return new Result(flag, flag ? Code.CREATED_OK : Code.CREATED_ERR);
    }

    //更新课程状态
    @PutMapping("/admin/courseStop")
    public Result UpdateStopStatus() {
        boolean flag = courseService.updateStatus(0);
        return new Result(flag, flag ? Code.CREATED_OK : Code.CREATED_ERR);
    }

    //初始化穿梭栏
    @GetMapping("/admin/selectStudent/{id}")
    public Result SelectStudent(@PathVariable int id) {
        //该课名下的学生
        List<StuDetails> stuDetails = courseService.SelectStudentCourse(id);
        //没有课程的学生
        List<StuDetails> stuDetails1 = courseService.SelectStudentNull(id);

        Map<String, List<StuDetails>> map = new HashMap<>();
        map.put("notNull", stuDetails);
        map.put("null", stuDetails1);
        return new Result(map, Code.GET_OK);
    }

    //更新学生名下课程
    @PostMapping("/admin/updateStudent")
    public Result UpdateStudent(@RequestBody CourseStudent courseStudent) {
        boolean flag = courseService.CourseStudentUpdate(courseStudent);
        return new Result(flag, flag ? Code.CREATED_OK : Code.CREATED_ERR);
    }
}
