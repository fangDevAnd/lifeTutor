function LoadingManager(picPath, bgcolor) {
    this.color = bgcolor;
    var isShow=false;
    var maskLayer = $(" <div style='display:none;text-align:center'> <span style='height:100%; display:inline-block;vertical-align:middle'> </span><img style='vertical-align:middle' src='" + picPath + "' /><div>");
    
    $("body").append(maskLayer);
  
    this.show = function (selector) {
        var targetObj=    selector==null?null:$(selector);
        var top = targetObj == null ? 0 : targetObj.offset().top;
        var left = targetObj == null ? 0 : targetObj.offset().left;
        var width = targetObj == null ? $(document).width() : targetObj.width();
        var height = targetObj == null ? $(window).height() : targetObj.height();

        maskLayer.css({
            display:"block",
            position:"absolute",
            opacity: 1,
            top: top,
            left: left,
            width: width,
            height: height,
            zIndex:1000,
            background: this.color

        });
        maskLayer.show();
    }
    this.hide = function () {

        maskLayer.animate({
            opacity: 0,

        }, 300, function () {

            maskLayer.hide();
        });
    }

   

}