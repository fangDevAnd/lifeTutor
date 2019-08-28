///支持 scollEnd事件,是否支持溢出（overflow）
function ScorllManager(ele,options) {
    var _this=this;
    var _ele=$(ele);
    var _child=$(ele).children().eq(0);
    var _isStart=false;
    var _startY=0;
    var _currentY=0;
    var _overflowY=0;//溢出了多少
    var _damping=1;//溢出阻尼
    var _options={
        overflow:true,
        scrollEnd:function(overflow){
          //  alert(overflow);
        },
        scroll:function(y){
            //  alert(overflow);
        }
    }
    this.scrollY=function(y){
        _child.transition({y: y,easing: 'snap'});
        _currentY=y;
    };
    var isPC=function()
    {
        var userAgentInfo = navigator.userAgent;
        var Agents = new Array("Android", "iPhone", "SymbianOS", "Windows Phone", "iPad", "iPod");
        var flag = true;
        for (var v = 0; v < Agents.length; v++) {
            if (userAgentInfo.indexOf(Agents[v]) > 0) { flag = false; break; }
        }
        return flag;
    }
    _options=$.extend(_options,options);
   // _ele.height( $(window).height()-_ele.offset().top);
    document.addEventListener('mousemove', function (e) { e.preventDefault(); }, false);
    document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
    //视窗高度
    this.vHeight= _ele.height();
    //内容高度
    this.cHeight=_ele[0].scrollHeight;
    alert(_ele.css("overflow"));
    this.reset=function(){
       // _ele.css("overflow","scorll");
        //视窗高度
        this.vHeight= _ele.height();
        //内容高度
        this.cHeight=_ele[0].scrollHeight;

     //   alert(_ele[0].scrollHeight);
       // _ele.css("overflow","hidden");

    }
    _ele.css("overflow","hidden");
    this.on=function(type,callback){
        _options[type]=callback;

    }
    var mouseDown=function(e){

        _isStart=true;
        _startY= e.pageY;
        $(document).bind("mouseup", mouseUp);
        $(document).bind("mousemove", mouseMove);

    };
    var getY=function (e){
        if(isPC())
            return e.pageY;
        else
            return e.touches[0].pageY;
    }
    var touchStart=function(e){

        _isStart=true;
        _startY= e.touches[0].pageY;
        document.addEventListener("touchend",mouseUp,false);
        document.addEventListener('touchmove',touchMove,false);
    };
    var mouseUp=function(){
        _damping=1;
        _isStart=false;
        if(_currentY>0&&_options.overflow) {
            _child.transition({y: '0',easing: 'snap',});
            _currentY=0;
        }
        if(_overflowY<0&&_options.overflow) {
            _child.transition({y: _this.vHeight - _this.cHeight,easing: 'snap',});
            _currentY= _this.vHeight - _this.cHeight;

        }
        _options.scrollEnd(_overflowY);
        _overflowY=0;
        unregEndEvent();

    };
    var touchMove=function(e){
        var cy=getY(e);
         if(Math.abs( e.touches[0].pageY-_startY)<1)
            return;
        //  e.preventDefault();
        if(_currentY>0){
            _overflowY=_currentY;
            _damping=Math.abs(_overflowY/50);//增加阻尼
        }
        else {
            console.info(_this.vHeight-_this.cHeight);
            if(_currentY<(_this.vHeight-_this.cHeight  ))
            _overflowY = _currentY + (_this.cHeight - _this.vHeight);
            else
            _overflowY=0;
        }

        if(_isStart){
            //禁止溢出时退出
            if(_currentY>0&&!_options.overflow){
                return;
            }
            if(_overflowY<0){
                if(!_options.overflow){
                    return;
                }
                else {
                    _damping=Math.abs(_overflowY/50);//增加阻尼
                }
            }

            _damping=_damping<1?1:_damping;
            _currentY=_currentY+(cy-_startY)/_damping;
            _startY= cy;
           // _child.transition({y:_currentY,easing: 'snap',});
            _child[0].style.transform="translateY("+_currentY+"px)";
            _options.scroll(cy);
        }
    };
    var mouseMove=function(e){
        var cy=getY(e);
        if(Math.abs( e.pageY-_startY)<1)
            return;
        e.preventDefault();
        if(_currentY>0){
            _overflowY=_currentY;
            _damping=Math.abs(_overflowY/50);//增加阻尼
        }
        else {
            if (_currentY < (_this.vHeight - _this.cHeight  ))
                _overflowY = _currentY + (_this.cHeight - _this.vHeight);
            else
                _overflowY = 0;
        }

        if(_isStart){
            //禁止溢出时退出
            if(_currentY>0&&!_options.overflow){

                _currentY=0;
                return;
            }

            if(_overflowY<-10){
                if(!_options.overflow){

                    _currentY=-(_this.cHeight-_this.vHeight);
                    return;
                }
                else {
                    _damping=Math.abs(_overflowY/50);//增加阻尼
                }
            }

            _damping=_damping<1?1:_damping;

            _currentY=_currentY+(cy-_startY)/_damping;
            _startY=cy;
            _child[0].style.transform="translateY("+_currentY+"px)";
        }
    };



    var regEndEvent=function(){
        if(isPC()) {
            _ele.bind("mousedown",mouseDown);
        }else {

            _ele[0].addEventListener("touchstart", touchStart);
        }
    }
    var unregEndEvent=function(){

        if(isPC()) {
            $(document).unbind("mousemove");
            $(document).unbind("mouseup");
        }
        else{
            document.removeEventListener("touchend",mouseUp);
            document.removeEventListener('touchmove',touchMove);
        }



    }

    regEndEvent();

}
