package org.wxf.file.service;

import com.baomidou.mybatisplus.extension.service.IService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.wxf.file.model.FileUpload;
import org.wxf.file.vo.DownloadVo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wangxiaofei
 * @since 2023-09-12
 */
public interface FileUploadService extends IService<FileUpload> {


	void fileDownload(DownloadVo downloadVo, HttpServletResponse response);

}
