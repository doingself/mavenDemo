package com.syc.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.NotFoundException;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.BasicStroke;
import java.awt.Image;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;

import static java.lang.Math.min;

@Controller
@RequestMapping("/qrcode")
public class QRCodeController {

    /**
     * 创建二维码
     *
     * @param request
     * @param response
     * @param context
     */
    @RequestMapping(value = "/create/{context}", method = {RequestMethod.POST, RequestMethod.GET})
    public void getQRcode(HttpServletRequest request, HttpServletResponse response, @PathVariable String context) {

        ServletOutputStream stream = null;
        try {
            stream = response.getOutputStream();
            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix m = writer.encode(context, BarcodeFormat.QR_CODE, 200, 200);
            MatrixToImageWriter.writeToStream(m, "png", stream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.flush();
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 创建带 logo 的二维码
     * @param str
     * @param response
     */
    @RequestMapping("/createlogo/{str}")
    public void getQRcodeLogo(@PathVariable String str, HttpServletResponse response) {

        OutputStream stream = null;
        try {

            Hashtable<EncodeHintType, Object> hashtable = new Hashtable<>();
            hashtable.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            hashtable.put(EncodeHintType.CHARACTER_SET, "utf-8");
            hashtable.put(EncodeHintType.MARGIN, 1); // 边框
            BitMatrix bitMatrix = new MultiFormatWriter()
                    .encode(str, BarcodeFormat.QR_CODE, 400, 400, hashtable);

            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            int BLACK = 0xFF000000;
            int WHITE = 0xFFFFFFFF;
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    bufferedImage.setRGB(i, j, bitMatrix.get(i, j) ? BLACK : WHITE);
                }
            }

            // logo
            File logoFile = new File("/Users/syc/Downloads/logo.png");
            Image image = ImageIO.read(logoFile);
            // 压缩
            int imgWidth = min(image.getWidth(null), width);
            int imgHeight = min(image.getHeight(null), height);
            Image compressImg = image.getScaledInstance(imgWidth, imgHeight, Image.SCALE_SMOOTH);
            BufferedImage tempBufferImg = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);
            Graphics graphics = tempBufferImg.getGraphics();
            graphics.drawImage(compressImg, 0, 0, null);
            graphics.dispose();

            // 合并 logo 和二维码
            int x = (bufferedImage.getWidth() - imgWidth) / 2;
            int y = (bufferedImage.getHeight() - imgHeight) / 2;
            Graphics2D graphics2D = bufferedImage.createGraphics();
            graphics2D.drawImage(compressImg, x, y, imgWidth, imgHeight, null);
            Shape shape = new RoundRectangle2D.Float(x, y, imgWidth, imgHeight, 6, 6);
            graphics2D.setStroke(new BasicStroke(3f));
            graphics2D.draw(shape);
            graphics2D.dispose();
            //image.flush();
            compressImg.flush();

            // file
            File saveFile = new File("/Users/syc/Downloads/qrcode.png");
            ImageIO.write(bufferedImage, "png", saveFile);
            // 解析二维码
            this.decodeQRCode(saveFile);

            // stream
            stream = response.getOutputStream();
            ImageIO.write(bufferedImage, "png", stream);

        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.flush();
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            ;
        }
    }

    /**
     * 解析二维码
     * @param file
     */
    public void decodeQRCode(File file) {
        try {
            BufferedImage image = ImageIO.read(file);
            BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            Hashtable<DecodeHintType, Object> hashtable = new Hashtable<>();
            hashtable.put(DecodeHintType.CHARACTER_SET, "utf-8");

            Result result = new MultiFormatReader().decode(bitmap, hashtable);
            String resultStr = result.getText();
            System.out.println("二维码内容: " + resultStr);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }
}
