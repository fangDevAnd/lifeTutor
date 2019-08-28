app.service('LoadService', function () {

    this.hide=function() {
        app_load.hide();
    }

})

//2016.5.31加上 未单独独立出文件
app.service('scopeService', function() {
    return {
        safeApply: function ($scope, fn) {
            var phase = $scope.$root.$$phase;
            if (phase == '$apply' || phase == '$digest') {
                if (fn && typeof fn === 'function') {
                    fn();
                }
            } else {
                $scope.$apply(fn);
            }
        },
    };
});