/**
 * native interface caller cpr
 */
var NativeCaller = (function(){

    var service = {};

    service.handler = function () {};

    /*
    * 调用native 异步通知形式
    * */
    service.beginNativeCall = function (method,param,_handler) {
        this.handler = _handler;
        if(param==null)
            window.nobj[method]()
        else
          window.nobj[method](param);
    };

    service.endCallBack = function (method) {
        var result = window.nobj[method]();
        if(this.handler != null) this.handler(JSON.parse(result));
    };
    //返回值为字符串
    service.endStIngCallBack = function (method) {
        var result = window.nobj[method]();
        if(this.handler != null) this.handler(result);
    };

    /**
     * 调用native 同步返回形式
     * */
    service.nativeCall = function (method,param) {
        return window.nobj[method](param);
    };

    return service;
    
})();

/**
 * parameter factory cpr 上行实体工厂
 */
var ParamFactory = (function () {

    var obj = {};

    //queryScoreRecord 查询考试记录
    var query_score_record = {
        begin_date : "",
        end_date : "",
        stu_name : "",
        stu_class : "",
        exam_type: ""  // 0：实战 1：考核 2：训练
    };

    //queryOprateRecord 查询操作记录
    var query_oprate_record = {
        score_record_id : ""
    };

    //userLogin 用户登录
    var user_login = {
        password : ""
    };

    //queryArguments 查询参数配置
    var query_arguments = {
        password : ""
    };

    //changePassword 更改密码
    var change_password = {
        password : "",
        new_password : ""
    };

    //queryFlowArguments 查询评估流程配置
    var query_flow_arguments = {
        password : ""
    };

    //saveOprateRecord 保存操作记录
    var save_oprate_record = {
        stu_name : "",
        stu_class : "",
        groups : []
    };
    
    this.getParamObjByName = function (_name) {

        switch (_name){
            case "queryScoreRecord":
                obj = query_score_record;
                break;
            case "queryOprateRecord":
                obj = query_oprate_record;
                break;
            case "userLogin":
                obj = user_login;
                break;
            case "queryArguments":
                obj = query_arguments;
                break;
            case "changePassword":
                obj = change_password;
                break;
            case "queryFlowArguments":
                obj = query_flow_arguments;
                break;
            case "saveOprateRecord":
                obj = save_oprate_record;
                break;
            default:
                break;
        }

        return obj;
    };

    return this;
})();

/**
 * DBManager cpr 下行返回实体

 里面建立了一堆的对象实体
 */
var DBManager = (function () {

    var isFakeData = Config.fakeData;

    var count = Config.fakeDataCount;

    //历史记录实体模型
    function ScoreRecord(){
        this.id = "";
        this.stu_name = "李super";
        this.stu_class = "甲班";
        this.exam_date = $.formatDatePartString(new Date(),"/");
        this.exam_type = 0;
        this.score = 0;
    }

    //操作记录实体模型
    function OprateRecordGroup(){
        this.title = "测试操作名";
        this.records = [];
    }

    function OprateRecord(){
        this.name = "操作1";
        this.count = 0;
    }

    //参数配置实体模型
    function Argument(){
        this.name = "操作1";
        this.type = 0;
        this.value = 0;
        this.bvalue = 0;
        this.evalue = 100;
        this.unit = "%";
        this.oprate = ["xcha","xahc"];
    }

    //评估流程配置实体模型
    function FlowArgument(){
        this.name = "";
        this.value = "";
    }

    /*
    * para1 = methodName, para2 = params; para3 = handler;
    * */
    this.getData = function () {

        var resultArray = [];

        var _name = arguments[0];
        var _params = arguments[1];
        var _handler = arguments[2];

        if(isFakeData){

            var i; var j;

            var rdata = {
                type:"1",
                msg:""
            };

            switch (_name){
                case "queryScoreRecord":
                    for (i = 0; i < count; i++ ){
                        resultArray.push(new ScoreRecord());
                    }
                    rdata.score_records = resultArray;

                    break;
                case "queryOprateRecord":

                    rdata.title = "详情";
                    rdata.stu_name = "实验三班";
                    rdata.stu_class = "李super";

                    for (i = 0; i < 6; i++ ){
                        var group = new OprateRecordGroup();
                        for(j = 0; j < count; j++){
                            var temp1 = new OprateRecord();
                            temp1.count = i + j;
                            group.records.push(temp1);
                        }
                        resultArray.push(group);
                    }
                    rdata.groups = resultArray;

                    break;
                case "userLogin":

                    rdata.msg = "123456";

                    break;

                case "queryArguments":

                    for(i = 0; i < count; i++){
                        var temp2 = new Argument();
                        if(0 < 3){ temp2.type = i;}
                        resultArray.push(temp2);
                    }

                    rdata.argu_list = resultArray;

                    break;
                case "changePassword":

                    rdata.msg = "123456";

                    break;

                case "queryFlowArguments":

                    for(i = 0; i < count; i++){
                        resultArray.push(new FlowArgument());
                    }

                    rdata.flow_argu_list = resultArray;

                    break;

                case "saveArguments":

                    break;
                case "saveFlowArguments":

                    break;
                case "saveScoreRecord":

                    break;
                default:
                    break;
            }

            _handler(rdata);

        }else{
            NativeCaller.beginNativeCall(_name,_params,_handler);
        }

    };

    return this;

})();
