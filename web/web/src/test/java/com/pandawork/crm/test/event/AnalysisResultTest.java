package com.pandawork.crm.test.event;

import com.pandawork.crm.common.entity.profile.analysis.AnalysisResult;
import com.pandawork.crm.service.profile.analysis.AnalysisResultService;
import com.pandawork.crm.test.AbstractTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * AnalysisResultTest
 *
 * @author Flying
 * @date 2017/8/6 10:03
 */
public class AnalysisResultTest extends AbstractTestCase{

    @Autowired
    private AnalysisResultService analysisResultService;

    @Test
    public void listByMemberGroupId() throws Exception{
        List<AnalysisResult> analysisResult = analysisResultService.listByMemberGroupId(10);
        System.out.println(analysisResult);
    }

    @Test
    public void newAnalysisResult() throws Exception{
        AnalysisResult analysisResult = new AnalysisResult();
        analysisResult.setMemberGroupId(5);
        analysisResult.setClientId(1);
        analysisResult.setCreatedPartyId(1);
        analysisResultService.newAnalysisResult(analysisResult);
    }
}
