package com.boot.freemarker.controller;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URLEncoder;
import java.util.Date;

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
import com.lowagie.text.DocumentException;

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
		InputStream is = this.getClass().getClassLoader().getSystemResourceAsStream("img/baobao.jpg");
		String img = covertPicToBase64(is);
		map.put("img", img);
		ExportPdfUtil2(response, map, filePath, URLEncoder.encode("测试", "UTF-8") + new Date().getTime() + ".pdf");
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
			throws Exception, DocumentException, IOException {
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
	 * @param response
	 * @param map
	 * @param filePath
	 * @param fileName
	 * @throws Exception
	 * @throws DocumentException
	 * @throws IOException
	 */
	private void ExportPdfUtil2(HttpServletResponse response, ModelMap map, String filePath, String fileName)
			throws Exception, DocumentException, IOException {
		if (StringUtils.isEmpty(fileName)) {
			fileName = "default.pdf";
		}
		ITextRenderer renderer = new ITextRenderer();
		SharedContext sharedContext=renderer.getSharedContext();
		sharedContext.setReplacedElementFactory(new B64ImgReplacedElementFactory());
		sharedContext.getTextRenderer().setSmoothingThreshold(0);
//		javax.xml.parsers.DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
//		org.w3c.dom.Document doc = builder.parse(new ByteArrayInputStream(DynamicFtl2Html(map, filePath).getBytes("utf-8")));
		
		renderer.setDocumentFromString(DynamicFtl2Html(map, filePath));
//		renderer.setDocument(doc, null);
//		renderer.getSharedContext().setBaseURL("");
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
