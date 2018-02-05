package com.pandawork.crm.service.party.login.impl;

import com.pandawork.core.common.cache.Cache;
import com.pandawork.core.common.cache.decorators.LRUCache;
import com.pandawork.core.common.cache.decorators.SynchronizedCache;
import com.pandawork.core.common.cache.impl.LocalCache;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.util.Assert;
import com.pandawork.crm.common.exception.PartyException;
import com.pandawork.crm.service.party.login.SubjectStore;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

/**
 * T票存储LRU实现
 *
 * @author: zhangteng
 * @time: 2015/10/22 14:59
 **/
@Service("subjectStoreLRUImpl")
public class SubjectStoreLRUImpl implements SubjectStore {

    private static final int DEFAULT_CACHE_SIZE = 10000;

    private Cache<String, Subject> subjectCache = new LRUCache<String, Subject>(
            new SynchronizedCache(
                    new LocalCache()
            ), DEFAULT_CACHE_SIZE
    );

    @Override
    public Subject querySubject(String key) throws SSException {
        Assert.isNotNull(key, PartyException.SubjectKeyNotNull);
        return subjectCache.getObject(key);
    }

    @Override
    public void addTGT(String key, Subject value) throws SSException {
        Assert.isNotNull(key, PartyException.SubjectKeyNotNull);
        Assert.isNotNull(value, PartyException.SubjectNotNull);

        subjectCache.putObject(key, value);

        return ;
    }

    @Override
    public void delTGT(String key) throws SSException {
        Assert.isNotNull(key, PartyException.SubjectKeyNotNull);

        subjectCache.removeObject(key);

        return ;
    }
}
