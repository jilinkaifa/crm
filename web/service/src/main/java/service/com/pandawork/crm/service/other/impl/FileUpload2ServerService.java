package com.pandawork.crm.service.other.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.common.util.CommonUtil;
import com.pandawork.core.framework.web.spring.fileupload.PandaworkMultipartFile;
import com.pandawork.crm.common.enums.other.FileUploadPathEnums;
import com.pandawork.crm.common.exception.CrmException;
import com.pandawork.crm.common.utils.ImageUtils;
import com.pandawork.crm.common.utils.WebConstants;
import com.pandawork.crm.service.other.FileUploadService;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

/**
 * 把文件上传到指定的服务器
 * 需要搭建额外的文件上传服务
 * 文件服务代码在本工程的upload中
 *
 * @author: zhangteng
 * @time: 2015/10/29 20:10
 **/
public class FileUpload2ServerService implements FileUploadService {

    public static final String UPLOAD_URL = "/up/upload/cp";

    @Override
    public String uploadFile(PandaworkMultipartFile file, FileUploadPathEnums pathEnums, int width, int height, String... extendPath) throws SSException {
        // 非空检查
        Assert.isNotNull(file, CrmException.UploadFileNotNull);
        Assert.isNotNull(pathEnums, CrmException.UploadPathNotNull);
        if (file.isEmpty()) {
            return "";
        }

        // 对文件名进行md5加密
        File srcFile = file.getFile();
        String fileName = CommonUtil.md5(srcFile) + System.currentTimeMillis();
        String format = CommonUtil.getExtention(file.getOriginalFilename());
        if (!Assert.isNull(format)) {
            fileName += format;
        }
        // 判断是否需要压缩
        if (!Assert.lessOrEqualZero(width)
                || !Assert.lessOrEqualZero(height)) {
            try {
                ImageUtils.compressImage(srcFile, width, height);
            } catch (Exception e) {
                LogClerk.errLog.error(e);
                throw SSException.get(CrmException.ImageCompressedFail, e);
            }
        }

        String tmpPath = pathEnums.getPath();
        if (!tmpPath.startsWith("/")) {
            tmpPath = "/" + tmpPath;
        }
        if (!tmpPath.endsWith("/")) {
            tmpPath += "/";
        }
        if (!Assert.isNull(extendPath)
                && extendPath.length != 0) {
            if (extendPath[0].startsWith("/")) {
                tmpPath += extendPath[0].substring(1);
            } else {
                tmpPath += extendPath[0];
            }
        }
        if (!tmpPath.endsWith("/")) {
            tmpPath += "/";
        }
        String path = tmpPath + fileName;
        try {
            this.upload2Server(srcFile, path);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.UploadFileFail, e);
        }
        return path;
    }

    @Override
    public String uploadFile(PandaworkMultipartFile file, FileUploadPathEnums pathEnums, String... extendPath) throws SSException {
        return this.uploadFile(file, pathEnums, 0, 0, extendPath);
    }

    private void upload2Server(File file,
                               String path) throws Exception {
        String uploadWebsite = WebConstants.uploadWebsite;
        String url = uploadWebsite + UPLOAD_URL;

        String rs = "";
        FileBody fileBody = new FileBody(file);
        StringBody pathBody = new StringBody(path, ContentType.TEXT_PLAIN);

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url);

        HttpEntity reqEntity = MultipartEntityBuilder.create().
                                addPart("item", fileBody).
                                addPart("path", pathBody).
                                build();

        httpPost.setEntity(reqEntity);

        HttpResponse response = httpClient.execute(httpPost);

        LogClerk.sysout.debug("status code:" + response.getStatusLine().getStatusCode());

        HttpEntity resEntity = response.getEntity();
        InputStream inputStream = resEntity.getContent();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        byte[] b = new byte[1024];
        int len = -1;
        while ((len = inputStream.read(b)) != -1) {
            baos.write(b, 0, len);
        }
        rs = baos.toString();

        inputStream.close();
        baos.close();

        JSONObject jsonObject = JSON.parseObject(rs);
        int statusCode = response.getStatusLine().getStatusCode();
        boolean result = jsonObject.getBoolean("rs");
        if (statusCode != 200
                || !result) {
            throw SSException.get(CrmException.UploadFileFail);
        }

    }
}
