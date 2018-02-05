package com.pandawork.crm.service.party.dictionary.impl;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import com.pandawork.crm.common.dto.dictionary.DictionaryDto;
import com.pandawork.crm.common.entity.party.dictionary.Dictionary;
import com.pandawork.crm.common.enums.DeletedEnums;
import com.pandawork.crm.common.exception.PartyException;
import com.pandawork.crm.mapper.party.dictionary.DictionaryMapper;
import com.pandawork.crm.service.party.dictionary.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * DictionaryServiceImpl字典值实现类
 * Author： shura
 * Date: 2017/7/15
 * Time: 17:00
 */
@Service("dictionaryService")
public class DictionaryServiceImpl implements DictionaryService{

    @Autowired
    private DictionaryMapper dictionaryMapper;

    @Autowired
    @Qualifier("commonDao")
    private CommonDao commonDao;//core包

    /**
     * 新增字典值
     *
     * @param dictionary
     * @return
     * @throws SSException
     */
    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class,SSException.class},propagation = Propagation.REQUIRED)
    public Dictionary newDictionary(Dictionary dictionary) throws SSException{
        try{
            //实体信息校验
            if(Assert.isNull(dictionary.getName())){
                throw SSException.get(PartyException.DictionaryNameIsNull);
            }
            if(Assert.isNull(dictionary.getParentId())|| dictionary.getParentId()<0){
                throw SSException.get(PartyException.DictionaryIdError);
            }
            commonDao.insert(dictionary);
            return dictionary;
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.NewDictionaryFail, e);
        }
    }

    /**
     * 根据Id查询字典值
     *
     * @param id
     * @return
     * @throws SSException
     */
    @Override
    public Dictionary queryById(int id) throws SSException{
        try{
            if(Assert.isNull(id)||Assert.lessOrEqualZero(id)){
                throw SSException.get(PartyException.DictionaryIdError);
            }
            Dictionary dictionary = dictionaryMapper.queryById(id);
            return dictionary;
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.QueryDictionaryFail, e);
        }
    }

    /**
     * 根据父类Id查询子类字典值列表
     *
     * @return
     * @throws SSException
     */
    @Override
    public List<Dictionary> listByPId(int pId) throws SSException{
        try{
            List<Dictionary> dictionaryList = new ArrayList<Dictionary>();
            if(Assert.isNull(pId) || pId < 0){
                throw SSException.get(PartyException.DictionaryIdError);
            }
            dictionaryList = dictionaryMapper.listByPId(pId);
            return dictionaryList;
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.ListDictionaryFail, e);
        }
    }

    /**
     * 删除字典值
     *
     * @param id
     * @throws SSException
     */
    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class,SSException.class},propagation = Propagation.REQUIRED)
    public void delDictionary(int id) throws SSException{
        try{
            if(Assert.isNull(id)||Assert.lessOrEqualZero(id)){
                throw SSException.get(PartyException.DictionaryIdError);
            }
            //TODO:调用其他service的是否被使用的方法进行校验
            Dictionary dictionary = queryById(id);
            //判断字典值是否为系统配置不可删除
            if(dictionary.getDeleted() == 2){
                throw SSException.get(PartyException.DictionaryIsSystem);
            }
            //判断为父类字典不可删除
            if(this.isParent(id)){
                throw SSException.get(PartyException.DictionaryIsParent);
            }
            dictionaryMapper.delDictionary(id, DeletedEnums.Deleted.getId());
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.DelDictionaryFail, e);
        }

    }

    /**
     * 修改字典值
     *
     * @param dictionary
     * @return
     * @throws SSException
     */
    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class,SSException.class},propagation = Propagation.REQUIRED)
    public void updateDictionary(Dictionary dictionary) throws SSException{
        String defaultValue = "";
        try{
            //实体信息校验
            if(Assert.isNull(dictionary.getName())){
                throw SSException.get(PartyException.DictionaryNameIsNull);
            }
            if(Assert.isNull(dictionary.getParentId()) || dictionary.getParentId()<0){
                throw SSException.get(PartyException.DictionaryIdError);
            }
            //查看父类下子列表中是否已经存在
            int count  = dictionaryMapper.countDictionaryByPIdAndName(dictionary.getParentId(),dictionary.getName());
            String name = dictionary.getName();
            if(name.substring(0,name.length()-1).equals("new node")){
                if (count > 1) {
                    throw SSException.get(PartyException.DictionaryNameIsExist);
                }
            }else{
                if (count > 0) {
                    throw SSException.get(PartyException.DictionaryNameIsExist);
                }
            }
            //执行修改操作
            commonDao.update(dictionary);
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.UpdateDictionaryFail, e);
        }
    }

    /**
     * 根据父类Id和名称查询字典值
     *
     * @param pId
     * @param name
     * @return
     * @throws SSException
     */
    @Override
    public Dictionary queryDictionaryByPIdAndName(int pId,String name) throws SSException{
        try{
            if(Assert.isNull(name)){
                throw SSException.get(PartyException.DictionaryNameIsNull);
            }
            if(Assert.isNull(pId) || pId < 0){
                throw SSException.get(PartyException.DictionaryIdError);
            }
            return dictionaryMapper.queryDictionaryByPIdAndName(pId,name);
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.UpdateDictionaryFail, e);
        }
    }
    /**
     * 查询所有字典值组成Dto
     *
     * @return
     * @throws SSException
     */
    public List<DictionaryDto> listAll() throws SSException{
        try{
            //初始化顶级父类Dto
            List<DictionaryDto> parentDicDtoList = new ArrayList<DictionaryDto>();
            List<Dictionary> parentDicList = dictionaryMapper.listByPId(0);
            DictionaryDto parentDicDto = new DictionaryDto();
            //为父类Dto添加dictionary
            for(Dictionary dictionary : parentDicList){
                parentDicDto.setDictionary(dictionary);
                parentDicDtoList.add(parentDicDto);
                if(isParent(dictionary.getId())){
                    addChildDicDto(parentDicDto);
                }
            }
            return parentDicDtoList;
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.ListAllDictionaryFail, e);
        }
    }

    /**
     * 字典值为父类则为其Dto列表（递归方法）
     *
     * @param parentDicDto
     * @throws SSException
     */
    private void addChildDicDto(DictionaryDto parentDicDto) throws SSException {
          try {
              List<DictionaryDto> childDicDtoList = new ArrayList<DictionaryDto>();
              DictionaryDto childDicDto = new DictionaryDto();
              List<Dictionary> dictionaryList = listByPId(parentDicDto.getDictionary().getId());
              for (Dictionary dictionary : dictionaryList) {
                  childDicDto.setDictionary(dictionary);
                  childDicDtoList.add(childDicDto);
                  if (isParent(dictionary.getId())) {
                      addChildDicDto(childDicDto);
                  }
              }
              parentDicDto.setDictionaryList(childDicDtoList);
          }catch(Exception e){
              LogClerk.errLog.error(e);
              throw SSException.get(PartyException.ListAllDictionaryFail, e);
          }
    }

    /**
     * 判断是否为父类
     *
     * @param pId
     * @return
     * @throws SSException
     */
    @Override
    public boolean isParent(int pId) throws SSException {
        try{
            if(Assert.isNull(pId) || pId < 0){
                throw SSException.get(PartyException.DictionaryIdError);
            }
            if(dictionaryMapper.countParentById(pId) == 0){
                return false;
            }else{
                return true;
            }
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.ListAllDictionaryFail, e);
        }
    }

    /**
     * 模糊查询备选框
     *
     * @param key
     * @param pId
     * @return
     * @throws SSException
     */
    @Override
    public List<Dictionary> listForSearch(String key,int pId)throws SSException{
        try{
            List<Dictionary> dictionaryList = dictionaryMapper.listForSearch(key,pId);
            return dictionaryList;
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.ListAllDictionaryFail, e);
        }
    }

    public List<Dictionary> listAllDictionary()throws SSException{
        try{
            List<Dictionary> dictionaryList = dictionaryMapper.listAll();
            return dictionaryList;
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.ListAllDictionaryFail, e);
        }
    }
}
