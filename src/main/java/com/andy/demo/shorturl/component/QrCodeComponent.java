package com.andy.demo.shorturl.component;

import com.andy.demo.shorturl.config.QrCodeInfo;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.UUID;

/**
 * Description: 二维码工具
 * Author: Andy.wang
 * Date: 2019/11/20 11:07
 */
@Component
public class QrCodeComponent {
    @Autowired
    private QrCodeInfo qrcodeInfo;

    /**
     * 生成二维码，文件名称是 fileName，返回二维码文件的绝对路径
     *
     * @param content  二维码文本内容
     * @param logoPath 内嵌logo图片路径（为null则说明 没有logo）
     * @param destPath 目标文件路径（保存二维码的路径）
     * @param fileName 二维码文件名称（为空，则取UUID值）
     * @return 二维码文件的绝对路径
     * @throws Exception
     */
    public String qrCode(String content, String logoPath, String destPath, String fileName) throws Exception {
        if (StringUtils.isEmpty(content)) {
            throw new Exception("文本内容为空!");
        }

        BufferedImage image = createImage(content, logoPath);
        mkdirs(destPath);

        String pictureFormat = qrcodeInfo.getPictureFormat();

        // fileName 为空，则取UUID值
        if (StringUtils.isEmpty(fileName)) {
            fileName = UUID.randomUUID().toString() + "." + pictureFormat;
        } else {
            fileName = fileName.substring(0, fileName.indexOf(".") > 0 ? fileName.indexOf(".") : fileName.length()) + "." + pictureFormat;
        }

        File qrFile = new File(destPath + File.separator + fileName);
        ImageIO.write(image, pictureFormat, qrFile);

        return qrFile.getAbsolutePath();
    }

    /**
     * 生成二维码图片
     *
     * @param content  文本内容
     * @param logoPath 内嵌logo图片路径（为null则说明 没有logo）
     * @return 生成的二维码的BufferedImage对象
     * @throws Exception
     */
    private BufferedImage createImage(String content, String logoPath) throws Exception {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
        hints.put(EncodeHintType.MARGIN, 1);
        hints.put(EncodeHintType.CHARACTER_SET, qrcodeInfo.getCharset());
        // 容错级别 这里选择最高H级别
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

        // 画二维码图片
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE,
                qrcodeInfo.getQrCodeSize(), qrcodeInfo.getQrCodeSize(), hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }

        // 如果 logo 不为空，则插入logo图片
        if (!StringUtils.isEmpty(logoPath)) {
            // 插入Logo图片
            insertImage(image, logoPath);
        }
        return image;
    }

    /**
     * 向生成的二维码BufferedImage对象中 插入logo图片
     *
     * @param source   生成的二维码的BufferedImage对象
     * @param logoPath 内嵌logo图片路径
     * @throws Exception
     */
    private void insertImage(BufferedImage source, String logoPath) throws Exception {
        Resource resource = new ClassPathResource(logoPath);
        File file = resource.getFile();
        if (!file.exists()) {
            throw new Exception("not found logo.jpg int classPath(/logo/logo.jpg).");
        }

        // 读 logo图片
        Image logoImage = ImageIO.read(file);
        int width = logoImage.getWidth(null);
        int height = logoImage.getHeight(null);

        // 压缩logo
        if (width > qrcodeInfo.getLogoWidth()) {
            width = qrcodeInfo.getLogoWidth();
        }
        if (height > qrcodeInfo.getLogoHeight()) {
            height = qrcodeInfo.getLogoHeight();
        }
        Image image = logoImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = tag.getGraphics();
        g.drawImage(image, 0, 0, null);

        // Disposes of this graphics context and releases any system resources that it is using.
        // A Graphics object cannot be used after dispose has been called.
        g.dispose();
        logoImage = image;

        // 插入logo到二维码的BufferedImage对象中
        Graphics2D graph = source.createGraphics();
        int x = (qrcodeInfo.getQrCodeSize() - width) / 2;
        int y = (qrcodeInfo.getQrCodeSize() - height) / 2;
        graph.drawImage(logoImage, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 5, 5);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }

    /**
     * 创建文件夹
     *
     * @param destPath 目标文件路径（保存二维码的路径）
     * @throws IOException
     */
    private static void mkdirs(String destPath) throws IOException {
        File file = new File(destPath);
        FileUtils.forceMkdir(file);
    }
}
