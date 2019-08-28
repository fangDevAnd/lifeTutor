// 模块的$controllerProvider,用来动态注册controller  
var controllerProvider = null;
var app;

//初始化加载模块
var app_load = new LoadingManager(Config.loadPicPath, "#fff");

    //angular模块定义
    app = angular.module('app', ['ngRoute','ngTouch'], function ($controllerProvider) {
        controllerProvider = $controllerProvider;
    });

    //配置路由
    app.config(function ($routeProvider,$httpProvider) {

        $routeProvider.
        when('/:controller/:action', {
            templateUrl: function (rp) { app_load.show(); return rp.controller + "/" + rp.action + '.html' }
        }).
        when('/:action', {
            templateUrl: function (rp) { app_load.show(); return rp.action + '.html' }
        });

        $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded; charset=UTF-8';
    });

    app.directive('onFinishRenderFilters', function ($timeout) {
        return {
            restrict: 'A',
            link: function (scope, element, attr) {
                    $timeout(function () {
                        app_load.hide();
                    });
            }
        };
    });

    //调用angular组件注册controller
    function regController(elementId) {

        var controllerName= elementId+"Controller";
        controllerProvider.register(controllerName, window[controllerName]);
        $("#" + elementId).attr("ng-controller", controllerName);
        $("#" + elementId).attr("on-finish-render-filters", "");

    }

    //注册全屏controller
    function regControllerForFull(elementId) {

        var controllerName= elementId+"Controller";
        controllerProvider.register(controllerName, window[controllerName]);
        $("#" + elementId).attr("ng-controller", controllerName);
        $("#" + elementId).attr("on-finish-render-filters", "");
        $("#" + elementId).injector().invoke(function ($compile, $rootScope) {
            $compile($("#" + elementId))($rootScope);
        });
    }

    app.run(['$rootScope', function ($rootScope) {

        $.tool= {};

        $.showTip = function (tip) {
            $.tool.showTip(tip);
        };

        //扩展数组方法
        Array.prototype.remove = function(dx) {
            if(isNaN(dx)|| dx > this.length){ return false; }
            for(var i=0,n=0;i<this.length;i++)
            {
                if(this[i]!= this[dx])
                {
                    this[n++]=this[i]
                }
            }
            this.length-=1
        };

        //移动端接口是否存在
        $.tool.isHasNativeObject = function () {
            if(typeof(window.nobj) != "undefined" && window.nobj != null) {
                return true;
            }else{ return false; }

        };

        //提示信息
        $.tool.showTip = function (tip) {
            if(typeof(window.nobj) != "undefined" && window.nobj != null) {
                window.nobj.showTip(tip);
            }else{ alert(tip);}
        };

        //深克隆
        $.tool.deepClone=function(source){
           var json=JSON.stringify(source);
           return  JSON.parse(json);
        };

        //判断移动端接口是否存在
        $.tool.exitSystem = function () {
            if(typeof(window.nobj) != "undefined" && window.nobj != null) {
                window.nobj.exitWebViewFromMainPage();
            }else{ alert("运行在非移动端！");}
        };

        //获取Base64图片
        $.tool.getBase64Image = function (img) {
            //img.crossOrigin = "Anonymous";
            var canvas = document.createElement("canvas");
            canvas.width = img.width;
            canvas.height = img.height;

            var ctx = canvas.getContext("2d");
            ctx.drawImage(img, 0, 0, img.width, img.height);

            var dataURL = canvas.toDataURL("image/png");
            return dataURL.replace("data:image/png;base64,", "");
        };

        // 将标准日期格式字符串转化为Date类型
        $.formatDate = function(dateString) {
            return new Date(dateString);
        };

        // 将标准日期格式字符串输出成通用日期格式
        $.formatDateString = function (dateString) {
            var d = new Date(dateString);
            return d.getFullYear() + "." + (d.getMonth() + 1) + "." + d.getDate() + " " + d.getHours() + ":" + d.getMinutes() + ":" + d.getSeconds();
        };
        
        $.formatDatePartString = function (dateObj,spliter) {
            return dateObj.getFullYear() + spliter + (dateObj.getMonth() + 1) + spliter + dateObj.getDate();
        };

        $.formatTimePartString = function (dateObj,spliter) {
            return dateObj.getHours() + spliter + dateObj.getMinutes() + spliter + dateObj.getSeconds();
        };

        //验证电话格式
        $.tool.validatePhoneNumber = function (phone) {
            var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
            return myreg.test(phone);
        };

        //验证邮箱格式
        $.tool.validateEmail = function (email) {
            var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
            return myreg.test(email);
        };

        //获取启动APP类型
        $.tool.getAppType = function () {
            return window.nobj.getAppType();
        };

        //获取服务器时间（登录时）
        $.tool.getServerTime = function () {
            if($.tool.isHasNativeObject()){
                return window.nobj.getServerTime();
            }else{
                return null;
            }
        };

    }]);
