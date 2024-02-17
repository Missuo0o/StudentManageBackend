package zhangshun.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import zhangshun.domain.Sms;
import zhangshun.domain.User;

@Mapper
public interface SmsDao {

    @Update("update studentmanage.user set password=#{sms.password} where username =(select username from ${table_name} where ${table_name}.phone = #{sms.phone} and deleted = 0)")
    int updatePwd(@Param("table_name") String tablename, @Param("sms") Sms sms);

    @Select("select identity from studentmanage.user where username = #{username} and deleted = 0")
    User selectIdentity(String username);
}
