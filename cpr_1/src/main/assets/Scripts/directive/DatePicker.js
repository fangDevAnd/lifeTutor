/**
 * Created by Mohammed.Tell.Yes on 2016/6/3.
 */
app.directive('datepicker',function() {
    return {
        restrict: 'E',
        templateUrl : 'Scripts/directive_tpls/datepicker.html',
        replace:true,
        scope:{
            date:"="
        },
        link : function (scope,element,attrs,controller) {
            scope.date.setEle(element);
            scope.date.setScope(scope);
        }
    };
});


//日期控件 可注册到jquery或者独立继承到angular模块中
function DatePicker(year,month,day){
    var _scope;
    this.setScope=function(scope){
        _scope=scope;
    };
    var _ele;
    this.setEle=function(ele){
        _ele=$(ele);
    };

    this.tempYear;
    this.tempMonth;
    this.tempDay;
    //属性
    this.year =null;
    this.month = null;
    this.day =null;

    this.yearArray = [];
    this.monthArray = [];
    this.dayArray = [];
    this.isOpen = false;
    var _this=this;
    var smYear;
    var smMonth;
    var smDay;
    //接口
    this.onCancel=function(){};
    this.onEnter=function(){};
    this.cancel = function () {
        resetTemp();
        this.onCancel(_this);
    };
    this.enter = function () {

        _this.year={value: _this.tempYear};
        _this.month={value: _this.tempMonth};
        _this.day={value: _this.tempDay};
        this.onEnter(_this);
    };
     var resetTemp=function(){
         if(_this.day!=null&&_this.year!=null&&_this.month!=null) {
             _this.tempDay = _this.day.value;
             _this.tempMonth = _this.month.value;
             _this.tempYear = _this.year.value;
         }
         else {
             _this.tempYear = year;
             _this.tempMonth = month;
             _this.tempDay = day;
         }
     };

    //内部方法
    this.getDayValue = function (year,month) {
        var count = 0; //有多少天
        var leap = 0;
        if( (year % 4 == 0 && year % 100 != 0) || year % 400 == 0){
            leap = 1;
        }

        switch (_this.tempMonth){
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                count = 31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                count = 30;
                break;
            case 2:
                count = 28 + leap;
                break;
            default:
                break;
        }

        return count;
    };

    this.getPreviousYear = function () {

        if(this.yearArray.length == 0){
            return;
        }
        var val = this.yearArray[0].value;

        for(var i = 1; i <= 10; i++ ){


            var year = { value : val - i, selected : false};
            this.yearArray.splice(0, 0, year);
        }

    };

    this.getNextYear = function () {

        if(this.yearArray.length == 0){
            return;
        }
        var val = this.yearArray[this.yearArray.length - 1].value;
        for(var i = 1; i <= 10; i++ ){
            var year = { value : val + i, selected : false};
            this.yearArray.push(year);
        }

    };

    this.bindDays = function () {

        this.dayArray = [];

        var count = this.getDayValue(year,month);

        for (var z = 1; z <= count; z++ ){
            var day = { value : z, selected : false};

            this.dayArray.push(day);
        }

    };

    this.YearSelected = function (year) {
        _this.tempYear=year.value;
        //选中年份重绑定日期
        this.bindDays();

    };

    this.MonthSelected = function (month) {
        _this.tempMonth=month.value;
        this.bindDays();


    };

    this.DaySelected = function (day) {

       _this.tempDay=day.value;
    };

    this.getFullDateString = function () {
        return _this.year.value + "-" + _this.month.value + "-" + _this.day.value;
    };


    this.init = function () {


        //获取当前日期
        var dt = new Date();
       if(year==null)
           year=dt.getFullYear();

        if(month==null)
            month=dt.getMonth();

        if(day==null)
            day=dt.getDay();


       _this.tempYear=year;
       _this.tempMonth=month;
       _this.tempDay=day;
        //年份 赋值前后10年初始值
        for(var i = 10; i >= 1; i-- ){
            this.yearArray.push({ value :year - i, selected : false});
        }



        for(var j = 0; j <= 10; j++ ){
            this.yearArray.push( { value : year + j, selected : false});
        }

        //月份 固定12月

        for (var k = 1; k <= 12; k++ ){

            this.monthArray.push({ value : k, selected : false});
        }

        //日期
        this.bindDays();


    };

      var initSm=function(){

          smDay = new ScorllManager($("#day",_ele), {
              overflow: true
          });
          if((_this.tempDay>3))
          smDay.scrollY((_this.tempDay-3)*-80);

           smYear = new ScorllManager($("#year",_ele),{
              overflow: true,
              scrollEnd: function (overflow) {
                  if(overflow > 150){

                      _this.getPreviousYear();
                      _scope.$digest();
                      smYear.reset();
                  }
                  if(overflow < -150){

                      _this.getNextYear();
                      _scope.$digest();
                      smYear.reset();
                  }
              }
          });

          smYear.scrollY(80*-10);
           smMonth = new ScorllManager($("#month",_ele), {
              overflow: true
          });
          if((_this.tempMonth>3))
             smMonth.scrollY((_this.tempMonth-3)*-80);

      };
    this.show=function(){
        if(smYear==null)
            initSm();
        resetTemp();

    };
    this.init();
    this.toString=function(){

        if(_this.year==null)
             return "";
        else
            return _this.year.value+" 年 "+_this.month.value+" 月 "+_this.day.value+" 日 ";
    };
    
    this.toDateString = function (spliter) {
        if(_this.year==null)
            return "";
        else
            return _this.year.value+ spliter +_this.month.value+ spliter +_this.day.value ;
    };
    
    this.getDateObj = function () {

        if(_this.year == null)
            return null;
        else {
            var date = new Date();
            date.setYear(_this.year.value);
            date.setMonth(_this.month.value - 1);
            date.setDate(_this.day.value);
            return date;
        }
    };

    this.setDateValue = function (year,month,day) {
        _this.tempYear.value = year;
        _this.tempMonth.value = month;
        _this.tempDay.value = day;
    };

    this.setDateObj = function (dateObj) {
        _this.tempYear.value = dateObj.getFullYear();
        _this.tempMonth.value = dateObj.getMinutes() + 1;
        _this.tempDay.value = dateObj.getDate();
    };

    this.tag = {};

}