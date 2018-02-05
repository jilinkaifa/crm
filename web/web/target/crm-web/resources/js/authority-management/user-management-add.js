/*-----------------------------------------------------------------------------
* @Description:     权限管理-会员添加
* @Version:         1.0.0
* @author:          sunwanlin(1124038074@qq.com)
* @date             2017.7.20
* ==NOTES:=============================================
* v1.0.0(2017.7.20):
     初始生成
* ---------------------------------------------------------------------------*/
$(document).ready(function(){
    /**
     * 初始化提示信息、验证表单
     */
    // showTip();
    formValidatorAddForm();
    $('#Menu4,#logoMenu4').trigger('click');
    /**
     * 取消提示
     */
    function showTip(){
        setTimeout(function(){
            $('.J_tip').hide();
        }, 2000);
    }
    var flag;

    function valueIt(){
        flag = true;
    }
    /**
     * 真实姓名input失焦
     */
    $(".J_loginName").blur(function(){
        sendUserName();
    });
    /**
     * 发送真实姓名
     */
    function sendUserName(){
        var
            userName = $(".J_loginName").val();

        if(userName != ''){
            $.ajax({
                type: "GET",
                url: "/admin/party/group/employee/ajax/check/loginname",
                contentType: "application/x-www-form-urlencoded; charset=utf-8",
                data: {
                    userName: userName
                },     //JSON.stringify
                dataType: "json",
                success: function (rs) {
                    if(rs.code != 0){
                        $('#modalDialog').modal();
                    }else{//验证成功
                        valueIt();
                    }
                },
                error: function (errMsg) {
                    $('#errorDialog').modal();
                }
            });
        }
    }
    /**
     * 保存事件
     */
    function save(){
        var serializeForm=$('.J_tableForm').serializeObject();
            $.ajax({
            type: "POST",
            // url: jQuery.url.AuthorityManagement.addUser,
            url: "/admin/party/group/employee/ajax/save",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: serializeForm,     //JSON.stringify
            dataType: "json",
            success: function (rs) {
                if( rs.code == 0){
                    Alert("提示信息","保存成功！",function () {
                        window.location.href = "/admin/party/group/employee/list"
                    });
                }else{
                    Alert("提示信息","保存失败！");
                }
            },
            error: function (message) {
                Alert("提示信息","保存失败！");
            }
        });
    }
    /**
     * 点击save按钮时提交数据
     * @param  {[type]} ){                     var data [description]
     * @return {[type]}     [description]
     */
    $('.J_save').click(function(){
        var 
            form = $('.J_tableForm'),
            data = form.data('bootstrapValidator');
        if (data) {
        // 修复记忆的组件不验证
            data.validate();

            if (!data.isValid()) {
                return false;
            }else{
                if(flag){
                    save();
                    return true;
                }else{
                    $('#modalDialog').modal();
                }
            }
        }
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
    /**
     * 添加框验证
     * [formValidatorAddForm description]
     * @return {[type]} [description]
     */
    function formValidatorAddForm(){
        $('.J_tableForm').bootstrapValidator({
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
                            message: '姓名不能为空'
                        }
                    }
                },
                phone: {
                    validators: {
                        notEmpty: {
                            message: '电话号码不能为空'
                        },
                        regexp: {
                            regexp: /^\d{11}$/,
                            message: '请输入正确的号码'
                        }
                    }
                },
                loginName: {
                    validators: {
                        notEmpty: {
                            message: '账户名不能为空'
                        }
                    }
                },
                idcardNum: {
                    validators: {
                        stringLength: {
                            min:18,
                            max:18,
                            message: '必须为18位'
                        }
                    }
                },
                password: {
                    validators: {
                        notEmpty: {
                            message: '密码不能为空'
                        },
                        stringLength: {
                            min:6,
                            max:18,
                            message: '密码长度为6-18位'
                        }
                    }
                },
                email: {
                    validators: {
                        emailAddress: {
                            message: '不是标准邮箱格式'
                        }
                    }
                }
            }
        });
    }
});