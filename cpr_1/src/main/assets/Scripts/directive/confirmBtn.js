app.directive("confirmBtn", function () {
    return {
        restrict: "EA",
        transclude: true,
        template: "<span class='cancel_gray' ng-transclude></span>",
        compile:function(element, attrs){
            attrs.dClick=attrs.ngClick;
            attrs.ngClick="";
            return{
                post:function (scope, element, attrs,ngModel) {

                    element.bind("click",function(){
                        if(!$(element.find(".cancel_gray")).hasClass("cancel_red")){
                            $(element.find(".cancel_gray")).addClass("cancel_red");
                            var rock=new RockManager(element.find(".cancel_gray"),"leftRight");
                            setTimeout(function(){
                                $(element.find(".cancel_gray")).removeClass("cancel_red");
                            },2000);
                            rock.start(250);
                               return;
                        }
                        scope.$apply(function (){
                           scope.$eval(attrs.dClick);
                        });
                    });


                    //  scope.$apply(attrs["aclick"]);
                }

            }
        },

    };
});