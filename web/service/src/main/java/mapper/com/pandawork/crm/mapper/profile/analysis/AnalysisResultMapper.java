package com.pandawork.crm.mapper.profile.analysis;

import com.pandawork.crm.common.entity.profile.analysis.AnalysisResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * AnalysisResultMapper
 *
 * @author Flying
 * @date 2017/8/5 22:05
 */
public interface AnalysisResultMapper {

    /**
     * 根据行为分析组id查询
     *
     * @param memberGroupId
     * @return
     * @throws Exception
     */
    public List<AnalysisResult> listByMemberGroupId(@Param("memberGroupId") Integer memberGroupId) throws Exception;
}
