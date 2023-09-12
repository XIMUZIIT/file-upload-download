package org.wxf.file;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;


/**
 * @author wxf
 * @date 2023-09-12
 */

@SpringBootApplication
@MapperScan(basePackages = {"org.wxf.file.mapper"})
@EnableFeignClients(basePackages = {"org.wxf.file.feign"})
@ComponentScan(value = {"org.wxf"})
public class FileApp {

	public static void main(String[] args) {
		SpringApplication.run(FileApp.class, args);
	}
}
