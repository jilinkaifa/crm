/*-----------------------------------------------------------------------------
* @Description:     标签管理
* @Version:         1.0.0
* @author:          zhangxn(571946808@qq.com)
* @date             2017.7.20
* ==NOTES:=============================================
* v1.0.0(2017.7.20):
     初始生成
* ==NOTES:=============================================
* v1.2.0(2017.7.26):     yudan(862669640@qq.com)
  修改function add(){}，修改function edit(){}，表单序列化改为一个个取值.
* ---------------------------------------------------------------------------*/
$(function(){
    /**
     * 初始化提示信息、验证表单
     */
    $('#Menu2,#logoMenu2').trigger('click');
    formValidatorEditForm();
    formValidatorAddForm();
    Pagination(1,{});


    /**
     *
     * 取消提示
     */
    function showTip(){
        setTimeout(function(){
            $('.J_tip').hide();
        }, 2000);
    }

    /**
     * 确认刷新
     */
    $('.J_okay').click(function () {
        location.reload();
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
            currentPage = page,            
            str = '',
            data = {
                pageNo: currentPage
            };
        jQuery.extend(data, extraData);
        $.ajax({
            type: "GET",
            url: "/admin/profile/label/type/ajax/query/list",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: data,     //JSON.stringify
            dataType: "json",
            cache:false,
            success: function (rs) {
                $('.pageDataCount').val(rs.dataCount);
                $('#J_template').empty();
                if( rs.code == 0){
                    $('.J_record').html(rs.numCount);
                    $.each(rs.list, function(index, item){
                        str += '<tr data-id="'+ item.id +'" data-tagType="'+ item.name +'" data-amount="'+ item.labelCount +'" data-content="'+ item.labelText +'" data-remarks="'+ item.labelComment +'" data-radio="'+ item.isMultiple +'" data-time="'+ item.createdTime +'" data-people="'+ item.createdName +'" >\
                                    <td><input type="checkbox" class="J_select" value="'+ item.id +'"></td>\
                                    <td>'+ item.number +'</td>\
                                    <td class="control-width width1">\
                                        <div title=" ' + item.name + ' " class="control-width width1">' + item.name + '</div>\
                                    </td>\
                                    <td>'+ item.labelCount +'</td>\
                                    <td title=" ' + item.labelText + ' " class="labelT"><div class="labelT">'+ item.labelText +'</div></td>\
                                    <td class="control-width width2">\
                                        <div title=" ' + item.labelComment + ' " class="control-width width2">' + item.labelComment + '</div>\
                                    </td>\
                                    <td>'+ item.isMultiplea +'</td>\
                                    <td>'+ item.createdTime +'</td>\
                                    <td>'+ item.createdName +'</td>\
                                    <td>\
                                        <shiro:checkPermission name="Admin:Profile:Label:Type:Update">\
                                        <a href="#" class="label-info J_edit" data-toggle="modal" data-target="#editDialog"><i class="fa fa-pencil"></i>&nbsp;修改</a>\
                                        </shiro:checkPermission>\
                                        <shiro:checkPermission name="Admin:Profile:Label:Type:List">\
                                        <a href="#" class="label-info J_detail" data-toggle="modal" data-target="#detailDialog"><i class="fa fa-book"></i>&nbsp;详情</a>\
                                        </shiro:checkPermission>\
                                        <shiro:checkPermission name="Admin:Profile:Label:Item:List">\
                                        <a href="#" class="label-info J_handle"><i class="fa fa-tag"></i>&nbsp;标签项操作</a> \
                                        </shiro:checkPermission>\
                                    </td>\
                                </tr>'
                    }); 
                    $('#J_template').append(str);
                    //暂无数据提示
                    var tr = $('#J_template').children();
                    if(tr.length == 0){
                        $('#J_template').append('<tr><td colspan="10">暂无数据</td></tr>');
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
     * 搜索标签
     */
    $('.J_search').click(function(){
        // var
            // name = $('.J_tagType').val();
        var form = $('.J_searchForm').serializeObject();

        Pagination(1, form);
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

    /**
     * 标签项操作按钮
     */
    $(document).on('click', '.J_handle', function(e){
        var
            id = $(e.target).parents('tr').attr('data-id'),
            tagType = $(e.target).parents('tr').attr('data-tagType');

        $('.J_tagId').val(id);
        $('.hidId').val(id);
        $('.tagType').val(tagType);

        render();

        // $.ajax({
        //     type: "GET",
        //     url: "/admin/profile/label/item/ajax/list",
        //     contentType: "application/x-www-form-urlencoded; charset=utf-8",
        //     data: {typeId: id},     //JSON.stringify
        //     dataType: "json",
        //     success: function (rs) {
        //         if( rs.code == 0){
        //             $('#handleDialog').modal();
        //             $('.J_tpl').empty();
        //             $('.J_newTag').empty();
        //             $.each(rs.list, function(index, item){
        //                 str += '<tr data-id="'+ item.id +'">\
        //                             <td>'+ tagType +'</td>\
        //                             <td>'+ item.dicLabelName +'</td>\
        //                             <td>\
        //                                 <a href="#" class="label-info J_del"><i class="fa fa-times"></i>&nbsp;删除</a>\
        //                             </td>\
        //                         </tr>'
        //
        //             });
        //             $.each(rs.selectList, function(index, item){
        //                 selectStr += '<option value="'+ item.id +'">'+ item.name +'</option>';
        //             });
        //
        //             $('.J_tpl').append(str);
        //             $('.J_newTag').append(selectStr);
        //         }else{
        //             $('#modalDialog').modal();
        //         }
        //
        //     },
        //     error: function (message) {
        //         $('#handleDialog').modal('hide');
        //         $('#modalDialog').modal();
        //     }
        // });
    });

    /**
     * 渲染对话框
     */
    function render() {
        var
            id = $('.J_tagId').val(),
            tagType = $('.tagType').val(),
            str = '',
            selectStr = '';

        $.ajax({
            type: "GET",
            url: "/admin/profile/label/item/ajax/list",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: {typeId: id},     //JSON.stringify
            dataType: "json",
            success: function (rs) {
                if( rs.code == 0){
                    $('#handleDialog').modal();
                    $('.J_tpl').empty();
                    $('.J_newTag').empty();
                    $.each(rs.list, function(index, item){
                        str += '<tr data-id="'+ item.id +'">\
                                    <td>'+ tagType +'</td>\
                                    <td>'+ item.dicLabelName +'</td>\
                                    <td>\
                                        <shiro:checkPermission name="Admin:Profile:Label:Item:Delete">\
                                        <a href="#" class="label-info J_del"><i class="fa fa-times"></i>&nbsp;删除</a>\
                                        </shiro:checkPermission>\
                                    </td>\
                                </tr>'

                    });
                    $.each(rs.selectList, function(index, item){
                        selectStr += '<option value="'+ item.id +'">'+ item.name +'</option>';
                    });

                    $('.J_tpl').append(str);
                    $('.J_newTag').append(selectStr);
                }else{
                    $('#modalDialog').modal();
                }

            },
            error: function (message) {
                $('#handleDialog').modal('hide');
                $('#modalDialog').modal();
            }
        });
    }

    /**
     * 新增标签项
     */
    $('#handleDialog').on('click', '.J_addTag', function(){
        var
            tagItem = $('.J_newTag').val(),
            id = $('.hidId').val(),
            tagType = $('.tagType').val(),
            tagItemText = $('.J_newTag option:selected').text(),
            partyId = $("input[name='partyId']").val();

        if( tagItem != null){
            $.ajax({
                type: "POST",
                url: "/admin/profile/label/item/ajax/new",
                contentType: "application/x-www-form-urlencoded; charset=utf-8",
                data: {dicLabelId: tagItem, labelTypeId: id, dicLabelName: tagItemText, createdPartyId: partyId},     //JSON.stringify
                dataType: "json",
                success: function (rs) {
                    if( rs.code == 0){
                        //$('.J_tpl').append('<tr data-id="'+ tagItem +'"><td>'+ tagType +'</td><td>'+ tagItemText +'</td><td><a href="#" class="label-info J_del"><i class="fa fa-times"></i>&nbsp;删除</a></td></tr>')
                        //$('.J_newTag').val('');
                        Alert('提示信息', '新增成功！', render);
                    }else{
                        $('#handleDialog').modal('hide');
                        $('#modalDialog').modal();
                    }

                },
                error: function (message) {
                    $('#modalDialog').modal();
                }
            });
        }else{
            Alert("提示信息", "请选择你要新增的标签项!");
        }
    });

    /**
     * 点击批量删除标签类型
     */
    $('.J_delAll').click(function(){
        delAll();
    });

    /**
     * 判断是否选择删除项
     */
    function delAll(){
        var
            select = $('.J_select:checked').length;

        if(select == 0){
            $('#delTipDialog').modal();
        }else{
            $('#delAllDialog').modal();
        }
    }

    /**
     * 批量删除对话框确定事件
     */
    $('.J_delAllDlg').click(function(){
        sendDel();
    });

    /**
     * 获取删除项并发送数据
     */
    function sendDel(){
        var
            selectMap = $('.J_select:checked'),
            idArray = [],
            data;
        $.each(selectMap, function(index, item){
            idArray.push(item.value);
        });

        data = {
            idStr: idArray.toString()
        };

        $.ajax({
            type: "POST",
            url: "/admin/profile/label/type/ajax/delSeveral",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: data,     //JSON.stringify
            dataType: "json",
            success: function (rs) {
                if( rs.code == 0){                   
                    location.reload();
                }else{
                    $('#modalDialog').modal();
                }

            },
            error: function (message) {
                $('#delAllDialog').modal('hide');
                $('#modalDialog').modal();
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
            tagType = $(tr).attr('data-tagType'),
            remarks = $(tr).attr('data-remarks'),
            radio = $(tr).attr('data-radio'),
            people = $(tr).attr('data-people'),
            amount = $(tr).attr('data-amount'),
            content = $(tr).attr('data-content');

        $('.hidId').val(id);
        $('.J_editAmountInput').val(amount);
        $('.J_editConInput').val(content);
        $('.J_editTagTypeInput').val(tagType);
        $('.J_editRemInput').val(remarks);
        if( radio == '1'){
            $('.J_editRadio1').prop('checked', true);
        }else{
            $('.J_editRadio2').prop('checked', true);
        }
        $('.J_editPeoInput').val(people);
    });

    /**
     * 对话框编辑按钮
     * @param  {[type]} e){                 } [description]
     * @return {[type]}      [description]
     */
    $('.J_editDlg').click(function(){
        var 
            data = $('.J_editForm').data('bootstrapValidator');
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
     * 表格详情按钮
     * @param  {[type]} e){                 } [description]
     * @return {[type]}      [description]
     */
    $(document).on('click', '.J_detail', function(e){
        var
            tr = $(e.target).parents('tr'),
            id = $(tr).attr('data-id'),
            tagType = $(tr).attr('data-tagType'),
            remarks = $(tr).attr('data-remarks'),
            radio = $(tr).attr('data-radio'),
            people = $(tr).attr('data-people'),
            content = $(tr).attr('data-content'),
            number = $(tr).attr('data-amount');

        //$('.hidId').val(id);
        // $('.hidExp').val(express);
        // $('.hidDes').val(description);
        $('.J_detailTagTypeInput').val(tagType);
        $('.J_detailRemInput').val(remarks);
        if( radio == '1'){
            $('.J_detailRadio1').prop('checked', true);
        }else{
            $('.J_detailRadio2').prop('checked', true);
        }
        $('.J_detailPeoInput').val(people);
        $('.J_detailContentInput').val(content);
        $('.J_detailNumberInput').val(number);
    });

    /**
     * 对话框添加按钮
     * @param  {[type]} e){                 } [description]
     * @return {[type]}      [description]
     */
    $('.J_addDlg').click(function(){
        var 
            data = $('.J_addForm').data('bootstrapValidator');
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
     * 表格删除按钮，事件委托
     * @param  {[type]} e){                 } [description]
     * @return {[type]}      [description]
     */
    $('#handleDialog').on('click', '.J_del', function(e){
        var
            tr = $(e.target).parents('tr'),
            id = $(tr).attr('data-id');

        $('.tagId').val(id);
        $('#delDialog').modal();
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
     * 编辑标签类型
     */
    function edit(){
        var
            page = $('#pageLimit li.active').text(),
            id = $('.hidId').val(),
            // form = $('.J_editForm').serialize();
            name = $('.J_editForm').find("input[name='name']").val(),
            labelCount = $('.J_editForm').find("input[name='labelCount']").val(),
            isMultiple = $('.J_editForm').find("input[name='isMultiple']:checked").val(),
            labelText = $('.J_editForm').find("input[name='labelText']").val(),
            partyId = $('.J_editForm').find("input[name='partyId']").val(),
            labelComment = $('.J_editForm').find("textarea[name='remarks']").val(),
            form = {
                name: name,
                labelCount: labelCount,
                isMultiple: isMultiple,
                labelText: labelText,
                partyId: partyId,
                labelComment:labelComment
            };

        jQuery.extend(form, {
            id: id
        })
        $.ajax({
            type: "POST",
            url: "/admin/profile/label/type/ajax/update",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: form,     //JSON.stringify
            dataType: "json",
            success: function (rs) {
                $('#editDialog').modal('hide');
                if( rs.code == 0){                   
                    Pagination(page, form);
                    location.reload();
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
     * 添加标签类型
     */
    function add(){
        // var form = $('.J_addForm').serializeObject();
        var name = $('.J_addForm').find("select[name='name']").val(),
            labelCount = $('.J_addForm').find("input[name='labelCount']").val();
            isMultiple = $('.J_addForm').find("input[name='isMultiple']:checked").val();
            labelText = $('.J_addForm').find("input[name='labelText']").val();
            partyId = $('.J_addForm').find("input[name='partyId']").val();
            labelComment = $('.J_addForm').find("textarea[name='labelComment']").val();
            // pageNo = pageNo;
            form = {
                name: name,
                labelCount: labelCount,
                isMultiple: isMultiple,
                labelText: labelText,
                createdPartyId: partyId,
                labelComment:labelComment
                // pageNo:pageNo
            };

        $.ajax({
            type: "POST",
            url: "/admin/profile/label/type/ajax/new" ,
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
     * 删除标签项
     */
    function del(){
        var
            id = $('.hidId').val(),
            tagId = $('.tagId').val(),
            form = {
                // id: id,
                tagId: tagId
            };

        $.ajax({
            type: "POST",
            url: "/admin/profile/label/item/ajax/del",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: form,     //JSON.stringify
            dataType: "json",
            success: function (rs) {
                $('#delDialog').modal('hide');
                if( rs.code == 0){
                    render();
                    //$('#handleDialog tr[data-id="'+ tagId +'"]').remove();
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
        //$('.J_addForm').bootstrapValidator('resetForm', true);
        $('.J_addTagTypeInput').val('');
        $('.J_addRemInput').val('');
        $('.J_addConInput').val('');
        $('.J_addRadio2').prop("checked", true);;
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
                name: {
                    validators: {
                        notEmpty: {
                            message: '标签类型不能为空'
                        }
                    }
                },
                labelCount: {
                    validators: {
                        notEmpty: {
                            message: '标签数量不能为空'
                        },
                        regexp: {
                            regexp: /^[0-9]*[1-9][0-9]*$/,
                            message: '标签数量必须是整数'
                        }
                    }
                },
                labelText: {
                    validators: {
                        notEmpty: {
                            message: '标签项简略不能为空'
                        }
                    }
                },
                creator: {
                    validators: {
                        notEmpty: {
                            message: '添加人不能为空'
                        }
                    }
                },
                labelComment: {
                    validators: {
                        stringLength: {
                            min: 0,
                            max: 100,
                            message: '备注不能超过100字'
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
                name: {
                    validators: {
                        notEmpty: {
                            message: '标签类型不能为空'
                        }
                    }
                },
                labelCount: {
                    validators: {
                        notEmpty: {
                            message: '标签数量不能为空'
                        },
                        regexp: {
                            regexp: /^[0-9]*[1-9][0-9]*$/,
                            message: '标签数量必须是整数'
                        }
                    }
                },
                labelText: {
                    validators: {
                        notEmpty: {
                            message: '标签项简略不能为空'
                        }
                    }
                },
                creator: {
                    validators: {
                        notEmpty: {
                            message: '添加人不能为空'
                        }
                    }
                },
                labelComment: {
                    validators: {
                        stringLength: {
                            min: 0,
                            max: 100,
                            message: '备注不能超过100字'
                        }
                    }
                }
            }
        });
    }
    
});