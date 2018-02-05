 /*-----------------------------------------------------------------------------
* @Description:     权限管理-用户管理
* @Version:         1.0.0
* @author:          gts(616603151@qq.com)
* @date             2017.7.19
* ==NOTES:=============================================
* 
* ---------------------------------------------------------------------------*/
$(document).ready(function() {

    $('.J_del').click(function(e) {
        var tr = $(e.target).parents('tr'),
            id = $(tr).attr('data-id');
        $('.hidId').val(id);
    });

    /**
     * 初始化提示信息、验证表单
     */
    Pagination(1,{});
    // showTip();

    function showTip(){
        setTimeout(function(){
            $('.J_tip').hide();
        }, 2000);
    }
    /**
     * 操作点击事件
    */

    $(document).on('click', '.J_del', function(e) {
        var tr = $(e.target).parents('tr'),
        id = $(tr).attr('data-id');
        $('.hidId').val(id);
        console.log(id);
    });
     /**
     * 操作删除按钮
     
     */
    $('.J_delDlg').click(function() {
        del();
    });

    /**
     * 删除事件
     */

    function del() {
        var id = $(".hidId").val(),
        form = {
            id: id
        };

        $.ajax({
            type: "POST",
            // url: jQuery.url.AuthorityManagement.delUser,
            url: "/admin/party/group/employee/ajax/del",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: form,
            dataType: "json",
            success: function(rs) {
                $('#delDialog').modal('hide');
                if (rs.code == 0) {
                    location.reload();
                } else {
                    $('#modalDialog').modal();
                }

            },
            error: function(message) {
                $('#delDialog').modal('hide');
                $('#modalDialog').modal();
            }
        });
    }


    /**
     * 全选事件
     */
    $(function() {
        $("#checkAll").click(function() {
            $('input[name="subBox"]').prop("checked", this.checked);
        });
        $(document).on('click', "input[name='subBox']", function() {
            var $subBox = $("input[name='subBox']");
            $("#checkAll").prop("checked", $subBox.length == $("input[name='subBox']:checked").length ? true: false);
        });
    });

    /**
     * 不能批量添加
     */
    $('.J_add').click(function(){
        if($("input[type='checkbox']:checked").length != 0){
            Alert("提示信息", "选中多选按钮后不能添加用户", function(){
                $("input[type='checkbox']").removeAttr('checked');
            })
        }else{
            window.location.href =  "/admin/party/group/employee/new";
        }
    });


    /**
     * 批量删除事件
     */
    $("#del_model").click(function() {
        // 判断是否至少选择一项
        var checkedNum = $("input[name='subBox']:checked").length;
        if (checkedNum == 0) {
            $("#modalDel").modal();
            //return;
        } else {
            $("#modalAll").modal();
        }
    });

    // 批量选择 
    $(".J_delAllDlg").click(function() {
        // 判断是选择多项
        var checkedNum = $("input[name='subBox']:checked").length;
        if (checkedNum >= 1) {
            var subBoxList = $("input[name='subBox']:checked");
            var subBoxValue = [];
            console.log(subBoxList);
            if (subBoxList.length > 0) {
                for (var i = 0; i < subBoxList.length; i++) {
                    subBoxValue[i] = subBoxList.get(i).value;
                }

                var param = {
                    idList: subBoxValue.toString()
                };

                $.ajax({
                    type: "GET",
                    // url: jQuery.url.ClientManagement.batchDelete,
                    url: "/admin/party/group/employee/ajax/batchDelete",
                    contentType: "application/x-www-form-urlencoded; charset=utf-8",
                    data: param,
                    dataType: "json",
                    success: function(rs) {
                        $('#modalAll').modal('hide');
                        if (rs.code == 0) {
                            location.reload();
                        } else {
                            $('#modalDialog').modal();
                        }

                    },
                    error: function(message) {
                        $('#modalAll').modal('hide');
                        $('#modalDialog').modal();
                        console.log(message);
                    }
                });
            }
        }

    });
    /**
     * 批量修改事件
     */
    $("#edit_model").click(function() {
        // 判断是否至少选择一项
        var checkedNum = $("input[name='subBox']:checked").length;
        if (checkedNum == 0) {
            $("#modalDel").modal();
            //return;
        } else {
            $("#editDialog").modal();
        }
    });
    // 批量选择 
    $(".J_editDlg").click(function() {
        // 判断是选择多项
        var checkedNum = $("input[name='subBox']:checked").length;
        if (checkedNum >= 1) {
            var subBoxList = $("input[name='subBox']:checked");
            var subBoxValue = [];
            if (subBoxList.length > 0) {
                for (var i = 0; i < subBoxList.length; i++) {
                    subBoxValue[i] = subBoxList.get(i).value;
                }
                var userType = $("#customerType option:selected").val();
                var dicDepartment = $("#department option:selected").val();
                var dicPosition = $("#position option:selected").val();
                var dicImmediateS = $("#lead option:selected").val();

                var param = {
                    // idList: JSON.stringify(subBoxValue),
                    idList: subBoxValue.toString(),
                    userType: userType,
                    dicDepartment: dicDepartment,
                    dicPosition: dicPosition,
                    dicImmediateS: dicImmediateS
                };

                $.ajax({
                    type: "POST",
                    // url: jQuery.url.ClientManagement.batchUpdate,
                    url: "/admin/party/group/employee/ajax/batchUpdate",
                    contentType: "application/x-www-form-urlencoded; charset=utf-8",
                    data: param,
                    dataType: "json",
                    success: function(rs) {
                        $('#editDialog').modal('hide');
                        if (rs.code == 0) {
                            location.reload();
                        } else {
                            $('#modalDialog').modal();
                        }

                    },
                    error: function(message) {
                        $('#editDialog').modal('hide');
                        // $('#modalDialog').modal();
                    }

                });
            }
        }

    });
    /**
     * 批量分配角色事件
     */
    $("#allot_model").click(function() {
        // 判断是否至少选择一项
        var checkedNum = $("input[name='subBox']:checked").length;
        if (checkedNum == 0) {
            $("#modalDel").modal();
            //return;
        } else {
            $("#allotDialog").modal();
        }
    });

    $(".J_role").hover(function () {
        if($(".J_role option:selected").val() == 3){
            $(".J_group").removeClass("hidden");
        }else{
            $(".J_group").addClass("hidden");
        }
    });

    // 批量选择 
    $(".J_allotDlg").click(function() {
        // 判断是选择多项
        var checkedNum = $("input[name='subBox']:checked").length;
        if (checkedNum >= 1) {
            var subBoxList = $("input[name='subBox']:checked");
            var subBoxValue = [];
            if (subBoxList.length > 0) {
                for (var i = 0; i < subBoxList.length; i++) {
                    subBoxValue[i] = subBoxList.get(i).value;
                }

                var role = $("#role option:selected").val();

                var param = {
                    idList: subBoxValue.toString(),
                    role: role,
                    memberGroupId :  $("#memberGroup option:selected").val()
                };
                console.log(param);

                $.ajax({
                    type: "get",
                    // url: jQuery.url.ClientManagement.batchRole,
                    url: "/admin/party/group/employee/ajax/setRole",
                    contentType: "application/x-www-form-urlencoded; charset=utf-8",
                    data: param,
                    dataType: "json",
                    success: function(rs) {
                        $('#allotDialog').modal('hide');
                        if (rs.code == 0) {
                            location.reload();
                        } else if(rs.code == 2){
                           Alert("提示信息","所选用户列表有正处于登录状态的用户，不能更新角色,请重新选择！");
                        } else {
                            $('#modalDialog').modal();
                        }

                    },
                    error: function(message) {
                        $('#allotDialog').modal('hide');
                        $('#modalDialog').modal();
                    }

                });
            }
        }

    });

    /**
     * 点击搜索事件
     * @param  {[type]} ){                     search();    } [description]
     * @return {[type]}     [description]
     */

    $(".J_search").click(function(){
        var
            form = $(".J_searchForm").serializeObject();

        Pagination(1, form);
        $('#pageLimit').bootstrapPaginator({currentPage: 1});
    });

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
            Pagination(page,{});
        }
    });


    /**
     * 分页刷数据
     */
    function Pagination(page, extraData){

        var
            currentPage = page,
            str = '',
            data = {
                pageNo: currentPage
            };
        jQuery.extend(data, extraData);

        $.ajax({
            type: "GET",
            // url: jQuery.url.AuthorityManagement.systemUserList,
            url: "/admin/party/group/employee/ajax/search",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: data,
            dataType: "json",
            cache:false,
            success: function (rs) {
                if( rs.code == 0){
                        $('.J_record').html(rs.numCount);
                        $('#J_template').empty();
                        $.each(rs.list, function(index, item){
                            str += '<tr data-id="'+ item.id +'" >\
                                    <td>'+'<input type="checkbox" name="subBox" value=" '+item.id + '"/>'+'</td>\
                                    <td>'+ item.name +'</td>\
                                    <td>'+ item.phone+'</td>\
                                    <td>'+ item.roleName+'</td>\
                                    <td>'+ new Date(item.createdTime).format("yyyy-MM-dd hh:mm:ss")+'</td>\
                                    <td>'+ item.userTypeValue +'</td>\
                                    <td>'+ item.department  +'</td>\
                                    <td>'+ item.position  +'</td>\
                                    <td>'+ item.immediateS  +'</td>\
                                    <td>'+ item.statusValue+'</td>\
                                    <td>\
                                        <shiro:checkPermission name="Admin:SAdmin:User:Management:Employee:Update">\
                                        <a href="/admin/party/group/employee/edit?id=' + item.id + '"  class="label-info J_edit"><i class="fa fa-pencil"></i>&nbsp;编辑</a>\
                                        </shiro:checkPermission>\
                                         <shiro:checkPermission name="Admin:SAdmin:User:Management:Employee:Detail">\
                                        <a href="/admin/party/group/employee/detail?id=' + item.id + '" class="label-info J_details"><i class="fa fa-book"></i>&nbsp;详情</a>\
                                        </shiro:checkPermission>\
                                        <shiro:checkPermission name="Admin:SAdmin:User:Management:Employee:Delete">\
                                        <a href="#" class="label-info J_del" data-toggle="modal" data-target="#delDialog"><i class="fa fa-times"></i>&nbsp;删除</a>\
                                        </shiro:checkPermission>\
                                    </td>\
                                </tr>'
                        });
                        $('#J_template').append(str);
                        //暂无数据提示
                        var tr = $('#J_template').children();
                        if(tr.length == 0){
                            $('#J_template').append('<tr><td colspan="11">暂无数据</td></tr>');
                        }
                    $('#pageLimit').bootstrapPaginator({totalPages: rs.dataCount});
                }else{
                    Alert("提示信息","数据刷新失败！");
                }

            },
            error: function (message) {
                Alert("提示信息","数据刷新失败！");
            }
        });
    }

});