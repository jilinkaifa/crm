/*-----------------------------------------------------------------------------
 * @Description:     画像管理-批量修改画像管理
 * @Version:         1.0.0
 * @author:          gts(616603151@qq.com)
 * @date             2017.7.21
 * ==NOTES:=============================================
 *
 * ---------------------------------------------------------------------------*/
$(document).ready(function() {
    // 侧边栏
    $('#Menu2,#logoMenu2').trigger('click');
    /**
     * 点击单选选择事件，将input的name值取出放到隐藏input中
     */
    $('.J_selectRadio').click(function(e){
        selectRadio(e);
    });

    /**
     * 点击多选选择事件，将input的name值取出放到隐藏input中
     */
    $('.J_selectCheckbox').click(function(e){
        selectCheckbox(e);
    })

    /**
     * 点击单选按钮
     */
    $('.J_radioDlg').click(function(){
        radioSelect();
    });;

    /**
     * 点击多选按钮
     */
    $('.J_checkBoxDlg').click(function(){
        checkBoxSelect();
    });
    /**
     * 全选selectAll按钮
     */
    $('.J_selectAll').click(function(){
        selectAll();
    });

    /**
     * select按钮
     */
    $(document).on('click', '.J_select', function(){
        select();
    });
    /**
     *单选确定按钮
     */
    $(".J_secondDialog").click(function(){
        secondDialog();
    });
    /**
     *多选确定按钮
     */
    $(".J_checkBoxSecondDialog").click(function(){
        checkBoxSecondDialog();
    })
    /**
     * 二次单选确认事件
     */
    function  secondDialog() {
        var
            id = $('.J_id').val(),
            tagId = $('.J_tagId').val(),
            inputName = $('.J_inputName').val(),
            data = $(".J_radioSelect").find("option:selected").text(),
            labelItemId = $(".J_radioSelect").val(),
            personId = getUrlParam('id'),
            form = {
                id: id,
                typeId: tagId,
                itemId: labelItemId,
                clientId: personId.toString(),
                clickNumber: 2
            };
        if(labelItemId != -1){
            $.ajax({
                type: "POST",
                url: "/admin/profile/portrayal/ajax/update/select",
                contentType: "application/x-www-form-urlencoded; charset=utf-8",
                data: form,     //JSON.stringify
                dataType: "json",
                success: function (rs) {
                    $('#firstDialog').modal('hide');
                    if( rs.code == 0){
                        //Alert("提示信息", "修改成功");
                        $('input[name="'+ inputName +'"]').val(data);
                        $('.J_tip').removeClass("hidden");
                        showTip();
                    }else{
                        $('#errorDialog').modal();
                    }
                },
                error: function (message) {
                    $('#radioDialog').modal('hide');
                    $('#errorDialog').modal();
                }
            });
        }else{
            $.ajax({
                type: "POST",
                url: "/admin/profile/portrayal/ajax/update/select",
                contentType: "application/x-www-form-urlencoded; charset=utf-8",
                data: form,     //JSON.stringify
                dataType: "json",
                success: function (rs) {
                    $('#firstDialog').modal('hide');
                    if( rs.code != 0){
                        $('#errorDialog').modal();
                    }
                },
                error: function (message) {
                    $('#radioDialog').modal('hide');
                    $('#errorDialog').modal();
                }
            });
        }
    }
    /**
     * 二次多选确认事件
     */
    function  checkBoxSecondDialog() {
        var
            id = $('.J_id').val(),
            tagId = $('.J_tagId').val(),
            textareaName = $('.J_textareaName').val(),
            data = '',
            labelItemName,
            labelItemList = [],
            personId = getUrlParam('id'),
            form = {
                id: id,
                labelTypeId: tagId,
                clientId: personId.toString(),
                clickNumber: 2
            };

        $('input[name="'+ textareaName +'"]:checked').each(function(){
            labelItemName = $(this).parent().text();
            data += ''+ labelItemName +'' + '，';
        });
        $('input[name="'+ textareaName +'"]:checked').each(function(){
            labelItemList.push($(this).val());
        });
        jQuery.extend(form, {itemId: labelItemList.toString()});
        $.ajax({
            type: "POST",
            url: "/admin/profile/portrayal/ajax/update/multiple",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: form,
            dataType: "json",
            success: function (rs) {
                $('#checkBoxFirstDialog').modal('hide');
                if( rs.code == 0){
                    $('textarea[name="'+ textareaName +'"]').val('');
                    $('textarea[name="'+ textareaName +'"]').val(data);
                }else{
                    $('#errorDialog').modal();
                }
            },
            error: function (message) {
                $('#checkBoxDialog').modal('hide');
                $('#errorDialog').modal();
            }
        });
    }
    /**
     * 全选事件
     */
    function selectAll(){
        var
            selectMap = $('.J_select');

        if( selectMap.length != $('.J_select:checked').length){
            $('.J_selectAll').prop('checked', true);
            selectMap.prop('checked', true);
        }else{
            selectMap.prop('checked', false);
        }
    }

    /**
     * select全部选中事件
     */
    function select(){
        var
            selectMap = $('.J_select'),
            selectAll = $('.J_selectAll');
        if( selectMap.length == $('.J_select:checked').length){
            selectAll.prop('checked', true);
        }else{
            selectAll.prop('checked', false);
        }
    }

    /**
     * 关闭多选对话框清除全选按钮
     * @param  {[type]} ) {                       } [description]
     * @return {[type]}   [description]
     */
    $('#checkBoxDialog').on('hidden.bs.modal', function() {
        $(".J_selectAll").attr('checked', false);
    });
    /**
     * 画像单选情况-发送画像标签ID，将选择内容刷到选择弹出框中(点击选择)
     */
    function selectRadio(e){
        var
            tagId = $(e.target).parent().prev('div').children('input').attr('value'),
            tagName = $(e.target).parent().prev('div').children('label').html(),
            inputName = $(e.target).parent().prev('div').children('div').children('input').attr('name');

        $('.J_inputName').val(inputName);
        $('.J_tagId').val(tagId);
        $('.J_tagName').html(tagName);
        $.ajax({
            type: "GET",
            url: "/admin/profile/portrayal/ajax/select",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: {typeId: tagId},     //JSON.stringify
            dataType: "json",
            success: function (rs) {
                $(".J_radioSelect").empty();
                var initOption = '<option value="-1" selected="selected">---清除---</option>';
                $(".J_radioSelect").append(initOption);
                $(rs.list).each(function(key, item){
                    var option = "<option value='" + item.labelItemId + "'>" + item.labelItemName + "</option>";
                    $(".J_radioSelect").append(option);
                });
            },
            error: function (message) {
                $('#errorDialog').modal();
            }
        });
    }
    /**
     * 选择单选事件(第一次确认)
     */
    function radioSelect(){
        var
            id = $('.J_id').val(),
            tagId = $('.J_tagId').val(),
            inputName = $('.J_inputName').val(),
            data = $(".J_radioSelect").find("option:selected").text(),
            labelItemId = $(".J_radioSelect").val(),
            personId = getUrlParam('id'),
            form = {
                id: id,
                typeId: tagId,
                itemId: labelItemId,
                clientId: personId.toString(),
                clickNumber: 1
            };
        console.log(form);

        // if(labelItemId != -1){
            $.ajax({
                type: "POST",
                url: "/admin/profile/portrayal/ajax/update/select",
                contentType: "application/x-www-form-urlencoded; charset=utf-8",
                data: form,     //JSON.stringify
                dataType: "json",
                success: function (rs) {
                    $('#radioDialog').modal('hide');
                    if( rs.code == 0){
                        $('.J_p').text(rs.tipMsg);
                        $('#firstDialog').modal();
                    }else{
                        $('#errorDialog').modal();
                    }
                },
                error: function (message) {
                    $('#radioDialog').modal('hide');
                    $('#errorDialog').modal();
                }
            });
        // }
    }
    /**
     * 画像多选情况-发送画像标签ID，将选择内容刷到选择弹出框中
     */
    function selectCheckbox(e){
        var
            tagId = $(e.target).parent().prev('div').children('input').attr('value'),
            tagName = $(e.target).parent().prev('div').children('label').html(),
            textareaName = $(e.target).parent().prev('div').children('div').children('textarea').attr('name');

        $('.J_textareaName').val(textareaName);
        $('.J_tagId').val(tagId);
        $('.J_tagName').html(tagName);
        $.ajax({
            type: "GET",
            url: "/admin/profile/portrayal/ajax/select",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: {typeId: tagId},     //JSON.stringify
            dataType: "json",
            success: function (rs) {
                $(".J_checkboxSelect").empty();
                $(rs.list).each(function(key, item){
                    var str = '<div class="col-sm-6"><label title="'+ item.labelItemName +'" class="checkboxWidth"><input type="checkbox" class="J_select" name="'+ textareaName +'" value="'+ item.labelItemId +'">' + item.labelItemName + '</label></div>';
                    $(".J_checkboxSelect").append(str);
                });
            },
            error: function (message) {
                $('#errorDialog').modal();
            }
        });
    }

    function getUrlParam(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
        var r = window.location.search.substr(1).match(reg);  //匹配目标参数
        if (r != null) return unescape(r[2]); return null; //返回参数值
    }


    /**
     * 选择多选事件（第一次确认）
     */
    function checkBoxSelect(){
        var
            id = $('.J_id').val(),
            tagId = $('.J_tagId').val(),
            textareaName = $('.J_textareaName').val(),
            data = '',
            labelItemName,
            labelItemList = [],
            personId = getUrlParam('id'),
            form = {
                id: id,
                labelTypeId: tagId,
                clientId: personId.toString(),
                clickNumber: 1
            };

        $('input[name="'+ textareaName +'"]:checked').each(function(){
            labelItemName = $(this).parent().text();
            data += ''+ labelItemName +'' + '，';
        });
        $('input[name="'+ textareaName +'"]:checked').each(function(){
            labelItemList.push($(this).val());
        });
        jQuery.extend(form, {itemId: labelItemList.toString()});
        $.ajax({
            type: "POST",
            url: "/admin/profile/portrayal/ajax/update/multiple",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: form,
            dataType: "json",
            success: function (rs) {
                $('#checkBoxDialog').modal('hide');
                if( rs.code == 0){
                    // $('textarea[name="'+ textareaName +'"]').val('');
                    // $('textarea[name="'+ textareaName +'"]').val(data);
                    $('.J_dP').text(rs.tipMsg);
                    $('#checkBoxFirstDialog').modal();
                }else{
                    $('#errorDialog').modal();
                }
            },
            error: function (message) {
                $('#checkBoxDialog').modal('hide');
                $('#errorDialog').modal();
            }
        });
    }

    /**
     * 往textarea中赋值
     */
    $(document).ready(function(){
        var
            val = $(".J_hidId").val();
            $('.J_textArea').val(val);

    })
});

