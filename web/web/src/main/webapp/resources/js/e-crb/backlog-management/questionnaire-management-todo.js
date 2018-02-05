$(function(){

    /**
     * 初始化提示信息、验证表单
     */
    Pagination(1,{});
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
            str = '';
        $.ajax({
            type: "GET",
            url: "/admin/event/quest/prepare/ajax/list",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: {page: currentPage},     //JSON.stringify
            dataType: "json",
            cache:false,
            success: function (rs) {
                $('#J_template').empty();
                if( rs.code == 0){
                    $('.J_record').html(rs.numCount);
                    $.each(rs.list, function(index, item){
                        str += '<tr data-id="'+ item.id +'" data-time="'+ item.time +'" data-type="'+ item.type +'" data-thing="'+ item.content +'">' +
                            '<td>'+ item.number +'</td>' +
                            '<td>'+ item.nextQuestTime +'</td> ' +
                            '<td>'+ item.type +'</td> ' +
                            '<td>'+ item.content +'</td>' +
                            '<td><a href="/admin/client/quest/add?id='+ item.id +'" class="label-info J_deal" data-target="#dealDialog"><i class="fa fa-book"></i>&nbsp;处理</a></td>' +
                            '</tr>'
                    });
                    $('#J_template').append(str);
                    //暂无数据提示
                    var tr = $('#J_template').children();
                    if(tr.length == 0){
                        $('#J_template').append('<tr><td colspan="5">暂无数据</td></tr>');
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
     * 表格处理按钮
     * @param  {[type]} e){                 } [description]
     * @return {[type]}      [description]
     */
    $(document).on('click', '.J_deal', function(e){
        var
            tr = $(e.target).parents('tr'),
            id = $(tr).attr('data-id');
        $('#dealDialog').modal();
        $('.hidId').val(id);
    });

    /**
     * 对话框确定按钮
     * @param  {[type]} e){                 } [description]
     * @return {[type]}      [description]
     */
    $('.J_dealDlg').click(function(){
        del();
    });

    /**
     * 确定事件
     */
    function del(){
        var
            id = $('.hidId').val(),
            dealId = {
                id: id
            };

        $.ajax({
            type: "GET",
            url: "/admin/client/quest/add",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: dealId,     //JSON.stringify
            dataType: "json",
            success: function (rs) {
                $('#dealDialog').modal('hide');
                if( rs.code == 0){
                    // console.log(1);
                    location.reload();
                }
                else{
                    $('#modalDialog').modal();
                }
            },
            error: function (message) {
                $('#delDialog').modal('hide');
                $('#modalDialog').modal();
            }
        });
    }

});