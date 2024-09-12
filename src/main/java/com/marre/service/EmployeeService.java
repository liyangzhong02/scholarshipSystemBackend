package com.marre.service;

import com.marre.entity.Employee;
import com.marre.entity.dto.EmployeeDTO;
import com.marre.entity.dto.EmployeeLoginDTO;
import com.marre.entity.dto.EmployeePageQueryDTO;
import com.marre.utils.PageResult;

/**
 * @author :marRE
 * @Description:
 * @Date :2024/9/2
 */

public interface EmployeeService {
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    void save(EmployeeDTO employeeDTO);

    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    Employee getById(Long id);

    void update(EmployeeDTO employeeDTO);
}
