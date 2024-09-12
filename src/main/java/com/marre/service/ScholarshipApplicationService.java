package com.marre.service;

import com.marre.entity.dto.ApplicationDTO;
import com.marre.entity.dto.AuditDTO;
import com.marre.enumeration.AuditStatus;

/**
 * @project: scholarshipSystemBackend
 * @ClassName: ScholarshipApplicationService
 * @author: Marre
 * @creat: 2024/9/12 09:42
 * 奖学金审核Service
 */
public interface ScholarshipApplicationService {

    void submitApplication(ApplicationDTO applicationDTO);

    void auditApplication(AuditDTO auditDTO);

    AuditStatus getApplicationStatus(Long id);
}
