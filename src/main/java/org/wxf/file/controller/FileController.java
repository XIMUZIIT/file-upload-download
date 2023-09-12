package org.wxf.file.controller;

import java.util.Objects;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.wxf.file.service.FileUploadService;
import org.wxf.file.vo.DownloadVo;

@RestController
public class FileController {

	private static final Logger logger = LoggerFactory.getLogger(FileController.class);


	@Resource
	FileUploadService fileUploadService;


	@RequestMapping(value = "/download/file", method = RequestMethod.GET)
	public void download(DownloadVo downloadVo, HttpServletResponse response) {

		if (Objects.isNull(downloadVo) || Objects.isNull(downloadVo.getId())) {
			logger.error("参数异常：" + downloadVo.getId());
		}
		fileUploadService.fileDownload(downloadVo, response);

	}

}
