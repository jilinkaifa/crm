package com.pandawork.crm.service.other;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.crm.common.enums.other.SerialNumTemplateEnums;

/**
 * 流水号Service
 *
 * @author: zhangteng
 * @time: 2015/11/11 17:51
 **/
public interface SerialNumService {

    /**
     * 生成流水号
     *
     * @param templateEnums
     * @return
     * @throws SSException
     */
    public String generateSerialNum(SerialNumTemplateEnums templateEnums) throws SSException;
}
