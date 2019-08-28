app.service('FullScreenShowService', function () {

    var lm = new LoadingManager(Config.loadPicPath, "#fff");
    //记录状态当前页面是否正在显示
    this.isShow = false;
    var fullView;
    var _this = this;
    var fILOAr=new Array();
    function create(){
        fullView=$("<div></div>")
       $("body").append(fullView);
        fILOAr.push(fullView);
    }
    function hide(view){
        view.animate({
            left: $(window).width()

        }, 500, function () {
            $(".right").css("zIndex","");
            $(".left").css("zIndex","");
            view.remove();
        });

    }
    this.show = function (url) {

        create();
        $(".left").css("zIndex",-1);
        $(".right").css("zIndex",-1);
        lm.show();
            fullView.css({
            left:0,
            top: 0,
            width: $(window).width(),
            height: $(window).height(),
            opacity:1,
            background:"#d6d7da",
            position:"absolute",
            display:"none"
        });
        fullView.empty();
       
        
        fullView.load(url+"?dd=" + Math.random(), function () {
            fullView.show();
            _this.isShow = true;
            _this.notify("show");
        })
    }
    this.hideLoading=function(){
        lm.hide();
        _this.notify("hideLoading");
    }
    this.hideAll=function(){
        hide(fILOAr.pop());
        var view;
        while((view==fILOAr.pop())!=null){
            view.remove();
        }
        this.isShow = false;
    }
    this.hide = function () {
        var firstFullView=  fILOAr.pop();
        hide(firstFullView);
        _this.notify("hide");
        if(fILOAr.length==0)
            this.isShow = false;
    }

    this.notify = function (type) { }


});

