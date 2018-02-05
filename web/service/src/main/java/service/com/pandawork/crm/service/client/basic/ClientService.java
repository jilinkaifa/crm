package com.pandawork.crm.service.client.basic;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.crm.common.dto.client.basic.ClientSearchDto;
import com.pandawork.crm.common.entity.client.basic.Client;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * ClientService
 * Created by shura
 * Date:  2017/7/19.
 * Time:  17:42
 */
public interface ClientService {

    /**
     *  添加患者信息
     * @param client
     * @return
     * @throws SSException
     */
    public Client newClient(Client client) throws SSException;

    /**
     * 根据id查询患者
     * @param id
     * @return
     * @throws SSException
     */
    public Client queryById(int id)throws SSException;

    /**
     * 根据会员组id查询会员列表
     * @param id
     * @return
     * @throws SSException
     */
    public List<Client> listByMemberGroupId(int id)throws SSException;

    /**
     * 获取全部患者信息分页
     * @param offset
     * @param pageSize
     * @return
     * @throws SSException
     */
    public List<Client> listByPage(int offset, int pageSize) throws SSException;

    /**
     *  模糊查询搜索框返回备选值
     * @param searchDto
     * @return
     * @throws SSException
     */
    public List<Client> listForSearch(ClientSearchDto searchDto) throws SSException;

    /**
     *  修改患者信息
     *
     * @throws SSException
     */
    public void updateClient(Client client) throws SSException;

    /**
     * 删除患者
     *
     * @param id
     * @throws SSException
     */
    public void delClient(int id) throws SSException;

    /**
     * 校验身份证号重复
     * @param name
     * @return
     * @throws SSException
     */
    public int checkIdCardNumIsExist(String name) throws SSException;

    /**
     * 加入会员
     *
     * @throws SSException
     */
    public void addMember(int id) throws SSException;

    /**
     * 根据searchDto查询数据
     *
     * @param searchDto
     * @return
     * @throws SSException
     */
    public List<Client> listBySearchDto(ClientSearchDto searchDto)throws SSException;

    /**
     * 统计所有患者信息
     *
     * @return
     * @throws SSException
     */
    public int countBySearchDto(ClientSearchDto searchDto)throws SSException;

    /**
     * 更新问卷次数
     *
     * @param id
     * @throws SSException
     */
    public void updateQuestCount(int id)throws SSException;

    /**
     * 更新累计消费金额
     *
     * @param id
     * @throws SSException
     */
    public void updateAllCost(int id)throws SSException;

    /**
     * 更新累计积分和当前积分
     *
     * @param id
     * @throws SSException
     */
    public void updateAllPointsAndMemberPoints(int id)throws SSException;

    /**
     * 下载模板
     *
     * @param response
     * @throws SSException
     */
    public void downLoadTemplate(HttpServletResponse response)throws SSException;
}
