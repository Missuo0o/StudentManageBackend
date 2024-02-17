package zhangshun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import zhangshun.domain.*;
import zhangshun.service.DormitoryService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
//@CrossOrigin(allowCredentials = "true", originPatterns = "*")
public class DormitoryController {
    @Autowired
    private DormitoryService dormitoryService;

    @PostMapping("/student/addDormitory/{id}")
    public Result AddStudentDormitory(@PathVariable int id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("username");

        boolean flag = dormitoryService.AddStudentDormitory(id, user.getUsername());
        return new Result(flag, flag ? Code.CREATED_OK : Code.CREATED_ERR);
    }

    @PostMapping("/student/deleteDormitory/{id}")
    public Result DeleteStudentCourse(@PathVariable int id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("username");
        boolean flag = dormitoryService.DeleteStudentDormitory(id, user.getUsername());
        return new Result(flag, flag ? Code.CREATED_OK : Code.CREATED_ERR);
    }

    @GetMapping("/student/myDormitory")
    public Result MyDormitory(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("username");
        Dormitory dormitory = dormitoryService.MyDormitory(user.getUsername());
        int code = dormitory != null ? Code.GET_OK : Code.GET_ERR;
        return new Result(dormitory, code);
    }

    //学生分页查询宿舍
    @PostMapping({"/student/dormitory/selectAllByPageAndCondition/{currentPage}/{pageSize}", "/admin/dormitory/selectAllByPageAndCondition/{currentPage}/{pageSize}"})
    public Result SelectAllByPageAndCondition(@PathVariable int currentPage, @PathVariable int pageSize, @RequestBody Dormitory dormitory) {
        PageBean<Dormitory> coursePageBean = dormitoryService.SelectDormitoryByPageAndCondition(currentPage, pageSize, dormitory);
        int code = coursePageBean != null ? Code.GET_OK : Code.GET_ERR;
        return new Result(coursePageBean, code);
    }

    //学生分页查询已选宿舍
    @PostMapping("/student/dormitory/selectAlreadyByPageAndCondition/{currentPage}/{pageSize}")
    public Result SelectAlreadyByPageAndCondition(@PathVariable int currentPage, @PathVariable int pageSize, @RequestBody Dormitory dormitory, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("username");
        PageBean<Dormitory> coursePageBean = dormitoryService.SelectAlreadyCourseByPageAndCondition(currentPage, pageSize, dormitory, user.getUsername());
        int code = coursePageBean != null ? Code.GET_OK : Code.GET_ERR;
        return new Result(coursePageBean, code);
    }

    //管理员分页查询缴费情况
    @PostMapping("/admin/dormitory/selectPayByPageAndCondition/{currentPage}/{pageSize}")
    public Result SelectPayByPageAndCondition(@PathVariable int currentPage, @PathVariable int pageSize, @RequestBody StuDetails stuDetails) {
        PageBean<StuDetails> coursePageBean = dormitoryService.SelectPayByPageAndCondition(currentPage, pageSize, stuDetails);
        int code = coursePageBean != null ? Code.GET_OK : Code.GET_ERR;
        return new Result(coursePageBean, code);
    }

    //管理员分页查询未缴费
    @PostMapping("/admin/dormitory/selectNotPayByPageAndCondition/{currentPage}/{pageSize}")
    public Result SelectNotPayByPageAndCondition(@PathVariable int currentPage, @PathVariable int pageSize, @RequestBody StuDetails stuDetails) {
        PageBean<StuDetails> coursePageBean = dormitoryService.SelectNotPayByPageAndCondition(currentPage, pageSize, stuDetails);
        int code = coursePageBean != null ? Code.GET_OK : Code.GET_ERR;
        return new Result(coursePageBean, code);
    }

    @PutMapping("/admin/dormitory/success/{username}")
    public Result UpdateSuccessStatus(@PathVariable String username) {
        boolean flag = dormitoryService.UpdateSuccessPaid(username, 1);
        return new Result(flag, flag ? Code.CREATED_OK : Code.CREATED_ERR);
    }

    @PutMapping("/admin/dormitory/fail/{username}")
    public Result UpdateFailStatus(@PathVariable String username) {
        boolean flag = dormitoryService.UpdateFailPaid(username, 0);
        return new Result(flag, flag ? Code.CREATED_OK : Code.CREATED_ERR);
    }

    //删除
    @DeleteMapping("/admin/dormitory/{id}")
    public Result DeleteById(@PathVariable int id) {
        boolean flag = dormitoryService.DeleteById(id);
        return new Result(flag, flag ? Code.CREATED_OK : Code.CREATED_ERR);
    }

    //批量删除
    @DeleteMapping("/admin/dormitory/deleteDormitory")
    public Result DeleteRecords(@RequestBody int[] ids) {
        boolean flag = dormitoryService.DeleteDormitory(ids);
        return new Result(flag, flag ? Code.CREATED_OK : Code.CREATED_ERR);
    }

    //新增宿舍
    @PostMapping("/admin/dormitory")
    public Result AddDormitory(@RequestBody @Validated Dormitory dormitory) throws SQLIntegrityConstraintViolationException {
        boolean flag = dormitoryService.AddDormitory(dormitory);
        return new Result(flag, flag ? Code.CREATED_OK : Code.CREATED_ERR);
    }

    //更新宿舍
    @PutMapping("/admin/dormitory")
    public Result UpdateById(@RequestBody @Validated Dormitory dormitory) throws SQLIntegrityConstraintViolationException {
        boolean flag = dormitoryService.UpdateDormitory(dormitory);
        return new Result(flag, flag ? Code.CREATED_OK : Code.CREATED_ERR);
    }

    //回显数据
    @GetMapping("/admin/dormitory/{id}")
    public Result SelectById(@PathVariable int id) {
        Dormitory dormitory = dormitoryService.SelectById(id);
        int code = dormitory != null ? Code.GET_OK : Code.GET_ERR;
        return new Result(dormitory, code);
    }

    @GetMapping("/admin/selectDormitoryStudent/{id}")
    public Result SelectStudent(@PathVariable int id) {
        //该宿舍名下的学生
        List<StuDetails> stuDetails = dormitoryService.SelectStudentNotNull(id);
        //没有宿舍的学生
        List<StuDetails> stuDetails1 = dormitoryService.SelectStudentNull();

        Map<String, List<StuDetails>> map = new HashMap<>();
        map.put("notNull", stuDetails);
        map.put("null", stuDetails1);
        return new Result(map, Code.GET_OK);
    }

    @PostMapping("/admin/updateDormitoryStudent")
    public Result UpdateStudent(@RequestBody DormitoryStudent dormitoryStudent) {
        boolean flag = dormitoryService.StudentUpdate(dormitoryStudent);
        return new Result(flag, flag ? Code.CREATED_OK : Code.CREATED_ERR);
    }
}


