package com.marre.service;

import com.marre.entity.dto.ApplicationDTO;
import com.marre.entity.dto.ApplicationPageQueryDTO;
import com.marre.entity.dto.AuditDTO;
import com.marre.enumeration.AuditStatus;
import com.marre.utils.PageResult;

/**
 * @project: scholarshipSystemBackend
 * @ClassName: ScholarshipApplicationService
 * @author: Marre
 * @creat: 2024/9/12 09:42
 * 奖学金审核Service
 */
public interface ScholarshipApplicationService {

    void submitApplication(ApplicationDTO applicationDTO);

    void processApplication(AuditDTO auditDTO);

    AuditStatus getApplicationStatus(Long id);

    PageResult pageQuery(ApplicationPageQueryDTO applicationPageQueryDTO);
}
