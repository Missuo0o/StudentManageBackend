package zhangshun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import zhangshun.domain.Code;
import zhangshun.domain.Result;
import zhangshun.domain.User;
import zhangshun.service.DashBoardService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class DashBoardController {
    @Autowired
    private DashBoardService dashBoardService;

    @GetMapping("/admin/getData")
    public Result AdminGetData() {
        int[] arr = new int[7];
        arr[0] = dashBoardService.SelectCourse();
        arr[1] = dashBoardService.SelectDormitory();
        arr[2] = dashBoardService.SelectStudentTeacher();
        arr[3] = dashBoardService.SelectStudentHealthRecord();
        arr[4] = dashBoardService.SelectTeacherHealthRecord();
        arr[5] = dashBoardService.SelectLeaveRecord();
        arr[6] = dashBoardService.SelectStudentStatus();
        return new Result(arr, Code.GET_OK);
    }

    @GetMapping("/teacher/getData")
    public Result TeacherGetData(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("username");
        int[] arr = new int[3];
        arr[0] = dashBoardService.TeacherSelectCourse(user.getUsername());
        arr[1] = dashBoardService.TeacherSelectDormitory(user.getUsername());
        arr[2] = dashBoardService.TeacherSelectLeaveRecord(user.getUsername());
        return new Result(arr, Code.GET_OK);
    }

}
