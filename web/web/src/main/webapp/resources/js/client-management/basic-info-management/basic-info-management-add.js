/*-----------------------------------------------------------------------------
 * @Description:     患者信息管理-基本信息添加
 * @Version:         1.0.0
 * @author:          sunwanlin(1124038074@qq.com)
 * @date             2017.7.21
 * ==NOTES:=============================================
 * v1.0.0(2017.7.21):
 初始生成
 * ---------------------------------------------------------------------------*/
$(document).ready(function(){
    /**
     * 初始化提示信息、验证表单
     */

    formValidatorAddForm();
    /**
     * 分页
     */
    $('#pageLimit').bootstrapPaginator({
        currentPage: 3,
        totalPages: 10,
        size: "small",
        bootstrapMajorVersion: 3,
        alignment: "right",
        numberOfPages: 5,
        itemTexts: function (type, page, current) {
            switch (type) {
                case "first": return "首页";
                case "prev": return "<<";
                case "next": return ">>";
                case "last": return "末页";
                case "page": return page;
            }
        },
        pageUrl:function (url,page,current) {
            return "";
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
                tel: {
                    validators: {
                        notEmpty: {
                            message: '电话不能为空'
                        },
                        regexp: {
                            //regexp: /^\d{11}$/,
                            regexp: /^((1[3,5,8][0-9])|(14[5,7])|(17[0,6,7,8])|(19[7]))\d{8}$/,
                            message: '请输入正确的号码'
                        }
                    }
                },
                idCardNum: {
                    validators: {
                        regexp: {
                            regexp: /^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/,
                            message: '请输入正确的身份证号码'
                        }
                    }
                },
                MCINum:{
                    validators: {
                        regexp: {
                            regexp: /^[A-Za-z0-9]+$/,
                            message: '医保卡号由数字、字母组成'
                        }
                    }
                },
                secTel:{
                    validators: {
                        regexp: {
                            //regexp: /^1[0-9]{10}(,1[0-9]{10})*$/,
                            regexp: /^[A-Za-z0-9]+(,[A-Za-z0-9]+)*$/,
                            message: '可以填写多个联系方式，中间以英文逗号隔开'
                        }
                    }
                },
                age: {
                    validators: {
                        regexp: {
                            regexp: /^120$|^((1[0-1]|[1-9])?\d)$/,
                            message: '年龄必须大于零的整数'
                        }
                    }
                },
                height: {
                    validators: {
                        regexp: {
                            regexp: /^\d+(\.\d+)?$/,
                            message: '身高必须为大于0的数'
                        }
                    }
                },
                weight: {
                    validators: {
                        regexp: {
                            regexp: /^\d+(\.\d+)?$/,
                            message: '体重必须为大于0的数'
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
    $('.J_save').click(function(){
        var
            form = $('.J_tableForm'),
            data = form.data('bootstrapValidator');
        if (data) {
            // 修复记忆的组件不验证
            data.validate();

            if (!data.isValid()) {//如果验证不通过
                return false;
            }else{//如果前台验证通过，则调用sendCardId()方法，验证身份证是否唯一
                sendCardId();
            }
        }
    });
    /**
     * 身份证input框失焦
     * 功能：失焦后开始向后台发送cardId,并验重
     * @param  {[type]} ){} [description]
     * @return {[type]}                    [description]
     */
    $(".J_idcardNum").blur(function(){
        var
            cardId = $(".J_idcardNum").val();
        if(cardId != ''){
            ageCalculated(cardId);
        }
    });
    /**
     * 身份号码验重
     * 功能：身份号码向后台发ajax，验证是否唯一，唯一则将年龄计算出来显示在input内
     * @return {[type]} [description]
     */
    function sendCardId(){
        var
            cardId=$(".J_idcardNum").val();

        if(cardId != ''){
            $.ajax({
                type: "GET",
                url: "/admin/client/basic/ajax/checkIdCard",
                contentType: "application/x-www-form-urlencoded; charset=utf-8",
                data: {
                    idCard:cardId
                },     //JSON.stringify
                dataType: "json",
                success: function (rs) {
                    if( rs.code == 0){
                        //如果发送成功，并且身份证验证唯一，则计算年龄
                        // ageCalculated(ca
                        // rdId);
                        //计算年龄之后向后台发送整个表单内容
                        save();
                    }else{
                        Alert("提示信息", "身份证号码重复，请重新操作！");
                    }
                },
                error: function (message) {
                    Alert("提示信息", "操作失败，请重新操作！");
                }
            });
        }else{
            save();
        }
    }
    /**
     * 计算年龄函数
     * @return {[type]} [description]
     */
    function ageCalculated(cardId){

        var
            birthYear=cardId.substr(6,4),
            myDate = new Date(),
            nowYear=myDate.getFullYear(),
            age=nowYear-birthYear;
            
        $(".J_age").val(age);

        var ageValue = $(".J_age").val(),
            ageType = isInteger(ageValue);

        if(!ageType){
            if($(".ageValue").length==0){
                $('.J_age').after('<small class="ageValue has-error help-block" data-bv-validator="notEmpty" data-bv-for="name" data-bv-result="INVALID" style="color:#a94442;">年龄必须为大于零的整数</small>');
            }
        }else{
            if($(".ageValue").length>0){
                $('.ageValue').remove();
            }
        }
    }
    /**
     * 验证是否为大于零的整数
     * @param  {[type]}  obj [description]
     * @return {Boolean}     [description]
     */
    function isInteger(obj) {
        return obj%1 == 0 && obj>0 ;
    }
    /**
     * 保存事件
     * @return {[type]} [description]
     */
    function save(){

        var serializeForm=$('.J_tableForm').serializeObject();

        $.ajax({
            type: "POST",
            url:"/admin/client/basic/ajax/save",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: serializeForm,     //JSON.stringify
            dataType: "json",
            success: function (rs) {
                if( rs.code == 0){
                    LiterTip("提示信息","新增成功");
                    setTimeout(function(){
                        window.location.href= "/admin/client/basic";
                    }, 1000);
                }else{
                    Alert("提示信息",rs.errMsg);
                }
            },
            error: function (message) {
                Alert("提示信息", "操作失败，请重新操作！");
            }
        });
    }

    /**
     * 填写联系人事件
     */
    //hide();
   // $('.J_secContact').blur(function() {
        //hide();
    //});
    // function hide(){
    //     var
    //         secContact = $('.J_secContact').val();
    //     console.log(secContact);
    //     if(secContact != ''){
    //         $('.J_dicSecRelation').attr("disabled", false);
    //         $('.J_secTel').attr("disabled", false);
    //     }else{
    //         $('.J_dicSecRelation').attr("disabled", "disabled");
    //         $('.J_secTel').attr("disabled", "disabled");
    //         $("[name='dicSecRelation']").val('');
    //         $('.J_secTel').val("");
    //     }
    // }
});