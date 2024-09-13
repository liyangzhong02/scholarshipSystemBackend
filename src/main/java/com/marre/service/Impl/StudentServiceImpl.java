package com.marre.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.marre.entity.Awards;
import com.marre.entity.dto.AwardsPageQueryDTO;
import com.marre.service.StudentService;
import com.marre.constant.MessageConstant;
import com.marre.constant.PasswordConstant;
import com.marre.entity.Student;
import com.marre.entity.dto.StudentDTO;
import com.marre.entity.dto.StudentLoginDTO;
import com.marre.entity.dto.StudentPageQueryDTO;
import com.marre.exception.AccountNotFoundException;
import com.marre.exception.PasswordErrorException;
import com.marre.mapper.StudentMapper;
import com.marre.utils.BaseContext;
import com.marre.utils.PageResult;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class StudentServiceImpl implements StudentService{
    @Autowired
    StudentMapper studentMapper;

    @Override
    public Student login(StudentLoginDTO studentLoginDTO) {
        Long sNo = studentLoginDTO.getSNo();
        String sPassword = studentLoginDTO.getSPassword();

        Student student = studentMapper.getBySno(sNo);

        if(student == null){
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        sPassword = DigestUtils.md5DigestAsHex(sPassword.getBytes()).toLowerCase();

        String dbPassword = student.getSPassword().trim().toLowerCase();

        if(!sPassword.equals(dbPassword)){
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        return student;
    }

    @Override
    public void save(StudentDTO studentDTO) {
        Student student = new Student();

        BeanUtils.copyProperties(studentDTO, student);

        student.setSPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));

        studentMapper.insert(student);
    }

    @Override
    public PageResult pageQuery(StudentPageQueryDTO studentPageQueryDTO) {

        PageHelper.startPage(studentPageQueryDTO.getPage(), studentPageQueryDTO.getPageSize());

        Page<Student> page = studentMapper.pageQuery(studentPageQueryDTO);

        return new PageResult(page.getTotal(), page.getResult());

    }

    @Override
    public Student getById(Long id) {
        Student student = studentMapper.getById(id);
        return student;
    }

    @Override
    public void update(StudentDTO studentDTO) {
        Student student = new Student();
        BeanUtils.copyProperties(studentDTO, student);
        studentMapper.update(student);
    }

    @Override
    public PageResult awardsPageQuery(AwardsPageQueryDTO awardsPageQueryDTO) {
        PageHelper.startPage(awardsPageQueryDTO.getPage(), awardsPageQueryDTO.getPageSize());
        Page<Awards> page = studentMapper.awardsPageQuery(awardsPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }
}
