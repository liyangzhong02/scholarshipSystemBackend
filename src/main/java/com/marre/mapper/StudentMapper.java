package com.marre.mapper;

import com.github.pagehelper.Page;
import com.marre.annotation.AutoFill;
import com.marre.entity.Awards;
import com.marre.entity.Student;
import com.marre.entity.dto.AwardsPageQueryDTO;
import com.marre.entity.dto.StudentDTO;
import com.marre.entity.dto.StudentPageQueryDTO;
import com.marre.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author :marRE
 * @Description:
 * @Date :2024/9/3
 */
@Mapper
public interface StudentMapper {
    Student getBySno(Long sNo);

    @AutoFill(OperationType.INSERT)
    void insert(Student student);


    Page<Student> pageQuery(StudentPageQueryDTO studentPageQueryDTO);

    Student getById(Long id);

    @AutoFill(OperationType.UPDATE)
    void update(Student student);

    Page<Awards> awardsPageQuery(AwardsPageQueryDTO awardsPageQueryDTO);
}
