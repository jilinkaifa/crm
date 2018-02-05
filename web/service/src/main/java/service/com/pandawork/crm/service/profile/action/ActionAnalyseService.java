package com.pandawork.crm.service.profile.action;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.crm.common.dto.profile.action.ActionSearchDto;
import com.pandawork.crm.common.entity.client.basic.Client;
import java.util.List;

/**
 * ActionAnalyseService
 * Author： linjie
 * Date: 2017/8/2
 * Time: 10:25
 */
public interface ActionAnalyseService {
    /**
     * 查询各用户类型数量
     * @return
     * @throws SSException
     */
    public List<Client> queryIsMemberCountAll() throws SSException;

    /**
     * 查询各医保类型数量
     * @return
     * @throws SSException
     */
    public List<Client> queryDicMCITypeCountAll() throws SSException;

    /**
     * 查询有哪些性别
     * @return
     * @throws SSException
     */
    public List<Client> queryGenderAll() throws SSException;

    /**
     * 查询有哪些民族
     * @return
     * @throws SSException
     */
    public List<Client> queryDicNationNameAll() throws SSException;

    /**
     * 根据条件查询各用户
     * @param actionSearchDto
     * @return
     * @throws SSException
     */
    public List<Client> queryClientByActionSearchDto( ActionSearchDto actionSearchDto) throws SSException;

    /**
     * 查询各用户（无查询条件）
     * @param
     * @return
     * @throws SSException
     */
    public List<Client> queryClient() throws SSException;
    /**
     * 根据条件查询用户类型数量
     * @param actionSearchDto
     * @return
     * @throws SSException
     */
    public List<Client> queryIsMemberCountByActionSearchDto(ActionSearchDto actionSearchDto) throws SSException;

    /**
     * 根据条件查询医保类型数量
     * @param actionSearchDto
     * @return
     * @throws SSException
     */
    public List<Client> queryDicMCITypeCountByActionSearchDto(ActionSearchDto actionSearchDto)throws SSException;

    /**
     * 查询总条数，分页使用
     * @param
     * @return
     * @throws SSException
     */
    public Integer countByActionSearchDto( ActionSearchDto actionSearchDto) throws SSException;

    /**
     * 查询总条数（无查询条件）
     * @return
     * @throws SSException
     */
    public Integer queryCountAll() throws SSException;

    /**
     * 根据searchDto获取列表信息
     *
     * @param actionSearchDto
     * @return
     * @throws SSException
     */
    public List<Client> listActionListDtoBySearchDto(ActionSearchDto actionSearchDto) throws SSException;

    /**
     * 根据searchDto获取列表信息数量
     *
     * @param actionSearchDto
     * @return
     * @throws SSException
     */
    public int countActionListDtoBySearchDto(ActionSearchDto actionSearchDto) throws SSException;

    /**
     *
     * 根据行为分析选择活动模板
     *
     * @param id
     * @param clientId
     * @throws SSException
     */
    public void selectEventCase(int id,List<Integer> clientId,int partyId) throws SSException;

}
