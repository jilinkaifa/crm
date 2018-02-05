/*-----------------------------------------------------------------------------
 * @Description:     患者管理-会员列表
 * @Version:         1.0.0
 * @author:          lily(529116421@qq.com)
 * @date             2017.7.19
 * ==NOTES:=============================================
 * v1.0.0(2017.7.19):
 初始生成
 * ---------------------------------------------------------------------------*/
$(function(){
    /**
     * 初始化提示信息、分页
     */
    // showTip();
    Pagination(1,{});

    /**
     * 隐藏提示
     * @return {[type]} [description]
     */
    function showTip(){
        setTimeout(function(){
            $('.J_tip').hide();
        }, 2000);
    }

    /**
     * 点击搜索事件
     * @param  {[type]} ){                     search();    } [description]
     * @return {[type]}     [description]
     */
    $(".J_search").click(function(){
        var
            form = $(".J_searchForm").serializeObject();

        Pagination(1, form);
        $('#pageLimit').bootstrapPaginator({currentPage: 1});
    });

    /**
     * 分页
     */
    $('#pageLimit').bootstrapPaginator({
        size: "small",
        bootstrapMajorVersion: 3,
        alignment: "right",
        numberOfPages: 5,
        itemTexts: function (type, page, current) {
            switch (type) {
                case "first": return "首页";
                case "prev": return "<";
                case "next": return ">";
                case "last": return "末页";
                case "page": return page;
            }
        },
        onPageClicked: function (event, originalEvent, type, page) {
            var
                form = $(".J_searchForm").serializeObject();

            Pagination(page, form);
        }
    });

    /**
     * 分页刷数据
     */
    function Pagination(page, extraData){

        var
            currentPage = page,
            str = '',
            data = {
                page: currentPage
            };

        jQuery.extend(data, extraData);
        $.ajax({
            type: "GET",
            url: "/admin/client/member/ajax/list",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: data,     //JSON.stringify
            dataType: "json",
            cache:false,
            success: function (rs) {
                $('#J_template').empty();
                if( rs.code == 0){
                    $('.J_record').html(rs.numCount);
                    $('.pageDataCount').val(rs.dataCount);
                    $.each(rs.list, function(index, item){
                        str += '<tr data-id="'+ item.id +'">\
                                    <td>'+ item.number +'</td>\
                                    <td class="control-width width1">\
                                        <div title=" ' + item.name + ' " class="control-width width1">' + item.name + '</div>\
                                    </td>\
                                    <td>'+ item.idCardNum +'</td>\
                                    <td>'+ item.tel +'</td>\
                                    <td>'+ item.recordId +'</td>\
                                    <td>'+ item.level +'</td>\
                                    <td>'+ item.allCost +'</td>\
                                    <td>'+ item.recordDate +'</td>\
                                    <td>'+ item.memberStatus +'</td>\
                                    <td>'+ item.deadLine +'</td>\
                                    \<td>'+ item.isFixed +'</td>\
                                    <td>\
                                        <shiro:checkPermission name="Admin:Client:Member:Update">\
                                        <a href="/admin/client/member/update?id='+ item.id +'" class="label-info"><i class="fa fa-pencil"></i>&nbsp;修改</a>\
                                        </shiro:checkPermission>\
                                        <shiro:checkPermission name="Admin:Client:Member:Detail">\
                                        <a href="/admin/client/member/detail?id='+ item.id +'" class="label-info"><i class="fa fa-book"></i>&nbsp;详情</a>\
                                        </shiro:checkPermission>\
                                    </td>\
                                </tr>'
                    });
                    $('#J_template').append(str);
                    //暂无数据提示
                    var tr = $('#J_template').children();
                    if(tr.length == 0){
                        $('#J_template').append('<tr><td colspan="12">暂无数据</td></tr>');
                    }
                }else{
                    Alert("提示信息", "数据刷新失败！");
                }
                $('#pageLimit').bootstrapPaginator({totalPages: rs.dataCount});
            },
            error: function (message) {
                Alert("提示信息", "数据刷新失败！");
            }
        });
    }

    /**
     * 姓名-模糊匹配keyup事件——下拉框
     * @param  {[type]} ){                                          var            name [description]
     * @param  {[type]} dataType: "json"        [description]
     * @param  {String} success:  function      (rs)          {                                       var                                       li [description]
     * @param  {[type]} error:    function      (errMsg)      {                                       console.log(errMsg);            }                        } [description]
     * @return {[type]}           [description]
     */
    $('#J_selectName').keyup(function(){
        var
            name = this.value;
        $.ajax({
            type: "GET",
            url: "/admin/client/member/ajax/name",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: {name: name},     //JSON.stringify
            dataType: "json",
            success: function (rs) {
                $('#J_selectName').typeahead({
                    source: rs.list
                });
            },
            error: function (errMsg) {
                console.log(errMsg);
            }
        });
    });

    /**
     * 身份证-模糊匹配keyup事件——下拉框
     * @param  {[type]} ){                                          var            idcard [description]
     * @param  {[type]} dataType: "json"        [description]
     * @param  {String} success:  function      (rs)          {                                         var                                       li [description]
     * @param  {[type]} error:    function      (errMsg)      {                                         console.log(errMsg);            }                        } [description]
     * @return {[type]}           [description]
     */
    $('#J_selectId').keyup(function(){
        var
            idCard = this.value;
        $.ajax({
            type: "GET",
            url: "/admin/client/member/ajax/idCard",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: {idCard: idCard},     //JSON.stringify
            dataType: "json",
            success: function (rs) {
                $('#J_selectId').typeahead({
                    source: rs.list
                });
            },
            error: function (errMsg) {
                console.log(errMsg);
            }
        });
    });

    /**
     * 电话-模糊匹配keyup事件——下拉框
     * @param  {[type]} ){                                          var            phone [description]
     * @param  {[type]} dataType: "json"        [description]
     * @param  {String} success:  function      (rs)          {                                        var                                       li [description]
     * @param  {[type]} error:    function      (errMsg)      {                                        console.log(errMsg);            }                        } [description]
     * @return {[type]}           [description]
     */
    $('#J_selectPhone').keyup(function(){
        var
            phone = this.value;
        $.ajax({
            type: "GET",
            url: "/admin/client/member/ajax/tel",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: {tel: phone},     //JSON.stringify
            dataType: "json",
            success: function (rs) {
                $('#J_selectPhone').typeahead({
                    source: rs.list
                });
            },
            error: function (errMsg) {
                console.log(errMsg);
            }
        });
    });

    /**
     * 表格删除按钮
     * @param  {[type]} e){                 } [description]
     * @return {[type]}      [description]
     */
    // $('.J_del').click(function(e){
    //     var
    //         tr = $(e.target).parents('tr'),
    //         id = $(tr).attr('data-id');

    //     $('.hidId').val(id);
    //     // console.log($('.hidId').val());
    // });
    /**
     * 对话框删除按钮
     * @param  {[type]} e){                 } [description]
     * @return {[type]}      [description]
     */
    // $('.J_delDlg').click(function(){
    //     del();
    // });
    /**
     * 删除事件
     */
    // function del(){
    //     var
    //         id = $('.hidId').val(),
    //         form = {
    //             id: id
    //         };

    //     $.ajax({
    //         type: "GET",
    //         url: jQuery.url.AuthorityManagement[3][1],
    //         contentType: "application/x-www-form-urlencoded; charset=utf-8",
    //         data: form,     //JSON.stringify
    //         dataType: "json",
    //         success: function (rs) {
    //             $('#delDialog').modal('hide');
    //             location.reload();
    //             // $(tr).remove();

    //         },
    //         error: function (message) {

    //         }
    //     });
    // }

});