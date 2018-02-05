package com.pandawork.crm.mapper.profile.label;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.crm.common.entity.profile.label.LabelItem;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_EXCLUSIONPeer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * LabelItemMapper
 *
 * @author Daydreamer
 * @date 2017/7/20 8:50
 */
public interface LabelItemMapper {

    /**
     * 查询所有标签项
     *
     * @return
     * @throws SSException
     */
    public List<LabelItem> listAll() throws SSException;

    /**
     * 检查是否有重名的标签类型
     *
     * @param name
     * @return
     * @throws SSException
     */
    public int countByDicLabelName(@Param("name") String name) throws SSException;

    /**
     * 检查是否有重名的标签类型 王俊 20171027
     * @param labelTypeId 标签类型id
     * @param name 标签字典值
     * @return
     * @throws SSException
     */
    public int countByDicLabelNameNew(@Param("labelTypeId") Integer labelTypeId,@Param("name") String name) throws SSException;

    /**
     * 根据标签类型ID查询标签类型下的标签项
     *
     * @param typeId
     * @return
     * @throws SSException
     */
    public List<LabelItem> queryByTypeId(@Param("typeId") Integer typeId) throws SSException;

    /**
     * 查询标签类型下部分标签项的简略信息，
     * 限制显示个数
     *
     * @param typeId
     * @return
     * @throws SSException
     */
    public List<LabelItem> listTextByTypeId(@Param("typeId") int typeId) throws SSException;

    /**
     * 查询所有标签项个数
     *
     * @return
     * @throws SSException
     */
    public int count() throws SSException;

    /**
     * 查询所有未被删除的标签项个数
     *
     * @return
     * @throws SSException
     */
    public int countNotDeleted() throws SSException;

    /**
     * 查询某标签类型下所有标签项
     *
     * @return
     * @throws SSException
     */
    public int countByType(@Param("typeId") int typeId) throws SSException;

    /**
     * 查询某标签类型下所有未被删除的标签项
     *
     * @param typeId
     * @return
     * @throws SSException
     */
    public int countNotDeletedByType(@Param("typeId") int typeId) throws SSException;

    /**
     * 删除标签项
     *
     * @param id
     * @throws SSException
     */
    public void delById(@Param("id") int id) throws SSException;

    /**
     * 将标签类型下的标签项分页
     *
     * @param offset
     * @param pageSize
     * @param typeId
     * @return
     * @throws SSException
     */
    public List<LabelItem> listByPage(@Param("offset") Integer offset,
                                      @Param("pageSize") Integer pageSize,
                                      @Param("typeId") Integer typeId) throws SSException;
}
