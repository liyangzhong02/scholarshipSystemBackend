package com.marre.mapper;

import com.marre.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @project: scholarshipSystemBackend
 * @ClassName: ApplicationRepository
 * @author: Marre
 * @creat: 2024/9/12 16:07
 * Spring data JPA
 */
public interface ApplicationRepository extends JpaRepository<Application, Long> {
}
