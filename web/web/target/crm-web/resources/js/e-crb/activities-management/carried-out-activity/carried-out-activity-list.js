/*-----------------------------------------------------------------------------
* @Description:     活动管理-待开展活动列表页
* @Version:         1.0.0
* @author:          gts(616603151@qq.com)
* @date             2017.7.24
* ==NOTES:=============================================
* 
* ---------------------------------------------------------------------------*/
$(document).ready(function() {
     // showTip();
    Pagination(1);
	 /**
     * 取消提示
     */
    function showTip(){
        setTimeout(function(){
            $('.J_tip').hide();
        }, 2000);
    }

    /**
     * 点击搜索事件
     * @param  {[type]} ){                     search();    } [description]
     * @return {[type]}     [description]
     */
    
    $(".J_search").click(function(){
        var
            form = $(".J_searchForm").serializeObject();

        Pagination(1, form);
        $('#pageLimit').bootstrapPaginator({currentPage: 1});
    });

      /**
     * 操作注销点击事件
    */

   $(document).on('click', '.J_cancle', function(e){
        var
            tr = $(e.target).parents('tr'),
            id = $(tr).attr('data-id');

        $('.hidId').val(id);
    });



     /**
     * 操作注销按钮
     
     */
    $(document).on('click', '.J_cancleDlg', function() {
        cancle();
    });
    /**
     * 注销事件
     */

    function cancle() {
        var id = $(".hidId").val(),
        form = {
            id: id
        };
        
        $.ajax({
            type: "POST",
            url: "/admin/event/prepare/ajax/logout",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: form,
            //JSON.stringify
            dataType: "json",
            success: function(rs) {
                $('#cancleDialog').modal('hide');
                if (rs.code == 0) {
                    location.reload();
                    $('#cancleDialog').modal('hide');
                } else {
                    $('#modalDialog').modal();
                }

            },
            error: function(message) {
                console.log(message);
                $('#cancleDialog').modal('hide');
                $('#modalDialog').modal();
            }
        });
    }
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
            currentPage = page,
            userid = $('.J_userId').val(),
            str = '',
            data = {
                pageNo: currentPage
            };
        jQuery.extend(data, extraData);

        $.ajax({
            type: "GET",
            url: "/admin/event/prepare/ajax/list",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: data,     //JSON.stringify
            dataType: "json",
            cache:false,
            success: function (rs) {
                $(".pageDataCount").val(rs.dataCount);
                $('#J_template').empty();
                if( rs.code == 0){
                    $('.J_record').html(rs.numCount);
                    $.each(rs.list, function(index, item){
                        str += '<tr data-id="'+ item.id +'"  data-userId="'+ item.createdPartyId +'" >\
                                    <td class="control-width width150">\
                                        <div title=" ' + item.name + ' " class="control-width width150">' + item.name + '</div>\
                                    </td>\
                                    <td>'+ item.type +'</td>\
                                    <td>'+ item.level+'</td>\
                                    <td>'+ item.approvalStatusValue +'</td>\
                                    <td>'+ item.startDate +'</td>\
                                    <td>'+ item.endDate +'</td>\
                                    <td class="control-width width2">\
                                        <div title=" ' + item.memberGroupName + ' " class="control-width width2">' + item.memberGroupName + '</div>\
                                    </td>\
                                    <td>'+ item.createdPartyName +'</td>\
                                    <td>'+ item.createdTime +'</td>\
                                    <td>';
                        if(userid == item.createdPartyId){
                            if(item.approvalStatus == 2){
                                str += '<shiro:checkPermission name="Admin:E-CRB:Event:Prepare:Detail">\
                                        <a href="/admin/event/prepare/detail?id='+ item.id +'" class="label-info J_see"><i class="fa fa-book"></i>&nbsp;查看</a>\
                                        </shiro:checkPermission>';
                            } else {
                                str += '<shiro:checkPermission name="Admin:E-CRB:Event:Prepare:Detail">\
                                        <a href="/admin/event/prepare/detail?id='+ item.id +'" class="label-info J_see"><i class="fa fa-book"></i>&nbsp;查看</a>\
                                        </shiro:checkPermission>\
                                        <shiro:checkPermission name="Admin:E-CRB:Event:Prepare:Update">\
                                        <a href="/admin/event/prepare/update?id='+ item.id +'" class="label-info J_revise"><i class="fa fa-pencil"></i>&nbsp;修改</a>\
                                        </shiro:checkPermission>\
                                        <shiro:checkPermission name="Admin:E-CRB:Event:Prepare:Logout">\
                                        <a href="#" class="label-info J_cancle" data-toggle="modal" data-target="#cancleDialog"><i class="fa fa-exclamation"></i>&nbsp;注销</a>\
                                        </shiro:checkPermission>';
                            }
                        } else if(item.approval == 1){
                            str += '<shiro:checkPermission name="Admin:E-CRB:Event:Prepare:Detail">\
                                    <a href="/admin/event/prepare/detail?id='+ item.id +'" class="label-info J_see"><i class="fa fa-book"></i>&nbsp;查看</a>\
                                    </shiro:checkPermission>\
                                    <shiro:checkPermission name="Admin:E-CRB:Event:Prepare:Approval">\
                                    <a href="#" class="label-info J_approve" data-toggle="modal" data-target="#approveDialog"><i class="fa fa-check-square-o"></i>&nbsp;审批</a>\
                                    </shiro:checkPermission>';
                        } else{
                            str += '<shiro:checkPermission name="Admin:E-CRB:Event:Prepare:Detail">\
                                        <a href="/admin/event/prepare/detail?id='+ item.id +'" class="label-info J_see"><i class="fa fa-book"></i>&nbsp;查看</a>\
                                    </shiro:checkPermission>';
                        }
                        str += '</td></tr>';
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
    * 操作审批点击事件
    */

   $(document).on('click', '.J_approve', function(e){
        var
            tr = $(e.target).parents('tr'),
            id = $(tr).attr('data-id');

        $('.hidId').val(id);
      
    });



     /**
     * 操作审批按钮
     
     */
    $(document).on('click', '.J_approveDlg', function() {
        approve();
    });
    /**
     * 审批事件
     */

    function approve() {
        var 
            id = $(".hidId").val(),
            examineApprove=$(".J_examineApprove").val(),
            remaks=$(".J_remarks").val();
        if(id == ""){
            Alert("提示信息", "活动ID信息为空！");
            return;
        }
        if(examineApprove == "" ||examineApprove == "0"){
            Alert("提示信息", "请选择审批通过或驳回！");
            return;
        }
        form = {
            id: id,
            status:examineApprove,
            comment:remaks,
        };
        
        $.ajax({
            type: "POST",
            url: "/admin/event/prepare/ajax/approval",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: form,
            //JSON.stringify
            dataType: "json",
            success: function(rs) {
                $('#approveDialog').modal('hide');
                if (rs.code == 0) {
                    location.reload();
                } else {
                    //$('#modalDialog').modal();
                    Alert("提示信息", rs.errMsg);
                }

            },
            error: function(message) {
                $('#approveDialog').modal('hide');
                $('#modalDialog').modal();
            }
        });
    }

    /**
     * 活动名称-模糊匹配keyup事件——下拉框
     * @param  {[type]} ){                                          var            activityName[description]
     * @param  {[type]} dataType: "json"        [description]
     * @param  {String} success:  function      (rs)          {                                        var                                       li [description]
     * @param  {[type]} error:    function      (errMsg)      {                                        console.log(errMsg);            }                        } [description]
     * @return {[type]}           [description]
     */
    $('#J_selectActivity').keyup(function(){
        var
            activityName= this.value;
        $.ajax({
            type: "GET",
            url: "/admin/event/prepare/ajax/name",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: {name: activityName},     //JSON.stringify
            dataType: "json",
            success: function (rs) {
                $('#J_selectActivity').typeahead({
                    source: rs.list
                });
            },
            error: function (errMsg) {
                console.log(errMsg);
            }
        });
    });


});