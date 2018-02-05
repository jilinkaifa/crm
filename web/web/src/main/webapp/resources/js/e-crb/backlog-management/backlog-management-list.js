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
            url: "/admin/event/affair/prepare/ajax/list",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: {page: currentPage},     //JSON.stringify
            dataType: "json",
            cache:false,
            success: function (rs) {
                $('#pageLimit').bootstrapPaginator({totalPages: rs.dataCount});
                $('#J_template').empty();
                if( rs.code == 0){
                    $('.J_record').html(rs.numCount);
                    $.each(rs.list, function(index, item){
                        str += '<tr data-id="'+ item.id +'">\
                                    <td>'+ item.number +'</td>\
                                    <td>'+ item.name +'</td>\
                                    <td>'+ new Date(item.createdTime).format("yyyy-MM-dd") +'</td>\
                                    <td>'+ item.approvalStatusValue +'</td>\
                                    <td class="control-width width3">\
                                        <div title=" ' + item.approvalComment + ' " class="control-width width3">' + item.approvalComment + '</div>\
                                    </td>\
                                    <td>';
                        if(item.approvalStatus == 1){
                            str += '<a href="/admin/event/prepare/detail?id='+ item.id +'" class="label-info J_see"><i class="fa fa-book"></i>&nbsp;查看</a>\
                                    <shiro:checkPermission name="Admin:E-CRB:Event:Prepare:Approval">\
                                    <a href="#" class="label-info J_deal" data-toggle="modal" data-target="#approveDialog"><i class="fa fa-edit"></i>&nbsp;审批</a>\
                                    </shiro:checkPermission>';
                        }else{
                            str += '<a href="/admin/event/prepare/detail?id='+ item.id +'" class="label-info J_see"><i class="fa fa-book"></i>&nbsp;查看</a>\
                                    <shiro:checkPermission name="Admin:E-CRB:Event:Prepare:Update">\
                                    <a href="/admin/event/prepare/update?id='+ item.id +' " class="label-info"><i class="fa fa-pencil"></i>&nbsp;修改</a>\
                                    </shiro:checkPermission>';
                        }
                        str += '</td></tr>';
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


    /**
     * 操作审批点击事件
     */

    $(document).on('click', '.J_deal', function(e){
        var
            tr = $(e.target).parents('tr'),
            id = $(tr).attr('data-id');

        $('.hidId').val(id);
    });

    /**
     * 操作审批按钮

     */
    $(document).on('click', '.J_approveDlg', function() {
        approve();
    });
    /**
     * 审批事件
     */

    function approve() {
        //alert("11111111111111111111");
        var
            id = $(".hidId").val(),
            status=$(".J_examineApprove").val(),
            comment=$(".J_remarks").val(),
            form = {
                id: id,
                status:status,
                comment:comment
            };
        //alert("id="+id);
        //alert("status="+status);
        //alert("comment="+comment);
        //alert("22222222222222222");
        $.ajax({
            type: "POST",
            url: "/admin/event/prepare/ajax/approval",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: form,
            //JSON.stringify
            dataType: "json",
            success: function(rs) {
                $('#approveDialog').modal('hide');
                if (rs.code == 0) {
                    location.reload();
                } else {
                    $('#modalDialog').modal();
                }

            },
            error: function(message) {
                $('#approveDialog').modal('hide');
                $('#modalDialog').modal();
            }
        });
    }

});