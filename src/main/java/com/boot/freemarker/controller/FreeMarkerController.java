package com.boot.freemarker.controller;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.itextpdf.text.pdf.BaseFont;

import freemarker.template.Configuration;
import freemarker.template.Template;
import sun.misc.BASE64Encoder;

/**
 * freemarker <br>
 * 不能用@RestController,返回的是页面,不是数据字符串,所以我们需要使用@Controller
 * 
 * @author zpl
 *
 */
@SuppressWarnings("restriction")
// @RestController
@Controller
@RequestMapping("ftl")
public class FreeMarkerController {

	@Resource
	private FreeMarkerConfigurer freeMarkerConfigurer;

	@RequestMapping("/index.json")
	public String index(ModelMap map) {
		map.put("name", "张三");
		map.put("sex", 2);
		map.put("func", "<b>ABNCD</b>");
		InputStream is = this.getClass().getClassLoader().getSystemResourceAsStream("img/baobao.jpg");
		String img = covertPicToBase64(is);
		InputStream ap = this.getClass().getClassLoader().getSystemResourceAsStream("img/apollo.png");
		String apollo = covertPicToBase64(ap);
		InputStream aim = this.getClass().getClassLoader().getSystemResourceAsStream("img/aimsen.jpg");
		String aimsen = covertPicToBase64(aim);
		InputStream he = this.getClass().getClassLoader().getSystemResourceAsStream("img/img_head.png");
		String head = covertPicToBase64(he);
		map.put("apollo", apollo);
		map.put("aimsen", aimsen);
		map.put("head", head);
		map.put("img", img);
		return "/index";
	}

	@RequestMapping("/down.json")
	public void downPdf(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelMap map = new ModelMap();
		map.put("name", "张三");
		map.put("sex", 2);
		map.put("func", "<h5>ABNCD</h5>");
		String filePath = "/index.ftl";
		fillAnlle(map);
		// fillwalre(map);
		// fillaimsen(map);
		ExportPdfUtil2(response, map, filePath, URLEncoder.encode("测试", "UTF-8") + new Date().getTime() + ".pdf");
	}

	private void fillAnlle(ModelMap map) {
		fillAnlleImage(map, "img/anlle.jpg", "anlle");
		fillAnlleImage(map, "img/base_title.png", "title_base");
		fillAnlleImage(map, "img/edu_title.png", "edu_title");
		fillAnlleImage(map, "img/img_head.png", "img_head");
		fillAnlleImage(map, "img/img_square.png", "img_square");
		fillAnlleImage(map, "img/language_title.png", "language_title");
		fillAnlleImage(map, "img/present_title.png", "present_title");
		fillAnlleImage(map, "img/pro_title.png", "pro_title");
		fillAnlleImage(map, "img/top_title.png", "top_title");
		fillAnlleImage(map, "img/train_title.png", "train_title");
		fillAnlleImage(map, "img/vista.png", "vista");
		fillAnlleImage(map, "img/work_title.png", "work_title");
	}

	private void fillaimsen(ModelMap map) {
		fillAnlleImage(map, "imgs/project.png", "projects");
		fillAnlleImage(map, "imgs/11.png", "11");
		fillAnlleImage(map, "imgs/aimsen.jpg", "aimsen");
		fillAnlleImage(map, "imgs/apollo.png", "apollo");
		fillAnlleImage(map, "imgs/bg_base.png", "bg_base");
		fillAnlleImage(map, "imgs/bg_header.png", "header");
		fillAnlleImage(map, "imgs/bg_title.png", "title");
		fillAnlleImage(map, "imgs/img_head.png", "img_head");
		fillAnlleImage(map, "imgs/img_square.png", "square");
		fillAnlleImage(map, "imgs/title_language.png", "language");
		fillAnlleImage(map, "imgs/title_project.png", "project");
		fillAnlleImage(map, "imgs/title_train.png", "train");
		fillAnlleImage(map, "imgs/title_work.png", "work");
		fillAnlleImage(map, "imgs/title-edu.png", "edu");
	}

	private void fillwalre(ModelMap map) {
		fillAnlleImage(map, "imgas/bg_base.png", "bg_base");
		fillAnlleImage(map, "imgas/img_head.png", "img_head");
		fillAnlleImage(map, "imgas/img_square.png", "img_square");
		fillAnlleImage(map, "imgas/img_xiaochengxu.png", "img_xiaochengxu");
		fillAnlleImage(map, "imgas/lotus.png", "lotus");
		fillAnlleImage(map, "imgas/title_act.png", "title_act");
		fillAnlleImage(map, "imgas/title_base.png", "title_base");
		fillAnlleImage(map, "imgas/title_edu.png", "title_edu");
		fillAnlleImage(map, "imgas/title_language.png", "title_language");
		fillAnlleImage(map, "imgas/title_project.png", "title_project");
		fillAnlleImage(map, "imgas/title_train.png", "title_train");
		fillAnlleImage(map, "imgas/title_work.png", "title_work");
		fillAnlleImage(map, "imgas/walre.jpg", "walre");
	}

	private void fillAnlleImage(ModelMap map, String path, String name) {
		InputStream ap = this.getClass().getClassLoader().getSystemResourceAsStream(path);
		String lotus = covertPicToBase64(ap);
		System.out.println(lotus);
		map.put(name, lotus);
	}

	/**
	 * 导出pdf<br>
	 * 
	 * 千万要注意，这里用simsun字体，也就是宋体，<br>
	 * 所以在ftl中的body标签要加样式style=”font-family: SimSun”,否则不显示中文<br>
	 * 而且ftl非常严格，meta标签最后一般没有“/>”，会报错提醒这里格式不对。
	 * 
	 * @param response
	 * @param map
	 * @param filePath
	 * @throws Exception
	 * @throws DocumentException
	 * @throws IOException
	 */
	private void ExportPdfUtil(HttpServletResponse response, ModelMap map, String filePath, String fileName)
			throws Exception, IOException {
		if (StringUtils.isEmpty(fileName)) {
			fileName = "default.pdf";
		}
		ITextRenderer renderer = new ITextRenderer();
		renderer.setDocumentFromString(DynamicFtl2Html(map, filePath));
		renderer.getSharedContext().setBaseURL("");
		// 解决中文支持问题
		ITextFontResolver fontResolver = renderer.getFontResolver();
		// 设置字体
		fontResolver.addFont("/templates/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
		response.setCharacterEncoding("utf-8");
		ServletOutputStream out = response.getOutputStream();
		renderer.layout();
		renderer.createPDF(out, true);
		out.flush();
	}

	/**
	 * 方式2
	 * 
	 * @param response
	 * @param map
	 * @param filePath
	 * @param fileName
	 * @throws Exception
	 * @throws DocumentException
	 * @throws IOException
	 */
	private void ExportPdfUtil2(HttpServletResponse response, ModelMap map, String filePath, String fileName)
			throws Exception, IOException {
		if (StringUtils.isEmpty(fileName)) {
			fileName = "default.pdf";
		}
		ITextRenderer renderer = new ITextRenderer();
		SharedContext sharedContext = renderer.getSharedContext();
		sharedContext.getTextRenderer().setSmoothingThreshold(0);
		javax.xml.parsers.DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		org.w3c.dom.Document doc = builder
				.parse(new ByteArrayInputStream(DynamicFtl2Html(map, filePath).getBytes("utf-8")));

		renderer.setDocument(doc, null);
		renderer.getSharedContext().setBaseURL("file:/" + PDFUtil.getPath());
		// 解决中文支持问题
		ITextFontResolver fontResolver = renderer.getFontResolver();
		// 设置字体
		/***
		 * TODO 如果遇到编译后总是提示 simhei.ttf 不是有效的 ttf文件，就需要在pom.xml文件中加配置,编译过滤掉ttf文件
		 */
		fontResolver.addFont("/templates/simhei.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
		response.setCharacterEncoding("utf-8");
		ServletOutputStream out = response.getOutputStream();
		renderer.layout();
		renderer.createPDF(out, true);
		out.flush();
	}

	/**
	 * 动态ftl模板输出为静态html
	 * 
	 * @param obj
	 * @param templatePath
	 * @return
	 * @throws Exception
	 */
	private String DynamicFtl2Html(Object obj, String templatePath) throws Exception {
		Configuration configuration = freeMarkerConfigurer.getConfiguration();
		Template tp = configuration.getTemplate(templatePath);
		StringWriter stringWriter = new StringWriter();
		BufferedWriter writer = new BufferedWriter(stringWriter);
		tp.setOutputEncoding("UTF-8");
		tp.process(obj, writer);
		String htmlStr = stringWriter.toString();
		writer.flush();
		writer.close();
		return htmlStr;
	}

	/**
	 * 图片转换成base64编码
	 * 
	 * @param is
	 * @return
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	@SuppressWarnings({ "unused", "restriction" })
	private String covertPicToBase64(InputStream is) {
		if (is == null) {
			return null;
		}
		try {
			byte[] data = new byte[is.available()];
			is.read(data);
			BASE64Encoder encoder = new BASE64Encoder();
			return encoder.encode(data);
		} catch (Exception e) {
		} finally {
			if (null != is) {
				try {
					is.close();
				} catch (IOException e) {
				}
			}
		}
		return "";
	}

}
