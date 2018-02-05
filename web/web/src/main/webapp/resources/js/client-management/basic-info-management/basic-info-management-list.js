/*-----------------------------------------------------------------------------
 * @Description:     患者基本信息管理
 * @Version:         1.0.0
 * @author:          zhangxn(571946808@qq.com)
 * @date             2017.7.20
 * ==NOTES:=============================================
 * v1.0.0(2017.7.20):
 初始生成
 * ---------------------------------------------------------------------------*/
$(function(){

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
                page: currentPage
            };
        jQuery.extend(data, extraData);

        $.ajax({
            type: "GET",
            url: "/admin/client/basic/ajax/list",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: data,     //JSON.stringify
            dataType: "json",
            cache: false,
            success: function (rs) {
                if( rs.code == 0){
                    $('.J_record').html(rs.numCount);
                    $('#J_template').empty();
                    $('#pageLimit').bootstrapPaginator({totalPages: rs.dataCount});
                    $.each(rs.list, function(index, item){
                        str += '<tr data-id="'+ item.id +'">\
                                    <td>'+ item.number +'</td>\
                                    <td class="control-width width1">\
                                        <div title=" ' + item.name + ' " class="control-width width1">' + item.name + '</div>\
                                    </td>\
                                    <td>'+ item.gender +'</td>\
                                    <td>'+ item.idCardNum +'</td>\
                                    <td>'+ item.tel +'</td>\
                                    <td title=" ' + item.diagnoseType + ' " class="listStyle"><div class="listStyle">'+ item.diagnoseType +'</div></td>\
                                    <td>'+ item.dicType +'</td>\
                                    <td>'+ item.dicMciType +'</td>\
                                    <td>'+ item.allCost +'</td>\
                                    <td>\
                                        <shiro:checkPermission name="Admin:Client:Basic:Update">\
                                        <a href="/admin/client/basic/update?id='+ item.id +'" class="label-info"><i class="fa fa-pencil"></i>&nbsp;修改</a>\
                                        </shiro:checkPermission>\
                                        <shiro:checkPermission name="Admin:Client:Basic:Detail">\
                                        <a href="/admin/client/basic/detail?id='+ item.id +'" class="label-info"><i class="fa fa-book"></i>&nbsp;详情</a>\
                                        </shiro:checkPermission>\
                                        <shiro:checkPermission name="Admin:Client:Basic:Delete">\
                                        <a href="#" class="label-info J_del" data-toggle="modal" data-target="#delDialog"><i class="fa fa-times"></i>&nbsp;删除</a>\
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
                }else{
                    Alert("提示信息", "数据刷新失败！");
                }
            },
            error: function (message) {
                Alert("提示信息", "数据刷新失败！");
            }
        });
    }

    /**
     * 表格删除按钮
     * @param  {[type]} e){                 } [description]
     * @return {[type]}      [description]
     */
    $(document).on('click', '.J_del', function(e){
        var
            tr = $(e.target).parents('tr'),
            id = $(tr).attr('data-id');

        $('.hidId').val(id);
    });

    /**
     * 删除按钮
     * @param  {[type]} e){                 } [description]
     * @return {[type]}      [description]
     */
    $('.J_delDlg').click(function(){
        del();
    });

    /**
     * 删除事件
     */
    function del(){
        var
            id = $('.hidId').val(),
            form = {
                id: id
            };

        $.ajax({
            type: "POST",
            url: "/admin/client/basic/ajax/del",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: form,     //JSON.stringify
            dataType: "json",
            success: function (rs) {
                $('#delDialog').modal('hide');
                if(rs.code == 0){
                    Alert("提示信息", rs.errMsg);
                    window.location.reload();
                }else{
                    Alert("提示信息", rs.errMsg);
                }
            },
            error: function (message) {
                console.log('删除失败!');
            }
        });
    }

    /**
     * 姓名-模糊匹配-keyup事件
     */
    $('#J_selectName').keyup(function(){
        var
            name = this.value;
        $.ajax({
            type: "GET",
            url: "/admin/client/basic/ajax/name",
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
            url: "/admin/client/basic/ajax/idCard",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: {idCard: idcard},     //JSON.stringify
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
            url: "/admin/client/basic/ajax/tel",
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
     * 批量导入
     */
    $(".J_import").click(function() {
        if( filterFile() ){
            $.ajaxFileUpload({
                url: "/admin/client/basic/ajax/import",
                contentType: "application/x-www-form-urlencoded; charset=utf-8",
                secureuri: false,
                fileElementId: 'fileToUpload', //文件选择框的id属性
                dataType : "text/html",//服务器返回的格式，可以是json
                //相当于java中try语句块的用法
                success: function (data) {
                    //关闭遮盖层
                    $("#exportDialog").modal('hide');
                    data = JSON.parse(data);
                    if(data.code == 0){
                        Confirm("提示信息", data.errMsg,function(){
                            window.location.reload();
                        })
                    }else{
                        Alert("提示信息", data.errMsg);
                    }
                },
                error: function () {
                    Alert("导入出错!" );
                }
            });
        }
    });

    /**
     * 限制上传文件格式
     */
    function filterFile(){
        var
            filepath = $("#fileToUpload").val(),
            extStart = filepath.lastIndexOf("."),
            ext = filepath.substring(extStart, filepath.length).toUpperCase();

        if (ext != ".XLS" && ext != ".XLSX") {
            Alert("提示信息", "文件格式不正确");
            $("#fileToUpload").val("");
            return false;
        }
        return true;
    }


});