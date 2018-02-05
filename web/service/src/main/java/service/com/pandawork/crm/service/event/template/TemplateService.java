package com.pandawork.crm.service.event.template;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.crm.common.dto.event.EventSearchDto;
import com.pandawork.crm.common.entity.event.Event;

import java.util.List;

/**
 * TemplateService
 * 模板管理Service
 *
 * @author Flying
 * @date 2017/8/2 14:47
 */
public interface TemplateService {

    /**
     * 根据EventSearchDto查询
     *
     * @return
     * @throws SSException
     */
    public List<Event> listTemplateByEventSearchDto(EventSearchDto eventSearchDto) throws SSException;

    /**
     * 根据eventSearchDto查询数量
     *
     * @param eventSearchDto
     * @return
     * @throws SSException
     */
    public int countTemplateByEventSearchDto(EventSearchDto eventSearchDto)throws SSException;

    /**
     * 检查模板名称是否重复
     *
     * @param name
     * @return
     * @throws SSException
     */
    public boolean checkTemplateNameIsExit(String name) throws SSException;

    /**
     * 根据id查询模板的详情
     *
     * @param id
     * @return
     * @throws SSException
     */
    public Event queryById(int id) throws SSException;

    /**
     * 新建一个模板
     *
     * @return
     * @throws SSException
     */
    public Event newTemplate(Event event) throws SSException;

    /**
     * 根据id删除模板
     *
     * @param id
     * @throws SSException
     */
    public void delTemplateById(int id) throws SSException;

    /**
     * 修改活动
     *
     * @param event
     * @throws SSException
     */
    public void updateTemplate(Event event) throws SSException;
}
