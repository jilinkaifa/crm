package com.pandawork.crm.common.utils;

import com.alibaba.simpleimage.ImageRender;
import com.alibaba.simpleimage.SimpleImageException;
import com.alibaba.simpleimage.render.ReadRender;
import com.alibaba.simpleimage.render.ScaleParameter;
import com.alibaba.simpleimage.render.ScaleRender;
import com.alibaba.simpleimage.render.WriteRender;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import org.apache.commons.io.IOUtils;

import java.io.*;

/**
 * ImageUtils
 *
 * @author: zhangteng
 * @time: 2015/10/28 22:20
 **/
public final class ImageUtils {

    public static void compressImage(File file, int width, int height) throws Exception {
        if (Assert.lessOrEqualZero(width)
                && Assert.lessOrEqualZero(height)) {
            return ;
        }
        // 将图像缩略到1024x1024以内，不足1024x1024则不做任何处理
        ScaleParameter scaleParameter = new ScaleParameter(width, height, ScaleParameter.Algorithm.LANCZOS);
        InputStream is = null;
        ByteArrayInputStream bais = null;
        ByteArrayOutputStream os = null;
        WriteRender writeRender = null;
        try {
            is = new FileInputStream(file);
            os = new ByteArrayOutputStream();
            ImageRender imageRender = new ReadRender(is);
            ImageRender scaleRender = new ScaleRender(imageRender, scaleParameter);
            writeRender = new WriteRender(scaleRender, os);
            // 压缩
            writeRender.render();
            // 写回原文件
            bais = new ByteArrayInputStream(os.toByteArray());
            com.pandawork.core.common.util.CommonUtil.copyStreamToFile(bais, file);
        } catch (Exception e) {
            throw e;
        } finally {
            IOUtils.closeQuietly(is);
            IOUtils.closeQuietly(bais);
            IOUtils.closeQuietly(os);
            if (writeRender != null) {
                try {
                    writeRender.dispose();
                } catch (SimpleImageException e) {
                    LogClerk.errLog.error(e);
                    // ignore
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        File file = new File("F:\\tmp\\test3.jpg");
        System.out.println();
        compressImage(file, 800, 600);
    }
}
