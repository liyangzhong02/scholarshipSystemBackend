package com.marre.controller.student;

import com.marre.service.StudentService;
import com.marre.constant.JwtClaimsConstant;
import com.marre.entity.Student;
import com.marre.entity.dto.StudentDTO;
import com.marre.entity.dto.StudentLoginDTO;
import com.marre.entity.dto.StudentPageQueryDTO;
import com.marre.entity.vo.StudentLoginVO;
import com.marre.properties.JwtProperties;
import com.marre.utils.JwtUtil;
import com.marre.utils.PageResult;
import com.marre.utils.Result;
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
@RequestMapping("/student/student")
@Api(tags = "学生接口")
@Slf4j
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 学生登陆
     * @param studentLoginDTO
     * @return
     */
    @PostMapping("/login")
    public Result<StudentLoginVO> login(@RequestBody StudentLoginDTO studentLoginDTO){
        Student student = studentService.login(studentLoginDTO);

        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.ID, student.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getUserTtl(),
                claims
        );

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

    @GetMapping("/page")
    public Result<PageResult> page(StudentPageQueryDTO studentPageQueryDTO){
        log.info("分页查询中：{}", studentPageQueryDTO);
        PageResult pageResult = studentService.pageQuery(studentPageQueryDTO);
        return Result.success(pageResult);
    }

    @GetMapping("/{id}")
    public Result<Student> getById(@PathVariable Long id){
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
