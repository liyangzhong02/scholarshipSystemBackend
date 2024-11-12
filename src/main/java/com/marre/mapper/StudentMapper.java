package com.marre.mapper;

import com.github.pagehelper.Page;
import com.marre.annotation.AutoFill;
import com.marre.entity.Awards;
import com.marre.entity.Student;
import com.marre.entity.dto.AwardsPageQueryDTO;
import com.marre.entity.dto.StudentPageQueryDTO;
import com.marre.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author :marRE
 * @Description:
 * @Date :2024/9/3
 */
@Mapper
public interface StudentMapper {
    Student getBySno(Long sNo);

    @AutoFill(value = OperationType.INSERT)
    void insert(Student student);

    List<Student> selectAll();

    Page<Student> pageQuery(StudentPageQueryDTO studentPageQueryDTO);

    Student getById(Long id);

    @AutoFill(value = OperationType.UPDATE)
    void update(Student student);

    Page<Awards> awardsPageQuery(AwardsPageQueryDTO awardsPageQueryDTO);
}
