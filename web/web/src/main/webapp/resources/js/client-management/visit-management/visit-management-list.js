/*-----------------------------------------------------------------------------
* @Description:     访客管理
* @Version:         1.0.0
* @author:          zhangxn(571946808@qq.com)
* @date             2017.7.20
* ==NOTES:=============================================
* v1.0.0(2017.7.20):
     初始生成
* ---------------------------------------------------------------------------*/
$(function(){
    /**
     * 初始化提示信息、验证表单
     */
    // showTip();
    Pagination(1,{});

    /**
     * 取消提示
     */
    function showTip(){
        setTimeout(function(){
            $('.J_tip').hide();
        }, 2000);
    }

    /**
     * 分页
     */
    $('#pageLimit').bootstrapPaginator({
        //currentPage: 3,
        totalPages: $('.pageDataCount').val(),
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
            url: "/admin/client/visit/ajax/list",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: data,     //JSON.stringify
            dataType: "json",
            cache:false,
            success: function (rs) {
                $('.pageDataCount').val(rs.dataCount);
                $('#pageLimit').bootstrapPaginator({totalPages: $('.pageDataCount').val()});
                $('#J_template').empty();
                if( rs.code == 0){
                    $('.J_record').html(rs.numCount);
                    $.each(rs.list, function(index, item){
                        str += '<tr>\
                                    <td>'+ item.number +'</td>\
                                    <td class="control-width width1">\
                                        <div title=" ' + item.clientName + ' " class="control-width width1">' + item.clientName + '</div>\
                                    </td>\
                                    <td>'+ item.genderName +'</td>\
                                    <td>'+ item.clientIdcardNum +'</td>\
                                    <td>'+ item.clientTel +'</td>\
                                    <td>'+ item.clientType +'</td>\
                                    <td>'+ item.firstVisitType +'</td>\
                                    <td>'+ item.visitTimes +'</td>\
                                    <td>'+ item.visitTime +'</td>\
                                    <td>'+ item.cost +'</td>\
                                    <td>\
                                        <shiro:checkPermission name="Admin:Client:Visit:Detail">\
                                        <a href="/admin/client/visit/detailList?clientId='+ item.clientId +'" class="label-info"><i class="fa fa-search"></i>&nbsp;详情</a>\
                                        </shiro:checkPermission>\
                                    </td>\
                                </tr>'
                    });
                    $('#J_template').append(str);
                    //暂无数据提示
                    var tr = $('#J_template').children();
                    if(tr.length == 0){
                        $('#J_template').append('<tr><td colspan="11">暂无数据</td></tr>');
                    }
                }else{
                    location.reload();
                }

            },
            error: function (message) {
                location.reload();
            }
        });
    }

    /**
     * 姓名-模糊匹配-keyup事件
     */
    $('#J_selectName').keyup(function(){
        var
            clientName = this.value;
        $.ajax({
            type: "GET",
            url: "/admin/client/visit/ajax/clientName",
            contentType: "application/json; charset=utf-8",
            data: {clientName: clientName},     //JSON.stringify
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
     * 身份证-模糊匹配-keyup事件
     */
    $('#J_selectId').keyup(function(){
        var
            idcard = this.value;
        $.ajax({
            type: "GET",
            url: "/admin/client/visit/ajax/idcard",
            contentType: "application/json; charset=utf-8",
            data: {idcard: idcard},     //JSON.stringify
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
     * 电话-模糊匹配-keyup事件
     */
    $('#J_selectPhone').keyup(function(){
        var
            tel = this.value;
        $.ajax({
            type: "GET",
            url: "/admin/client/visit/ajax/tel",
            contentType: "application/json; charset=utf-8",
            data: {tel: tel},     //JSON.stringify
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
     * 列表点击搜索事件
     * @param  {[type]} ){                     var                                                            form [description]
     * @return {[type]}     [description]
     */
    $(".J_search").click(function(){
        var
            form = $(".J_searchForm").serializeObject();

        Pagination(1, form);
        $('#pageLimit').bootstrapPaginator({currentPage: 1});
    });
});