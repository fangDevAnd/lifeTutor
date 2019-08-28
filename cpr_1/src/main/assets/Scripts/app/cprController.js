
var fullScreenShow;
app.controller('cprController', function ($scope,$location, netService, $timeout,FullScreenShowService) {

    BackManager.addOb(function(){

        if (fullScreenShow!=null&&fullScreenShow.isShow)
            fullScreenShow.hide();
        else
            window.history.back();
    });
});


