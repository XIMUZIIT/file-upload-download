package org.wxf.file.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

/**
 * 文件下载实体对象
 *
 * @author wangxiaofei
 * @since 2023-09-12
 */
@Data
@ApiModel(value = "文件下载入参对象", description = "")
public class DownloadVo implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "id")
	private Long id;

}
