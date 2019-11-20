package com.andy.demo.shorturl.component;

import com.andy.demo.shorturl.config.BarCodeInfo;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.UUID;

/**
 * Description: 条形码工具
 * Author: Andy.wang
 * Date: 2019/11/20 11:09
 */
@Component
public class BarCodeComponent {
    @Autowired
    private BarCodeInfo barCodeInfo;

    /**
     * 生成条形码，文件名称是 fileName，返回条形码文件的绝对路径
     *
     * @param content  二维码文本内容
     * @param destPath 目标文件路径（保存二维码的路径）
     * @param fileName 二维码文件名称（为空，则取UUID值）
     * @param showText 是否显示文本
     * @return
     * @throws Exception
     */
    public String barCode(String content, String destPath, String fileName, boolean showText) throws Exception {
        if (StringUtils.isEmpty(content)) {
            throw new Exception("文本内容为空!");
        }

        BufferedImage image = getBarCode(content, showText);
        mkdirs(destPath);

        String pictureFormat = barCodeInfo.getPictureFormat();

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
     * 生成 图片缓冲
     *
     * @param vaNumber VA 码
     * @return 返回BufferedImage
     * @author fxbin
     */
    private BufferedImage getBarCode(String vaNumber, boolean showText) throws WriterException {
        Code128Writer writer = new Code128Writer();

        Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
        hints.put(EncodeHintType.MARGIN, 1);
        hints.put(EncodeHintType.CHARACTER_SET, barCodeInfo.getCharset());
        // 容错级别 这里选择最高H级别
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

        // 编码内容, 编码类型, 宽度, 高度, 设置参数
        BitMatrix bitMatrix = writer.encode(vaNumber, BarcodeFormat.CODE_128,
                barCodeInfo.getWidth(), barCodeInfo.getHeight(), hints);

        BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
        if (showText) {
            return insertWords(image, vaNumber);
        }
        return image;
    }

    /**
     * 把带logo的二维码下面加上文字
     *
     * @param image 条形码图片
     * @param words 文字
     * @return 返回BufferedImage
     * @author fxbin
     */
    private BufferedImage insertWords(BufferedImage image, String words) {
        // 新的图片，把带logo的二维码下面加上文字
        if (!StringUtils.isEmpty(words)) {

            BufferedImage outImage = new BufferedImage(barCodeInfo.getWidth(),
                    barCodeInfo.getWordHeight(), BufferedImage.TYPE_INT_RGB);

            Graphics2D g2d = outImage.createGraphics();

            // 抗锯齿
            setGraphics2D(g2d);
            // 设置白色
            setColorWhite(g2d);

            // 画条形码到新的面板
            g2d.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
            // 画文字到新的面板
            Color color = new Color(0, 0, 0);
            g2d.setColor(color);
            // 字体、字型、字号
            g2d.setFont(new Font("微软雅黑", Font.PLAIN, 18));
            //文字长度
            int strWidth = g2d.getFontMetrics().stringWidth(words);
            //总长度减去文字长度的一半  （居中显示）
            int wordStartX = (barCodeInfo.getWidth() - strWidth) / 2;
            //height + (outImage.getHeight() - height) / 2 + 12
            int wordStartY = barCodeInfo.getHeight() + 20;

            // 画文字
            g2d.drawString(words, wordStartX, wordStartY);
            g2d.dispose();
            outImage.flush();
            return outImage;
        }
        return null;
    }

    /**
     * 设置 Graphics2D 属性  （抗锯齿）
     *
     * @param g2d Graphics2D提供对几何形状、坐标转换、颜色管理和文本布局更为复杂的控制
     */
    private void setGraphics2D(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_DEFAULT);
        Stroke s = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
        g2d.setStroke(s);
    }

    /**
     * 设置背景为白色
     *
     * @param g2d Graphics2D提供对几何形状、坐标转换、颜色管理和文本布局更为复杂的控制
     */
    private void setColorWhite(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        //填充整个屏幕
        g2d.fillRect(0, 0, 600, 600);
        //设置笔刷
        g2d.setColor(Color.BLACK);
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

