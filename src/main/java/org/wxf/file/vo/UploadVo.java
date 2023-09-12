package org.wxf.file.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author wangxiaofei
 * @since 2023-09-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "FileUpload对象", description = "")
public class UploadVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String fileName;

	private String type;

	private String dispName;

	private String contentType;

	private Long userId;

	private Long companyId;

}
