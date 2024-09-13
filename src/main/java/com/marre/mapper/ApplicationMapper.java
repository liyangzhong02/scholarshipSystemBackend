package com.marre.mapper;

import com.github.pagehelper.Page;
import com.marre.entity.Application;
import com.marre.entity.dto.ApplicationPageQueryDTO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @project: scholarshipSystemBackend
 * @ClassName: ApplicationMapper
 * @author: Marre
 * @creat: 2024/9/13 09:35
 * 奖学金申请mapper
 */
@Mapper
public interface ApplicationMapper {

    Page<Application> pageQuery(ApplicationPageQueryDTO applicationPageQueryDTO);
}
