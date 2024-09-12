package com.marre.service;

import com.marre.entity.dto.ApplicationDTO;

/**
 * @project: scholarshipSystemBackend
 * @ClassName: ScholarshipApplicationService
 * @author: Marre
 * @creat: 2024/9/12 09:42
 * 奖学金审核Service
 */
public interface ScholarshipApplicationService {
    /**
     * 奖学金审核
     * @param applicationDTO
     * @return
     */
    void audits(ApplicationDTO applicationDTO);
}
