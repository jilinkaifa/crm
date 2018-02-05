package com.pandawork.crm.test.profile.portrayal;

import com.pandawork.crm.common.entity.profile.portrayal.Profile;
import com.pandawork.crm.service.profile.portrayal.PortrayalService;
import com.pandawork.crm.test.AbstractTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * PortrayalTest
 *
 * @author Flying
 * @date 2017/7/29 9:37
 */
public class PortrayalTest extends AbstractTestCase{

    @Autowired
    private PortrayalService portrayalService;

    @Test
    public void queryByClientId() throws Exception{
        int clientId = 3;
        List<Profile> profile = portrayalService.queryByClientId(clientId);
        System.out.println(profile);
    }

    @Test
    public void countByClientIdAndTypeId() throws Exception{
        int count = portrayalService.countByClientIdAndTypeId(3, 2);
        System.out.println(count);
    }

    @Test
    public void listProfileByClientIdAndTypeId() throws Exception{
        List<String> profileList = portrayalService.listProfileByClientIdAndTypeId(3, 2);
        String[] array = (String[])profileList.toArray(new String[profileList.size()]);
        String str = org.apache.commons.lang.StringUtils.join(array,",");
        System.out.println(str);
    }

    @Test
    public void batchNewProfile() throws Exception{
        List<Integer> list = new ArrayList<Integer>();
        list.add(4);
        list.add(2);
        portrayalService.batchNewProfile(list, 1, 1, 1);
    }

    @Test
    public void listByItemId() throws Exception{
        List<Profile> profileList = portrayalService.listByItemId(22);
        System.out.println(profileList.size());

    }

}
