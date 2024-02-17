package zhangshun.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DashBoardDao {
    @Select("SELECT COUNT(*) FROM studentmanage.stuDetails WHERE username NOT IN(SELECT studentid FROM studentmanage.course_student) and username in (select username from studentmanage.user where deleted = 0)")
    int selectCourse();

    @Select("select count(*) from studentmanage.stuDetails where dormitoryid is null and username in (select username from studentmanage.user where deleted = 0)")
    int selectDormitory();

    @Select("select count(*) from studentmanage.stuDetails where teacherid is null and username in (select username from studentmanage.user where deleted = 0)")
    int selectStudentTeacher();

    @Select("select COUNT(*) from studentmanage.stuDetails where username not in(select username from studentmanage.healthRecord where  UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %00:%00:%00'))  <= UNIX_TIMESTAMP(createtime) AND UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %00:%00:%00')) + 86400 >= UNIX_TIMESTAMP(createtime) and deleted=0)")
    int selectStudentHealthRecord();

    @Select("select COUNT(*) from studentmanage.teacherDetails where username not in(select username from studentmanage.healthRecord where  UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %00:%00:%00'))  <= UNIX_TIMESTAMP(createtime) AND UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %00:%00:%00')) + 86400 >= UNIX_TIMESTAMP(createtime) and deleted=0)")
    int selectTeacherHealthRecord();

    @Select("select count(*) from studentmanage.leaveRecord where status = 0 and deleted = 0")
    int selectLeaveRecord();

    @Select("select count(*) from studentmanage.stuDetails where status='否' and username in (select username from studentmanage.user where deleted = 0)")
    int selectStudentStatus();

    @Select("SELECT COUNT(*) FROM studentmanage.stuDetails WHERE username NOT IN(SELECT studentid FROM studentmanage.course_student) and username in (select username from studentmanage.user where deleted = 0) and username in(select username from studentmanage.stuDetails where teacherid=#{username})")
    int teacherSelectCourse(String username);

    @Select("select count(*) from studentmanage.stuDetails where dormitoryid is null and username in (select username from studentmanage.user where deleted = 0) and username in(select username from studentmanage.stuDetails where teacherid=#{username}) ")
    int teacherSelectDormitory(String username);

    @Select("select count(*) from studentmanage.leaveRecord where status = 0 and deleted = 0 and username in(select username from studentmanage.stuDetails where teacherid=#{username})")
    int teacherSelectLeaveRecord(String username);
}
