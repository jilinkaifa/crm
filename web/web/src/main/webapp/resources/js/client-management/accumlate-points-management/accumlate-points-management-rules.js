/*-----------------------------------------------------------------------------
* @Description:     积分管理-积分详情
* @Version:         1.0.0
* @author:          gts(616603151@qq.com)
* @date             2017.7.20
* ==NOTES:=============================================
* 
* ---------------------------------------------------------------------------*/
$(document).ready(function() {
     /**
     * 初始化提示信息、验证表单
     */
    // showTip();
    formValidatorAddForm();
   	/**
     * 隐藏提示
     * @return {[type]} [description]
     */
    function showTip(){
        setTimeout(function(){
            $('.J_tip').hide();
        }, 2000);
    }
    function formValidatorAddForm(){
        $('.J_tableForm').bootstrapValidator({
            message: 'This value is not valid',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                exchangePoints: {
                    validators: {
                        notEmpty: {
                            message: '积分不能为空 '
                        },
                        regexp: {
                            regexp: /^\d+(\.\d+)?$/,
                            message: '兑换积分只能为正数'
                        }
                    }
                }
            }
        });
    }
    /**
     * 点击save按钮时提交数据
     * @param  {[type]} ){                     var data [description]
     * @return {[type]}     [description]
     */
    $('.J_submit').click(function(){
        var
            form = $('.J_tableForm'),
            data = form.data('bootstrapValidator');
        if (data) {
            // 修复记忆的组件不验证
            data.validate();

            if (!data.isValid()) {//如果验证不通过
                return false;
            }
            $('#pointsDialog').modal();
        }
    });

    /**
     * 确认事件
     */
    $(document).on('click', '.J_points',function(){

        var points = $("input[name=exchangePoints]").val(),
            form = {
                points: points
            };

        $.ajax({
            type: "POST",
            url: "/admin/party/pointsConvert/ajax/save",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: form,
            //JSON.stringify
            dataType: "json",
            success: function(rs) {
                $('#pointsDialog').modal('hide');
                if (rs.code == 0) {
                    Alert("提示信息", "编辑成功！");
                } else {
                    Alert("提示信息", rs.errMsg);
                }
            },
            error: function(message) {
                $('#tipDialog').modal();
            }
        });
    });

    /**
     * 点击reset按钮时清空校验、数据
     * @param  {[type]} )
     * [description]
     * @return {[type]}   [description]
     */
    $('.J_reset').on('click', function() {
        $('.J_tableForm').bootstrapValidator('resetForm', true);
        $(".J_tableForm").data('bootstrapValidator').destroy();
        $('.J_tableForm').data('bootstrapValidator', null);
        formValidatorAddForm();
    });


});    