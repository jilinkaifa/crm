/*-----------------------------------------------------------------------------
* @Description:     活动管理-进行中的活动
* @Version:         1.0.0
* @author:          lily(529116421@qq.com)
* @date             2017.7.26
* ==NOTES:=============================================
* * v1.0.0(2017.7.26):
     初始生成
* ---------------------------------------------------------------------------*/
$(function(){
    /**
     *  初始化提示信息、分页
     */
    Pagination(1);
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

            Pagination(page);  
        }
    });

     /**
     * 分页刷数据
     */
    function Pagination(page){
        //取消全选选中
         $('.J_selectAll').prop('checked', false);

        var
            currentPage = page,            
            str = '',
            data = {
                eventTermId : $('.eventTermId').val(),
                pageNo: currentPage,
                participantName : $('#J_selectName').val(),
                participantIdcard : $('#J_selectId').val()
            };
        $.ajax({
            type: "POST",
            // url: jQuery.url.ECRBManagement.notificationDetailList,
            url: "/admin/event/processing/ajax/notice/detail",
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
                                    <td><input type="checkbox" class="J_select" value="'+ item.id +'"></td>\
                                    <td>'+ item.participantName +'</td>\
                                    <td>'+ item.participantIdcard +'</td>\
                                    <td>'+ item.participantTel +'</td>\
                                    <td>'+ item.noticeStatusValue+'</td>\
                                    <td>'+ item.comment +'</td>\
                                    <td>'+ new Date(item.noticeTime).format("yyyy-MM-dd") +'</td>\
                                    <td>'+ item.sysName +'</td>\
                                    <td>\
                                        <a href="/admin/event/processing/notice?clientId='+ item.clientId +'" class="label-info"><i class="fa fa-edit"></i>&nbsp;通知</a>\
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

    $(".J_search").click(function(){
        // var
        //     form = $(".J_searchForm").serializeObject();
        Pagination(1);

        // Pagination(1, form);
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
});