package com.marre.controller.student;

import com.marre.entity.Application;
import com.marre.entity.dto.ApplicationDTO;
import com.marre.service.ScholarshipApplicationService;
import com.marre.service.StudentService;
import com.marre.constant.JwtClaimsConstant;
import com.marre.entity.Student;
import com.marre.entity.dto.StudentDTO;
import com.marre.entity.dto.StudentLoginDTO;
import com.marre.entity.dto.StudentPageQueryDTO;
import com.marre.entity.vo.StudentLoginVO;
import com.marre.properties.JwtProperties;
import com.marre.utils.BaseContext;
import com.marre.utils.JwtUtil;
import com.marre.utils.PageResult;
import com.marre.utils.Result;
import io.micrometer.core.instrument.binder.BaseUnits;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author :marRE
 * @Description:
 * @Date :2024/9/3
 */

@RestController
@RequestMapping("/student/student") //前一个代表从哪个客户端发来的请求
@Api(tags = "学生接口")
@Slf4j
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private ScholarshipApplicationService applicationService;

    /**
     * 学生登陆
     * @param studentLoginDTO
     * @return
     */
    @PostMapping("/login")
    public Result<StudentLoginVO> login(@RequestBody StudentLoginDTO studentLoginDTO){
        Student student = studentService.login(studentLoginDTO);
        // 生成token
        String token = JwtUtil.createToken(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                student.getId()
        );
        //  封装进vo对象
        StudentLoginVO studentLoginVO = StudentLoginVO.builder()
                .id(student.getId())
                .sNo(student.getSNo())
                .sPassword(student.getSPassword())
                .token(token)
                .build();
        return Result.success(studentLoginVO);
    }

    /**
     * 登出
     * @return
     */
    @PostMapping("/logout")
    public Result<String> logout(){return Result.success();}

    @PostMapping()
    public Result<String> save(@RequestBody StudentDTO studentDTO){
        log.info("新增学生：{}", studentDTO);
        studentService.save(studentDTO);

        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<Student> getById(@PathVariable Long id){
        log.info("查询学生中 Id：{}", id);
        Student student = studentService.getById(id);
        return Result.success(student);
    }

    @PutMapping()
    public Result update(@RequestBody StudentDTO studentDTO){
        log.info("修改信息：{}", studentDTO);
        studentService.update(studentDTO);
        return Result.success();
    }
}
