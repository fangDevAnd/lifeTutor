//@ sourceURL=oprateRecordController.js
var cprVM;
var cprScope;
function cprIndexController($scope,$location,netService,FullScreenShowService,scopeService) {


    var vm = $scope.vm = {};

    //初始化
    vm.init = function () {

        //绑定menu
        vm.menus = Config.cprMenus;
        vm.menus.forEach(function (menu) {
            menu.selected = false;
        });
        vm.selected_menu = Config.cprMenus[0];
        vm.selected_menu.selected = true;
       if(window.nobj.getLanguage()==0)
            vm.lang=new Chinese();
        else
            vm.lang=new English();

        vm.menus[0].title =vm.lang.lilunxuexi;
        vm.menus[0].description =vm.lang.lilunxuexi1;
        vm.menus[1].title =vm.lang.xunlianmoshi;
        vm.menus[1].description =vm.lang.xunlianmoshi;

        vm.menus[2].title =vm.lang.kaohemoshi;
        vm.menus[2].description =vm.lang.kaohemoshi;

        vm.menus[3].title =vm.lang.shizhanmoshi;
        vm.menus[3].description =vm.lang.shizhanmoshi;

        vm.menus[4].title =vm.lang.lishichengji;
        vm.menus[4].description =vm.lang.lishichengji;


    };
    vm.init();
    /*;*/
  //  if (Config.fakeData) {
    //    vm.init()
   // } else {
    //    NativeCaller.beginNativeCall("getModuleInfo", null, function (data) {
     //       for (var i = 0; i < data.length; i++) {
           //     Config.cprMenus[i].description = data[i];
     //       }


       //     vm.init();
       //     $scope.$digest();
       // });



     /**
     菜单选择
     */
    vm.onMenuSelected = function (menu) {
        if(vm.selected_menu.title==menu.title){
            vm.onStartModel();
        }
        vm.menus.forEach(function (menu) {
            menu.selected = false;
        });
        menu.selected = true;

        vm.selected_menu = menu;
    };


    //菜单进入
    vm.onStartModel = function () {

        if(vm.selected_menu.title== Config.cprMenus[1].title){

            window.nobj.goXunlianCpr();
            return;
        }
        if(vm.selected_menu.title== Config.cprMenus[2].title){

            window.nobj.goKaoHeCpr();
            return;
        }
        if(vm.selected_menu.title== Config.cprMenus[3].title){

            window.nobj.goShiZhanCpr();
            return;
        }

        $location.path(vm.selected_menu.url);
        //跳转页
        // FullScreenShowService.show(vm.selected_menu.url + ".html");

    }
}

/**
立即执行
*/
$(function () {
    regController("cprIndex");
});

