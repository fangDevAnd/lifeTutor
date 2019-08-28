/**
 * Created by customer on 2016/6/17.
 */
app.
filter("operType", function() {

    var filterfun = function(index){
       return  Config.operTypeItems[index];
    }
    return filterfun;
});