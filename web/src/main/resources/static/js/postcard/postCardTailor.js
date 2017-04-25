$.fn.PostCard = function () {
    /** 记录坐标变化值
     *  @type {{deltaX: number, deltaY: number}}
     */
    var positionDelta = {};
    /** 图像原始尺寸，用于获取长宽比例
     *  @type {{width: number, height: number}} */
    var imgOriginalSize = {
        width: 100,
        height: 100
    };
    /** 轮询定时器，当前如果没有需要裁切的明信片的时候，设置定时器，并将ID设置到此对象中 */
    var pollingTimer;
    /** 裁切板对象 */
    var $tailorPad; //保存裁切板对象
    /** 裁切框对象 */
    var $cropBox; //
    /** 遮罩处理的图像 */
    var $pictureBack;
    /** 明信片白边*/
    var $cropWhiteEdge;
    /** 当前明信片数据*/
    var currentData = {
        "postCardId": "2",
        "postCardFileId": "6490240608b819bee181eb3f17102553",
        "productSize": {"width": 148.0, "height": 100.0},
        "pictureSize": {"width": 138.0, "height": 69.0},
        "location": {"x": 5.0, "y": 5.0},
        "cropInfo": {
            rotation: 0,
            left: 0,
            top: 0,
            height: 0,
            width: 0
        }
    };
    /** 图片框信息 */
    var pictureBox = {
        location: {
            x: 0,
            y: 0
        },
        size: {
            width: 0,
            height: 0
        }

    };
    /** 裁切框信息 */
    var cropBox = {
        location: {
            x: 0,
            y: 0
        },
        size: {
            width: 0,
            height: 0
        }

    };
    /**
     * 设定是否可以提交
     * 默认不可提交
     * @type {boolean}
     */
    var canSubmit = false;
    /**
     * 设定是否可以移动
     * 默认不可移动
     * @type {boolean}
     */
    var canMove = false;
    /**
     * 用于存放遮罩区域对象
     * 一共有四个对象
     */
    var $cropCoverArea;
    //清理内容
    $(this.html(''));
    // 创建对象 ==========================================================
    $(this).append(
        $('<div>').attr('id', 'MyContext')
            .append($('<div>').attr('id', 'cropBox02'))
            .append($('<div>').attr('id', 'cropBack'))
            .append($('<div>')
                .append($('<img>')
                    .attr('id', 'pic')))//图片层
            .append($('<div>').addClass('cropCover'))//遮罩层
            .append($('<div>').addClass('cropCover'))//遮罩层
            .append($('<div>').addClass('cropCover'))//遮罩层
            .append($('<div>').addClass('cropCover'))//裁切框
            .append($('<div>').addClass('cropBoxCantSubmit').attr('id', 'cropBox'))//裁切框
            .append($('<div>').attr('id', 'tailorPad'))//移动定位
    );
    // 保存对象变量 ======================================================
    $tailorPad = $("#tailorPad"); //取得 裁切板对象
    $cropBox = $("#cropBox");//取得裁切框
    $cropWhiteEdge = $("#cropBack");//取得明信片纸张信息
    $pictureBack = $("#pic");//取得背景图片
    $cropCoverArea = $(".cropCover");
    $cropCoverArea.hide();
    // 绑定对象事件 ======================================================
    $pictureBack.on('load', initPic);//背景图片加载完成后，执行初始化操作
    $tailorPad.on('mouseenter', function (e) {
        // console.log("鼠标进入画板区");
        positionDelta.deltaX = e.pageX - pictureBox.location.x;//修改相对坐标X
        positionDelta.deltaY = e.pageY - pictureBox.location.y;//修改相对坐标Y
    }).on("mousemove", mouseMove);//绑定移动效果;
    $cropBox.hide();//隐藏裁切框
    $cropWhiteEdge.hide();//隐藏背景框
    $cropCoverArea.hide();//隐藏裁切区域
    $pictureBack.hide();//隐藏图片框
    // 请求明信片信息 ====================================================
    requestNextPostCard();
    /**
     * 初始化画框和图像框信息
     */
    function initCropBoxAndCropShadeArea() {
        if (!$tailorPad.hasClass('tailorPad_enabled_move')) {
            // console.log("当前不可移动");
            return;
        }
        // console.log($tailorPad.width());
        // console.log($tailorPad.height());
        //宽度设置为DIV-200像素
        cropBox.size.width = $tailorPad.width() - 200;
        cropBox.size.height = cropBox.size.width * currentData.pictureSize.height / currentData.pictureSize.width;
        // console.log(cropBox);
        if (cropBox.size.height + 200 > $tailorPad.height()) {
            cropBox.size.height = $tailorPad.height() - 200;
            cropBox.size.width = cropBox.size.height * currentData.pictureSize.width / currentData.pictureSize.height;
        }

        cropBox.location.x = ($tailorPad.width() - cropBox.size.width) / 2;
        cropBox.location.y = ($tailorPad.height() - cropBox.size.height) / 2;
        // $cropBox.css({
        //     width: $tailorPad.width() - 200,
        //     height: ($tailorPad.width() - 200) * currentData.pictureSize.height / currentData.pictureSize.width
        // });
        //如果高度的边框不足200像素，高度DIV-200像素，宽度随之改变
        // if ($cropBox.height() + 200 > $tailorPad.height()) {
        //     $cropBox.css({
        //         height: $tailorPad.height() - 200,
        //         width: ($tailorPad.height() - 200) * currentData.pictureSize.width / currentData.pictureSize.height
        //     });
        // }
        //宽度和高度确定后，调整左右边距
        $cropBox.css({
            left: cropBox.location.x,
            top: cropBox.location.y,
            width: cropBox.size.width,
            height: cropBox.size.height
        });
        console.log(cropBox);
        //保存裁切框信息，只在初始化的时候进行此操作
        // console.log("正在保存cropBox信息");
        // console.log(cropBox);
        // console.log("正在显示裁切框");
        //显示裁切框和裁切框背景，这个是两个对象
        $cropBox.show();
        $cropWhiteEdge.css({//设置背景色
            left: $cropBox.position().left,
            top: $cropBox.position().top,
            width: cropBox.size.width,
            height: $cropBox.height()
        });
        $cropWhiteEdge.show();
        //显示遮罩图层
        refreshCropBoxShade();

    }

    $(window).on('resize', function () {
        initPictureBox();//初始化
        initCropBoxAndCropShadeArea();
    });
    function enabledMove() {
        $tailorPad.addClass('tailorPad_enabled_move');
    }

    /**
     * 初始化图像加载信息
     */
    function initPic() {
        enabledMove();//允许移动
        console.log("图片加载成功，正在执行初始化");
        if (imgOriginalSize == undefined) {
            imgOriginalSize = {};
            imgOriginalSize.width = $pictureBack.width();//设置原始尺寸的宽度
            imgOriginalSize.height = $pictureBack.height();//设置原始尺寸的高度
        }
        console.log(imgOriginalSize);
        initCropBoxAndCropShadeArea();
        //初始化图像框和画布框
        initPictureBox();//初始化
        $(this).show();//显示背景图片

        //自动裁切
        if (imgOriginalSize.width / imgOriginalSize.height == cropBox.size.width / cropBox.size.height) {
            console.log("长宽比正常完全正确，自动提交");
            enabledSubmit();
            submitMyPostCard();
        } else {//长宽比不正常，1秒后可以提交
            //长宽比不同意
            console.log((imgOriginalSize.width - imgOriginalSize.height) * (cropBox.size.width - cropBox.size.height));
            if ((imgOriginalSize.width - imgOriginalSize.height) * (cropBox.size.width - cropBox.size.height) < 0 && currentData.cropInfo.rotation == undefined) {
                currentData.cropInfo.rotation = 270;
                console.log("图像长宽比不统一，旋转的角度为:" + currentData.cropInfo.rotation);
                $cropBox.hide();//隐藏裁切框
                $cropWhiteEdge.hide();//隐藏背景框
                $cropCoverArea.hide();//隐藏裁切区域
                $pictureBack.hide();//隐藏图片框
                var myWidth = imgOriginalSize.width;
                //noinspection JSSuspiciousNameCombination
                imgOriginalSize.width = imgOriginalSize.height;
                //noinspection JSSuspiciousNameCombination
                imgOriginalSize.height = myWidth;
                $pictureBack
                //4、设置宽度和高度为自动（可能不是必要操作）
                    .css({//设置图片，等待图片加载完成
                        width: "auto",
                        height: "auto"
                    })
                    .attr("src", "http://localhost:8089/file/" + currentData.postCardFileId + "?rotation=" + currentData.cropInfo.rotation);
                return;//返回，不在接着执行后面的语句
            }
            if (currentData.cropInfo.rotation == undefined) {
                currentData.cropInfo.rotation = 0;
            }
            //长宽比统一的情况下，准备裁切；
            setTimeout(enabledSubmit, 1000);//设置允许提交的时间间隔
        }
    }

    $tailorPad
    // .addClass('tailorPad_enabled_move')//允许移动
        .on("mousemove", mouseMove)//鼠标移动操作
        .on("mousewheel", picture_zoomSize)//鼠标滑轮操作
        .on('click', submitMyPostCard);//点击的时候提交卡片
    /**
     * 请求下一张明信片
     * ajax向后台请求下一张明信片
     */
    function requestNextPostCard() {
        $.ajax({
            headers: {
                tokenId: tokenId
            },
            url: "http://localhost:8100/api/postcard/next",
            type: "post",
            success: prepareNewPostCard,
            dataType: "json",
            contentType: "application/json"
        });
    }

    /**
     * 鼠标在裁切板上移动的时候触发
     * @param e 鼠标等参数信息
     */
    function mouseMove(e) {
        // console.log("鼠标移动逻辑");
        //如果有class，则执行，否则什么也不做
        if ($(this).hasClass('tailorPad_enabled_move')) {
            // mouserPosition.x = e.pageX;//获取鼠标x坐标
            // mouserPosition.y = e.pageY;//获取鼠标y坐标
            // if (canMove) { //如果可以移动的话，修改图片坐标
            pictureBox.location.x = e.pageX - positionDelta.deltaX;//修改 数据中的图片框X坐标
            pictureBox.location.y = e.pageY - positionDelta.deltaY;//修改 数据中的图片框Y坐标
            refreshPictureBox();//刷新图像框位置
            // }
            //设置鼠标相对路径
            positionDelta.deltaX = e.pageX - pictureBox.location.x;//修改相对坐标X
            positionDelta.deltaY = e.pageY - pictureBox.location.y;//修改相对坐标Y
        }
    }


    function disabledMove() {
        $tailorPad.removeClass('tailorPad_enabled_move');
    }

    function submitMyPostCard() {
        //如果没有数据或者还没有到可以提交的时间,返回
        if (!canSubmit) {
            return;
        }
        disabledSubmit();//禁止提交
        disabledMove();
        $cropBox.hide();//隐藏裁切框
        $cropWhiteEdge.hide();//隐藏背景框
        $cropCoverArea.hide();//隐藏裁切区域
        $pictureBack.hide();//隐藏图片框
        // $tailorPad.unbind("mousemove");//取消移动效果
        if (currentData.cropInfo == undefined) {
            console.log("裁切信息没有初始化");
            currentData.cropInfo = {};
            currentData.cropInfo.rotation = 0;
        }
        currentData.cropInfo.left = (cropBox.location.x - pictureBox.location.x) / pictureBox.size.width;
        currentData.cropInfo.top = (cropBox.location.y - pictureBox.location.y) / pictureBox.size.height;
        currentData.cropInfo.width = cropBox.size.width / pictureBox.size.width;
        currentData.cropInfo.height = cropBox.size.height / pictureBox.size.height;
        console.log(JSON.stringify(currentData));

        $.ajax({
            headers: {
                tokenId: tokenId
            },
            url: "http://localhost:8100/api/postcard/submit",
            type: "post",
            success: function (result) {
                console.log(result);
                //获取下一张postCard
                requestNextPostCard();
            },
            error: function () {
                alert("服务器发生异常");
            },
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify(currentData)
        });
    }

    /**
     * 初始化currentData数据，修改图片路径，等待加载完成后
     * @param data AJAX传过来的数据
     * @type {{code: number, message: string,body:{postCardId: string, postCardFileId: string, productSize: {width: number, height: number}, pictureSize: {width: number, height: number}, location: {x: number, y: number}, cropInfo: null}}}
     */
    function prepareNewPostCard(data) {
        if (data.code == 200) {//返回码为200，有记录
            //1、清理轮询计时器
            clearPollingTimer();
            //2、保存body数据
            currentData = data.body;
            imgOriginalSize = undefined;
            //初始化裁切信息
            currentData.cropInfo = {};
            //裁切旋转角度为0
            //3、从数据中获取图片ID,设置新的路径
            $pictureBack
            //4、设置宽度和高度为自动（可能不是必要操作）
                .css({//设置图片，等待图片加载完成
                    width: "auto",
                    height: "auto"
                })
                .attr("src", "http://localhost:8089/file/" + currentData.postCardFileId);
        } else {
            alert(data.message);//打印消息
            startPollingTimer();
        }
    }

    /**
     * 清理轮询定时器
     */
    function clearPollingTimer() {
        if (pollingTimer != undefined) {
            window.clearInterval(pollingTimer);//取消定时器
            pollingTimer = undefined;//定时器设置为undefined，以备下一次启动
        }
    }

    /**
     * 开启轮询定时器
     */
    function startPollingTimer() {
        //启动之前，保证之前没有定时器
        clearPollingTimer();
        //启动轮询器，并且将循环定时器ID保存起来
        pollingTimer = setInterval(function () {
            //TODO:轮询逻辑
        }, 2000)
    }

    /**
     * 载入图片之后进行初始化
     */
    function initPictureBox() {
        console.log(cropBox.size);
        // setCropBox(cropBox.size.width, cropBox.size.height);
        //先尝试按照高相同进行等比缩放
        pictureBox.size.height = cropBox.size.height;
        pictureBox.size.width = cropBox.size.height * imgOriginalSize.width / imgOriginalSize.height;
        console.log(imgOriginalSize);
        if (pictureBox.size.width < cropBox.size.width) {//等比缩放后发现宽度比裁切框小，说明是正方形，宽度相同，高度等比缩放；
            pictureBox.size.width = cropBox.size.width;
            pictureBox.size.height = cropBox.size.width * imgOriginalSize.height / imgOriginalSize.width;
        }
        pictureBox.location.x = cropBox.location.x;//左侧坐标为裁切框坐标
        pictureBox.location.y = cropBox.location.y;//上侧坐标为裁切框坐标
        refreshPictureBox();
    }

    /**
     * 验证并且设置位置信息
     */
    function checkLocation() {
        //如果图片左侧坐标大于裁切框左侧坐标，则图片坐标设置成裁切框左侧坐标
        if (pictureBox.location.x > cropBox.location.x) {
            pictureBox.location.x = cropBox.location.x;
        }
        //如果图片上侧坐标大于裁切框左侧坐标，则图片坐标设置成裁切框上侧坐标
        if (pictureBox.location.y > cropBox.location.y) {
            pictureBox.location.y = cropBox.location.y;
        }
        //如果图片右侧小于裁切框右侧，图片右侧设置为裁切框的右侧
        if (pictureBox.location.x + pictureBox.size.width < cropBox.location.x + cropBox.size.width) {
            // console.log(pictureBox.location.x);
            pictureBox.location.x = cropBox.size.width - pictureBox.size.width + cropBox.location.x;
        }
        //如果图片下侧小于裁切框右侧，图片下侧设置为裁切框的右侧
        if (pictureBox.location.y + pictureBox.size.height < cropBox.location.y + cropBox.size.height) {
            pictureBox.location.y = cropBox.size.height - pictureBox.size.height + cropBox.location.y;
        }
        //如果宽度小于裁切框
        if (pictureBox.size.width < cropBox.size.width) {
            pictureBox.location.x = cropBox.location.x + (cropBox.size.width - pictureBox.size.width) / 2;
        }
        //如果高度小于裁切框
        if (pictureBox.size.height < cropBox.size.height) {
            pictureBox.location.y = cropBox.location.y + (cropBox.size.height - pictureBox.size.height) / 2;
        }
    }

    /**
     * 刷新图片框
     */
    function refreshPictureBox() {
        //检验图像框尺寸是否有问题
        checkPictureBoxSize();
        //检验图像框坐标是否有问题
        checkLocation();
        //刷新图片
        $pictureBack.css({
            left: pictureBox.location.x,
            top: pictureBox.location.y,
            width: pictureBox.size.width,
            height: pictureBox.size.height
        })
    }

    /**
     * 验证图片尺寸是否在正确的范围之内
     */
    function checkPictureBoxSize() {
        if (pictureBox.size.width < cropBox.size.width && pictureBox.size.height < cropBox.size.height) {//两边都小于裁切框
            //如果上下一定有白边
            if (imgOriginalSize.width / imgOriginalSize.height > cropBox.size.width / cropBox.size.height) {
                pictureBox.size.width = cropBox.size.width;
                pictureBox.size.height = cropBox.size.width * imgOriginalSize.height / imgOriginalSize.width;
            } else {
                pictureBox.size.height = cropBox.size.height;
                pictureBox.size.width = cropBox.size.height * imgOriginalSize.width / imgOriginalSize.height;
            }
        }
        else if (pictureBox.size.width > cropBox.size.width && pictureBox.size.height > cropBox.size.height) {//两遍都大于裁切框
            if (imgOriginalSize.width / imgOriginalSize.height < cropBox.size.width / cropBox.size.height) {//图片是正方形样式的
                pictureBox.size.width = cropBox.size.width;
                pictureBox.size.height = cropBox.size.width * imgOriginalSize.height / imgOriginalSize.width;
            } else {//这一个是长方形,高度规定，宽度自适应
                pictureBox.size.height = cropBox.size.height;
                pictureBox.size.width = cropBox.size.height * imgOriginalSize.width / imgOriginalSize.height;
            }
        }
    }

    /**
     * 鼠标滑轮滚动的时候进行缩放
     * @param e 滚动参数
     */
    function picture_zoomSize(e) {
        var delta = (e.originalEvent.wheelDelta && (e.originalEvent.wheelDelta > 0 ? 1 : -1)) || (e.originalEvent.detail && (e.originalEvent.detail > 0 ? -1 : 1)); // firefox
        if (delta > 0) {
            if (pictureBox.size.width / pictureBox.size.height > cropBox.size.width / cropBox.size.height) {
                pictureBox.size.width += e.shiftKey ? 100 : e.altKey ? 2 : 20;
                pictureBox.size.height = pictureBox.size.width * imgOriginalSize.height / imgOriginalSize.width;
            } else if (pictureBox.size.width / pictureBox.size.height < cropBox.size.width / cropBox.size.height) {
                pictureBox.size.height += e.shiftKey ? 100 : e.altKey ? 2 : 20;
                pictureBox.size.width = pictureBox.size.height * imgOriginalSize.width / imgOriginalSize.height;
            }
        } else if (delta < 0) {
            if (pictureBox.size.width / pictureBox.size.height > cropBox.size.width / cropBox.size.height) {
                pictureBox.size.width -= e.shiftKey ? 100 : e.altKey ? 2 : 20;
                pictureBox.size.height = pictureBox.size.width * imgOriginalSize.height / imgOriginalSize.width;
            } else if (pictureBox.size.width / pictureBox.size.height < cropBox.size.width / cropBox.size.height) {
                pictureBox.size.height -= e.shiftKey ? 100 : e.altKey ? 2 : 20;
                pictureBox.size.width = pictureBox.size.height * imgOriginalSize.width / imgOriginalSize.height;
            }
        }
        refreshPictureBox()
    }

    /**
     * 设置允许提交
     */
    function enabledSubmit() {
        canSubmit = true;
        $cropBox.removeClass("cropBoxCantSubmit").addClass("cropBoxCanSubmit");
    }

    function disabledSubmit() {
        canSubmit = false;
        $cropBox.addClass("cropBoxCantSubmit").removeClass("cropBoxCanSubmit");
    }

    /**
     * 刷新遮罩图层
     */
    function refreshCropBoxShade() {
        $cropCoverArea.eq(0)
            .css({//最左侧的区域
                width: ($tailorPad.width() - cropBox.size.width) / 2,
                left: 0,
                top: 0
            });
        $cropCoverArea.eq(1)
            .css({//最右侧的区域
                width: ($tailorPad.width() - cropBox.size.width) / 2,
                right: 0,
                top: 0
            });
        $cropCoverArea.eq(2)
            .css({//最上侧的区域
                width: cropBox.size.width,
                height: ($tailorPad.height() - cropBox.size.height) / 2,
                left: cropBox.location.x,
                top: 0
            });
        $cropCoverArea.eq(3)
            .css({//最下侧的区域
                width: cropBox.size.width,
                height: ($tailorPad.height() - cropBox.size.height) / 2,
                left: cropBox.location.x,
                bottom: 0
            });
        $cropCoverArea
            .css({//设置所有遮罩属性
                position: "absolute"
            })
            .show();
    }

    return this;
};