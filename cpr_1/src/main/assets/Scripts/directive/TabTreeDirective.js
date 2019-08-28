/**
 * 动态tabtree created by Mohammed.Tell.Yes
 */
app.directive('tabtree', ['netService',function(netService) {
    return {
        restrict: 'E',
        templateUrl : 'Scripts/directive_tpls/tabtree.html',
        replace:true,

        scope:{
            ngModel:"="
        },
        link : function (scope,element,attrs,controller) {

            scope.ngModel.setNetService(netService);
            scope.ngModel.setEle(element);
            scope.ngModel.init();

        }
    };
}]);

function TabTree(rootNodeName,rootNodeId,url){
    url=url==null?"Osce/QueryTypeMsg":url;
    var _this=this;
    var scroll;
    var netService;
    var tempSelectNode=null;
    var lastRNode=null;//最后一次确定使用的node
    var lastCNode=null;
    var ele;
    this.setNetService=function(net){
        netService=net;
    }
    this.setEle=function(ele){
        ele=$(ele);
        scroll=new ScorllManager($(".toolTypeColumn ",ele), {
            overflow:true
        });
    }
    this.rNode;
    this.cNode;
    this.selectNode;
    //外部调用
    this.onEnter=function(){

    }
    this.onCancel=function(){

    }


    this.enter=function(){
        if(tempSelectNode==null){
            $.showTip("未选中，退出请点击取消");
            return;
        }
        this.selectNode=tempSelectNode;

        lastRNode=  $.tool.deepClone(this.rNode);

        lastCNode= $.tool.deepClone(this.cNode);
        tempSelectNode=null;
        this.onEnter();
    }
    this.cancel=function(){
        this.onCancel();
    }
    this.loadData=function(id){
        var query=new Object();
        query.id=id;
        var pdata=JSON.stringify(query)
        netService.post(url,pdata, function (rdata) {

            if (rdata.type == "1") {

                _this.cNode.children=rdata.typies;

            } else {
                $.showTip("数据获取失败");
            }
        },null);
        scroll.reset();
    }
    this.titleClick=function(node){
        node.next=null;
        this.cNode=node;
        tempSelectNode=null;

    }
    this.itemClick=function(node) {
        if (!node.isLeaf){
            this.cNode.next = node;
            this.cNode = this.cNode.next;
            this.cNode.next=null;
            this.loadData(this.cNode.id);
            tempSelectNode=null;
        }
        else{
            for(var i =0;i<this.cNode.children.length;i++){
                this.cNode.children[i].selected=false;
            }
            tempSelectNode=node;
            node.selected=true;
        }


    }
    this.init=function(){
        this.rNode=getNode(rootNodeName,rootNodeId);
        this.cNode=this.rNode;
        this.loadData(this.rNode.id);
    }
    this.toList=function(){

        var res=new Array();
        var temp=this.rNode;
        while(temp!=null){
                res.push(temp);
                temp=temp.next;
        }

        return res;
    }
    this.show=function(){
        this.reset();
    }
    this.toString=function(){

        isBtnHide=true;
        if(this.selectNode==null)
              return"";
        else
              return this.selectNode.name;
    }
    this.reset=function(){
        if(lastRNode==null){
           this.init();
            return;
        }

        tempSelectNode=this.selectNode;

        this.rNode=$.tool.deepClone(lastRNode);
        this.cNode=$.tool.deepClone(lastCNode);


    }

    function getNode(name,id,next,children){

        var res=new Object();
        res.name=name;
        res.id=id;
        res.next=next;
        res.children=children;
        return res;
    }


}

