package zhangshun.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import zhangshun.domain.User;

@Mapper
public interface LoginDao {
    @Select("select username,name,identity from studentmanage.user where username = #{username} and password=#{password} and deleted = 0")
    User loginCheck(User user);

    @Select("select username,name,identity from studentmanage.user where username = #{username} and deleted = 0")
    User authCheck(String username);
}
