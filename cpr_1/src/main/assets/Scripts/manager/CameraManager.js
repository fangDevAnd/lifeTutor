/**
 * 本地摄像头及扫描二维码服务 created by Tellyes_worker on 2016/5/24.
 */
var CameraManager = (function(){

    var service = {};

    /**
     *  实验员资源分配扫码
     */

    //扫码返回回调
    service.handle = function (ids) {};

    //开启扫码界面
    //url：扫码检查接口的url,
    //count: 可扫码次数
    //_handler: 扫码完成回调
    service.startScanner = function (url,typeid,count,_handler) {
        this.handle = _handler;
        window.nobj.startScannerFromMainPage(url,typeid,count);
    };

    // 获取扫码返回字符串
    service.getScannerString = function () {
        var ids = window.nobj.getScannerCodeFromMainPage();
        this.handle(ids);
    };


    /**
     *  实验员资源回收扫码
     */

    //扫码返回回调
    service.rec_handle = function () {};

    //开启扫码界面
    //url：扫码检查接口的url,
    //count: 可扫码次数
    //_handler: 扫码完成回调
    service.startRecycleScanner = function (checkUrl,recUrl,count,_handler) {
        this.rec_handle = _handler;
        window.nobj.startRecScannerFromMainPage(checkUrl,recUrl,count);
    };

    // 获取扫码返回字符串
    service.recFinish = function () {
        this.rec_handle();
    };


    /**
     *  添加模型扫码
     * */

    //扫码返回回调
    service.add_model_handle = function () {};

    //开启扫码界面
    //_handler: 扫码完成回调
    service.startAddModelScanner = function (_handler) {
        this.add_model_handle = _handler;
        window.nobj.startAddModelScannerFromMainPage();
    };

    // 获取扫码返回字符串
    service.addModelFinish = function () {
        var code = window.nobj.getAddModelCodeFromMainPage();
        this.add_model_handle(code);
    };


    /**
     *  扫码搜索
     * */

    //扫码返回回调
    service.search_handle = function () {};

    //开启扫码界面
    //_handler: 扫码完成回调
    service.startSearchScanner = function (checkUrl,_handler) {
        this.search_handle = _handler;
        window.nobj.startSearchScannerFromMainPage(checkUrl);
    };

    // 获取扫码返回字符串
    service.searchFinish = function () {
        var id = window.nobj.getSearchIDFromMainPage();
        this.search_handle(id);
    };


    /**
     *  扫码绑定资源
     * */

    //扫码返回回调
    service.bind_handler = function () {};

    //开启扫码界面
    //_handler: 扫码完成回调
    service.startBindScanner = function (id,bindUrl,_handler) {
        this.bind_handler = _handler;
        window.nobj.startBindScannerFromMainPage(id,bindUrl);
    };

    // 获取扫码返回字符串
    service.bindFinish = function () {
        this.bind_handler();
    };


    /**
     *  头像拍照上传
     * */

    //拍照回调
    service.photo_handle = function(){};

    //开启拍照界面
    //_handler完成回调
    service.startTakePhoto = function (_handler){
        this.photo_handle = _handler;
        window.nobj.startPhotographFromMainPage();
        window.nobj.clearPicData();
    };

    //开启图片选择界面
    //_handler完成回调
    service.startPickPhoto = function (_handler) {
        this.photo_handle = _handler;
        window.nobj.startPickPhotoFromMainPage();
    };

    //获取图片base64字符串
    service.getBase64String = function () {
        var picString = window.nobj.getPhotoBase64FromMainPage();
        this.photo_handle(picString);
        window.nobj.clearPicData();
    };

    /**
     *  房间拍照上传
     * */
    service.startTakeRoomPhoto = function (_handler){
        this.photo_handle = _handler;
        window.nobj.startRoomPhotographFromMainPage();
        window.nobj.clearPicData();
    };

    return service;
})();
