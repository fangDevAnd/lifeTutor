/* indexController 2016/7/29 系统首页*/
app.controller('indexController', function ($scope,$location, netService, $timeout,FullScreenShowService) {

    vm = $scope.vm = {};

    //初始化
    vm.init = function () {

        //绑定menu
        vm.menus = Config.cprMenus;
        vm.selected_menu = Config.cprMenus[0];
        vm.selected_menu.selected = true;
    };

    vm.init();
    
    vm.onMenuSelected = function (menu) {
        vm.menus.forEach(function (menu) {
            menu.selected = false;
        });
        menu.selected = true;

        vm.selected_menu = menu;
    };


    //菜单进入
    vm.onStartModel = function () {
alert(1);
       $location.path(vm.selected_menu.url);
        //跳转页
       // FullScreenShowService.show(vm.selected_menu.url + ".html");

    }

});

//android 返回按钮事件
function onBackEvent() {
    if (FullScreenShowService.isShow)
        FullScreenShowService.hide();
    else
        window.history.back();
}

