$(function(){

    var eventId = $(".eventId").val();
    Pagination(1,{id:eventId});

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
            str = '',
            data = {
                pageNo: currentPage,
                id : $(".eventId").val()
            };
        jQuery.extend(data, extraData);

        $.ajax({
            type: "GET",
            url: "/admin/event/archived/ajax/detaiNoticelList",
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
                                    <td class="control-width width150">\
                                        <div title=" ' + item.name + ' " class="control-width width150">'+ item.name +'</div>\
                                    </td>\
                                    <td>'+ item.idcard +'</td>\
                                    <td>'+ item.tel +'</td>\
                                    <td>'+ item.noticeStatus+'</td>\
                                    <td>'+ item.comment +'</td>\
                                    <td>'+ item.noticeTime +'</td>\
                                    <td>'+ item.noticeName +'</td>\
                                </tr>'
                    }); 
                    $('#J_template').append(str);
                    //暂无数据提示
                    var tr = $('#J_template').children();
                    if(tr.length == 0){
                        $('#J_template').append('<tr><td colspan="7">暂无数据</td></tr>');
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