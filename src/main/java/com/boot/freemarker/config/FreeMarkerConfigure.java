package com.boot.freemarker.config;

import java.io.IOException;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import freemarker.template.TemplateException;

/**
 * freeMarker的配置,这里是java代码实现配置, <br>
 * 其实可以在配置文件中配置.properties
 * 
 * 
 * # FREEMARKER (FreeMarkerProperties)
 * spring.freemarker.allow-request-override=false # Whether HttpServletRequest
 * attributes are allowed to override (hide) controller generated model
 * attributes of the same name. <br>
 * spring.freemarker.allow-session-override=false # Whether HttpSession
 * attributes are allowed to override (hide) controller generated model
 * attributes of the same name. <br>
 * spring.freemarker.cache=false # Whether to enable template caching.
 * 是否使用freemarker的缓存<br>
 * spring.freemarker.charset=UTF-8 # Template encoding.freemarker的编码<br>
 * spring.freemarker.check-template-location=true # Whether to check that the
 * templates location exists. <br>
 * spring.freemarker.content-type=text/html # Content-Type value. 内容类型<br>
 * spring.freemarker.enabled=true # Whether to enable MVC view resolution for
 * this technology. 是否为此技术启用MVC视图分辨率<br>
 * 
 * 
 * spring.freemarker.expose-request-attributes=false # 在与模板合并之前，是否应将所有请求属性添加到模型中
 * <br>
 * 
 * 
 * spring.freemarker.expose-session-attributes=false
 * 在与模板合并之前，是否应将所有httpsession属性添加到模型中.<br>
 * spring.freemarker.expose-spring-macro-helpers=true <br>
 * 是否公开请求上下文以供Spring的宏库使用，名称为“SpringMacroRequestContext”.<br>
 * spring.freemarker.prefer-file-system-access=true # Whether to prefer file
 * system access for template loading. File system access enables hot detection
 * of template changes. <br>
 * spring.freemarker.prefix= # 在构建URL时用于查看名称的前缀.<br>
 * 
 * spring.freemarker.request-context-attribute= # Name of the RequestContext
 * attribute for all views.<br>
 * // * spring.freemarker.settings.*= # 传递到FreeMarker配置的已知FreeMarker键. <br>
 * spring.freemarker.suffix=.ftl # Suffix that gets appended to view names when
 * building a URL.<br>
 * spring.freemarker.template-loader-path=classpath:/templates/ # 以逗号分隔的模板路径列表.
 * <br>
 * spring.freemarker.view-names= # 可解析视图名称的白名单.
 * 
 * @author zpl
 *
 */
@Configuration //如果要使用代码配置,放开注解就可以了
public class FreeMarkerConfigure {

	/**
	 * 配置视图处理器
	 * 
	 * @return
	 */
	@Bean
	public ViewResolver viewResolver() {
		FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
		resolver.setCache(false);
		resolver.setViewClass(org.springframework.web.servlet.view.freemarker.FreeMarkerView.class);
		resolver.setExposeSpringMacroHelpers(true);
		resolver.setExposeRequestAttributes(true);
		resolver.setExposeSessionAttributes(true);
		resolver.setSuffix(".ftl");// 后缀
		resolver.setContentType("text/html; charset=UTF-8");// 内容类型
		return resolver;
	}

	/**
	 * 设置freeMarker的配置
	 * 
	 * @return
	 * @throws IOException
	 * @throws TemplateException
	 */
	@Bean
	public FreeMarkerConfigurer freemarkerConfig() throws IOException, TemplateException {
		FreeMarkerConfigurationFactory factory = new FreeMarkerConfigurationFactory();
		factory.setTemplateLoaderPaths("classpath:/templates", "src/main/resources/ftl"); // 意思指定了两个地方逗号隔开
		factory.setDefaultEncoding("UTF-8");
		FreeMarkerConfigurer result = new FreeMarkerConfigurer();// freeMarker的配置类

		freemarker.template.Configuration configuration = factory.createConfiguration();
		configuration.setClassicCompatible(true);// 用于处理空值的一种模式。https://www.cnblogs.com/eastson/p/3914138.html
		result.setConfiguration(configuration);
		Properties settings = new Properties();
		settings.put("template_update_delay", "0");
		settings.put("default_encoding", "UTF-8");
		settings.put("number_format", "0.##########");
		settings.put("datetime_format", "yyyy-MM-dd HH:mm:ss");
		settings.put("classic_compatible", true);
		settings.put("template_exception_handler", "ignore");
		result.setFreemarkerSettings(settings);
		return result;
	}

}
