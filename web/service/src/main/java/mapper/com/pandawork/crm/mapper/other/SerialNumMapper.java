package com.pandawork.crm.mapper.other;

import org.apache.ibatis.annotations.Param;

/**
 * 流水号Mapper
 *
 * @author: zhangteng
 * @time: 2015/11/11 15:46
 **/
public interface SerialNumMapper {

    /**
     * 获取流水号
     *
     * @return
     * @throws Exception
     */
    public int querySerialNum(@Param("serialKey") String serialKey) throws Exception;
}
