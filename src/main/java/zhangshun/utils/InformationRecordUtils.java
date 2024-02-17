package zhangshun.utils;

import org.springframework.stereotype.Component;
import zhangshun.domain.InformationRecord;

import java.sql.Timestamp;
import java.util.Date;

@Component
public class InformationRecordUtils {
    public void setStudentInformationRecord(InformationRecord informationRecord) {
        informationRecord.setDeleted(0);
        informationRecord.setStatus(1);
//        Date now = new Date();
//        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        informationRecord.setCreatetime(new Timestamp(new Date().getTime()));
    }

    public void setTeacherInformationRecord(InformationRecord informationRecord) {
        informationRecord.setDeleted(0);
        informationRecord.setStatus(2);
//        Date now = new Date();
//        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        informationRecord.setCreatetime(new Timestamp(new Date().getTime()));
    }

}
