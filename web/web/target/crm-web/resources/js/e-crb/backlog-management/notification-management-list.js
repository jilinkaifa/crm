
/*-----------------------------------------------------------------------------
* @Description:     客户关怀-待办活动通知列表
* @Version:         1.0.0
* @author:          lily(529116421@qq.com)
* @date             2017.7.26
* ==NOTES:=============================================
* v1.0.0(2017.7.26):
     初始生成
* ---------------------------------------------------------------------------*/
$(function(){
     /**
     * 初始化提示信息、分页
     */
    Pagination(1);


    /**
     * 列表点击搜索事件
     * @param  {[type]} ){                     }
     * @return {[type]}     [description]
     */
    $(".J_search").click(function(){
        var
            form = $(".J_form").serializeObject();

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
                form = $(".J_form").serializeObject();

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
            data = jQuery.extend(extraData, {pageNo: currentPage});

        $.ajax({
            type: "GET",
            url: "/admin/event/prepareNotice/ajax/list",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: data,     //JSON.stringify
            dataType: "json",
            cache:false,
            success: function (rs) {
                $('#J_template').empty();
                if( rs.code == 0){
                    $('.J_record').html(rs.numCount);
                    $.each(rs.list, function(index, item){
                        str += '<tr data-id="'+ item.id +'">\
                                    <td>'+ item.num +'</td>\
                                    <td class="control-width width150">\
                                        <div title=" ' + item.name + ' " class="control-width width150">' + item.name + '</div>\
                                    </td>\
                                    <td>'+ item.typea +'</td>\
                                    <td>'+ item.levela +'</td>\
                                    <td>'+ item.startDate +'</td>\
                                    <td class="control-width width2">\
                                        <div title=" ' + item.memberGroup + ' " class="control-width width2">' + item.memberGroup + '</div>\
                                    </td>\
                                    <td>'+ item.countPeople +'</td>\
                                    <td>\
                                        <a href="/admin/event/processing/detail?eventTermId='+ item.id +'" class="label-info"><i class="fa fa-book"></i>&nbsp;详情</a>\
                                    </td>\
                                </tr>';
                    }); 
                    $('#J_template').append(str);
                    //暂无数据提示
                    var tr = $('#J_template').children();
                    if(tr.length == 0){
                        $('#J_template').append('<tr><td colspan="8">暂无数据</td></tr>');
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
     * 模糊匹配-可输入也可下拉选择（引用插件）
     * @type {String}
     */
    $('#editable-select1').editableSelect1({
        effects: 'slide'
    });
    $('#editable-select2').editableSelect2({
        effects: 'slide'
    });
    $('#editable-select3').editableSelect3({
        effects: 'slide'
    });
    $('#html1').editableSelect1();
    $('#html2').editableSelect2();
    $('#html3').editableSelect3();

    /**
     * 姓名-模糊匹配keyup事件——下拉框
     * @param  {[type]} ){                                          var            name [description]
     * @param  {[type]} dataType: "json"        [description]
     * @param  {String} success:  function      (rs)          {                                       var                                       li [description]
     * @param  {[type]} error:    function      (errMsg)      {                                       console.log(errMsg);            }                        } [description]
     * @return {[type]}           [description]
     */
    $('.J_selectName').keyup(function(){
        var
            name = $('input.J_selectName').val();

        $(".es-list1").empty();
        $.ajax({
            type: "GET",
            url: jQuery.url.ClientManagement.selectNameData,
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: {name: name},     //JSON.stringify
            dataType: "json",
            success: function (rs) {
                var
                    li = "";

                $(rs.list).each(function(key, item){
                    li +='<li class="es-visible" style="display: block;">' + item.name + '</li>';
                    // console.log(li);
                });
                $(".es-list1").append(li);
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
    $('.J_selectId').keyup(function(){
        var
            idcard = $('input.J_selectId').val();

        $(".es-list2").empty();
        $.ajax({
            type: "GET",
            url: jQuery.url.ClientManagement.selectIdData,
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: {idcard: idcard},     //JSON.stringify
            dataType: "json",
            success: function (rs) {
                var
                    li = "";

                $(rs.list).each(function(key, item){
                    li +='<li class="es-visible" style="display: block;">' + item.idcard + '</li>';
                    // console.log(li);
                });
                $(".es-list2").append(li);
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
    $('.J_selectPhone').keyup(function(){
        var
            phone = $('input.J_selectPhone').val();

        $(".es-list3").empty();
        $.ajax({
            type: "GET",
            url: jQuery.url.ClientManagement.selectPhoneData,
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: {phone: phone},     //JSON.stringify
            dataType: "json",
            success: function (rs) {
                var
                    li = "";

                $(rs.list).each(function(key, item){
                    li +='<li class="es-visible" style="display: block;">' + item.phone + '</li>';
                    // console.log(li);
                });
                $(".es-list3").append(li);
            },
            error: function (errMsg) {
                console.log(errMsg);
            }
        });
    });

})