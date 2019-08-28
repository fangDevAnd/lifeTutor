//@ sourceURL=scoreRecordController.js
function scoreRecordController($scope,$location,netService,FullScreenShowService,scopeService,$timeout,$interval) {

    var vm = $scope.vm = {};
    vm.query = ParamFactory.getParamObjByName("queryScoreRecord");
    var lm = new LoadingManager(Config.loadPicPath, "#fff");
    //滚动控制
    var sm;
    if(window.nobj.getLanguage()==0)
        vm.lang=new Chinese();
    else
        vm.lang=new English();
    $timeout(function () {

        $("#scoreList").height($(window).height() - $(".SBlock").height() - $(".SlistLine").height());
        $("#scoreList").css("overflow","auto");
        var input_type=$("#input_type");
        $("#exam_type").css({
            width:input_type.width(),
            height:input_type.height(),
            top:input_type.offset().top,
            left:input_type.offset().left
        });
        vm.onSearch();
    },200);

    vm.removing=false;



    //查询条件JSON
   // vm.score_records=[];
  //  vm.score_records[0]={stu_name:"dd"}
//{"data":[{"tables":[{"records":[{"count":"0","name":"清除异物"},{"count":"0","name":"脉搏检查"},{"count":"0","name":"急救呼叫"},{"count":"0","name":"呼吸检查"},{"count":"0","name":"意识判断"},{"count":"0","name":"CPR得分"},{"count":"否","name":"是否救活"},{"count":"0","name":"总分"}],"title":"成绩详情","type":2},{"records":[{"count":"0","name":"按压总数"},{"count":"0","name":"按压正确"},{"count":"0","name":"按压错误"},{"count":"0","name":"按压过大"},{"count":"0","name":"按压过小"},{"count":"0","name":"多按"},{"count":"0","name":"少按"},{"count":"0","name":"频率错误"},{"count":"0","name":"吹气总数"},{"count":"0","name":"吹气正确"},{"count":"0","name":"吹气错误"},{"count":"0","name":"吹气过大"},{"count":"0","name":"吹气过小"},{"count":"0","name":"多按"},{"count":"0","name":"少按"}],"title":"合计操作","type":1},{"pingyu":"","title":"评语","type":0}],"exam_date":"2016-08-05","exam_type":"考核模式","id":"9da0c6d3-29f5-46ea-8511-f0fcb5d28537","stu_name":"lyc","stu_class":"啊班级","exam_data_long":1470380054354,"score":0},{"tables":[{"records":[{"count":"0","name":"清除异物"},{"count":"0","name":"脉搏检查"},{"count":"0","name":"急救呼叫"},{"count":"0","name":"呼吸检查"},{"count":"0","name":"意识判断"},{"count":"0","name":"CPR得分"},{"count":"否","name":"是否救活"},{"count":"0","name":"总分"}],"title":"成绩详情","type":2},{"records":[{"count":"0","name":"按压正确率"},{"count":"152","name":"按压总数"},{"count":"3","name":"按压正确"},{"count":"149","name":"按压错误"},{"count":"0","name":"按压过大"},{"count":"19","name":"按压过小"},{"count":"0","name":"多按"},{"count":"113","name":"少按"},{"count":"33","name":"频率错误"},{"count":"0","name":"吹气正确率"},{"count":"10","name":"吹气总数"},{"count":"0","name":"吹气正确"},{"count":"10","name":"吹气错误"},{"count":"8","name":"吹气过大"},{"count":"2","name":"吹气过小"},{"count":"1","name":"多按"},{"count":"0","name":"少按"}],"title":"合计操作","type":1},{"records":[{"count":"0","name":"按压正确率"},{"count":"30","name":"按压总数"},{"count":"2","name":"按压正确"},{"count":"28","name":"按压错误"},{"count":"0","name":"按压过大"},{"count":"0","name":"按压过小"},{"count":"0","name":"多按"},{"count":"17","name":"少按"},{"count":"11","name":"频率错误"},{"count":"0","name":"吹气正确率"},{"count":"2","name":"吹气总数"},{"count":"0","name":"吹气正确"},{"count":"2","name":"吹气错误"},{"count":"0","name":"吹气过大"},{"count":"1","name":"吹气过小"},{"count":"0","name":"多按"},{"count":"1","name":"少按"}],"title":"循环1","type":1},{"records":[{"count":"0","name":"按压正确率"},{"count":"30","name":"按压总数"},{"count":"1","name":"按压正确"},{"count":"29","name":"按压错误"},{"count":"0","name":"按压过大"},{"count":"6","name":"按压过小"},{"count":"0","name":"多按"},{"count":"19","name":"少按"},{"count":"7","name":"频率错误"},{"count":"0","name":"吹气正确率"},{"count":"2","name":"吹气总数"},{"count":"0","name":"吹气正确"},{"count":"2","name":"吹气错误"},{"count":"1","name":"吹气过大"},{"count":"1","name":"吹气过小"},{"count":"0","name":"多按"},{"count":"0","name":"少按"}],"title":"循环2","type":1},{"records":[{"count":"0","name":"按压正确率"},{"count":"30","name":"按压总数"},{"count":"0","name":"按压正确"},{"count":"30","name":"按压错误"},{"count":"0","name":"按压过大"},{"count":"5","name":"按压过小"},{"count":"0","name":"多按"},{"count":"25","name":"少按"},{"count":"5","name":"频率错误"},{"count":"0","name":"吹气正确率"},{"count":"3","name":"吹气总数"},{"count":"0","name":"吹气正确"},{"count":"3","name":"吹气错误"},{"count":"3","name":"吹气过大"},{"count":"0","name":"吹气过小"},{"count":"1","name":"多按"},{"count":"0","name":"少按"}],"title":"循环3","type":1},{"records":[{"count":"0","name":"按压正确率"},{"count":"30","name":"按压总数"},{"count":"0","name":"按压正确"},{"count":"30","name":"按压错误"},{"count":"0","name":"按压过大"},{"count":"4","name":"按压过小"},{"count":"0","name":"多按"},{"count":"26","name":"少按"},{"count":"4","name":"频率错误"},{"count":"0","name":"吹气正确率"},{"count":"2","name":"吹气总数"},{"count":"0","name":"吹气正确"},{"count":"2","name":"吹气错误"},{"count":"2","name":"吹气过大"},{"count":"0","name":"吹气过小"},{"count":"0","name":"多按"},{"count":"0","name":"少按"}],"title":"循环4","type":1},{"records":[{"count":"0","name":"按压正确率"},{"count":"30","name":"按压总数"},{"count":"0","name":"按压正确"},{"count":"30","name":"按压错误"},{"count":"0","name":"按压过大"},{"count":"4","name":"按压过小"},{"count":"0","name":"多按"},{"count":"26","name":"少按"},{"count":"4","name":"频率错误"},{"count":"0","name":"吹气正确率"},{"count":"2","name":"吹气总数"},{"count":"0","name":"吹气正确"},{"count":"2","name":"吹气错误"},{"count":"2","name":"吹气过大"},{"count":"0","name":"吹气过小"},{"count":"0","name":"多按"},{"count":"0","name":"少按"}],"title":"循环5","type":1},{"pingyu":"吞吞吐吐","title":"评语","type":0}],"exam_date":"2016-08-05","exam_type":"实战模式","id":"0e228a9a-5e2a-4087-813b-39db579dc215","stu_name":"lxc","stu_class":"班级","exam_data_long":1470381133502,"score":0}],"type":1}
 // vm.score_records=JSON.parse('{"data":[{"tables":[{"records":[{"count":"0","name":"清除异物"},{"count":"0","name":"脉搏检查"},{"count":"0","name":"急救呼叫"},{"count":"0","name":"呼吸检查"},{"count":"0","name":"意识判断"},{"count":"0","name":"CPR得分"},{"count":"否","name":"是否救活"},{"count":"0","name":"总分"}],"title":"成绩详情","type":2},{"records":[{"count":"0","name":"按压总数"},{"count":"0","name":"按压正确"},{"count":"0","name":"按压错误"},{"count":"0","name":"按压过大"},{"count":"0","name":"按压过小"},{"count":"0","name":"多按"},{"count":"0","name":"少按"},{"count":"0","name":"频率错误"},{"count":"0","name":"吹气总数"},{"count":"0","name":"吹气正确"},{"count":"0","name":"吹气错误"},{"count":"0","name":"吹气过大"},{"count":"0","name":"吹气过小"},{"count":"0","name":"多按"},{"count":"0","name":"少按"}],"title":"合计操作","type":1},{"pingyu":"","title":"评语","type":0}],"exam_date":"2016-08-05","exam_type":"考核模式","id":"9da0c6d3-29f5-46ea-8511-f0fcb5d28537","stu_name":"lyc","stu_class":"啊班级","exam_data_long":1470380054354,"score":0},{"tables":[{"records":[{"count":"0","name":"清除异物"},{"count":"0","name":"脉搏检查"},{"count":"0","name":"急救呼叫"},{"count":"0","name":"呼吸检查"},{"count":"0","name":"意识判断"},{"count":"0","name":"CPR得分"},{"count":"否","name":"是否救活"},{"count":"0","name":"总分"}],"title":"成绩详情","type":2},{"records":[{"count":"0","name":"按压正确率"},{"count":"152","name":"按压总数"},{"count":"3","name":"按压正确"},{"count":"149","name":"按压错误"},{"count":"0","name":"按压过大"},{"count":"19","name":"按压过小"},{"count":"0","name":"多按"},{"count":"113","name":"少按"},{"count":"33","name":"频率错误"},{"count":"0","name":"吹气正确率"},{"count":"10","name":"吹气总数"},{"count":"0","name":"吹气正确"},{"count":"10","name":"吹气错误"},{"count":"8","name":"吹气过大"},{"count":"2","name":"吹气过小"},{"count":"1","name":"多按"},{"count":"0","name":"少按"}],"title":"合计操作","type":1},{"records":[{"count":"0","name":"按压正确率"},{"count":"30","name":"按压总数"},{"count":"2","name":"按压正确"},{"count":"28","name":"按压错误"},{"count":"0","name":"按压过大"},{"count":"0","name":"按压过小"},{"count":"0","name":"多按"},{"count":"17","name":"少按"},{"count":"11","name":"频率错误"},{"count":"0","name":"吹气正确率"},{"count":"2","name":"吹气总数"},{"count":"0","name":"吹气正确"},{"count":"2","name":"吹气错误"},{"count":"0","name":"吹气过大"},{"count":"1","name":"吹气过小"},{"count":"0","name":"多按"},{"count":"1","name":"少按"}],"title":"循环1","type":1},{"records":[{"count":"0","name":"按压正确率"},{"count":"30","name":"按压总数"},{"count":"1","name":"按压正确"},{"count":"29","name":"按压错误"},{"count":"0","name":"按压过大"},{"count":"6","name":"按压过小"},{"count":"0","name":"多按"},{"count":"19","name":"少按"},{"count":"7","name":"频率错误"},{"count":"0","name":"吹气正确率"},{"count":"2","name":"吹气总数"},{"count":"0","name":"吹气正确"},{"count":"2","name":"吹气错误"},{"count":"1","name":"吹气过大"},{"count":"1","name":"吹气过小"},{"count":"0","name":"多按"},{"count":"0","name":"少按"}],"title":"循环2","type":1},{"records":[{"count":"0","name":"按压正确率"},{"count":"30","name":"按压总数"},{"count":"0","name":"按压正确"},{"count":"30","name":"按压错误"},{"count":"0","name":"按压过大"},{"count":"5","name":"按压过小"},{"count":"0","name":"多按"},{"count":"25","name":"少按"},{"count":"5","name":"频率错误"},{"count":"0","name":"吹气正确率"},{"count":"3","name":"吹气总数"},{"count":"0","name":"吹气正确"},{"count":"3","name":"吹气错误"},{"count":"3","name":"吹气过大"},{"count":"0","name":"吹气过小"},{"count":"1","name":"多按"},{"count":"0","name":"少按"}],"title":"循环3","type":1},{"records":[{"count":"0","name":"按压正确率"},{"count":"30","name":"按压总数"},{"count":"0","name":"按压正确"},{"count":"30","name":"按压错误"},{"count":"0","name":"按压过大"},{"count":"4","name":"按压过小"},{"count":"0","name":"多按"},{"count":"26","name":"少按"},{"count":"4","name":"频率错误"},{"count":"0","name":"吹气正确率"},{"count":"2","name":"吹气总数"},{"count":"0","name":"吹气正确"},{"count":"2","name":"吹气错误"},{"count":"2","name":"吹气过大"},{"count":"0","name":"吹气过小"},{"count":"0","name":"多按"},{"count":"0","name":"少按"}],"title":"循环4","type":1},{"records":[{"count":"0","name":"按压正确率"},{"count":"30","name":"按压总数"},{"count":"0","name":"按压正确"},{"count":"30","name":"按压错误"},{"count":"0","name":"按压过大"},{"count":"4","name":"按压过小"},{"count":"0","name":"多按"},{"count":"26","name":"少按"},{"count":"4","name":"频率错误"},{"count":"0","name":"吹气正确率"},{"count":"2","name":"吹气总数"},{"count":"0","name":"吹气正确"},{"count":"2","name":"吹气错误"},{"count":"2","name":"吹气过大"},{"count":"0","name":"吹气过小"},{"count":"0","name":"多按"},{"count":"0","name":"少按"}],"title":"循环5","type":1},{"pingyu":"吞吞吐吐","title":"评语","type":0}],"exam_date":"2016-08-05","exam_type":"实战模式","id":"0e228a9a-5e2a-4087-813b-39db579dc215","stu_name":"lxc","stu_class":"班级","exam_data_long":1470381133502,"score":0}],"type":1}').data;
    //查询数据
    vm.onSearch = function () {
//return;
        lm.show("#scoreList");
        DBManager.getData("queryScoreRecord",JSON.stringify(vm.query), function (rdata) {
            if (rdata.type == "1") {
                vm.score_records = rdata.data;
                $scope.$digest();
                lm.hide();
            } else {
                $.showTip(rdata.msg);
            }
        });

    };

    vm.onSelectType=function(){

        $("#examType").click();

    }

    //开始日期选择
    vm.onDateClick = function (e) {
        NativeCaller.beginNativeCall("showDatePicker",null,function(data){

                if(e=="begin"){
                    vm.query.begin_date=data;
                }
                else{
                    vm.query.end_date=data;
                }
            $scope.$digest();


        });
    };
    var startRemoving=function(){
        $.showTip(vm.lang.jinrushanchumoshi);
        vm.removing=true;
        BackManager.setOnlyOb(function(){

            breakRemoving();
            $scope.$digest();
        });
    }
    var breakRemoving=function(){
        BackManager.clearOnlyOb();
        $.showTip(vm.lang.tuichushanchumoshi);
        vm.removing=false;
        return;
    }
    vm.gotoRemove=function(id){
        if(vm.removing){
            breakRemoving();
        }else
            startRemoving();


    }
    //记录行点击
    vm.onRecordClick = function (record) {
     ///   alert(1);
        if(vm.removing){
            vm.score_records.remove(record);
           window.nobj.removeScore(record.id);
          //  vm.onSearch();

            $.showTip(vm.lang.shanchuchenggong);
           return;
        }
        $location.path("cpr/oprateRecord").search({
            id:record.id,
            module:"history"
        });

    }
}
Array.prototype.remove = function(val) {
    var index = this.indexOf(val);
    if (index > -1) {
        this.splice(index, 1);
    }
};
$(function () {
    regController("scoreRecord");
});