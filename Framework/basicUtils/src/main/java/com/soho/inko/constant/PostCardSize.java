package com.soho.inko.constant;


import com.soho.inko.image.Size;

/**
 * Created by ZhongChongtao on 2017/3/25.
 */
public interface PostCardSize {
    /**
     * 获取净图像大小
     *
     * @param paperSize 成品尺寸
     * @return 图像大小
     */
    Size getPictureSize(Size paperSize);

    String getName();
}
