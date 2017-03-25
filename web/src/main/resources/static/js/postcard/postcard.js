/**
 * Created by ZhongChongtao on 2017/3/21.
 */

function ooo() {
    var $myPic = $('#myPic');
    $myPic.cropper('setAspectRatio', 1);
    $myPic.cropper('replace', 'images/2.jpg');
}

var options = {
    viewMode: 0,
    dragMode: 'move',
    autoCropArea: 0.60,
    restore: false,
    guides: false,
    highlight: false,
    cropBoxMovable: false,
    cropBoxResizable: false,
    toggleDragModeOnDblclick: false,
    preview: '#myPreview',
    aspectRatio: 5,
    ready: function (a) {
        var $myPic = $('#myPic');
        var canvasData = $myPic.cropper('getCanvasData');
        var cropBoxData = $myPic.cropper('getCropBoxData');
        if (cropBoxData.width / cropBoxData.height < canvasData.width / canvasData.height) {
            $myPic.cropper('zoomTo', (cropBoxData.height / canvasData.naturalHeight));
        } else {
            $myPic.cropper('zoomTo', (cropBoxData.width / canvasData.naturalWidth));
        }
    }
};
var currentMouserPoint = undefined;
var isSubmitEnabled = false;
var currentPostCardInfo = {
    "postCardId": "402881e75ad23605015ad25472bf0003",
    "postCardFileId": "fb413367e2e29e2f05baff4489baac79",
    "type": "TYPE_A",
    "productWidth": 100.0,
    "productHeight": 145.0,
    "pictureWidth": 0.0,
    "pictureHeight": 100.0,
    "cropInfo": {
        left: 0,
        top: 0,
        width: 0,
        height: 0
    }
};
$(function () {
    var $myPic = $('#myPic');
    //设置好裁切对象
    $myPic.cropper(options);
    //相关事件
    $('#postcard')
    //鼠标移动事件
        .on('mousemove', function (a, b, c) {
            //第一次鼠标移动
            if (currentMouserPoint == undefined) {
                currentMouserPoint = {
                    x: a.screenX,
                    y: a.screenY
                };
                return;
            }
            //之后的鼠标移动变化量
            var mousePointDelta = {
                x: a.screenX - currentMouserPoint.x,
                y: a.screenY - currentMouserPoint.y
            };
            //保存鼠标位置
            currentMouserPoint = {
                x: a.screenX,
                y: a.screenY
            };
            // var $testInfo = $('#testInfo');
            var $pic = $('#myPic');
            var canvasData = $pic.cropper('getCanvasData');
            var cropBoxData = $pic.cropper('getCropBoxData');
            moveMyPicture({deltaX: mousePointDelta.x, deltaY: mousePointDelta.y});
            currentPostCardInfo.cropInfo = {
                left: (cropBoxData.left - canvasData.left) / canvasData.width,
                top: (cropBoxData.top - canvasData.top) / canvasData.height,
                width: cropBoxData.width / canvasData.width,
                height: cropBoxData.height / canvasData.height
            };
            // var testInfoText =
            //         '画布左侧: ' + canvasData.left + '<br/>' +
            //         '画布上侧: ' + canvasData.top + '<br/>' +
            //         '画布宽度: ' + canvasData.width + '<br/>' +
            //         '画布高度: ' + canvasData.height + '<br/><br/>' +
            //         '数据左侧: ' + ((cropBoxData.left - canvasData.left) / canvasData.width).toFixed(2) + '<br/>' +
            //         '数据上侧: ' + ((cropBoxData.top - canvasData.top) / canvasData.height).toFixed(2) + '<br/>' +
            //         '数据宽度: ' + ((cropBoxData.width / canvasData.width)).toFixed(2) + '<br/>' +
            //         '数据高度: ' + ((cropBoxData.height / canvasData.height )).toFixed(2) + '<br/><br/>'
            //
            //     ;
            // $testInfo
            //     .html(testInfoText);
        })
        //鼠标滚动事件
        .on('mousewheel', function () {
            var canvasData = $myPic.cropper('getCanvasData');
            var cropBoxData = $myPic.cropper('getCropBoxData');
            if ((canvasData.width > cropBoxData.width) && (canvasData.height > cropBoxData.height)) {
                //调整大小
                if (cropBoxData.width / cropBoxData.height <= canvasData.width / canvasData.height) {
                    $myPic.cropper('zoomTo', (cropBoxData.height / canvasData.naturalHeight));
                } else {
                    $myPic.cropper('zoomTo', (cropBoxData.width / canvasData.naturalWidth));
                }
            }
            moveMyPicture(
                {
                    deltaX: 0,
                    deltaY: 0
                }
            );
        })
        .on('click', function () {
            if (isSubmitEnabled) {
                isSubmitEnabled = false;
                console.log("正在提交");
                //提交逻辑
                $.ajax({
                    url: 'http://localhost:8080/postcard/submit',
                    dataType: "json",
                    contentType: "application/json",
                    type: 'post',
                    data: JSON.stringify(currentPostCardInfo),
                    success: function () {
                        console.log("执行成功");
                        //加载下一张逻辑
                        getNextPostCard();
                    },
                    error:function (a) {
                        console.log("发生错误");
                        console.log(a);
                    }
                });
            }
        });
    getNextPostCard();
    /** @namespace result.pictureHeight 图片高度 */
    /** @namespace result.pictureWidth 图片宽度 */
    /** @namespace result.postCardFileId  明信片图片ID*/
    function getNextPostCard() {
        console.log("正在执行下一个postCard");
        // 初始化init
        $.ajax({
            method: 'get',
            url: 'http://localhost:8080/postcard/next',
            success: function (result) {
                currentPostCardInfo = result;
                var $myPic = $('#myPic');
                $myPic.cropper('setAspectRatio', result.pictureWidth / result.pictureHeight);
                $myPic.cropper('replace', 'http://localhost:8089/file/' + result.postCardFileId);
                isSubmitEnabled = true;
            },
            error: function () {
                console.log("网络异常");
            }
        });
    }


    /**
     *
     * @param  a
     */
    function moveMyPicture(a) {
        var $pic = $('#myPic');
        var canvasData = $pic.cropper('getCanvasData');
        var cropBoxData = $pic.cropper('getCropBoxData');
        var targetCanvasData = {
            left: canvasData.left + a.deltaX,
            top: canvasData.top + a.deltaY,
            width: canvasData.width,
            height: canvasData.height
        };
        var tmpDelta;
        if (targetCanvasData.width > cropBoxData.width && targetCanvasData.height > cropBoxData.height) {
            // 太靠左侧的情况
            tmpDelta = (cropBoxData.left + cropBoxData.width) - (targetCanvasData.left + targetCanvasData.width);//当图片太位于左侧的话，计算出来需要位移的距离
            if (tmpDelta > 0) {
                a.deltaX += tmpDelta;
                // $pic.cropper('move', tmpDelta, 0);
            }
            //太靠右侧的情况
            tmpDelta = -(cropBoxData.left ) + (targetCanvasData.left);//当图片太位于左侧的话，计算出来需要位移的距离
            if (tmpDelta > 0) {
                a.deltaX -= tmpDelta;
                // $pic.cropper('move', -tmpDelta, 0);
            }
            // 太靠上侧的情况
            tmpDelta = (cropBoxData.top + cropBoxData.height) - (targetCanvasData.top + targetCanvasData.height);//当图片太位于左侧的话，计算出来需要位移的距离
            if (tmpDelta > 0) {
                a.deltaY += tmpDelta;
                // $pic.cropper('move', 0, tmpDelta);
            }
            //太靠下侧的情况
            tmpDelta = -(cropBoxData.top ) + (targetCanvasData.top);//当图片太位于左侧的话，计算出来需要位移的距离
            if (tmpDelta > 0) {
                a.deltaY -= tmpDelta;
                // $pic.cropper('move', 0, -tmpDelta);
            }

        } else if (targetCanvasData.width > cropBoxData.width) {// 裁切框的宽度小于图片框的宽度，裁切框的高度大于图片框的高度
            // 太靠左侧的情况
            tmpDelta = (cropBoxData.left + cropBoxData.width) - (targetCanvasData.left + targetCanvasData.width);//当图片太位于左侧的话，计算出来需要位移的距离
            if (tmpDelta > 0) {
                a.deltaX += tmpDelta;
                // $pic.cropper('move', tmpDelta, 0);
            }
            //太靠右侧的情况
            tmpDelta = -(cropBoxData.left ) + (targetCanvasData.left);//当图片太位于左侧的话，计算出来需要位移的距离
            if (tmpDelta > 0) {
                a.deltaX -= tmpDelta;
                // $pic.cropper('move', -tmpDelta, 0);
            }
            var topWhiteHeight = (cropBoxData.height - targetCanvasData.height) / 2;
            a.deltaY = cropBoxData.top - canvasData.top + topWhiteHeight;
        } else if (targetCanvasData.height > cropBoxData.height) {
            var leftWhiteWidth = (cropBoxData.width - targetCanvasData.width) / 2;
            a.deltaX = cropBoxData.left - canvasData.left + leftWhiteWidth;
            tmpDelta = (cropBoxData.top + cropBoxData.height) - (targetCanvasData.top + targetCanvasData.height);//当图片太位于左侧的话，计算出来需要位移的距离
            if (tmpDelta > 0) {
                a.deltaY += tmpDelta;
                // $pic.cropper('move', 0, tmpDelta);
            }
            //太靠下侧的情况
            tmpDelta = -(cropBoxData.top ) + (targetCanvasData.top);//当图片太位于左侧的话，计算出来需要位移的距离
            if (tmpDelta > 0) {
                a.deltaY -= tmpDelta;
                // $pic.cropper('move', 0, -tmpDelta);
            }
        } else {
            //如果是一个正方形样式的
            if (cropBoxData.width / cropBoxData.height > canvasData.width / canvasData.height) {
                $myPic.cropper('zoomTo', (cropBoxData.height / canvasData.naturalHeight));
            } else {
                $myPic.cropper('zoomTo', (cropBoxData.width / canvasData.naturalWidth));
            }
            leftWhiteWidth = (cropBoxData.width - targetCanvasData.width) / 2;
            a.deltaX = cropBoxData.left - canvasData.left + leftWhiteWidth;
            topWhiteHeight = (cropBoxData.height - targetCanvasData.height) / 2;
            a.deltaY = cropBoxData.top - canvasData.top + topWhiteHeight;
        }
        //移动指定的位置
        $pic.cropper('move', a.deltaX, a.deltaY);
    }

});



