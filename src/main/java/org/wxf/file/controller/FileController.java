package org.wxf.file.controller;

import java.util.Objects;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.wxf.file.service.FileUploadService;
import org.wxf.file.vo.DownloadVo;
import org.wxf.file.vo.UploadVo;


@RestController
public class FileController {

	private static final Logger logger = LoggerFactory.getLogger(FileController.class);


	@Resource
	FileUploadService fileUploadService;


	@RequestMapping(value = "/download/file", method = RequestMethod.GET)
	public void download(DownloadVo downloadVo, HttpServletResponse response) {

		if (Objects.isNull(downloadVo) || Objects.isNull(downloadVo.getId())) {
			logger.error("参数异常：" + downloadVo);
		}
		fileUploadService.fileDownload(downloadVo, response);

	}


	@PostMapping("/upload/file")
	public String upload(@RequestParam("file") MultipartFile[] files, UploadVo uploadVo) {

		return fileUploadService.fileUpload(files, uploadVo);
	}

}
