package org.wxf.file.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.wxf.file.config.FileConfig;
import org.wxf.file.mapper.FileUploadMapper;
import org.wxf.file.model.FileUpload;
import org.wxf.file.service.FileUploadService;
import org.springframework.stereotype.Service;
import org.wxf.file.tools.FileTools;
import org.wxf.file.tools.GeneralTools;
import org.wxf.file.vo.DownloadVo;
import org.wxf.file.vo.UploadVo;

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

	@Resource
	FileTools fileTools;


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

				response.setContentType(contentType);
				response.setHeader("content-disposition", "inline; filename="
						+ new String(fileName.getBytes("UTF-8"), "ISO8859_1"));

				ServletOutputStream servletoutputstream =
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

	@Override
	public String fileUpload(MultipartFile[] files, UploadVo uploadVo) {
		if (files == null || files.length <= 0) {
			return "";
		}

		try {
			//获取文件名
			String fileName = files[0].getOriginalFilename();
			if (fileName.equals("")) {
				return "没有文件上传!";
			}
			//去除空格
			fileName = fileName.trim();
			String fileText = "";
			//用于编译正则表达式
			Pattern p1 = Pattern.compile("(.*)\\.([^\\.]*)");
			Matcher m = p1.matcher(fileName);
			if (m.find()) {
				fileText = m.group(2);
			}
			//自定义路径
			String path = fileTools.aGetCreateDir();
			String pathAll = fileConfig.filepath + path;
			//创建文件路径
			fileTools.aCreateDir(pathAll);
			//随机文件名称
			String newFilename = fileTools.aGetRandom() + ".zip";

			File file = null;

			try {
				file = new File(pathAll, newFilename);
			} catch (Exception ex) {
				logger.error("upload", ex);
				return "文件上传失败!";
			}

			file = fileTools.aPolicyRename(file);
			try {
				newFilename = file.getName();
			} catch (Exception ex) {
				logger.error("upload", ex);
				return "文件上传失败!";
			}

			OutputStream obj = null;
			InputStream is = files[0].getInputStream();
			try {
				ZipOutputStream zipoutputstream = new ZipOutputStream(
						new BufferedOutputStream(new FileOutputStream(file)));
				zipoutputstream.setMethod(8);
				zipoutputstream.putNextEntry(new ZipEntry("file." + fileText));
				obj = zipoutputstream;

				byte[] b = new byte[1024];
				int bl = 0;
				while ((bl = is.read(b)) >= 0) {
					if (bl > 0) {
						obj.write(b, 0, bl);
					}
				}
			} catch (Exception ioexception) {
			} finally {
				try {
					if (obj != null) {
						obj.close();
					}
				} catch (Exception ioexception) {
				}
			}
			FileUpload fileUpload = GeneralTools.dtoTransfer(uploadVo, FileUpload.class);
			fileUpload.setFilePath(path + newFilename);
			fileUpload.setFileName(fileName);
			fileUpload.setContentType(fileConfig.getContentType("." + fileText));

			fileUploadMapper.insert(fileUpload);

			return String.valueOf(fileUpload.getId());
		} catch (Exception ex) {
			logger.error("upload", ex);
			return "文件上传失败!";
		}

		//return "";
	}
}
