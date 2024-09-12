package com.marre.service.Impl;

import com.marre.entity.dto.ApplicationDTO;
import com.marre.service.ScholarshipApplicationService;
import org.springframework.stereotype.Service;

/**
 * @project: scholarshipSystemBackend
 * @ClassName: ScholarshipApplicationServiceImpl
 * @author: Marre
 * @creat: 2024/9/12 09:42
 * 奖学金审核实现类
 */
@Service
public class ScholarshipApplicationServiceImpl implements ScholarshipApplicationService {
    /**
     * 奖学金审核
     * @param applicationDTO
     * @return
     */
    @Override
    public void audits(ApplicationDTO applicationDTO) {
        //TODO 使用redis做数据库
    }
}
