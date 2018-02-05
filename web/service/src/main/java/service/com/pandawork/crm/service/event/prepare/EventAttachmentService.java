package com.pandawork.crm.service.event.prepare;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.framework.web.spring.fileupload.PandaworkMultipartFile;
import com.pandawork.crm.common.entity.event.EventAttachment;

import javax.servlet.http.HttpServletRequest;

/**
 * EventAttachmentService
 * 活动附件Service
 *
 * @author Flying
 * @date 2017/7/30 17:40
 */
public interface EventAttachmentService {

    /**
     * 上传附件
     *
     * @param file
     * @param request
     * @return
     * @throws SSException
     */
    public EventAttachment newAttachment(PandaworkMultipartFile file, HttpServletRequest request) throws SSException;

    /**
     * 添加活动附件
     *
     * @param eventAttachment
     * @return
     * @throws SSException
     */
    public EventAttachment newAttachment(EventAttachment eventAttachment) throws SSException;

    /**
     * 根据附件id获取附件
     *
     * @param attachmentId
     * @return
     * @throws SSException
     */
    public EventAttachment queryById(int attachmentId) throws SSException;
}
