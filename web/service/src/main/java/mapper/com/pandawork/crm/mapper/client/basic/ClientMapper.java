1111111package com.pandawork.crm.mapper.client.basic;

import com.pandawork.crm.common.dto.client.basic.ClientSearchDto;
import com.pandawork.crm.common.dto.client.member.MemberDto;
import com.pandawork.crm.common.dto.client.member.MemberSearchDto;
import com.pandawork.crm.common.entity.client.basic.Client;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ClientMapper
 * Created by shura
 * Date:  2017/7/20.
 * Time:  10:38
 */
public interface ClientMapper {

    /**
     * 计算相同身份证号个数
     *
     * @param idCardNum
     * @return
     * @throws Exception
     */
    public Integer countIdCardNum(@Param("idCardNum") String idCardNum) throws Exception;

    /**
     * 加入会员
     *
     * @param id
     * @param isMember
     * @throws Exception
     */
    public void addMember(@Param("id") Integer id, @Param("isMember") Integer isMember) throws Exception;

    /**
     * 根据id删除患者信息
     *
     * @param id
     * @throws Exception
     */
    public void delClient(@Param("id") Integer id, @Param("deleted") Integer deleted)throws Exception;

    /**
     * 根据SearchDto查询患者信息
     * @param searchDto
     * @return
     * @throws Exception
     */
    public List<Client> listBySearchDto(@Param("searchDto") ClientSearchDto searchDto)throws Exception;

    /**
     * 模糊查询患者搜索框返回备选值
     * @param searchDto
     * @return
     * @throws Exception
     */
    public List<Client> listForSearch(@Param("searchDto") ClientSearchDto searchDto)throws Exception;

    /**
     * 会员模糊搜索查询搜索框返回备选值
     *
     * @param searchDto
     * @return
     * @throws Exception
     */
    public List<Client> listMemberForSearch(@Param("searchDto") ClientSearchDto searchDto)throws Exception;

    /**
     * 获取全部患者信息分页
     * @param offset
     * @param pageSize
     * @return
     * @throws Exception
     */
    public List<Client> listByPage(@Param("offset") int offset, @Param("pageSize") int pageSize) throws Exception;

    /**
     * 根据id查询患者
     * @param id
     * @return
     * @throws Exception
     */
    public Client queryById(@Param("id") Integer id)throws Exception;

    /**
     * 根据会员组id查询会员列表
     * @param id
     * @return
     * @throws Exception
     */
    public List<Client> listByMemberGroupId(@Param("id") Integer id)throws Exception;

    /**
     * 条件统计所有患者信息
     * @return
     * @throws Exception
     */
    public Integer count(@Param("searchDto") ClientSearchDto searchDto)throws Exception;

    /**
     * 根据SearchDto查询会员信息
     * @param searchDto
     * @return
     * @throws Exception
     */
    public List<Client> listMemberBySearchDto(@Param("searchDto") MemberSearchDto searchDto) throws Exception;

    /**
     * 修改会员状态
     * @param id
     * @param memberStatus
     * @throws Exception
     */
    public void updateMemberStatus(@Param("id") Integer id, @Param("memberStatus") Integer memberStatus)throws Exception;

    /**
     * 根据组名统计会员组会员数量
     *
     * @param id
     * @throws Exception
     */
    public Integer countByGroupId(@Param("id") Integer id)throws Exception;

    /***
     * 条件统计会员组会员数量
     *
     * @throws Exception
     */
    public Integer countMemberBySearchDto(@Param("searchDto") MemberSearchDto searchDto)throws Exception;

    /**
     * 修改会员信息
     *
     * @param memberDto
     * @throws Exception
     */
    public void updateMember(@Param("memberDto") MemberDto memberDto)throws Exception;

    /**
     * 统计相同会员档案号数量
     *
     * @param id
     * @return
     * @throws Exception
     */
    public Integer countRecordId(@Param("id")String id)throws Exception;
}
