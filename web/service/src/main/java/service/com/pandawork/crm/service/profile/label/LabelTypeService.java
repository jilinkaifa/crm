package com.pandawork.crm.service.profile.label;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.crm.common.entity.profile.label.LabelType;

import java.util.List;

/**
 * LabelTypeService
 * Author： Daydreamer
 * Date: 2017/7/18
 * Time: 14:52
 */
public interface LabelTypeService {

    /**
     * 获取所有标签类型信息
     *
     * @return
     * @throws SSException
     */
    public List<LabelType> listAll() throws SSException;

    /**
     * 查询存在标签项的标签类型
     *
     * @return
     * @throws SSException
     */
    public List<LabelType> listExistLabelItem() throws SSException;

    /**
     * 升序获取所有标签类型信息
     *
     * @return
     * @throws SSException
     */
    public List<LabelType> listAllAsc() throws SSException;

    /**
     * 根据id获取标签类型名称
     *
     * @param id
     * @return
     * @throws SSException
     */
    public LabelType queryById(int id) throws SSException;

    /**
     * 根据输入的关键字查询所有相关的标签类型
     *
     * @return
     * @throws SSException
     */
    public List<LabelType> queryByKeyWord(String key,
                                          int pageNo,
                                          int pageSize) throws SSException;

    /**
     * 根据输入的关键字查询所有相关的标签类型 总条数
     *
     * @return
     * @throws SSException
     */
    public int countByKeyWord(String key) throws SSException;

    /**
     * 添加新的标签类型
     *
     * @param labelType
     * @return
     * @throws SSException
     */
    public LabelType newLabelType(LabelType labelType) throws SSException;

    /**
     * 根据标签类型id删除标签类型记录
     *
     * @param id
     * @throws SSException
     */
    public void delById(int id) throws SSException;

    /**
     * 检查标签类型是否正在被使用，正被使用则不删除
     *
     * @param id
     * @return
     * @throws SSException
     */
    public boolean labelTypeUsed(int id) throws SSException;

    /**
     * 修改标签类型
     *
     * @param labelType
     * @throws SSException
     */
    public void updateLabelType(LabelType labelType) throws SSException;

    /**
     * 给标签分页
     *
     * @param pageNo
     * @param pageSize
     * @return
     * @throws SSException
     */
    public List<LabelType> listByPage(int pageNo,
                                      int pageSize) throws SSException;

    /**
     * 给根据关键词查到的标签计数
     *
     * @return
     * @throws SSException
     */
    public int count(String key) throws SSException;

    /**
     * 根据标签类型名称查询
     *
     * @param name
     * @return
     * @throws SSException
     */
    public LabelType queryByName(String  name) throws SSException;

}
