 /*-----------------------------------------------------------------------------
 * @Description:    后台管理--配置url地址 (url-core.js)
 * @Version: 	    V1.0.0
 * @author: 		yud(862669640@qq.com)
 * @date			2017.6.30
 * ==NOTES:=============================================
 * v1.0.0(2015.11.02) cuiy:
 * 经项目实践,发现目前项目架构不适合调试使用,需要不断修改IO层,这样会对前\后端的开发带来不便,故决定使用此插件来解决问题
 * ---------------------------------------------------------------------------*/
(function(){
    var
        site ={
            website:'/', //站点地址
            staticWebsite: '/' // 前端服务器地址
        };


    _pw_apiData = {
        //  后台登录
        Login:[
            ['getLogin', site.staticWebsite + 'mock/common.json', 'get', '后台登录']
        ],
        //权限管理
        AuthorityManagement:[
            ['delAuthority', site.staticWebsite + 'mock/common.json', 'get', '删除权限'],
            ['addAuthority', site.staticWebsite + 'mock/common.json', 'get', '添加权限'],
            ['editAuthority', site.staticWebsite + 'mock/common.json', 'get', '编辑权限'],
            ['delSecurityAuthority', site.staticWebsite + 'mock/common.json', 'get', '删除安全组权限'],
            ['addSecurityAuthority', site.staticWebsite + 'mock/common.json', 'get', '添加安全组权限'],
            ['editSecurityAuthority', site.staticWebsite + 'mock/common.json', 'get', '编辑安全组权限'],
            ['delUser', site.staticWebsite + 'mock/common.json', 'get', '删除用户'],
            ['sendTableName', site.staticWebsite + 'mock/common.json', 'get', '用户添加时身份证号码验重'],
            ['sendDicTree', site.staticWebsite + 'mock/dictionary-tree.json', 'get', '获取字典值信息'],
            ['sendAuthority', site.staticWebsite + 'mock/common.json', 'get', '发送添加的权限'],
            ['addUser', site.staticWebsite + 'mock/common.json', 'get', '添加用户']
        ],
        //患者管理
        ClientManagement:[
            ['delUserInfo', site.staticWebsite + 'mock/common.json', 'get', '删除用户信息'],
            ['joinUserInfo', site.staticWebsite + 'mock/common.json', 'get', '加入会员'],
            ['sendTableName', site.staticWebsite + 'mock/common.json', 'get', '患者添加时身份证号码验重'],
            //会员管理
            ['delMember', site.staticWebsite + 'mock/common.json', 'get', '删除会员信息'],
            ['selectNameData', site.staticWebsite + 'mock/member-management-select-name.json', 'get', '获取姓名信息'],
            ['selectIdData', site.staticWebsite + 'mock/member-management-select-id.json', 'get', '获取身份证信息'],
            ['selectPhoneData', site.staticWebsite + 'mock/member-management-select-phone.json', 'get', '获取电话信息'],
            ['sendSearchData', site.staticWebsite + 'mock/common.json', 'get', '发送搜索数据']
        ],
        // 画像管理
        PortrayalManagement:[
            ['delInfo', site.staticWebsite + 'mock/common.json', 'get', '删除信息']
            ],
        // 客户关怀管理
        ECRBManagement: [
            ]
    };

    jQuery.extend({
        url: _pw_apiData
    });
})();