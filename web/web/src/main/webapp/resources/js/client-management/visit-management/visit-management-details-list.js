/*-----------------------------------------------------------------------------
* @Description:     来访管理
* @Version:         1.0.0
* @author:          sunwanlin(1124038074@qq.com)
* @date             2017.7.24
* ==NOTES:=============================================
* v1.0.0(2017.7.24):
     初始生成
* ---------------------------------------------------------------------------*/
$(function(){
    /**
     * 初始化提示信息、验证表单
     */
    formValidatorEditForm();
    formValidatorAddForm();
    Pagination(1,{});
    //来访日期验证是否为空
    $('.J_visitTimeValid').click(function(){
        validDate();
    })
    /***************************************************以下是分页相关√***********************************************************/
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
            url: "/admin/client/visit/ajax/detailList",
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
                                        str += '<tr data-id="'+ item.id +'" data-number="'+ item.number +'" data-clientName="'+ item.clientName +'" data-visitFrom="'+ item.visitFrom +'" data-visitTime="'+ new Date(item.visitTime).format("yyyy-MM-dd") +'" data-visitContent="'+ item.visitContent +'" data-cost="'+ item.cost +'" data-partyName="'+ item.partyName +'" data-lastModifiedTime="'+ item.lastModifiedTime +'">\
                                                    <td>'+ item.number +'</td>\
                                                    <td>'+ item.clientName +'</td>\
                                                    <td>'+ item.visitFrom +'</td>\
                                                    <td>'+ new Date(item.visitTime).format("yyyy-MM-dd") +'</td>\
                                                    <td>'+ item.visitContent +'</td>\
                                                    <td>'+ item.cost +'</td>\
                                                    <td>'+ item.partyName +'</td>\
                                                    <td>'+ new Date(item.lastModifiedTime).format("yyyy-MM-dd hh:mm:ss") +'</td>\
                                                    <td>\
                                                        <shiro:checkPermission name="Admin:Client:Visit:DetailUpdate">\
                                                        <a href="#" class="label-info J_edit" data-toggle="modal" data-target="#editDialog"><i class="fa fa-pencil"></i>&nbsp;修改</a>\
                                                        </shiro:checkPermission>\
                                                        <shiro:checkPermission name="Admin:Client:Visit:DetailDelete"> \
                                                        <a href="#" class="label-info J_del" data-toggle="modal" data-target="#delDialog"><i class="fa fa-times"></i>&nbsp;删除</a>\
                                                        </shiro:checkPermission>\
                                                    </td>\
                                                </tr>'
                                    });
                    $('#J_template').append(str);
                    //暂无数据提示
                    var tr = $('#J_template').children();
                    if(tr.length == 0){
                        $('#J_template').append('<tr><td colspan="9">暂无数据</td></tr>');
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
/***************************************************以下是删除相关√***********************************************************/
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
     * 删除弹框中的“确定删除”按钮
     * @param  {[type]} e){                 } [description]
     * @return {[type]}      [description]
     */
    $('.J_delDlg').click(function(){
        del();
    });
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
            url: "/admin/client/visit/ajax/del",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: form,     //JSON.stringify
            dataType: "json",
            success: function (rs) {
                $('#delDialog').modal('hide');
                if( rs.code == 0){
                    Alert("提示信息","删除成功");
                    location.reload();
                }else{
                    Alert("提示信息",rs.errMsg);
                }
            },
            error: function (message) {
                $('#delDialog').modal('hide');
                $('#modalDialog').modal();
            }
        });
    }
/***************************************************以下是查询相关√***********************************************************/
    /**
     * 列表点击查询事件
     * @param  {[type]} ){                     }
     * @return {[type]}     [description]
     */
    $(".J_search").click(function(){
        var
            form = $(".J_searchForm").serializeObject(),
            id =$('.clientId').val();
        jQuery.extend(form,{clientId:id});
        Pagination(1, form);
        $('#pageLimit').bootstrapPaginator({currentPage: 1});
    });
/***************************************************以下是添加相关***********************************************************/
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
                visitFrom: {
                    validators: {
                        notEmpty: {
                            message: '来访来源不能为空！'
                        },
                        regexp: {
                            regexp: /^[0-9]\d*$/,
                            message: ' '
                        }
                    }
                },
                cost: {
                    validators: {
                        notEmpty: {
                            message: '消费金额输入不正确，请重新输入！'
                        },
                        regexp: {
                            regexp: /^\d+(\.\d+)?$/,
                            message: ' '
                        },
                        stringLength: {
                            min:1,
                            max:8,
                            message: ''
                        }
                    }
                },
                visitContent: {
                    validators: {
                        notEmpty: {
                            message: '来访内容不能为空！'
                        }
                    }
                }
            }
        });
    }
    /**
     * 验证来访日期是否为空（手动验证）
     */
    function validDate(){
        var visitTime = $('.J_addVisitTime').val();
        if(visitTime == ''){
            if($('.time').length == 0){
                $('.J_addVisitTime').after('<small class="time has-error help-block" data-bv-validator="notEmpty" data-bv-for="name" data-bv-result="INVALID" style="color:#a94442;">来访日期不能为空</small>');
            }
        return false;
        }else{
            if($('.time').length > 0){
                $('.time').remove();
            }
        return true;
        }
    }
    /**
     * 添加事件
     */
    function add(){
        var
            form = $('.J_addForm').serializeObject(),
            id =$('.clientId').val();
            jQuery.extend(form,{clientId:id});
        $.ajax({
            type: "POST",
            url: "/admin/client/visit/ajax/add",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: form,     //JSON.stringify
            dataType: "json",
            success: function (rs) {
                $('#addDialog').modal('hide');
                if( rs.code == 0){
                    //console.log("发送成功验证成功");
                    //Pagination(page, form);
                    Alert("提示信息","添加成功")
                    location.reload();
                }else{   
                    Alert("添加失败",rs.errMsg);
                    $('#modalDialog').modal();
                }

            },
            error: function (message) {
                console.log("发送失败");  
                $('#addDialog').modal('hide');
                $('#modalDialog').modal();
            }
        });
    }
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
            }else if(validDate()){
                add();
            }
        }
    });
    /**
     * 关闭添加对话框清除校验、数据
     * @param  {[type]} ) {                       } [description]
     * @return {[type]}   [description]
     */
    $('#addDialog').on('hidden.bs.modal', function() {
        //$('.J_addForm').bootstrapValidator('resetForm', true);
        $('.J_addVisitTime').val('');
        $('.J_addVisitFromInput').val('');
        $('.J_addCostInput').val('');
        $('.J_addVisitContentInput').val('');

        //$('.J_addRadio1').prop("checked", true);
        $(".J_addForm").data('bootstrapValidator').destroy();
        $('.J_addForm').data('bootstrapValidator', null);
        formValidatorAddForm();
    });
/***************************************************以下是编辑相关***********************************************************/
    /**
     * 获取下拉框中的文字
     * @return {[type]} [description]
     */
    /*function getSelectVal(){
        var selectVal = $("option").val
    }*/
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
                visitFrom: {
                    validators: {
                        notEmpty: {
                            message: '来访来源不能为空！'
                        },
                        regexp: {
                            regexp: /^[0-9]\d*$/,
                            message: ' '
                        }
                    }
                },
                visitContent: {
                    validators: {
                        notEmpty: {
                            message: '来访内容不能为空！'
                        }
                    }
                },
                cost: {
                    validators: {
                        notEmpty: {
                            message: '消费金额输入不正确，请重新输入！'
                        },
                        regexp: {
                            regexp: /^\d+(\.\d+)?$/,
                            message: ' '
                        },
                        stringLength: {
                            min:1,
                            max:8,
                            message: ''
                        }
                    }
                }
            }
        });
    }
    /**
     * 编辑事件
     */
    function edit(){
        var
            page = $('#pageLimit li.active').text(),
            id = $('.hidId').val(),
            form = $('.J_editForm').serializeObject();
            
        jQuery.extend(form, {
            id: id
        })

        $.ajax({
            type: "POST",
            url: "/admin/client/visit/ajax/update",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: form,     //JSON.stringify
            dataType: "json",
            success: function (rs) {
                $('#editDialog').modal('hide');
                if( rs.code == 0){                   
                    //Pagination(page, form);
                    Confirm("提示消息","编辑成功",function () {
                        location.reload();/*不要加reload，不然不显示修改成功信息*/
                    });
                }else{
                    Alert("编辑失败",rs.errMsg);
                }

            },
            error: function (message) {
                $('#editDialog').modal('hide');
                $('#modalDialog').modal();
            }
        });
    }
    /**
     * 弹出的对话框里的编辑按钮
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
            }else if(validEditDate()){
                edit();
            }
        }
    });
    /**
     * 验证修改的来访日期是否为空（手动验证）
     */
    function validEditDate(){
        var visitEditTime = $('.J_editVisitTime').val();
        if(visitEditTime == ''){
            if($('.time').length == 0){
                $('.J_editVisitTime').after('<small class="time has-error help-block" data-bv-validator="notEmpty" data-bv-for="name" data-bv-result="INVALID" style="color:#a94442;">来访日期不能为空</small>');
            }
            return false;
        }else{
            if($('.time').length > 0){
                $('.time').remove();
            }
            return true;
        }
    }
    /**
     * 表格中的编辑按钮
     * @param  {[type]} e){                 } [description]
     * @return {[type]}      [description]
     */
    $(document).on('click','.J_edit',function(e){
        var
            tr = $(e.target).parents('tr'),
            id = $(tr).attr('data-id'),
            number = $(tr).attr('data-number'),
            clientName = $(tr).attr('data-clientName'),
            visitFrom = $(tr).attr('data-visitFrom'),
            visitTime = $(tr).attr('data-visitTime'),
            visitContent = $(tr).attr('data-visitContent'),
            cost = $(tr).attr('data-cost'),
            partyName = $(tr).attr('data-partyName');

        $('.hidId').val(id);
        $('.J_editNameInput').val(clientName);
        if(visitFrom == "门诊"){
            $('.J_editVisitFromInput').val(0);
        }else if(visitFrom == "住院"){
            $('.J_editVisitFromInput').val(1);
        }else{
            $('.J_editVisitFromInput').val(-1);
        }
        $('.J_editVisitTime').val(visitTime);
        $('.J_editVisitContentInput').val(visitContent);
        $('.J_editCostInput').val(cost);
        $('.J_editPartyNameInput').val(partyName);
        /*if( radio == 0){
            $('.J_editRadio1').prop('checked', true);
        }else{
            $('.J_editRadio2').prop('checked', true);
        }
        $('.J_editPeoInput').val(people);*/
    });
    /**
     * 关闭编辑对话框清除校验
     * @param  {[type]} ) {                       } [description]
     * @return {[type]}   [description]
     */
    $('#editDialog').on('hidden.bs.modal', function(){
        $(".J_editForm").data('bootstrapValidator').destroy();
        $('.J_editForm').data('bootstrapValidator', null);
        formValidatorEditForm();
    });
/***************************************************以下是模糊匹配√***********************************************************/
    /**
     * 模糊匹配-可输入也可下拉选择
     */
    $('#editable-select1').editableSelect1({
        effects: 'slide'  
    });
    $('#editable-select2').editableSelect2({
        effects: 'slide'  
    });
    $('#editable-select3').editableSelect3({
        effects: 'slide'  
    });
    $('#html1').editableSelect1();
    $('#html2').editableSelect2();
    $('#html3').editableSelect3();

    /**
     * 姓名-模糊匹配-keyup事件
     */
    $('.J_selectName').keyup(function(){
        var
            name = $('input.J_selectName').val();

        $(".es-list1").empty();     
        $.ajax({
            type: "GET",
            url: "/admin/client/visit/ajax/name",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: {name: name},     //JSON.stringify
            dataType: "json",
            success: function (rs) {
                var
                    li = "";
                $(rs.list).each(function(key, item){
                    li +='<li class="es-visible" style="display: block;">' + item.name + '</li>';                   
                    // console.log(rs.list);
                });
                $(".es-list1").append(li);
            },
            error: function (errMsg) {
                console.log(errMsg);
            }
        });
    });

    /**
     * 身份证-模糊匹配-keyup事件
     */
    $('.J_selectId').keyup(function(){
        var
            idcard = $('input.J_selectId').val();

        $(".es-list2").empty();     
        $.ajax({
            type: "GET",
            url: "/admin/client/visit/ajax/idcard",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: {idcard: idcard},     //JSON.stringify
            dataType: "json",
            success: function (rs) {
                var
                    li = "";

                $(rs.list).each(function(key, item){
                    li +='<li class="es-visible" style="display: block;">' + item.idcard + '</li>';                 
                    // console.log(li);
                });
                $(".es-list2").append(li);
            },
            error: function (errMsg) {
                console.log(errMsg);
            }
        });
    });
    
    /**
     * 电话-模糊匹配-keyup事件
     */
    $('.J_selectPhone').keyup(function(){
        var
            phone = $('input.J_selectPhone').val();

        $(".es-list3").empty();     
        $.ajax({
            type: "GET",
            url: "/admin/client/visit/ajax/tel",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: {tel: phone},     //JSON.stringify
            dataType: "json",
            success: function (rs) {
                var
                    li = "";

                $(rs.list).each(function(key, item){
                    li +='<li class="es-visible" style="display: block;">' + item.phone + '</li>';                  
                    // console.log(li);
                });
                $(".es-list3").append(li);
            },
            error: function (errMsg) {
                console.log(errMsg);
            }
        });
    });    
});