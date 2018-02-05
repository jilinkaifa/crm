/*-----------------------------------------------------------------------------
 * @Description:     患者管理-查看患者详情
 * @Version:         1.0.0
 * @author:          gts(616603151@qq.com)
 * @date             2017.7.15
 * ==NOTES:=============================================
 * v1.0.0(2015.9.17):
 初始生成
 * ---------------------------------------------------------------------------*/
$(function(){
    /**
     * 取消提示
     */
    function showTip(){
        setTimeout(function(){
            $('.J_tip').hide();
        }, 2000);
    }
    /**
     * 点击save按钮时提交数据
     * @param  {[type]} ){                     var data [description]
     * @return {[type]}     [description]
     */
    $('.J_save').click(function(){
        var
            tableName = $(".J_idCard").val();
        if(tableName == ""){
            Alert("提示信息", "身份证号为空不能加入会员，请返回修改患者信息！")
        }else{
            $('#joinDialog').modal();
        }
    });
    /**
     * 取id值
     */
    $(document).on('click', '.J_join',function(){
        var id = $("#J_clientId").val();
        var param = {
            id: id
        };
        /**
         * 发送id事件
         */

        $.ajax({
            type: "GET",
            url: "/admin/client/basic/ajax/addMember",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: param,
            dataType: "json",
            success: function (rs) {
                $('#joinDialog').modal('hide');
                if (rs.code == 0) {
                    window.location.href="/admin/client/member/update?id="+id;
                } else {
                    $('#modalDialog').modal();
                }

            },
            error: function(message) {
                $('#joinDialog').modal('hide');
                $('#modalDialog').modal();
            }
        });

    });
});