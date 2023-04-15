package com.baidu.travel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Swagger2配置类
 *
 * @author zhoubin
 * @since 1.0.0
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {
    //规定哪些包下面扫描swagger 文档
    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)//设置文档类型
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.baidu.travel.controller"))//设置要扫描的包
                .paths(PathSelectors.any())//设置路径
                .build();
				/*.securityContexts(securityContexts())
				.securitySchemes(securitySchemes());*/
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("travel-zxc接口文档")// 标题
                .description("travel-zxc接口文档")// 描述
                .contact(new Contact("zxc","http:localhost:8888/doc.html","1954509371@xxxx.com"))//作者 url  邮箱
                .version("1.0")
                .build();
    }


	/*private List<ApiKey> securitySchemes(){
		//设置请求头信息
		List<ApiKey> result= new ArrayList<>();
		ApiKey apiKey = new ApiKey("Authorization","Authorization","Header");
		result.add(apiKey);
		return result;
	}

	private List<SecurityContext> securityContexts(){
		//设置需要登录认证的路径
		List<SecurityContext> result = new ArrayList<>();
		result.add(getContextByPath("/hello/.*"));
		return result;
	}*/

	/*private SecurityContext getContextByPath(String pathRegex) {
		return SecurityContext.builder()
				.securityReferences(defaultAuth())
				.forPaths(PathSelectors.regex(pathRegex))
				.build();
	}*/

	/*private List<SecurityReference> defaultAuth() {
		List<SecurityReference> result = new ArrayList<>();
		AuthorizationScope authorizationScope = new AuthorizationScope("global","accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		result.add(new SecurityReference("Authorization",authorizationScopes));
		return result;
	}*/

}