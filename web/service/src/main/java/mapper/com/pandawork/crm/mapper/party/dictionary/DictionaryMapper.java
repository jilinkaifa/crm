package com.pandawork.crm.mapper.party.dictionary;

import com.pandawork.crm.common.entity.party.dictionary.Dictionary;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * DictionaryMapper
 * Author： shura
 * Date: 2017/7/15
 * Time: 18:16
 */
public interface DictionaryMapper {

    /**
     * 根据Id查询字典值
     *
     * @param id
     * @return
     * @throws Exception
     */
    public Dictionary queryById(@Param("id") Integer id) throws Exception;

    /**
     * 根据父类Id查询子类字典值列表
     *
     * @param pId
     * @return
     * @throws Exception
     */
    public List<Dictionary> listByPId(@Param("parentId") Integer pId) throws Exception;

    /**
     * 根据父类id和名称查询字典值
     *
     * @param pId
     * @param name
     * @return
     * @throws Exception
     */
    public Dictionary queryDictionaryByPIdAndName(@Param("parentId") Integer pId ,@Param("name") String name) throws Exception;

    /**
     * 根据父类id和名称查询字典值数量
     *
     * @param pId
     * @param name
     * @return
     * @throws Exception
     */
    public int  countDictionaryByPIdAndName(@Param("parentId") Integer pId ,@Param("name") String name) throws Exception;

    /**
     * 根据id查询作为父类字典值得个数
     *
     * @param id
     * @return
     * @throws Exception
     */
    public Integer countParentById(@Param("id") Integer id)throws Exception;

    /**
     * 修改字典值状态为删除
     *
     * @param id
     * @throws Exception
     */
    public void delDictionary(@Param("id") Integer id,@Param("deleted") Integer deleted) throws Exception;

    /**
     * 根据父类id与键值模糊查询
     *
     * @param key
     * @param pId
     * @return
     * @throws Exception
     */
    public List<Dictionary> listForSearch(@Param("key")String key,@Param("pId")Integer pId)throws Exception;

    /**
     * 获取全部字典值数据
     *
     * @return
     */
    public List<Dictionary> listAll()throws Exception;
}
