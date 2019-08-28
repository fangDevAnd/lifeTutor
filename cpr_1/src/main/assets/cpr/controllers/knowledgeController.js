//@ sourceURL=editController.js
function knowledgeController($scope,$timeout,netService,FullScreenShowService,scopeService) {

    var vm = $scope.vm = {};
   vm.basePath=window.nobj.getBaseFilePath()
   vm.list= JSON.parse( window.nobj.getFiles(vm.basePath));
 //  vm.list= JSON.parse('["新建文本文档.txt","ACLS10800高级急救技能训练系统---通讯协议1.10.doc"]');

    vm.data=new Array();
    for(var i=0;i<vm.list.length;i++){
        var path=vm.list[i];

        var type=path.substr(path.lastIndexOf(".")+1).toLowerCase();
        vm.data[i]={
            path:path,
            type:type,
        }
    }
    vm.openFile=function(file){
       window.nobj.openFile( vm.basePath+file.path);
    }

}

$(function () {
    regController("knowledge");
});