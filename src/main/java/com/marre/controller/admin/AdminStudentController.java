package com.marre.controller.admin;

import com.marre.entity.Student;
import com.marre.entity.dto.StudentDTO;
import com.marre.entity.dto.StudentPageQueryDTO;
import com.marre.service.StudentService;
import com.marre.utils.PageResult;
import com.marre.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @project: scholarshipSystemBackend
 * @ClassName: AdminStudentController
 * @author: Marre
 * @creat: 2024/9/13 09:55
 * 管理端学生接口
 */
@RestController
@RequestMapping("/admin/student")
@Api(tags = "管理端的学生接口")
@Slf4j
public class AdminStudentController {

    @Autowired
    private StudentService studentService;

    /**
     * 分页查询学生
     * @param studentPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("分页查询学生")
    public Result<PageResult> page(StudentPageQueryDTO studentPageQueryDTO){
        log.info("分页查询学生中：{}", studentPageQueryDTO);
        PageResult pageResult = studentService.pageQuery(studentPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 修改学生信息
     * @param studentDTO
     * @return
     */
    @PutMapping
    @ApiOperation("修改学生信息")
    public Result update(@RequestBody StudentDTO studentDTO){
        log.info("修改信息：{}", studentDTO);
        studentService.update(studentDTO);
        return Result.success();
    }

    /**
     * 新增学生
     * @param studentDTO
     * @return
     */
    @PostMapping
    @ApiOperation("新增学生")
    public Result<String> save(@RequestBody StudentDTO studentDTO){
        log.info("新增学生：{}", studentDTO);
        studentService.save(studentDTO);

        return Result.success();
    }

    /**
     * 根据id查询学生
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询学生")
    public Result<Student> getById(@PathVariable Long id){
        log.info("查询学生中 Id：{}", id);
        Student student = studentService.getById(id);
        return Result.success(student);
    }
}
