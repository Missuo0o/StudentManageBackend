package zhangshun.utils;

import org.springframework.stereotype.Component;
import zhangshun.domain.HealthRecord;

import java.sql.Timestamp;
import java.util.Date;

@Component
public class HealthRecordUtils {
    public void setStudentHealthRecord(HealthRecord healthRecord) {
        if ("是".equals(healthRecord.getInschool())) {
            healthRecord.setAddress("学校内");
        }
        healthRecord.setDeleted(0);
        healthRecord.setStatus(1);
//        Date now = new Date();
//        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        healthRecord.setCreatetime(new Timestamp(new Date().getTime()));
    }

    public void setTeacherHealthRecord(HealthRecord healthRecord) {
        if ("是".equals(healthRecord.getInschool())) {
            healthRecord.setAddress("学校内");
        }
        healthRecord.setDeleted(0);
        healthRecord.setStatus(2);
//        Date now = new Date();
//        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        healthRecord.setCreatetime(new Timestamp(new Date().getTime()));
    }
}
