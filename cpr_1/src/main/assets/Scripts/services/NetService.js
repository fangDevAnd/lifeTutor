/*网络服务 created by Mohammed.Tell.Yes*/

//netService 基础网络访问NetService(可能转化成native的调用方式，只提供网络请求服务)



//最后一次请求的成功回调函数
var lastSCb;
//最后一次请求的失败回调函数
var lastFCb;
app.service('netService',['$http','$rootScope', function ($http,$rootScope) {
    var obList= [];
    var _this=this;
    var service = {};
    var proxy="Osce/proxy";
    var   par="osce2/index.php/home/";
    $.sCb=function(){
        var obj= $.parseJSON(window.nobj.getAjaxStr());
        $rootScope.$apply(function(){
            lastSCb(obj);
        });
    };
    $.fCb=function(){
        lastFCb();
    };

    //post数据后会调用该函数
    service.addOb=function(callback){
      obList.push(callback);
  };

  service.baseUrl = "http://192.168.7.100:80/";
    if(Config.fakeData)
         service.baseUrl = "http://192.168.100.46:9002/";

    function webDebugHandleURL(url){
        url=url.replace(par,"").replace("CommonEntity","Osce");
        url=url.replace("LaboratoryEntity","Osce");
        return url;
    }
    function webDebugHandleData(data,url){

        data="url="+url+"&json="+data;
        return data;
    }


    service.post = function (url,data,success,error) {
        for(var i=0;i< obList.length;i++){
            obList[i](url);
        }
        url=service.baseUrl+par+url;
        if(Config.fakeData){
            url= webDebugHandleURL(url);
        }
        lastSCb=success;
        lastFCb=error;
        if(!Config.nativeRequest){
            //下面两行确认使用代理，还是不使用
            data=webDebugHandleData(data,url);
            url="http://192.168.100.46:9002/"+proxy;

            $http.post(url,data).success(function (data) {
                if(typeof(success) != "undefined" && success != null) success(data);


            }).error(function (err) {
                if(typeof(error)!= "undefined" && error != null) {
                    error();
                }else {
                    $.showTip("网络访问失败");
                }
            });
        }

        else{
            window.nobj.AjaxReturnCallback(url,data," $.sCb","$.fCb");
        }

    };
    return service;
}]);

//netServiceExt 基础网络访问通道扩展(主要在扩展实现判断权限，日志记录，访问控制等)
app.factory('netServiceExt',['netService', function(ns) {

    var netServiceExt = function() {
        this.url = "";
        this.busy = false;
        this.data = {};
        this.success = function (data) {}
        this.error = function () {}
    };

    netServiceExt.prototype = ns;

    netServiceExt.prototype.postData = function() {

        if (this.busy) return;
        this.busy = true;
        var _this = this;

        this.post(this.url,this.data, function (data) {
            _this.success(data); _this.busy = false;
        }, function () {
            _this.error(); _this.busy = false;
        });

    };

    return netServiceExt;
}]);



