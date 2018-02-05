package com.pandawork.crm.service.other;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.framework.web.spring.fileupload.PandaworkMultipartFile;
import com.pandawork.crm.common.enums.other.FileUploadPathEnums;

/**
 * 文件上传服务
 *
 * @author: zhangteng
 * @time: 2015/10/29 8:56
 **/
public interface FileUploadService {

    /**
     * 上传文件
     *
     * @param file          待上传的文件
     * @param pathEnums     上传文件存放的路径
     * @param extendPath    额外的上传路径
     *                      最后的上传路径=FileUploadPathEnums.path + extendPath
     * @return              文件上传完之后的路径
     * @throws SSException
     */
    public String uploadFile(PandaworkMultipartFile file,
                             FileUploadPathEnums pathEnums,
                             String... extendPath) throws SSException;

    /**
     * 上传文件
     *
     * @param file      待上传的文件
     * @param pathEnums 上传文件存放的路径
     * @param width     压缩之后的宽度
     * @param height    压缩之后的高度
     * @param extendPath
     * @return          文件上传之后的路径
     * @throws SSException
     */
    public String uploadFile(PandaworkMultipartFile file,
                             FileUploadPathEnums pathEnums,
                             int width,
                             int height,
                             String... extendPath) throws SSException;
}
