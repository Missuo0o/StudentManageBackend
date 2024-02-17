package zhangshun.controller;


import com.alibaba.excel.EasyExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import zhangshun.domain.*;
import zhangshun.service.*;
import zhangshun.utils.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@RestController
//@CrossOrigin(allowCredentials = "true", originPatterns = "*")
@RequestMapping("/admin")
public class ExcelController {
    @Autowired
    private StudentManageService studentManageService;

    @Autowired
    private TeacherManageService teacherManageService;

    @Autowired
    private AdminManageService adminManageService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private DormitoryService dormitoryService;

    @GetMapping("/download/student")
    public void DownloadStudent(HttpServletResponse response) throws IOException {
        List<StuDetails> data = new ArrayList<>();
        data.add(new StuDetails());
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("Student", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), StuDetails.class).sheet("Sheet1").doWrite(data);
    }

    @GetMapping("/download/teacher")
    public void DownloadTeacher(HttpServletResponse response) throws IOException {
        List<TeacherDetails> data = new ArrayList<>();
        data.add(new TeacherDetails());
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("Teacher", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), TeacherDetails.class).sheet("Sheet1").doWrite(data);
    }

    @GetMapping("/download/admin")
    public void DownloadAdmin(HttpServletResponse response) throws IOException {
        List<AdminDetails> data = new ArrayList<>();
        data.add(new AdminDetails());
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("Admin", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), AdminDetails.class).sheet("Sheet1").doWrite(data);
    }

    @GetMapping("/download/course")
    public void DownloadCourse(HttpServletResponse response) throws IOException {
        List<Course> data = new ArrayList<>();
        data.add(new Course());
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("Course", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), Course.class).sheet("Sheet1").doWrite(data);
    }

    @GetMapping("/download/dormitory")
    public void DownloadDormitory(HttpServletResponse response) throws IOException {
        List<Dormitory> data = new ArrayList<>();
        data.add(new Dormitory());
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("Dormitory", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), Dormitory.class).sheet("Sheet1").doWrite(data);
    }


    @PostMapping("/upload/student")
    public Result UploadStudent(MultipartFile file) throws IOException, SQLIntegrityConstraintViolationException {

        try {
            EasyExcel.read(file.getInputStream(), StuDetails.class, new StudentDataListener(studentManageService)).sheet().doRead();
            return new Result(null, Code.CREATED_OK);
        } catch (Exception e) {
            Throwable cause = e.getCause();
            if (cause instanceof SQLIntegrityConstraintViolationException) {
                throw new SQLIntegrityConstraintViolationException();
            } else {
                throw e;
            }
        }
    }

    @PostMapping("/upload/teacher")
    public Result UploadTeacher(MultipartFile file) throws IOException, SQLIntegrityConstraintViolationException {
        try {
            EasyExcel.read(file.getInputStream(), TeacherDetails.class, new TeacherDataListener(teacherManageService)).sheet().doRead();
            return new Result(null, Code.CREATED_OK);
        } catch (Exception e) {
            Throwable cause = e.getCause();
            if (cause instanceof SQLIntegrityConstraintViolationException) {
                throw new SQLIntegrityConstraintViolationException();
            } else {
                throw e;
            }
        }
    }

    @PostMapping("/upload/admin")
    public Result UploadAdmin(MultipartFile file) throws IOException, SQLIntegrityConstraintViolationException {
        try {
            EasyExcel.read(file.getInputStream(), AdminDetails.class, new AdminDataListener(adminManageService)).sheet().doRead();
            return new Result(null, Code.CREATED_OK);
        } catch (Exception e) {
            Throwable cause = e.getCause();
            if (cause instanceof SQLIntegrityConstraintViolationException) {
                throw new SQLIntegrityConstraintViolationException();
            } else {
                throw e;
            }
        }
    }

    @PostMapping("/upload/course")
    public Result UploadCourse(MultipartFile file) throws IOException, SQLIntegrityConstraintViolationException {
        try {
            EasyExcel.read(file.getInputStream(), Course.class, new CourseDataListener(courseService)).sheet().doRead();
            return new Result(null, Code.CREATED_OK);
        } catch (Exception e) {
            Throwable cause = e.getCause();
            if (cause instanceof SQLIntegrityConstraintViolationException) {
                throw new SQLIntegrityConstraintViolationException();
            } else {
                throw e;
            }
        }
    }

    @PostMapping("/upload/dormitory")
    public Result UploadDormitory(MultipartFile file) throws IOException, SQLIntegrityConstraintViolationException {
        try {
            EasyExcel.read(file.getInputStream(), Dormitory.class, new DormitoryDataListener(dormitoryService)).sheet().doRead();
            return new Result(null, Code.CREATED_OK);
        } catch (Exception e) {
            Throwable cause = e.getCause();
            if (cause instanceof SQLIntegrityConstraintViolationException) {
                throw new SQLIntegrityConstraintViolationException();
            } else {
                throw e;
            }
        }
    }

}
