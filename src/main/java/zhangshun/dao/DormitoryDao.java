package zhangshun.dao;

import org.apache.ibatis.annotations.*;
import zhangshun.domain.Dormitory;
import zhangshun.domain.DormitoryStudent;
import zhangshun.domain.StuDetails;

import java.util.List;

@Mapper
public interface DormitoryDao {

    @Select("select * from studentmanage.dormitory where deleted = 0")
    List<Dormitory> selectAll();

    @Select("select count(*) from studentmanage.stuDetails where username=#{username} and dormitoryid is not null")
    int selectDuplicate(String username);

    @Update("update studentmanage.stuDetails set dormitoryid=#{id} where username=#{username} and paied = 0")
    int insertDormitory_Student(@Param("id") int id, @Param("username") String username);

    @Update("update studentmanage.dormitory set current=current+1,version=version+1 where deleted = 0 and current < limited and id=#{id} and version=(select version from(select version from studentmanage.dormitory where id=#{id})as a)")
    int updateAddDormitory(int id);

    @Update("update studentmanage.dormitory set current=current-1,version=version+1 where deleted = 0 and current <= limited and id=#{id} and exists(select paied from studentmanage.stuDetails where paied = 0 and username = #{username}) and version=(select version from(select version from studentmanage.dormitory where id=#{id})as a)")
    int updateDeleteDormitory(@Param("id") int id, @Param("username") String username);

    @Update("update studentmanage.stuDetails set dormitoryid = null where dormitoryid=#{id} and username=#{username} and paied = 0")
    int deleteCourse_Dormitory(@Param("id") int id, @Param("username") String username);

    @Select("select * from studentmanage.dormitory where deleted = 0 and id=(select dormitoryid from studentmanage.stuDetails where username=#{username} and paied = 1)")
    Dormitory myDormitory(String username);

    @Select("select * from studentmanage.dormitory where deleted = 0 and id=(select dormitoryid from studentmanage.stuDetails where username=#{username})")
    Dormitory getStuDormitory(String username);

    @Update("update studentmanage.stuDetails set paied = 1 where username=#{username}")
    int updateStatus(String username);

    @Select("<script> select count(*) from studentmanage.dormitory <where>" +
            "<if test=\"building !=''\">and building like concat('%',#{building},'%')</if> " +
            "<if test=\"floor!=''\">and floor like concat('%',#{floor},'%')</if>" +
            "<if test=\"room!=''\">and room like concat('%',#{room},'%')</if>" +
            "and  deleted = 0 " +
            "</where>" +
            "</script>")
    int selectAllTotalCountCondition(Dormitory dormitory);

    @Select("<script> select * from studentmanage.dormitory <where>" +
            "<if test=\"dormitory.building !=''\">and building like concat('%',#{dormitory.building},'%')</if> " +
            "<if test=\"dormitory.floor!=''\">and floor like concat('%',#{dormitory.floor},'%')</if>" +
            "<if test=\"dormitory.room!=''\">and room like concat('%',#{dormitory.room},'%')</if>" +
            "and deleted = 0" +
            "</where>" +
            "order by dormitory.id desc " +
            "limit #{begin},#{size}" +
            "</script>")
    List<Dormitory> selectAllByPageAndCondition(@Param("begin") int begin, @Param("size") int size, @Param("dormitory") Dormitory dormitory);


    @Select("<script> select count(*) from studentmanage.dormitory <where>" +
            "<if test=\"dormitory.building !=''\">and building like concat('%',#{dormitory.building},'%')</if> " +
            "<if test=\"dormitory.floor!=''\">and floor like concat('%',#{dormitory.floor},'%')</if>" +
            "<if test=\"dormitory.room!=''\">and room like concat('%',#{dormitory.room},'%')</if>" +
            "and  deleted = 0 and id=(select dormitoryid from studentmanage.stuDetails where username=#{username}) " +
            "</where>" +
            "</script>")
    int selectAlreadyTotalCountCondition(@Param("dormitory") Dormitory dormitory, @Param("username") String username);

    @Select("<script> select * from studentmanage.dormitory <where>" +
            "<if test=\"dormitory.building !=''\">and building like concat('%',#{dormitory.building},'%')</if> " +
            "<if test=\"dormitory.floor!=''\">and floor like concat('%',#{dormitory.floor},'%')</if>" +
            "<if test=\"dormitory.room!=''\">and room like concat('%',#{dormitory.room},'%')</if>" +
            "and deleted = 0 and id=(select dormitoryid from studentmanage.stuDetails where username=#{username}) " +
            "</where>" +
            "order by dormitory.id desc " +
            "limit #{begin},#{size}" +
            "</script>")
    List<Dormitory> selectAlreadyByPageAndCondition(@Param("begin") int begin, @Param("size") int size, @Param("dormitory") Dormitory dormitory, @Param("username") String username);


    @Select("<script> select username,name,dormitoryid,paied from studentmanage.stuDetails <where>" +
            "<if test=\"stuDetails.username !=''\">and username like concat('%',#{stuDetails.username},'%')</if> " +
            "<if test=\"stuDetails.name!=''\">and name like  concat('%',#{stuDetails.name},'%')</if>" +
            "and stuDetails.username in (select username from user where deleted = 0)" +
            "</where>" +
            "order by stuDetails.username " +
            "limit #{begin},#{size}" +
            "</script>")
    List<StuDetails> selectPayByPageAndCondition(@Param("begin") int begin, @Param("size") int size, @Param("stuDetails") StuDetails stuDetails);

    @Select("<script> select count(*) from studentmanage.stuDetails <where>" +
            "<if test=\"username !=''\">and username like concat('%',#{username},'%')</if> " +
            "<if test=\"name!=''\">and name like  concat('%',#{name},'%')</if>" +
            "and username in (select username from user where deleted = 0)" +
            "</where>" +
            "</script>")
    int selectPayTotalCountCondition(StuDetails stuDetails);

    @Select("<script> select username,name,dormitoryid,paied from studentmanage.stuDetails <where>" +
            "<if test=\"stuDetails.username !=''\">and username like concat('%',#{stuDetails.username},'%')</if> " +
            "<if test=\"stuDetails.name!=''\">and name like  concat('%',#{stuDetails.name},'%')</if>" +
            "and stuDetails.paied = 0 and stuDetails.username in (select username from user where deleted = 0)" +
            "</where>" +
            "order by stuDetails.username " +
            "limit #{begin},#{size}" +
            "</script>")
    List<StuDetails> selectNotPayByPageAndCondition(@Param("begin") int begin, @Param("size") int size, @Param("stuDetails") StuDetails stuDetails);

    @Select("<script> select count(*) from studentmanage.stuDetails <where>" +
            "<if test=\"username !=''\">and username like concat('%',#{username},'%')</if> " +
            "<if test=\"name!=''\">and name like  concat('%',#{name},'%')</if>" +
            "and stuDetails.paied = 0 and username in (select username from user where deleted = 0)" +
            "</where>" +
            "</script>")
    int selectNotPayTotalCountCondition(StuDetails stuDetails);

    @Update("update studentmanage.stuDetails set paied=#{id} where username=(select username from studentmanage.user where username=#{username} and deleted = 0) and dormitoryid is not null")
    int updatePay(@Param("username") String username, @Param("id") int id);


    @Insert("<script>insert into studentmanage.dormitory values <foreach collection='list' item='item' separator=','>  (null,#{item.building},#{item.floor},#{item.room},#{item.price},#{item.limited},0,1,0) </foreach> </script>")
    int addExcelDormitory(List<Dormitory> dormitoryList);

    @Update("update studentmanage.dormitory set deleted=1 where id=#{id}")
    int deleteById(int id);

    @Update("<script>update studentmanage.dormitory set deleted=1 where id in " +
            "<foreach collection='ids' item='id' separator=',' open='(' close=')'>  #{id} </foreach></script> ")
    int deleteDormitory(@Param("ids") int[] ids);

    @Insert("insert into studentmanage.dormitory values (null,#{building},#{floor},#{room},#{price},#{limited},0,1,0) ")
    int addDormitory(Dormitory dormitory);

    @Update("update studentmanage.dormitory set building=#{building},floor=#{floor},room=#{room},price=#{price},limited=#{limited} where id=#{id}")
    int updateDormitory(Dormitory dormitory);

    @Select("select * from studentmanage.dormitory where deleted = 0 and id=#{id}")
    Dormitory selectById(int id);

    @Select("select username,name from studentmanage.stuDetails where dormitoryid = #{id} and username in (select username from studentmanage.user where deleted = 0) ")
    List<StuDetails> selectStudentNotnull(int id);

    @Select("select username,name from studentmanage.stuDetails where dormitoryid is null and username in (select username from studentmanage.user where deleted = 0) ")
    List<StuDetails> selectStudentNull();

    @Update("<script>update studentmanage.stuDetails set dormitoryid=#{dormitoryid} where username in (select username from studentmanage.user where deleted = 0) and username in <foreach collection='usernameNotNull' item='id' separator=',' open='(' close=')'>  #{id} </foreach> </script>")
    int updateStudentNotNull(DormitoryStudent dormitoryStudent);

    @Update("<script>update studentmanage.stuDetails set dormitoryid=null where username in (select username from studentmanage.user where deleted = 0) and username in <foreach collection='usernameNull' item='id' separator=',' open='(' close=')'>  #{id}</foreach> </script>")
    int updateStudentNull(DormitoryStudent dormitoryStudent);

    @Update("update studentmanage.dormitory set current=(select count(*) from studentmanage.stuDetails where dormitoryid=#{dormitoryid} and username in (select username from studentmanage.user where deleted = 0)) where id=#{dormitoryid}")
    int updateDormitoryCount(DormitoryStudent dormitoryStudent);
}
