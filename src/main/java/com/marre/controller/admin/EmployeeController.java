package com.marre.controller.admin;

import com.marre.entity.dto.*;
import com.marre.enumeration.AuditStatus;
import com.marre.service.EmployeeService;
import com.marre.constant.JwtClaimsConstant;
import com.marre.entity.Employee;
import com.marre.entity.vo.EmployeeLoginVO;
import com.marre.properties.JwtProperties;
import com.marre.service.ScholarshipApplicationService;
import com.marre.service.StudentService;
import com.marre.utils.JwtUtil;
import com.marre.utils.PageResult;
import com.marre.utils.Result;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * @author :marRE
 * @Description: 管理员接口
 * @Date :2024/9/2
 */
@RestController
@RequestMapping("/admin/employee")
@Api(tags = "管理员接口")
@Slf4j
public class EmployeeController {


    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private ScholarshipApplicationService applicationService;

    @Autowired
    StudentService studentService;


    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO){
        log.info("登录用户为：{}", employeeLoginDTO);
        Employee employee = employeeService.login(employeeLoginDTO);
        // 生成token
        String token = JwtUtil.createToken(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                employee.getId()
        );
        // 封装进VO对象
        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .eNo(employee.getENo())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 登出
     * @return
     */
    @PostMapping("/logout")
    public Result<String> logout(){return Result.success();}

    /**
     * 新增员工
     * @param employeeDTO
     * @return
     */
    @PostMapping
    public Result<String> save(@RequestBody EmployeeDTO employeeDTO){
        log.info("新增员工为：{}", employeeDTO);
        employeeService.save(employeeDTO);
        return Result.success();
    }

    /**
     * 分页查询教师
     * @param employeePageQueryDTO
     * @return
     */
    @GetMapping("/page")
    public Result<PageResult> page(EmployeePageQueryDTO employeePageQueryDTO){
        log.info("分页查询教师中：{}", employeePageQueryDTO);
        PageResult pageResult = employeeService.pageQuery(employeePageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 分页查询学生
     * @param studentPageQueryDTO
     * @return
     */
    @GetMapping("/studentpage")
    public Result<PageResult> page(StudentPageQueryDTO studentPageQueryDTO){
        log.info("分页查询学生中：{}", studentPageQueryDTO);
        PageResult pageResult = studentService.pageQuery(studentPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Employee> getById(@PathVariable Long id){
        Employee employee = employeeService.getById(id);
        return Result.success(employee);
    }

    /**
     * 更新用户
     * @param employeeDTO
     * @return
     */
    @PutMapping()
    public Result update(@RequestBody EmployeeDTO employeeDTO){
        log.info("修改信息为：{}", employeeDTO);
        employeeService.update(employeeDTO);
        return Result.success();
    }

}
