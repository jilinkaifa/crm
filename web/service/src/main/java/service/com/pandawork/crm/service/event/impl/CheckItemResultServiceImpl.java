package com.pandawork.crm.service.event.impl;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import com.pandawork.crm.common.entity.event.CheckItemResult;
import com.pandawork.crm.common.exception.CrmException;
import com.pandawork.crm.mapper.event.CheckItemResultMapper;
import com.pandawork.crm.service.event.CheckItemResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * CheckItemResultServiceImpl
 * Author： wychen
 * Date: 2017/7/24
 * Time: 10:31
 */
@Service("checkItemResultService")
public class CheckItemResultServiceImpl implements CheckItemResultService{

    @Autowired
    @Qualifier("commonDao")
    private CommonDao commonDao;

    @Autowired
    private CheckItemResultMapper checkItemResultMapper;

    /**
     *新增检查项结果
     * @param checkItemResult
     * @return
     * @throws SSException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public CheckItemResult newCheckItemResult(CheckItemResult checkItemResult) throws SSException{
        try{
            //实体非空信息校验
            if(Assert.isNull(checkItemResult.getEventRecordNoticeId())){
                throw SSException.get(CrmException.EventRecordNoticeIdIsNull);
            }
            if(Assert.isNull(checkItemResult.getCheckItemId())){
                throw SSException.get(CrmException.CheckItemIdIsNull);
            }
            if(Assert.isNotNull(checkItemResult)){
                Date date = new Date();
                String time= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date1 = null;
                date1 = sdf.parse(time);
                checkItemResult.setCheckTime(date1);
                commonDao.insert(checkItemResult);
            }

        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.NewCheckItemResultFail,e);
        }
        return checkItemResult;
    }

    /**
     * 获取检查项结果列表
     * @return
     * @throws SSException
     */
    @Override
    public List<CheckItemResult> listAll() throws SSException{
        List<CheckItemResult> checkItemResults = Collections.emptyList();
        try{
            checkItemResults = checkItemResultMapper.listAll();
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.ListAllCheckItemResultFail,e);
        }
        return checkItemResults;
    }

    /**
     *根据活动通知记录id获取检查项结果列表
     * @param eventRecordNoticeId
     * @return
     * @throws SSException
     */
    @Override
    public List<CheckItemResult> listByEventRecordNoticeId(int eventRecordNoticeId) throws SSException{
        List<CheckItemResult> checkItemResults = Collections.emptyList();
        try{
            checkItemResults = checkItemResultMapper.listByEventRecordNoticeId(eventRecordNoticeId);
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.ListByEventRecordNoticeIdCheckItemResultFail,e);
        }
        return checkItemResults;
    }

    /**
     *检查项结果修改
     * @param
     * @throws SSException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void updateCheckItemResult(CheckItemResult checkItemResult) throws SSException{

        try{
            if(checkItemResult != null){
                if(Assert.isNull(checkItemResult.getId()) || checkItemResult.getId() < 0){
                    throw SSException.get(CrmException.CheckItemResultIdError);
                }else{
                    Date date = new Date();
                    String time= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date1 = null;
                    date1 = sdf.parse(time);
                    checkItemResult.setCheckTime(date1);
                    checkItemResultMapper.updateCheckItemResult(checkItemResult);

                }
            }

        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.updateCheckItemResultFail,e);
        }
    }
}
