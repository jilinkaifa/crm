package com.pandawork.crm.service.event.template.impl;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import com.pandawork.crm.common.dto.event.EventSearchDto;
import com.pandawork.crm.common.entity.event.Event;
import com.pandawork.crm.common.enums.event.EventApprovalStatusEnums;
import com.pandawork.crm.common.enums.event.EventLevelEnums;
import com.pandawork.crm.common.enums.event.EventTypeEnums;
import com.pandawork.crm.common.exception.CrmException;
import com.pandawork.crm.mapper.event.TemplateMapper;
import com.pandawork.crm.service.event.template.TemplateService;
import com.pandawork.crm.service.party.member.MemberGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * TemplateServiceImpl
 *
 * @author Flying
 * @date 2017/8/2 15:04
 */
@Service("templateService")
public class TemplateServiceImpl implements TemplateService{

    @Autowired
    @Qualifier("commonDao")
    private CommonDao commonDao;

    @Autowired
    private TemplateMapper templateMapper;

    @Autowired
    private MemberGroupService memberGroupService;

    @Override
    public List<Event> listTemplateByEventSearchDto(EventSearchDto eventSearchDto) throws SSException {
        try {
            return templateMapper.listTemplateByEventSearchDto(eventSearchDto);
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryBySearchDtoFail, e);
        }
    }

    @Override
    public int countTemplateByEventSearchDto(EventSearchDto eventSearchDto)throws SSException{
        Integer count = 0;
        try{
            count = templateMapper.countTemplateByEventSearchDto(eventSearchDto);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.CountBySearchDtoFail, e);
        }
        return count == null ? 0 : count;
    }

    @Override
    public boolean checkTemplateNameIsExit(String name) throws SSException{
        int num = 0;
        try{
            num = templateMapper.countByName(name);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.TemplateNameIsExist, e);
        }
        return num > 0 ;
    }

    @Override
    public Event queryById(int id) throws SSException{
        Event template = new Event();
        try{
            //检查id是否为空
            if(Assert.isNull(id)){
                throw SSException.get(CrmException.EventIdNotNull);
            }
            template = commonDao.queryById(Event.class, id);
            //调用私有方法
            setExtraValue(template);
            return template;
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryTemplateByIdFail, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public Event newTemplate(Event event) throws SSException{
        try {
            return commonDao.insert(event);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.InsertNewTemplateFail, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void delTemplateById(int id) throws SSException{
        try {
            if (Assert.lessOrEqualZero(id)){
                throw SSException.get(CrmException.TemplateIdError);
            }
            templateMapper.delTemplateById(id);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.DelByClientIdFailed, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void updateTemplate(Event event) throws SSException{
        try {
            commonDao.update(event);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.UpdateEventFail, e);
        }
    }

    /**
     * 私有方法
     * 处理枚举字段的value值
     *
     * @param template
     * @throws Exception
     */
    private void setExtraValue(Event template)throws Exception{
        try{
            if(Assert.isNotNull(template)){
                //设置等级
                if (Assert.isNotNull(template.getLevel())){
                    template.setLevelValue(EventLevelEnums.valueOf(template.getLevel()).getLevel());
                }
                //设置类型
                if (Assert.isNotNull(template.getType())){
                    template.setTypeValue(EventTypeEnums.valueOf(template.getType()).getType());
                }
                //设置活动人员
                if (Assert.isNotNull(template.getMemberGroupId())){
                    template.setMemberGroupName(memberGroupService.queryById(template.getMemberGroupId()).getName());
                }
                //设置活动审批状态
                if (Assert.isNotNull(template.getApprovalStatus())){
                    template.setApprovalStatusValue(EventApprovalStatusEnums.valueOf(template.getApprovalStatus()).getStatus());
                }
            }
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.SetExtraDataFail, e);
        }

    }

}
