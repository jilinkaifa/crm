/*-----------------------------------------------------------------------------
 * @Description:     进行中的活动通知页
 * @Version:         1.0.0
 * @author:          zhangfc(546641398@qq.com)
 * @date             2017.7.28
 ---------------------------------------------------------*/
$(function(){
    /**
     * 初始化提示信息、验证表单
     */
    $('#Menu3,#logoMenu3').trigger('click');
    Pagination(1,{});


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
            str = '',
            data = {
                pageNo: currentPage,
                clientId : $(".clientId").val()
            };
        jQuery.extend(data, extraData);

        $.ajax({
            type: "POST",
            // url: jQuery.url.ECRBManagement.informList,
            url: "/admin/event/processing/ajax/eventTermList",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: data,
            dataType: "json",
            cache:false,
            success: function (rs) {
                $('#J_template').empty();
                if( rs.code == 0){
                    $('.J_record').html(rs.numCount);
                    $.each(rs.list, function(index, item){
                        str += '<tr data-id="'+ item.id +'">\
                                    <td><input type="checkbox" class="J_select" value="'+ item.id +'"></td>\
                                    <td>'+ item.name +'</td>\
                                    <td>'+ item.typeValue +'</td>\
                                    <td>'+ item.levelValue +'</td>\
                                    <td>'+ new Date(item.startDate).format("yyyy-MM-dd") +'</td>\
                                    <td>'+ item.noticeContent +'</td>\
                                </tr>'
                    });
                    $('#J_template').append(str);
                    //暂无数据提示
                    var tr = $('#J_template').children();
                    if(tr.length == 0){
                        $('#J_template').append('<tr><td colspan="6">暂无数据</td></tr>');
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

    //点击通知按钮，弹出提示框
    $('.J_inform').click(function(){
        openTipDlg();
        // sendDel();
    })

    /**
     * 根据是否有通知项弹出不同的提示框
     * @return {[type]} [description]
     */
    function openTipDlg(){
        var checked = $('input[type=checkbox]:checked').length;
        if(checked == 0){
            $('#delTipDialog').modal();
        }else{
            $('#informDialog').modal();
        }
    }

    /*
     **弹出框确定按钮
     */
    $('.J_delAllDlg').click(function(){
        var
            selectMap = $('.J_select:checked'),
            tr = selectMap.parents('tr'),
            idArray = [],
            data;
        form = $('.J_editForm').serializeObject();
        $.each(tr, function(){
            id = $(this).attr('data-id'),
                idArray.push(id);
        });
        data = {id: idArray.toString()};
        jQuery.extend(form, data);
        var param = {
            id : idArray.toString(),
            status : $(".J_selectName").val(),
            comment : $(".J_editRemInput").val()
        };
        $.ajax({
            type: "POST",
            // url: jQuery.url.ECRBManagement.informOk,
            url: "/admin/event/processing/ajax/batchNotice",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            // data: JSON.stringify(data),     //JSON.stringify
            data:param,
            dataType: "json",
            success: function (rs) {
                if( rs.code == 0){
                    location.reload();
                }else{
                    location.reload();
                }
            },
            error: function (message) {
                $('#modalDialog').modal();
            }
        });
    });
})