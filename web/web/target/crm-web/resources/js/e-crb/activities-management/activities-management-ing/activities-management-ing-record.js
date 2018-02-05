/*-----------------------------------------------------------------------------
 * @Description:     活动管理-进行中的活动
 * @Version:         1.0.0
 * @author:          gts(616603151@qq.com)
 * @date             2017.7.24
 * ==NOTES:=============================================
 *
 * ---------------------------------------------------------------------------*/
$(document).ready(function() {
    formValidatorForm();
    formValidatorDetailForm();
    Pagination(1);
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
            str = '',
            data = {
                pageNo: currentPage,
                clientId: $(".clientId").val(),
                eventRecordNoticeId: $(".eventRecordNoticeId").val()
            };

        $.ajax({
            type: "POST",
            // url: jQuery.url.ECRBManagement.recordList,
            url: "/admin/event/processing/ajax/record/list",
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
                                        <div title=" ' + item.name + ' " class="control-width width150">' + item.name + '</div>\
                                    </td>\
                                    <td>'+ item.typeValue +'</td>\
                                    <td>'+ item.levelValue +'</td>\
                                    <td>'+ item.statusValue+'</td>\
                                    <td>'+ new Date(item.startDate).format("yyyy-MM-dd") +'</td>\
                                    <td>'+ item.memberGroupName +'</td>\
                                    <td>'+ item.createdPartyName +'</td>\
                                    <td>'+ new Date(item.createdTime).format("yyyy-MM-dd") +'</td>\
                                    <shiro:checkPemission name="Admin:E-CRB::Event:Processing:Handle">\
                                    <td>\
                                        <a href="/admin/event/processing/notice?clientId= '+ $(".clientId").val() +'" class="label-info"><i class="fa fa-edit"></i>&nbsp;处理</a>\
                                    </td>\
                                    </shiro:checkPemission>\
                                </tr>'
                    });
                    $('#J_template').append(str);
                    //暂无数据提示
                    var tr = $('#J_template').children();
                    if(tr.length == 0){
                        $('#J_template').append('<tr><td colspan="9">暂无数据</td></tr>');
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
    $(document).on('click', '.J_process', function(e){
        var
            tr = $(e.target).parents('tr'),
            id = $(tr).attr('data-id');

        $('.hidId').val(id);
    });

    /**
     * 设置没有检查项列表时，检查项和结果为不可选和不可编辑
     *
     * @type {*}
     */
    var ck = $(".checkItemId").children();
    if (ck > 1){
        $(".checkItemId").attr("disabled","disabled");
        $(".checkItemResult").attr("disabled","disabled");
    }


    /**
     * 确定提交按钮
     * @param  {[type]} e){                 } [description]
     * @return {[type]}      [description]
     */
    $('.J_submit').click(function(){
        var
            data = $('.J_detailForm').data('bootstrapValidator');

        if (data) {
            // 修复记忆的组件不验证
            data.validate();

            if (!data.isValid()) {
                return false;
            }
        }
        submit();
    });

    /**
     * 提交数据
     */
    function submit(){
        var
            form = $(".J_detailForm").serializeObject();

        $.ajax({
            type: "POST",
            // url: jQuery.url.ECRBManagement.sendRecord,
            url: "/admin/event/processing/ajax/record/save",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: form,     //JSON.stringify
            dataType: "json",
            success: function (rs) {
                if(rs.code == 0){
                    Alert("提示信息","保存成功！",function(){
                        location.reload();
                    });
                }else{
                    $('#tipDialog').modal();
                }
            },
            error: function (message) {
                $('#tipDialog').modal();
            }
        });

    }

    /**
     * 处理按钮
     * @param  {[type]} e){                 } [description]
     * @return {[type]}      [description]
     */
    $('.J_save').click(function(){
        var
            data = $('.J_form').data('bootstrapValidator');

        if (data) {
            // 修复记忆的组件不验证
            data.validate();

            if (!data.isValid()) {
                return false;
            }
        }
        process();
    });

    /**
     * 处理事件
     */
    function process(){
        var
            id = $('.hidId').val(),
            form = $('.J_form').serializeArray();

        jQuery.extend(form, {
            id: id
        });

        $.ajax({
            type: "GET",
            url: jQuery.url.ECRBManagement.processActivity,
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: form,     //JSON.stringify
            dataType: "json",
            success: function (rs) {
                if(rs.code == 0){
                    $('#modalDialog').modal('hide');
                    location.reload();
                    $(".participantStatus").val(1);
                    $(".checkItemResult").val(" ");
                    $('#createdTime').val(today());
                    $(".checkItemId").val("");
                }else{
                    $('#modalDialog').modal('hide');
                    $('#tipDialog').modal();
                }
            },
            error: function (message) {
                $('#tipDialog').modal();
            }
        });
    }

    /**
     * 关闭处理对话框清除校验、数据
     * @param  {[type]} ) {                       } [description]
     * @return {[type]}   [description]
     */
    $('#modalDialog').on('hidden.bs.modal', function() {
        $('.J_form').bootstrapValidator('resetForm', true);
        $('.J_date').val('2017-07-01');
        $('.J_nextTime').val('2017-08-01');
        $(".J_form").data('bootstrapValidator').destroy();
        $('.J_form').data('bootstrapValidator', null);
        formValidatorForm();
    });

    /**
     * 详情页验证
     */
    function formValidatorDetailForm(){
        $('.J_detailForm').bootstrapValidator({
            message: 'This value is not valid',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                time: {
                    validators: {
                        notEmpty: {
                            message: '提醒时间不能为空'
                        }
                    }
                },
                date: {
                    validators: {
                        notEmpty: {
                            message: '日期不能为空'
                        }
                    }
                },
                checkItem: {
                    validators: {
                        notEmpty: {
                            message: '检查项选择不能为空'
                        }
                    }
                },
                nextTime: {
                    validators: {
                        notEmpty: {
                            message: '下次计划时间不能为空'
                        }
                    }
                },
                remarks: {
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
     * 处理框验证
     */
    function formValidatorForm(){
        $('.J_form').bootstrapValidator({
            message: 'This value is not valid',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                date: {
                    validators: {
                        notEmpty: {
                            message: '日期不能为空'
                        }
                    }
                },
                checkItem: {
                    validators: {
                        notEmpty: {
                            message: '检查项选择不能为空'
                        }
                    }
                },
                nextTime: {
                    validators: {
                        notEmpty: {
                            message: '下次计划时间不能为空'
                        }
                    }
                },
                remarks: {
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

    //两个日期都默认为当天,获取文本id并且传入当前日期
    $('#createdTime').val(today());
    /**
     * 日期默认为当天,获取文本id并且传入当前日期
     * @return {[type]} [description]
     */
    function today(){
        var today = new Date(),
            y = today.getFullYear(),
            m = today.getMonth()+1,
            d = today.getDate();
        //这里判断日期是否<10,如果是在日期前面加'0'
        m = m<10 ? '0'+m : m;
        d = d<10 ? '0'+d : d;
        return y + '-' + m + '-' + d;
    }

    //重置
    $(".J_reset").click(function () {
        $(".participantStatus").val(1);
        $(".checkItemResult").val(" ");
        $('#createdTime').val(today());
        $(".checkItemId").val("");
    })

});