package zhangshun.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import zhangshun.domain.AdminDetails;
import zhangshun.domain.StuDetails;
import zhangshun.domain.TeacherDetails;
import zhangshun.domain.User;

@Mapper
public interface DetailsDao {
    @Select("select * from studentmanage.stuDetails where username = #{username}")
    StuDetails selectStudentDetailsData(String username);

    @Select("select * from studentmanage.teacherDetails where username = #{username} ")
    TeacherDetails selectTeacherDetailsData(String username);

    @Select("select * from studentmanage.adminDetails where username = #{username} ")
    AdminDetails selectAdminDetailsData(String username);

    @Update("update ${table_name} set ${table_name}.profile = #{profile} where ${table_name}.username =#{username}")
    int updateProfile(@Param("table_name") String tablename, @Param("profile") String profile, @Param("username") String username);

    @Update("update studentmanage.stuDetails set sex = #{sex},phone = #{phone},address = #{address} where username = #{username}")
    int studetailsUpdate(StuDetails stuDetails);

    @Update("update studentmanage.teacherDetails set sex = #{sex},phone = #{phone},address = #{address} where username=#{username} ")
    int teadetailsUpdate(TeacherDetails teaDetails);

    @Update("update studentmanage.adminDetails,studentmanage.user set " +
            "adminDetails.username=#{username}," +
            "adminDetails.sex = #{sex}," +
            "adminDetails.phone = #{phone}," +
            "adminDetails.address = #{address}," +
            "adminDetails.name = #{name}," +
            "user.name = #{name}," +
            "user.username= #{username}" +
            " where adminDetails.username = #{oldusername} and user.username=#{oldusername} ")
    int admdetailsUpdate(AdminDetails admDetails);

    @Update("update studentmanage.user set password = (#{password}) where username =#{username} ")
    int updatePassword(User user);

    @Select("select password from studentmanage.user where username=#{username}")
    String selectPassword(String username);
}
