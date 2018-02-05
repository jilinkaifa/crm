package com.pandawork.crm.service.profile.analysis;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.crm.common.entity.event.Event;
import com.pandawork.crm.common.entity.profile.analysis.AnalysisResult;

import java.util.List;

/**
 * AnalysisResultService
 * 行为分析结果Service
 *
 * @author Flying
 * @date 2017/8/5 21:48
 */
public interface AnalysisResultService {

    /**
     * 根据行为分析组id查询所有结果
     *
     * @param memberGroupId
     * @return
     * @throws SSException
     */
    public List<AnalysisResult> listByMemberGroupId(int memberGroupId) throws SSException;

    /**
     * 新增行为分析结果
     *
     * @param analysisResult
     * @return
     * @throws SSException
     */
    public AnalysisResult newAnalysisResult(AnalysisResult analysisResult)throws SSException;
}
