package zhangshun.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhangshun.dao.DormitoryDao;
import zhangshun.domain.Dormitory;
import zhangshun.domain.DormitoryStudent;
import zhangshun.domain.PageBean;
import zhangshun.domain.StuDetails;
import zhangshun.service.DormitoryService;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Service
public class DormitoryServiceImpl implements DormitoryService {
    @Autowired
    private DormitoryDao dormitoryDao;

    @Override
    public boolean AddStudentDormitory(int id, String username) {
        //判断是否已选
        int i = dormitoryDao.selectDuplicate(username);
        if (i >= 1) {
            return false;
        }

        int j = dormitoryDao.updateAddDormitory(id);
        if (j == 0) {
            return false;
        }

        int k = dormitoryDao.insertDormitory_Student(id, username);
        return j + k > 1;
    }

    @Override
    public boolean DeleteStudentDormitory(int id, String username) {
        int i = dormitoryDao.updateDeleteDormitory(id, username);
        int j = dormitoryDao.deleteCourse_Dormitory(id, username);
        return i + j > 1;
    }

    @Override
    public Dormitory MyDormitory(String username) {
        return dormitoryDao.myDormitory(username);
    }

    @Override
    public PageBean<Dormitory> SelectDormitoryByPageAndCondition(int currentPage, int size, Dormitory dormitory) {
        //计算开始索引
        int begin = (currentPage - 1) * size;

        //如果当前页码值大于总页码值，重新查询，使用最大页码值作为当前页码值
        PageBean<Dormitory> pageBean = new PageBean<>();
        List<Dormitory> rows;

        int totalCount = dormitoryDao.selectAllTotalCountCondition(dormitory);

        if (totalCount == 0) {
            pageBean.setRows(null);
            pageBean.setTotalCount(0);
            return pageBean;
        }

        int totalPage = totalCount % size != 0 ? totalCount / size + 1 : totalCount / size;

        if (currentPage > totalPage) {
            rows = dormitoryDao.selectAllByPageAndCondition((totalPage - 1) * size, size, dormitory);
        } else {
            rows = dormitoryDao.selectAllByPageAndCondition(begin, size, dormitory);
        }

        pageBean.setRows(rows);
        pageBean.setTotalCount(totalCount);
        return pageBean;
    }

    @Override
    public PageBean<Dormitory> SelectAlreadyCourseByPageAndCondition(int currentPage, int size, Dormitory dormitory, String username) {
        //计算开始索引
        int begin = (currentPage - 1) * size;

        //如果当前页码值大于总页码值，重新查询，使用最大页码值作为当前页码值
        PageBean<Dormitory> pageBean = new PageBean<>();
        List<Dormitory> rows;

        int totalCount = dormitoryDao.selectAlreadyTotalCountCondition(dormitory, username);

        if (totalCount == 0) {
            pageBean.setRows(null);
            pageBean.setTotalCount(0);
            return pageBean;
        }

        int totalPage = totalCount % size != 0 ? totalCount / size + 1 : totalCount / size;

        if (currentPage > totalPage) {
            rows = dormitoryDao.selectAlreadyByPageAndCondition((totalPage - 1) * size, size, dormitory, username);
        } else {
            rows = dormitoryDao.selectAlreadyByPageAndCondition(begin, size, dormitory, username);
        }

        pageBean.setRows(rows);
        pageBean.setTotalCount(totalCount);
        return pageBean;
    }

    @Override
    public PageBean<StuDetails> SelectPayByPageAndCondition(int currentPage, int size, StuDetails stuDetails) {
        //计算开始索引
        int begin = (currentPage - 1) * size;

        //如果当前页码值大于总页码值，重新查询，使用最大页码值作为当前页码值
        PageBean<StuDetails> pageBean = new PageBean<>();
        List<StuDetails> rows;

        int totalCount = dormitoryDao.selectPayTotalCountCondition(stuDetails);

        if (totalCount == 0) {
            pageBean.setRows(null);
            pageBean.setTotalCount(0);
            return pageBean;
        }

        int totalPage = totalCount % size != 0 ? totalCount / size + 1 : totalCount / size;

        if (currentPage > totalPage) {
            rows = dormitoryDao.selectPayByPageAndCondition((totalPage - 1) * size, size, stuDetails);
        } else {
            rows = dormitoryDao.selectPayByPageAndCondition(begin, size, stuDetails);
        }

        pageBean.setRows(rows);
        pageBean.setTotalCount(totalCount);
        return pageBean;
    }

    @Override
    public PageBean<StuDetails> SelectNotPayByPageAndCondition(int currentPage, int size, StuDetails stuDetails) {
        //计算开始索引
        int begin = (currentPage - 1) * size;

        //如果当前页码值大于总页码值，重新查询，使用最大页码值作为当前页码值
        PageBean<StuDetails> pageBean = new PageBean<>();
        List<StuDetails> rows;

        int totalCount = dormitoryDao.selectNotPayTotalCountCondition(stuDetails);

        if (totalCount == 0) {
            pageBean.setRows(null);
            pageBean.setTotalCount(0);
            return pageBean;
        }

        int totalPage = totalCount % size != 0 ? totalCount / size + 1 : totalCount / size;

        if (currentPage > totalPage) {
            rows = dormitoryDao.selectNotPayByPageAndCondition((totalPage - 1) * size, size, stuDetails);
        } else {
            rows = dormitoryDao.selectNotPayByPageAndCondition(begin, size, stuDetails);
        }

        pageBean.setRows(rows);
        pageBean.setTotalCount(totalCount);
        return pageBean;
    }

    @Override
    public boolean UpdateSuccessPaid(String username, int id) {
        return dormitoryDao.updatePay(username, id) > 0;
    }

    @Override
    public boolean UpdateFailPaid(String username, int id) {
        return dormitoryDao.updatePay(username, id) > 0;
    }

    @Override
    public void ExcelAddDormitory(List<Dormitory> list) {
        dormitoryDao.addExcelDormitory(list);
    }

    @Override
    public boolean DeleteById(int id) {
        return dormitoryDao.deleteById(id) > 0;
    }

    @Override
    public boolean DeleteDormitory(int[] ids) {
        return dormitoryDao.deleteDormitory(ids) > 0;
    }

    @Override
    public boolean AddDormitory(Dormitory dormitory) throws SQLIntegrityConstraintViolationException {
        try {
            List<Dormitory> dormitories = dormitoryDao.selectAll();
            for (Dormitory d : dormitories) {
                if (d.getFloor() == dormitory.getFloor() && d.getBuilding() == dormitory.getBuilding() && d.getRoom() == dormitory.getRoom()) {
                    return false;
                }
            }
            return dormitoryDao.addDormitory(dormitory) > 0;
        } catch (Exception e) {
            Throwable cause = e.getCause();
            if (cause instanceof SQLIntegrityConstraintViolationException) {
                throw new SQLIntegrityConstraintViolationException();
            } else {
                throw e;
            }
        }
    }

    @Override
    public boolean UpdateDormitory(Dormitory dormitory) throws SQLIntegrityConstraintViolationException {
        try {
            List<Dormitory> dormitories = dormitoryDao.selectAll();
            for (Dormitory d : dormitories) {
                if (d.getId() != dormitory.getId() && d.getFloor() == dormitory.getFloor() && d.getBuilding() == dormitory.getBuilding() && d.getRoom() == dormitory.getRoom()) {
                    return false;
                }
            }
            return dormitoryDao.updateDormitory(dormitory) > 0;
        } catch (Exception e) {
            Throwable cause = e.getCause();
            if (cause instanceof SQLIntegrityConstraintViolationException) {
                throw new SQLIntegrityConstraintViolationException();
            } else {
                throw e;
            }
        }
    }

    @Override
    public Dormitory SelectById(int id) {
        return dormitoryDao.selectById(id);
    }

    @Override
    public List<StuDetails> SelectStudentNotNull(int id) {
        return dormitoryDao.selectStudentNotnull(id);
    }

    @Override
    public List<StuDetails> SelectStudentNull() {
        return dormitoryDao.selectStudentNull();
    }

    @Override
    public boolean StudentUpdate(DormitoryStudent dormitoryStudent) {
        //如果前端传来的2个数组都不为空数组
        if (dormitoryStudent.getUsernameNotNull().size() > 0 && dormitoryStudent.getUsernameNull().size() > 0) {
            int i = dormitoryDao.updateStudentNotNull(dormitoryStudent);
            int j = dormitoryDao.updateStudentNull(dormitoryStudent);
            int k = dormitoryDao.updateDormitoryCount(dormitoryStudent);
            return i + j + k > 2;
        }
        //如果传来的一个数组为空数组
        else if (dormitoryStudent.getUsernameNull().size() > 0) {
            int i = dormitoryDao.updateStudentNull(dormitoryStudent);
            int k = dormitoryDao.updateDormitoryCount(dormitoryStudent);
            return i + k > 1;
        } else if (dormitoryStudent.getUsernameNotNull().size() > 0) {
            int i = dormitoryDao.updateStudentNotNull(dormitoryStudent);
            int k = dormitoryDao.updateDormitoryCount(dormitoryStudent);
            return i + k > 1;
        } else {
            return false;
        }
    }

}
