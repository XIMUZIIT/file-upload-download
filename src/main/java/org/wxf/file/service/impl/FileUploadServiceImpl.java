package org.wxf.file.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;
import org.wxf.file.config.FileConfig;
import org.wxf.file.mapper.FileUploadMapper;
import org.wxf.file.model.FileUpload;
import org.wxf.file.service.FileUploadService;
import org.springframework.stereotype.Service;
import org.wxf.file.vo.DownloadVo;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wangxiaofei
 * @since 2023-09-12
 */
@Service
public class FileUploadServiceImpl extends ServiceImpl<FileUploadMapper, FileUpload>
		implements FileUploadService {

	private static final Logger logger = LoggerFactory.getLogger(FileUploadServiceImpl.class);


	@Resource
	FileUploadMapper fileUploadMapper;

	@Resource
	FileConfig fileConfig;


	@Override
	public void fileDownload(DownloadVo downloadVo, HttpServletResponse response) {
		//查询文件
		LambdaQueryWrapper<FileUpload> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(FileUpload::getId, downloadVo.getId());
		FileUpload fileUpload = fileUploadMapper.selectOne(queryWrapper);
		if (!ObjectUtils.isEmpty(fileUpload)) {

		}

		try {

			String fileName = fileUpload.getFileName();
			String filePath = fileUpload.getFilePath();
			String contentType = fileUpload.getContentType();

			byte abyte0[] = new byte[1024];
			BufferedInputStream bufferedinputstream = null;

			File file = new File(fileConfig.filepath + filePath);
			ZipInputStream zipinputstream = new ZipInputStream(new FileInputStream(file));
			ZipEntry zipEntry = zipinputstream.getNextEntry();
			if (zipEntry != null) {
				bufferedinputstream = new BufferedInputStream(zipinputstream);
			}

			if (bufferedinputstream != null) {
				try {
					response.setContentType(contentType);
					response.setHeader("content-disposition", "inline; filename="
							+ new String(fileName.getBytes("UTF-8"), "ISO8859_1"));
				} catch (Exception exception1) {
				}

				javax.servlet.ServletOutputStream servletoutputstream =
						response.getOutputStream();
				int i;
				int l = 0;
				while ((i = bufferedinputstream.read(abyte0)) != -1) {
					servletoutputstream.write(abyte0, 0, i);
					l++;
					if (l >= 100) {
						servletoutputstream.flush();
						l = 0;
					}
				}
				if (l > 0) {
					servletoutputstream.flush();
				}
				bufferedinputstream.close();
				servletoutputstream.close();
				return;
			}
		} catch (Exception e) {
			logger.error("文件下载异常：" + e);
		}


	}
}
