/**
 * 明信片裁切器
 * @param {Object} obj 作为明信片裁切器的对象
 */
function PostCard(obj) {
    /**
     * 记录坐标变化值
     * @type {{deltaX: number, deltaY: number}}
     */
    var positionDelta =
        {
            deltaX: 10, //横坐标增量
            deltaY: 10 //纵坐标增量
        };
    /**
     * 原始尺寸，用于获取长宽比例
     * @type {{width: number, height: number}}
     */
    var originalSize = {
        width: 100,
        height: 100
    };
    var myTime;
    /**
     * 记录当前工作区的尺寸
     * 尺寸发生变化，以此为判断
     */
    var currentDivSize = {
        width: 0,
        height: 0
    };
    /**
     * 存放鼠标当前位置
     */
    var mouserPosition = {
        x: 0,
        y: 0
    };
    /**
     * 裁切板对象
     */
    var $tailorPad; //保存裁切板对象
    /**
     * 裁切框对象
     */
    var $cropBox; //
    /**
     * 遮罩处理的图像
     */
    var $pictureBack;
    /**
     * 存放明信片背景
     */
    var $cropBack;
    /**
     * 当前明信片详细信息
     */
    var currentData = {
        postCard: {
            id: 1,
            order: {
                id: 1,
                customerName: "",
                waterMark: "",
                size: {
                    width: 138,
                    height: 90
                },
                type: "",
                operator: {
                    id: 1,
                    name: "",
                    password: ""
                }
            },
            filePath: "",
            fileName: "",
            createTime: 1475824695000,
            stateId: 0
        },
        cropBox: {
            location: {
                x: 0,
                y: 0
            },
            size: {
                width: 100,
                height: 100
            }
        },
        pictureBox: {
            location: {
                x: 0,
                y: 0
            },
            size: {
                width: 100,
                height: 100
            }
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
    /*初始化视图信息*/
    var tmphtml = '';
    tmphtml += '<div id="MyContext" style="width: 100%;height: 100%;">\n';
    // tmphtml += '	<div id="orange"><img id="pic2"></div>\n';
    // tmphtml += '	<div id="cropBox"><img id="pic"></div></div>\n';
    tmphtml += '    <div id="cropBox02"></div>\n';
    tmphtml += '    <div id="cropBack"></div>\n';
    tmphtml += '    <div>\n';
    tmphtml += '        <img id="pic" src="">\n';
    tmphtml += '    </div>\n';
    tmphtml += '    <div class="cropCover"></div>\n';
    tmphtml += '    <div class="cropCover"></div>\n';
    tmphtml += '    <div class="cropCover"></div>\n';
    tmphtml += '    <div class="cropCover"></div>\n';
    tmphtml += '    <div id="cropBox" class="cropBoxCantSubmit"></div>\n';
    tmphtml += '    <div id="tailorPad"></div>\n';
    tmphtml += '</div>\n';
    obj.html(tmphtml); //添加视图
    $tailorPad = $("#tailorPad"); //取得 裁切板对象
    $cropBox = $("#cropBox");//取得裁切框
    $cropBack = $("#cropBack");//取得明信片纸张信息
    $pictureBack = $("#pic");//取得背景图片
    $cropCoverArea = $(".cropCover");
    $cropCoverArea.hide();
    $pictureBack.load(initPic);//背景图片加载完成后，执行初始化操作
    setInterval(checkSize, 100);
    function initPic() {
        console.log("图片加载成功，正在执行初始化");
        originalSize.width = $pictureBack.width();//设置原始尺寸的宽度
        originalSize.height = $pictureBack.height();//设置原始尺寸的高度
        setCropBox(currentData.cropBox.size.width, currentData.cropBox.size.height);//重置裁切框

        $pictureBack.css({width: "auto", height: "auto"});//尺寸设置为自动
        //保存好原始尺寸
        init();//初始化
        $(this).show();//显示背景图片
        $cropBox.show();
        canMove = true;//允许移动
        $tailorPad.css({
            cursor: "move"//鼠标为移动的样式
        });
        if ($pictureBack.width() / $pictureBack.height() == $cropBox.width() / $cropBox.height()) {
            enabledSubmit();
            submitMyPostCard();
        } else {
            $tailorPad.bind("mousemove", mouseMove);//绑定移动效果
            setTimeout(enabledSubmit, 1000);//设置时间间隔
        }
    }

    $tailorPad.css({cursor: "move"})
        .bind("mousemove", mouseMove)
        .on("mousewheel", picture_zoomSize).click(submitMyPostCard);
    $.ajax({
        url: "orange.json",
        type: "get",
        success: setAllInfo,
        dataType: "json",
        contentType: "application/json"
    });
    /**
     * 鼠标在裁切板上移动的时候触发
     * @param e 鼠标等参数信息
     */
    function mouseMove(e) {
        console.log("触发鼠标移动事件");
        if (currentData.postCard == null) {//如果数据为NULL，返回
            return;
        }
        mouserPosition.x = e.pageX;//获取鼠标x坐标
        mouserPosition.y = e.pageY;//获取鼠标y坐标
        if (canMove) { //如果可以移动的话，修改图片坐标
            currentData.pictureBox.location.x = e.pageX - positionDelta.deltaX;//修改 数据中的图片框X坐标
            currentData.pictureBox.location.y = e.pageY - positionDelta.deltaY;//修改 数据中的图片框Y坐标
            checkLocation();//验证位置信息
        }
        //设置鼠标相对路径
        positionDelta.deltaX = e.pageX - currentData.pictureBox.location.x;//修改相对坐标X
        positionDelta.deltaY = e.pageY - currentData.pictureBox.location.y;//修改相对坐标Y
    }

    function submitMyPostCard() {
        //如果没有数据或者还没有到可以提交的时间,返回
        if (currentData.postCard == null || !canSubmit) {
            return;
        }
        disabledSubmit();//禁止提交
        $cropBox.hide();//隐藏裁切框
        $cropBack.hide();//隐藏背景框
        $cropCoverArea.hide();//隐藏裁切区域
        $pictureBack.hide();//隐藏图片框
        $tailorPad.unbind("mousemove");//取消移动效果

        $.ajax({
            url: "orange.json",
            type: "get",
            success: setAllInfo,
            error: function () {
                alert("服务器发生异常");
            },
            dataType: "json",
            contentType: "application/json"//,
            // data: JSON.stringify(currentData)
        });
    }

    /**
     * 初始化currentData数据，修改图片路径，等待加载完成后
     * @param data AJAX传过来的数据
     */
    function setAllInfo(data) {
        console.log("数据获取成功");
        currentData = data;//覆盖数据
        if (currentData.postCard == null) {//当前没有需要裁切的明信片
            if (myTime == null) {//当前没有定时器
                // myTime = setInterval(function () {//ajax定时器，每隔10秒请求一次
                //     $.ajax({
                //         url: "../postCardTailor/nextPostCard",
                //         type: "post",
                //         success: setAllInfo,
                //         dataType: "json",
                //         contentType: "application/json"
                //     })
                // }, 10000);
                // setTimeout(function () {//10秒后显示此对话框
                //     $.messager.confirm('当前没有需要裁切的图片', '当前没有找到需要裁切的明信片，是否重新加载?', function (r) {
                //         if (r) {
                //             $.ajax({
                //                 url: "../postCardTailor/nextPostCard",
                //                 type: "post",
                //                 success: setAllInfo,
                //                 dataType: "json",
                //                 contentType: "application/json"
                //             })
                //         } else {
                //             window.location = "../";
                //         }
                //     });
                // }, 1000);

            }
            $cropBox.hide();//隐藏裁切框
            $pictureBack.hide();//隐藏背景图
        } else {//有需要裁切的图片
            if (myTime != null) {//如果定时器没有取消，则取消定时器
                window.clearInterval(myTime);//取消定时器
                myTime = null;//定时器设置为NULL，以备下一次启动
            }
            console.log(JSON.stringify(data.cropBox.size));
            setCropBox(currentData.cropBox.size.width, currentData.cropBox.size.height);
            $tailorPad.bind("mousemove", mouseMove);//绑定移动效果

            $pictureBack.css({//设置图片，等待图片加载完成
                width: "auto",
                height: "auto"
            }).attr("src", "http://localhost:8080/1.jpg");// + currentData.postCard.id);
        }
    }

    /**
     * 载入图片之后进行初始化
     */
    function init() {
        if (currentData.postCard == null) {//如果当前数据为NULL，跳出
            return;
        }
        console.log(currentData.cropBox.size);
        setCropBox(currentData.cropBox.size.width, currentData.cropBox.size.height);
        //调整一下图片的尺寸
        //如果图片的宽度比较大的话，适应高度
        if (originalSize.width / originalSize.height > $cropBox.width() / $cropBox.height()) {//如果原始长宽比 比较 长
            $pictureBack.css({
                width: $cropBox.height() * originalSize.width / originalSize.height,
                height: $cropBox.height()
            });
        } else {//如果原始长宽比比较方，或者纵向的话
            $pictureBack.css({
                width: $cropBox.width,
                height: $cropBox.width() * originalSize.height / originalSize.width
            });
        }
        currentData.pictureBox.location.x = $cropBox.position().left;//左侧坐标为裁切框坐标
        currentData.pictureBox.location.y = $cropBox.position().top;//上侧坐标为裁切框坐标
        checkPictureSize();//验证图片尺寸信息
        checkLocation();//验证位置信息
    }

    /**
     * 验证并且设置位置信息
     */
    function checkLocation() {
        if (currentData.postCard == null) {
            return;
        }
        //如果图片左侧坐标大于裁切框左侧坐标，则图片坐标设置成裁切框左侧坐标
        if (currentData.pictureBox.location.x > $cropBox.position().left) {
            currentData.pictureBox.location.x = $cropBox.position().left;
        }
        //如果图片上侧坐标大于裁切框左侧坐标，则图片坐标设置成裁切框左侧坐标
        if (currentData.pictureBox.location.y > $cropBox.position().top) {
            currentData.pictureBox.location.y = $cropBox.position().top;
        }

        //设置左侧坐标
        $pictureBack.css({
            left: currentData.pictureBox.location.x,
            top: currentData.pictureBox.location.y
        });
        //如果图片右侧小于裁切框右侧，图片右侧设置为裁切框的右侧
        if ($pictureBack.position().left + $pictureBack.width() < $cropBox.position().left + $cropBox.width()) {
            currentData.pictureBox.location.x = $cropBox.width() - $pictureBack.width() + $cropBox.position().left;
            $pictureBack.css({
                left: currentData.pictureBox.location.x
            });
        }
        //如果图片下侧小于裁切框右侧，图片下侧设置为裁切框的右侧
        if ($pictureBack.position().top + $pictureBack.height() < $cropBox.position().top + $cropBox.height()) {
            currentData.pictureBox.location.y = $cropBox.height() - $pictureBack.height() + $cropBox.position().top;
            $pictureBack.css({
                top: currentData.pictureBox.location.y
            });
        }
        //如果宽度小于裁切框
        if ($pictureBack.width() < $cropBox.width()) {
            $pictureBack.css({
                left: $cropBox.position().left + ($cropBox.width() - $pictureBack.width()) / 2
            })
        }
        //如果高度小于裁切框
        if ($pictureBack.height() < $cropBox.height()) {
            $pictureBack.css({
                top: $cropBox.position().top + ($cropBox.height() - $pictureBack.height()) / 2
            })
        }
    }

    /**
     * 验证图片尺寸是否在正确的范围之内
     */
    function checkPictureSize() {
        if (currentData.postCard == null) {//如果原始数据为NULL，则返回
            return;
        }
        if ($pictureBack.width() < $cropBox.width() && $pictureBack.height() < $cropBox.height()) {//两边都小于裁切框
            //如果上下一定有白边
            if (originalSize.width / originalSize.height > $cropBox.width() / $cropBox.height()) {
                $pictureBack.css(
                    {
                        width: $cropBox.width()
                    }
                );
            } else {
                $pictureBack.css({
                    height: $cropBox.height()
                })
            }
        }

        else if ($pictureBack.width() > $cropBox.width() && $pictureBack.height() > $cropBox.height()) {
            if (originalSize.width / originalSize.height < $cropBox.width() / $cropBox.height()) {
                $pictureBack.css({//这一个是正方形
                    height: $cropBox.width() * originalSize.height / originalSize.width//高度大于裁切框
                })
            } else {//这一个是长方形
                $pictureBack.css({
                    width: $cropBox.height() * originalSize.width / originalSize.height//宽度大于裁切框
                })
            }
        }
        currentData.pictureBox.size.width = $pictureBack.width();
        currentData.pictureBox.size.height = $pictureBack.height();
    }

    /**
     * 重新设置裁切框比例
     * @param tmpWidth 比例宽度
     * @param tmpHeight 比例高度
     */
    function setCropBox(tmpWidth, tmpHeight) {
        if (currentData.postCard == null) {
            return;
        }
        $cropBox.css({
            width: $tailorPad.width() - 200,
            height: ($tailorPad.width() - 200) * tmpHeight / tmpWidth
        });
        if ($cropBox.height() + 200 > $tailorPad.height()) {
            $cropBox.css({
                height: $tailorPad.height() - 200,
                width: ($tailorPad.height() - 200) * tmpWidth / tmpHeight
            });
        }
        $cropBox.css({
            left: ($tailorPad.width() - $cropBox.width()) / 2,
            top: ($tailorPad.height() - $cropBox.height()) / 2
        });
        checkLocation();
        //初始化CropBox之后，保存此信息CropBox的信息
        currentData.cropBox.size.width = $cropBox.width();
        currentData.cropBox.size.height = $cropBox.height();
        currentData.cropBox.location.x = $cropBox.position().left;
        currentData.cropBox.location.y = $cropBox.position().top;
        //设置背景白色
        // tmpCropBox02.css(
        //     {
        //         left: tmpCropBox.position().left,
        //         top: tmpCropBox.position().top,
        //         width: tmpCropBox.width(),
        //         height: tmpCropBox.height()
        //     }
        // );
        positionDelta.deltaX = mouserPosition.x - currentData.pictureBox.location.x;
        positionDelta.deltaY = mouserPosition.y - currentData.pictureBox.location.y;
        $cropBox.show();
        $cropBack.css({//设置背景色
            left: $cropBox.position().left,
            top: $cropBox.position().top,
            width: $cropBox.width(),
            height: $cropBox.height()
        });
        $cropBack.show();
        cropBox_refresh();
        //==============================================================================================================
    }

    /**
     * 当尺寸修改的时候，触发此信息
     */
    function checkSize() {
        if (currentData.postCard == null) {
            return;
        }
        if ($tailorPad.width() != currentDivSize.width || $tailorPad.height() != currentDivSize.height) {
            setCropBox(currentData.cropBox.size.width, currentData.cropBox.size.height);
            currentDivSize.width = $tailorPad.width();
            currentDivSize.height = $tailorPad.height();
        }
    }

    /**
     * 鼠标滑轮滚动的时候进行缩放
     * @param e 滚动参数
     */
    function picture_zoomSize(e) {
        if (currentData.postCard == null) {
            return;
        }
        var delta = (e.originalEvent.wheelDelta && (e.originalEvent.wheelDelta > 0 ? 1 : -1)) || (e.originalEvent.detail && (e.originalEvent.detail > 0 ? -1 : 1)); // firefox
        if (delta > 0) {
            if ($pictureBack.width() / $pictureBack.height() > $cropBox.width() / $cropBox.height()) {
                $pictureBack.css({
                    height: 'auto',
                    width: $pictureBack.width() + 20//长方形增加宽度
                })
            } else if ($pictureBack.width() / $pictureBack.height() < $cropBox.width() / $cropBox.height()) {
                $pictureBack.css({
                    width: 'auto',
                    height: $pictureBack.height() + 20//正方形增加高度
                })
            }
        } else if (delta < 0) {
            if ($pictureBack.width() / $pictureBack.height() > $cropBox.width() / $cropBox.height()) {
                $pictureBack.css({
                    height: 'auto',
                    width: $pictureBack.width() - 20//长方形减少宽度
                })
            } else if ($pictureBack.width() / $pictureBack.height() < $cropBox.width() / $cropBox.height()) {
                $pictureBack.css({
                    width: 'auto',
                    height: $pictureBack.height() - 20//正方形减少高度
                })
            }
        }
        checkPictureSize();//检查尺寸是否合格
        checkLocation();//检查位置信息是否合格
    }

    /**
     *设置不允许移动
     */
    this.disabledMove = function () {
        canMove = false;
        $tailorPad.css({
            cursor: "pointer"
        });
    };
    /**
     *设置允许移动
     */
    this.enabledMove = function () {
        canMove = true;
        $tailorPad.css({
            cursor: "move"
        });
    };
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
     * 刷新遮罩图层的样式
     */
    function cropBox_refresh() {
        $cropCoverArea.css({//设置所有遮罩属性
            position: "absolute"
        }).show();
        $cropCoverArea.eq(0).css({
            width: ($tailorPad.width() - $cropBox.width()) / 2,
            left: 0,
            top: 0
        });
        $cropCoverArea.eq(1).css({
            width: ($tailorPad.width() - $cropBox.width()) / 2,
            right: 0,
            top: 0
        });
        $cropCoverArea.eq(2).css({
            width: $cropBox.width(),
            height: ($tailorPad.height() - $cropBox.height()) / 2,
            left: $cropBox.position().left,
            top: 0
        });
        $cropCoverArea.eq(3).css({
            width: $cropBox.width(),
            height: ($tailorPad.height() - $cropBox.height()) / 2,
            left: $cropBox.position().left,
            bottom: 0
        });
    }

    return this;
}