/**
 * Created by Administrator on 2016/9/4.
 */
app.directive('touchHold',function() {
    return function($scope, element, attrs){
     //   ele.bind('mouseenter', function(){

    //    })

        $(element).on({
         touchstart : function(e){
         //      mousedown : function(e){
                timeOutEvent = setTimeout(function(){

                    $scope.$apply(attrs.touchHold);

                },500);
             //   e.preventDefault();
            },
            touchmove: function(){
                if(timeOutEvent!=null)
                   clearTimeout(timeOutEvent);

            },
          touchend: function(){
           // mouseup : function(e){
                if(timeOutEvent!=null)
                clearTimeout(timeOutEvent);

              //  return false;
            }
        })
    }


});