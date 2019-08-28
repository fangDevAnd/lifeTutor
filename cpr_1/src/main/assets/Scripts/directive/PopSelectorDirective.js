/**
 * 动态tabtree created by Mohammed.Tell.Yes
 */
app.directive('popselector',function() {
    return {
        restrict: 'E',
        templateUrl : 'Scripts/directive_tpls/popselector.html',
        replace: true,
        scope:{
            selector:"="
        },
        link : function (scope,element,attrs,controller) {

        }
    };
});

