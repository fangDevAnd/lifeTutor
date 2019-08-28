//@ sourceURL=oprateRecordController.js
var cprVM;
var cprScope;
function cprDataDetailController($scope,$timeout,netService,FullScreenShowService,scopeService) {

    var vm = $scope.vm = {};

    if(window.nobj.getLanguage()==0)
        vm.lang=new Chinese();
    else {
        vm.lang = new English();
        $(".ctitle2").css("fontSize",13);
        $(".ctitle").css("fontSize",13)
    }

    vm.flow = {
        yiwu:false,
        maibo:false,
        hujiao:false,
        huxi:false,
        yishi:false
    };
    $timeout(function(){
        $("#cprDataDetail").css({
            height:$(window).height(),
            overflow:"hidden"
        });
        $("#leftTb").width($(window).width()*0.25);
        $("#autoTb").hide();
        $("#autoTb").width(0);
        $("#rightTb").width($(window).width()*0.25);
        $("#rightAutoTb").width($(window).width()*0.5);

    },200);
    vm.showLeft=function(){
        $("#autoTb").show();
        $("#autoTb").animate({width:$(window).width()*0.5});
       // $.showTip("向左滑动即可恢复");

    }
    vm.hideLeft=function(){
        $("#autoTb").animate({width:0},function(){

            $("#autoTb").hide();
        });
    }
    cprVM = vm;
    cprScope = $scope;
    vm.cdata = {};
    vm.tdata = {};
    vm.cycle = 1;
    vm.showCpr = true;
    $timeout(function () {
        vm.changeHeight();

    }, 200);

    vm.changeHeight = function () {
        if (vm.showCpr) {
            $("tr").height($(window).height() / 4);
        }
        else {
            $("tr").height($(window).height() / 2);

        }

    }
}


$(function () {
    regController("cprDataDetail");
});

function setdata(cdata,tdata){

    cprVM.cdata=JSON.parse(cdata);
    cprVM.tdata=JSON.parse(tdata);
    cprScope.$digest();
}

function setCycle(cycle){
    cprVM.cycle=cycle+1;
    cprScope.$digest();
}
function setFlow(flow){
    cprVM.flow=JSON.parse(flow);
    cprScope.$digest();
}
function changeShow(){
    cprVM.showCpr=!cprVM.showCpr;
    cprVM.changeHeight();
    cprScope.$digest();
}
function reset(){
    cprVM.cdata={};
    cprVM.tdata={};
    cprVM.cycle=1;

    cprVM.showCpr=false;
    cprVM.flow = {
        yiwu:false,
        maibo:false,
        hujiao:false,
        huxi:false,
        yishi:false
    };
    cprScope.$digest();
}