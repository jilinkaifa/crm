package com.pandawork.crm.common.shiro;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.crm.common.entity.party.security.SecurityGroup;
import com.pandawork.crm.common.entity.party.security.SecurityGroupPermission;
import com.pandawork.crm.common.entity.party.security.SecurityUser;
import com.pandawork.crm.common.enums.party.EnableEnums;
import com.pandawork.crm.service.party.security.SecurityGroupPermissionService;
import com.pandawork.crm.service.party.security.SecurityUserGroupService;
import com.pandawork.crm.service.party.security.SecurityUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.*;
import org.apache.shiro.authz.permission.*;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.util.Initializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * shiro权限数据源
 * Created by lionel on 2014/6/4.
 */
public class ShiroAuthorizingRealm extends AuthenticatingRealm
        implements Authorizer, Initializable, PermissionResolverAware, RolePermissionResolverAware {
    /*-------------------------------------------
    |             C O N S T A N T S             |
    ============================================*/
    private static final Logger log = LoggerFactory.getLogger(AuthorizingRealm.class);

    /**
     * The default suffix appended to the realm name for caching AuthorizationInfo instances.
     */
    private static final String DEFAULT_AUTHORIZATION_CACHE_SUFFIX = ".authorizationCache";

    private static final AtomicInteger INSTANCE_COUNT = new AtomicInteger();

    /*-------------------------------------------
    |    I N S T A N C E   V A R I A B L E S    |
    ============================================*/
    /**
     * The cache used by this realm to store AuthorizationInfo instances associated with individual Subject principals.
     */
    private boolean authorizationCachingEnabled;
    private Cache<Object, AuthorizationInfo> authorizationCache;
    private String authorizationCacheName;

    private PermissionResolver permissionResolver;

    private RolePermissionResolver permissionRoleResolver;

    /*-------------------------------------------
    |         C O N S T R U C T O R S           |
    ============================================*/

    public ShiroAuthorizingRealm() {
        this(null, null);
    }

    public ShiroAuthorizingRealm(CacheManager cacheManager) {
        this(cacheManager, null);
    }

    public ShiroAuthorizingRealm(CredentialsMatcher matcher) {
        this(null, matcher);
    }

    public ShiroAuthorizingRealm(CacheManager cacheManager, CredentialsMatcher matcher) {
        super();
        if (cacheManager != null) setCacheManager(cacheManager);
        if (matcher != null) setCredentialsMatcher(matcher);

        this.authorizationCachingEnabled = true;
        this.permissionResolver = new WildcardPermissionResolver();

        int instanceNumber = INSTANCE_COUNT.getAndIncrement();
        this.authorizationCacheName = getClass().getName() + DEFAULT_AUTHORIZATION_CACHE_SUFFIX;
        if (instanceNumber > 0) {
            this.authorizationCacheName = this.authorizationCacheName + "." + instanceNumber;
        }
    }

    @Autowired
    @Qualifier("securityUserGroupService")
    private SecurityUserGroupService securityUserGroupService;

    @Autowired
    @Qualifier("securityUserService")
    private SecurityUserService securityUserService;

    @Autowired
    @Qualifier("securityGroupPermissionService")
    private SecurityGroupPermissionService securityGroupPermissionService;

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String loginName = (String)principals.getPrimaryPrincipal();
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@loginName="+loginName);

        // 查询安全组列表
        List<SecurityGroup> groupList = null;
        try {
            groupList = securityUserGroupService.listSecurityGroupByLoginName(loginName);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            // 由于这里不支持，因此抛出一个运行时异常
            throw new RuntimeException(e);
        }

        // 获取安全组名称
        Set<String> roleSet = new HashSet<String>();
        for (SecurityGroup tmp : groupList){
            roleSet.add(tmp.getName());
        }

        // 查询所有权限组的权限列表
        List<SecurityGroupPermission> groupPermissionList = new ArrayList<SecurityGroupPermission>();
        List<SecurityGroupPermission> tmpPerList = Collections.emptyList();
        try {
            for (SecurityGroup securityGroup : groupList){
                System.out.println("@@@@@@@@@@@@@@@@@@@@@@securityGroup.getId()="+securityGroup.getId());
                tmpPerList = securityGroupPermissionService.listByGroupId(securityGroup.getId());
                groupPermissionList.addAll(tmpPerList);
            }
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            // 由于这里不支持，因此抛出一个运行时异常
            throw new RuntimeException(e);
        }

        // 获取所有的权限表达式
        Set<String> permissionsSet = new HashSet<String>();
        for(SecurityGroupPermission p: groupPermissionList) {
            permissionsSet.add(p.getPermissionExpression());
        }

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(roleSet);
        authorizationInfo.setStringPermissions(permissionsSet);
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String loginName = (String) token.getPrincipal();
        SecurityUser securityUser = null;
        try {
            securityUser = securityUserService.queryByLoginName(loginName);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            // 由于这里不支持，因此抛出一个运行时异常
            throw new RuntimeException(e);
        }

        if(Assert.isNull(securityUser)) {
            throw new UnknownAccountException();//用户不存在
        } else if (!EnableEnums.Enabled.getId().equals(securityUser.getStatus())) {
            throw new DisabledAccountException();//用户被禁用
        }

        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以在此判断或自定义实现
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                securityUser.getLoginName(), //用户名
                securityUser.getPassword(), //密码
                ByteSource.Util.bytes(Integer.valueOf(securityUser.hashCode()).toString()),//salt=username+salt
                getName() //realm name
        );
        return authenticationInfo;
    }


    /*-------------------------------------------
    |  A C C E S S O R S / M O D I F I E R S    |
    ============================================*/

    public void setName(String name) {
        super.setName(name);
        String authzCacheName = this.authorizationCacheName;
        if (authzCacheName != null && authzCacheName.startsWith(getClass().getName())) {
            //get rid of the default class-name based cache name.  Create a more meaningful one
            //based on the application-unique Realm name:
            this.authorizationCacheName = name + DEFAULT_AUTHORIZATION_CACHE_SUFFIX;
        }
    }

    public void setAuthorizationCache(Cache<Object, AuthorizationInfo> authorizationCache) {
        this.authorizationCache = authorizationCache;
    }

    public Cache<Object, AuthorizationInfo> getAuthorizationCache() {
        return this.authorizationCache;
    }

    public String getAuthorizationCacheName() {
        return authorizationCacheName;
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public void setAuthorizationCacheName(String authorizationCacheName) {
        this.authorizationCacheName = authorizationCacheName;
    }

    /**
     * Returns {@code true} if authorization caching should be utilized if a {@link CacheManager} has been
     * {@link #setCacheManager(CacheManager) configured}, {@code false} otherwise.
     * <p/>
     * The default value is {@code true}.
     *
     * @return {@code true} if authorization caching should be utilized, {@code false} otherwise.
     */
    public boolean isAuthorizationCachingEnabled() {
        return isCachingEnabled() && authorizationCachingEnabled;
    }

    /**
     * Sets whether or not authorization caching should be utilized if a {@link CacheManager} has been
     * {@link #setCacheManager(CacheManager) configured}, {@code false} otherwise.
     * <p/>
     * The default value is {@code true}.
     *
     * @param authenticationCachingEnabled the value to set
     */
    @SuppressWarnings({"UnusedDeclaration"})
    public void setAuthorizationCachingEnabled(boolean authenticationCachingEnabled) {
        this.authorizationCachingEnabled = authenticationCachingEnabled;
        if (authenticationCachingEnabled) {
            setCachingEnabled(true);
        }
    }

    public PermissionResolver getPermissionResolver() {
        return permissionResolver;
    }

    public void setPermissionResolver(PermissionResolver permissionResolver) {
        if (permissionResolver == null) throw new IllegalArgumentException("Null PermissionResolver is not allowed");
        this.permissionResolver = permissionResolver;
    }

    public RolePermissionResolver getRolePermissionResolver() {
        return permissionRoleResolver;
    }

    public void setRolePermissionResolver(RolePermissionResolver permissionRoleResolver) {
        this.permissionRoleResolver = permissionRoleResolver;
    }

    /*--------------------------------------------
    |               M E T H O D S               |
    ============================================*/

    /**
     * Initializes this realm and potentially enables a cache, depending on configuration.
     * <p/>
     * When this method is called, the following logic is executed:
     * <ol>
     * <li>If the {@link #setAuthorizationCache cache} property has been set, it will be
     * used to cache the AuthorizationInfo objects returned from {@link #getAuthorizationInfo}
     * method invocations.
     * All future calls to {@code getAuthorizationInfo} will attempt to use this cache first
     * to alleviate any potentially unnecessary calls to an underlying data store.</li>
     * <li>If the {@link #setAuthorizationCache cache} property has <b>not</b> been set,
     * the {@link #setCacheManager cacheManager} property will be checked.
     * If a {@code cacheManager} has been set, it will be used to create an authorization
     * {@code cache}, and this newly created cache which will be used as specified in #1.</li>
     * <li>If neither the {@link #setAuthorizationCache (org.apache.shiro.cache.Cache) cache}
     * or {@link #setCacheManager(CacheManager) cacheManager}
     * properties are set, caching will be disabled and authorization look-ups will be delegated to
     * subclass implementations for each authorization check.</li>
     * </ol>
     */
    protected void onInit() {
        super.onInit();
        //trigger obtaining the authorization cache if possible
        getAvailableAuthorizationCache();
    }

    protected void afterCacheManagerSet() {
        super.afterCacheManagerSet();
        //trigger obtaining the authorization cache if possible
        getAvailableAuthorizationCache();
    }

    private Cache<Object, AuthorizationInfo> getAuthorizationCacheLazy() {

        if (this.authorizationCache == null) {

            if (log.isDebugEnabled()) {
                log.debug("No authorizationCache instance set.  Checking for a cacheManager...");
            }

            CacheManager cacheManager = getCacheManager();

            if (cacheManager != null) {
                String cacheName = getAuthorizationCacheName();
                if (log.isDebugEnabled()) {
                    log.debug("CacheManager [" + cacheManager + "] has been configured.  Building " +
                            "authorization cache named [" + cacheName + "]");
                }
                this.authorizationCache = cacheManager.getCache(cacheName);
            } else {
                if (log.isInfoEnabled()) {
                    log.info("No cache or cacheManager properties have been set.  Authorization cache cannot " +
                            "be obtained.");
                }
            }
        }

        return this.authorizationCache;
    }

    private Cache<Object, AuthorizationInfo> getAvailableAuthorizationCache() {
        Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
        if (cache == null && isAuthorizationCachingEnabled()) {
            cache = getAuthorizationCacheLazy();
        }
        return cache;
    }

    /**
     * Returns an account's authorization-specific information for the specified {@code principals},
     * or {@code null} if no account could be found.  The resulting {@code AuthorizationInfo} object is used
     * by the other method implementations in this class to automatically perform access control checks for the
     * corresponding {@code Subject}.
     * <p/>
     * This implementation obtains the actual {@code AuthorizationInfo} object from the subclass's
     * implementation of
     * {@link #doGetAuthorizationInfo(PrincipalCollection) doGetAuthorizationInfo}, and then
     * caches it for efficient reuse if caching is enabled (see below).
     * <p/>
     * Invocations of this method should be thought of as completely orthogonal to acquiring
     * {@link #getAuthenticationInfo(AuthenticationToken) authenticationInfo}, since either could
     * occur in any order.
     * <p/>
     * For example, in &quot;Remember Me&quot; scenarios, the user identity is remembered (and
     * assumed) for their current session and an authentication attempt during that session might never occur.
     * But because their identity would be remembered, that is sufficient enough information to call this method to
     * execute any necessary authorization checks.  For this reason, authentication and authorization should be
     * loosely coupled and not depend on each other.
     * <h3>Caching</h3>
     * The {@code AuthorizationInfo} values returned from this method are cached for efficient reuse
     * if caching is enabled.  Caching is enabled automatically when an {@link #setAuthorizationCache authorizationCache}
     * instance has been explicitly configured, or if a {@link #setCacheManager cacheManager} has been configured, which
     * will be used to lazily create the {@code authorizationCache} as needed.
     * <p/>
     * If caching is enabled, the authorization cache will be checked first and if found, will return the cached
     * {@code AuthorizationInfo} immediately.  If caching is disabled, or there is a cache miss, the authorization
     * info will be looked up from the underlying data store via the
     * {@link #doGetAuthorizationInfo(PrincipalCollection)} method, which must be implemented
     * by subclasses.
     * <h4>Changed Data</h4>
     * If caching is enabled and if any authorization data for an account is changed at
     * runtime, such as adding or removing roles and/or permissions, the subclass implementation should clear the
     * cached AuthorizationInfo for that account via the
     * {@link #clearCachedAuthorizationInfo(PrincipalCollection) clearCachedAuthorizationInfo}
     * method.  This ensures that the next call to {@code getAuthorizationInfo(PrincipalCollection)} will
     * acquire the account's fresh authorization data, where it will then be cached for efficient reuse.  This
     * ensures that stale authorization data will not be reused.
     *
     * @param principals the corresponding Subject's identifying principals with which to look up the Subject's
     *                   {@code AuthorizationInfo}.
     * @return the authorization information for the account associated with the specified {@code principals},
     *         or {@code null} if no account could be found.
     */
    protected AuthorizationInfo getAuthorizationInfo(PrincipalCollection principals) {

        if (principals == null) {
            return null;
        }

        AuthorizationInfo info = null;

        if (log.isTraceEnabled()) {
            log.trace("Retrieving AuthorizationInfo for principals [" + principals + "]");
        }

        Cache<Object, AuthorizationInfo> cache = getAvailableAuthorizationCache();
        if (cache != null) {
            if (log.isTraceEnabled()) {
                log.trace("Attempting to retrieve the AuthorizationInfo from cache.");
            }
            Object key = getAuthorizationCacheKey(principals);
            info = cache.get(key);
            if (log.isTraceEnabled()) {
                if (info == null) {
                    log.trace("No AuthorizationInfo found in cache for principals [" + principals + "]");
                } else {
                    log.trace("AuthorizationInfo found in cache for principals [" + principals + "]");
                }
            }
        }


        if (info == null) {
            // Call template method if the info was not found in a cache
            info = doGetAuthorizationInfo(principals);
            // If the info is not null and the cache has been created, then cache the authorization info.
            if (info != null && cache != null) {
                if (log.isTraceEnabled()) {
                    log.trace("Caching authorization info for principals: [" + principals + "].");
                }
                Object key = getAuthorizationCacheKey(principals);
                cache.put(key, info);
            }
        }

        return info;
    }

    protected Object getAuthorizationCacheKey(PrincipalCollection principals) {
        return principals;
    }

    /**
     * Clears out the AuthorizationInfo cache entry for the specified account.
     * <p/>
     * This method is provided as a convenience to subclasses so they can invalidate a cache entry when they
     * change an account's authorization data (add/remove roles or permissions) during runtime.  Because an account's
     * AuthorizationInfo can be cached, there needs to be a way to invalidate the cache for only that account so that
     * subsequent authorization operations don't used the (old) cached value if account data changes.
     * <p/>
     * After this method is called, the next authorization check for that same account will result in a call to
     * {@link #getAuthorizationInfo(PrincipalCollection) getAuthorizationInfo}, and the
     * resulting return value will be cached before being returned so it can be reused for later authorization checks.
     * <p/>
     * If you wish to clear out all associated cached data (and not just authorization data), use the
     * {@link #clearCache(PrincipalCollection)} method instead (which will in turn call this
     * method by default).
     *
     * @param principals the principals of the account for which to clear the cached AuthorizationInfo.
     */
    protected void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        if (principals == null) {
            return;
        }

        Cache<Object, AuthorizationInfo> cache = getAvailableAuthorizationCache();
        //cache instance will be non-null if caching is enabled:
        if (cache != null) {
            Object key = getAuthorizationCacheKey(principals);
            cache.remove(key);
        }
    }

    private Collection<Permission> getPermissions(AuthorizationInfo info) {
        Set<Permission> permissions = new HashSet<Permission>();

        if (info != null) {
            Collection<Permission> perms = info.getObjectPermissions();
            if (!CollectionUtils.isEmpty(perms)) {
                permissions.addAll(perms);
            }
            perms = resolvePermissions(info.getStringPermissions());
            if (!CollectionUtils.isEmpty(perms)) {
                permissions.addAll(perms);
            }

            perms = resolveRolePermissions(info.getRoles());
            if (!CollectionUtils.isEmpty(perms)) {
                permissions.addAll(perms);
            }
        }

        if (permissions.isEmpty()) {
            return Collections.emptySet();
        } else {
            return Collections.unmodifiableSet(permissions);
        }
    }

    private Collection<Permission> resolvePermissions(Collection<String> stringPerms) {
        Collection<Permission> perms = Collections.emptySet();
        PermissionResolver resolver = getPermissionResolver();
        if (resolver != null && !CollectionUtils.isEmpty(stringPerms)) {
            perms = new LinkedHashSet<Permission>(stringPerms.size());
            for (String strPermission : stringPerms) {
                Permission permission = getPermissionResolver().resolvePermission(strPermission);
                perms.add(permission);
            }
        }
        return perms;
    }

    private Collection<Permission> resolveRolePermissions(Collection<String> roleNames) {
        Collection<Permission> perms = Collections.emptySet();
        RolePermissionResolver resolver = getRolePermissionResolver();
        if (resolver != null && !CollectionUtils.isEmpty(roleNames)) {
            perms = new LinkedHashSet<Permission>(roleNames.size());
            for (String roleName : roleNames) {
                Collection<Permission> resolved = resolver.resolvePermissionsInRole(roleName);
                if (!CollectionUtils.isEmpty(resolved)) {
                    perms.addAll(resolved);
                }
            }
        }
        return perms;
    }

    public boolean isPermitted(PrincipalCollection principals, String permission) {
        Permission p = getPermissionResolver().resolvePermission(permission);
        return isPermitted(principals, p);
    }

    public boolean isPermitted(PrincipalCollection principals, Permission permission) {
        AuthorizationInfo info = getAuthorizationInfo(principals);
        return isPermitted(permission, info);
    }

    private boolean isPermitted(Permission permission, AuthorizationInfo info) {
        Collection<Permission> perms = getPermissions(info);
        if (perms != null && !perms.isEmpty()) {
            for (Permission perm : perms) {
                // 兼容页面的菜单展示
                if (permission instanceof ViewWildcardPermission && permission.implies(perm)) {
                    return true;
                }

                if (perm.implies(permission)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean[] isPermitted(PrincipalCollection subjectIdentifier, String... permissions) {
        List<Permission> perms = new ArrayList<Permission>(permissions.length);
        for (String permString : permissions) {
            perms.add(getPermissionResolver().resolvePermission(permString));
        }
        return isPermitted(subjectIdentifier, perms);
    }

    public boolean[] isPermitted(PrincipalCollection principals, List<Permission> permissions) {
        AuthorizationInfo info = getAuthorizationInfo(principals);
        return isPermitted(permissions, info);
    }

    protected boolean[] isPermitted(List<Permission> permissions, AuthorizationInfo info) {
        boolean[] result;
        if (permissions != null && !permissions.isEmpty()) {
            int size = permissions.size();
            result = new boolean[size];
            int i = 0;
            for (Permission p : permissions) {
                result[i++] = isPermitted(p, info);
            }
        } else {
            result = new boolean[0];
        }
        return result;
    }

    public boolean isPermittedAll(PrincipalCollection subjectIdentifier, String... permissions) {
        if (permissions != null && permissions.length > 0) {
            Collection<Permission> perms = new ArrayList<Permission>(permissions.length);
            for (String permString : permissions) {
                perms.add(getPermissionResolver().resolvePermission(permString));
            }
            return isPermittedAll(subjectIdentifier, perms);
        }
        return false;
    }

    public boolean isPermittedAll(PrincipalCollection principal, Collection<Permission> permissions) {
        AuthorizationInfo info = getAuthorizationInfo(principal);
        return info != null && isPermittedAll(permissions, info);
    }

    protected boolean isPermittedAll(Collection<Permission> permissions, AuthorizationInfo info) {
        if (permissions != null && !permissions.isEmpty()) {
            for (Permission p : permissions) {
                if (!isPermitted(p, info)) {
                    return false;
                }
            }
        }
        return true;
    }

    public void checkPermission(PrincipalCollection subjectIdentifier, String permission) throws AuthorizationException {
        Permission p = getPermissionResolver().resolvePermission(permission);
        checkPermission(subjectIdentifier, p);
    }

    public void checkPermission(PrincipalCollection principal, Permission permission) throws AuthorizationException {
        AuthorizationInfo info = getAuthorizationInfo(principal);
        checkPermission(permission, info);
    }

    protected void checkPermission(Permission permission, AuthorizationInfo info) {
        if (!isPermitted(permission, info)) {
            String msg = "User is not permitted [" + permission + "]";
            throw new UnauthorizedException(msg);
        }
    }

    public void checkPermissions(PrincipalCollection subjectIdentifier, String... permissions) throws AuthorizationException {
        if (permissions != null) {
            for (String permString : permissions) {
                checkPermission(subjectIdentifier, permString);
            }
        }
    }

    public void checkPermissions(PrincipalCollection principal, Collection<Permission> permissions) throws AuthorizationException {
        AuthorizationInfo info = getAuthorizationInfo(principal);
        checkPermissions(permissions, info);
    }

    protected void checkPermissions(Collection<Permission> permissions, AuthorizationInfo info) {
        if (permissions != null && !permissions.isEmpty()) {
            for (Permission p : permissions) {
                checkPermission(p, info);
            }
        }
    }

    public boolean hasRole(PrincipalCollection principal, String roleIdentifier) {
        AuthorizationInfo info = getAuthorizationInfo(principal);
        return hasRole(roleIdentifier, info);
    }

    protected boolean hasRole(String roleIdentifier, AuthorizationInfo info) {
        return info != null && info.getRoles() != null && info.getRoles().contains(roleIdentifier);
    }

    public boolean[] hasRoles(PrincipalCollection principal, List<String> roleIdentifiers) {
        AuthorizationInfo info = getAuthorizationInfo(principal);
        boolean[] result = new boolean[roleIdentifiers != null ? roleIdentifiers.size() : 0];
        if (info != null) {
            result = hasRoles(roleIdentifiers, info);
        }
        return result;
    }

    protected boolean[] hasRoles(List<String> roleIdentifiers, AuthorizationInfo info) {
        boolean[] result;
        if (roleIdentifiers != null && !roleIdentifiers.isEmpty()) {
            int size = roleIdentifiers.size();
            result = new boolean[size];
            int i = 0;
            for (String roleName : roleIdentifiers) {
                result[i++] = hasRole(roleName, info);
            }
        } else {
            result = new boolean[0];
        }
        return result;
    }

    public boolean hasAllRoles(PrincipalCollection principal, Collection<String> roleIdentifiers) {
        AuthorizationInfo info = getAuthorizationInfo(principal);
        return info != null && hasAllRoles(roleIdentifiers, info);
    }

    private boolean hasAllRoles(Collection<String> roleIdentifiers, AuthorizationInfo info) {
        if (roleIdentifiers != null && !roleIdentifiers.isEmpty()) {
            for (String roleName : roleIdentifiers) {
                if (!hasRole(roleName, info)) {
                    return false;
                }
            }
        }
        return true;
    }

    public void checkRole(PrincipalCollection principal, String role) throws AuthorizationException {
        AuthorizationInfo info = getAuthorizationInfo(principal);
        checkRole(role, info);
    }

    protected void checkRole(String role, AuthorizationInfo info) {
        if (!hasRole(role, info)) {
            String msg = "User does not have role [" + role + "]";
            throw new UnauthorizedException(msg);
        }
    }

    public void checkRoles(PrincipalCollection principal, Collection<String> roles) throws AuthorizationException {
        AuthorizationInfo info = getAuthorizationInfo(principal);
        checkRoles(roles, info);
    }

    public void checkRoles(PrincipalCollection principal, String... roles) throws AuthorizationException {
        checkRoles(principal, Arrays.asList(roles));
    }

    protected void checkRoles(Collection<String> roles, AuthorizationInfo info) {
        if (roles != null && !roles.isEmpty()) {
            for (String roleName : roles) {
                checkRole(roleName, info);
            }
        }
    }

    /**
     * Calls {@code super.doClearCache} to ensure any cached authentication data is removed and then calls
     * {@link #clearCachedAuthorizationInfo(PrincipalCollection)} to remove any cached
     * authorization data.
     * <p/>
     * If overriding in a subclass, be sure to call {@code super.doClearCache} to ensure this behavior is maintained.
     *
     * @param principals the principals of the account for which to clear any cached AuthorizationInfo
     * @since 1.2
     */
    @Override
    protected void doClearCache(PrincipalCollection principals) {
        super.doClearCache(principals);
        clearCachedAuthorizationInfo(principals);
    }
}
