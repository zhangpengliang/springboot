package com.boot.freemarker.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.xhtmlrenderer.pdf.ITextRenderer;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class PDFUtil {

	/**
	 * <p>
	 * Description: 生成PDF到文件
	 * </p>
	 * <p>
	 * Company:
	 * </p>
	 * 
	 * @author ZY
	 * @param ftlPath
	 *            模板文件路径（不含文件名）
	 * @param ftlName
	 *            模板文件（不含路径）
	 * @param imageDiskPath
	 *            图片的磁盘路径
	 * @param data
	 *            数据 （填到模板上的数据）
	 * @param outputFile
	 *            目标文件（全路径名称）
	 * @throws Exception
	 */
	public static void generateToFile(String ftlPath, String ftlName, String imageDiskPath, Object data,
			String outputFile) throws Exception {
		OutputStream out = null;
		ITextRenderer render = null;
		try {
			String html = getPdfContent(ftlPath, ftlName, data); // 组装模板和数据生成html串
			out = new FileOutputStream(outputFile);
			render = getRender();
			render.setDocumentFromString(html); // 此处抛异常
			if (imageDiskPath != null && !imageDiskPath.equals("")) {
				// html中如果有图片，图片的路径则使用这里设置的路径的相对路径，这个是作为根路径
				render.getSharedContext().setBaseURL("file:/" + imageDiskPath);
			}
			render.layout();
			render.createPDF(out);
			render.finishPDF();
			render = null;
		} catch (Exception e) {
			throw e;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					throw e;
				}
			}
		}
	}

	/**
	 * 生成PDF到输出流中（ServletOutputStream用于下载PDF）
	 * 
	 * @param ftlPath
	 *            ftl模板文件的路径（不含文件名）
	 * @param ftlName
	 *            ftl模板文件的名称（不含路径）
	 * @param imageDiskPath
	 *            如果PDF中要求图片，那么需要传入图片所在位置的磁盘路径
	 * @param data
	 *            输入到FTL中的数据
	 * @param response
	 *            HttpServletResponse
	 * @return
	 * @throws TemplateNotFoundException
	 * @throws MalformedTemplateNameException
	 * @throws ParseException
	 * @throws IOException
	 * @throws TemplateException
	 * @throws DocumentException
	 */
	public static OutputStream generateToServletOutputStream(String ftlPath, String ftlName, String imageDiskPath,
			Object data, HttpServletResponse response) throws Exception {
		String html = getPdfContent(ftlPath, ftlName, data);
		OutputStream out = null;
		ITextRenderer render = null;
		out = response.getOutputStream();
		render = getRender();
		render.setDocumentFromString(html);
		if (imageDiskPath != null && !imageDiskPath.equals("")) {
			// html中如果有图片，图片的路径则使用这里设置的路径的相对路径，这个是作为根路径
			render.getSharedContext().setBaseURL("file:/" + imageDiskPath);
		}
		render.layout();
		render.createPDF(out);
		render.finishPDF();
		render = null;
		return out;
	}

	public static ITextRenderer getRender() throws DocumentException, IOException {
		ITextRenderer render = new ITextRenderer();
		String path = getPath();
		// 添加字体，以支持中文
		render.getFontResolver().addFont(path + "simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
		return render;
	}

	// 获取要写入PDF的内容
	public static String getPdfContent(String ftlPath, String ftlName, Object o) throws Exception {
		return useTemplate(ftlPath, ftlName, o);
	}

	// 使用freemarker得到html内容
	public static String useTemplate(String ftlPath, String ftlName, Object o) throws Exception {
		String html = null;
		Template tpl = getFreemarkerConfig(ftlPath).getTemplate(ftlName);
		tpl.setEncoding("UTF-8");
		StringWriter writer = new StringWriter();
		tpl.process(o, writer);
		writer.flush();
		html = writer.toString();
		return html;
	}

	/**
	 * 获取Freemarker配置
	 * 
	 * @param templatePath
	 * @return
	 * @throws IOException
	 */
	private static Configuration getFreemarkerConfig(String templatePath) throws IOException {
		Configuration config = new Configuration();
		config.setDirectoryForTemplateLoading(new File(templatePath));
		config.setEncoding(Locale.CHINA, "utf-8");
		return config;
	}

	/**
	 * 获取类项目根路径
	 * 
	 * @return
	 */
	public static String getPath() {
		// return PDFUtil.class.getResource("").getPath().substring(1);
		// //返回类路径（当前类所在的路径）
		return PDFUtil.class.getResource("/").getPath().substring(1); // 返回项目根路径（编译之后的根路径）
	}
}
