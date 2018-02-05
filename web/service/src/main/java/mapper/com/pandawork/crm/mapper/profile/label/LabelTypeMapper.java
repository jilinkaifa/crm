package com.pandawork.crm.mapper.profile.label;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.crm.common.entity.party.dictionary.Dictionary;
import com.pandawork.crm.common.entity.profile.label.LabelItem;
import com.pandawork.crm.common.entity.profile.label.LabelType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * LabelTypeMapper
 * Author： Daydreamer
 * Date: 2017/7/18
 * Time: 15:23
 */
public interface LabelTypeMapper {

    /**
     * 获取所有标签类型信息
     *
     * @return
     * @throws Exception
     */
    public List<LabelType> listAll() throws Exception;

    /**
     * 升序获取所有标签类型信息
     *
     * @return
     * @throws Exception
     */
    public List<LabelType> listAllAsc() throws Exception;

    /**
     * 根据输入的关键字查询所有相关的标签类型
     *
     * @param key
     * @return
     * @throws Exception
     */
    public List<LabelType> queryByKeyWord(@Param("key") String key,
                                          @Param("offset") int offset,
                                          @Param("pageSize") int pageSize) throws Exception;

    /**
     * 根据输入的关键字查询所有相关的标签类型 总条数
     *
     * @param key
     * @return
     * @throws Exception
     */
    public int countByKeyWord(@Param("key") String key) throws Exception;

    /**
     * 检查是否有重名的标签类型
     *
     * @param name
     * @return
     * @throws Exception
     */
    public int countByLabelTypeName(@Param("name") String name) throws Exception;

    /**
     * 检查标签类型是否可用
     *
     * @param id
     * @return
     * @throws Exception
     */
    public int labelTypeUsed(@Param("id") Integer id) throws Exception;

    /**
     * 若标签类型未被使用则将deleted设为1（不可用）
     *
     * @param id
     * @throws Exception
     */
    public void delLabelType(@Param("id") Integer id) throws Exception;

    /**
     * 分页
     *
     * @param offset
     * @param pageSize
     * @return
     */
    public List<LabelType> listByPage(@Param("offset") int offset,
                                      @Param("pageSize") int pageSize) throws Exception;

    /**
     * 给搜索得到的标签计数
     *
     * @return
     * @throws SSException
     */
    public int count(@Param("key") String key) throws Exception;

    /**
     * 根据标签类型名称查询
     *
     * @param name
     * @return
     * @throws Exception
     */
    public LabelType queryByName(@Param("name") String name) throws Exception;

    /**
     * 查询存在标签项的标签类型
     *
     * @return
     * @throws Exception
     */
    public List<LabelType> listExistLabelItem() throws Exception;

}
