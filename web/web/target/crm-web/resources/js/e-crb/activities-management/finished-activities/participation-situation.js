$(function(){

	/**
     * 初始化提示信息、验证表单
     */
    var eventId = $(".eventId").val();
    var recordNoticeId = $(".recordNoticeId").val();
    Pagination(1,{eventId:eventId,recordNoticeId:recordNoticeId});

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
    function Pagination(page,extraData){

        var
            currentPage = page,
            str = '';
        data = {
            pageNo: currentPage,
            eventId : $(".eventId").val(),
            recordNoticeId:$(".recordNoticeId").val()
        };
        jQuery.extend(data, extraData);

        $.ajax({
            type: "GET",
            url: "/admin/event/archived/current",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: date,     //JSON.stringify
            dataType: "json",
            cache:false,
            success: function (rs) {
                $('#J_template').empty();
                if( rs.code == 0){  
                    $('.J_record').html(rs.numCount);
                    $.each(rs.list, function(index, item){
                        str += '<tr data-id="'+ item.id +'">\
                       <td>'+ item.name +'</td> \
                       <td>'+ item.startDate +'</td> \
                       <td>'+ item.content +'</td>\
                       <td>'+ item.comment +'</td>\
                       <td>'+ item.createPartyName +'</td>\
                       <td>'+ item.createdTime +'</td>\
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