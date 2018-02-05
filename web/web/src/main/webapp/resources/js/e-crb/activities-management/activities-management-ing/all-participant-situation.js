/**
 * Created by 13583 on 2017/8/7.
 */
$(function(){

    /**
     * 初始化提示信息、验证表单
     */
    // showTip();
    Pagination(1);

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

        var str = '';
        var param = {
            pageNo : page,
            eventRecordNoticeId : $(".eventRecordNoticeId").val()
        };


        $.ajax({
            type: "POST",
            // url: jQuery.url.ECRBManagement.participSituation,
            url: "/admin/event/processing/ajax/total/participant",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: param,     //JSON.stringify
            dataType: "json",
            cache:false,
            success: function (rs) {
                $('#J_template').empty();
                if( rs.code == 0){
                    $('.J_record').html(rs.numCount);
                    $.each(rs.list, function(index, item){
                        str += '<tr data-id="'+ item.id +'">\
                       <td>'+ item.name +'</td> \
                       <td>'+ new Date(item.bqstartDate).format("yyyy-MM-dd") +'</td> \
                       <td>'+ item.content +'</td>\
                       <td>'+ item.comment +'</td>\
                       <td>'+ item.createdPartyName +'</td>\
                       <td>'+ new Date(item.createdTime).format("yyyy-MM-dd hh-mm-ss") +'</td>\
                       </tr>'
                    });
                    $('#J_template').append(str);
                    //暂无数据提示
                    var tr = $('#J_template').children();
                    if(tr.length == 0){
                        $('#J_template').append('<tr><td colspan="6">暂无数据</td></tr>');
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
})