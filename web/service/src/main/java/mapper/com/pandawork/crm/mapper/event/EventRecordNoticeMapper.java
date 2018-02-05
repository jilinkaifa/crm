package com.pandawork.crm.mapper.event;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.crm.common.entity.event.EventRecordNotice;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * ArchivedEventMapper
 * Author： wychen
 * Date: 2017/7/21
 * Time: 10:40
 */
public interface EventRecordNoticeMapper {

    /**
     * 根据活动id获取活动记录通知列表
     *
     * @param eventTermId
     * @param offset
     * @param pageSize
     * @return
     * @throws Exception
     */
    public List<EventRecordNotice> queryByEventTermId(@Param("eventTermId") Integer eventTermId,
                                                  @Param("participantName") String participantName,
                                                  @Param("participantIdcard") String participantIdcard,
                                                  @Param("offset") Integer offset,
                                                  @Param("pageSize") Integer pageSize) throws Exception;

    /**
     * 获取该活动下的参与人员记录总数目
     *
     * @param eventTermId
     * @return
     * @throws Exception
     */
    public Integer countByEventTermId(@Param("eventTermId") Integer eventTermId,
                                  @Param("participantName") String participantName,
                                  @Param("participantIdcard") String participantIdcard) throws Exception;

    /**
     * 根据每期活动id获取未通知的人员记录列表
     *
     * @param eventTermId
     * @param offset
     * @param pageSize
     * @return
     * @throws Exception
     */
    public List<EventRecordNotice> queryByEventTermIdWithoutNotice(@Param("eventTermId") Integer eventTermId,
                                                               @Param("offset") Integer offset,
                                                               @Param("pageSize") Integer pageSize) throws Exception;

    /**
     * 根据活动id获取未通知的人员记录列表数目
     *
     * @param eventId
     * @return
     * @throws Exception
     */
    public Integer countByEventIdWithoutNotice(@Param("eventId") Integer eventId) throws Exception;

    /**
     * 根据参与人名模糊查询前20条名称
     *
     * @param eventTermId
     * @param participantName
     * @return
     * @throws SSException
     */
    public List<EventRecordNotice> listNameTop20ByEventTermId(@Param("eventTermId") Integer eventTermId,
                                                              @Param("participantName") String participantName) throws SSException;

    /**
     * 根据参与人idcard模糊查询前20条idcard
     *
     * @param eventTermId
     * @param participantIdcard
     * @return
     * @throws SSException
     */
    public List<EventRecordNotice> listIdcardTop20ByEventTermId(@Param("eventTermId") Integer eventTermId,
                                                                @Param("participantIdcard") String participantIdcard) throws SSException;
}
