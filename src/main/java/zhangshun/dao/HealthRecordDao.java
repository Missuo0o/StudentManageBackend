package zhangshun.dao;

import org.apache.ibatis.annotations.*;
import zhangshun.domain.HealthRecord;
import zhangshun.domain.StuDetails;
import zhangshun.domain.TeacherDetails;

import java.util.List;

@Mapper
public interface HealthRecordDao {
    @Insert("insert into studentmanage.healthRecord values (null,#{username},#{name},#{phone},#{symptom},#{inschool},#{address},#{createtime},#{status},#{deleted})")
    int addHealthRecord(HealthRecord healthRecord);

    @Select("select * from studentmanage.healthRecord where  UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %00:%00:%00'))  <= UNIX_TIMESTAMP(createtime) AND UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %00:%00:%00')) + 86400 >= UNIX_TIMESTAMP(createtime) and username=#{username} and deleted=0")
    List<HealthRecord> selectValidByUsername(String username);

    @Select("select count(*) from studentmanage.healthRecord where username=#{username} and deleted=0")
    int selectCountHealthRecord(String username);

    @Select("select * from studentmanage.healthRecord where username=#{healthRecord.username} and deleted = 0 and status=#{healthRecord.status} order by id desc limit #{begin},#{size} ")
    List<HealthRecord> selectByPageAndUsername(@Param("begin") int begin, @Param("size") int size, @Param("healthRecord") HealthRecord healthRecord);

    @Select("select count(*) from studentmanage.healthRecord where username=#{username} and deleted=0")
    int selectTotalCountByUsername(HealthRecord healthRecord);

    @Select("<script> select count(*) from studentmanage.healthRecord <where>" +
            "<if test=\"healthRecord.username !=''\">and username like concat('%',#{healthRecord.username},'%')</if> " +
            "<if test=\"healthRecord.name!=''\">and name like  concat('%',#{healthRecord.name},'%')</if>" +
            "<if test=\"healthRecord.inschool!=''\">and inschool like  concat('%',#{healthRecord.inschool},'%')</if>" +
            "and  healthRecord.deleted = 0 and  healthRecord.username in (select username from stuDetails where teacherid=#{username})" +
            "</where>" +
            "</script>")
    int selectTotalCountCondition(@Param("healthRecord") HealthRecord healthRecord, @Param("username") String username);

    @Select("<script> select * from studentmanage.healthRecord <where>" +
            "<if test=\"healthRecord.username !=''\">and username like concat('%',#{healthRecord.username},'%')</if> " +
            "<if test=\"healthRecord.name!=''\">and name like  concat('%',#{healthRecord.name},'%')</if>" +
            "<if test=\"healthRecord.inschool!=''\">and inschool like  concat('%',#{healthRecord.inschool},'%')</if>" +
            "and healthRecord.deleted = 0 and  healthRecord.username in (select username from stuDetails where teacherid=#{username})" +
            "</where>" +
            "order by healthRecord.id desc " +
            "limit #{begin},#{size}" +
            "</script>")
    List<HealthRecord> selectByPageAndCondition(@Param("begin") int begin, @Param("size") int size, @Param("healthRecord") HealthRecord healthRecord, @Param("username") String username);

    @Select("<script> select count(*) from studentmanage.healthRecord <where>" +
            "<if test=\"healthRecord.username !=''\">and username like concat('%',#{healthRecord.username},'%')</if> " +
            "<if test=\"healthRecord.name!=''\">and name like  concat('%',#{healthRecord.name},'%')</if>" +
            "<if test=\"healthRecord.inschool!=''\">and inschool like  concat('%',#{healthRecord.inschool},'%')</if>" +
            "and  healthRecord.deleted = 0 and  healthRecord.username in (select username from stuDetails where teacherid=#{username}) and UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %00:%00:%00'))  &lt;= UNIX_TIMESTAMP(healthRecord.createtime) AND UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %00:%00:%00')) + 86400 &gt;= UNIX_TIMESTAMP(healthRecord.createtime)" +
            "</where>" +
            "</script>")
    int selectValidTotalCountCondition(@Param("healthRecord") HealthRecord healthRecord, @Param("username") String username);

    @Select("<script> select * from studentmanage.healthRecord <where>" +
            "<if test=\"healthRecord.username !=''\">and username like concat('%',#{healthRecord.username},'%')</if> " +
            "<if test=\"healthRecord.name!=''\">and name like  concat('%',#{healthRecord.name},'%')</if>" +
            "<if test=\"healthRecord.inschool!=''\">and inschool like  concat('%',#{healthRecord.inschool},'%')</if>" +
            "and healthRecord.deleted = 0 and  healthRecord.username in (select username from stuDetails where teacherid=#{username}) and UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %00:%00:%00'))  &lt;= UNIX_TIMESTAMP(healthRecord.createtime) AND UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %00:%00:%00')) + 86400 &gt;= UNIX_TIMESTAMP(healthRecord.createtime)" +
            "</where>" +
            "order by healthRecord.id desc " +
            "limit #{begin},#{size}" +
            "</script>")
    List<HealthRecord> selectValidByPageAndCondition(@Param("begin") int begin, @Param("size") int size, @Param("healthRecord") HealthRecord healthRecord, @Param("username") String username);

    @Select("select * from studentmanage.healthRecord where healthRecord.deleted = 0 and  healthRecord.username in (select username from stuDetails where teacherid=#{username}) and UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %00:%00:%00'))  <= UNIX_TIMESTAMP(healthRecord.createtime) AND UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %00:%00:%00')) + 86400 >= UNIX_TIMESTAMP(healthRecord.createtime)")
    List<HealthRecord> selectAllValid(@Param("healthRecord") HealthRecord healthRecord, @Param("username") String username);

    @Select("<script> select count(*) from studentmanage.stuDetails <where>" +
            "<if test=\"healthRecord.username !=''\">and username like concat('%',#{healthRecord.username},'%')</if> " +
            "<if test=\"healthRecord.name!=''\">and name like  concat('%',#{healthRecord.name},'%')</if>" +
            "and teacherid=#{username} and username not in <foreach collection='list' item='item' separator=',' open='(' close=')'>  #{item.username} </foreach> " +
            "</where>" +
            "</script>")
    int selectNotValidTotalCountCondition(@Param("healthRecord") HealthRecord healthRecord, @Param("username") String username, @Param("list") List<HealthRecord> healthRecords);

    @Select("<script> select username,name,phone from studentmanage.stuDetails <where>" +
            "<if test=\"healthRecord.username !=''\">and username like concat('%',#{healthRecord.username},'%')</if> " +
            "<if test=\"healthRecord.name!=''\">and name like  concat('%',#{healthRecord.name},'%')</if>" +
            "and teacherid=#{username} and username not in <foreach collection='list' item='item' separator=',' open='(' close=')'>  #{item.username} </foreach> " +
            "</where>" +
            "limit #{begin},#{size}" +
            "</script>")
    List<HealthRecord> selectNotValidByPageAndCondition(@Param("begin") int begin, @Param("size") int size, @Param("healthRecord") HealthRecord healthRecord, @Param("username") String username, @Param("list") List<HealthRecord> healthRecords);

    @Select("<script> select count(*) from studentmanage.stuDetails <where>" +
            "<if test=\"healthRecord.username !=''\">and username like concat('%',#{healthRecord.username},'%')</if> " +
            "<if test=\"healthRecord.name!=''\">and name like  concat('%',#{healthRecord.name},'%')</if>" +
            "and teacherid=#{username} " +
            "</where>" +
            "</script>")
    int selectAllNotValidTotalCountCondition(@Param("healthRecord") HealthRecord healthRecord, @Param("username") String username);

    @Select("<script> select username,name,phone from studentmanage.stuDetails <where>" +
            "<if test=\"healthRecord.username !=''\">and username like concat('%',#{healthRecord.username},'%')</if> " +
            "<if test=\"healthRecord.name!=''\">and name like  concat('%',#{healthRecord.name},'%')</if>" +
            "and teacherid=#{username}" +
            "</where>" +
            "limit #{begin},#{size}" +
            "</script>")
    List<HealthRecord> selectAllNotValidByPageAndCondition(@Param("begin") int begin, @Param("size") int size, @Param("healthRecord") HealthRecord healthRecord, @Param("username") String username);

    @Select("<script> select * from studentmanage.healthRecord <where>" +
            "<if test=\"healthRecord.username !=''\">and username like concat('%',#{healthRecord.username},'%')</if> " +
            "<if test=\"healthRecord.name!=''\">and name like  concat('%',#{healthRecord.name},'%')</if>" +
            "<if test=\"healthRecord.inschool!=''\">and inschool like  concat('%',#{healthRecord.inschool},'%')</if>" +
            "and deleted = 0 and status=#{healthRecord.status}" +
            "</where>" +
            "order by healthRecord.id desc " +
            "limit #{begin},#{size}" +
            "</script>")
    List<HealthRecord> selectAllByPageAndCondition(@Param("begin") int begin, @Param("size") int size, @Param("healthRecord") HealthRecord healthRecord);

    @Select("<script> select count(*) from studentmanage.healthRecord <where>" +
            "<if test=\"username !=''\">and username like concat('%',#{username},'%')</if> " +
            "<if test=\"name!=''\">and name like  concat('%',#{name},'%')</if>" +
            "<if test=\"inschool!=''\">and inschool like  concat('%',#{inschool},'%')</if>" +
            "and  deleted = 0 and status=#{status}" +
            "</where>" +
            "</script>")
    int selectAllCountCondition(HealthRecord healthRecord);

    @Select("<script> select count(*) from studentmanage.healthRecord <where>" +
            "<if test=\"username !=''\">and username like concat('%',#{username},'%')</if> " +
            "<if test=\"name!=''\">and name like  concat('%',#{name},'%')</if>" +
            "<if test=\"inschool!=''\">and inschool like  concat('%',#{inschool},'%')</if>" +
            "and  deleted = 0 and status=#{status} and UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %00:%00:%00'))  &lt;= UNIX_TIMESTAMP(createtime) AND UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %00:%00:%00')) + 86400 &gt;= UNIX_TIMESTAMP(createtime)" +
            "</where>" +
            "</script>")
    int selectAllValidTotalCountCondition(HealthRecord healthRecord);

    @Select("<script> select * from studentmanage.healthRecord <where>" +
            "<if test=\"healthRecord.username !=''\">and username like concat('%',#{healthRecord.username},'%')</if> " +
            "<if test=\"healthRecord.name!=''\">and name like  concat('%',#{healthRecord.name},'%')</if>" +
            "<if test=\"healthRecord.inschool!=''\">and inschool like  concat('%',#{healthRecord.inschool},'%')</if>" +
            "and healthRecord.deleted = 0 and healthRecord.status=#{healthRecord.status} and UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %00:%00:%00'))  &lt;= UNIX_TIMESTAMP(healthRecord.createtime) AND UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %00:%00:%00')) + 86400 &gt;= UNIX_TIMESTAMP(healthRecord.createtime)" +
            "</where>" +
            "order by healthRecord.id desc " +
            "limit #{begin},#{size}" +
            "</script>")
    List<HealthRecord> selectAllValidByPageAndCondition(@Param("begin") int begin, @Param("size") int size, @Param("healthRecord") HealthRecord healthRecord);


    @Select("select * from studentmanage.healthRecord where deleted = 0 and status=#{status} and UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %00:%00:%00'))  <= UNIX_TIMESTAMP(createtime) AND UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %00:%00:%00')) + 86400 >= UNIX_TIMESTAMP(createtime)")
    List<HealthRecord> selectAllPeopleValid(HealthRecord healthRecord);

    @Select("<script> select count(*) from ${tablename} <where>" +
            "<if test=\"healthRecord.username !=''\">and username like concat('%',#{healthRecord.username},'%')</if> " +
            "<if test=\"healthRecord.name!=''\">and name like  concat('%',#{healthRecord.name},'%')</if>" +
            "and username not in <foreach collection='list' item='item' separator=',' open='(' close=')'>  #{item.username} </foreach> " +
            "</where>" +
            "</script>")
    int selectAllPeopleValidTotalCountCondition(@Param("healthRecord") HealthRecord healthRecord, @Param("tablename") String tablename, @Param("list") List<HealthRecord> healthRecords);

    @Select("<script> select username,name,phone from ${tablename} <where>" +
            "<if test=\"healthRecord.username !=''\">and username like concat('%',#{healthRecord.username},'%')</if> " +
            "<if test=\"healthRecord.name!=''\">and name like  concat('%',#{healthRecord.name},'%')</if>" +
            "and username not in <foreach collection='list' item='item' separator=',' open='(' close=')'>  #{item.username} </foreach> " +
            "</where>" +
            "limit #{begin},#{size}" +
            "</script>")
    List<HealthRecord> selectAllNotPeopleValidByPageAndCondition(@Param("begin") int begin, @Param("size") int size, @Param("healthRecord") HealthRecord healthRecord, @Param("tablename") String tablename, @Param("list") List<HealthRecord> healthRecords);

    @Select("<script> select count(*) from ${tablename} <where>" +
            "<if test=\"healthRecord.username !=''\">and username like concat('%',#{healthRecord.username},'%')</if> " +
            "<if test=\"healthRecord.name!=''\">and name like  concat('%',#{healthRecord.name},'%')</if>" +
            "</where>" +
            "</script>")
    int selectallPeopleValidTotalCountCondition(@Param("healthRecord") HealthRecord healthRecord, @Param("tablename") String tablename);


    @Select("<script> select username,name,phone from ${tablename} <where>" +
            "<if test=\"healthRecord.username !=''\">and username like concat('%',#{healthRecord.username},'%')</if> " +
            "<if test=\"healthRecord.name!=''\">and name like  concat('%',#{healthRecord.name},'%')</if>" +
            "</where>" +
            "limit #{begin},#{size}" +
            "</script>")
    List<HealthRecord> selectallNotValidByPageAndCondition(@Param("begin") int begin, @Param("size") int size, @Param("healthRecord") HealthRecord healthRecord, @Param("tablename") String tablename);

    @Select("select * from studentmanage.healthRecord where id = #{id}")
    HealthRecord selectById(int id);

    @Update("update studentmanage.healthRecord set username=#{username},name=#{name},phone=#{phone},symptom=#{symptom},inschool=#{inschool},address=#{address} where id=#{id} and status=(select identity from studentmanage.user where username=#{username} and name=#{name})")
    int updateHealth(HealthRecord healthRecord);

    @Update("update studentmanage.healthRecord set deleted=1 where id=#{id}")
    int deleteById(int id);

    @Insert("insert into studentmanage.healthRecord " +
            "(id,username,name,phone,symptom,inschool,address,createtime,status,deleted) " +
            "select null,#{healthRecord.username}," +
            "(select name from ${tablename} where username=#{healthRecord.username})," +
            "(select phone from ${tablename} where username=#{healthRecord.username})," +
            "#{healthRecord.symptom},#{healthRecord.inschool},#{healthRecord.address}," +
            "#{healthRecord.createtime},#{healthRecord.status}," +
            "#{healthRecord.deleted} from dual " +
            "where exists (select username from studentmanage.user where identity =#{identity} and username=#{healthRecord.username} and deleted = 0)")
    int addHealth(@Param("healthRecord") HealthRecord healthRecord, @Param("tablename") String tablename, @Param("identity") int identity);

    @Update("<script>update studentmanage.healthRecord set deleted=1 where id in " +
            "<foreach collection='ids' item='id' separator=',' open='(' close=')'>  #{id} </foreach> " +
            "</script>")
    int deleteHealth(@Param("ids") int[] ids);

    @Select("select phone from studentmanage.stuDetails where username not in(select username from studentmanage.healthRecord where  UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %00:%00:%00'))  <= UNIX_TIMESTAMP(createtime) AND UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %00:%00:%00')) + 86400 >= UNIX_TIMESTAMP(createtime) and deleted=0)")
    List<StuDetails> selectNotValidStudentSchedule();

    @Select("select phone from studentmanage.teacherDetails where username not in(select username from studentmanage.healthRecord where  UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %00:%00:%00'))  <= UNIX_TIMESTAMP(createtime) AND UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %00:%00:%00')) + 86400 >= UNIX_TIMESTAMP(createtime) and deleted=0)")
    List<TeacherDetails> selectNotValidTeacherSchedule();

}