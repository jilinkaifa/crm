package com.pandawork.crm.mapper.event;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.crm.common.dto.event.EventSearchDto;
import com.pandawork.crm.common.entity.event.Event;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * TemplateMapper
 *
 * @author Flying
 * @date 2017/8/2 15:53
 */
public interface TemplateMapper {

    /**
     * 根据EventSearchDto查询
     *
     * @return
     * @throws Exception
     */
    public List<Event> listTemplateByEventSearchDto(@Param("eventSearchDto") EventSearchDto eventSearchDto) throws Exception;

    /**
     * 根据eventSearchDto查询数量
     *
     * @param eventSearchDto
     * @return
     * @throws Exception
     */
    public int countTemplateByEventSearchDto(@Param("eventSearchDto") EventSearchDto eventSearchDto) throws Exception;

    /**
     * 根据模板名称查询数量
     *
     * @return
     * @throws Exception
     */
    public int countByName(@Param("name") String name) throws Exception;

    /**
     * 根据模板id删除模板
     *
     * @param id
     * @throws Exception
     */
    public void delTemplateById(@Param("id") int id) throws Exception;
}
