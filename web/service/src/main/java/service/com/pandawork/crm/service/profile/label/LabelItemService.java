package com.pandawork.crm.service.profile.label;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.crm.common.entity.profile.label.LabelItem;
import java.util.List;

/**
 * LabelItemService
 *
 * @author Daydreamer
 * @date 2017/7/20 8:26
 */

public interface LabelItemService {

    /**
     * 查询所有未被删除的标签项
     *
     * @return
     * @throws SSException
     */
    public List<LabelItem> listAll() throws SSException;

    /**
     * 根据标签类型ID查询类型下所有标签项
     *
     * @param typeId
     * @return
     * @throws SSException
     */
    public List<LabelItem> listByTypeId(int typeId) throws SSException;

    /**
     * 查询标签类型下部分标签项的简略信息，
     * 限制显示个数
     *
     * @param typeId
     * @return
     * @throws SSException
     */
    public List<LabelItem> listTextByTypeId(int typeId) throws SSException;

    /**
     * 新增标签项
     *
     * @param labelItem
     * @return
     * @throws SSException
     */
    public LabelItem newLabelItem(LabelItem labelItem) throws SSException;

    /**
     * 修改标签项
     *
     * @param labelItem
     * @throws SSException
     */
    public void updateLabelItem(LabelItem labelItem) throws SSException;

    /**
     * 删除标签项
     *
     * @param id
     * @throws SSException
     */
    public void delLabelItem(int id) throws SSException;

    /**
     * 查询所有标签项个数
     *
     * @return
     * @throws SSException
     */
    public int count() throws SSException;

    /**
     *查询所有未被删除的标签项个数
     *
     * @return
     * @throws SSException
     */
    public int countNotDeleted() throws SSException;

    /**
     * 查询某标签类型下所有标签项个数
     *
     * @param typeId
     * @return
     * @throws SSException
     */
    public int countByTypeId(int typeId) throws SSException;

    /**
     * 查询某标签类型下所有未被删除的标签项个数
     *
     * @param typeId
     * @return
     * @throws SSException
     */
    public int countNotDeletedByTypeId(int typeId) throws SSException;

    /**
     * 给标签项分页
     *
     * @param pageNo
     * @param pageSize
     * @return
     * @throws SSException
     */
    public List<LabelItem> listByPage(int pageNo,
                                      int pageSize,
                                      int typeId) throws SSException;

    /**
     * 根据id查询标签项
     *
     * @param id
     * @return
     * @throws SSException
     */
    public LabelItem queryById(int id) throws SSException;
}
