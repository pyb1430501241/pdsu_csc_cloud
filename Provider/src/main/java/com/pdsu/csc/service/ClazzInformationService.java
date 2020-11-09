package com.pdsu.csc.service;

import com.pdsu.csc.bean.ClazzInformation;
import org.springframework.lang.NonNull;

/**
 * @author 半梦
 * @create 2020-08-21 14:46
 */
@Deprecated
public interface ClazzInformationService {

    /**
     * 添加班级
     * @param clazzInformation
     * @return
     */
    @Deprecated
    public boolean insert(@NonNull ClazzInformation clazzInformation);

}
