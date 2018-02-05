package com.pandawork.crm.service.party.group.impl;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.framework.dao.CommonDao;
import com.pandawork.crm.common.entity.party.group.Party;
import com.pandawork.crm.common.exception.PartyException;
import com.pandawork.crm.service.party.group.PartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * 当事人Service实现
 *
 * @author: zhangteng
 * @time: 15/10/16 上午9:20
 */
@Service("partyService")
public class PartyServiceImpl implements PartyService {

    @Autowired
    @Qualifier("commonDao")
    private CommonDao commonDao;

    @Override
    public Party newParty(Party party) throws SSException {
        try {
            Party party1 = commonDao.insert(party);
            return party1;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }
    }

    @Override
    public Integer queryPartyTypeById(int id) throws SSException {
        try {
            return commonDao.queryById(Party.class,id).getPartyTypeId();
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }
    }

}
