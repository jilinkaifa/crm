/*-----------------------------------------------------------------------------
* @Description:     活动管理-进行中的活动
* @Version:         1.0.0
* @author:          gts(616603151@qq.com)
* @date             2017.7.24
* ==NOTES:=============================================
* 
* ---------------------------------------------------------------------------*/
$(document).ready(function() {
    Pagination(1);

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

            Pagination(page);  
        }
    });

    /**
     * 分页刷数据
     */
    function Pagination(page){

        var
            currentPage = page,            
            str = '',
            data = {
                pageNo: currentPage,
                clientId : $(".clientId").val()
            };

        $.ajax({
            type: "POST",
            // url: jQuery.url.ECRBManagement.joinList,
            url: "/admin/event/processing/ajax/total/list",
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
                                    <td class="control-width width2">\
                                        <div title=" ' + item.name + ' " class="control-width width2">' + item.name + '</div>\
                                    </td>\
                                    <td>'+ item.typeValue +'</td>\
                                    <td>'+ item.levelValue +'</td>\
                                    <td>'+ new Date(item.startDate).format("yyyy-MM-dd") +'</td>\
                                    <td class="control-width width1">\
                                        <div title=" ' + item.memberGroupName + ' " class="control-width width1">' + item.memberGroupName + '</div>\
                                    </td>\
                                    <td>'+ item.createdPartyName +'</td>\
                                    <td>'+ new Date(item.createdTime).format("yyyy-MM-dd") +'</td>\
                                    <td>\
                                        <a href="/admin/event/processing/detail?id= ' + item.id + '"  class="label-info J_process" data-toggle="modal" data-target="#modalDialog"><i class="fa fa-book"></i>&nbsp;详情</a>\
                                    </td>\
                                </tr>'
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
});