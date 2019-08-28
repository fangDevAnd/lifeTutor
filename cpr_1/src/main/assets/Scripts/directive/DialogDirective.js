/**
 * popWindow created by Mohammed.Tell.Yes
 */
app.directive('dialog', function () {
    return {
        restrict: 'E',
        template : '<div  style="display: none; position:absolute;top: 0;left: 0;z-index:1000;background:#ccc;opacity:0.8"><div ng-transclude></div></div>',
        transclude: true,
        scope:{
            title : '@'
        },
        link: function (scope,element,attrs,controller) {

        }
    };
})

app.directive('popwindow', function () {
    return {
        restrict: 'E',
        templateUrl : 'tpls/sub/popwindow.html',
        replace: true,
        transclude: true,
        scope:{
            title : '@',
            subtitle : '@',
            okclick : '&',
            cancelclick : '&'
        }
    };
});


/*app.factory('popWin',function() {

    var popWin = function() {
        this.LoadingStyleDisplay = true;
        this.OkOrCancelStyleDisplay = false;
        this.style = 1;
        this.title = "";
        this.sub_title = "";
        this.onOKClick = function () {}
        this.onCancelClick = function () {}
    };

    popWin.prototype.setStyle = function (styleIndex) {

        switch(styleIndex)
        {
            case 1:
                this.LoadingStyleDisplay = true;
                this.OkOrCancelStyleDisplay = false;
                this.style = styleIndex;
                break;
            case 2:
                this.OkOrCancelStyleDisplay = true;
                this.LoadingStyleDisplay = false;
                this.style = styleIndex;
                break;
            default:
                break;
        }
    }

    return popWin;
});*/

