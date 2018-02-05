/*-----------------------------------------------------------------------------
* @Description:     系统管理-会员组管理
* @Version:         1.0.0
* @author:          lily(529116421@qq.com)
* @date             2017.7.19
* ==NOTES:=============================================
* v1.0.0(2017.7.19):
     初始生成
* ---------------------------------------------------------------------------*/
$(function(){
    /**
     * 初始化提示信息、验证表单
     */
    // showTip();
    formValidatorEditForm();
    formValidatorAddForm();
    Pagination(1); 
 
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
            Pagination(page);
        }
    });

    /**
     * 分页刷数据
     */
    function Pagination(page){

        var
            pageNo = page,
            str = '';

        $.ajax({
            type: "GET",
            url: "/admin/party/member/group/ajax/list",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: {pageNo: pageNo},     //JSON.stringify
            dataType: "json",
            cache:false,
            success: function (rs) {
                $('#J_template').empty();
                if( rs.code == 0){  
                    $('.J_record').html(rs.numCount);
                    $.each(rs.list, function(index, item){
                        str += '<tr data-id="'+ item.id +'" data-group-name="'+ item.name +'" data-group-remark="'+ item.comment +'">\
                                    <td>'+ item.name +'</td>\
                                    <td>'+ item.comment +'</td>\
                                    <td>\
                                        <shiro:checkPermission name="Admin:SAdmin:Party:Member:Group:Update">\
                                        <a href="#" class="label-info J_edit" data-toggle="modal" data-target="#editDialog"><i class="fa fa-pencil"></i>&nbsp;编辑</a>\
                                        </shiro:checkPermission>\
                                        <shiro:checkPermission name="Admin:SAdmin:Party:Member:Group:Del">\
                                        <a href="#" class="label-info J_del" data-toggle="modal" data-target="#delDialog"><i class="fa fa-times"></i>&nbsp;删除</a>\
                                        </shiro:checkPermission>\
                                    </td>\
                                </tr>'
                    }); 
                    $('#J_template').append(str);
                    //暂无数据提示
                    var tr = $('#J_template').children();
                    if(tr.length == 0){
                        $('#J_template').append('<tr><td colspan="3">暂无数据</td></tr>');
                    }
                }else{                
                    location.reload();
                }
                $('#pageLimit').bootstrapPaginator({totalPages: rs.dataCount});
            },
            error: function (message) {
                location.reload();
            }
        });
    }

    /**
     * 表格编辑按钮
     * @param  {[type]} e){                 } [description]
     * @return {[type]}      [description]
     */
    $(document).on('click', '.J_edit', function(e){
        var
            tr = $(e.target).parents('tr'),
            id = $(tr).attr('data-id'),
            groupName = $(tr).attr('data-group-name'),
            groupRemark = $(tr).attr('data-group-remark');

        $('.hidId').val(id);
        $('.J_editGroupName').val(groupName);
        $('.J_editGroupRemark').val(groupRemark);
    });
    /**
     * 对话框编辑按钮
     * @param  {[type]} e){                 } [description]
     * @return {[type]}      [description]
     */
    $('.J_editDlg').click(function(){
        var data = $('.J_editForm').data('bootstrapValidator');
        if (data) {
        // 修复记忆的组件不验证
            data.validate();

            if (!data.isValid()) {
                return false;
            }
        }
        edit();
    });
    /**
     * 对话框添加按钮
     * @param  {[type]} e){                 } [description]
     * @return {[type]}      [description]
     */
    $('.J_addDlg').click(function(){
        var data = $('.J_addForm').data('bootstrapValidator');
        if (data) {
        // 修复记忆的组件不验证
            data.validate();

            if (!data.isValid()) {
                return false;
            }
        }
        add();
    });
    /**
     * 表格删除按钮
     * @param  {[type]} e){                 } [description]
     * @return {[type]}      [description]
     */
    $(document).on('click', '.J_del', function(e){
        var
            tr = $(e.target).parents('tr'),
            id = $(tr).attr('data-id');

        $('.hidId').val(id);
    });
    /**
     * 对话框删除按钮
     * @param  {[type]} e){                 } [description]
     * @return {[type]}      [description]
     */
    $('.J_delDlg').click(function(){
        del();
    });
    /**
     * 编辑事件
     */
    function edit(){
        var
            page = $('#pageLimit li.active').text(),
            id = $('.hidId').val(),
            groupName = $('.J_editGroupName').val(),
            groupRemark = $('.J_editGroupRemark').val(),
            form = {
                id: id,
                name: groupName,
                comment: groupRemark
            };

        $.ajax({
            type: "PUT",
            url: "/admin/party/member/group/ajax/update",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: form,     //JSON.stringify
            dataType: "json",
            success: function (rs) {
                $('#editDialog').modal('hide');
                if( rs.code == 0){
                    Pagination(page);
                }else{                
                    $('#modalDialog').modal();
                }
            },
            error: function (message) {
                $('#editDialog').modal('hide');
                $('#modalDialog').modal();
            }
        });
    }
    /**
     * 添加事件
     */
    function add(){
        var
            groupName = $('.J_addGroupName').val(),
            groupRemark = $('.J_addGroupRemark').val(),
            form = {
                name: groupName,
                comment: groupRemark
            };

        $.ajax({
            type: "POST",
            url: "/admin/party/member/group/ajax/new",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: form,     //JSON.stringify
            dataType: "json",
            success: function (rs) {
                $('#addDialog').modal('hide');
                if( rs.code == 0){                   
                    location.reload();
                }else{                
                    $('#modalDialog').modal();
                }
            },
            error: function (message) {
                $('#addDialog').modal('hide');
                $('#modalDialog').modal();
            }
        });
    }
    /**
     * 删除事件
     */
    function del(){
        var
            id = $('.hidId').val(),
            form = {
                id: id
            };

        $.ajax({
            type: "POST",
            url: "/admin/party/member/group/ajax/del",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: form,     //JSON.stringify
            dataType: "json",
            success: function (rs) {
                $('#delDialog').modal('hide');
                if( rs.code == 0){                   
                    location.reload();
                }else{                
                    $('#modalDialog').modal();
                }
            },
            error: function (message) {
                $('#delDialog').modal('hide');
                $('#modalDialog').modal();
            }
        });
    }

    /**
     * 关闭编辑对话框清除校验
     * @param  {[type]} ) {                       } [description]
     * @return {[type]}   [description]
     */
    $('#editDialog').on('hidden.bs.modal', function() {
        $(".J_editForm").data('bootstrapValidator').destroy();
        $('.J_editForm').data('bootstrapValidator', null);
        formValidatorEditForm();
    });

    /**
     * 关闭添加对话框清除校验、数据
     * @param  {[type]} ) {                       } [description]
     * @return {[type]}   [description]
     */
    $('#addDialog').on('hidden.bs.modal', function() {
        $('.J_addForm').bootstrapValidator('resetForm', true);
        $(".J_addForm").data('bootstrapValidator').destroy();
        $('.J_addForm').data('bootstrapValidator', null);
        formValidatorAddForm();
    });

    /**
     * 添加框验证
     */
    function formValidatorAddForm(){
        $('.J_addForm').bootstrapValidator({
            message: 'This value is not valid',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                groupName: {
                    validators: {
                        notEmpty: {
                            message: '会员组组名不能为空'
                        }
                    }
                },
                groupRemark: {
                    validators: {
                        notEmpty: {
                            message: '备注不能为空'
                        }
                    }
                }
            }
        });
    }

    /**
     * 编辑框验证
     */
    function formValidatorEditForm(){
        $('.J_editForm').bootstrapValidator({
            message: 'This value is not valid',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                groupName: {
                    validators: {
                        notEmpty: {
                            message: '会员组组名不能为空'
                        }
                    }
                },
                groupRemark: {
                    validators: {
                        notEmpty: {
                            message: '备注不能为空'
                        }
                    }
                }
            }
        });
    }
    
});