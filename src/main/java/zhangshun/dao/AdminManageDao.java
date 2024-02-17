package zhangshun.dao;

import org.apache.ibatis.annotations.*;
import zhangshun.domain.AdminDetails;

import java.util.List;

@Mapper
public interface AdminManageDao {
    @Insert("insert into studentmanage.adminDetails (username,name,sex,phone,address,profile) values" +
            "(#{username},#{name},#{sex},#{phone},#{address},#{profile})")
    int addAdminDetails(AdminDetails adminDetails);

    @Insert("insert into studentmanage.user values (#{username},#{password},#{name},3,0)")
    int addAdminUser(AdminDetails adminDetails);

    @Update("<script>update studentmanage.user set deleted=1 where username in " +
            "<foreach collection='ids' item='id' separator=',' open='(' close=')'>  #{id} </foreach></script> ")
    int deleteAdmins(@Param("ids") String[] ids);

    @Update("update studentmanage.user set deleted=1 " +
            "where username = #{username}")
    int deleteAdmin(String username);

    @Select("<script> select * from studentmanage.adminDetails <where> " +
            "<if test=\"adminDetails.username !=''\">and username like concat('%',#{adminDetails.username},'%')</if> " +
            "<if test=\"adminDetails.name!=''\">and name like  concat('%',#{adminDetails.name},'%')</if>" +
            "<if test=\"adminDetails.sex!=''\">and sex like  concat('%',#{adminDetails.sex},'%')</if>" +
            "and  adminDetails.username in (select username from user where deleted = 0)" +
            "</where>" +
            "order by adminDetails.username " +
            "limit #{begin},#{size}" +
            "</script>")
    List<AdminDetails> selectByPageAndCondition(@Param("begin") int begin, @Param("size") int size, @Param("adminDetails") AdminDetails adminDetails);

    @Select("<script> select count(*) from studentmanage.adminDetails <where>" +
            "<if test=\"username !=''\">and username like concat('%',#{username},'%')</if> " +
            "<if test=\"name!=''\">and name like  concat('%',#{name},'%')</if>" +
            "<if test=\"sex!=''\">and sex like  concat('%',#{sex},'%')</if>" +
            "and  username in (select username from user where deleted = 0)" +
            "</where>" +
            "</script>")
    int selectTotalCountCondition(AdminDetails adminDetails);

    @Select("select * from studentmanage.adminDetails where username=(select username from studentmanage.user where username=#{username} and deleted = 0)")
    AdminDetails selectByUsername(String username);

    @Update("<script>update studentmanage.adminDetails,studentmanage.user <set> " +
            ",adminDetails.username=#{username}" +
            ",adminDetails.sex = #{sex}" +
            ",adminDetails.phone = #{phone}" +
            ",adminDetails.address = #{address}" +
            ",adminDetails.name = #{name}" +
            ",adminDetails.profile=#{profile}" +
            ",user.name = #{name}" +
            ",user.username= #{username},<if test='password !=null'>user.password= #{password} </if></set> " +
            "where adminDetails.username = #{oldusername} and user.username= #{oldusername} and user.deleted=0</script>")
    int updateByUsername(AdminDetails adminDetails);

    @Insert("<script>insert into studentmanage.adminDetails values <foreach collection='list' item='item' separator=','>  (#{item.username},#{item.name},#{item.sex},#{item.phone},#{item.address},#{item.profile}) </foreach> </script>")
    int addExcelAdminDetails(List<AdminDetails> adminDetails);

    @Insert("<script>insert into studentmanage.user values <foreach collection='list' item='item' separator=','> (#{item.username},#{item.password},#{item.name},3,0) </foreach></script>")
    int addExcelAdmin(List<AdminDetails> adminDetails);
}
