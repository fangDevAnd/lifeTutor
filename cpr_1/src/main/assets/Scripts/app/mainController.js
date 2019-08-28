
var fullScreenShow;
app.controller('mainController', function ($scope,$location, netService, $timeout,FullScreenShowService) {

    fullScreenShow = FullScreenShowService;
    $scope.menuIndex = 0;
    $scope.height = $(document).height();


    //0实验员手持端 ，1课程评分， 考试评分3
    if(Config.appType==0){
        $scope.menus =Config.adminMenus;
        //实验定时刷新待办事项

        netService.addOb(function(url){
            if(url.indexOf("ToDoQueryMsg")==-1){
                netService.post("LaboratoryEntity/ToDoQueryMsg","",function(data){
                    $scope.menus[0].num=data.resCount;
                    $scope.menus[1].num=data.modelCount+data.equipmentCount;
                });
            }
        });

    }
    if(Config.appType==1)
        $scope.menus =Config.sujectMenus;
    if(Config.appType==3)
        $scope.menus =Config.teacherMenus;
    $location.path($scope.menus[0].url).search({});
    $scope.jump = function (index) {
       $scope.menuIndex = index;
       $location.path($scope.menus[$scope.menuIndex].url).search({});
    }


    $scope.userInfo = function () {
        //跳转到user页
        $location.path('user/user');
    }
    
});

//android 返回按钮事件
function onBackEvent() {
    if (fullScreenShow.isShow)
        fullScreenShow.hide();
    else
        window.history.back();
}

