/*-----------------------------------------------------------------------------
* @Description:     积分管理-积分详情
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
     * 列表点击搜索事件
     * @param  {[type]} ){                     }
     * @return {[type]}     [description]
     */
    $(".J_search").click(function(){
        var
            form = $(".J_searchForm").serializeObject();
            id =$('.clientId').val();
        jQuery.extend(form,{clientId:id});
        Pagination(1, form);
        $('#pageLimit').bootstrapPaginator({currentPage: 1});
    });

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
            clientId=$('.clientId').val();
            currentPage = page,            
            str = '',
            data = {
                page: currentPage,clientId:clientId
            };
        jQuery.extend(data, extraData);

        $.ajax({
            type: "GET",
            url: "/admin/client/points/ajax/detailList",
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
                                    <td>'+ new Date(item.lastVisitDate).format("yyyy-MM-dd") +'</td>\
                                    <td>'+ item.pointsFrom+'</td>\
                                    <td>'+ item.pointsSize+'</td>\
                                    <td>'+ item.currentPoints +'</td>\
                                    <td>'+ item.currentSumpoints+'</td>\
                                    <td>'+ item.eventName +'</td>\
                                </tr>'
                    }); 
                    $('#J_template').append(str);
                    //暂无数据提示
                    var tr = $('#J_template').children();
                    if(tr.length == 0){
                        $('#J_template').append('<tr><td colspan="6">暂无数据</td></tr>');
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

});
