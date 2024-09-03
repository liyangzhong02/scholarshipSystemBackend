package com.marre.Controller;

import com.github.pagehelper.Page;
import com.marre.Service.EmployeeService;
import com.marre.constant.JwtClaimsConstant;
import com.marre.entity.Employee;
import com.marre.entity.dto.EmployeeDTO;
import com.marre.entity.dto.EmployeeLoginDTO;
import com.marre.entity.dto.EmployeePageQueryDTO;
import com.marre.entity.vo.EmployeeLoginVO;
import com.marre.mapper.EmployeeMapper;
import com.marre.properties.JwtProperties;
import com.marre.utils.JwtUtil;
import com.marre.utils.PageResult;
import com.marre.utils.Result;
import io.jsonwebtoken.Jwt;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author :marRE
 * @Description:
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

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO){
        log.info("login:{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims
        );

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

    @PostMapping
    public Result<String> save(@RequestBody EmployeeDTO employeeDTO){
        log.info("新增员工：{}", employeeDTO);
        employeeService.save(employeeDTO);

        return Result.success();
    }

    /**
     * 分页查询
     * @param employeePageQueryDTO
     * @return
     */
    @GetMapping("/page")
    public Result<PageResult> page(EmployeePageQueryDTO employeePageQueryDTO){
        log.info("分页查询中：{}", employeePageQueryDTO);
        PageResult pageResult = employeeService.pageQuery(employeePageQueryDTO);

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
