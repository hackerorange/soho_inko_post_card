package com.soho.inko.handler;

import com.alibaba.fastjson.JSONObject;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.soho.inko.configuration.properties.FileProperties;
import com.soho.inko.constant.UnitTypeEnum;
import com.soho.inko.database.constant.PostCardProcessStatus;
import com.soho.inko.database.entity.FileEntity;
import com.soho.inko.database.entity.PostCardEntity;
import com.soho.inko.database.repository.PostCardRepository;
import com.soho.inko.domain.PostCardInfoDTO;
import com.soho.inko.image.Size;
import com.soho.inko.mapper.FileMapper;
import com.soho.inko.service.ProcessedFileService;
import com.soho.inko.utils.PictureUtils;
import com.soho.inko.utils.TypeChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.UUID;

/**
 * Created by ZhongChongtao on 2017/3/25.
 */
@Component
public class PostCardConsumer implements MessageHandler {

    private final FileMapper fileMapper;
    private final PostCardRepository postCardRepository;
    private final ProcessedFileService processedFileService;
    private final FileProperties fileProperties;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public PostCardConsumer(FileMapper fileMapper, PostCardRepository postCardRepository, ProcessedFileService processedFileService, FileProperties fileProperties) {
        this.fileMapper = fileMapper;
        this.postCardRepository = postCardRepository;
        this.processedFileService = processedFileService;
        this.fileProperties = fileProperties;
    }

    @Override
    public void handler(String message) {
        PostCardInfoDTO postCardInfoDTO = JSONObject.parseObject(message, PostCardInfoDTO.class);
        String filePathByFileId = fileMapper.getFilePathByFileId(postCardInfoDTO.getPostCardFileId());
        String extension = filePathByFileId.substring(filePathByFileId.lastIndexOf("."));
        String tmpFileName = filePathByFileId.substring(0, filePathByFileId.length() - extension.length()) + "_tmp_" + UUID.randomUUID().toString();
        String tmpFilePath = tmpFileName + extension;
        String pdfFilePath = tmpFileName + ".pdf";
        File file = new File(filePathByFileId);
        PostCardEntity postCardEntity = postCardRepository.findOne(postCardInfoDTO.getPostCardId());
        if (file.exists() && !TypeChecker.isNull(postCardEntity)) {
            Size resultSize = new Size(
                    postCardInfoDTO.getPictureSize().getWidth(),
                    postCardInfoDTO.getPictureSize().getHeight());
            //上下留白情况
            if (postCardInfoDTO.getCropInfo().getHeight() > 1) {//上下留白的情况
                resultSize.setHeight(resultSize.getHeight() / postCardInfoDTO.getCropInfo().getHeight());
                System.out.println(String.format("成品的高度为:%s", resultSize.getHeight()));
                System.out.println(postCardInfoDTO.getCropInfo().getTop());
                postCardInfoDTO.getCropInfo().setTop(0);
                postCardInfoDTO.getCropInfo().setHeight(1);
            }
            //重新调整尺寸
            if (postCardInfoDTO.getCropInfo().getWidth() > 1) {
                resultSize.setWidth(resultSize.getWidth() / postCardInfoDTO.getCropInfo().getWidth());
                System.out.println(String.format("成品的宽度为:%s", resultSize.getWidth()));
                System.out.println(postCardInfoDTO.getCropInfo().getLeft());
                postCardInfoDTO.getCropInfo().setLeft(0);
                postCardInfoDTO.getCropInfo().setWidth(1);
            }
            try {
                //1、旋转图片
                PictureUtils.rotatePicture(file.getAbsolutePath(), tmpFilePath, postCardInfoDTO.getCropInfo().getRotation());
                //2、根据百分比裁切图片
                PictureUtils.cutImageByPercent(
                        tmpFilePath,
                        postCardInfoDTO.getCropInfo().getLeft(),
                        postCardInfoDTO.getCropInfo().getTop(),
                        postCardInfoDTO.getCropInfo().getWidth() + postCardInfoDTO.getCropInfo().getLeft(),
                        postCardInfoDTO.getCropInfo().getHeight() + postCardInfoDTO.getCropInfo().getTop()
                );
                //3、根据像素缩放图片
                PictureUtils.zoomPicture(
                        tmpFilePath,
                        (int) (resultSize.getWidth() * 11.8),
                        (int) (resultSize.getHeight() * 11.8));
                //4、判断PDF文件是否存在，不存在，创建新的文件
                File pdfFile = new File(pdfFilePath);
                if (!pdfFile.exists()) {
                    if (!pdfFile.getParentFile().exists()) {
                        //noinspection ResultOfMethodCallIgnored
                        pdfFile.getParentFile().mkdirs();
                    }
                    //noinspection ResultOfMethodCallIgnored
                    pdfFile.createNewFile();
                }
                PdfWriter pdfWriter = new PdfWriter(pdfFilePath);
                PdfDocument pdfDocument = new PdfDocument(pdfWriter);
                //设置PDF默认尺寸
                pdfDocument.setDefaultPageSize(new PageSize(
                        (float) UnitTypeEnum.MILLIMETRE.convertTo(postCardInfoDTO.getProductSize().getWidth(), UnitTypeEnum.PIX),
                        (float) UnitTypeEnum.MILLIMETRE.convertTo(postCardInfoDTO.getProductSize().getHeight(), UnitTypeEnum.PIX)));
                Image image = new Image(
                        ImageDataFactory.create(tmpFilePath)
                );
                image.setAutoScale(true);
                Document document = new Document(pdfDocument);
                document.setMargins(0, 0, 0, 0);
                //水平方向的白边
                double leftWhite = 0.5 * (postCardInfoDTO.getPictureSize().getWidth() - resultSize.getWidth());
                //垂直方向的白边
                double topWhite = 0.5 * (postCardInfoDTO.getPictureSize().getHeight() - resultSize.getHeight());


                //======================================================================================================================
                //边距计算公式
                //======================================================================================================================/


                //左侧边距（水平方向的白边+location.x）
                double marginLeft = leftWhite + postCardInfoDTO.getLocation().getX();
                //上侧边距（垂直方向的白边+location.y）
                double marginTop = topWhite + postCardInfoDTO.getLocation().getY();
                //右侧边距（成品宽度-实际的宽度-左侧白边）
                double marginRight = postCardInfoDTO.getProductSize().getWidth() - (leftWhite + resultSize.getWidth()) - postCardInfoDTO.getLocation().getX();
                //右侧边距（水平方向的白边+）
                double marginBotton = postCardInfoDTO.getProductSize().getHeight() - (topWhite + resultSize.getHeight()) - postCardInfoDTO.getLocation().getY();


                document.setLeftMargin((float) UnitTypeEnum.MILLIMETRE.convertTo(marginLeft, UnitTypeEnum.PIX));
                //右侧边距
                document.setRightMargin((float) UnitTypeEnum.MILLIMETRE.convertTo(marginRight, UnitTypeEnum.PIX));
                //上面边距
                document.setTopMargin((float) UnitTypeEnum.MILLIMETRE.convertTo(marginTop, UnitTypeEnum.PIX));
                //下面边距
                document.setBottomMargin((float) UnitTypeEnum.MILLIMETRE.convertTo(marginBotton, UnitTypeEnum.PIX));
                //添加图片
                document.add(image);
                pdfDocument.close();
//                PictureUtils.zoomPicture(pdfFilePath, "D:/tmp/111.jpg", 500, 500);
                File tmpFile = new File(tmpFilePath);
                if (tmpFile.exists()) {
                    //noinspection ResultOfMethodCallIgnored
                    tmpFile.delete();
                }
                FileEntity fileEntity = processedFileService.addNewFile(pdfFile, "PostCardPDF成品", "pdf");
                if (TypeChecker.isNull(fileEntity)) {
                    logger.info("保存成品文件的时候发生异常，没有保存成功，正在尝试重新制定明信片裁切");
                    postCardEntity.setProcessStatus(PostCardProcessStatus.CREATE_SUCCESS_1);
                } else {
                    postCardEntity.setProductFileId(fileEntity.getId());
                }
                //noinspection ResultOfMethodCallIgnored
                pdfFile.delete();
                //保存文件
                postCardRepository.save(postCardEntity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(postCardInfoDTO);
            System.out.println(postCardInfoDTO.getPostCardFileId() + "文件不存在");
        }
    }
}
