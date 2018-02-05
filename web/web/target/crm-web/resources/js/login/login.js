/*-----------------------------------------------------------------------------
* @Description:     登录首页
* @Version:         1.0.0
* @author:          yudan(862669640@qq.com)
* @date             2017.7.14
* ==NOTES:=============================================
* v1.0.0(2017.7.14):
     初始生成
* ---------------------------------------------------------------------------*/
$(document).ready(function(){
    formValidatorAddForm();

    /**
     * 提交数据
     *
     */

    $('.J_submitBtn').click(function(){
        var
            username = $('.J_username').val(),
            pwd = $('.J_pwd').val(),
            isRememberMe = $('.J_remember').is(':checked') + 0,
            form = {
                loginName: username,
                password: pwd,
                isRememberMe: isRememberMe
            };

        $.ajax({
            type: "POST",
            url: "/admin/ajax/login",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: form,
            dataType: "json",
            cache: false,
            success: function (rs) {
                if(rs.code == 1){
                    Alert("提示信息","用户名或者密码输入有误，请检查！");
                }else {
                    location.reload();
                }
            },
            error: function (message) {
                   Alert("提示信息","登陆出错了！");
            }
        });
    });

    /**
     * 重置按钮清除校验
     */
    $(".J_resetBtn").click(function(){
        location.reload();
    });

    /**
     * 登录验证
     */
    function formValidatorAddForm(){
        $('#J_form').bootstrapValidator({
            message: 'This value is not valid',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                loginName: {
                    validators: {
                        notEmpty: {
                            message: '用户名不能为空，请重新输入'
                        }
                    }
                },
                pwd: {
                    validators: {
                        notEmpty: {
                            message: '密码不能为空，请重新输入'
                        },
                        stringLength: {
                            min:6,
                            max:18,
                            message: '密码为6-18位'
                        }
                    }
                }
            }
        });
    }
});