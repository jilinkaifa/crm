/*-----------------------------------------------------------------------------
 * @Description:     问卷管理列表
 * @Version:         1.0.0
 * @author:          yudan (862669640@qq.com)
 * @date             2017.7.20
 * ==NOTES:=============================================
 * v1.0.0(2017.7.20):
 初始生成
 * ---------------------------------------------------------------------------*/
$(function(){
    /**
     * 初始化提示信息、验证表单
     */
    // showTip();
    Pagination(1,{});

    /**
     * 取消提示
     */
    function showTip(){
        setTimeout(function(){
            $('.J_tip').hide();
        }, 2000);
    };

    /**
     * 问卷次数验证
     * [formValidatorAddForm description]
     * @return {[type]} [description]
     */
    formValidatorAddForm();
    function formValidatorAddForm(){
        $('.J_searchForm').bootstrapValidator({
            message: 'This value is not valid',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                questCount: {
                    validators: {
                        regexp: {
                            regexp: /^[1-9]\d*|0$/,
                            message: '问卷次数请输入为正整数'
                        }
                    }
                }
            }
        });
    }
    /**
     * 分页
     */
    $('#pageLimit').bootstrapPaginator({
        totalPages: $('.pageDataCount').val(),
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
            var
                form = $(".J_searchForm").serializeObject();
            Pagination(page, form);
        }
    });

    /**
     * 分页刷数据
     */
    function Pagination(page, extraData){

        var
            currentPage = page,
            str = '',
            data = {
                page: currentPage
            };
        jQuery.extend(data, extraData);

        $.ajax({
            type: "GET",
            url:"/admin/client/quest/ajax/list",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: data,     //JSON.stringify
            dataType: "json",
            cache:false,
            success: function (rs) {
                $('#J_template').empty();
                $('#pageLimit').bootstrapPaginator({totalPages: rs.dataCount});
                if( rs.code == 0){
                    $('.J_record').html(rs.numCount);
                    $('.pageDataCount').val(rs.dataCount);
                    $.each(rs.list, function(index, item){
                        str += '<tr data-id="'+ item.id +'">\
                                    <td>'+ item.number +'</td>\
                                    <td class="control-width width1">\
                                        <div title=" ' + item.name + ' " class="control-width width1">' + item.name + '</div>\
                                    </td>\
                                    <td>'+ item.idCardNum +'</td>\
                                    <td>'+ item.tel +'</td>\
                                    <td>'+ item.allCost +'</td>\
                                    <td>'+ item.memberGroup +'</td>\
                                    <td>'+ item.level +'</td>\
                                    <td>'+ item.questCount +'</td>\
                                    <td><span show-quest-time = "'+ item.showQuestTime +'"  class="J_nextQuestTime">'+ item.nextQuestTime +'</span></td>\
                                    <td>\
                                        <shiro:checkPermission name="Admin:Client:Quest:Detail">\
                                        <a href="/admin/client/quest/detail?id='+ item.id +'" class="label-info"><i class="fa fa-book"></i>&nbsp;详情</a>\
                                        </shiro:checkPermission>\
                                        <shiro:checkPermission name="Admin:Client:Quest:New">\
                                        <a href="/admin/client/quest/add?id='+ item.id +'"  class="label-info J_questBtn"><i class="fa fa-file-text-o"></i>&nbsp;问卷</a>\
                                        </shiro:checkPermission>\
                                    </td>\
                                </tr>'
                    });
                    $('#J_template').append(str);
                    //暂无数据提示
                    var tr = $('#J_template').children();
                    if(tr.length == 0){
                        $('#J_template').append('<tr><td colspan="10">暂无数据</td></tr>');
                    }
                    //判断当前日期是否可填写问卷
                    showBtn();
                }else{
                    Alert("提示信息", "数据刷新失败！");
                }
            },
            error: function (errMsg) {
                Alert("提示信息", "数据刷新失败！");
            }
        });
    };

    /**
     * [showBtn description]根据下次计划时间显示\隐藏问卷按钮
     * @return {[type]} [description]
     */
    // function showBtn(){
    //     var curTime = new Date();
    //     $('#J_template tr').each(function(item){
    //         var showQuest = $(this).find('.J_nextQuestTime').attr("show-quest-time"),
    //             showQuestTime = new Date(Date.parse(showQuest));
    //         if(curTime>showQuestTime){
    //             $(this).find('.J_questBtn').removeAttr("hidden");
    //         }
    //     })
    // };

    /**
     * 列表点击搜索事件
     * @param  {[type]}
     * @return {[type]}     [description]
     */
    $(".J_search").click(function(){
        var
            form = $(".J_searchForm").serializeObject();
        Pagination(1, form);
        $('#pageLimit').bootstrapPaginator({currentPage: 1});
    });

    /**
     * 姓名-模糊匹配-keyup事件
     */
    $('#J_selectName').keyup(function(){
        var
            name = this.value;

        $.ajax({
            type: "GET",
            url: "/admin/client/quest/ajax/name",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: {name: name},     //JSON.stringify
            dataType: "json",
            success: function (rs) {
                $('#J_selectName').typeahead({
                    source: rs.list
                });
            },
            error: function (errMsg) {
                console.log(errMsg);
            }
        });
    });

    /**
     * 身份证-模糊匹配-keyup事件
     */
    $('#J_selectId').keyup(function(){
        var
            idcard = this.value;
        $.ajax({
            type: "GET",
            url: "/admin/client/quest/ajax/idCard",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: {idCard: idcard},
            dataType: "json",
            success: function (rs) {
                $('#J_selectId').typeahead({
                    source: rs.list
                });
            },
            error: function (errMsg) {
                console.log(errMsg);
            }
        });
    });

    /**
     * 电话-模糊匹配-keyup事件
     */
    $('#J_selectPhone').keyup(function(){
        var
            phone = this.value;
        $.ajax({
            type: "GET",
            url: "/admin/client/quest/ajax/tel",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: {tel: phone},     //JSON.stringify
            dataType: "json",
            success: function (rs) {
                $('#J_selectPhone').typeahead({
                    source: rs.list
                });
            },
            error: function (errMsg) {
                console.log(errMsg);
            }
        });
    });
});