package com.pandawork.crm.service.party.login;

import com.pandawork.core.common.exception.SSException;
import org.apache.shiro.subject.Subject;

/**
 * T票存储仓库
 *
 * @author: zhangteng
 * @time: 2015/10/22 14:55
 **/
public interface SubjectStore {

    /**
     * 查找T票
     *
     * @param key
     * @return
     * @throws SSException
     */
    public Subject querySubject(String key) throws SSException;

    /**
     * 保存T票
     *
     * @param key
     * @param value
     * @return
     * @throws SSException
     */
    public void addTGT(String key, Subject value) throws SSException;

    /**
     * 删除T票
     *
     * @param key
     * @return
     * @throws SSException
     */
    public void delTGT(String key) throws SSException;
}
