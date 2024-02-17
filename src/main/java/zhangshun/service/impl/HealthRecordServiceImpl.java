package zhangshun.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhangshun.dao.HealthRecordDao;
import zhangshun.domain.HealthRecord;
import zhangshun.domain.PageBean;
import zhangshun.service.HealthRecordService;
import zhangshun.utils.HealthRecordUtils;

import java.util.List;

@Service
public class HealthRecordServiceImpl implements HealthRecordService {
    @Autowired
    private HealthRecordDao healthRecordDao;
    @Autowired
    private HealthRecordUtils healthRecordUtils;

    @Override
    public boolean AddStudentHealthRecord(HealthRecord healthRecord) {
        healthRecordUtils.setStudentHealthRecord(healthRecord);
        return healthRecordDao.addHealthRecord(healthRecord) > 0;
    }

    @Override
    public boolean AddTeacherHealthRecord(HealthRecord healthRecord) {
        healthRecordUtils.setTeacherHealthRecord(healthRecord);
        return healthRecordDao.addHealthRecord(healthRecord) > 0;
    }

    @Override
    public List<HealthRecord> SelectValidByUsername(String username) {
        return healthRecordDao.selectValidByUsername(username);
    }

    @Override
    public int SelectCountHealthRecord(String username) {
        return healthRecordDao.selectCountHealthRecord(username);
    }

    @Override
    public PageBean<HealthRecord> SelectByPageAndUsername(int currentPage, int size, HealthRecord healthRecord) {
        //计算开始索引
        int begin = (currentPage - 1) * size;

        //如果当前页码值大于总页码值，重新查询，使用最大页码值作为当前页码值
        PageBean<HealthRecord> pageBean = new PageBean<>();
        List<HealthRecord> rows;

        int totalCount = healthRecordDao.selectTotalCountByUsername(healthRecord);

        if (totalCount == 0) {
            pageBean.setRows(null);
            pageBean.setTotalCount(0);
            return pageBean;
        }

        int totalPage = totalCount % size != 0 ? totalCount / size + 1 : totalCount / size;

        if (currentPage > totalPage) {
            rows = healthRecordDao.selectByPageAndUsername((totalPage - 1) * size, size, healthRecord);
        } else {
            rows = healthRecordDao.selectByPageAndUsername(begin, size, healthRecord);
        }
        pageBean.setRows(rows);
        pageBean.setTotalCount(totalCount);
        return pageBean;
    }

    @Override
    public PageBean<HealthRecord> SelectStudentByPageAndCondition(int currentPage, int size, HealthRecord healthRecord, String username) {
        //计算开始索引
        int begin = (currentPage - 1) * size;

        //如果当前页码值大于总页码值，重新查询，使用最大页码值作为当前页码值
        PageBean<HealthRecord> pageBean = new PageBean<>();
        List<HealthRecord> rows;

        int totalCount = healthRecordDao.selectTotalCountCondition(healthRecord, username);

        if (totalCount == 0) {
            pageBean.setRows(null);
            pageBean.setTotalCount(0);
            return pageBean;
        }

        int totalPage = totalCount % size != 0 ? totalCount / size + 1 : totalCount / size;

        if (currentPage > totalPage) {
            rows = healthRecordDao.selectByPageAndCondition((totalPage - 1) * size, size, healthRecord, username);
        } else {
            rows = healthRecordDao.selectByPageAndCondition(begin, size, healthRecord, username);
        }
        pageBean.setRows(rows);
        pageBean.setTotalCount(totalCount);
        return pageBean;
    }

    @Override
    public PageBean<HealthRecord> SelectValidByPageAndCondition(int currentPage, int size, HealthRecord healthRecord, String username) {
        //计算开始索引
        int begin = (currentPage - 1) * size;

        //如果当前页码值大于总页码值，重新查询，使用最大页码值作为当前页码值
        PageBean<HealthRecord> pageBean = new PageBean<>();
        List<HealthRecord> rows;

        int totalCount = healthRecordDao.selectValidTotalCountCondition(healthRecord, username);

        if (totalCount == 0) {
            pageBean.setRows(null);
            pageBean.setTotalCount(0);
            return pageBean;
        }

        int totalPage = totalCount % size != 0 ? totalCount / size + 1 : totalCount / size;

        if (currentPage > totalPage) {
            rows = healthRecordDao.selectValidByPageAndCondition((totalPage - 1) * size, size, healthRecord, username);
        } else {
            rows = healthRecordDao.selectValidByPageAndCondition(begin, size, healthRecord, username);
        }
        pageBean.setRows(rows);
        pageBean.setTotalCount(totalCount);
        return pageBean;
    }

    @Override
    public PageBean<HealthRecord> SelectNotValidByPageAndCondition(int currentPage, int size, HealthRecord healthRecord, String username) {
        //计算开始索引
        int begin = (currentPage - 1) * size;

        //如果当前页码值大于总页码值，重新查询，使用最大页码值作为当前页码值
        PageBean<HealthRecord> pageBean = new PageBean<>();
        List<HealthRecord> rows;

        //查询今日打卡的数据
        List<HealthRecord> healthRecords = healthRecordDao.selectAllValid(healthRecord, username);
        if (healthRecords.size() > 0) {
            int totalCount = healthRecordDao.selectNotValidTotalCountCondition(healthRecord, username, healthRecords);

            if (totalCount == 0) {
                pageBean.setRows(null);
                pageBean.setTotalCount(0);
                return pageBean;
            }

            int totalPage = totalCount % size != 0 ? totalCount / size + 1 : totalCount / size;

            if (currentPage > totalPage) {
                rows = healthRecordDao.selectNotValidByPageAndCondition((totalPage - 1) * size, size, healthRecord, username, healthRecords);
            } else {
                rows = healthRecordDao.selectNotValidByPageAndCondition(begin, size, healthRecord, username, healthRecords);
            }
            pageBean.setRows(rows);
            pageBean.setTotalCount(totalCount);
            return pageBean;
        } else {
            int totalCount = healthRecordDao.selectAllNotValidTotalCountCondition(healthRecord, username);

            if (totalCount == 0) {
                pageBean.setRows(null);
                pageBean.setTotalCount(0);
                return pageBean;
            }

            int totalPage = totalCount % size != 0 ? totalCount / size + 1 : totalCount / size;

            if (currentPage > totalPage) {
                rows = healthRecordDao.selectAllNotValidByPageAndCondition((totalPage - 1) * size, size, healthRecord, username);
            } else {
                rows = healthRecordDao.selectAllNotValidByPageAndCondition(begin, size, healthRecord, username);
            }
            pageBean.setRows(rows);
            pageBean.setTotalCount(totalCount);
            return pageBean;
        }

    }

    @Override
    public PageBean<HealthRecord> SelectAllStudentByPageAndCondition(int currentPage, int size, HealthRecord healthRecord) {
        //计算开始索引
        int begin = (currentPage - 1) * size;

        healthRecord.setStatus(1);

        //如果当前页码值大于总页码值，重新查询，使用最大页码值作为当前页码值
        PageBean<HealthRecord> pageBean = new PageBean<>();
        List<HealthRecord> rows;

        int totalCount = healthRecordDao.selectAllCountCondition(healthRecord);

        if (totalCount == 0) {
            pageBean.setRows(null);
            pageBean.setTotalCount(0);
            return pageBean;
        }

        int totalPage = totalCount % size != 0 ? totalCount / size + 1 : totalCount / size;

        if (currentPage > totalPage) {
            rows = healthRecordDao.selectAllByPageAndCondition((totalPage - 1) * size, size, healthRecord);
        } else {
            rows = healthRecordDao.selectAllByPageAndCondition(begin, size, healthRecord);
        }
        pageBean.setRows(rows);
        pageBean.setTotalCount(totalCount);
        return pageBean;
    }

    @Override
    public PageBean<HealthRecord> SelectAllTeacherByPageAndCondition(int currentPage, int size, HealthRecord healthRecord) {
        //计算开始索引
        int begin = (currentPage - 1) * size;

        healthRecord.setStatus(2);

        //如果当前页码值大于总页码值，重新查询，使用最大页码值作为当前页码值
        PageBean<HealthRecord> pageBean = new PageBean<>();
        List<HealthRecord> rows;

        int totalCount = healthRecordDao.selectAllCountCondition(healthRecord);

        if (totalCount == 0) {
            pageBean.setRows(null);
            pageBean.setTotalCount(0);
            return pageBean;
        }

        int totalPage = totalCount % size != 0 ? totalCount / size + 1 : totalCount / size;

        if (currentPage > totalPage) {
            rows = healthRecordDao.selectAllByPageAndCondition((totalPage - 1) * size, size, healthRecord);
        } else {
            rows = healthRecordDao.selectAllByPageAndCondition(begin, size, healthRecord);
        }
        pageBean.setRows(rows);
        pageBean.setTotalCount(totalCount);
        return pageBean;
    }

    @Override
    public PageBean<HealthRecord> SelectAllStudentValidByPageAndCondition(int currentPage, int size, HealthRecord healthRecord) {
        //计算开始索引
        int begin = (currentPage - 1) * size;
        healthRecord.setStatus(1);
        //如果当前页码值大于总页码值，重新查询，使用最大页码值作为当前页码值
        PageBean<HealthRecord> pageBean = new PageBean<>();
        List<HealthRecord> rows;

        int totalCount = healthRecordDao.selectAllValidTotalCountCondition(healthRecord);

        if (totalCount == 0) {
            pageBean.setRows(null);
            pageBean.setTotalCount(0);
            return pageBean;
        }

        int totalPage = totalCount % size != 0 ? totalCount / size + 1 : totalCount / size;

        if (currentPage > totalPage) {
            rows = healthRecordDao.selectAllValidByPageAndCondition((totalPage - 1) * size, size, healthRecord);
        } else {
            rows = healthRecordDao.selectAllValidByPageAndCondition(begin, size, healthRecord);
        }
        pageBean.setRows(rows);
        pageBean.setTotalCount(totalCount);
        return pageBean;
    }

    @Override
    public PageBean<HealthRecord> SelectAllTeacherValidByPageAndCondition(int currentPage, int size, HealthRecord healthRecord) {
        //计算开始索引
        int begin = (currentPage - 1) * size;
        healthRecord.setStatus(2);
        //如果当前页码值大于总页码值，重新查询，使用最大页码值作为当前页码值
        PageBean<HealthRecord> pageBean = new PageBean<>();
        List<HealthRecord> rows;

        int totalCount = healthRecordDao.selectAllValidTotalCountCondition(healthRecord);

        if (totalCount == 0) {
            pageBean.setRows(null);
            pageBean.setTotalCount(0);
            return pageBean;
        }

        int totalPage = totalCount % size != 0 ? totalCount / size + 1 : totalCount / size;

        if (currentPage > totalPage) {
            rows = healthRecordDao.selectAllValidByPageAndCondition((totalPage - 1) * size, size, healthRecord);
        } else {
            rows = healthRecordDao.selectAllValidByPageAndCondition(begin, size, healthRecord);
        }
        pageBean.setRows(rows);
        pageBean.setTotalCount(totalCount);
        return pageBean;
    }

    @Override
    public PageBean<HealthRecord> SelectNotAllStudentValidByPageAndCondition(int currentPage, int size, HealthRecord healthRecord) {
        //计算开始索引
        int begin = (currentPage - 1) * size;
        healthRecord.setStatus(1);
        //如果当前页码值大于总页码值，重新查询，使用最大页码值作为当前页码值
        PageBean<HealthRecord> pageBean = new PageBean<>();
        List<HealthRecord> rows;

        //查询今日打卡的数据
        List<HealthRecord> healthRecords = healthRecordDao.selectAllPeopleValid(healthRecord);
        if (healthRecords.size() > 0) {
            int totalCount = healthRecordDao.selectAllPeopleValidTotalCountCondition(healthRecord, "studentmanage.stuDetails", healthRecords);

            if (totalCount == 0) {
                pageBean.setRows(null);
                pageBean.setTotalCount(0);
                return pageBean;
            }

            int totalPage = totalCount % size != 0 ? totalCount / size + 1 : totalCount / size;

            if (currentPage > totalPage) {
                rows = healthRecordDao.selectAllNotPeopleValidByPageAndCondition((totalPage - 1) * size, size, healthRecord, "studentmanage.stuDetails", healthRecords);
            } else {
                rows = healthRecordDao.selectAllNotPeopleValidByPageAndCondition(begin, size, healthRecord, "studentmanage.stuDetails", healthRecords);
            }
            pageBean.setRows(rows);
            pageBean.setTotalCount(totalCount);
            return pageBean;
        } else {
            int totalCount = healthRecordDao.selectallPeopleValidTotalCountCondition(healthRecord, "studentmanage.stuDetails");

            if (totalCount == 0) {
                pageBean.setRows(null);
                pageBean.setTotalCount(0);
                return pageBean;
            }

            int totalPage = totalCount % size != 0 ? totalCount / size + 1 : totalCount / size;

            if (currentPage > totalPage) {
                rows = healthRecordDao.selectallNotValidByPageAndCondition((totalPage - 1) * size, size, healthRecord, "studentmanage.stuDetails");
            } else {
                rows = healthRecordDao.selectallNotValidByPageAndCondition(begin, size, healthRecord, "studentmanage.stuDetails");
            }
            pageBean.setRows(rows);
            pageBean.setTotalCount(totalCount);
            return pageBean;
        }
    }

    @Override
    public PageBean<HealthRecord> SelectNotAllTeacherValidByPageAndCondition(int currentPage, int size, HealthRecord healthRecord) {
        //计算开始索引
        int begin = (currentPage - 1) * size;
        healthRecord.setStatus(2);
        //如果当前页码值大于总页码值，重新查询，使用最大页码值作为当前页码值
        PageBean<HealthRecord> pageBean = new PageBean<>();
        List<HealthRecord> rows;

        //查询今日打卡的数据
        List<HealthRecord> healthRecords = healthRecordDao.selectAllPeopleValid(healthRecord);
        if (healthRecords.size() > 0) {
            int totalCount = healthRecordDao.selectAllPeopleValidTotalCountCondition(healthRecord, "studentmanage.teacherDetails", healthRecords);

            if (totalCount == 0) {
                pageBean.setRows(null);
                pageBean.setTotalCount(0);
                return pageBean;
            }

            int totalPage = totalCount % size != 0 ? totalCount / size + 1 : totalCount / size;

            if (currentPage > totalPage) {
                rows = healthRecordDao.selectAllNotPeopleValidByPageAndCondition((totalPage - 1) * size, size, healthRecord, "studentmanage.teacherDetails", healthRecords);
            } else {
                rows = healthRecordDao.selectAllNotPeopleValidByPageAndCondition(begin, size, healthRecord, "studentmanage.teacherDetails", healthRecords);
            }
            pageBean.setRows(rows);
            pageBean.setTotalCount(totalCount);
            return pageBean;
        } else {
            int totalCount = healthRecordDao.selectallPeopleValidTotalCountCondition(healthRecord, "studentmanage.teacherDetails");

            if (totalCount == 0) {
                pageBean.setRows(null);
                pageBean.setTotalCount(0);
                return pageBean;
            }

            int totalPage = totalCount % size != 0 ? totalCount / size + 1 : totalCount / size;

            if (currentPage > totalPage) {
                rows = healthRecordDao.selectallNotValidByPageAndCondition((totalPage - 1) * size, size, healthRecord, "studentmanage.teacherDetails");
            } else {
                rows = healthRecordDao.selectallNotValidByPageAndCondition(begin, size, healthRecord, "studentmanage.teacherDetails");
            }
            pageBean.setRows(rows);
            pageBean.setTotalCount(totalCount);
            return pageBean;
        }
    }

    @Override
    public HealthRecord SelectById(int id) {
        return healthRecordDao.selectById(id);
    }

    @Override
    public boolean UpdateHealth(HealthRecord healthRecord) {
        if ("是".equals(healthRecord.getInschool())) {
            healthRecord.setAddress("学校内");
        }
        return healthRecordDao.updateHealth(healthRecord) > 0;
    }

    @Override
    public boolean DeleteById(int id) {
        return healthRecordDao.deleteById(id) > 0;
    }

    @Override
    public boolean AddStudentHealth(HealthRecord healthRecord) {
        healthRecordUtils.setStudentHealthRecord(healthRecord);
        return healthRecordDao.addHealth(healthRecord, "studentmanage.stuDetails", 1) > 0;
    }


    @Override
    public boolean AddTeacherHealth(HealthRecord healthRecord) {
        healthRecordUtils.setTeacherHealthRecord(healthRecord);
        return healthRecordDao.addHealth(healthRecord, "studentmanage.teacherDetails", 2) > 0;
    }

    @Override
    public boolean DeleteHealth(int[] ids) {
        return healthRecordDao.deleteHealth(ids) > 0;
    }

}
