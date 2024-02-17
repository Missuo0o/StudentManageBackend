package zhangshun.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhangshun.dao.LeaveRecordDao;
import zhangshun.domain.LeaveRecord;
import zhangshun.domain.PageBean;
import zhangshun.service.LeaveRecordService;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Service
public class LeaveRecordServiceImpl implements LeaveRecordService {
    @Autowired
    private LeaveRecordDao leaveRecordDao;

    @Override
    public boolean AddLeaveRecord(LeaveRecord leaveRecord) {
        leaveRecord.setStatus(0);
        leaveRecord.setDeleted(0);
        return leaveRecordDao.addLeaveRecord(leaveRecord) > 0;
    }

    @Override
    public PageBean<LeaveRecord> SelectStudentByPageAndCondition(int currentPage, int size, LeaveRecord leaveRecord, String username) {
        //计算开始索引
        int begin = (currentPage - 1) * size;

        //如果当前页码值大于总页码值，重新查询，使用最大页码值作为当前页码值
        PageBean<LeaveRecord> pageBean = new PageBean<>();
        List<LeaveRecord> rows;

        int totalCount = leaveRecordDao.selectTotalCountCondition(leaveRecord, username);

        if (totalCount == 0) {
            pageBean.setRows(null);
            pageBean.setTotalCount(0);
            return pageBean;
        }

        int totalPage = totalCount % size != 0 ? totalCount / size + 1 : totalCount / size;

        if (currentPage > totalPage) {
            rows = leaveRecordDao.selectByPageAndCondition((totalPage - 1) * size, size, leaveRecord, username);
        } else {
            rows = leaveRecordDao.selectByPageAndCondition(begin, size, leaveRecord, username);
        }

        //将内容省略
        for (LeaveRecord i : rows) {
            if (i.getRemark().length() >= 10) {
                i.setRemark(i.getRemark().substring(0, 8) + "...");
            }
        }

        pageBean.setRows(rows);
        pageBean.setTotalCount(totalCount);
        return pageBean;
    }

    @Override
    public PageBean<LeaveRecord> SelectNotApprovedStudentByPageAndCondition(int currentPage, int size, LeaveRecord leaveRecord, String username) {
        //计算开始索引
        int begin = (currentPage - 1) * size;

        //如果当前页码值大于总页码值，重新查询，使用最大页码值作为当前页码值
        PageBean<LeaveRecord> pageBean = new PageBean<>();
        List<LeaveRecord> rows;

        int totalCount = leaveRecordDao.selectNotApprovedTotalCountCondition(leaveRecord, username);

        if (totalCount == 0) {
            pageBean.setRows(null);
            pageBean.setTotalCount(0);
            return pageBean;
        }

        int totalPage = totalCount % size != 0 ? totalCount / size + 1 : totalCount / size;

        if (currentPage > totalPage) {
            rows = leaveRecordDao.selectNotApprovedByPageAndCondition((totalPage - 1) * size, size, leaveRecord, username);
        } else {
            rows = leaveRecordDao.selectNotApprovedByPageAndCondition(begin, size, leaveRecord, username);
        }

        //将内容省略
        for (LeaveRecord i : rows) {
            if (i.getRemark().length() >= 10) {
                i.setRemark(i.getRemark().substring(0, 8) + "...");
            }
        }

        pageBean.setRows(rows);
        pageBean.setTotalCount(totalCount);
        return pageBean;
    }

    @Override
    public boolean UpdateSuccessStatus(int id, int status) {
        return leaveRecordDao.updateStatus(id, status) > 0;
    }

    @Override
    public boolean UpdateFailStatus(int id, int status) {
        return leaveRecordDao.updateStatus(id, status) > 0;
    }

    @Override
    public boolean UpdateOriginsStatus(int id, int status) {
        return leaveRecordDao.updateStatus(id, status) > 0;
    }

    @Override
    public LeaveRecord SelectById(int id) {
        return leaveRecordDao.selectById(id);
    }

    @Override
    public PageBean<LeaveRecord> SelectByPageAndUsername(int currentPage, int size, String username) {
        //计算开始索引
        int begin = (currentPage - 1) * size;

        //如果当前页码值大于总页码值，重新查询，使用最大页码值作为当前页码值
        PageBean<LeaveRecord> pageBean = new PageBean<>();
        List<LeaveRecord> rows;

        int totalCount = leaveRecordDao.selectCountLeaveRecordByusername(username);

        if (totalCount == 0) {
            pageBean.setRows(null);
            pageBean.setTotalCount(0);
            return pageBean;
        }

        int totalPage = totalCount % size != 0 ? totalCount / size + 1 : totalCount / size;

        if (currentPage > totalPage) {
            rows = leaveRecordDao.selectByPageAndUsername((totalPage - 1) * size, size, username);
        } else {
            rows = leaveRecordDao.selectByPageAndUsername(begin, size, username);
        }
        //将内容省略
        for (LeaveRecord i : rows) {
            if (i.getRemark().length() >= 10) {
                i.setRemark(i.getRemark().substring(0, 8) + "...");
            }
        }
        pageBean.setRows(rows);
        pageBean.setTotalCount(totalCount);
        return pageBean;
    }

    @Override
    public PageBean<LeaveRecord> SelectAllStudentByPageAndCondition(int currentPage, int size, LeaveRecord leaveRecord) {
        //计算开始索引
        int begin = (currentPage - 1) * size;

        //如果当前页码值大于总页码值，重新查询，使用最大页码值作为当前页码值
        PageBean<LeaveRecord> pageBean = new PageBean<>();
        List<LeaveRecord> rows;

        int totalCount = leaveRecordDao.selectAllTotalCountCondition(leaveRecord);

        if (totalCount == 0) {
            pageBean.setRows(null);
            pageBean.setTotalCount(0);
            return pageBean;
        }

        int totalPage = totalCount % size != 0 ? totalCount / size + 1 : totalCount / size;

        if (currentPage > totalPage) {
            rows = leaveRecordDao.selectAllByPageAndCondition((totalPage - 1) * size, size, leaveRecord);
        } else {
            rows = leaveRecordDao.selectAllByPageAndCondition(begin, size, leaveRecord);
        }

        //将内容省略
        for (LeaveRecord i : rows) {
            if (i.getRemark().length() >= 10) {
                i.setRemark(i.getRemark().substring(0, 8) + "...");
            }
        }

        pageBean.setRows(rows);
        pageBean.setTotalCount(totalCount);
        return pageBean;
    }

    @Override
    public PageBean<LeaveRecord> SelectAllNotApprovedStudentByPageAndCondition(int currentPage, int size, LeaveRecord leaveRecord) {
        //计算开始索引
        int begin = (currentPage - 1) * size;

        //如果当前页码值大于总页码值，重新查询，使用最大页码值作为当前页码值
        PageBean<LeaveRecord> pageBean = new PageBean<>();
        List<LeaveRecord> rows;

        int totalCount = leaveRecordDao.selectAllNotApprovedTotalCountCondition(leaveRecord);

        if (totalCount == 0) {
            pageBean.setRows(null);
            pageBean.setTotalCount(0);
            return pageBean;
        }

        int totalPage = totalCount % size != 0 ? totalCount / size + 1 : totalCount / size;

        if (currentPage > totalPage) {
            rows = leaveRecordDao.selectAllNotApprovedByPageAndCondition((totalPage - 1) * size, size, leaveRecord);
        } else {
            rows = leaveRecordDao.selectAllNotApprovedByPageAndCondition(begin, size, leaveRecord);
        }

        //将内容省略
        for (LeaveRecord i : rows) {
            if (i.getRemark().length() >= 10) {
                i.setRemark(i.getRemark().substring(0, 8) + "...");
            }
        }

        pageBean.setRows(rows);
        pageBean.setTotalCount(totalCount);
        return pageBean;
    }

    @Override
    public boolean DeleteRecords(int[] ids) {
        return leaveRecordDao.deleteRecords(ids) > 0;
    }

    @Override
    public boolean AddStudentLeave(LeaveRecord leaveRecord) throws SQLIntegrityConstraintViolationException {
        try {
            return leaveRecordDao.addStudentLeave(leaveRecord) > 0;
        } catch (Exception e) {
            Throwable cause = e.getCause();
            if (cause instanceof SQLIntegrityConstraintViolationException) {
                throw new SQLIntegrityConstraintViolationException();
            } else {
                throw e;
            }
        }
    }
}
