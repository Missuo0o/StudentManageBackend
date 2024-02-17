package zhangshun.dao;

import org.apache.ibatis.annotations.*;
import zhangshun.domain.LeaveRecord;

import java.util.List;

@Mapper
public interface LeaveRecordDao {
    @Insert("insert into studentmanage.leaveRecord values (null,#{username},#{name},#{phone},#{outtime},#{intime},#{typename},#{remark},#{deleted},#{status})")
    int addLeaveRecord(LeaveRecord leaveRecord);

    @Select("<script> select count(*) from studentmanage.leaveRecord <where>" +
            "<if test=\"leaveRecord.username !=''\">and username like concat('%',#{leaveRecord.username},'%')</if> " +
            "<if test=\"leaveRecord.name!=''\">and name like concat('%',#{leaveRecord.name},'%')</if>" +
            "<if test=\"leaveRecord.typename!=''\">and typename like  concat('%',#{leaveRecord.typename},'%')</if>" +
            "and  leaveRecord.deleted = 0 and  leaveRecord.username in (select username from stuDetails where teacherid=#{username})" +
            "</where>" +
            "</script>")
    int selectTotalCountCondition(@Param("leaveRecord") LeaveRecord leaveRecord, @Param("username") String username);

    @Select("<script> select * from studentmanage.leaveRecord <where>" +
            "<if test=\"leaveRecord.username !=''\">and username like concat('%',#{leaveRecord.username},'%')</if> " +
            "<if test=\"leaveRecord.name!=''\">and name like  concat('%',#{leaveRecord.name},'%')</if>" +
            "<if test=\"leaveRecord.typename!=''\">and typename like  concat('%',#{leaveRecord.typename},'%')</if>" +
            "and leaveRecord.deleted = 0 and  leaveRecord.username in (select username from stuDetails where teacherid=#{username})" +
            "</where>" +
            "order by leaveRecord.id desc " +
            "limit #{begin},#{size}" +
            "</script>")
    List<LeaveRecord> selectByPageAndCondition(@Param("begin") int begin, @Param("size") int size, @Param("leaveRecord") LeaveRecord leaveRecord, @Param("username") String username);


    @Select("<script> select count(*) from studentmanage.leaveRecord <where>" +
            "<if test=\"leaveRecord.username !=''\">and username like concat('%',#{leaveRecord.username},'%')</if> " +
            "<if test=\"leaveRecord.name!=''\">and name like concat('%',#{leaveRecord.name},'%')</if>" +
            "<if test=\"leaveRecord.typename!=''\">and typename like  concat('%',#{leaveRecord.typename},'%')</if>" +
            "and  leaveRecord.deleted = 0 and leaveRecord.status = 0 and  leaveRecord.username in (select username from stuDetails where teacherid=#{username})" +
            "</where>" +
            "</script>")
    int selectNotApprovedTotalCountCondition(@Param("leaveRecord") LeaveRecord leaveRecord, @Param("username") String username);

    @Select("<script> select * from studentmanage.leaveRecord <where>" +
            "<if test=\"leaveRecord.username !=''\">and username like concat('%',#{leaveRecord.username},'%')</if> " +
            "<if test=\"leaveRecord.name!=''\">and name like  concat('%',#{leaveRecord.name},'%')</if>" +
            "<if test=\"leaveRecord.typename!=''\">and typename like  concat('%',#{leaveRecord.typename},'%')</if>" +
            "and leaveRecord.deleted = 0  and leaveRecord.status = 0 and leaveRecord.username in (select username from stuDetails where teacherid=#{username})" +
            "</where>" +
            "order by leaveRecord.id desc " +
            "limit #{begin},#{size}" +
            "</script>")
    List<LeaveRecord> selectNotApprovedByPageAndCondition(@Param("begin") int begin, @Param("size") int size, @Param("leaveRecord") LeaveRecord leaveRecord, @Param("username") String username);

    @Update("update studentmanage.leaveRecord set status=#{status} where id=#{id}")
    int updateStatus(@Param("id") int id, @Param("status") int status);

    @Select("select * from studentmanage.leaveRecord where id=#{id}")
    LeaveRecord selectById(int id);

    @Select("select * from studentmanage.leaveRecord where username=#{username} and deleted=0 order by id desc limit #{begin},#{size}")
    List<LeaveRecord> selectByPageAndUsername(@Param("begin") int begin, @Param("size") int size, @Param("username") String username);

    @Select("select count(*) from studentmanage.leaveRecord where username=#{username} and deleted=0")
    int selectCountLeaveRecordByusername(String username);

    @Select("<script> select count(*) from studentmanage.leaveRecord <where>" +
            "<if test=\"username !=''\">and username like concat('%',#{username},'%')</if> " +
            "<if test=\"name!=''\">and name like concat('%',#{name},'%')</if>" +
            "<if test=\"typename!=''\">and typename like  concat('%',#{typename},'%')</if>" +
            "and  deleted = 0" +
            "</where>" +
            "</script>")
    int selectAllTotalCountCondition(LeaveRecord leaveRecord);

    @Select("<script> select * from studentmanage.leaveRecord <where>" +
            "<if test=\"leaveRecord.username !=''\">and username like concat('%',#{leaveRecord.username},'%')</if> " +
            "<if test=\"leaveRecord.name!=''\">and name like  concat('%',#{leaveRecord.name},'%')</if>" +
            "<if test=\"leaveRecord.typename!=''\">and typename like  concat('%',#{leaveRecord.typename},'%')</if>" +
            "and leaveRecord.deleted = 0" +
            "</where>" +
            "order by leaveRecord.id desc " +
            "limit #{begin},#{size}" +
            "</script>")
    List<LeaveRecord> selectAllByPageAndCondition(@Param("begin") int begin, @Param("size") int size, @Param("leaveRecord") LeaveRecord leaveRecord);


    @Select("<script> select count(*) from studentmanage.leaveRecord <where>" +
            "<if test=\"username !=''\">and username like concat('%',#{username},'%')</if> " +
            "<if test=\"name!=''\">and name like concat('%',#{name},'%')</if>" +
            "<if test=\"typename!=''\">and typename like  concat('%',#{typename},'%')</if>" +
            "and  deleted = 0 and status = 0" +
            "</where>" +
            "</script>")
    int selectAllNotApprovedTotalCountCondition(LeaveRecord leaveRecord);

    @Select("<script> select * from studentmanage.leaveRecord <where>" +
            "<if test=\"leaveRecord.username !=''\">and username like concat('%',#{leaveRecord.username},'%')</if> " +
            "<if test=\"leaveRecord.name!=''\">and name like  concat('%',#{leaveRecord.name},'%')</if>" +
            "<if test=\"leaveRecord.typename!=''\">and typename like  concat('%',#{leaveRecord.typename},'%')</if>" +
            "and leaveRecord.deleted = 0  and leaveRecord.status = 0" +
            "</where>" +
            "order by leaveRecord.id desc " +
            "limit #{begin},#{size}" +
            "</script>")
    List<LeaveRecord> selectAllNotApprovedByPageAndCondition(@Param("begin") int begin, @Param("size") int size, @Param("leaveRecord") LeaveRecord leaveRecord);

    @Update("<script>update studentmanage.leaveRecord set deleted=1 where id in " +
            "<foreach collection='ids' item='id' separator=',' open='(' close=')'>  #{id} </foreach></script> ")
    int deleteRecords(@Param("ids") int[] ids);

    @Insert("INSERT INTO studentmanage.leaveRecord \n" +
            "  SELECT NULL, username, name, phone, outtime, intime, typename, remark, 0, 0\n" +
            "  FROM (\n" +
            "    SELECT \n" +
            "      #{username} AS username, \n" +
            "      (SELECT name FROM studentmanage.stuDetails WHERE username=#{username}) AS name,\n" +
            "      (SELECT phone FROM studentmanage.stuDetails WHERE username=#{username}) AS phone,\n" +
            "      #{outtime} AS outtime,\n" +
            "      #{intime} AS intime,\n" +
            "      #{typename} AS typename,\n" +
            "      #{remark} AS remark\n" +
            "  ) AS tmp\n" +
            "  WHERE EXISTS (\n" +
            "    SELECT *\n" +
            "    FROM studentmanage.user\n" +
            "    WHERE username = #{username} AND deleted = 0\n" +
            "  );\n")

//    @Insert("INSERT INTO studentmanage.leaveRecord " +
//            "(id, username, name, phone, outtime, intime, typename, remark, status, deleted) " +
//            "SELECT NULL, " +
//            "#{username}, " +
//            "(SELECT name FROM studentmanage.stuDetails WHERE username = #{username}), " +
//            "(SELECT phone FROM studentmanage.stuDetails WHERE username = #{username}), " +
//            "#{outtime}, " +
//            "#{intime}, " +
//            "#{typename}, " +
//            "#{remark}, " +
//            "0, " +
//            "0 " +
//            "FROM DUAL " +
//            "WHERE EXISTS (SELECT * FROM studentmanage.user WHERE username = #{username} AND deleted = 0)")

    int addStudentLeave(LeaveRecord leaveRecord);
}
