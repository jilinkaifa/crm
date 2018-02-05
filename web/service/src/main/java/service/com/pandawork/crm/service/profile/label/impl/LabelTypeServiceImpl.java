package com.pandawork.crm.service.profile.label.impl;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import com.pandawork.crm.common.entity.profile.label.LabelType;
import com.pandawork.crm.common.exception.CrmException;
import com.pandawork.crm.mapper.profile.label.LabelTypeMapper;
import com.pandawork.crm.service.profile.label.LabelTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * LabelServiceImpl
 * Author： Daydreamer
 * Date: 2017/7/18
 * Time: 15:07
 */

@Service("labelTypeService")
public class LabelTypeServiceImpl implements LabelTypeService{

    @Autowired
    @Qualifier("commonDao")
    private CommonDao commonDao;

    @Autowired
    private LabelTypeMapper labelTypeMapper;

    @Override
    public List<LabelType> listAll() throws SSException {
        try {
            return labelTypeMapper.listAll();
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryLabelTypeFail, e);
        }
    }

    @Override
    public List<LabelType> listAllAsc() throws SSException{
        try {
            return labelTypeMapper.listAllAsc();
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryLabelTypeFail, e);
        }
    }

    @Override
    public LabelType queryById(int id)throws SSException{
        try{
            return commonDao.queryById(LabelType.class, id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<LabelType> queryByKeyWord(String key, int pageNo, int pageSize) throws SSException{
        int offset = 0;
        if (Assert.isNotNull(pageNo)) {
            pageNo = pageNo <= 0 ? 0 : pageNo - 1;
            offset = pageNo * pageSize;
        }
//        if (Assert.isNull(key)){
//            try{
//                return labelTypeMapper.listByPage(offset, pageSize);
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//        if (Assert.lessZero(offset)){
//            return Collections.emptyList();
//        }
//        List<LabelType> labelTypes = Collections.emptyList();
        try{
            return labelTypeMapper.queryByKeyWord(key, offset, pageSize);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryLabelTypeFail, e);
        }
    }

    /**
     * 根据输入的关键字查询所有相关的标签类型 总条数
     *
     * @param key
     * @return
     * @throws SSException
     */
    @Override
    public int countByKeyWord(String key) throws SSException{
        Integer num = 0;
        try {
            num = labelTypeMapper.countByKeyWord(key);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryLabelTypeFail,e);
        }
        return num == null ? 0 : num;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public LabelType newLabelType(LabelType labelType) throws SSException{
        try{
            if (!checkBeforeSave(labelType)){
                return null;
            }
            return commonDao.insert(labelType);
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.InsertLabelTypeFail, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void delById(int id) throws SSException{
        if (Assert.lessOrEqualZero(id)){
            return ;
        }
        try{
            labelTypeMapper.delLabelType(id);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.DeleteLabelTypeFail, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void updateLabelType(LabelType labelType) throws SSException{
        if (Assert.isNull(labelType)){
            return ;
        }
        if (Assert.lessOrEqualZero(labelType.getId())){
            throw SSException.get(CrmException.LabelTypeIdError);
        }
        try{
            commonDao.update(labelType);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.UpdateLabelTypeFail,e);
        }
    }

    @Override
    public boolean labelTypeUsed(int id) throws SSException{
        try {
            //profile表中查到该标签类型在被使用，返回false给前台
            if (labelTypeMapper.labelTypeUsed(id) != 0) {
                return false;
            }else {
                return true;
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.DeleteLabelTypeFail);
        }
    }

    @Override
    public List<LabelType> listByPage(int pageNo,
                                      int pageSize) throws SSException{
        pageNo = pageNo <= 0 ? 0 : pageNo - 1;
        int offset = pageNo * pageSize;
        if (Assert.lessZero(offset)){
            return Collections.emptyList();
        }
        List<LabelType> labelTypes = Collections.emptyList();
        try{
            labelTypes = labelTypeMapper.listByPage(offset, pageSize);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryLabelTypeFail,e);
        }return labelTypes;
    }

    @Override
    public int count(String key) throws SSException{
        Integer num = 0;
        try {
            num = labelTypeMapper.count(key);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryLabelTypeFail,e);
        }
        return num == null ? 0 : num;
    }

    @Override
    public LabelType queryByName(String name) throws SSException{
        try {
            if (Assert.isNull(name)){
                throw SSException.get(CrmException.LabelItemNameNotNull);
            }
            return labelTypeMapper.queryByName(name);
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryLabelTypeFail,e);
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
    private boolean queryLabelTypeIsExist(String name) throws SSException{
        int num = 0;
        try {
            num = labelTypeMapper.countByLabelTypeName(name);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryLabelTypeByNameFail,e);
        }
        return num > 0;
    }

    private boolean checkBeforeSave(LabelType labelType) throws SSException{
        //非空检查
        if(Assert.isNull(labelType)){
            return false;
        }
        Assert.isNotNull(labelType.getName(), CrmException.LabelTypeNameNotNull);
        //检查是否重名
        if (this.queryLabelTypeIsExist(labelType.getName())){
            throw SSException.get(CrmException.LabelTypeNameIsExist);
        }
        return true;
    }

    /**
     * 查询存在标签项的标签类型
     *
     * @return
     * @throws SSException
     */
    public List<LabelType> listExistLabelItem() throws SSException{
        try {
            return labelTypeMapper.listExistLabelItem();
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryLabelTypeFail, e);
        }
    }
}
