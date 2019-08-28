/**
 * Created by customer on 2016/5/24.
 */

function AnimationTypeFatory(ele,animationType){

    style="display: none; width: 100%; position:absolute;top: 0;left: 0;z-index:1000;background: '+bgColor+';opacity:0"

    this.notify=function(){};
    this.startCss={};
    this.endCss={};
    this.showTime=0;
    this.hideTime=0;
    this.maskEndCss={};
    this.maskStartCss={
        display:"none",
        width:"100%",
        position:"absolute",
        top:0,
        left:0,
        zIndex:1000,
        opacity:0
    };

    switch (animationType){
        case "full":
            this.showTime=300;
            this.hideTime=300;
            this.startCss={opacity:0,
                zIndex:1001};
            this.endCss={opacity:1};
            this.maskEndCss={opacity:0.8};
            break;
        case "center":


            this.showTime=300;
            this.hideTime=300;
            this.startCss={
                opacity:0,
                zIndex:1001,
                left:($(window).width()-ele.width())/2,
                top:($(window).height()-ele.height())/2,
            };
            this.endCss={opacity:1};
            this.maskEndCss={opacity:0.5};
            break;
        case "bottom":
            this.showTime=300;
            this.hideTime=300;
            this.startCss={
                top:$(window).height(),
                display:"block",
                zIndex:1001,
                height:ele.height()
            };
            this.endCss={
                top:$(window).height()-ele.height(),
                bottom:0
            };
            this.maskEndCss={opacity:0.5};
            break;
        case "right":
            this.showTime=300;
            this.hideTime=300;
            this.maskEndCss={opacity:0.5};
            this.startCss={
                right:-ele.width(),
                display:"block",
                zIndex:1001
            };
            this.endCss={
                 right:0
            };
            break;
        case "selectDialog":
            this.maskEndCss={opacity:0.01};
            this.maskEndCss={opacity:0.01};
            this.showTime=300;
            this.hideTime=300;
            this.startCss={
                opacity:0,
                zIndex:1001
            };
            this.endCss={opacity:1};
            break;
        default:
            throw new Error("动画类型错误")
            break;

    }
}

//添加同时弹出多个dialog 2016-6-12
function DialogManager(bgColor,selector,animationType){

    var _this=this;
    var _ele=$(selector);
    var _at;

    //如果已经存在dialog 设置新dialog的zindex并把之前的mask设置为透明，在hide的时候透明度恢复过去

    var _mask;

    //单击空白区域是否关闭
    this.clickMaskToHide=true;
    this.isShow=false;

    this.show = function(){
        _at=new AnimationTypeFatory(_ele,animationType);
        this.isShow=true;
        var _lastMask=$(".mask").last();
        if(_lastMask[0]!=null){
            _at.maskStartCss.zIndex= parseInt( _lastMask.css("zIndex"))+2;
            _at.startCss.zIndex=parseInt( _lastMask.css("zIndex"))+3;
            _lastMask.attr("backopacity", _lastMask.css("opacity"));
        }
        _mask = $('<div class="mask" style="background: ' + bgColor + '"></div>');
        $("body").append(_mask);

        _mask.bind("click", function () {
            if(_this.clickMaskToHide)
                 _this.hide();
        });
        _mask.css(_at.maskStartCss);

        _at.notify("show");
        _lastMask.animate({"opacity":0}, _at.showTime);
        _mask.height($(document).height());
        _mask.show();
        _ele.css({

            display:"block"

        });
        _ele.css(_at.startCss);
        _ele.animate(_at.endCss, _at.showTime);
        _mask.animate(_at.maskEndCss,_at.showTime);
    };

    this.on = function(type,callback){
        _at[type]=callback;

    };

    this.hide = function(){
        this.isShow=false;
        if(_mask.css("zIndex")>1000){
            var mm= $(".mask").eq($(".mask").length-2);
            mm.animate({"opacity":mm.attr("backopacity")}, _at.hideTime);

        }

            _mask.animate({"opacity": 0}, _at.hideTime,function(){
                _mask.remove();
            })
        _ele.animate(_at.startCss,_at.hideTime,function(){

            _at.notify("hide");
            _ele.css({
                zIndex:"inherit",
                display:"none"
            });
        });

    };

    //设置是否点击空白处关闭，必须在show之前设置
    this.setClickMaskToHide = function (isHide) {
        this.clickMaskToHide = isHide;
    };

    if(typeof arguments[arguments.length - 1] == "boolean"){
        this.clickMaskToHide = arguments[arguments.length - 1];
    }


}

//抖动动画效果
function JumpObj(elem, range, startFunc, endFunc) {
    var curMax = range = range || 6;
    startFunc = startFunc || function(){};
    endFunc = endFunc || function(){};
    var drct = 0;
    var step = 1;

    init();

    function init() { elem.style.position = 'relative';active() }
/*    function active() { elem.onmouseover = function(e) {if(!drct)jump()} }
    function deactive() { elem.onmouseover = null }*/

    function active() { if(!drct)jump(); }
    function deactive() { }

    function jump() {
        var t = parseInt(elem.style.top);
        if (!drct) motionStart();
        else {
            var nextTop = t - step * drct;
            if (nextTop >= -curMax && nextTop <= 0) elem.style.top = nextTop + 'px';
            else if(nextTop < -curMax) drct = -1;
            else {
                var nextMax = curMax / 2;
                if (nextMax < 1) {motionOver();return;}
                curMax = nextMax;
                drct = 1;
            }
        }
        setTimeout(function(){jump()}, 200 / (curMax+3) + drct * 3);
    }
    function motionStart() {
        startFunc.apply(this);
        elem.style.top='0';
        drct = 1;
    }
    function motionOver() {
        endFunc.apply(this);
        curMax = range;
        drct = 0;
        elem.style.top = '0';
    }

    this.jump = jump;
    this.active = active;
    this.deactive = deactive;
}