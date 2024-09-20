package com.marre.service.Impl;

import com.marre.entity.Student;
import com.marre.mapper.StudentMapper;
import com.marre.service.AwardService;
import com.marre.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Class: com.marre.service.Impl
 * @ClassName: AwardServiceImpl
 * @author: Marre
 * @creat: 9月 19
 * 奖学金评定实现类
 */
@Service
public class AwardServiceImpl implements AwardService {

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public Result award() {
        // 1. 获取所有学生
        List<Student> allStudents = studentMapper.selectAll();
        // 2. 按照年级，年份分组
        Map<String, List<Student>> groupedStudents = allStudents.stream()
                .collect(Collectors.groupingBy(student ->
                        student.getSGrade() + "-" + student.getSYear()));
        // 3. 处理每个分组
        for (Map.Entry<String, List<Student>> entry : groupedStudents.entrySet()) {
            List<Student> studentsInGroup = entry.getValue();

            // 4. 按总分降序排序
            studentsInGroup.sort((s1, s2) -> Double.compare(s2.getSTotals(), s1.getSTotals()));

            // 5. 设置获奖状态
            for (int i = 0; i < studentsInGroup.size(); i++) {
                Student student = studentsInGroup.get(i);
                if (i == 0) {
                    // 第一名
                    student.setIsPrice(true);
                    student.setAwardName("一等奖学金");
                } else if (i >= 1 && i <= 4) {
                    // 二到五名
                    student.setIsPrice(true);
                    student.setAwardName("二等奖学金");
                } else {
                    // 其他名次
                    student.setIsPrice(false);
                }
                // 6. 更新学生信息
                studentMapper.update(student);
            }
        }

        return Result.success();
    }
}