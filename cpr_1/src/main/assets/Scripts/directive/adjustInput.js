/**
 * Created by customer on 2016/5/24.
 */
app.directive("adjustInput", function () {
    return {
        restrict: "EA",

        template: "<span class='jia'></span><input class='adjustInput borderColor fontMiddle' type='text' ng-model='model' /><span class='jian'></span>",
        scope: {
            model:'='
        },

        link: function (scope, element, attrs) {
            var maxModel=scope.model;

            var children= $(element).children();
            children.eq(0).bind ('click', function ($event) {

                $event.stopPropagation();
              //  if(parseInt(scope.model)<=0)
                 //   return;
                scope.model = parseInt(scope.model)- 1;
                scope.$apply();
            });
            children.eq(2).bind('click', function ($event) {
              //  if(parseInt(scope.model)>=maxModel)
               //     return;
                $event.stopPropagation();
                scope.model = parseInt(scope.model)+ 1;

                scope.$apply();
            })
        }
    };
});