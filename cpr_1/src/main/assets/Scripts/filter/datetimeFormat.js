/**
 * Created by customer on 2016/6/17.
 */
app.
filter("datetimeFormat", function() {

    var filterfun = function(dt, fmt) {
        var datetime=new Date(dt);
        fmt= fmt||"yyyy/MM/dd hh:mm:ss";
        var o = {
            "M+" : datetime.getMonth()+1,                 //月份
            "d+" : datetime.getDate(),                    //日
            "h+" : datetime.getHours(),                   //小时
            "m+" : datetime.getMinutes(),                 //分
            "s+" : datetime.getSeconds(),                 //秒
            "q+" : Math.floor((datetime.getMonth()+3)/3), //季度
            "S"  : datetime.getMilliseconds()             //毫秒
        };
    //    alert(datetime);
        if(/(y+)/.test(fmt))
            fmt=fmt.replace(RegExp.$1, (datetime.getFullYear()+"").substr(4 - RegExp.$1.length));
        for(var k in o)
            if(new RegExp("("+ k +")").test(fmt))
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        return fmt;

    };
    return filterfun;
});