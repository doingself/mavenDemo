package com.syc.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/qrcode")
public class QRCodeController {

    /**
     * 创建二维码
     */
    @RequestMapping(value = "/create/{str}",method={RequestMethod.POST,RequestMethod.GET})
    public void getQRcode(HttpServletRequest request, HttpServletResponse response, @PathVariable String str){

        ServletOutputStream stream = null;
        try {
            stream = response.getOutputStream();
            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix m = writer.encode(str, BarcodeFormat.QR_CODE, 200, 200);
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
}
