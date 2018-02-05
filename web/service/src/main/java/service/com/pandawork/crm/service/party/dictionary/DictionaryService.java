package com.pandawork.crm.service.party.dictionary;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.crm.common.dto.dictionary.DictionaryDto;
import com.pandawork.crm.common.entity.party.dictionary.Dictionary;

import java.util.List;

/**
 * Dictionary字典值Service
 * Author： shura
 * Date: 2017/7/15
 * Time: 16:41
 */
public interface DictionaryService {

    /**
     * 新增字典值
     *
     * @param dictionary
     * @return
     * @throws SSException
     */
    public Dictionary newDictionary(Dictionary dictionary) throws SSException;

    /**
     * 根据id查询字典值
     *
     * @param id
     * @return
     * @throws SSException
     */
    public Dictionary queryById(int id) throws SSException;

    /**
     * 根据父类Id查询子类字典值列表
     *
     * @return
     * @throws SSException
     */
    public List<Dictionary> listByPId(int pId) throws SSException;

    /**
     * 删除字典值
     *
     * @param id
     * @throws SSException
     */
    public void delDictionary(int id) throws SSException;

    /**
     * 修改字典值
     *
     * @param dictionary
     * @return
     * @throws SSException
     */
    public void updateDictionary(Dictionary dictionary) throws SSException;

    /**
     * 根据父类Id和名称查询字典值
     *
     * @param pId
     * @param name
     * @return
     * @throws SSException
     */
    public Dictionary queryDictionaryByPIdAndName(int pId,String name) throws SSException;

    /**
     * 查询所有字典值组成Dto
     *
     * @return
     * @throws SSException
     */
    public List<DictionaryDto> listAll() throws SSException;

    /**
     * 根据id查询作为父类字典值得个数
     *
     * @param pId
     * @return
     * @throws SSException
     */
    public boolean isParent(int pId) throws SSException;

    /**
     * 根据父类id与键值模糊查询
     *
     * @param key
     * @param pId
     * @return
     * @throws SSException
     */
    public List<Dictionary> listForSearch(String key,int pId)throws SSException;

    /**
     * 查询全部字典值数据
     *
     * @return
     * @throws SSException
     */
    public List<Dictionary> listAllDictionary()throws SSException;
}
