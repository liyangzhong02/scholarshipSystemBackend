package com.marre.mapper;

import com.github.pagehelper.Page;
import com.marre.annotation.AutoFill;
import com.marre.entity.Employee;
import com.marre.entity.dto.EmployeePageQueryDTO;
import com.marre.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author :marRE
 * @Description:
 * @Date :2024/9/2
 */
@Mapper
public interface EmployeeMapper {

    @Select("select * from employee where e_no = #{eNo}")
    Employee getByEno(Long eNo);

    @Insert("insert into employee (e_no, e_name, e_password, create_time, update_time, create_user, update_user)"
        + "values" +
            "(#{eNo}, #{eName}, #{ePassword}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    @AutoFill(value = OperationType.INSERT)
    void insert(Employee employee);

    Page<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    @Select("select * from employee where id = #{id}")
    Employee getById(Long id);
    @AutoFill(value = OperationType.UPDATE)
    void update(Employee employee);
}
