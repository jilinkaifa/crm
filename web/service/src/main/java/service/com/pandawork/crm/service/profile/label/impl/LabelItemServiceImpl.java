package com.pandawork.crm.service.profile.label.impl;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import com.pandawork.crm.common.entity.profile.label.LabelItem;
import com.pandawork.crm.common.exception.CrmException;
import com.pandawork.crm.mapper.profile.label.LabelItemMapper;
import com.pandawork.crm.service.profile.label.LabelItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * LabelItemServiceImpl
 *
 * @author Daydreamer
 * @date 2017/7/20 8:43
 */
@Service("labelItemService")
public class LabelItemServiceImpl implements LabelItemService{

    @Autowired
    @Qualifier("commonDao")
    private CommonDao commonDao;

    @Autowired
    private LabelItemMapper labelItemMapper;

    @Override
    public List<LabelItem> listAll() throws SSException{
        List<LabelItem> labelItems = Collections.emptyList();
        try{
            return labelItemMapper.listAll();
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryLabelItemFail, e);
        }
    }

    @Override
    public List<LabelItem> listByTypeId(int typeId) throws SSException{
        List<LabelItem> labelItems = Collections.emptyList();
        if (Assert.lessOrEqualZero(typeId)){
            return null;
        }
        try{
            labelItems = labelItemMapper.queryByTypeId(typeId);
            return labelItems;
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryLabelItemFail, e);
        }
    }

    @Override
    public List<LabelItem> listTextByTypeId(int typeId) throws SSException{
        if (Assert.lessOrEqualZero(typeId)){
            return null;
        }
        try {
            return labelItemMapper.listTextByTypeId(typeId);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryLabelItemTextFail, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public LabelItem newLabelItem(LabelItem labelItem) throws SSException{
        try {
            if (!checkBeforeSave(labelItem)){
                return null;
            }
            return commonDao.insert(labelItem);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.InsertLabelItemFail, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void updateLabelItem(LabelItem labelItem) throws SSException{
        if (Assert.isNull(labelItem)){
            return ;
        }
        if (Assert.lessOrEqualZero(labelItem.getId())){
            throw SSException.get(CrmException.LabelItemIdNotNull);
        }
        try{
            commonDao.update(labelItem);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.UpdateLabelItemFail, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void delLabelItem(int id) throws SSException{
        if (Assert.lessOrEqualZero(id)){
            return ;
        }
        try {
            labelItemMapper.delById(id);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.DeleteLabelItemFail, e);
        }
    }

    @Override
    public int count() throws SSException{
        int num = 0;
        try{
            num = labelItemMapper.count();
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryLabelItemFail, e);
        }return num;
    }

    @Override
    public int countNotDeleted() throws SSException{
        int num = 0;
        try {
            num = labelItemMapper.countNotDeleted();
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryLabelItemFail, e);
        }return num;
    }

    @Override
    public int countByTypeId(int typeId) throws SSException{
        int num = 0;
        if (Assert.lessOrEqualZero(typeId)){
            return 0;
        }
        try{
            num = labelItemMapper.countByType(typeId);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryLabelItemFail, e);
        }
        return num;
    }

    @Override
    public int countNotDeletedByTypeId(int typeId) throws SSException{
        int num = 0;
        if (Assert.lessOrEqualZero(typeId)){
            return 0;
        }
        try{
            num = labelItemMapper.countNotDeletedByType(typeId);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryLabelItemFail, e);
        }
        return num;
    }

    @Override
    public List<LabelItem> listByPage(int pageNo,
                                      int pageSize,
                                      int typeId) throws SSException{
        pageNo = pageNo <= 0 ? 0 : pageNo - 1;
        int offset = pageNo * pageSize;
        if (Assert.lessZero(offset)){
            return Collections.emptyList();
        }
        List<LabelItem> labelItems = Collections.emptyList();
        try{
            labelItems = labelItemMapper.listByPage(offset,
                    pageSize,
                    typeId);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryLabelItemFail, e);
        }
        return labelItems;
    }

    @Override
    public LabelItem queryById(int id) throws SSException{
        try {
            if (Assert.isNull(id)){
                throw SSException.get(CrmException.LabelItemIdError);
            }
            return commonDao.queryById(LabelItem.class, id);
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryLabelItemFail, e);
        }
    }


    //私有方法
    /**
     * 检查是否有重名的标签类型
     *
     * @param name
     * @return
     * @throws SSException
     */
    private boolean queryLabelItemIsExist(String name) throws SSException{
        int num = 0;
        try {
            num = labelItemMapper.countByDicLabelName(name);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryLabelItemByNameFail,e);
        }
        return num > 0;
    }

    /**
     * 检查是否有重名的标签类型 王俊 重载 20171027
     * @param labelTypeId 标签类型id
     * @param name 标签字典值
     * @return
     * @throws SSException
     */
    private boolean queryLabelItemIsExist(Integer labelTypeId,String name) throws SSException{
        int num = 0;
        try {
            //num = labelItemMapper.countByDicLabelName(name);
            num = labelItemMapper.countByDicLabelNameNew(labelTypeId,name);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryLabelItemByNameFail,e);
        }
        return num > 0;
    }

    private boolean checkBeforeSave(LabelItem labelItem) throws SSException{
        //非空检查
        if(Assert.isNull(labelItem)){
            return false;
        }
        Assert.isNotNull(labelItem.getDicLabelName(), CrmException.LabelItemNameNotNull);
        //检查是否重名
//        if (this.queryLabelItemIsExist(labelItem.getDicLabelName())){
//            throw SSException.get(CrmException.QueryLabelItemFail);
//        }
        if (this.queryLabelItemIsExist(labelItem.getLabelTypeId(),labelItem.getDicLabelName())){
            throw SSException.get(CrmException.QueryLabelItemFail);
        }
        return true;
    }
}
