package zhangshun.dao;

import org.apache.ibatis.annotations.*;
import zhangshun.domain.StuDetails;

import java.util.List;

@Mapper
public interface StudentManageDao {
    @Select("select * from studentmanage.stuDetails where username in (select username from studentmanage.user where deleted = 0)")
    List<StuDetails> selectAll();

    @Insert("insert into studentmanage.stuDetails (username,name,sex,college,major,classname,phone,address,status,profile,schooladdress)values" +
            "(#{username},#{name},#{sex},#{college},#{major},#{classname},#{phone},#{address},#{status},#{profile},#{schooladdress})")
    int addStudentDetails(StuDetails stuDetails);

    @Insert("insert into studentmanage.user values (#{username},#{password},#{name},1,0)")
    int addStudentUser(StuDetails stuDetails);

    @Update("<script>update studentmanage.user set deleted=1 where username in " +
            "<foreach collection='ids' item='id' separator=',' open='(' close=')'>  #{id} </foreach> " +
            "</script>")
    int deleteStudents(@Param("ids") String[] ids);


    @Update("update studentmanage.user set deleted=1 " +
            "where username = #{username}")
    int deleteStudent(String username);

    @Select("select * from studentmanage.stuDetails where username in (select username from studentmanage.user where deleted = 0) limit #{begin},#{size}")
    List<StuDetails> selectByPage(@Param("begin") int begin, @Param("size") int size);

    @Select("select count(*) from studentmanage.stuDetails where username in (select username from studentmanage.user where deleted = 0)")
    int selectTotalCount();

    @Select("<script> select * from studentmanage.stuDetails <where>" +
            "<if test=\"stuDetails.username !=''\">and username like concat('%',#{stuDetails.username},'%')</if> " +
            "<if test=\"stuDetails.name!=''\">and name like  concat('%',#{stuDetails.name},'%')</if>" +
            "<if test=\"stuDetails.sex!=''\">and sex like  concat('%',#{stuDetails.sex},'%')</if>" +
            "<if test=\"stuDetails.status!=''\">and status like  concat('%',#{stuDetails.status},'%')</if>" +
            "and stuDetails.username in (select username from user where deleted = 0)" +
            "</where>" +
            "order by stuDetails.username " +
            "limit #{begin},#{size}" +
            "</script>")
    List<StuDetails> selectByPageAndCondition(@Param("begin") int begin, @Param("size") int size, @Param("stuDetails") StuDetails stuDetails);

    @Select("<script> select count(*) from studentmanage.stuDetails <where>" +
            "<if test=\"username !=''\">and username like concat('%',#{username},'%')</if> " +
            "<if test=\"name!=''\">and name like  concat('%',#{name},'%')</if>" +
            "<if test=\"sex!=''\">and sex like  concat('%',#{sex},'%')</if>" +
            "<if test=\"status!=''\">and status like  concat('%',#{status},'%')</if>" +
            "and username in (select username from user where deleted = 0)" +
            "</where>" +
            "</script>")
    int selectTotalCountCondition(StuDetails stuDetails);

    @Select("select * from studentmanage.stuDetails where username =(select username from studentmanage.user where username=#{username} and deleted = 0) ")
    StuDetails selectByUsername(String username);

    @Update("<script>update studentmanage.stuDetails,studentmanage.user <set> " +
            ",stuDetails.username=#{username}" +
            ",stuDetails.sex = #{sex}" +
            ",stuDetails.phone = #{phone}" +
            ",stuDetails.address = #{address}" +
            ",stuDetails.name = #{name}" +
            ",stuDetails.college=#{college}" +
            ",stuDetails.major=#{major}" +
            ",stuDetails.classname=#{classname}" +
            ",stuDetails.profile=#{profile}" +
            ",stuDetails.status=#{status}" +
            ",stuDetails.schooladdress=#{schooladdress}" +
            ",user.name = #{name}" +
            ",user.username= #{username},<if test='password != null'>user.password= #{password} </if></set>" +
            "where stuDetails.username =#{oldusername} and user.username= #{oldusername} and user.deleted=0</script>")
    int updateByUsername(StuDetails stuDetails);

    @Insert("<script>insert into studentmanage.stuDetails values <foreach collection='list' item='item' separator=','>  (#{item.username},#{item.name},#{item.sex},#{item.college},#{item.major},#{item.classname},#{item.phone},#{item.address},#{item.schooladdress},#{item.teacherid},#{item.teachername},#{item.status},#{item.profile},null,0) </foreach> </script>")
    int addExcelStudentDetails(List<StuDetails> stuDetails);

    @Insert("<script>insert into studentmanage.user values <foreach collection='list' item='item' separator=','> (#{item.username},#{item.password},#{item.name},1,0) </foreach></script>")
    int addExcelStudent(List<StuDetails> stuDetails);

    @Update("update studentmanage.stuDetails set dormitoryid = null where username=#{username}")
    void deleteDormitoryid(String username);

    @Delete("delete from studentmanage.course_student where studentid=#{username}")
    void deleteCourseid(String username);

    @Update("<script>update studentmanage.stuDetails set dormitoryid = null where username in " +
            "<foreach collection='ids' item='id' separator=',' open='(' close=')'>  #{id} </foreach> " +
            "</script>")
    void deleteDormitoryids(@Param("ids") String[] ids);

    @Delete("<script>delete from studentmanage.course_student where studentid in " +
            "<foreach collection='ids' item='id' separator=',' open='(' close=')'>  #{id} </foreach> " +
            "</script>")
    void deleteCourseids(@Param("ids") String[] ids);

    @Select("select id from studentmanage.dormitory")
    List<Integer> selectDormitoryids();

    @Select("select id from studentmanage.course")
    List<Integer> selectCourseids();

    @Update("<script>update studentmanage.dormitory SET current = CASE id <foreach collection='list' item='item'>WHEN #{item} THEN (select count(*) from studentmanage.stuDetails where dormitoryid=#{item} and username in (select username from studentmanage.user where deleted = 0)) </foreach> END</script>")
    void updateDormitoryCount(List<Integer> list);

    @Update("<script>update studentmanage.course SET current = CASE id <foreach collection='list' item='item'>WHEN #{item} THEN (select count(*) from studentmanage.course_student where courseid=#{item} and studentid in (select username from studentmanage.user where deleted = 0)) </foreach> END</script>")
    void updateCourseCount(List<Integer> list);
}
