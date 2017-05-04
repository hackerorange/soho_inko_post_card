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
    // autoCropArea: 0.60,
    restore: false,
    strict: false,
    guides: true,
    highlight: true,
    cropBoxMovable: false,
    cropBoxResizable: false,
    toggleDragModeOnDblclick: false,
    preview: '#myPreview',
    modal:true,
    // aspectRatio: 5,
    zoomOnWheel: false,
    ready: function () {

        var $myPic = $('#myPic');
        var canvasData = $myPic.cropper('getCanvasData');
        var cropBoxData = $myPic.cropper('getCropBoxData');
        var containerData = $myPic.cropper('getContainerData');
        $myPic.cropper('zoomTo', 1);
        console.log("========================");
        var initRotate = 0;
        // currentPostCardInfo.pictureSize.width
        // 图像方向和画布方向不一致
        if ((canvasData.width - canvasData.height) * (cropBoxData.height - cropBoxData.width) > 0) {
            $myPic.cropper('rotate', -90);
            $myPic.cropper('autoCropArea', 0.6);
            initRotate -= 90;
        }
        if (cropBoxData.width / cropBoxData.height < canvasData.width / canvasData.height) {
            $myPic.cropper('zoomTo', (cropBoxData.height / canvasData.naturalHeight));
        } else {
            $myPic.cropper('zoomTo', (cropBoxData.width / canvasData.naturalWidth));
        }
        currentPostCardInfo.cropInfo = {
            left: (cropBoxData.left - canvasData.left) / canvasData.width,
            top: (cropBoxData.top - canvasData.top) / canvasData.height,
            width: cropBoxData.width / canvasData.width,
            height: cropBoxData.height / canvasData.height,
            rotation: initRotate
        };
    }

};
var currentMouserPoint = undefined;
var isSubmitEnabled = false;
var currentPostCardInfo = {
    "pictureSize": {
        "height": 90.0,
        "width": 135.0
    },
    "postCardFileId": "fb413367e2e29e2f05baff4489baac79",
    "postCardId": "402881e75ad23605015ad25472bf0003",
    "productSize": {
        "height": 100.0,
        "width": 145.0
    },
    "type": "TYPE_A"
};
$(function () {
    var $myPic = $('#myPic');
    //设置好裁切对象
    var $cropper = $myPic.cropper(options);
    //相关事件
    $('#postcard')
        .on('mouseenter', function (a) {
            currentMouserPoint = {
                x: a.screenX,
                y: a.screenY
            };
        })
        .on('mouseleave', function () {
            console.log("鼠标移除");
        })
        //鼠标移动事件
        .on('mousemove', function (a, b, c) {
            //第一次鼠标移动，一般鼠标进入的时候执行
            if (currentMouserPoint == undefined) {
                console.log("鼠标在里面");
                currentMouserPoint = {
                    x: a.screenX,
                    y: a.screenY
                };
                return;
            }
            if (!isSubmitEnabled) {
                return
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
            moveMyPicture({deltaX: mousePointDelta.x, deltaY: mousePointDelta.y});
        })
        //鼠标滚动事件
        .on('mousewheel', function (a) {
            if (!isSubmitEnabled) {
                return
            }
            var canvasData = $myPic.cropper('getCanvasData');
            var cropBoxData = $myPic.cropper('getCropBoxData');
            if (a.originalEvent.wheelDelta > 0) {
                $myPic.cropper('zoom', 0.05);
            } else {
                $myPic.cropper('zoom', -0.05);
            }

            moveMyPicture(
                {
                    deltaX: 0,
                    deltaY: 0
                }
            );
        })
        .on('click', function () {
            //如果两条边都比大于1，说明白边太大，进行修正
            // if (currentPostCardInfo.cropInfo.width > 1 && currentPostCardInfo.cropInfo.height > 1) {
            //     //调整大小
            //     var canvasData = $myPic.cropper('getCanvasData');
            //     var cropBoxData = $myPic.cropper('getCropBoxData');
            //     if (cropBoxData.width / cropBoxData.height >= canvasData.width / canvasData.height) {
            //         $myPic.cropper('zoomTo', (cropBoxData.height / canvasData.naturalHeight));
            //     } else {
            //         $myPic.cropper('zoomTo', (cropBoxData.width / canvasData.naturalWidth));
            //     }
            //     moveMyPicture(
            //         {
            //             deltaX: 0,
            //             deltaY: 0
            //         }
            //     );
            // }

            if (isSubmitEnabled) {
                isSubmitEnabled = false;
                $myPic.cropper('disable');
                //提交逻辑
                $.ajax({
                    url: apiBasePath+ '/postcard/submit',
                    dataType: "json",
                    contentType: "application/json",
                    type: 'post',
                    data: JSON.stringify(currentPostCardInfo),
                    success: function () {
                        console.log("执行成功");
                        //加载下一张逻辑
                        getNextPostCard();
                    },
                    error: function (a) {
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
        // 初始化init
        $.ajax({
            method: 'get',
            url: apiBasePath+ '/postcard/next',
            success: function (result) {
                var $myPic = $('#myPic');
                $myPic.cropper('destroy');
                $myPic.cropper(options);
                if (result.code == 200) {
                    currentPostCardInfo = result.body;
                    $myPic.cropper('setAspectRatio', currentPostCardInfo.pictureSize.width / currentPostCardInfo.pictureSize.height);
                    var containerData = $myPic.cropper('getContainerData');
                    if (containerData.width / containerData.height > currentPostCardInfo.pictureSize.width / currentPostCardInfo.pictureSize.height) {
                        var tmpCropBox = {};
                        tmpCropBox.height = containerData.height - 100;
                        tmpCropBox.width = currentPostCardInfo.pictureSize.width / currentPostCardInfo.pictureSize.height * tmpCropBox.height;
                        tmpCropBox.left = (containerData.width - tmpCropBox.width) / 2;
                        tmpCropBox.top = (containerData.height - tmpCropBox.height) / 2;
                        $myPic.cropper('zoomTo', 1);
                        $myPic.cropper('setCropBoxData', tmpCropBox);
                        console.log(tmpCropBox);
                        console.log($myPic.cropper('getCropBoxData'))
                    }
                    // $myPic.cropper().setAspectRatio(currentPostCardInfo.pictureSize.width / currentPostCardInfo.pictureSize.height);
                    $myPic.cropper('replace', fileBasePath+ '/' + currentPostCardInfo.postCardFileId);
                    isSubmitEnabled = true;
                }
                if (result.code == 404) {
                    alert("当前已经没有需要处理的明信片，请稍后刷新页面");
                }
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
        //如果画布太大，布满画布
        if ((canvasData.width > cropBoxData.width) && (canvasData.height > cropBoxData.height)) {
            //调整大小
            if (cropBoxData.width / cropBoxData.height <= canvasData.width / canvasData.height) {
                $myPic.cropper('zoomTo', (cropBoxData.height / canvasData.naturalHeight));
            } else {
                $myPic.cropper('zoomTo', (cropBoxData.width / canvasData.naturalWidth));
            }
            moveMyPicture({deltaX: 0, deltaY: 0});
            return
        }
        //如果画布太小，调大画布
        if ((canvasData.width < cropBoxData.width) && (canvasData.height < cropBoxData.height)) {
            //调整大小
            if (cropBoxData.width / cropBoxData.height >= canvasData.width / canvasData.height) {
                $myPic.cropper('zoomTo', (cropBoxData.height / canvasData.naturalHeight));
            } else {
                $myPic.cropper('zoomTo', (cropBoxData.width / canvasData.naturalWidth));
            }
            setTimeout(function () {
                moveMyPicture({deltaX: 0, deltaY: 0});
            }, 100);
            return
        }


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
        if (currentPostCardInfo.cropInfo == undefined) {
            currentPostCardInfo.cropInfo = {};
        }
        currentPostCardInfo.cropInfo.left = (cropBoxData.left - canvasData.left) / canvasData.width;
        currentPostCardInfo.cropInfo.top = (cropBoxData.top - canvasData.top) / canvasData.height;
        currentPostCardInfo.cropInfo.width = cropBoxData.width / canvasData.width;
        currentPostCardInfo.cropInfo.height = cropBoxData.height / canvasData.height;
    }

});



