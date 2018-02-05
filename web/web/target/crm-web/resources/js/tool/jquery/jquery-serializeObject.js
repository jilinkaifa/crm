/*-----------------------------------------------------------------------------
* @Description:     序列化成对象
* @Version:         1.0.0
* @author:          zhangxn(571946808@qq.com)
* @date             2017.7.21
* ==NOTES:=============================================
* v1.0.0(2017.7.21):
     初始生成
* 针对于jQuery序列化不能序列化成对象进行解决
* ---------------------------------------------------------------------------*/
$.fn.serializeObject = function(){ 
    var obj = new Object(); 
    $.each(this.serializeArray(),function(index,param){ 
        if(!(param.name in obj)){ 
            obj[param.name]=param.value; 
        } 
    }); 
    return obj; 
}; 