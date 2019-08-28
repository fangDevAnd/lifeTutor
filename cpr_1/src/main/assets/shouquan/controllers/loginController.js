/**
 * Created by customer on 2016/11/30.
 */
function loginController($scope,$timeout,$http,$location,scopeService) {
    var vm = $scope.vm = {};
    vm.loginData={};
    var loginUrl=Config.serverBasePath+"Home/Login"
    vm.login=function(){
        $http.post(loginUrl,"json="+JSON.stringify( vm.loginData)).success(function(data){
            if(data.type)
                 $location.path("shouquan/index");
            else
                alert(data.msg);
        });
    };
}
$(function () {
    regController("login");
});