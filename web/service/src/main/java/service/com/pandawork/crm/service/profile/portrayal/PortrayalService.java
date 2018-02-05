package com.pandawork.crm.service.profile.portrayal;


import com.pandawork.core.common.exception.SSException;
import com.pandawork.crm.common.entity.profile.portrayal.Profile;

import java.util.List;

/**
 * ProtrayalService
 * 患者画像的Service
 *
 * @author Flying
 * @date 2017/7/19 10:59
 */
public interface PortrayalService {

    /**
     * 新增一个画像
     *
     * @param profile
     * @return
     * @throws SSException
     */
    public Profile newProfile(Profile profile) throws SSException;

    /**
     * 检查画像信息是否已经存在
     *
     * @param clientId
     * @param typeId
     * @param itemId
     * @return
     * @throws SSException
     */
    public boolean queryProfileIsExist(int clientId, int typeId, int itemId) throws SSException;

    /**
     * 判空
     *
     * @param profile
     * @return
     * @throws SSException
     */
    public boolean checkBeforeSave(Profile profile) throws SSException;

    /**
     * 根据患者id查询画像信息
     *
     * @param clientId
     * @return
     * @throws SSException
     */
    public List<Profile> queryByClientId(int clientId) throws SSException;

    /**
     * 根据患者id和类型id查询画像表中的标签项的数量
     *
     * @param clientId
     * @param typeId
     * @return
     * @throws SSException
     */
    public int countByClientIdAndTypeId(int clientId, int typeId) throws SSException;

    /**
     * 根据患者Id和类型Id查询画像信息
     *
     * @param clientId
     * @param typeId
     * @return
     * @throws SSException
     */
    public List<String> listProfileByClientIdAndTypeId(int clientId, int typeId )throws SSException;

    /**
     * 批量删除画像
     *
     * @param clientIdList
     * @param typeId
     * @throws SSException
     */
    public void batchDelProfile(List<Integer> clientIdList, int typeId) throws SSException;

    /**
     * 给患者批量画像
     *
     * @param clientIdList
     * @param typeId
     * @param itemId
     * @throws SSException
     */
    public void batchNewProfile(List<Integer> clientIdList, int typeId, int itemId, int partyId) throws SSException;

    /**
     * 根据标签项id查询所有画像信息
     *
     * @param itemId
     * @return
     * @throws SSException
     */
    public List<Profile> listByItemId(int itemId) throws SSException;

}
