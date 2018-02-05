/*-----------------------------------------------------------------------------
 * @Description:     问卷管理添加
 * @Version:         1.0.0
 * @author:          yudan (862669640@qq.com)
 * @date             2017.7.24
 * ==NOTES:=============================================
 * v1.0.0(2017.7.24):
 初始生成
 * ---------------------------------------------------------------------------*/
$(function(){
    // 结束日期默认为当天,获取文本id并且传入当前日期
    $('#endTime').val(today());
    function today(){
        var today = new Date(),
            y = today.getFullYear(),
            m = today.getMonth()+1,
            d = today.getDate();
        //这里判断日期是否<10,如果是在日期前面加'0'
        m = m<10 ? '0'+m : m;
        d = d<10 ? '0'+d : d;
        return y + '-' + m + '-' + d;
    };

    //添加验证
    formValidatorAddForm();
    //点击提醒显示提醒天数
    $(':radio').click(function(){
        showDays();
    })
    //全选事件
    $('.J_selectAll').click(function(){
        var selectMap = $('.J_select');
        if( selectMap.length != $('.J_select:checked').length){
            $('.J_selectAll').prop('checked', true);
            selectMap.prop('checked', true);
        }else{
            selectMap.prop('checked', false);
        }
    });
    $(document).on('click', '.J_select', function(){
        var selectMap = $('.J_select'),
            selectAll = $('.J_selectAll');
        if( selectMap.length == $('.J_select:checked').length){
            selectAll.prop('checked', true);
        }else{
            selectAll.prop('checked', false);
        }
    });
     //暂无数据提示
    dataTip();
    function dataTip(){
        var tr = $('.J_tbody').children();
        if(tr.length == 0){
            $('.J_tbody').append('<tr class="J_dataTip"><td colspan="4">暂无数据</td></tr>');
        }else{
            $('.J_dataTip').remove();
        }
    }
    //点击添加用药对话框确定按钮验证
    $('.J_addDlg').click(function(){
        validItem();
    });

    //药名验证是否为空
    $('.J_validMedicinal').change(function(){
        validDosageName();
    })
    //点击删除按钮，弹出提示框
    $('.J_del').click(function(){
        openTipDlg();
    })
    //删除用药项
    $('.J_delDlg').click(function(){
        delItem();
    });

    //点击保存表单按钮验证表单
    $('.J_submit').click(function(){
        validForm();
    });

    //重置表单
    $('.J_reset').click(function() { 
        $('#nextTime').val('');
        $('.J_remindDays').val('');
        $("input[name='comment']").val('');
        $('.J_questReset').val('');
        $('tbody').find('tr').remove();
        dataTip();
    });

    /**==NOTES:=============================================*/
    function showDays(){
        var checked = $(":radio:checked").val();
        if(checked == 1){
            $('.J_editRadio').after('<div class="form-group J_days">\
				<label class="col-sm-4 control-label">\<span class="requires">&nbsp;*</span>提前提醒天数</label>\
				<div class="col-sm-3">\
					<input class="new180 J_remindDays"  type="text" name="remindaheadDays"  value=""/>\
				</div></div>');
        }else{
            $('.J_days').remove();
        }
    }

    /**
     * 验证添加用药对话框表单内容
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
                validName();
            }
        }
    }
    function validName(){
        var dosageName = $('#editable-select1').val();
        if(dosageName == ''){
            if($('.medicinalName').length == 0){
                $('.J_medicinal').after('<small class="medicinalName has-error help-block" data-bv-validator="notEmpty" data-bv-for="name" data-bv-result="INVALID" style="color:#a94442;">药名不能为空</small>');
            }
        }else{
            if($('.medicinalName').length > 0){
                $('.medicinalName').remove();
            }
            addItem();
        }
    }
    function validDosageName(){
        var dosageName = $('#editable-select1').val();
        if(dosageName == ''){
            if($('.medicinalName').length == 0){
                $('.J_medicinal').after('<small class="medicinalName has-error help-block" data-bv-validator="notEmpty" data-bv-for="name" data-bv-result="INVALID" style="color:#a94442;">药名不能为空</small>');
            }
        }else{
            if($('.medicinalName').length > 0){
                $('.medicinalName').remove();
            }
        }
    }
    /**
     * 新增一条用药数据项
     * [addItem description]
     */
    function addItem(){
        var dicQuestItem = $('.J_dicQuestItemId').val(),
            dosageName =$('.J_medicinal').val(),
            dosageQuantity = $('.J_dosage').val(),
            dicDosageUnitText = $(".J_unit").find("option:selected").text(),
            dicDosageUnit = $(".J_unit").find("option:selected").val(),
            dosageNum = $('.J_times').val();

        $('.J_tbody').append('<tr data-id="' + dicQuestItem + '"> \
			<td> <input class="J_select"  type="checkbox" name="subCheck" > </td> \
			<td> ' + dosageName + ' </td>\
			<td>'+ dosageQuantity +  dicDosageUnitText +'</td> \
			<td hidden="hidden">'+ dosageQuantity +'</td> \
			<td hidden="hidden">'+ dicDosageUnit +'</td> \
			<td> '+ dosageNum +' </td> </tr>');
        $('#addDialog').modal('hide');
        dataTip();
    };
    /**
     * 提交表单
     * @return {[type]} [description]
     */
    function submitForm(){
        var form = $('.J_form').serializeObject(),
            tr = $('.J_tbody').children(),
            questItemList =[],
            id, dosageName, dosageQuantity, dicDosageUnit, dosageNum, dicQuestItem,
            questItems = $('.J_questItems');

        tr.each(function(){
            var trList = {},
                tdArr = $(this).children(),
                dicQuestItem = $(this).attr('data-id'),
                dosageName = tdArr.eq(1).text(),
                dosageQuantity = tdArr.eq(3).text(),
                dicDosageUnit = tdArr.eq(4).text(),
                dosageNum = tdArr.eq(5).text();
            jQuery.extend(trList, {
                dicQuestItem: dicQuestItem,
                dosageName: dosageName,
                dosageQuantity: dosageQuantity,
                dicDosageUnit: dicDosageUnit,
                dosageNum: dosageNum
            });
            questItemList.push(trList);
        });
        questItems.each(function(){
            var questList = {},
                inpArr = $(this).children(),
                dicQuestItem = inpArr.eq(0).val(),
                questContent = inpArr.eq(1).val();
            jQuery.extend(questList, {
                dicQuestItem: dicQuestItem,
                questContent: questContent
            });
            questItemList.push(questList);
        });
        jQuery.extend(form, {
            questItemList:questItemList
        });
        $.ajax({
            type: "POST",
            url: "/admin/client/quest/ajax/save",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(form),     //JSON.stringify
            dataType: "json",
            success: function (rs) {
                if( rs.code == 0){
                    LiterTip("提示信息","问卷成功");
                    setTimeout(function(){
                        window.location.href= "/admin/client/quest/list";
                    }, 1000);
                }else{
                    Alert("提示信息",rs.errMsg);
                }
            },
            error: function (message) {
                alert(message);
            }
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
        dataTip();
        if($('.J_select').length == 0){
            $('.J_selectAll').prop('checked', false);
        }
        $('#delDialog').modal('hide');
    };

    /**
     * 验证表单
     * @return {[type]} [description]
     */
    function validForm(){
        var nextTime = $('#nextTime').val();
        days = $('.J_remindDays').val();
        if(nextTime == '') {
            $('#tipDialog').modal();
            return false;
        }else if(days == ''){
            $('#daysDialog').modal();
            return false;
        }else{
            submitForm();
        }
    }

    /**
     * 添加框验证
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
                dosageQuantity: {
                    validators: {
                        notEmpty: {
                            message: '剂量不能为空'
                        },
                        regexp: {
                            regexp: /^[0-9]*$/,
                            message: '输入有误，请输入为数字'
                        },
                        stringLength: {
                            min:1,
                            max:6,
                            message: '输入有误！'
                        }
                    }
                },
                dosageNum: {
                    validators: {
                        notEmpty: {
                            message: '每日次数不能为空'
                        },
                        regexp: {
                            regexp: /^[0-9]*$/,
                            message: '输入有误，请输入为数字'
                        },
                        stringLength: {
                            min:1,
                            max:6,
                            message: '输入有误！'
                        }
                    }
                }
            }
        });
    }
    /**
     * 模糊匹配-可输入也可下拉选择
     */
    $('#J_medicinal').editableSelect1({
        effects: 'slide'
    });
    $('#html1').editableSelect1();
    /**
     * 药名-模糊匹配-keyup事件
     */
    $('#J_medicinal').keyup(function(){
        var
            name = $('.J_medicinal').val();

        $(".es-list1").empty();

        $.ajax({
            type: "GET",
            url: "/admin/client/quest/ajax/dosage",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: {name: name},     //JSON.stringify
            dataType: "json",
            success: function (rs) {
                var
                    li = "";

                $(rs.list).each(function(key, item){
                    li +='<li class="es-visible" style="display: block;">' + item.name + '</li>';
                });
                $(".es-list1").append(li);
            },
            error: function (errMsg) {
                console.log(errMsg);
            }
        });
    });
    /**
     * 关闭添加对话框清除校验、数据
     * @param  {[type]} ) {                       } [description]
     * @return {[type]}   [description]
     */
    $('#addDialog').on('hidden.bs.modal', function() {
        $('#editable-select1').val('');
        $('.J_dosage').val('');
        $('.J_times').val('');
        $(".J_addForm").data('bootstrapValidator').destroy();
        $('.J_addForm').data('bootstrapValidator', null);
        formValidatorAddForm();
    });
});