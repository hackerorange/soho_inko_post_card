package com.soho.inko.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by ZhongChongtao on 2017/3/11.
 */
@Configuration
@ConfigurationProperties(prefix = "soho.file")
public class FileProperties {
	private String filePathTemplate = "D:/files/#YEAR/#MONTH/#DAY/#FILE_NAME";
	private String tmpFilePathTemplate = "D:/tmpFiles/#FILE_NAME_#ROTATION";

	public String getTmpFilePathTemplate() {
		return tmpFilePathTemplate;
	}

	public void setTmpFilePathTemplate(String tmpFilePathTemplate) {
		this.tmpFilePathTemplate = tmpFilePathTemplate;
	}

	public String getFilePathTemplate() {
		return filePathTemplate;
	}
	public void setFilePathTemplate(String filePathTemplate) {
		this.filePathTemplate = filePathTemplate;
	}
}
