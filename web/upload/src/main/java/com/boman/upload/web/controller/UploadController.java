package com.boman.upload.web.controller;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.sytem.SystemInstance;
import com.pandawork.core.common.util.CommonUtil;
import com.pandawork.core.framework.bean.StaticAutoWire;
import com.pandawork.core.framework.fileUpload.FileUploadProcessor;
import com.pandawork.core.framework.fileUpload.UploadFile;
import com.pandawork.core.framework.web.spring.controller.Base;
import com.pandawork.core.framework.web.spring.fileupload.PandaworkMultipartFile;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

@Controller
@RequestMapping(value = "/up")
public class UploadController extends Base {
	private final static String defaultPathKey = "defaultPathKey";
	
	public void init(HttpServletRequest request, HttpServletResponse response){
    	super.init(request, response);
    	
    	defaultPath = (String) SystemInstance.getIntance().getPropertie(defaultPathKey);
    }
	

	@StaticAutoWire
	@Qualifier("defaultFileUpload")
	private static FileUploadProcessor defaultFileUpload;

	private static String defaultPath = "";

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return "/test/home";
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public @ResponseBody
	JSONObject upload(@RequestParam("item") PandaworkMultipartFile file,
			String path, String key) {
		JSONObject rs = new JSONObject();
		String backPath = "";

		// 创建一个文件上传对象
		UploadFile uploadFile = new UploadFile(file.getFile(), defaultPath,
				path, file.getOriginalFilename(), getRequest());

		try {
			// 默认的文件上传
			backPath = defaultFileUpload.uploadOneFile(uploadFile);
            System.out.println(backPath);
            rs.put("rs", true);
			rs.put("path", backPath);
		} catch (SSException e) {
			LogClerk.errLog.error(e);
			rs.put("rs", false);
		}
		return rs;
	}

	@RequestMapping(value = "/upload/cp", method = RequestMethod.POST)
	public @ResponseBody
	JSONObject uploadCP(@RequestParam("item") PandaworkMultipartFile file,
			String path, String key) {
		JSONObject rs = new JSONObject();
		String backPath = "";

		// 创建文件目录
		String dirpath = defaultPath + path;
		File dir = new File(dirpath);

		boolean mkdirSuccess = true;
		if (!dir.isDirectory()) {
			// 创建目录
			mkdirSuccess = dir.mkdirs();
		}

		String targetPath = defaultPath + path;
		LogClerk.sysout.debug("fileUpload targetPath:" + targetPath);
		File target = new File(targetPath);
		if (target.exists()) {
			target.delete();
		}
		try {
			target.createNewFile();

			CommonUtil.copyFile(file.getFile(), target);
			// 默认的文件上传
			rs.put("rs", true);
			rs.put("path", path);
		} catch (Exception e) {
			LogClerk.errLog.error(e);
			rs.put("rs", false);
		}
		return rs;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public @ResponseBody
	JSONArray listDir(String path, String key) {
		JSONArray arr = new JSONArray();

		// 创建文件目录
		String dirpath = defaultPath + path;
		File dir = new File(dirpath);

		if (!dir.isDirectory()) {
			return arr;
		}

		for (File f : dir.listFiles()) {
			JSONObject obj = new JSONObject();
			String backPath = path + f.getName();
			obj.put("path", backPath);
			obj.put("name", f.getName());
			obj.put("rs", true);
			
			arr.add(obj);
		}
		return arr;
	}
}
