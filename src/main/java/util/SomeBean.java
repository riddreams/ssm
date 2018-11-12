package util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * @author lwyan on 2018-05-25 16:03
 */
@Configuration
public class SomeBean {
	// 文件上传解析器bean
	@Bean
	public MultipartResolver multipartResolver(){
		return new CommonsMultipartResolver();
	}
}