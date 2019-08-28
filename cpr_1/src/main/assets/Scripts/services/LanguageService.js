/*语言服务 created by Mohammed.Tell.Yes*/
app.service('LanguageService', function() {

    //Map数据结构
    function Map() {
        /** 存放键的数组(遍历用到) */
        this.keys = new Array();
        /** 存放数据 */
        this.data = new Object();

        /**
         * 放入一个键值对
         * @param {String} key
         * @param {Object} value
         */
        this.set = function(key, value) {
            if(this.data[key] == null){
                this.keys.push(key);
            }
            this.data[key] = value;
        };

        /**
         * 获取某键对应的值
         * @param {String} key
         * @return {Object} value
         */
        this.get = function(key) {
            return this.data[key];
        };

        /**
         * 删除一个键值对
         * @param {String} key
         */
        this.remove = function(key) {
            this.keys.remove(key);
            this.data[key] = null;
        };

        /**
         * 遍历Map,执行处理函数
         *
         * @param {Function} 回调函数 function(key,value,index){..}
         */
        this.each = function(fn){
            if(typeof fn != 'function'){
                return;
            }
            var len = this.keys.length;
            for(var i=0;i<len;i++){
                var k = this.keys[i];
                fn(k,this.data[k],i);
            }
        };

        /**
         * 获取键值数组(类似Java的entrySet())
         * @return 键值对象{key,value}的数组
         */
        this.entrys = function() {
            var len = this.keys.length;
            var entrys = new Array(len);
            for (var i = 0; i < len; i++) {
                entrys[i] = {
                    key : this.keys[i],
                    value : this.data[i]
                };
            }
            return entrys;
        };

        /**
         * 判断Map是否为空
         */
        this.isEmpty = function() {
            return this.keys.length == 0;
        };

        /**
         * 获取键值对数量
         */
        this.size = function(){
            return this.keys.length;
        };

    }

    var service = {};

    service.lang = {
        name : "zh_cn",
        mapping : new Map()
    };

    service.languages = [];
    service.languages.push(service.lang);

    //添加一项新语言
    service.add = function (lang) {
        //判断是否有重复key,如果有重复key就覆盖

        if(service.languages.length > 0) {
            var flag = false;

            for (var i = 0; i < service.languages.length; i++) {
                if (service.languages[i].name == lang.name) {
                    service.languages[i] = lang;
                    flag = true;
                }
            }

            if (flag == false) {
                service.languages.push(lang);
            }
        }else{ service.languages.push(lang); }

    }

    //输出当前所有的语言keys
    service.printKeys = function () {
        return service.lang.mapping.forEach(function (value, key, map) {
            console.log("m[" + key + "] = " + value);
        });
    }

    //核心方法
    service.render = function (key) {
        return service.lang.mapping.get(key);
    }

    //加载现有语言
    service.load = function (name) {
        for (var i = 0; i < service.languages.length; i++) {
            if (service.languages[i].name == name) {
                service.lang = service.languages[i];
            }
        }
    }

    //初始化加载默认语言包
    service.init = function () {
        //配置中文语言包
        var zh_cn_lang = {
            name : "zh_cn",
            mapping : new Map()
        };

        zh_cn_lang.mapping.set("resource_alloc","资源分配");
        zh_cn_lang.mapping.set("resource_manage","资源管理");
        zh_cn_lang.mapping.set("resource_recycle","资源回收");
        zh_cn_lang.mapping.set("data_load_fail","加载数据失败");
        zh_cn_lang.mapping.set("input_message","请输入关键字");
        zh_cn_lang.mapping.set("user_info","用户信息");
        zh_cn_lang.mapping.set("resource_search","资源搜索");
        zh_cn_lang.mapping.set("resource_log","资源日志");


        //配置英文语言包
        var en_lang = {
            name : "en",
            mapping : new Map()
        };

        en_lang.mapping.set("resource_alloc","Resource Allocation");
        en_lang.mapping.set("resource_manage","Resource Manage");
        en_lang.mapping.set("resource_recycle","Resource Recycle");
        en_lang.mapping.set("data_load_fail","Data loading failed");
        en_lang.mapping.set("input_message","Please input keyword");
        en_lang.mapping.set("user_info","About User");
        en_lang.mapping.set("resource_search","Resource Search");
        en_lang.mapping.set("resource_log","Resource Log");

        //添加语音包
        service.add(zh_cn_lang);
        service.add(en_lang);

        //选定语言包
        service.load("en");
    }

    service.init();

    return service;
});