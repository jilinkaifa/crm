package com.pandawork.crm.mapper.event;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.crm.common.entity.event.EventTerm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * EventTermMapper
 * Author： wychen
 * Date: 2017/8/10
 * Time: 9:51
 */
public interface EventTermMapper {

    /**
     * 获取所有指定状态的活动
     *
     * @return
     * @throws Exception
     */
    public List<EventTerm> listAllByStatus(@Param("status") Integer status) throws Exception;

    /**
     * 根据名称查询前20个活动
     *
     * @param name
     * @return
     * @throws Exception
     */
    public List<EventTerm> searchTOP20ByName(@Param("name") String name) throws Exception;

    /**
     * 获取已经到通知期的活动
     *
     * @return
     * @throws SSException
     */
    public List<EventTerm> getTodayNoticeActivitiesList() throws Exception;

    /**
     * 取在通知期和执行期的活动
     *
     * @return
     * @throws Exception
     */
    public List<EventTerm> listAllByNotificationExecutionActivity() throws Exception;


}
