/*-----------------------------------------------------------------------------
* @Description:     积分管理-积分列表
* @Version:         1.0.0
* @author:          gts(616603151@qq.com)
* @date             2017.7.20
* ==NOTES:=============================================
* 
* ---------------------------------------------------------------------------*/
$(document).ready(function() {
    /**
     * 初始化提示信息、验证表单
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
            url: "/admin/client/points/ajax/list",
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
                        str += '<tr data-id="'+ item.number +'">\
                                    <td class="control-width width1">\
                                        <div title=" ' + item.clientName + ' " class="control-width width1">' + item.clientName + '</div>\
                                    </td>\
                                    <td>'+ item.clientIdcardNum +'</td>\
                                    <td>'+ item.clientTel+'</td>\
                                    <td>'+ item.allCost +'</td>\
                                    <td>'+ item.times +'</td>\
                                    <td>'+ new Date(item.lastVisitDate).format("yyyy-MM-dd") +'</td>\
                                    <td>'+ item.currentSumpoints +'</td>\
                                    <td>'+ item.currentPoints +'</td>\
                                    <td>\
                                    <shiro:checkPermission name="Admin:Client:Points:DetailList">\
                                    <a href="/admin/client/points/detailList?clientId='+ item.clientId +'" class="label-info J_details"><i class="fa fa-book"></i>&nbsp;详情</a>\
                                    </td>\
                                </tr>'
                    }); 
                    $('#J_template').append(str);
                    //暂无数据提示
                    var tr = $('#J_template').children();
                    if(tr.length == 0){
                        $('#J_template').append('<tr><td colspan="9">暂无数据</td></tr>');
                    }
                }else{                
                    location.reload();
                }

            },
            error: function (message) {
                // location.reload();
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
            clientName = this.value;
        $.ajax({
            type: "GET",
            url: "/admin/client/points/ajax/clientName",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
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
     * 身份证-模糊匹配keyup事件——下拉框
     * @param  {[type]} ){                                          var            idcard [description]
     * @param  {[type]} dataType: "json"        [description]
     * @param  {String} success:  function      (rs)          {                                         var                                       li [description]
     * @param  {[type]} error:    function      (errMsg)      {                                         console.log(errMsg);            }                        } [description]
     * @return {[type]}           [description]
     */
    $('#J_selectId').keyup(function(){
        var
            idcard = this.value;
        $.ajax({
            type: "GET",
            url: "/admin/client/points/ajax/idcard",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
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
     * 电话-模糊匹配keyup事件——下拉框
     * @param  {[type]} ){                                          var            phone [description]
     * @param  {[type]} dataType: "json"        [description]
     * @param  {String} success:  function      (rs)          {                                        var                                       li [description]
     * @param  {[type]} error:    function      (errMsg)      {                                        console.log(errMsg);            }                        } [description]
     * @return {[type]}           [description]
     */
    $('#J_selectPhone').keyup(function(){
        var
            tel = this.value;
        $.ajax({
            type: "GET",
            url: "/admin/client/points/ajax/tel",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
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
     * @param  {[type]} ){                     }
     * @return {[type]}     [description]
     */
    $(".J_search").click(function(){
        var
            form = $(".J_searchForm").serializeObject();

        Pagination(1, form);
        $('#pageLimit').bootstrapPaginator({currentPage: 1});
    });



});