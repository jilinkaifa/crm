package com.pandawork.crm.service.profile.analysis.impl.AnalysisResultServiceImpl;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import com.pandawork.crm.common.entity.profile.analysis.AnalysisResult;
import com.pandawork.crm.common.exception.CrmException;
import com.pandawork.crm.mapper.profile.analysis.AnalysisResultMapper;
import com.pandawork.crm.service.profile.analysis.AnalysisResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * AnalysisResultServiceImpl
 *
 * @author Flying
 * @date 2017/8/5 21:53
 */
@Service("analysisResultService")
public class AnalysisResultServiceImpl implements AnalysisResultService {

    @Autowired
    @Qualifier("commonDao")
    private CommonDao commonDao;

    @Autowired
    private AnalysisResultMapper analysisResultMapper;

    @Override
    public List<AnalysisResult> listByMemberGroupId(int memberGroupId) throws SSException {
        try {
            if (Assert.isNull(memberGroupId)){
                throw SSException.get(CrmException.AnalysisResultIdNotNull);
            }
            return analysisResultMapper.listByMemberGroupId(memberGroupId);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryAnalysisResultFail, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public AnalysisResult newAnalysisResult(AnalysisResult analysisResult)throws SSException{
        try {
            if (Assert.isNull(analysisResult)){
                throw SSException.get(CrmException.AnalysisResultNotNull);
            }
            return commonDao.insert(analysisResult);
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.NewAnalysisResultFail, e);
        }
    }

}
