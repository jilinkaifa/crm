package com.pandawork.crm.service.party.member.impl;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import com.pandawork.crm.common.entity.party.member.Member;
import com.pandawork.crm.common.exception.PartyException;
import com.pandawork.crm.common.utils.CommonUtil;
import com.pandawork.crm.mapper.party.member.MemberGroupMapper;
import com.pandawork.crm.service.party.member.MemberGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;


/**
 * @author Flying
 * @date 2017/7/16 16:42
 */

@Service("memberGroupService")
public class MemberGroupServiceImpl implements MemberGroupService {

    @Autowired
    @Qualifier("commonDao")
    private CommonDao commonDao;

    @Autowired
    private MemberGroupMapper memberGroupMapper;

    @Override
    public List<Member> listAll() throws SSException{
        try{
            return memberGroupMapper.listAll();
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.MemberGroupQueryFailed,e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public Member newMember(Member member) throws SSException{
        try{
            if(!checkBeforeSave(member)){
                return null;
            }
            return commonDao.insert(member);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.MemberGroupInsertFailed,e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void delById(int id) throws SSException{
        if(Assert.lessOrEqualZero(id)){
            return ;
        }
        try {
            commonDao.deleteById(Member.class, id);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.MemberGroupDeleteFailed,e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void updateMember(Member member) throws SSException{
        try{
            //检查是否为空
            if(Assert.isNull(member)){
                return ;
            }
            //检查id是否为空
            CommonUtil.checkId(member.getId(), PartyException.MemberGroupIdNotNull);
            //检查name是否为空
            Assert.isNotNull(member.getName(), PartyException.MemberGroupNameNotNull);

            Member oldMember = this.queryById(member.getId());
            Assert.isNotNull(oldMember.getName(), PartyException.MemberGroupNameNotNull);
            if(!oldMember.getName().equals(member.getName())){
                //检查修改的会员组名称是否已经存在了
                if(this.queryMemberGroupNameIsExist(member.getName())){
                    throw SSException.get(PartyException.MemberGroupNameIsExist);
                }
            }
            commonDao.update(member);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.MemberGroupUpdateFailed,e);
        }
    }

    @Override
    public Member queryById(int id) throws SSException{
        if(Assert.lessOrEqualZero(id)){
            return null;
        }
        try{
            return commonDao.queryById(Member.class, id);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.MemberGroupQueryFailed,e);
        }
    }

    @Override
    public List<Member> isNotDeleted() throws SSException{
        try{
           return memberGroupMapper.isNotDeleted();
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.MemberGroupQueryIsNotDeleted,e);
        }
    }

    @Override
    public List<Member> listByPage(int pageNo, int pageSize) throws SSException{
        pageNo = pageNo <= 0 ? 0 : pageNo - 1;
        int offset = pageNo * pageSize;
        if(Assert.lessZero(offset)){
            return Collections.emptyList();
        }
        List<Member> memberList = Collections.emptyList();

        try{
            memberList = memberGroupMapper.listByPage(offset , pageSize);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.MemberGroupQueryFailed,e);
        }
        return memberList;
    }

    @Override
    public int count() throws SSException{
        Integer num = 0;
        try{
            num = memberGroupMapper.count();
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.MemberGroupQueryFailed,e);
        }
        return num == null ? 0 : num;
    }

    //私有方法
    /**
     * 检查是否重名
     *
     * @param name
     * @return
     * @throws SSException
     */
    private boolean queryMemberGroupNameIsExist(String name) throws SSException{
        int num = 0;
        try {
            num = memberGroupMapper.countByMemberGroupName(name);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.MemberGroupNameIsExist,e);
        }
        return num > 0 ;
    }
    /**
     * 检查是否为空和重名
     *
     * @param member
     * @return
     * @throws SSException
     */
    public boolean checkBeforeSave(Member member) throws SSException {
        //非空检查
        if (Assert.isNull(member)){
            return false;
        }
        Assert.isNotNull(member.getName(), PartyException.MemberGroupNameNotNull);
        //检查是否重名
        if(this.queryMemberGroupNameIsExist(member.getName())){
            throw SSException.get(PartyException.MemberGroupNameIsExist);
        }
        return true;
    }

}
