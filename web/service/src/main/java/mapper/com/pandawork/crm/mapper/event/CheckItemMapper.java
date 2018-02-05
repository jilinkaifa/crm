package com.pandawork.crm.mapper.event;


import com.pandawork.crm.common.entity.event.CheckItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * CheckItem
 * Author： wychen
 * Date: 2017/7/20
 * Time: 21:10
 */
public interface CheckItemMapper {

    /**
     * 检查项列表显示
     *
     * @return
     * @throws Exception
     */
    public List<CheckItem> listAll() throws Exception;

    /**
     * 根据活动id
     *
     * @param eventId
     * @return
     * @throws Exception
     */
    public List<CheckItem> listByEventId(@Param("eventId") Integer eventId) throws Exception;

    /**
     * 检查名称是否存在
     *
     * @param name
     * @return
     * @throws Exception
     */
    public Integer checkExistByName(@Param("name") String name) throws Exception;

    /**
     * 根据活动id删除检查项
     *
     * @param eventId
     * @throws Exception
     */
    public void deleteByEventId(@Param("eventId") Integer eventId) throws Exception;

}
