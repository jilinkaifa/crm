package com.pandawork.crm.mapper.profile.portrayal;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.crm.common.entity.profile.portrayal.Profile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * PortrayalMapper
 * 患者画像Mapper
 *
 * @author Flying
 * @date 2017/7/19 11:00
 */
public interface PortrayalMapper {

    /**
     * 查询是否已经画像
     *
     * @param clientId
     * @param typeId
     * @param itemId
     * @return
     * @throws Exception
     */
    public Integer countProfile(@Param("clientId") Integer clientId,
                            @Param("typeId") Integer typeId,
                            @Param("itemId") Integer itemId) throws Exception;

    /**
     * 根据患者id查询画像信息
     *
     * @param clientId
     * @return
     * @throws Exception
     */
    public List<Profile> queryByClientId(@Param("clientId") Integer clientId) throws Exception;

    /**
     * 根据clientId和typeId查询画像数量
     *
     * @param clientId
     * @param typeId
     * @return
     * @throws Exception
     */
    public Integer countByClientIdAndTypeId(@Param("clientId") Integer clientId,
                                            @Param("typeId") Integer typeId) throws Exception;

    /**
     * 根据clientId和typeId查询画像信息
     *
     * @param clientId
     * @param typeId
     * @return
     * @throws Exception
     */
    public List<String> listProfileByClientIdAndTypeId(@Param("clientId") Integer clientId,
                                                        @Param("typeId") Integer typeId) throws Exception;

    /**
     * 根据clientId和typeId删除画像信息
     *
     * @param clientId
     * @param typeId
     * @throws Exception
     */
    public void delByByClientIdAndTypeId(@Param("clientId") Integer clientId,
                                         @Param("typeId") Integer typeId) throws Exception;

    /**
     * 根据itemId查询画像信息
     *
     * @param itemId
     * @return
     * @throws Exception
     */
    public List<Profile> listByItemId(@Param("itemId") int itemId) throws Exception;


}
