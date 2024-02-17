package zhangshun.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhangshun.dao.InformationRecordDao;
import zhangshun.domain.InformationRecord;
import zhangshun.domain.PageBean;
import zhangshun.service.InformationRecordService;
import zhangshun.utils.InformationRecordUtils;

import java.util.List;

@Service
public class InformationRecordServiceImpl implements InformationRecordService {
    @Autowired
    InformationRecordDao informationRecordDao;
    @Autowired
    InformationRecordUtils informationRecordUtils;

    @Override
    public boolean AddStudentRecord(InformationRecord informationRecord) {
        informationRecordUtils.setStudentInformationRecord(informationRecord);
        return informationRecordDao.addRecord(informationRecord) > 0;
    }

    @Override
    public boolean AddTeacherRecord(InformationRecord informationRecord) {
        informationRecordUtils.setTeacherInformationRecord(informationRecord);
        return informationRecordDao.addRecord(informationRecord) > 0;
    }

    @Override
    public PageBean<InformationRecord> SelectStudentRecordByPageAndCondition(int currentPage, int size, InformationRecord informationRecord) {
        //计算开始索引
        int begin = (currentPage - 1) * size;
        //如果当前页码值大于总页码值，重新查询，使用最大页码值作为当前页码值
        PageBean<InformationRecord> pageBean = new PageBean<>();
        List<InformationRecord> rows;
        informationRecord.setStatus(1);
        int totalCount = informationRecordDao.selectTotalCountCondition(informationRecord);
        if (totalCount == 0) {
            pageBean.setRows(null);
            pageBean.setTotalCount(0);
            return pageBean;
        }

        int totalPage = totalCount % size != 0 ? totalCount / size + 1 : totalCount / size;

        if (currentPage > totalPage) {
            rows = informationRecordDao.selectByPageAndCondition((totalPage - 1) * size, size, informationRecord);
        } else {
            rows = informationRecordDao.selectByPageAndCondition(begin, size, informationRecord);
        }

        //将内容省略
        for (InformationRecord i : rows) {
            if (i.getContent().length() >= 30) {
                i.setContent(i.getContent().substring(0, 27) + "...");
            }
        }

        pageBean.setRows(rows);
        pageBean.setTotalCount(totalCount);
        return pageBean;
    }

    @Override
    public PageBean<InformationRecord> SelectTeacherRecordByPageAndCondition(int currentPage, int size, InformationRecord informationRecord) {
        {
            //计算开始索引
            int begin = (currentPage - 1) * size;
            //如果当前页码值大于总页码值，重新查询，使用最大页码值作为当前页码值
            PageBean<InformationRecord> pageBean = new PageBean<>();
            List<InformationRecord> rows;
            informationRecord.setStatus(2);
            int totalCount = informationRecordDao.selectTotalCountCondition(informationRecord);
            if (totalCount == 0) {
                pageBean.setRows(null);
                pageBean.setTotalCount(0);
                return pageBean;
            }

            int totalPage = totalCount % size != 0 ? totalCount / size + 1 : totalCount / size;

            if (currentPage > totalPage) {
                rows = informationRecordDao.selectByPageAndCondition((totalPage - 1) * size, size, informationRecord);
            } else {
                rows = informationRecordDao.selectByPageAndCondition(begin, size, informationRecord);
            }

            //将内容省略
            for (InformationRecord i : rows) {
                if (i.getContent().length() >= 30) {
                    i.setContent(i.getContent().substring(0, 28) + "...");
                }
            }
            pageBean.setRows(rows);
            pageBean.setTotalCount(totalCount);
            return pageBean;
        }

    }

    @Override
    public boolean DeleteRecord(int id) {
        return informationRecordDao.deleteRecord(id) > 0;
    }

    @Override
    public boolean DeleteRecords(int[] ids) {
        return informationRecordDao.deleteRecords(ids) > 0;
    }

    @Override
    public InformationRecord SelectById(int id) {
        return informationRecordDao.selectById(id);
    }

    @Override
    public boolean UpDateById(InformationRecord informationRecord) {
        return informationRecordDao.updateById(informationRecord) > 0;
    }
}
