/**
 * Created by Tellyes_worker on 2016/6/8.
 */
app.directive('autograph',function() {
    return {
        restrict: 'E',
        templateUrl: 'Scripts/directive_tpls/autograph.html',
        replace: true,
        scope: {
            model: "="
        },
        link: function (scope, element, attrs, controller) {

            scope.canvas = $(element).find("canvas")[0];
            var signaturePad = new SignaturePad(scope.canvas);

            //清空签名版
            scope.model.clear = function () {
                signaturePad.clear();
            };

            //获取签名数据
            scope.model.getData = function () {
                if(signaturePad.isEmpty()){
                    return "";
                }
                var Pic = scope.canvas.toDataURL("image/png");
                Pic = Pic.replace(/^data:image\/(png|jpg);base64,/, "");
                return Pic;
            };

            //当前签名宽度
            scope.model.penWidth = 1;
            scope.model.widthStyle = { width : 0 };

            scope.$watch('model.penWidth',function(newValue,oldValue, scope){
                signaturePad.minWidth = parseFloat(newValue)  - 0.5;
                signaturePad.maxWidth = parseFloat(newValue)  + 1.5;

                scope.model.widthStyle = { width : 22.5 * scope.model.penWidth - 23};
                //console.log("penWidth:"+newValue + "min:" + signaturePad.minWidth + "max:" + signaturePad.maxWidth);
            });


        }
    }
});

/*
app.directive('autograph',function() {
    return {
        restrict: 'E',
        templateUrl : 'Scripts/directive_tpls/autograph.html',
        replace:true,
        scope:{},
        link : function (scope,element,attrs,controller) {

            scope.IsPressed = false;
            scope.canvas = {};
            scope.context = {};

            scope.init = function (){

                this.canvas = $(element).find("canvas")[0];
                this.context = this.canvas.getContext("2d");

                //双缓冲对象
                var canvasBuffer = document.createElement("canvas");
                canvasBuffer.width = this.canvas.width;
                canvasBuffer.height = this.canvas.height;

                var contextBuffer = canvasBuffer.getContext("2d");
                contextBuffer.clearRect(0, 0, canvasBuffer.width, canvasBuffer.height);

                //开始
                function begin (evt) {
                    evt.preventDefault();
                    contextBuffer.beginPath();
                    var p = pos(evt);
                    contextBuffer.moveTo(p.x, p.y);
                    scope.IsPressed = true;
                }

                //移动
                function move (evt) {
                    evt.preventDefault();
                    if (scope.IsPressed)
                    {
                        var p = pos(evt);

                        contextBuffer.save();
                        contextBuffer.lineTo(p.x, p.y);
                        contextBuffer.lineWidth =$('#selWidth').val();
                        contextBuffer.strokeStyle = $('#selColor').val();
                        contextBuffer.shadowColor = $('#selColor').val();
                        contextBuffer.shadowBlur = 1;
                        contextBuffer.stroke();
                        contextBuffer.restore();

                        scope.context.drawImage(canvasBuffer, 0, 0);


                    }
                }

                //结束
                function end (evt) {
                    evt.preventDefault();
                    scope.IsPressed = false;
                }

                //获取位置
                function pos(event){
                    var x,y;
                    if(isTouch(event)){
                        x = event.touches[0].pageX - $(element).find("canvas").offset().left;
                        y = event.touches[0].pageY - $(element).find("canvas").offset().top;
                    }else{
                        x = event.pageX - $(element).find("canvas").offset().left;
                        y = event.pageY - $(element).find("canvas").offset().top;
                    }
                    return {x:x,y:y};
                }

                function isTouch(event)
                {
                    var type = event.type;
                    if(type.indexOf('touch')>=0){
                        return true;
                    }else{
                        return false;
                    }
                }

                this.canvas.addEventListener('mousedown', begin, false);
                this.canvas.addEventListener('mousemove', move, false);
                this.canvas.addEventListener('mouseup', end, false);

                this.canvas.addEventListener('touchstart',begin,false);
                this.canvas.addEventListener('touchmove',move,false);
                this.canvas.addEventListener('touchend',end,false)

            }

            scope.init();

            scope.clearArea = function (){
/!*                this.context.setTransform(1, 0, 0, 1, 0, 0);
                this.context.clearRect(0, 0, this.context.canvas.width, this.context.canvas.height);*!/
                alert(scope.getImageDataBase64());

            }

            // Generate the image data
            scope.getImageDataBase64 = function () {
                var Pic = this.canvas.toDataURL("image/png");
                Pic = Pic.replace(/^data:image\/(png|jpg);base64,/, "");
                return Pic;
            }
        }
    };
});
*/
