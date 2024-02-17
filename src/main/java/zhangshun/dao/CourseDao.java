package zhangshun.dao;

import org.apache.ibatis.annotations.*;
import zhangshun.domain.Course;
import zhangshun.domain.CourseStudent;
import zhangshun.domain.Course_Student;
import zhangshun.domain.StuDetails;

import java.util.List;

@Mapper
public interface CourseDao {

    @Select("select * from studentmanage.course_student where studentid=#{studentid} and courseid=#{courseid}")
    Course_Student selectDuplicate(Course_Student course_student);

    @Select("select * from studentmanage.course where status = 1 and deleted = 0 and id in(select courseid from studentmanage.course_student where studentid=#{studentid})")
    List<Course> selectTime(Course_Student course_student);

    @Select("select * from studentmanage.course where status = 1 and deleted = 0 and id=#{id}")
    Course selectById(int id);

    @Select("select count(*) from studentmanage.course_student where studentid=#{studentid}")
    int selectCountById(Course_Student course_student);

    @Update("update studentmanage.course set current=current+1,version=version+1 where status = 1 and deleted = 0 and current < `limit` and id=#{id} and version=(select version from(select version from studentmanage.course where id=#{id})as a)")
    int updateAddCourse(int id);

    @Insert("insert into studentmanage.course_student values (#{studentid},#{courseid})")
    int insertCourse_Student(Course_Student course_student);

    @Update("update studentmanage.course set current=current-1,version=version+1 where status = 1 and deleted = 0 and current <= `limit` and id=#{id} and version=(select version from(select version from studentmanage.course where id=#{id})as a)")
    int updateDeleteCourse(int id);

    @Delete("delete from studentmanage.course_student where studentid=#{studentid} and courseid=#{courseid}")
    int deleteCourse_Student(Course_Student course_student);

    @Select("<script> select count(*) from studentmanage.course <where>" +
            "<if test=\"name !=''\">and name like concat('%',#{name},'%')</if> " +
            "<if test=\"week!=''\">and week like concat('%',#{week},'%')</if>" +
            "and  deleted = 0 and status = 1" +
            "</where>" +
            "</script>")
    int selectAllTotalCountCondition(Course course);

    @Select("<script> select * from studentmanage.course <where>" +
            "<if test=\"course.name !=''\">and name like concat('%',#{course.name},'%')</if> " +
            "<if test=\"course.week!=''\">and week like  concat('%',#{course.week},'%')</if>" +
            "and deleted = 0 and status = 1" +
            "</where>" +
            "order by course.id desc " +
            "limit #{begin},#{size}" +
            "</script>")
    List<Course> selectAllByPageAndCondition(@Param("begin") int begin, @Param("size") int size, @Param("course") Course course);

    @Select("<script> select count(*) from studentmanage.course <where>" +
            "<if test=\"course.name !=''\">and name like concat('%',#{course.name},'%')</if> " +
            "<if test=\"course.week!=''\">and week like concat('%',#{course.week},'%')</if>" +
            "and  deleted = 0 and status = 1 " +
            "and id in (select courseid from studentmanage.course_student where studentid=#{username})" +
            "</where>" +
            "</script>")
    int selectAlreadyTotalCountCondition(@Param("course") Course course, @Param("username") String username);

    @Select("<script> select * from studentmanage.course <where>" +
            "<if test=\"course.name !=''\">and name like concat('%',#{course.name},'%')</if> " +
            "<if test=\"course.week!=''\">and week like  concat('%',#{course.week},'%')</if>" +
            "and deleted = 0 and status = 1 " +
            "and id in (select courseid from studentmanage.course_student where studentid=#{username})" +
            "</where>" +
            "order by course.id desc " +
            "limit #{begin},#{size}" +
            "</script>")
    List<Course> selectAlreadyByPageAndCondition(@Param("begin") int begin, @Param("size") int size, @Param("course") Course course, @Param("username") String username);


    @Update("<script>update studentmanage.course set deleted=1 where id in " +
            "<foreach collection='ids' item='id' separator=',' open='(' close=')'>  #{id} </foreach></script> ")
    int deleteCourses(@Param("ids") int[] ids);

    @Update("update studentmanage.course set deleted=1 where id=#{id}")
    int deleteById(int id);

    @Select("<script> select count(*) from studentmanage.course <where>" +
            "<if test=\"name !=''\">and name like concat('%',#{name},'%')</if> " +
            "<if test=\"week!=''\">and week like concat('%',#{week},'%')</if>" +
            "and  deleted = 0 " +
            "</where>" +
            "</script>")
    int selectAdminAllTotalCountCondition(Course course);

    @Select("<script> select * from studentmanage.course <where>" +
            "<if test=\"course.name !=''\">and name like concat('%',#{course.name},'%')</if> " +
            "<if test=\"course.week!=''\">and week like  concat('%',#{course.week},'%')</if>" +
            "and deleted = 0 " +
            "</where>" +
            "order by course.id desc " +
            "limit #{begin},#{size}" +
            "</script>")
    List<Course> selectAdminAllByPageAndCondition(@Param("begin") int begin, @Param("size") int size, @Param("course") Course course);

    @Update("update studentmanage.course set name=#{name},start=#{start},end=#{end},week=#{week},`limit`=#{limit} where id=#{id}")
    int updateCourse(Course course);

    @Select("select * from studentmanage.course where deleted = 0")
    List<Course> selectAll();

    @Insert("insert into studentmanage.course values (null,#{name},#{start},#{end},#{week},#{limit},0,1,0,0) ")
    int addCourse(Course course);

    @Select("select * from studentmanage.course where id = #{id} and deleted = 0")
    Course selectAllById(int id);

    @Update("update studentmanage.course set status = #{status}")
    int updateStatus(int status);

    @Insert("<script>insert into studentmanage.course values <foreach collection='list' item='item' separator=','>  (null,#{item.name},#{item.start},#{item.end},#{item.week},#{item.limit},0,1,0,0) </foreach> </script>")
    int addExcelCourse(List<Course> courseList);


    //查询该课程名下的学生
    @Select("select username,name from studentmanage.stuDetails where username in(select studentid from studentmanage.course_student where courseid=#{id}) and username in (select username from studentmanage.user where deleted = 0) ")
    List<StuDetails> selectStudentCourse(int id);

    @Select("select username,name from studentmanage.stuDetails where username not in (select studentid from studentmanage.course_student where courseid=#{id}) and username in (select username from studentmanage.user where deleted = 0) ")
    List<StuDetails> selectStudentNull(int id);

    @Update("<script>delete from studentmanage.course_student where courseid=#{courseid} and studentid in <foreach collection='usernameNull' item='id' separator=',' open='(' close=')'>  #{id}</foreach></script>")
    int updateStudentNull(CourseStudent courseStudent);

    @Insert("<script>insert into studentmanage.course_student values <foreach collection='usernameNotNull' item='item' separator=','> (#{item},#{courseid}) </foreach> </script>")
    int updateStudentNotNull(CourseStudent courseStudent);

    @Update("update studentmanage.course set current=(select count(*) from studentmanage.course_student where courseid=#{courseid} and studentid in (select username from studentmanage.user where deleted = 0)) where id=#{courseid}")
    int updateCourseCount(CourseStudent courseStudent);

    @Select("<script>select studentid from studentmanage.course_student where courseid=#{courseid} and studentid in <foreach collection='usernameNotNull' item='item' separator=',' open='(' close=')'> #{item} </foreach> </script>")
    List<String> selectDupCourseStudent(CourseStudent courseStudent);
}
