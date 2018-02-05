package com.pandawork.crm.common.shiro;


import com.pandawork.core.common.util.Assert;
import org.apache.shiro.web.tags.PermissionTag;

/**
 * Created by Lionel on 2014/8/6.
 */
public class HasPermissionTag extends PermissionTag {
    public HasPermissionTag() {
    }

    protected boolean showTagBody(String p) {
        return checkPermission(p);
    }

    protected boolean checkPermission(String p) {
        if(Assert.isNull(getSubject())){
                return false;
        }

        if(!getSubject().isPermitted(new ViewWildcardPermission(p))) {
            return false;
        }

        return true;
    }

}
