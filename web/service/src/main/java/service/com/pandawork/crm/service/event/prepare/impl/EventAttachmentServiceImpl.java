package com.pandawork.crm.service.event.prepare.impl;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import com.pandawork.core.framework.web.spring.fileupload.PandaworkMultipartFile;
import com.pandawork.crm.common.entity.event.EventAttachment;
import com.pandawork.crm.common.enums.other.FileUploadPathEnums;
import com.pandawork.crm.common.exception.CrmException;
import com.pandawork.crm.service.event.prepare.EventAttachmentService;
import com.pandawork.crm.service.other.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

/**
 * EventAttachmentImpl
 *
 * @author Flying
 * @date 2017/7/30 17:41
 */
@Service("eventAttachmentService")
public class EventAttachmentServiceImpl implements EventAttachmentService {

    @Autowired
    private CommonDao commonDao;

    @Autowired
    private FileUploadService fileUploadService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public EventAttachment newAttachment(PandaworkMultipartFile file, HttpServletRequest request) throws SSException{
        if(file.isEmpty()){
            return null;
        }
        String path = fileUploadService.uploadFile(file, FileUploadPathEnums.EventAttachmentPath);
        EventAttachment eventAttachment = new EventAttachment();
        eventAttachment.setPath(path);
        return this.newAttachment(eventAttachment);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public EventAttachment newAttachment(EventAttachment eventAttachment) throws SSException{
        try {
            if (!checkBeforeSave(eventAttachment)) {
                return null;
            }
            return commonDao.insert(eventAttachment);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.InsertNewAttachmentFail, e);
        }
    }

    @Override
    public EventAttachment queryById(int attachmentId) throws SSException{
        try{
            if(Assert.isNull(attachmentId)){
                throw SSException.get(CrmException.AttachmentIdIsNull);
            }
           return commonDao.queryById(EventAttachment.class,attachmentId);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryAttachmentById, e);
        }
    }

    /**
     * 检查实体及其关键字段是否为空
     *
     * @param eventAttachment
     * @return
     * @throws SSException
     */
    private boolean checkBeforeSave(EventAttachment eventAttachment) throws SSException {
        if (Assert.isNull(eventAttachment)) {
            return false;
        }
        Assert.isNotNull(eventAttachment.getPath(), CrmException.UploadPathNotNull);
        Assert.isNotNull(eventAttachment.getName(), CrmException.UploadNameNotNull);
        return true;
    }

}
