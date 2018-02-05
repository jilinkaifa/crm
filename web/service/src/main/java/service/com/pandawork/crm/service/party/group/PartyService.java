package com.pandawork.crm.service.party.group;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.crm.common.entity.party.group.Party;

/**
 * 当事人Service
 *
 * @author: zhangteng
 * @time: 15/10/16 上午9:18
 */
public interface PartyService {

    /**
     * 添加当事人
     *
     * @param party
     * @return
     * @throws SSException
     */
    public Party newParty(Party party) throws SSException;

    /**
     * 获取partyType
     * @param id
     * @return
     * @throws SSException
     */
    public Integer queryPartyTypeById(int id) throws SSException;
}
