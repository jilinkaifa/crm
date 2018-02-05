package com.pandawork.crm.mapper.event;

import com.pandawork.crm.common.dto.event.processing.CountParticipantDto;
import com.pandawork.crm.common.entity.event.CheckItemResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * OngoingEventMapper
 * Author： wychen
 * Date: 2017/7/21
 * Time: 10:41
 */
public interface CheckItemResultMapper {
    /**
     *检查项结果列表显示
     * @return
     * @throws Exception
     */
    public List<CheckItemResult> listAll() throws Exception;

    /**
     *根据活动通知记录id进行查询
     * @param eventRecordNoticeId
     * @return
     * @throws Exception
     */
    public List<CheckItemResult> listByEventRecordNoticeId(@Param("eventRecordNoticeId") Integer eventRecordNoticeId) throws Exception;

    /**
     * 修改检查项结果
     * @param checkItemResult
     * @throws Exception
     */
    public void updateCheckItemResult(@Param("checkItemResult") CheckItemResult checkItemResult) throws Exception;

    /**
     * 根据活动记录通知id获取患者活动记录信息
     *
     * @param eventRecordNoticeId
     * @return
     * @throws Exception
     */
    public List<CountParticipantDto> queryByEventRecordNoticeId(@Param("eventRecordNoticeId") Integer eventRecordNoticeId) throws Exception;

}
