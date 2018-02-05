/*-----------------------------------------------------------------------------
* @Description:     基于Bootstrap简单对话框实现
* @Version:         1.0.0
* @author:          zhangxn(571946808@qq.com)
* @date             2017.8.8
* ==NOTES:=============================================
* v1.0.0(2017.8.8):
     初始生成，主要存在Alert与Confirm
* ---------------------------------------------------------------------------*/
function Alert(title, msg, callback){
    if($('#alert').length > 0){
        $('.title-alert').text(title);
        $('.msg-alert').text(msg);
        console.log(title, msg)
    }else{
        var
            str = '<div class="modal fade" id="alert" tabindex="-1">\
                <div class="modal-dialog">\
                    <div class="modal-content">\
                        <div class="modal-header">\
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>\
                            <h4 class="modal-title title-alert">'+ title +'</h4>\
                        </div>\
                        <div class="modal-body">\
                            <p class="msg-alert">'+ msg +'</p>\
                        </div>\
                        <div class="modal-footer">\
                            <button type="button" class="btn btn-success alertDialog" data-dismiss="modal">确定</button>\
                        </div>\
                    </div>\
                </div>\
            </div>';

        $(document.body).append(str);
    }
    
    $('#alert').modal();
    if(typeof(callback) == 'function'){
        $(document).on('click', 'button.alertDialog', callback);
    }
}

function Confirm(title, msg, callback){
    if($('#confirm').length > 0){
        $('.title-confirm').text(title);
        $('.msg-confirm').text(msg);
    }else{
        var
            str = '<div class="modal fade" id="confirm" tabindex="-1">\
            <div class="modal-dialog">\
                <div class="modal-content">\
                    <div class="modal-header">\
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>\
                        <h4 class="modal-title title-confirm">'+ title +'</h4>\
                    </div>\
                    <div class="modal-body">\
                        <p class="msg-confirm">'+ msg +'</p>\
                    </div>\
                    <div class="modal-footer">\
                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>\
                        <button type="button" class="btn btn-success confirmDialog" data-dismiss="modal">确定</button>\
                    </div>\
                </div>\
            </div>\
        </div>';

        $(document.body).append(str);
    };

    $('#confirm').modal();
    if(typeof(callback) == 'function'){
        $(document).on('click', 'button.confirmDialog', callback);
    }
}
function LiterTip(title, msg){
    if($('#literTip').length > 0){
        $('.title-literTip').text(title);
        $('.msg-literTip').text(msg);
    }else{
        var
            str = '<div class="modal fade" id="literTip" tabindex="-1">\
                <div class="modal-dialog">\
                    <div class="modal-content">\
                        <div class="modal-header">\
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>\
                            <h4 class="modal-title title-literTip">'+ title +'</h4>\
                        </div>\
                        <div class="modal-body">\
                            <p class="msg-literTip">'+ msg +'</p>\
                        </div>\
                    </div>\
                </div>\
            </div>';

        $(document.body).append(str);
    }
    
    $('#literTip').modal();
}


/**
 * Alert('提示信息', '信息！', function(){
        console.log('alert');
    }); 
    Confirm('提示信息', '信息！', function(){
        console.log('confirm');
    }); 
 */
