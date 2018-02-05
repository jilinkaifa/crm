package com.pandawork.crm.service.event;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.crm.common.entity.event.CheckItemResult;
import java.util.List;

/**
 * CheckItemResult
 * Author： wychen
 * Date: 2017/7/24
 * Time: 10:31
 */
public interface CheckItemResultService {
    /**
     * 新增检查项结果
     * @param checkItemResult
     * @return
     * @throws SSException
     */
    public CheckItemResult newCheckItemResult(CheckItemResult checkItemResult) throws SSException;

    /**
     * 获取检查项结果列表
     * @return
     * @throws SSException
     */
    public List<CheckItemResult> listAll() throws SSException;

    /**
     *根据活动通知记录id获取检查项结果列表
     * @param eventRecordNoticeId
     * @return
     * @throws SSException
     */
    public List<CheckItemResult> listByEventRecordNoticeId(int eventRecordNoticeId) throws SSException;

    /**
     * 修改检查项结果
     * @param checkItemResult
     * @throws SSException
     */
    public void updateCheckItemResult(CheckItemResult checkItemResult) throws SSException;
}
