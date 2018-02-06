/*-----------------------------------------------------------------------------
* @Description:     客户关怀-活动添加
* @Version:         1.0.0
* @author:          sunwanlin(529116421@qq.com)
* @date             2017.7.26
* ==NOTES:=============================================
* v1.0.0(2017.7.26):
     初始生成
* ---------------------------------------------------------------------------*/
$(function(){
    var roleName =  $("#roleName").val();
    if(roleName == '营销人员'){
        $(".J_activitiesType option[value='1']").remove();
        $(".J_group").hide();
        $(".J_polling").hide();
        $("[name='pollingTime']").val(-1);
        $("[name='memberGroupId']").val(-1);
    }
    //切换侧边栏
    $('#Menu3,#logoMenu3').trigger('click');
    //新建活动form验证
    formValidatorForm();
    //检查项添加弹框验证
    formValidatorAddForm();
    //积分项添加弹框验证
    formValidatorAddPointsForm();
    //提醒时间提示信息样式
    $('.J_date').siblings(".form-control-feedback").addClass("readmine-size");
    /**
     * 声明模板ID变量和是否被使用变量
     */
    /*var templateId;
    var isTemplateUsed;*/
    /**
     * 隐藏提示
     * @return {[type]} [description]
     */
    function showTip(){
        setTimeout(function(){
            $('.J_tip').hide();
        }, 5000);
    }
    /**
     * 关怀型活动-->显示循环粒度和活动人员
     * 营销型活动-->隐藏循环粒度和活动人员
     * @param  {[type]} ){                     var activitiesType [description]
     * @return {[type]}     [description]
     */
    $(".J_activitiesType").change(function(){
        var activitiesType = $(".J_activitiesType").val();
        if(activitiesType==1){
            $(".J_group").show();
            $(".J_polling").show();

            $(".J_form").data('bootstrapValidator').destroy();
            $('.J_form').data('bootstrapValidator', null);
            formValidatorForm();
        }else if(activitiesType==2){
            $(".J_group").hide();
            $(".J_polling").hide();
            $("[name='pollingTime']").val(-1);
            $("[name='memberGroupId']").val(-1);
        }
    });
    //两个日期都默认为当天,获取文本id并且传入当前日期
    if(!$('#startDate').val()){
        $('#startDate').val(today());
    }
    if(!$('#end').val()){
        $('#end').val(today());
    }
    // $('#startDate').val(today());
    // $('#end').val(today());

    //根据提醒时间限制活动日期
    // function remindDate(){
    //     var
    //         time = $('.J_date').val(),
    //         startTime = new Date($('#startDate').val().replace("-", "/").replace("-", "/")),
    //         start = new Date(today().replace("-", "/").replace("-", "/"));
    //
    //     if(convertToday(startTime-start)-1>time){
    //         return true;
    //     }else{
    //         Alert("提示信息", "活动时间填写不合理，请重新填写！");
    //         return false;
    //     }
    // }

    function addDate(t,days){
        var v = t.valueOf();
        var ti = v + days * 24 * 60 * 60 * 1000;
        var da = new Date(ti);
        return da;
    }

    function currentDate(){
        var today = new Date(),
            y = today.getFullYear(),
            m = today.getMonth()+1,
            d = today.getDate();
        //这里判断日期是否<10,如果是在日期前面加'0'
        m = m<10 ? '0'+m : m;
        d = d<10 ? '0'+d : d;
        return y + '-' + m + '-' + d;
    }

    function remindDate(){
        //提醒时间 日
        var remindTime = $('.J_date').val();
        //alert("remindTime="+remindTime);

        //活动开始时间
        var startDate = new Date($('#startDate').val().replace("-", "/").replace("-", "/"));
        //alert("startDate="+startDate);
        //活动结束时间
        var endDate = new Date($('#end').val().replace("-", "/").replace("-", "/"));
        //alert("endDate="+endDate);
        //今天日期
        var todayDate = new Date(currentDate().replace("-", "/").replace("-", "/"));
        //alert("todayDate="+todayDate);

        // alert("addDate="+addDate(todayDate,remindTime));

        //校验【开始日期】：选择日期应大于今天+【提醒时间】的值。
        if(startDate  <= addDate(todayDate,remindTime)){
            alert("填写不正确，请重新填写【提醒时间】或【活动开始时间】。\n" +
                "【活动开始时间】应大于今天+【提醒时间】的值。例如【提醒时间】值为“2”，今天为11月1日，则【活动开始时间】选择的日期为11月3日之后的日期，不包含11月3日。");
            return false;
        }
        //校验【结束日期】结束时间大于开始时间
        if(endDate <= startDate){
            alert("活动结束时间必须大于活动开始时间!");
            return false;
        }
        return true;
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
     * 活动名称input失焦
     */
    // $(".J_name").blur(function(){
    //     sendName();
    // });
    /**
     * 保存按钮
     */
    $(".J_save").click(function(){
        var data = $('.J_form').data('bootstrapValidator');
        if (data) {
        // 修复记忆的组件不验证
            data.validate();
            if (!data.isValid()) {
                return false;
            }else if(remindDate()){
                sendTemplateName();
                return true;
            }
        }

    });
    $(".J_submit").click(function(){
        var data = $('.J_form').data('bootstrapValidator');
        if (data) {
            data.validate();
            if (!data.isValid()) {
                return false;
            }else if(remindDate()){
                submitTemplateName();
            }
        }
        //不管是否验证成功，都先发送模板名称
    });
    /**
     * 保存为模板事件-发送添加模板数据
     * 点击保存为模板按钮
     */
     function save(){
        var
            form = $('.J_form').serializeObject(),
            content = $('.J_content').val(),
            noticeContent = $('.J_noticeContent').val(),
            memberGroupId = $('.J_group input').val(),

            attachment = $('.attachment').val();
        var templateId = $(".J_templateId").val();
        var isTemplateUsed = $(".J_isTemplateUsed").val();
        alert(memberGroupId);
        jQuery.extend(form,{
            content: content,
            noticeContent: noticeContent,
            attachment: attachment,
            templateId:templateId,
            isTemplateUsed:isTemplateUsed,
            memberGroupId:memberGroupId
        });

        var
            tr = $('.J_tbody').children(),
            trPoints = $('.J_tbodyPoints').children(),

            checkItemList =[],
            pointsItemList =[],
            id, checkItemName, checkItemContent,
            idPoints, checkPointsItemName, checkPointsItemContent,
            data;

        tr.each(function(){
            var tdArr = $(this).children(),
                id = $(this).attr('data-id'),
                trList = {},
                checkItemName = tdArr.eq(1).text(),
                checkItemContent = tdArr.eq(2).text();

            var checkItem = {
                name: checkItemName,
                content: checkItemContent
            };
            checkItemList.push(checkItem);
        });
        trPoints.each(function(){
            var tdArr = $(this).children(),
                idPoints = $(this).attr('data-id'),
                trPointsList = {},
                checkPointsItemName = tdArr.eq(1).text(),
                checkPointsItemContent = tdArr.eq(2).text();

            var pointsItem = {
                name: checkPointsItemName,
                pointsValue: checkPointsItemContent
            };
            pointsItemList.push(pointsItem);
        });

        var param = {
            event:form,
            checkItemList: checkItemList,
            pointsItemList: pointsItemList
        };

        $.ajax({
                type: "POST",
                url: "/admin/event/prepare/ajax/new/template",
                // contentType: "application/x-www-form-urlencoded; charset=utf-8",
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify(param),     //JSON.stringify
                dataType: "json",
                success: function (rs) {
                    if(rs.code == 0){
                        if((rs.templateId!=null)&&(rs.isTemplateUsed!=null)){
                            $(".J_templateId").val(rs.templateId);
                            $(".J_isTemplateUsed").val(rs.isTemplateUsed);
                        }
                        //Alert("提示信息",rs.tipMsg);
                        // LiterTip("提示信息","保存为模板成功！");
                        LiterTip("提示信息",rs.tipMsg);
                        setTimeout(function(){
                            window.location.href= "/admin/event/prepare/list";
                        }, 1000);
                    }else{
                        $('#modalDialog').modal();//活动名称重复对话框
                    }

                },
                error: function (errMsg) {
                    $('#errorDialog').modal();
                }
            });
     }
    //主页面检查项显示
    $('#forjianchaxianshi').on("click",function() {
        $("#aa").show();
        $('#aa div').children().remove();
       

        var
                tr = $('.J_tbody').children(),
                trPoints = $('.J_tbodyPoints').children(),

                checkItemList = [],
                pointsItemList = [],
                id, checkItemName, checkItemContent,
                idPoints, checkPointsItemName, checkPointsItemContent,
                data;

            tr.each(function () {
                var tdArr = $(this).children(),
                    id = $(this).attr('data-id'),
                    trList = {},
                    checkItemName = tdArr.eq(1).text(),
                    checkItemContent = tdArr.eq(2).text();
                    $('#aa div').append(
                        '<p>'+checkItemName+ ':'+checkItemContent+'</p>'
                    );

            });



        }
);

    //主页积分显示
    $('#forjifenxianshi').on("click",function() {
        $("#bb").show();
            $('#bb div').children().remove();

            var

                trPoints = $('.J_tbodyPoints').children(),
                idPoints, checkPointsItemName, checkPointsItemContent,
                data;
        　　　　　trPoints.each(function () {
                 var tdArr = $(this).children(),
                 idPoints = $(this).attr('data-id'),
                      checkPointsItemName = tdArr.eq(1).text(),
                      checkPointsItemContent = tdArr.eq(2).text();
                    $('#bb div').append(
                        '<p>'+checkPointsItemName+':' +checkPointsItemContent+'</p>'
                    );

                });


        }
    );
    /**
     * 提交审核
     * 点击了提交审核按钮
     * @return {[type]} [description]
     */
    function submitForm(templateId,isTemplateUsed){
        var 
            form = $('.J_form').serializeObject(),
            content = $('.J_content').val(),
            noticeContent = $('.J_noticeContent').val(),
            attachment = $('.attachment').val();
        var templateId = $(".J_templateId").val();
        var isTemplateUsed = $(".J_isTemplateUsed").val();

        jQuery.extend(form,{
            content: content,
            noticeContent: noticeContent,
            attachment: attachment,
            templateId:templateId,
            isTemplateUsed:isTemplateUsed
        });

        var 
            tr = $('.J_tbody').children(),
            trPoints = $('.J_tbodyPoints').children(),
            
            checkItemList =[],
            pointsItemList =[],
            id, checkItemName, checkItemContent,
            idPoints, checkPointsItemName, checkPointsItemContent,
            data;

        tr.each(function(){
            var tdArr = $(this).children(),
                id = $(this).attr('data-id'),
                trList = {}, 
                checkItemName = tdArr.eq(1).text(),
                checkItemContent = tdArr.eq(2).text();

            var checkItem = {
                name: checkItemName,
                content: checkItemContent
            };
            checkItemList.push(checkItem);
            alert(checkItemList);
        });
        trPoints.each(function(){
            var tdArr = $(this).children(),
                idPoints = $(this).attr('data-id'),
                trPointsList = {},
                checkPointsItemName = tdArr.eq(1).text(),
                checkPointsItemContent = tdArr.eq(2).text();

            var pointsItem = {
                name: checkPointsItemName,
                pointsValue: checkPointsItemContent
            };
            pointsItemList.push(pointsItem);
        });

          var param = {
              event:form,
              checkItemList: checkItemList,
              pointsItemList: pointsItemList
          };
        $.ajax({
                type: "POST",
                url: "/admin/event/prepare/ajax/new/event",
                // contentType: "application/x-www-form-urlencoded; charset=utf-8",
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify(param),
                dataType: "json",
                success: function (rs) {
                    if(rs.code == 0){
                        //Alert("提示信息","提交审核成功!");
                        LiterTip("提示信息","提交审核成功");
                        setTimeout(function(){
                            window.location.href= "/admin/event/prepare/list";
                        }, 1000);
                    }
                    else{
                        $('#modalDialog').modal();//活动名称重复对话框
                    }
                },
                error: function (errMsg) {
                    $('#errorDialog').modal();
                }
            });
    }
    /**
     * 发送模板活动名称、验唯一性
     * tips：点击保存为模板时，发送活动名称，后台验证是否唯一，若唯一则保存为模板
     * @return {[type]} [description]
     */
    function sendTemplateName(){
        var 
            name=$(".J_name").val();   
        $.ajax({
                type: "GET",
                url: "/admin/event/prepare/ajax/new/checkname/template",
                contentType: "application/x-www-form-urlencoded; charset=utf-8",
                data: {
                    name: name
                },     //JSON.stringify
                dataType: "json",
                success: function (rs) {
                    if(rs.code == 0){
                        save();
                    }else{
                        $('#modalDialog').modal();//活动名称重复对话框！
                    }
                },
                error: function (errMsg) {
                    $('#errorDialog').modal();
                }
            });
    }
    /**
     * 发送模板活动名称、验唯一性
     * tips：点击提交审核时，发送活动名称，后台验证是否唯一，若唯一则保存为模板
     * @return {[type]} [description]
     */
    function submitTemplateName(){
        var
            name=$(".J_name").val();
        $.ajax({
            type: "GET",
            // url: jQuery.url.ECRBManagement.sendActiveName,
            url: "/admin/event/prepare/ajax/new/checkname",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: {
                name: name
            },     //JSON.stringify
            dataType: "json",
            success: function (rs) {
                if(rs.code == 0){
                    validForm();
                }else{
                    $('#modalDialog').modal();//活动名称重复对话框！
                }
            },
            error: function (errMsg) {
                $('#errorDialog').modal();
            }
        });
    }
    /**
     * 点击提交审核按钮时验证表单
     * @return {[type]} [description]
     */
    function validForm(templateId,isTemplateUsed){
        var data = $('.J_form').data('bootstrapValidator');
        if (data) {
            // 修复记忆的组件不验证
            data.validate();
            if (!data.isValid()) {
                return false;
            }
        }
        submitForm(templateId,isTemplateUsed);
    }

    /**
     * 活动名称失焦事件-发送活动名称
     */
    // function sendName(){
    //     var
    //         name = $("input[name='name']").val();
    //
    //     if(name != ''){
    //         $.ajax({
    //             type: "GET",
    //             url: "/admin/event/prepare/ajax/new/checkname",
    //             contentType: "application/x-www-form-urlencoded; charset=utf-8",
    //             data: {
    //                 name: name
    //             },     //JSON.stringify
    //             dataType: "json",
    //             success: function (rs) {
    //                 if(rs.code != 0){
    //                     $('#modalDialog').modal();
    //                 }else{//验证成功
    //                     valueIt();
    //                 }
    //             },
    //             error: function (errMsg) {
    //                 $('#errorDialog').modal();
    //             }
    //         });
    //     }
    // }
    // function valueIt(){
    //     var flag=true;
    // }
    //检查项全选事件
    $(".J_selectAll").click(function() {
        $('input[name="subCheck"]').prop("checked", this.checked);
    });
    $('.J_tbody').on('click', "input[name='subCheck']", function() {
        var subCheck = $("input[name='subCheck']");
        $(".J_selectAll").prop("checked", (subCheck.length == $("input[name='subCheck']:checked").length) ? true: false);
    });
    //积分项全选事件
    $(".J_selectAllPoints").click(function() {
        $('input[name="subCheck"]').prop("checked", this.checked);
    });
    $('.J_tbodyPoints').on('click', "input[name='subCheck']", function() {
        var subCheckPoints = $("input[name='subCheck']");
        $(".J_selectAllPoints").prop("checked", subCheckPoints.length == $("input[name='subCheck']:checked").length ? true: false);
    });
/*************************************以下是检查项的函数*******************************************************/    
    //点击check添加对话框确定按钮验证
    $('.J_addDlg').click(function(){
        validItem();
    });
    //点击删除按钮，弹出提示框
    $('.J_del').click(function(){
        openTipDlg();
    })
    //删除用药项
    $('.J_delDlg').click(function(){
        delItem();
    });
    //重置表单
    // $('.J_cancel').click(function() {
    //     resetForm();
    // });
/*******************************************以下是积分项的函数******************************************************/
    //点击check添加对话框确定按钮验证
    $('.J_addPointsDlg').click(function(){
        validPointsItem();
    });
    //点击删除按钮，弹出提示框
    $('.J_delPoints').click(function(){
        openTipDlg();
    })
    //删除用药项
    $('.J_delDlg').click(function(){
        delItem();
    });
/*******************************************以下是自定义函数******************************************************/
    /**
     * 新建活动验证
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
                            message: ' '
                        },
                        regexp: {
                            regexp: /^[^ ]+$/,
                            message: '活动名称不能为空'
                        }
                    }
                },
                level: {
                    validators: {
                        notEmpty: {
                            message: ' '
                        },
                        regexp: {
                            regexp: /^[0-9]\d*$/,
                            message: '活动级别不能为空'
                        }
                    }
                },
                startDate: {
                    validators: {
                        notEmpty: {
                            message: '活动时间不能为空'
                        }
                    }
                },
                pollingTime: {
                    validators: {
                        notEmpty: {
                            message: ' '
                        },
                        regexp: {
                            regexp: /^[0-9]\d*$/,
                            message: '循环粒度不能为空'
                        }
                    }
                },
                type: {
                    validators: {
                        notEmpty: {
                            message: ' '
                        },
                        regexp: {
                            regexp: /^[0-9]\d*$/,
                            message: '活动类型不能为空'
                        }
                    }
                },
                endDate: {
                    validators: {
                        notEmpty: {
                            message: '活动时间不能为空'
                        }
                    }
                },

                type: {
                    validators: {
                        notEmpty: {
                            message: ' '
                        },
                        regexp: {
                            regexp: /^[0-9]\d*$/,
                            message: '活动类型不能为空'
                        }
                    }
                },
                remindTime:{
                    validators: {
                        notEmpty: {
                            message: ' '
                        },
                        regexp: {
                            regexp: /^[1-9]\d*$/,
                            message: '提醒时间不能为空'
                        }
                    }
                }
            }
        });
    }
    /**
    * 检查项添加框验证
    * [formValidatorAddForm description]
    * @return {[type]} [description]
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
                checkItemName: {
                    validators: {
                        notEmpty: {
                            message: '名称不能为空'
                        }
                    }
                },
                checkItemContent: {
                    validators: {
                        notEmpty: {
                            message: ' '
                        },
                        regexp: {
                            regexp: /^[1-9]\d*$/,
                            message: '内容不能为空'
                        }
                    }
                }
            }
        });
    }
    /**
    * 积分项添加框验证
    * [formValidatorAddForm description]
    * @return {[type]} [description]
    */
    function formValidatorAddPointsForm(){
        $('.J_addPointsForm').bootstrapValidator({
            message: 'This value is not valid',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {

                checkItemContent: {
                    validators: {
                        notEmpty: {
                            message: '积分值不能为空'
                        },
                        regexp: {
                            regexp: /^[1-9]\d*$/,
                            message: '积分值必须为数字'
                        }
                    }
                },
                checkItemName: {
                    validators: {
                        notEmpty: {
                            message: '名称不能为空'
                        }
                    }
                }
            }
        });
    }
    /**
     * 验证检查项添加对话框表单内容
     * @return {[type]} [description]
     */
    function validItem(){
         var form = $('.J_addForm'),
             data = form.data('bootstrapValidator');
        if (data) {
        // 修复记忆的组件不验证
            data.validate();
            if (!data.isValid()) {
                return false;
            }else{
                addItem();
                return true;
            }
        }
    }

    /**
     * 验证积分项添加对话框表单内容
     * @return {[type]} [description]
     */
    function validPointsItem(){
         var form = $('.J_addPointsForm'),
             data = form.data('bootstrapValidator');
        if (data) {
        // 修复记忆的组件不验证
            data.validate();
            if (!data.isValid()) {
                return false;
            }else{
                addPointsItem();
                return true;
            }
        }
    }
    /**
     * 自动给关联检查项的名称赋值
     * @param  {[type]} )var itemTr [description]
     * @return {[type]}     [description]
     */
    $(document).on('click','.J_add',function(){
        if($(".J_tbody tr").length==0){
            $(".J_checkItemName").val('检查项1');
        }else{
            var itemTr = $(".J_tbody tr:last td:nth-child(2)");
            var trText = itemTr.text();//获取最后一条内容的text,string类型
            var trTextString = trText.substr(-1,1);
            var nameNum = parseInt(trTextString);//将最后一条名称的编号赋值给nameNum,string类型
            $(".J_checkItemName").val('检查项'+(nameNum+1));
        }

    });

    /**
     * 新增一条检查项名称-内容数据项
     * [addItem description]
     */
    function addItem(){
        var id = $('.J_tbody').children("tr:last-child").attr('data-id'),
            curId = id + 1,
            checkItemName =$('.J_checkItemName').val(),
            checkItemContent = $('.J_checkItemContent').find("option:selected").text();




        $('.J_tbody').append(
            '<tr data-id="' + curId + '"> \
                <td> <input class="J_subCheck"  type="checkbox" name="subCheck" > </td> \
                <td>'+checkItemName+'</td>\
                <td> <span>'+ checkItemContent + '</span> </td> \
            </tr>');

              $('#addDialog').modal('hide');
        /**
         * 清楚验证缓存
         */
        $('#addDialog').on('hidden.bs.modal', function() {
            $('.J_addForm').bootstrapValidator('resetForm', true);
            $('.J_checkItemName').val('');
            $('.J_checkItemContent').val('');
            $(".J_addForm").data('bootstrapValidator').destroy();
            $('.J_addForm').data('bootstrapValidator', null);
            formValidatorAddForm();
        });
    }

    /**
     * 新增一条积分项名称-内容数据项
     * [addItem description]
     */
    function addPointsItem(){
        var id = $('.J_tbodyPoints').children("tr:last-child").attr('data-id'),
            curId = id + 1,
            pointsItemName =$('.J_pointsItemName').find("option:selected").text();
            pointsItemContent = $('.J_pointsItemContent').val();

        $('.J_tbodyPoints').append(
            '<tr data-id="' + curId + '"> \
                <td> <input class="J_subCheckPoints"  type="checkbox" name="subCheck" > </td> \
                <td>'+pointsItemName + '</td>\
                <td>'+ pointsItemContent + '</td> \
            </tr>');
        $('#addPointsDialog').modal('hide');
        /**
         * 清楚验证缓存
         */
        $('#addPointsDialog').on('hidden.bs.modal', function() {
            $('.J_addPointsForm').bootstrapValidator('resetForm', true);

            $('.J_pointsItemName').val('');
            $('.J_pointsItemContent').val('');
            $(".J_addPointsForm").data('bootstrapValidator').destroy();
            $('.J_addPointsForm').data('bootstrapValidator', null);
            formValidatorAddPointsForm();
        });
    }
    /**
     * 根据是否有删除项弹出不同的提示框
     * @return {[type]} [description]
     */
    function openTipDlg(){
        var checked = $('input[type=checkbox]:checked').length; 
        if(checked == 0){
            $('#delTipDialog').modal();
        }else{
            $('#delDialog').modal();
        }
    }
    /**
     * 删除所选项
     * [delItem description]
     * @return {[type]} [description]
     */
    function delItem(){
        var checked = $('tbody').find('input[type=checkbox]:checked'),
            tr = checked.parents("tr");
        tr.remove();
        $('#delDialog').modal('hide');
        $(".J_selectAll").prop("checked", subCheck.length == $("input[name='subCheck']:checked").length ? true: false);
    }
    /**
     * 点击新建活动reset按钮时清空校验、数据
     * @param  {[type]} ) 
     * [description]
     * @return {[type]}   [description]
     */
    // function resetForm(){
    //     $('.J_form').bootstrapValidator('resetForm', true);
    //     $(".J_form").data('bootstrapValidator').destroy();
    //     $('.J_form').data('bootstrapValidator', null);
    //     $('.tip').remove();
    //     formValidatorForm();
    // }


    /**********************上传文件发ajax*****************************/

    /**
     * 点击上传文件按钮
     */
    $(".J_file").change(function(){
        filterFile();
    });

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
            Alert("提示信息","文件格式不正确！");
            $(".J_file").val("");
            return false;
        }
        if(fileSize > 1000){
            Alert("提示信息","文件大小不能超过1000M！");
            $(".J_file").val("");
            return false;
        }
    }

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