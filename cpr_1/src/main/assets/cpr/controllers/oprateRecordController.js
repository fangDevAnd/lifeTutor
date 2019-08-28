//@ sourceURL=oprateRecordController.js
function oprateRecordController($scope,$location,$timeout,netService,FullScreenShowService,scopeService) {

    var vm = $scope.vm = {};

    //history 为历史记录
    vm.module=$location.search()["module"];
    vm.currentIndex = 0;
    if(window.nobj.getLanguage()==0)
        vm.lang=new Chinese();
    else
        vm.lang=new English();

    $timeout(function(){
       $(".RModulBG").height($(window).height()-75-39);
        $(".RModulBG").css({
            "overflow":"auto",
            "background":"#f5f7f9"
        });
        vm.init();
    },200);
    //参数
    vm.query={};
    vm.query.id=$location.search()["id"];
//{"data":[{"records":[{"count":"0","name":"测试1"}],"type":1},{"records":[{"count":"5","name":"测试1"}],"type":2}],"type":1}
  // vm.data=JSON.parse('{"tables":[{"records":[{"count":"0","name":"清除异物"},{"count":"0","name":"脉搏检查"},{"count":"0","name":"急救呼叫"},{"count":"0","name":"呼吸检查"},{"count":"0","name":"意识判断"},{"count":"0","name":"CPR得分"},{"count":"否","name":"是否救活"},{"count":"0","name":"总分"}],"title":"成绩详情","type":2},{"records":[{"count":"0","name":"按压总数"},{"count":"0","name":"按压正确"},{"count":"0","name":"按压错误"},{"count":"0","name":"按压过大"},{"count":"0","name":"按压过小"},{"count":"0","name":"多按"},{"count":"0","name":"少按"},{"count":"0","name":"频率错误"},{"count":"0","name":"吹气总数"},{"count":"0","name":"吹气正确"},{"count":"0","name":"吹气错误"},{"count":"0","name":"吹气过大"},{"count":"0","name":"吹气过小"},{"count":"0","name":"多按"},{"count":"0","name":"少按"}],"title":"合计操作","type":1},{"pingyu":"","title":"评语","type":0}],"exam_date":"2016-08-05","exam_type":"实战模式","exam_data_long":1470379870868,"score":0}');
  //  console.info(vm.data);
    //初始化
    vm.init = function () {
 //   return;
        DBManager.getData("queryOprateRecord",JSON.stringify(vm.query), function (rdata) {


                vm.data = rdata;
               $scope.$digest();

        });
    };

    vm.onPlayBack=function(){
        $.showTip( vm.lang.zhunbeihuifangzhong);
        window.nobj.playBack(vm.data.id);
    }
    vm.save=function(){
        if(vm.data.stu_class==null||vm.data.stu_class==""){
            $.showTip(vm.lang.banjibunengweikong);
            return;
        }
        if(vm.data.stu_name==null||vm.data.stu_name==""){
            $.showTip(vm.lang.xingmingbunengweikong);
            return;
        }
        vm.module="history";
       // $.showTip("正在保存...");
        $timeout(function () {
            window.nobj.saveCpr(JSON.stringify(vm.data));
            $.showTip(vm.lang.baocunchenggong);
        },800)




    }
    vm.init();

    //打印成绩
    vm.onPrintRecord = function () {
        window.nobj.printCpr(JSON.stringify(vm.data));
    };
    //打印波形图
    vm.onPrintRecordPic = function () {
        window.nobj.printCprPic(JSON.stringify(vm.data));
    };
    //左划动
    vm.onSwipeLeft = function () {
        if((vm.currentIndex + 1) > vm.data.length - 1){
            return;
        }
        vm.currentIndex++;


    };

    //右划动
    vm.onSwipeRight = function () {
        if((vm.currentIndex - 1) < 0){
            return;
        }
        vm.currentIndex--;


    }
}

$(function () {
    regController("oprateRecord");
});