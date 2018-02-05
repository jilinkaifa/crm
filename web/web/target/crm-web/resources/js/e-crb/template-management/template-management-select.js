/*-----------------------------------------------------------------------------
* @Description:     客户关怀-选择模板列表
* @Version:         1.0.0
* @author:          lily(529116421@qq.com)
* @date             2017.8.05
* ==NOTES:=============================================
* v1.0.0(2017.8.05):
     初始生成
* ---------------------------------------------------------------------------*/
$(function(){
	/**
     * 初始化提示信息、验证表单
     */
    Pagination(1); 


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
     * 列表点击选择事件
     */
    $(document).on('click','.J_templateId',function(e){
        var
            tr = $(e.target).parents('tr'),
            id = $(tr).attr('data-id');
        
        $('.hidId').val(id);
    });

    /**
     * 弹出框点击确定事件
     */
    $(".J_selectTemplate").click(function(){
        sendTemplateId();
    });

    /**
     * 分页
     */
    $('#pageLimit').bootstrapPaginator({
        //currentPage: 3,
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
            userid = $('.J_userId').val(),         
            str = '',
            data = jQuery.extend(extraData, {pageNo: currentPage});

        $.ajax({
            type: "GET",
            url: "/admin/event/template/ajax/select/list",
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
                                    <td>'+ item.number +'</td>\
                                    <td class="control-width width150">\
                                        <div title=" ' + item.name + ' " class="control-width width150">' + item.name + '</div>\
                                    </td>\
                                    <td>'+ item.type +'</td>\
                                    <td>'+ item.level +'</td>\
                                    <td>'+ item.startDate +'</td>\
                                    <td>'+ item.endDate +'</td>\
                                    <td>'+ item.memberGroupName +'</td>\
                                    <td>'+ item.createdPartyName +'</td>\
                                    <td>'+ item.createdTime+'</td>\
                                    <td>\
                        	           <a href="/admin/event/prepare/new?id='+ item.id +'" class="label-info J_templateId"><i class="fa fa-tag"></i>&nbsp;选择</a>\
                                    </td>\
                                </tr>';
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

            },
            error: function (message) {
                Alert("提示信息","数据刷新失败！");
            }
        });
    }

});