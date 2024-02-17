package zhangshun.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import zhangshun.domain.StuDetails;

@Mapper
public interface StudentRegisterDao {
    @Update("update studentmanage.stuDetails set status=#{status},schooladdress=#{schooladdress} where username=#{username}")
    int updateStudentStatus(StuDetails stuDetails);

    @Select("select status from studentmanage.stuDetails where username=#{username}")
    String selectStudentStatus(String username);
}
