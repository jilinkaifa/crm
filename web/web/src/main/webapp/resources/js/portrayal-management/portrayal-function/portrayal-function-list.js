/*-----------------------------------------------------------------------------
* @Description:     画像管理-画像功能
* @Version:         1.0.0
* @author:          gts(616603151@qq.com)
* @date             2017.7.21
* ==NOTES:=============================================
* 
* ---------------------------------------------------------------------------*/
$(document).ready(function() {
    
    /**
     * 初始化提示信息、验证表单
     */
    showTip();
    Pagination(1);

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
                pageNo: currentPage
            };
        jQuery.extend(data, extraData);

        $.ajax({
            type: "GET",
            url: "/admin/profile/portrayal/ajax/list",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: data,     //JSON.stringify
            dataType: "json",
            cache:false,
            success: function (rs) {
                $('#J_template').empty();
                if( rs.code == 0){  
                    $('.J_record').html(rs.numCount);
                    $.each(rs.list, function(index, item){
                        str += '<tr data-id="'+ item.id +'" >\
                                    <td><input type="checkbox" class="J_select" value="'+ item.id +'"></td>\
                                    <td class="control-width width1">\
                                        <div title=" ' + item.name + ' " class="control-width width1">' + item.name + '</div>\
                                    </td>\
                                    <td>'+ item.gender+'</td>\
                                    <td>'+ item.idCardNum+'</td>\
                                    <td>'+ item.tel+'</td>\
                                    <td>'+ item.dicType+'</td>\
                                    <td>'+ item.dicMciType+'</td>\
                                    <td>'+ item.allCost+'</td>\
                                    <td>'+ item.partyName+'</td>\
                                    <td>'+ item.lastModifiedTime+'</td>\
                                    <td>\
                                        <a href="/admin/client/member/update?id='+ item.id +'" class="label-info J_edit"><i class="fa fa-user"></i>&nbsp;画像</a>\
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
                    Alert("提示信息","数据刷新失败！");
                }
                $('#pageLimit').bootstrapPaginator({totalPages: rs.dataCount});
            },
            error: function (message) {
                Alert("提示信息","数据刷新失败！");
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
            url: "/admin/profile/portrayal/ajax/name",
            contentType: "application/json; charset=utf-8",
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
            idcard = this.value;
        $.ajax({
            type: "GET",
            url: "/admin/profile/portrayal/ajax/idCard",
            contentType: "application/json; charset=utf-8",
            data: {idCard: idcard},     //JSON.stringify
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
     * 批量修改详情事件
     */
    // $("#edit_model").click(function() {
    //     // 判断是否至少选择一项
    //     var checkedNum = $("input[name='subBox']:checked").length;
    //     if (checkedNum == 0) {
    //         $("#modalDel").modal();
    //         //return;
    //     } else {
    //          ;
    //     }
    // });
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
    /**
     * selectAll全选
     */
    $('.J_selectAll').click(function(){
        var
            selectMap = $('.J_select');
        if( selectMap.length != $('.J_select:checked').length){
            $('.J_selectAll').prop('checked', true);
            selectMap.prop('checked', true);
        }else{
            selectMap.prop('checked', false);
        }
    });

    /**
     * select按钮
     */
    $(document).on('click', '.J_select', function(){
        var
            selectMap = $('.J_select'),
            selectAll = $('.J_selectAll');
        if( selectMap.length == $('.J_select:checked').length){
            selectAll.prop('checked', true);
        }else{
            selectAll.prop('checked', false);
        }
    });

     /**
     * 点击批量修改
     */
    $('.J_editAll').click(function(){
        editAll();
    });
  /**
     * 判断是否选择删除项
     */
    function editAll(){
        var
            select = $('.J_select:checked').length;

        if(select == 0){
            $('#editTipDialog').modal();
        }
        else{
            sendEdit()
        }
    }
    /**
     * 获取修改项并发送数据
     */
    function  sendEdit(){
        var
            selectMap = $('.J_select:checked'),
            idArray = [],
            data;
        $.each(selectMap, function(index, item){
            idArray.push(item.value);
        });

        window.location = '/admin/profile/portrayal/update?id='+idArray;
        
    }



});    