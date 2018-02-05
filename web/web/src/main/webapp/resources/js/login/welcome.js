
$(function(){

    //侧边栏隐藏
        if ($('.bars').siblings().is(":visible") === true) {
            $('.sidebar').hide();
            $('.holder, .footer').css({
                'margin-left': '30px'
            });
            $('.sidebar').css({
                'margin-left': '-200px'
            });
            $('.fa-exchange').css({
                'width':'30px'
            });
            $('#firstMenu1').hide();
            $('#firstMenu2').hide();
            $('#firstMenu3').hide();
            $('#firstMenu4').hide();

        }
})
   