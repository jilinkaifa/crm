package com.pandawork.crm.web.controller.admin.event;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.crm.common.annotation.Module;
import com.pandawork.crm.common.entity.event.EventAttachment;
import com.pandawork.crm.common.enums.other.ModuleEnums;
import com.pandawork.crm.common.utils.URLConstants;
import com.pandawork.crm.web.spring.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

import static com.pandawork.crm.common.utils.CommonUtil.uuid;

/**
 * FileController
 * Author： wychen
 * Date: 2017/8/9
 * Time: 13:30
 */
@Controller
@Module(ModuleEnums.AdminECRBEventPrepare)
@RequestMapping(value = URLConstants.ADMIN_FILE)
public class FileController extends AbstractController{

    /**
     * ajax 上传文件
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "ajax/upload",method = RequestMethod.POST)
    @ResponseBody
    public JSON ajaxUpload(HttpServletRequest request) throws Exception {
        String UPLOAD_FILE_PATH = request.getSession().getServletContext().getRealPath("") + "/";
        JSONObject json = new JSONObject();
        try {
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                    request.getSession().getServletContext());
            if (multipartResolver.isMultipart(request)) {
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
                List<MultipartFile> fileList = multiRequest.getFiles("attachment");
                if (fileList.size() > 0) {
                    for (MultipartFile file : fileList) {
                        EventAttachment eventAttachment = new EventAttachment();
                        if (file != null) {
                            String fileName = file.getOriginalFilename();
                            String suffix =  "." + fileName.substring(fileName.lastIndexOf(".") + 1);
                            if (fileName.trim() != "") {
                                String uuid = uuid();
                                String filePath = UPLOAD_FILE_PATH + uuid + suffix ;
                                File localFile = new File(filePath);
                                file.transferTo(localFile);
                                eventAttachment.setName(fileName);
                                eventAttachment.setPath(uuid + suffix);
                                eventAttachment.setSize(file.getSize());
                                eventAttachment = eventAttachmentService.newAttachment(eventAttachment);
                            }
                            json.put("attachment", eventAttachment);
                            json.put("code", AJAX_SUCCESS_CODE);
                        }
                    }
                }
            } else {
                json.put("code", AJAX_FAILURE_CODE);
            }
        }catch (Exception e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
        }
        return json;
    }


    /**
     * ajax 文件下载
     *
     * @param fileId
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "ajax/download", method = RequestMethod.GET)
    public void ajaxDownload(@RequestParam("id") Integer fileId,
                             HttpServletRequest request,
                             HttpServletResponse response) throws Exception {
        String UPLOAD_FILE_PATH = request.getSession().getServletContext().getRealPath("") + "/";
        EventAttachment eventAttachment = new EventAttachment();
        try {
            eventAttachment = eventAttachmentService.queryById(fileId);
            if (eventAttachment == null) {
               throw new Exception("文件不存在！");
            } else {
                File proposeFile = new File(UPLOAD_FILE_PATH + eventAttachment.getPath());
                InputStream inputStream = null;
                OutputStream bufferOut = null;
                try {
                    // 设置响应报头
                    long fSize = proposeFile.length();
                    response.setContentType("application/x-download");
                    response.setCharacterEncoding("utf-8");
                    response.addHeader("Content-Disposition", "attachment; filename=" + new String(proposeFile.getName().getBytes("gbk"), "iso-8859-1"));
                    response.setHeader("Accept-Ranges", "bytes");
                    long pos = 0, last = fSize - 1, sum = 0;
                    if (null != request.getHeader("Range")) {
                        // 断点续传
                        response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
                        try {
                            String numRang = request.getHeader("Range").replaceAll("bytes=", "");
                            String[] strRange = numRang.split("-");
                            if (strRange.length == 2) {
                                pos = Long.parseLong(strRange[0].trim());
                                last = Long.parseLong(strRange[1].trim());
                            } else {
                                pos = Long.parseLong(numRang.replaceAll("-", "").trim());
                            }
                        } catch (NumberFormatException e) {
                            pos = 0;
                        }
                    }
                    long rangLength = last - pos + 1;// 总共需要读取的字节
                    String contentRange = new StringBuffer("bytes ").append(pos).append("-").append(last).append("/").append(fSize).toString();
                    response.setHeader("Content-Range", contentRange);
                    response.addHeader("Content-Length", String.valueOf(rangLength));

                    bufferOut = new BufferedOutputStream(response.getOutputStream());
                    inputStream = new BufferedInputStream(new FileInputStream(proposeFile));
                    inputStream.skip(pos);
                    byte[] buffer = new byte[1024];
                    int length = 0;
                    while (sum < rangLength) {
                        length = inputStream.read(buffer, 0, ((rangLength - sum) <= buffer.length ? ((int) (rangLength - sum)) : buffer.length));
                        sum = sum + length;
                        bufferOut.write(buffer, 0, length);
                    }
                } catch (Throwable e) {
//                    if (e instanceof ClientAbortException) {
//                        // 浏览器点击取消
//                        LOGGER.info("用户取消下载!");
//                    } else {
//                        LOGGER.info("下载文件失败....");
//                        e.printStackTrace();
//                    }
                } finally {
                    try {
                        if (bufferOut != null) {
                            bufferOut.close();
                        }
                        if (inputStream != null) {
                            inputStream.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
        }
    }

}
