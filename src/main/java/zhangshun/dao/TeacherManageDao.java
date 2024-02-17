package zhangshun.dao;

import org.apache.ibatis.annotations.*;
import zhangshun.domain.StuDetails;
import zhangshun.domain.TeacherDetails;
import zhangshun.domain.TeacherStudent;

import java.util.List;

@Mapper
public interface TeacherManageDao {
    @Insert("insert into studentmanage.teacherDetails (username,name,sex,phone,address,profile) values" +
            "(#{username},#{name},#{sex},#{phone},#{address},#{profile})")
    int addTeacherDetails(TeacherDetails teacherDetails);

    @Insert("insert into studentmanage.user values (#{username},#{password},#{name},2,0)")
    int addTeacherUser(TeacherDetails teacherDetails);

    @Update("<script>update studentmanage.user set deleted=1 where username in " +
            "<foreach collection='ids' item='id' separator=',' open='(' close=')'>  #{id} </foreach></script> ")
    int deleteTeachers(@Param("ids") String[] ids);

    @Update("update studentmanage.user set deleted=1 " +
            "where username = #{username}")
    int deleteTeacher(String username);

    @Select("<script> select * from studentmanage.teacherDetails <where>" +
            "<if test=\"teacherDetails.username !=''\">and username like concat('%',#{teacherDetails.username},'%')</if> " +
            "<if test=\"teacherDetails.name!=''\">and name like  concat('%',#{teacherDetails.name},'%')</if>" +
            "<if test=\"teacherDetails.sex!=''\">and sex like  concat('%',#{teacherDetails.sex},'%')</if>" +
            "and  teacherDetails.username in (select username from user where deleted = 0)" +
            "</where>" +
            "order by teacherDetails.username " +
            "limit #{begin},#{size}" +
            "</script>")
    List<TeacherDetails> selectByPageAndCondition(@Param("begin") int begin, @Param("size") int size, @Param("teacherDetails") TeacherDetails teacherDetails);

    @Select("<script> select count(*) from studentmanage.teacherDetails <where>" +
            "<if test=\"username !=''\">and username like concat('%',#{username},'%')</if> " +
            "<if test=\"name!=''\">and name like  concat('%',#{name},'%')</if>" +
            "<if test=\"sex!=''\">and sex like  concat('%',#{sex},'%')</if>" +
            "and  username in (select username from user where deleted = 0)" +
            "</where>" +
            "</script>")
    int selectTotalCountCondition(TeacherDetails teacherDetails);

    @Select("select * from studentmanage.teacherDetails where username=(select username from studentmanage.user where username=#{username} and deleted = 0)")
    TeacherDetails selectByUsername(String username);

    @Update("<script>update studentmanage.teacherDetails,studentmanage.user,studentmanage.stuDetails <set> " +
            ",teacherDetails.username=#{username}" +
            ",teacherDetails.sex = #{sex}" +
            ",teacherDetails.phone = #{phone}" +
            ",teacherDetails.address = #{address}" +
            ",teacherDetails.name = #{name}" +
            ",teacherDetails.profile=#{profile}" +
            ",user.name = #{name}" +
            ",user.username= #{username} " +
            ",stuDetails.teachername= #{name},<if test='password !=null'>user.password= #{password} </if></set>" +
            "where teacherDetails.username = #{oldusername} and user.username= #{oldusername} and user.deleted=0</script>")
    int updateByUsername(TeacherDetails teacherDetails);

    @Select("select username,name from studentmanage.stuDetails where teacherid = #{username} and username in (select username from studentmanage.user where deleted = 0)")
    List<StuDetails> selectStudent(String username);

    @Select("select username,name from studentmanage.stuDetails where teacherid is null and username in (select username from studentmanage.user where deleted = 0)")
    List<StuDetails> selectStudentNull();

    @Update("<script>update studentmanage.stuDetails set teachername=#{teachername},teacherid=#{teacherid} where username in (select username from studentmanage.user where deleted = 0) and username in <foreach collection='usernameNotNull' item='id' separator=',' open='(' close=')'>  #{id} </foreach> </script>")
    int updateStudentNotNull(TeacherStudent teacherStudent);

    @Update("<script>update studentmanage.stuDetails set teachername=null,teacherid=null where username in (select username from studentmanage.user where deleted = 0) and username in <foreach collection='usernameNull' item='id' separator=',' open='(' close=')'>  #{id}</foreach> </script>")
    int updateStudentNull(TeacherStudent teacherStudent);

    @Insert("<script>insert into studentmanage.teacherDetails values <foreach collection='list' item='item' separator=','>  (#{item.username},#{item.name},#{item.sex},#{item.phone},#{item.address},#{item.profile}) </foreach> </script>")
    int addExcelTeacherDetails(List<TeacherDetails> teacherDetails);

    @Insert("<script>insert into studentmanage.user values <foreach collection='list' item='item' separator=','> (#{item.username},#{item.password},#{item.name},2,0) </foreach></script>")
    int addExcelTeacher(List<TeacherDetails> teacherDetails);
}
