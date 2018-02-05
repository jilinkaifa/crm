$(function(){
    /**
     * 初始化提示信息、验证表单
     */
    // showTip();
    Pagination(1,{});

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
            clientId=$('.clientId').val();
            currentPage = page,
            str = '',
            data = {
                page: currentPage,clientId:clientId
            };
        jQuery.extend(data, extraData);
        $.ajax({
            type: "GET",
            url: "/admin/client/quest/ajax/detailList",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: data,     //JSON.stringify
            dataType: "json",
            cache:false,
            success: function (rs) {
                $('#J_template').empty();
                $('#pageLimit').bootstrapPaginator({totalPages: rs.dataCount});
                $('.pageDataCount').val(rs.dataCount);
                if( rs.code == 0){
                    $('.J_record').html(rs.numCount);
                    $.each(rs.list, function(index, item){
                        str += '<tr data-id="'+ item.id +'">' +
                            ' <td>'+ item.number +'</td> ' +
                            '<td>'+ item.dicQuestItem +'</td> ' +
                            '<td>'+ item.questContent +'</td> ' +
                            '<td>'+ item.startDate +'</td>' +
                            ' <td>'+ item.endDate +'</td>' +
                            ' </tr>'
                    });
                    $('#J_template').append(str);
                    //暂无数据提示
                    var tr = $('#J_template').children();
                    if(tr.length == 0){
                        $('#J_template').append('<tr><td colspan="5">暂无数据</td></tr>');
                    }
                }else{
                    Alert("提示信息", "数据刷新失败！");
                }

            },
            error: function (message) {
                Alert("提示信息", "数据刷新失败！");
            }
        });
    }

    /**
     * 列表点击搜索事件
     * @param  {[type]} ){                     }
     * @return {[type]}     [description]
     */
    $(".J_search").click(function(){
        var
            form = $(".J_searchForm").serializeObject();
        // console.log(form);
        Pagination(1, form);
        $('#pageLimit').bootstrapPaginator({currentPage: 1});
    });

});