/*-----------------------------------------------------------------------------
 * @Description:     活动管理-活动详情
 * @Version:         1.0.0
 * @author:          gts(616603151@qq.com)
 * @date             2017.7.27
 * ==NOTES:=============================================
 *
 * ---------------------------------------------------------------------------*/
$(document).ready(function() {

    Pagination(1,{});
    /**
     * 取消提示
     */
    function showTip(){
        setTimeout(function(){
            $('.J_tip').hide();
        }, 2000);
    }
    $('#Menu3,#logoMenu3').trigger('click');

    /**
     * 分页
     */
    $('#pageLimit').bootstrapPaginator({
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
            status = $(".status").val(),
            currentPage = page,
            str = '',
            data = {
                pageNo: currentPage,
                eventTermId : $(".eventId").val(),
                participantName : $('input.J_selectName').val(),
                participantIdcard : $('input.J_selectId').val()
            };
        jQuery.extend(data, extraData);

        $.ajax({
            type: "POST",
            // url: jQuery.url.ECRBManagement.activitesDetailsList,
            url: "/admin/event/processing/ajax/eventNoticeList",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: data,     //JSON.stringify
            dataType: "json",
            cache:false,
            success: function (rs) {
                $('#J_template').empty();
                if( rs.code == 0){
                    $('.J_record').html(rs.numCount);
                    $.each(rs.list, function(index, item){
                        str += '<tr data-id="'+ item.id +'">\
                                    <td><input type="checkbox" class="J_select" value="'+ item.id +'"></td>\
                                    <td>'+ item.participantName +'</td>\
                                    <td>'+ item.participantIdcard +'</td>\
                                    <td>'+ item.participantTel +'</td>\
                                    <td>'+ item.noticeStatusValue+'</td>\
                                    <td>'+ new Date(item.bqstartDate).format("yyyy-MM-dd") +'</td>\
                                    <td>\
                                        <a href="/admin/event/processing/current/participant?id='+ item.id +'" class=" J_participation" > '+ item.eventParticipantTimes +'</a>\
                                    </td>\
                                    <td>'+ item.eventParticipantStatusValue +'</td>\
                                    <td>\
                                        <a href="/admin/event/processing/total/participant?id='+ item.id +'" class=" J_totalParticipation" > '+ item.totalParticipant +'</a>\
                                    </td>\
                                    <shiro:checkPemission name="Admin:E-CRB:Event:Processing:Record">'
                                    if(status == 2){
                                      str += '<td>\
                                              <a href="/admin/event/processing/record?eventTermId='+ item.eventTermId +'&clientId='+  item.clientId +'&eventRecordNoticeId='+  item.id+'" class="label-info J_record" ><i class="fa fa-book"></i>&nbsp;记录</a>\
                                              </td>'
                                    }
                        str += '</shiro:checkPemission>\
                                </tr>'
                    });
                    $('#J_template').append(str);
                    //暂无数据提示
                    var tr = $('#J_template').children();
                    if(tr.length == 0){
                        $('#J_template').append('<tr><td colspan="10">暂无数据</td></tr>');
                    }
                }else{
                    Alert("提示信息","数据刷新失败！");
                }
                $('#pageLimit').bootstrapPaginator({totalPages: rs.dataCount});
            },
            error: function (message) {
                Alert("提示信息","数据刷新失败！");
            }
        });
    }

    /**
     * 姓名-模糊匹配keyup事件——下拉框
     * @param  {[type]} ){                                          var            name [description]
     * @param  {[type]} dataType: "json"        [description]
     * @param  {String} success:  function      (rs)          {                                       var                                       li [description]
     * @param  {[type]} error:    function      (errMsg)      {                                       console.log(errMsg);            }                        } [description]
     * @return {[type]}           [description]
     */
    $('#J_selectName').keyup(function(){

        var
            param = {
                eventTermId : $(".eventId").val(),
                name : this.value
            };

        $.ajax({
            type: "GET",
            // url: jQuery.url.ClientManagement.selectNameData,
            url: "/admin/event/processing/ajax/searchByParticipantName",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: param,     //JSON.stringify
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
     * 身份证-模糊匹配keyup事件——下拉框
     * @param  {[type]} ){                                          var            idcard [description]
     * @param  {[type]} dataType: "json"        [description]
     * @param  {String} success:  function      (rs)          {                                         var                                       li [description]
     * @param  {[type]} error:    function      (errMsg)      {                                         console.log(errMsg);            }                        } [description]
     * @return {[type]}           [description]
     */
    $('#J_selectId').keyup(function(){
        var
            param = {
                eventTermId : $(".eventId").val(),
                idcardNum : this.value
            };

        $.ajax({
            type: "GET",
            // url: jQuery.url.ClientManagement.selectIdData,
            url: "/admin/event/processing/ajax/searchByParticipantIdcard",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: param,     //JSON.stringify
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
     * 列表点击搜索事件
     * @param  {[type]} ){                     }
     * @return {[type]}     [description]
     */
    $(".J_search").click(function(){
        var
            form = $(".J_searchForm").serializeObject();

        Pagination(1, form);
        $('#pageLimit').bootstrapPaginator({currentPage: 1});
    });
    /**
     * selectAll全选
     */
    $('.J_selectAll').click(function(){
        var
            selectMap = $('.J_select');
        if( selectMap.length != $('.J_select:checked').length){
            $('.J_selectAll').prop('checked', true);
            selectMap.prop('checked', true);
        }else{
            selectMap.prop('checked', false);
        }
    });

    /**
     * select按钮
     */
    $(document).on('click', '.J_select', function(){
        var
            selectMap = $('.J_select'),
            selectAll = $('.J_selectAll');
        if( selectMap.length == $('.J_select:checked').length){
            selectAll.prop('checked', true);
        }else{
            selectAll.prop('checked', false);
        }
    });

    /**
     * 点击批量添加
     */
    $('.J_addIntegral').click(function(){
        addAll();
    });

    /**
     * 判断是否选择添加项
     */
    function addAll(){
        var
            select = $('.J_select:checked').length;

        if(select == 0){
            $('#addTipDialog').modal();
        }else{
            $('#integralDialog').modal();
        }
    }

    /**
     * 批量编辑对话框确定事件
     */
    $('.J_integral').click(function(){
        sendAdd();
    });

    /**
     * 获取编辑项并发送数据
     */
    function sendAdd(){
        var
            selectMap = $('.J_select:checked'),
            pointItem= $(".J_pointItem option:selected").val(),
            idArray = [];

        $.each(selectMap, function(index, item){
            idArray.push(item.value);
        });

        data = {
            ids : idArray.toString(),
            pointsItemId : pointItem,
            eventTermId : $(".eventId").val()
        };

        $.ajax({
            type: "POST",
            // url: jQuery.url.ECRBManagement.addIntegral,
            url: "/admin/event/processing/ajax/batchPoints",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: data,     //JSON.stringify
            dataType: "json",
            success: function (rs) {
                $('#integralDialog').modal('hide');
                if( rs.code == 0){
                    location.reload();

                }else{
                    alert("出错了！");
                }

            },
            error: function (message) {
                $('#modalDialog').modal();
            }
        });
    }



});