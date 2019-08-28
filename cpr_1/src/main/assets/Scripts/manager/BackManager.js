/**
 * Created by Administrator on 2016/9/4.
 */
function onBackEvent(){
    if(BackManager.onlyCallback!=null){
        BackManager.onlyCallback();
        return;
    }
  for(var x in BackManager.obs)
     BackManager.obs[x]();

}
var BackManager = (function(){

    var unique;
    this.onlyCallback;
    function Construct(){

        this.obs=new Array();
        this.addOb=function(callback){
            this.obs.push(callback);
        }
        //独占返回  不用一定要清空
        this.setOnlyOb=function (callback) {
            this.onlyCallback=callback;
        }
        this.clearOnlyOb=function(){
            this.onlyCallback=null;
        }

    }



    unique = new Construct();
    return unique;

})();
