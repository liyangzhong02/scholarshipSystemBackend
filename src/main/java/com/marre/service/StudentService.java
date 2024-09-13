package com.marre.service;

import com.marre.entity.Student;
import com.marre.entity.dto.AwardsPageQueryDTO;
import com.marre.entity.dto.StudentDTO;
import com.marre.entity.dto.StudentLoginDTO;
import com.marre.entity.dto.StudentPageQueryDTO;
import com.marre.utils.PageResult;

/**
 * @author :marRE
 * @Description:
 * @Date :2024/9/2
 */
public interface StudentService {
    Student login(StudentLoginDTO studentLoginDTO);

    void save(StudentDTO studentDTO);

    PageResult pageQuery(StudentPageQueryDTO studentPageQueryDTO);

    Student getById(Long id);

    void update(StudentDTO studentDTO);

    PageResult awardsPageQuery(AwardsPageQueryDTO awardsPageQueryDTO);
}
