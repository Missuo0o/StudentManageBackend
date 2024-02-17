package zhangshun.service;

public interface DashBoardService {
    //查询未选课的学生数
    int SelectCourse();

    //查询未选宿舍的学生数
    int SelectDormitory();

    //查询未有辅导员的学生数
    int SelectStudentTeacher();

    //查询今日未打卡的学生数
    int SelectStudentHealthRecord();

    //查询今日未打卡的老师数
    int SelectTeacherHealthRecord();

    //查询未处理学生请假的条目数
    int SelectLeaveRecord();

    //查询未返校的学生数
    int SelectStudentStatus();

    //辅导员查询未选课的学生数
    int TeacherSelectCourse(String username);

    //辅导员查询未选宿舍的学生数
    int TeacherSelectDormitory(String username);

    //辅导员查询未处理学生请假的条目数
    int TeacherSelectLeaveRecord(String username);

}
