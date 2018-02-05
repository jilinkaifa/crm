/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.pandawork.crm.common.shiro;

import com.pandawork.core.common.log.LogClerk;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.WildcardPermission;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

/**
 * 对implies
 */
public class ViewWildcardPermission extends WildcardPermission {

    protected ViewWildcardPermission() {
    }

    public ViewWildcardPermission(String wildcardString) {
        super(wildcardString, DEFAULT_CASE_SENSITIVE);
    }

    public boolean implies(Permission p) {
        // By default only supports comparisons with other WildcardPermissions
        if (!(p instanceof WildcardPermission)) {
            return false;
        }

        WildcardPermission wp = (WildcardPermission) p;
        List<Set<String>> otherParts = null;
        try {
            Field f = WildcardPermission.class.getDeclaredField("parts");
            f.setAccessible(true);
            otherParts = (List<Set<String>>) f.get(wp);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            return false;
        }

        int i = 0;
        for (Set<String> otherPart : otherParts) {
            // If this permission has less parts than the other permission, everything after the number of parts contained
            // in this permission is automatically implied, so return true
            if (getParts().size() - 1 < i) {
                return true;
            } else {
                Set<String> part = getParts().get(i);
                if(part.contains(WILDCARD_TOKEN)) {
                    // 如果是通配符，默认通过
                    return true;
                } else if (!part.contains(WILDCARD_TOKEN) && !part.containsAll(otherPart)) {
                    return false;
                }
                i++;
            }
        }

        // If this permission has more parts than the other parts, only imply it if all of the other parts are wildcards
        for (; i < getParts().size(); i++) {
            Set<String> part = getParts().get(i);
            if (!part.contains(WILDCARD_TOKEN)) {
                return false;
            }
        }

        return true;
    }
}
