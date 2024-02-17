package zhangshun.dao;

import org.apache.ibatis.annotations.*;
import zhangshun.domain.InformationRecord;

import java.util.List;

@Mapper
public interface InformationRecordDao {
    @Insert("insert into studentmanage.informationRecord values(null,#{title},#{content},#{createtime},#{adminid},#{adminname},#{status},#{deleted}) ")
    int addRecord(InformationRecord informationRecord);

    @Select("<script> select * from studentmanage.informationRecord <where>" +
            "<if test=\"informationRecord.id !=''\">and id like concat('%',#{informationRecord.id},'%')</if> " +
            "<if test=\"informationRecord.title!=''\">and title like  concat('%',#{informationRecord.title},'%')</if>" +
            "<if test=\"informationRecord.adminname!=''\">and adminname like  concat('%',#{informationRecord.adminname},'%')</if>" +
            "and deleted = 0 and status=#{informationRecord.status} " +
            "</where>" +
            "order by informationRecord.id desc " +
            "limit #{begin},#{size}" +
            "</script>")
    List<InformationRecord> selectByPageAndCondition(@Param("begin") int begin, @Param("size") int size, @Param("informationRecord") InformationRecord informationRecord);


    //id=0时，if语句没有执行。
    //
    //究其原因，原来mybatis默认将integer=0的参数等于&lsquo;&rsquo;空串。
    @Select("<script> select count(*) from studentmanage.informationRecord <where>" +
            "<if test=\"id !=''\">and id like concat('%',#{id},'%')</if> " +
            "<if test=\"title!=''\">and title like  concat('%',#{title},'%')</if>" +
            "<if test=\"adminname!=''\">and adminname like  concat('%',#{adminname},'%')</if>" +
            "and deleted = 0 and status=#{status}" +
            "</where>" +
            "</script>")
    int selectTotalCountCondition(InformationRecord informationRecord);

    @Update("<script>update studentmanage.informationRecord set deleted=1 where id in " +
            "<foreach collection='ids' item='id' separator=',' open='(' close=')'>  #{id} </foreach></script> ")
    int deleteRecords(@Param("ids") int[] ids);

    @Update("update studentmanage.informationRecord set deleted=1 " +
            "where id = #{id}")
    int deleteRecord(int id);

    @Select("select * from studentmanage.informationRecord where id=#{id} ")
    InformationRecord selectById(int id);

    @Update("update studentmanage.informationRecord set title=#{title} ,content=#{content} where id = #{id} ")
    int updateById(InformationRecord informationRecord);
}
