package com.pandawork.crm.mapper.profile.action;

        import com.pandawork.core.common.exception.SSException;
        import com.pandawork.crm.common.dto.profile.action.ActionSearchDto;
        import com.pandawork.crm.common.dto.profile.action.CountSearchDto;
        import com.pandawork.crm.common.entity.client.basic.Client;
        import org.apache.ibatis.annotations.Param;

        import java.util.List;

/**
 * ActionAnalyseMapper
 * Author： linjie
 * Date: 2017/8/2
 * Time: 9:51
 */
public interface ActionAnalyseMapper {
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
     *
     * @return
     * @throws SSException
     */
    public List<Client> queryProfessionCountByActionSearchDto() throws SSException;
    /**
     * 查询各用户（无查询条件）
     * @param
     * @return
     * @throws SSException
     */
    public List<Client> queryClient() throws SSException;

    /**
     * 根据条件查询各用户
     * @param actionSearchDto
     * @return
     * @throws SSException
     */
    public List<Client> queryClientByActionSearchDto(@Param("actionSearchDto") ActionSearchDto actionSearchDto) throws SSException;

    /**
     * 查询总数（分页用）
     * @return
     * @throws SSException
     */
    public Integer queryCountAll() throws SSException;
    /**
     * 根据searchDto查询总数（分页用）
     *
     * @param actionSearchDto
     * @return
     * @throws SSException
     */
    public Integer countByActionSearchDto(@Param("actionSearchDto") ActionSearchDto actionSearchDto) throws SSException;

    /**
     * 根据searchDto查询用户类型数目
     * @param actionSearchDto
     * @return
     * @throws SSException
     */
    public List<Client> queryIsMemberCountByActionSearchDto(@Param("actionSearchDto") ActionSearchDto actionSearchDto) throws SSException;

    /**
     * 根据searchDto查询医保类型数目
     * @param actionSearchDto
     * @return
     * @throws SSException
     */
    public List<Client> queryDicMCITypeCountByActionSearchDto(@Param("actionSearchDto") ActionSearchDto actionSearchDto) throws SSException;

    /**
     * 根据searchDto查询行业类型数目
     * @param countSearchDto
     * @return
     * @throws SSException
     */
    public List<Client> queryProfessionCountByCountSearchDto(@Param("countSearchDto") CountSearchDto countSearchDto) throws SSException;

    /**
     * 根据searchDto获取患者信息
     *
     * @param actionSearchDto
     * @return
     * @throws Exception
     */
    public List<Client> listClientBySearchDto(@Param("actionSearchDto") ActionSearchDto actionSearchDto) throws  Exception;

    public List<Client> countClientBySearchDto(@Param("actionSearchDto") ActionSearchDto actionSearchDto) throws  Exception;

    /**
     * 根据searchDto获取患者信息 新 20171103
     *
     * @param actionSearchDto
     * @return
     * @throws Exception
     */
    public List<Client> listClientBySearchDtoNew(@Param("actionSearchDto") ActionSearchDto actionSearchDto) throws Exception;

    /**
     * 根据searchDto获取患者总数 新 20171103
     *
     * @param actionSearchDto
     * @return
     * @throws Exception
     */
    public int countClientBySearchDtoNew(@Param("actionSearchDto") ActionSearchDto actionSearchDto) throws Exception;

  }
