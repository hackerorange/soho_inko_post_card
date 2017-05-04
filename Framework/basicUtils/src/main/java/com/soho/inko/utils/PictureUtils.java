package com.soho.inko.utils;

import com.soho.inko.constant.im4java.ImageCommandType;
import com.soho.inko.image.Size;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.im4java.core.*;
import org.im4java.process.ArrayListOutputConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@SuppressWarnings({"unused", "SameParameterValue", "WeakerAccess"})
public class PictureUtils {
    private static boolean IS_WINDOWS_OS = System.getProperty("os.name").toLowerCase().contains("win");
    private static String PICTURE_MAGIC_PATH = "D:/Program Files (x86)/GraphicsMagick-1.3.25-Q16";
    private static boolean USE_GRAPHICS_MAGICK_PATH = true;

    private static Logger logger = LoggerFactory.getLogger(PictureUtils.class);

    /**
     * 初始化图片处理程序路径路径
     * imageMagick或者是graphicsMagick的路径
     *
     * @param path imageMagic安装路径
     */
    public static void setPictureMagicPath(String path) {
        PICTURE_MAGIC_PATH = path;
    }

    /**
     * 设置是否使用graphicsMagic
     * 默认使用true
     *
     * @param useGraphicsMagickPath 使用的是graphicsMagic，则设置为true,否则设置为false
     */
    public static void setUseGraphicsMagickPath(boolean useGraphicsMagickPath) {
        USE_GRAPHICS_MAGICK_PATH = useGraphicsMagickPath;
    }

    /**
     * 根据坐标裁剪图片,裁切后覆盖原始图片,坐标点为图片的百分比
     *
     * @param sourcePath 要裁剪图片的路径
     * @param startX     起始横坐标（%）
     * @param startY     起始纵坐标（%）
     * @param endX       结束横坐标（%）
     * @param endY       结束纵坐标（%）
     */
    public static void cutImageByPercent(String sourcePath, double startX, double startY, double endX, double endY) throws InterruptedException, IOException, IM4JavaException {
        cutImageByPercent(sourcePath, sourcePath, startX, startY, endX, endY);
    }

    /**
     * 根据坐标裁剪图片,裁切后覆盖原始图片,坐标点为图片的百分比
     *
     * @param sourcePath 要裁剪图片的路径
     * @param targetPath 裁剪图片后的路径
     * @param startX     起始横坐标（%）
     * @param startY     起始纵坐标（%）
     * @param endX       结束横坐标（%）
     * @param endY       结束纵坐标（%）
     */
    public static void cutImageByPercent(String sourcePath, String targetPath, double startX, double startY, double endX, double endY) throws InterruptedException, IOException, IM4JavaException {
        Map<String, String> imageInfo = getImageInfo(sourcePath);
        assert imageInfo != null;
        int width = Integer.parseInt(imageInfo.get("width"));
        int height = Integer.parseInt(imageInfo.get("height"));
        cutImage(sourcePath, targetPath, (int) (startX * width), (int) (startY * height), (int) (endX * width), (int) (endY * height));
    }

    /**
     * 根据坐标裁剪图片,裁切后覆盖原始图片
     *
     * @param sourcePath 要裁剪图片的路径
     * @param startX     起始横坐标
     * @param startY     起始纵坐标
     * @param endX       结束横坐标
     * @param endY       结束纵坐标
     */
    public static void cutImage(String sourcePath, int startX, int startY, int endX, int endY) throws InterruptedException, IOException, IM4JavaException {
        cutImage(sourcePath, sourcePath, startX, startY, endX, endY);
    }

    /**
     * 根据坐标裁剪图片
     *
     * @param sourcePath 要裁剪图片的路径
     * @param targetPath 裁剪图片后的路径
     * @param startX     起始横坐标
     * @param startY     起始纵坐标
     * @param endX       结束横坐标
     * @param endY       结束纵坐标
     */
    public static void cutImage(String sourcePath, String targetPath, int startX, int startY, int endX, int endY) throws InterruptedException, IOException, IM4JavaException {
        int width = endX - startX;
        int height = endY - startY;
        IMOperation op = new IMOperation();
        op.addImage(sourcePath);
        op.crop(width, height, startX, startY);
        op.addImage(targetPath);
        getImageCommand(ImageCommandType.CONVERT).run(op);
    }


    /**
     * 根据宽度和高度，缩放指定路径下的图片，并覆盖原始文件
     *
     * @param sourcePath   源图片路径
     * @param targetWidth  缩放后的图片宽度
     * @param targetHeight 缩放后的图片高度
     */
    public static void zoomPicture(String sourcePath, int targetWidth, int targetHeight) throws InterruptedException, IOException, IM4JavaException {
        zoomPicture(sourcePath, sourcePath, targetWidth, targetHeight);
    }

    /**
     * 根据宽度和高度，缩放指定路径下的文件，并保存到目标文件,并旋转指定的角度
     *
     * @param sourcePath   源图片路径
     * @param targetPath   缩放后图片的路径
     * @param targetWidth  缩放后的图片宽度
     * @param targetHeight 缩放后的图片高度
     * @param rotation     旋转的角度
     */
    public static void zoomPicture(String sourcePath, String targetPath, int targetWidth, int targetHeight, double rotation) throws InterruptedException, IOException, IM4JavaException {
        IMOperation op = new IMOperation();
        op.addImage(sourcePath);
        op.resize(targetWidth, targetHeight);
        if (rotation != 0) {
            op.rotate(rotation);
        }
        op.addImage(targetPath);
        logger.info("正在生成缩略图");
        logger.info("原始路径为:" + sourcePath);
        logger.info("目标路径为:" + sourcePath);
        getImageCommand(ImageCommandType.CONVERT).run(op);
    }

    /**
     * 根据宽度和高度，缩放指定路径下的文件，并保存到目标文件
     *
     * @param sourcePath   源图片路径
     * @param targetPath   缩放后图片的路径
     * @param targetWidth  缩放后的图片宽度
     * @param targetHeight 缩放后的图片高度
     */
    public static void zoomPicture(String sourcePath, String targetPath, int targetWidth, int targetHeight) throws InterruptedException, IOException, IM4JavaException {
        zoomPicture(sourcePath, targetPath, targetWidth, targetHeight, 0);
    }

    /**
     * 旋转图片
     *
     * @param sourcePath 要旋转的图片的路径
     * @param targetPath 旋转后的图片保存路径
     * @param rotation   旋转的角度
     */
    public static void rotatePicture(String sourcePath, String targetPath, double rotation) throws InterruptedException, IOException, IM4JavaException {
        IMOperation op = new IMOperation();
        op.addImage(sourcePath);
        op.rotate(rotation);
        op.addImage(targetPath);
        getImageCommand(ImageCommandType.CONVERT).run(op);
    }

    /**
     * 根据宽度缩放图片
     *
     * @param width   缩放后的图片宽度
     * @param srcPath 源图片路径
     * @param newPath 缩放后图片的路径
     */
    public static void cutImage(int width, String srcPath, String newPath) throws InterruptedException, IOException, IM4JavaException {
        IMOperation op = new IMOperation();
        op.addImage(srcPath);
        op.resize(width, null);
        op.addImage(newPath);

        ConvertCmd convert = new ConvertCmd();
        getImageCommand(ImageCommandType.CONVERT).run(op);
    }

    /**
     * 给图片添加文字水印
     *
     * @param pressText     水印文字
     * @param srcImageFile  源图像地址
     * @param destImageFile 目标图像地址
     * @param fontName      水印的字体名称
     * @param fontStyle     水印的字体样式
     * @param color         水印的字体颜色
     * @param fontSize      水印的字体大小
     * @param x             修正值
     * @param y             修正值
     * @param alpha         透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     */
    public static void pressText(String pressText, String srcImageFile, String destImageFile, String fontName, int fontStyle, Color color, int fontSize, int x, int y, float alpha) {
        try {
            File img = new File(srcImageFile);
            Image src = ImageIO.read(img);
            int width = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();

            g.drawImage(src, 0, 0, width, height, null);
            g.setColor(color);
            g.setFont(new Font(fontName, fontStyle, fontSize));
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            // 在指定坐标绘制水印文字
            g.drawString(pressText, (width - ((pressText.length()) * fontSize)) / 2 + x, (height - fontSize) / 2 + y);
            g.dispose();
            ImageIO.write(image, "JPEG", new File(destImageFile));// 输出到文件流
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 给图片加水印
     *
     * @param srcPath 源图片路径
     */
    public static void addImageText(String srcPath) {

        IMOperation op = new IMOperation();
        op.font("宋体").gravity("southeast").pointsize(18).fill("#BCBFC8").draw("Test Version");
        op.addImage();
        op.addImage();
        ConvertCmd convert = (ConvertCmd) getImageCommand(ImageCommandType.CONVERT);
        ConvertCmd identifyCmd = (ConvertCmd) getImageCommand(ImageCommandType.CONVERT);
        try {
            convert.run(op, srcPath, srcPath);
        } catch (IOException | InterruptedException | IM4JavaException e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取图片信息
     *
     * @param sourcePath 图片路径
     * @return Map {height=, fileLength=, directory=, width=, filename=}
     * @throws Exception 发生异常，抛出
     */
    public static Map<String, String> getImageInfo(String sourcePath) throws InterruptedException, IOException, IM4JavaException {
        IMOperation op = new IMOperation();
        op.format("%w,%h,%d,%f,%b");
        op.addImage(sourcePath);
        IdentifyCmd identifyCmd = (IdentifyCmd) getImageCommand(ImageCommandType.IDENTIFY);
        ArrayListOutputConsumer output = new ArrayListOutputConsumer();
        identifyCmd.setOutputConsumer(output);
        identifyCmd.run(op);
        ArrayList<String> cmdOutput = output.getOutput();
        if (cmdOutput.size() != 1) return null;
        String line = cmdOutput.get(0);
        String[] arr = line.split(",");
        Map<String, String> info = new HashMap<>();
        info.put("width", arr[0]);
        info.put("height", arr[1]);
        info.put("directory", arr[2]);
        info.put("filename", arr[3]);
        info.put("fileLength", arr[4]);
        return info;
    }

    /**
     * 获取图片尺寸，单位：px
     *
     * @param sourcePath 源文件路径
     * @return 图像尺寸
     */
    public static Size getImageSize(String sourcePath) throws InterruptedException, IOException, IM4JavaException {
        Map<String, String> imageInfo = getImageInfo(sourcePath);
        if (imageInfo != null) {
            return new Size(Double.parseDouble(imageInfo.get("width")), Double.parseDouble(imageInfo.get("height")));
        }
        return null;
    }

    /**
     * 生成缩略图
     *
     * @param sourcePath 原始PDF文件路径
     * @param targetPath 生成的目标文件路径
     * @return 是否生成图片成功
     */
    public static boolean convertSinglePagePdfToPicture(String sourcePath, String targetPath) {
        try {
            File file = new File(sourcePath);
            if (!file.exists()) {
                return false;
            }
            PDDocument doc = PDDocument.load(file);
            PDFRenderer renderer = new PDFRenderer(doc);
            int pageCount = doc.getNumberOfPages();
            BufferedImage image = renderer.renderImageWithDPI(0, 72);
            ImageIO.write(image, "PNG", new File(targetPath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }


    /**
     * 获取 ImageCommand
     *
     * @param type 命令类型
     * @return ImageCommand对象
     */
    private static ImageCommand getImageCommand(ImageCommandType type) {
        ImageCommand imageCommand = null;
        try {
            imageCommand = type.getCommandClazz().getConstructor(boolean.class).newInstance(USE_GRAPHICS_MAGICK_PATH);
            //判断是否使用windows系统,使用windows系统的话，需要设置路径
            if (IS_WINDOWS_OS) {
                imageCommand.setSearchPath(PICTURE_MAGIC_PATH);
            }
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return imageCommand;
    }


    public static void main(String[] args) throws Exception {
//        convertPdfToImage("D:/1.pdf", "D:/1.jpg");
//        ConvertCmd convertCmd = new ConvertCmd(true);
//        Map<String, String> imageInfo = getImageInfo("D:\\Desktop\\796544326121704559.jpg");
//        System.out.println(imageInfo);
//        Long javaTime = 0L;
//        Long jniTime = 0L;
//        for (int i = 0; i < 100; i++) {
//
//            Long b = System.currentTimeMillis();
//            getImageInfo("D:\\Desktop\\新建文件夹\\1.jpg");
//            jniTime += (System.currentTimeMillis() - b);
//            System.out.println("第" + i + "次JNI时间：" + jniTime);
//
//            Long a = System.currentTimeMillis();
//            getImageSize("D:\\Desktop\\新建文件夹\\1.jpg");
//            javaTime += (System.currentTimeMillis() - a);
//            System.out.println("第" + i + "次java时间：" + javaTime);
//        }
//        System.out.println(javaTime / 100);
//        System.out.println(jniTime / 100);
    }
}