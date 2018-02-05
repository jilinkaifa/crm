/*-----------------------------------------------------------------------------
* @Description:     已归档活动列表页
* @Version:         1.0.0
* @author:          zhangfc(546641398@qq.com)
* @date             2017.7.28
---------------------------------------------------------*/
$(function(){

    /**
     * 初始化提示信息、验证表单
     */

    Pagination(1,{});
    $('#Menu3,#logoMenu3').trigger('click');

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
    /**
     * 分页刷数据
     */
    function Pagination(page,extraData){

        var
            currentPage = page,
            str = '',
            data = {
                pageNo: currentPage
            };
        jQuery.extend(data, extraData);

        $.ajax({
            type: "GET",
            url: "/admin/event/archived/ajax/list",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: data,        //JSON.stringify
            dataType: "json",
            cache:false,
            success: function (rs) {
                $('#J_template').empty();
                if( rs.code == 0){
                    $('.J_record').html(rs.numCount);
                    $.each(rs.list, function(index, item) {
                            // str += '<tr data-id="'+ item.id +'"> <td>'+ item.id +'</td> <td>'+ item.dic_quest_item +'</td> <td>'+ item.quest_content +'</td> <td>'+ item.start_data +'</td> <td>'+ item.end_data +'</td> </tr>'
                            str += '<tr data-id="' + item.id + '">\
                                    <td class="control-width width1">\
                                        <div title=" ' + item.eventName + ' " class="control-width width1">' + item.eventName + '</div>\
                                    </td>\
                                    <td>' + item.eventType + '</td>\
                                    <td>' + item.eventLevel + '</td>\
                                    <td>' + item.eventStatus + '</td>';
                                    if (item.eventTermId < 0){
                                       str += '<td>' + 0 + '</td>';
                                    }else{
                                        str += '<td><a href="/admin/event/processing/toNotice?eventTermId='+ item.eventTermId +'">'+ item.countNoticeing +' </a>\</td>';
                                     }
                                    str += '<td>'+ item.BQStartDate +'</td>\
                                    <td class="control-width width2">\
                                        <div title=" ' + item.memberName + ' " class="control-width width2">' + item.memberName + '</div>\
                                    </td>\
                                    <td>'+ item.BQEndDate +'</td>\
                                    <td>'+ item.countFinshing +'</td>\
                                    <td>'+ item.createdPartyName +'</td>\
                                    <td>'+ new Date(item.createdTime).format("yyyy-MM-dd")+'</td>\
                                    <td>\
                                       <shiro:checkPermission name="Admin:E-CRB:Event:Archived:Detail">\
                                        <a href="/admin/event/archived/detail?id='+ item.id +'" class="label-info J_details"><i class="fa fa-book"></i>&nbsp;详情</a>\
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
     * 姓名-模糊匹配-keyup事件
     */
    $('#J_selectName').keyup(function(){
        var
            createdPartyName = this.value;
        $.ajax({
            type: "GET",
            url: "/admin/event/archived/ajax/searchByName",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: {createdPartyName: createdPartyName},     //JSON.stringify
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
            idcardNum = this.value;
        $.ajax({
            type: "GET",
            url: "/admin/event/archived/ajax/searchByIdcard",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: {idcardNum: idcardNum},     //JSON.stringify
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
     * 活动名称-模糊匹配-keyup事件
     */
    $('#J_selectActivity').keyup(function(){
        var
            eventName = this.value;
        $.ajax({
            type: "GET",
            url: "/admin/event/archived/ajax/searchByEventName",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: {eventName: eventName},     //JSON.stringify
            dataType: "json",
            success: function (rs) {
                $('#J_selectActivity').typeahead({
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
})