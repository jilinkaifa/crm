/*-----------------------------------------------------------------------------
* @Description:     客户关怀-模板管理添加
* @Version:         1.0.0
* @author:          lily(529116421@qq.com)
* @date             2017.7.25
* ==NOTES:=============================================
* v1.0.0(2017.7.25):
     初始生成
* ---------------------------------------------------------------------------*/
$(function(){
    /**
     * 初始化提示信息、分页
     */
    formValidatorForm();
    var oldNames = $('.J_name').val();
    $('#Menu3,#logoMenu3').trigger('click');
    //验证提示信息样式
    $('.J_name').siblings(".form-control-feedback").addClass("right-size");
    $('.J_date').siblings(".form-control-feedback").addClass("readmine-size");

    /**
     * 点击活动类型select框
     */
    $('.J_type').change(function(){
        hide();
    });

    /**
     * 点击上传文件按钮
     */
    $(".J_file").change(function(){
        filterFile();
    });

    /**
     * 点击重置按钮事件
     */
    $('.J_reset').click(function(){
        clearValid();
    });

    /**
     * 添加页——保存按钮
     */
    $(".J_save").click(function(){
        var data = $('.J_form').data('bootstrapValidator');
        if (data) {
            // 修复记忆的组件不验证
            data.validate();
            if (!data.isValid()) {
                return false;
            }else if(remindDate()){
                sendName();
            }
        }
    });

    /**
     * 编辑页——保存按钮
     */
    $(".J_edit").click(function(){
        var 
            newName = $('.J_name').val(),
            data = $('.J_form').data('bootstrapValidator');

        if (data) {
            // 修复记忆的组件不验证
            data.validate();
            if (!data.isValid()) {
                return false;
            }else if(remindDate()){
                if(oldNames != newName){
                    sendEditName();
                }else{
                    edit();
                }
            }
        }
    });

    /**
     * 详情页——控制显示循环力度和活动人员
     */
    show();
    function show(){
        var
            id = $('.J_detail').attr("id");

        if(id == 2){
            $('.J_pollingTime').hide();
            $('.J_memberGroupId').hide();
        }else{
            $('.J_pollingTime').show();
            $('.J_memberGroupId').show();
        }
    }

    /**
     * 点击重置清除验证功能
     */
    function clearValid(){
        $('.J_form').bootstrapValidator('resetForm', true);
        $(".J_form").data('bootstrapValidator').destroy();
        $('.J_form').data('bootstrapValidator', null);
        formValidatorForm();
    }

    /**
     * 限制上传文件格式
     */
    function filterFile(){
        var 
            filepath = $("input[type='file']").val(),
            extStart = filepath.lastIndexOf("."),
            ext = filepath.substring(extStart, filepath.length).toUpperCase(),
            size = $('.J_file')[0].files[0].size,
            fileSize = Math.ceil(size / 1024 / 1024);// Size returned in MB

        if (ext != ".TXT" && ext != ".DOCX" && ext != ".DOC" && ext != ".PDF" && ext != ".XLS" && ext != ".XLSX") {
            $('#fileDialog').modal();
            $(".J_file").val("");
            return false;
        } 
        if(fileSize > 1000){
            $('#fileSizeDialog').modal();
            $(".J_file").val("");
            return false;
        }
    }

    /**
     * 点击活动类型select框，隐藏循环力度和活动人员
     */
    function hide(){
        var
            type = $('.J_type').val();

        if(type == 2){
            $("[name='pollingTime']").val(-1);
            $("[name='memberGroupId']").val(-1);
            $('.J_hide').hide();
        }else{
            $('.J_hide').show();
        }
    }

    //根据提醒时间限制活动日期
    function remindDate(){
        var
            time = $('.J_date').val(),
            startTime = new Date($('#start').val().replace("-", "/").replace("-", "/")),
            start = new Date(today().replace("-", "/").replace("-", "/"));

        if($('#start').val() !=''){
            if(convertToday(startTime-start)-1>time){
                return true;
            }else{
                Alert("提示信息", "活动时间填写不合理，请重新填写！");
                return false;
            }
        }else{
            return true;
        }
    }
    function convertToday(str){
        var today = new Date(str),
            d = today.getDate();
        return d;
    }

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

    /**
     * 活动名称失焦事件-发送活动名称
     */
    function sendName(){
        var
            name = $("input[name='name']").val();

        if(name != ''){
            $.ajax({
                type: "GET",
                url: "/admin/event/template/ajax/new/checkname",
                contentType: "application/x-www-form-urlencoded; charset=utf-8",
                data: {name: name},     //JSON.stringify
                dataType: "json",
                success: function (rs) {
                    if(rs.code != 0){
                        $('#modalDialog').modal();
                        return false;
                    }else{
                        save();
                    }
                },
                error: function (errMsg) {
                    $('#errorDialog').modal();
                }
            });
        }
    }

    /**
     * 编辑页——发送活动名称
     */
    function sendEditName(){
        var
            name = $("input[name='name']").val();

        if(name != ''){
            $.ajax({
                type: "GET",
                url: "/admin/event/template/ajax/new/checkname",
                contentType: "application/x-www-form-urlencoded; charset=utf-8",
                data: {name: name},     //JSON.stringify
                dataType: "json",
                success: function (rs) {
                    if(rs.code != 0){
                        $('#modalDialog').modal();
                        return false;
                    }else{
                        edit();
                    }
                },
                error: function (errMsg) {
                    $('#errorDialog').modal();
                }
            });
        }
    }



    /**
     * 保存事件-发送添加模板数据（添加页）
     */
     function save(){
        var
            form = $('.J_form').serializeObject(),
            content = $('.J_content').val(),
            noticeContent = $('.J_noticeContent').val();
            // attenchment = $('.J_file').val(),
            //formData = new FormData($('.J_file')[0]);

        jQuery.extend(form,{
            content: content,
            noticeContent: noticeContent
        });

        $.ajax({
            type: "POST",
            url: "/admin/event/template/ajax/new",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: form,     //JSON.stringify
            dataType: "json",
            success: function (rs) {
                if(rs.code == 0){
                    LiterTip("提示信息","保存成功");
                    setTimeout(function(){
                        window.location.href= "/admin/event/template/list";
                    }, 1000)
                }else{
                    $('#errorDialog').modal();
                }
            },
            error: function (errMsg) {
                $('#errorDialog').modal();
            }
        });
     }

    /**
     * 获取列表页的编辑项id
     */
    function getUrlParam(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
        var r = window.location.search.substr(1).match(reg);  //匹配目标参数
        if (r != null) return unescape(r[2]); return null; //返回参数值
    }

    /**
     * 保存事件-发送添加模板数据(编辑页)
     */
    function edit(){
        var
            form = $('.J_form').serializeObject(),
            id = getUrlParam('id'),
            content = $('.J_content').val(),
            noticeContent = $('.J_noticeContent').val();
        // attenchment = $('.J_file').val(),
        //formData = new FormData($('.J_file')[0]);

        jQuery.extend(form,{
            id: id,
            content: content,
            noticeContent: noticeContent,
            attachment: $(".attachment").val()
        });
        // jQuery.extend(form, formData);
        $.ajax({
            type: "POST",
            url: "/admin/event/template/ajax/update",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            //contentType: false,
            // processData : false,
            data: form,     //JSON.stringify
            dataType: "json",
            success: function (rs) {
                //console.log(rs.code);
                if(rs.code == 0){
                    LiterTip("提示信息","保存成功");
                    setTimeout(function(){
                        window.location.href= "/admin/event/template/list";
                    }, 1000)
                }else{
                    $('#errorDialog').modal();
                }
            },
            error: function (errMsg) {
                $('#errorDialog').modal();
            }
        });
    }

    /**
     * 活动名称验证
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
                name: {
                    validators: {
                        notEmpty: {
                            message: '活动名称不能为空'
                        },
                        regexp: {
                            regexp: /^[^ ]+$/,
                            message: ' '
                        },
                    }
                },
                remindTime: {
                    validators: {
                        notEmpty: {
                            message: '提醒时间不能为空'
                        },
                        regexp: {
                            regexp: /^\d{1,}$/,
                            message: ' '
                        }
                    }
                }
            }
        });
    }

    /**********************上传文件发ajax*****************************/

    $(".J_file").change(function () {
        var
            formdata = new FormData(),
            file = $('.J_file')[0].files[0];
        formdata.append("attachment",$('.J_file')[0].files[0]);
        if(file != ""){
            $('.J_files').hide();
        }
        $.ajax({
            type: "POST",
            url: "/admin/file/ajax/upload",
            contentType: false,
            processData : false,
            data: formdata,
            success: function (rs) {
                if(rs.code == 0){
                    $(".attachment").val(rs.attachment.id);
                }else{
                    // $('#errorDialog').modal();
                }
            },
            error: function (errMsg) {
                $('#errorDialog').modal();
            }
        });
    })

});