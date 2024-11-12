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
    public void award() {
        List<Student> allStudents = studentMapper.selectAll();
        Map<String, List<Student>> groupedStudents = allStudents.stream()
                .collect(Collectors.groupingBy(student -> student.getSGrade() + "-" + student.getSYear()));
        groupedStudents.forEach((key, studentsInGroup) -> {
            studentsInGroup.sort((s1, s2) -> Double.compare(s2.getSTotals(), s1.getSTotals()));
            for (int i = 0; i < studentsInGroup.size(); i++) {
                Student student = studentsInGroup.get(i);
                if (i == 0) {
                    student.setIsPrice(true);
                    student.setAwardName("一等奖学金");
                } else if (i >= 1 && i <= 4) {
                    student.setIsPrice(true);
                    student.setAwardName("二等奖学金");
                } else {
                    student.setIsPrice(false);
                }
                studentMapper.update(student);
            }
        });
    }
}