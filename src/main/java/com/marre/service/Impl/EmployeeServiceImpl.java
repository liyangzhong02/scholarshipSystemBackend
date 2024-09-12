package com.marre.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.marre.service.EmployeeService;
import com.marre.constant.MessageConstant;
import com.marre.constant.PasswordConstant;
import com.marre.entity.Employee;
import com.marre.entity.dto.EmployeeDTO;
import com.marre.entity.dto.EmployeeLoginDTO;
import com.marre.entity.dto.EmployeePageQueryDTO;
import com.marre.exception.AccountNotFoundException;
import com.marre.exception.PasswordErrorException;
import com.marre.mapper.EmployeeMapper;
import com.marre.utils.PageResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;


/**
 * @author :marRE
 * @Description:
 * @Date :2024/9/2
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;
    @Override
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        Long eNo = employeeLoginDTO.getENo();
        String ePassword = employeeLoginDTO.getEPassword();

        // 查询数据库数据
        Employee employee = employeeMapper.getByEno(eNo);

        // handle exception
        if(employee == null){
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        // 密码比对
        // 加密前端密码
        ePassword = DigestUtils.md5DigestAsHex(ePassword.getBytes()).toLowerCase();

        String dbPassword = employee.getEPassword().trim().toLowerCase();

        System.out.println("前端密码: '" + ePassword + "'");
        System.out.println("数据库密码: '" + dbPassword + "'");
        System.out.println("密码长度 - 前端: " + ePassword.length() + ", 数据库: " + dbPassword.length());

        if(!ePassword.equals(dbPassword)){
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        return employee;
    }

    @Override
    public void save(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();

        BeanUtils.copyProperties(employeeDTO, employee);

        employee.setEPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));

        employeeMapper.insert(employee);
    }

    @Override
    public PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO) {
        PageHelper.startPage(employeePageQueryDTO.getPage(), employeePageQueryDTO.getPageSize());

        Page<Employee> page = employeeMapper.pageQuery(employeePageQueryDTO);

        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public Employee getById(Long id) {
        Employee employee = employeeMapper.getById(id);
        return employee;
    }

    @Override
    public void update(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);

        employeeMapper.update(employee);
    }
}
