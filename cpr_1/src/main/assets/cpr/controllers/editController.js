//@ sourceURL=editController.js
function editController($scope,$timeout,netService,FullScreenShowService,scopeService) {

    var vm = $scope.vm = {};
    vm.data={};
    vm.currentIndex=0;
    $timeout(function () {

        $("#edit").css({
            height:$(window).height(),
            overflow:"hidden"
        })

        var input_type=$("#inputChuiqi");
        $("#chuianshunxu").css({
            width:input_type.width(),
            height:input_type.height(),
            top:input_type.offset().top,
            left:input_type.offset().left
        });


        vm.init();
    },200);
    vm.langNumber=window.nobj.getLanguage();
    if( vm.langNumber==0)
        vm.lang=new Chinese();
    else{
        vm.lang=new English();

    }

    vm.data={};
    vm.data.anyahegelv=55;
    vm.data.anchuishunxu=vm.lang.xianchuihouan;
    vm.init=function(){
        //{"anchuishunxu":1,"anyacishu":30,"anyahegelv":60,"anyapinlvMax":130,"anyapinlvMin":100,"anyashenduMax":6,"anyashenduMin":5,"chuiqicishu":2,"chuiqihegelv":60,"chuiqiliangMax":1000,"chuiqiliangMin":500,"flowCPR":90,"flowHujiao":2,"flowHuxi":2,"flowMaibo":2,"flowYishi":2,"flowYiwu":2,"xunhuancishu":5}
        var json=window.nobj.getSetting();

      //  alert(json);
      vm.data= JSON.parse(json);
        if(vm.data.anchuishunxu==0)
            vm.anchuishunxu=vm.lang.xianchuihouan;
        else
            vm.anchuishunxu=vm.lang.xiananhouchui;
       $scope.$digest();
    }
    vm.save=function(){

        if(vm.currentIndex==2){
            if(vm.newPwd!=vm.confirmPwd){
                $.showTip(vm.lang.mimabuyizhi);
                return;
            }
            else {
                var msg = window.nobj.savePwd(vm.oldPwd, vm.newPwd);

                    $.showTip(msg);

            }
            return;
        }
        if((vm.data.flowYiwu+vm.data.flowMaibo+vm.data.flowHujiao+vm.data.flowHuxi+vm.data.flowYishi+vm.data.flowCPR)!=100){
            $.showTip(vm.lang.chaoguo100);
            return;
        }


        if(vm.anchuishunxu==vm.lang.xianchuihouan)
            vm.data.anchuishunxu=0;
        else
            vm.data.anchuishunxu=1;
        window.nobj.saveSetting(JSON.stringify( vm.data));
        $.showTip(vm.lang.baocunchenggong);
    }


}

$(function () {
    regController("edit");
});