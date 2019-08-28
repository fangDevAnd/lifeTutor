/**
 * Created by customer on 2016/6/17.
 */
app.
filter("modelState", function() {

    var filterfun = function(index){
       return  Config.modelStateItems[index];
    }
    return filterfun;
});