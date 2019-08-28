/**
 * Created by customer on 2016/6/22.
 */

function RockStyleFatory(ele,style){


    //单击空白区域是否关闭
    this.signTime=50;
    this.startCss={};
    this.endCss={};
    this.defaultCss={
        position:ele.css("position"),
        left:ele.css("left"),
        top:ele.css("top"),
    };


    switch (style){
        case "leftRight":
            this.startCss={
                position:"relative",
                left:-5,
                top:0,
            };
            this.endCss={
                left:5
            };
            break;
        default:
            throw new Error("动画类型错误")
            break;

    }
}
function RockManager(selector,style){

    var _ele=$(selector);
    var _at=new RockStyleFatory(_ele,style);
    var _time;
    var _takeTime=0;
    this.start=function(time){
        _takeTime=0;
        _time=time;
       _ele.css(_at.startCss);
       rock(false);

    }
    function rock(isback){
        _ele.animate(isback?_at.startCss:_at.endCss,_at.signTime,function(){
            _takeTime+=_at.signTime;
            if(_takeTime<_time)
                rock(!isback);
            else
                _ele.css(_at.defaultCss);
        });

    }
}