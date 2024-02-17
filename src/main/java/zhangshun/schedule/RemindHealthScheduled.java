package zhangshun.schedule;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import zhangshun.dao.HealthRecordDao;
import zhangshun.domain.StuDetails;
import zhangshun.domain.TeacherDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Component
public class RemindHealthScheduled {
    @Autowired
    private HealthRecordDao healthRecordDao;

    @Scheduled(cron = "0 0 20 * * ?")
    public void scheduled() {
        //获取未打卡学生
        List<StuDetails> stuDetails = healthRecordDao.selectNotValidStudentSchedule();
        List<TeacherDetails> teacherDetails = healthRecordDao.selectNotValidTeacherSchedule();

        List<String> phones = new ArrayList<>();
        for (StuDetails stu : stuDetails) {
            phones.add("+86" + stu.getPhone());
        }
        for (TeacherDetails tea : teacherDetails) {
            phones.add("+86" + tea.getPhone());
        }

        int size = phones.size();
        if (size == 0) {
            System.out.println("所有学生今日都已完成打卡");
        } else {
            String[] arr = new String[size];
            //腾讯云默认一次数组只能存放200个手机号
            //如果数组长度小于200
            if (size <= 200) {
                this.sendReminder(phones.toArray(arr));
            }
            //如果数组长度超过200
            else {
                int Size = size % 200 != 0 ? size / 200 : size / 200 - 1;

                for (int i = 0; i < Size; i++) {
                    int to = 200 + 200 * i;
                    if (size / (200 * (i + 1)) < 1) {
                        to = size % 200 + 200 * i;
                    }
                    //切片 前闭后开
                    this.sendReminder(phones.toArray(Arrays.copyOfRange(arr, 200 * i, to)));
                }
            }

        }

    }

    public void sendReminder(String[] arr) {
        try {
            // 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey,此处还需注意密钥对的保密
            // 密钥可前往https://console.cloud.tencent.com/cam/capi网站进行获取
            Credential cred = new Credential("", "");
            // 实例化一个http选项，可选的，没有特殊需求可以跳过
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("sms.tencentcloudapi.com");
            // 实例化一个client选项，可选的，没有特殊需求可以跳过
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            // 实例化要请求产品的client对象,clientProfile是可选的
            SmsClient client = new SmsClient(cred, "ap-nanjing", clientProfile);
            // 实例化一个请求对象,每个接口都会对应一个request对象
            SendSmsRequest req = new SendSmsRequest();
            req.setPhoneNumberSet(arr);

            req.setSmsSdkAppId("");
            req.setSignName("");
            req.setTemplateId("");


            // 返回的resp是一个SendSmsResponse的实例，与请求对象对应
            SendSmsResponse resp = client.SendSms(req);
            // 输出json格式的字符串回包
            System.out.println(SendSmsResponse.toJsonString(resp));
        } catch (TencentCloudSDKException e) {
            System.out.println(e.getMessage());
        }
    }
}
