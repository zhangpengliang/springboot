package com.lieni.apollo.gw.candidate.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.SaveFormat;
import com.huayong.awa.apollo.enums.EnumAwaCompany;
import com.huayong.awa.apollo.recommend.dto.ResEduMirrorDto;
import com.huayong.awa.apollo.recommend.dto.ResInfoMirrorDto;
import com.huayong.awa.apollo.recommend.dto.ResLanguageMirrorDto;
import com.huayong.awa.apollo.recommend.dto.ResProjectMirrorDto;
import com.huayong.awa.apollo.recommend.dto.ResReportDto;
import com.huayong.awa.apollo.recommend.dto.ResTrainingMirrorDto;
import com.huayong.awa.apollo.recommend.dto.ResWorkMirrorDto;
import com.huayong.awa.apollo.recommend.facade.IResEduMirrorFacade;
import com.huayong.awa.apollo.recommend.facade.IResInfoMirrorFacade;
import com.huayong.awa.apollo.recommend.facade.IResLanguageMirrorFacade;
import com.huayong.awa.apollo.recommend.facade.IResProjectMirrorFacade;
import com.huayong.awa.apollo.recommend.facade.IResReportMirrorFacade;
import com.huayong.awa.apollo.recommend.facade.IResTrainingMirrorFacade;
import com.huayong.awa.apollo.recommend.facade.IResWorkMirrorFacade;
import com.huayong.awa.core.result.ApiResult;
import com.huayong.awa.util.other.StringUtil;
import com.huayong.resume.bean.Gender;
import com.huayong.resume.bean.ReportExpectEntryTime;
import com.huayong.resume.cv.bean.LanguageProficiency;
import com.huayong.resume.cv.bean.MaritalStatus;
import com.huayong.resume.cv.bean.SalaryType;
import com.huayong.resume.cv.remote.ICvEducationExperienceRemoteService;
import com.huayong.resume.cv.remote.ICvLanguageAbilityRemoteService;
import com.huayong.resume.cv.remote.ICvProjectExperienceRemoteService;
import com.huayong.resume.cv.remote.ICvResumeInfoRemoteService;
import com.huayong.resume.cv.remote.ICvTrainingExperienceRemoteService;
import com.huayong.resume.cv.remote.ICvWorkExperienceRemoteService;
import com.huayong.resume.model.CvEducationExperience;
import com.huayong.resume.model.CvLanguageAbility;
import com.huayong.resume.model.CvProjectExperience;
import com.huayong.resume.model.CvResumeInfo;
import com.huayong.resume.model.CvTrainingExperience;
import com.huayong.resume.model.CvWorkExperience;
import com.huayong.resume.model.ResumeReport;
import com.huayong.resume.remote.IResumeReportRemoteService;
import com.itextpdf.text.pdf.BaseFont;
import com.lieni.api.recommend.exception.RecommendedException;
import com.lieni.apollo.api.basic.bean.ApolloEnumManage;
import com.lieni.apollo.api.candidate.bean.RecomEmployeeRole;
import com.lieni.apollo.api.candidate.bean.ResumeReportFileType;
import com.lieni.apollo.api.candidate.exception.RecommendException;
import com.lieni.apollo.api.candidate.remote.IRecommendRemoteService;
import com.lieni.apollo.api.client.remote.IClientRemoteService;
import com.lieni.apollo.api.job.remote.IJobRemoteService;
import com.lieni.apollo.api.user.remote.IEmployeeRemoteService;
import com.lieni.apollo.candidate.model.Recommend;
import com.lieni.apollo.client.model.Client;
import com.lieni.apollo.gw.base.service.IBaseDataService;
import com.lieni.apollo.gw.candidate.report.view.CandidateReportView;
import com.lieni.apollo.gw.candidate.report.view.EducationExperienceView;
import com.lieni.apollo.gw.candidate.report.view.LanguageView;
import com.lieni.apollo.gw.candidate.report.view.ProjectExperienceView;
import com.lieni.apollo.gw.candidate.report.view.ResumeInfoView;
import com.lieni.apollo.gw.candidate.report.view.ResumeReportView;
import com.lieni.apollo.gw.candidate.report.view.TrainExperienceView;
import com.lieni.apollo.gw.candidate.report.view.WorkExperienceView;
import com.lieni.apollo.gw.candidate.service.IReportExportService;
import com.lieni.apollo.gw.util.ResMirrorUtil;
import com.lieni.apollo.gw.util.ResReportUtil;
import com.lieni.apollo.job.model.Job;
import com.lieni.apollo.user.model.Employee;
import com.lieni.basedata.bean.AdvancedDegree;
import com.lieni.basedata.bean.LanguageBean;
import com.lieni.basedata.model.LanguageLevel;
import com.lieni.core.util.CalcUtil;
import com.lieni.core.util.DateTimeUtil;
import com.lieni.core.util.EnumUtil;
import com.lieni.core.util.JsonUtil;
import com.lieni.core.util.MessageUtil;
import com.lieni.file.remote.IFileRemoteService;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Service
public class ReportExportService implements IReportExportService {

	private static final String WORD_FTL = "candidate/candidateReportTabWord.ftl";
	private static final String WORD_ANLLE_FTL = "candidate/candidateReportTabWord_anlle.ftl";
	private static final String WORD_WALRE_FTL = "candidate/candidateReportTabWord_walre.ftl";

	@Autowired
	private IJobRemoteService jobRemoteService;
	@Autowired
	private IClientRemoteService clientRemoteService;
	@Autowired
	private IResumeReportRemoteService resumeReportRemoteService;
	@Autowired
	private ICvResumeInfoRemoteService cvResumeInfoRemoteService;
	@Autowired
	private IEmployeeRemoteService employeeRemoteService;
	@Autowired
	private IRecommendRemoteService recommendRemoteService;
	@Autowired
	private IBaseDataService baseDataService;
	@Autowired
	private ICvEducationExperienceRemoteService cvEducationExperienceRemoteService;
	@Autowired
	private ICvWorkExperienceRemoteService cvWorkExperienceRemoteService;
	@Autowired
	private ICvTrainingExperienceRemoteService cvTrainingExperienceRemoteService;
	@Autowired
	private ICvProjectExperienceRemoteService cvProjectExperienceRemoteService;
	@Autowired
	private ICvLanguageAbilityRemoteService cvLanguageAbilityRemoteService;
	@Autowired
	private IFileRemoteService resumeReportFileRemoteService;
	@Autowired
	private IResReportMirrorFacade resReportMirrorFacade;
	@Autowired
	private IResInfoMirrorFacade resInfoMirrorFacade;
	@Autowired
	private IResWorkMirrorFacade resWorkMirrorFacade;
	@Autowired
	private IResEduMirrorFacade resEduMirrorFacade;
	@Autowired
	private IResProjectMirrorFacade resProjectMirrorFacade;
	@Autowired
	private IResTrainingMirrorFacade resTrainingMirrorFacade;
	@Autowired
	private IResLanguageMirrorFacade resLanguageMirrorFacade;
	@Autowired
	private MessageSource messageSource;

	@Value("${to.this.day}")
	private String toNow;
	@Value("${report.str.default.value}")
	private String strDefaultValue;
	@Value("${report.education.school.national.unified}")
	private String nationalUnified;
	@Value("${report.education.school.not.national.unified}")
	private String notNationalUnified;
	@Value("${report.college.to.university}")
	private String collegeToUniversity;

	@Value("${sys.brand.id}")
	private Integer brandId;

	private Logger logger = LoggerFactory.getLogger(ReportExportService.class);

	@Override
	public ResumeReport getResumeReportById(long resumeReportId) throws Exception {
		ResumeReport resumeReport = resumeReportRemoteService.getById(resumeReportId);
		// 容错 薪酬福利在新老系统取的字段不同
		if (resumeReport != null) {
			if (StringUtil.isBlank(resumeReport.getRemunerationAnalysis())) {
				resumeReport.setRemunerationAnalysis(resumeReport.getOtherIncome());
			}
		}
		return resumeReport;
	}

	@Override
	public CandidateReportView getCandidateReportView(Recommend recommend) throws Exception {
		if (recommend == null) {
			return null;
		}
		CandidateReportView candidateReportView = new CandidateReportView();

		Long recommendId = Long.valueOf(recommend.getId());
		String resumeId = recommend.getCaid();

		ApiResult<ResInfoMirrorDto> resInfoMirrorApiResult = resInfoMirrorFacade
				.getByRecommendId(Long.valueOf(recommendId));
		ResInfoMirrorDto resInfoMirrorDto = resInfoMirrorApiResult.getData();
		CvResumeInfo cvResumeInfo = ResMirrorUtil.copyToCvResumeInfo(resInfoMirrorDto);
		if (cvResumeInfo == null) {
			cvResumeInfo = cvResumeInfoRemoteService.getById(resumeId);
		}

		// 基本信息
		candidateReportView.setResumeInfo(getResumeInfoView(cvResumeInfo));
		// 工作经历
		candidateReportView.setWorkExperiences(getWorkExperienceViews(resumeId, recommendId));
		// 教育经历
		candidateReportView.setEducationExperiences(getEducationExperienceViews(resumeId, recommendId));
		// 项目经历
		candidateReportView.setProjectExperiences(getProjectExperienceViews(resumeId, recommendId));
		// 培训经历
		candidateReportView.setTrainExperiences(getTrainExperienceViews(resumeId, recommendId));
		// 语言能力
		candidateReportView.setLanguages(getLanguageViews(resumeId, recommendId));
		return candidateReportView;
	}

	@Override
	public void exportCandidateWord(HttpServletResponse response, int recommendId) throws Exception {
		// 1.获取推荐信息
		Recommend recommend = recommendRemoteService.getById(recommendId);
		if (recommend == null) {
			throw new RecommendedException(RecommendException.ILLEGAL_OPERATION);
		}
		// 2.获取推荐人信息
		Employee employee = getReportRecomPerson(recommend, RecomEmployeeRole.RECOMMEND_COUNSELOR);
		// 3.获取推荐报告内容数据
		CandidateReportView view = getCandidateReportView(recommend, employee, true);
		// 4.获取文件流
		ByteArrayOutputStream outputStream = getOutputStream4Word(view);
		if (outputStream == null) {
			throw new RecommendedException(RecommendException.SYSTEM_ERROR);
		}
		// 5.生成附件名称
		String docName = generateFileName(recommend, employee, "doc");
		// 6.输出文件到浏览器
		responseByFile4Word(response, outputStream, docName);
	}

	@Override
	public void exportCandidateReportPdf(HttpServletResponse response, int recommendId) throws Exception {
		// 1.获取推荐信息
		Recommend recommend = recommendRemoteService.getById(recommendId);
		if (recommend == null) {
			throw new RecommendedException(RecommendException.ILLEGAL_OPERATION);
		}
		// 2.获取推荐人信息
		Employee employee = getReportRecomPerson(recommend, RecomEmployeeRole.RECOMMEND_COUNSELOR);
		// 3.获取推荐报告内容数据
		CandidateReportView candidateReportView = getCandidateReportView(recommend, employee, true);
		// 4.获取文件内容
		Template template = getTemplate4Pdf();
		StringWriter stringWriter = new StringWriter();
		template.process(candidateReportView, stringWriter);
		String content = stringWriter.toString();

		// 5.生成附件名称
		String fileName = generateFileName(recommend, employee, "pdf");
		// 6.输出文件到浏览器
		responseByFile4Pdf(response, content, fileName);
	}

	@Override
	public Map<String, Object> uploadReportFile(Recommend recommend, RecomEmployeeRole employeeRoleInReport,
			boolean needReumeReportBody, ResumeReportFileType resumeReportFileType) throws Exception {
		if (recommend == null) {
			throw new RecommendedException(RecommendException.ILLEGAL_OPERATION);
		}
		// 报告显示推荐报告
		Employee employee = getReportRecomPerson(recommend, employeeRoleInReport);
		// 参数封装
		CandidateReportView candidateReportView = getCandidateReportView(recommend, employee, needReumeReportBody);
		// 获取文件流
		byte[] bytes = null;
		if (ResumeReportFileType.WORD == resumeReportFileType) {
			ByteArrayOutputStream onputStream = getOutputStream4Word(candidateReportView);
			bytes = onputStream.toByteArray();
		} else {
			// 模板加载路径
			Template template = getTemplate4Pdf();
			Writer stringWriter = new StringWriter();
			template.process(candidateReportView, stringWriter);
			if (resumeReportFileType.getValue() == ResumeReportFileType.HTML.getValue()) {
				bytes = stringWriter.toString().getBytes("GBK");
			} else {
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				generate(stringWriter.toString(), outputStream);
				bytes = outputStream.toByteArray();
				outputStream.close();
			}
		}
		String fileName = generateFileName(recommend, employee, resumeReportFileType.getSuffix());
		String filePath = resumeReportFileRemoteService.upload(resumeReportFileType.getSuffix(), bytes);

		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("sender", employee);
		resultMap.put("name", fileName);
		resultMap.put("path", filePath);
		resultMap.put("fullPath", resumeReportFileRemoteService.getUrl(filePath));
		return resultMap;
	}

	/**
	 * 输出文件到浏览器
	 * 
	 * @param response
	 * @param outputStream
	 * @param fileName
	 * @throws Exception
	 */
	private void responseByFile4Word(HttpServletResponse response, ByteArrayOutputStream outputStream, String fileName)
			throws Exception {
		String targetFileName = new String(fileName.replace(" ", "").getBytes("GBK"), "ISO-8859-1");
		response.setContentType("application/msword;charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=" + targetFileName);
		ServletOutputStream out = null;
		try {
			out = response.getOutputStream();
			out.write(outputStream.toByteArray());// 写到输出流(out)中
		} catch (Exception ex) {
			logger.error("process data error", ex);
		} finally {
			// 关闭输出流
			if (null != out) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					logger.error("close outputstream error", e);
				}
			}
		}
	}

	private void responseByFile4Pdf(HttpServletResponse response, String htmlStr, String fileName) throws Exception {
		String targetFileName = new String(fileName.replace(" ", "").getBytes("GBK"), "ISO-8859-1");

		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=" + targetFileName);
		response.setCharacterEncoding("utf-8");

		ServletOutputStream out = null;
		try {
			out = response.getOutputStream();
			generate(htmlStr, out);
		} catch (Exception ex) {
			logger.error("process data error", ex);
		} finally {
			// 关闭输出流
			if (null != out) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					logger.error("close outputstream error", e);
				}
			}
		}

	}

	/**
	 * 获取生成word文件输出流
	 * 
	 * @param view
	 * @return
	 * @throws Exception
	 */
	private ByteArrayOutputStream getOutputStream4Word(CandidateReportView view) throws Exception {
		if (view == null) {
			return null;
		}
		// 需要显示html代码的map集合
		List<Map<String, Object>> htmlColumnList = getHtmlColumnMapList(view);
		String viewJson = JsonUtil.toJson(view).replace("&", "&#38;").replace(">", "&#62;").replace("<", "&#60;");
		viewJson = com.lieni.core.util.StringUtils.replaceBr(viewJson).replace("\\n", "<w:br/>")
				.replace("\\r", "<w:br/>").replace("\\t", "").replace("\\n\\t", "").replace("\n\t", "")
				.replace("\n\r", "<w:br/>").replace("\\n\\r", "<w:br/>").replace("\\r\\n", "<w:br/>")
				.replace("\r\n", "<w:br/>").replace("< w: br / >", "<w:br/>");
		// 用于word合并的代码
		view = JsonUtil.toObject(viewJson, CandidateReportView.class);
		Template template = getTemplate4Word();
		// 使用aspose插件将生成word
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		Writer wrter = new OutputStreamWriter(bos, "utf-8");
		if (template != null) {
			template.process(view, wrter);
		}
		wrter.close();
		ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
		Document doc = new Document(bis);
		// 插入html内容
		if (CollectionUtils.isNotEmpty(htmlColumnList)) {
			DocumentBuilder builder = new DocumentBuilder(doc);
			for (Map<String, Object> map : htmlColumnList) {
				insertHtmlToDoc(builder, map);
			}
		}
		doc.getMailMerge().deleteFields();
		// 生成word流
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		doc.save(outputStream, SaveFormat.DOC);
		return outputStream;
	}

	/**
	 * 设置需要显示html内容的项
	 *
	 * @param candidateReportView
	 *            简历报告
	 * @throws Exception
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	private List<Map<String, Object>> getHtmlColumnMapList(CandidateReportView candidateReportView) throws Exception {
		if (candidateReportView == null) {
			return null;
		}
		List<Map<String, Object>> mapList = new ArrayList<>();
		// 推荐报告
		if (candidateReportView.getResumeReport() != null) {
			Map<String, Object> htmlCloumnMap = candidateReportView.getResumeReport().getHtmlBodyColumnMap();
			mapList.add(htmlCloumnMap);
		}
		// 工作经历
		if (CollectionUtils.isNotEmpty(candidateReportView.getWorkExperiences())) {
			for (WorkExperienceView view : candidateReportView.getWorkExperiences()) {
				Map<String, Object> htmlCloumnMap = view.getNotBlanHtmlColumnRowMap();
				mapList.add(htmlCloumnMap);
			}
		}
		// 项目经历
		if (CollectionUtils.isNotEmpty(candidateReportView.getProjectExperiences())) {
			for (ProjectExperienceView view : candidateReportView.getProjectExperiences()) {
				Map<String, Object> htmlCloumnMap = view.getHtmlColumnRowMap();
				mapList.add(htmlCloumnMap);
			}
		}
		return mapList;
	}

	/**
	 * 插入html文档
	 *
	 * @param builder
	 * @param map
	 *            插入的域、值map
	 * @throws Exception
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	private void insertHtmlToDoc(DocumentBuilder builder, Map<String, Object> map) throws Exception {
		for (Map.Entry<String, Object> item : map.entrySet()) {
			builder.moveToMergeField(item.getKey(), true, true);
			builder.insertHtml(item.getValue() == null ? StringUtils.EMPTY
					: item.getValue().toString().replace("\n", "<br/>").replace("</p><p>", "<br/>").replace("<p>", "")
							.replace("</p>", ""),
					true);
		}
	}

	/**
	 * 获取语言能力显示文本
	 *
	 * @param list
	 * @param languageId
	 *            语言能力id
	 * @param languageLevel
	 *            语言能力级别
	 * @return
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	private void setLanguageColumn(LanguageView view, List<LanguageBean> list, Integer languageId,
			Integer languageLevel, Integer languageProficiency) {
		String languageText = "";
		for (LanguageBean languageBean : list) {
			if (languageId != languageBean.getId()) {
				continue;
			}
			// 语言名称
			view.setLanguage(languageBean.getName());
			languageText += languageBean.getName();
			if (CollectionUtils.isNotEmpty(languageBean.getLevels())) {
				for (LanguageLevel level : languageBean.getLevels()) {
					if (languageLevel != level.getId().intValue()) {
						continue;
					}
					// 语言等级
					view.setLanguageLevel(level.getName());
					languageText += com.lieni.core.util.StringUtils.BLANK + level.getName();
				}
			}
			if (languageProficiency <= 0) {
				continue;
			}
			// 熟练程度
			LanguageProficiency languageProficiencyEnum = LanguageProficiency.getByValue(languageProficiency);
			String languageProficiencyText = ApolloEnumManage.getNameByEnum(languageProficiencyEnum);
			view.setLanguageProficiency(languageProficiencyText);
			languageText += com.lieni.core.util.StringUtils.BLANK + languageProficiencyText;
		}
		view.setLanguageText(languageText);
	}

	/**
	 * 获取经历时间范围显示
	 *
	 * @param startYear
	 *            开始年份
	 * @param startMonth
	 *            开始月份
	 * @param endYear
	 *            结束年份
	 * @param endMonth
	 *            结束月份
	 * @return
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	private String getExperienceTimeRange(int startYear, int startMonth, int endYear, int endMonth) {
		String result = startYear + com.lieni.core.util.StringUtils.SLASH + startMonth;
		result += "-";
		result += endYear == 9999 ? toNow : (endYear + com.lieni.core.util.StringUtils.SLASH + endMonth);
		return result;
	}

	/**
	 * 获取工作年限文本
	 *
	 * @param workYear
	 *            工作年份
	 * @return
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	private String getWorkYearText(Integer workYear) {
		if (workYear == null) {
			return null;
		}
		if (workYear < 1) {
			return MessageUtil.message("cv.resume.work.year.less.than.one", messageSource);
		} else if (workYear > 12) {
			return MessageUtil.message("cv.resume.work.year.greater.than.twelve", messageSource);
		} else {
			final String YAER_UNIT = MessageUtil.message("year.ch", messageSource);
			return workYear + YAER_UNIT;
		}
	}

	/**
	 * 获取简历报告view
	 *
	 * @param recommend
	 *            订单
	 * @param employee
	 *            报告线束推荐员工
	 * @param needReumeReportBody
	 *            是否需要显示推荐分析内容
	 * @return
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	private CandidateReportView getCandidateReportView(Recommend recommend, Employee employee,
			boolean needReumeReportBody) throws Exception {

		CandidateReportView candidateReportView = getCandidateReportView(recommend);

		if (candidateReportView != null && needReumeReportBody) {
			ApiResult<ResReportDto> resReportDtoApiResult = resReportMirrorFacade
					.getByRecommendId(Long.valueOf(recommend.getId()));
			ResReportDto resReportDto = resReportDtoApiResult.getData();
			ResumeReport resumeReport = ResReportUtil.copyPropertyToResumeReport(resReportDto);
			if (resumeReport == null) {
				resumeReport = getResumeReportById(Long.parseLong(recommend.getLienirptno()));
			}
			/**
			 * if (resumeReport != null) { String offlineOffer =
			 * resumeReport.getOfflineOffer(); if
			 * (StringUtils.isNotBlank(offlineOffer)) { offlineOffer =
			 * offlineOffer.replace("</br>
			 * ", "\r"); offlineOffer = offlineOffer.replace("<br/>
			 * ", "\r"); resumeReport.setOfflineOffer(offlineOffer); } }
			 */
			candidateReportView.setResumeReport(getResumeReportView(recommend, employee, resumeReport));
		}

		return candidateReportView;
	}

	/**
	 * 获取推荐报告view
	 *
	 * @param recommend
	 *            订单id
	 * @param recomEmployeeRole
	 *            报告显示推荐员工角色
	 * @return
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	private ResumeReportView getResumeReportView(Recommend recommend, Employee employee, ResumeReport resumeReport) {
		if (recommend == null || resumeReport == null) {
			return null;
		}
		// 8大分析
		ResumeReportView view = new ResumeReportView();
		if (recommend.getRecomdate() == null) {
			view.setRecomDate(DateTimeUtil.dateString(recommend.getAddtime(), "yyyy-MM-dd"));
		} else {
			view.setRecomDate(DateTimeUtil.dateString(recommend.getRecomdate(), "yyyy-MM-dd"));
		}
		view.setRecomPosition(recommend.getJobname());
		if (resumeReport != null) {
			view.setReportNo(resumeReport.getCode());
			String curSalary = resumeReport.getNowSalary() == null ? StringUtils.EMPTY
					: resumeReport.getNowSalary().toString();
			if (resumeReport.getNowSalaryType() > 0) {
				String beforeTaxText = MessageUtil.message("cv.resume.report.salary.before.tax", messageSource);
				String afterTaxText = MessageUtil.message("cv.resume.report.salary.after.tax", messageSource);
				curSalary += "（" + (resumeReport.getNowSalaryType() == 1 ? beforeTaxText : afterTaxText) + "）";
			}
			view.setCurSalary(curSalary);
			view.setExpSalary(SalaryType.NEGOTIABLE.getValue().equals(resumeReport.getExpectSalaryType())
					? ApolloEnumManage.getNameByEnum(SalaryType.getByValue(resumeReport.getExpectSalaryType()))
					: resumeReport.getExpectSalary());
			String remunerationAnalysis = StringUtil.isBlank(resumeReport.getRemunerationAnalysis())
					? resumeReport.getOtherIncome() : resumeReport.getRemunerationAnalysis();
			view.setRemunerationAnalysis(remunerationAnalysis);
			view.setJobAnalysis(resumeReport.getJobAnalysis());
			view.setProfessionalAnalysis(resumeReport.getProfessionalAnalysis());
			view.setJobdevelopmentAnalysis(resumeReport.getJobdevelopmentAnalysis());
			view.setJobintentionAnalysis(resumeReport.getJobintentionAnalysis());
			view.setCharacterAnalysis(resumeReport.getCharacterAnalysis());
			view.setFamilyAnalysis(resumeReport.getFamilyAnalysis());
			view.setAdvantageAnalysis(resumeReport.getAdvantageAnalysis());
			view.setExpectInterviewTime(resumeReport.getExpectInterviewTime());
			view.setExpectEntryTime(ApolloEnumManage
					.getNameByEnum(ReportExpectEntryTime.getByValue(resumeReport.getExpectEntryTime())));
			view.setOfflineOffer(resumeReport.getOfflineOffer());
		}
		// 获取职位中的公司信息
		String clientName = recommend.getClientname();
		view.setRecommendCompany(clientName);
		if (employee == null) {
			return view;
		}
		String recommendPerson = employee.getFullname();
		if (StringUtils.isNotEmpty(employee.getMobile())) {
			recommendPerson += com.lieni.core.util.StringUtils.BLANK
					+ com.lieni.core.util.StringUtils.LEFT_MEDIUM_BRACKET + employee.getMobile()
					+ com.lieni.core.util.StringUtils.RIGHT_MEDIUM_BRACKET;
		}
		view.setRecommendPerson(recommendPerson);
		return view;
	}

	/**
	 * 获取基本信息view
	 *
	 * @param recommend
	 *            订单
	 * @return
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	private ResumeInfoView getResumeInfoView(CvResumeInfo cvResumeInfo) {
		if (cvResumeInfo == null) {
			return null;
		}

		Gender gender = Gender.getByValue(cvResumeInfo.getGender());
		String age = (int) CalcUtil.sub(DateTimeUtil.getYear(new Date()), cvResumeInfo.getBirthYear())
				+ MessageUtil.message("job.report.age.unit", messageSource);
		if (cvResumeInfo.getBirthYear() != null) {
			age += "(" + cvResumeInfo.getBirthYear() + "年)";
		}
		String maritalState = null;
		if (cvResumeInfo.getMaritalStatus() != null) {
			maritalState = ApolloEnumManage.getNameByEnum(MaritalStatus.getByValue(cvResumeInfo.getMaritalStatus()));
		}

		String workCityText = "";
		if (cvResumeInfo.getWorkProvince() != null && cvResumeInfo.getWorkProvince() > 0) {
			workCityText += baseDataService.getLocationNameById(cvResumeInfo.getWorkProvince().intValue());
		}
		if (!checkSpecialProvince(workCityText) && cvResumeInfo.getWorkCity() != null
				&& cvResumeInfo.getWorkCity() > 0) {
			workCityText += baseDataService.getLocationNameById(cvResumeInfo.getWorkCity().intValue());
		}
		if (cvResumeInfo.getWorkDistrict() != null && cvResumeInfo.getWorkDistrict() > 0) {
			workCityText += baseDataService.getLocationNameById(cvResumeInfo.getWorkDistrict().intValue());
		}

		String hometownCity = "";
		if (cvResumeInfo.getHometownProvince() != null && cvResumeInfo.getHometownProvince() > 0) {
			hometownCity += baseDataService.getLocationNameById(cvResumeInfo.getHometownProvince().intValue());
		}
		if (!checkSpecialProvince(hometownCity) && cvResumeInfo.getHometownCity() != null
				&& cvResumeInfo.getHometownCity() > 0) {
			hometownCity += baseDataService.getLocationNameById(cvResumeInfo.getHometownCity().intValue());
		}
		ResumeInfoView view = new ResumeInfoView();
		view.setName(cvResumeInfo.getName());
		view.setGender(ApolloEnumManage.getNameByEnum(gender));
		view.setAge(age);
		view.setMaritalState(maritalState);
		view.setWorkCity(workCityText);
		view.setWorkYears(getWorkYearText(cvResumeInfo.getWorkYears()));
		view.setHometownCity(hometownCity);
		return view;
	}

	/**
	 * 检查特殊省份
	 *
	 * @param workCityText
	 * @return
	 */
	private boolean checkSpecialProvince(String workCityText) {
		if ("北京市".equals(workCityText) || "上海市".equals(workCityText) || "天津市".equals(workCityText)
				|| "重庆市".equals(workCityText)) {
			return true;
		}
		return false;
	}

	/**
	 * 获取工作经历
	 *
	 * @param resumeId
	 *            简历id
	 * @param recommendId
	 *            推荐id
	 * @return
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	private List<WorkExperienceView> getWorkExperienceViews(String resumeId, Long recommendId) {
		if (StringUtils.isBlank(resumeId)) {
			return null;
		}
		List<CvWorkExperience> list = new ArrayList<>();

		// 先获取镜像
		List<ResWorkMirrorDto> workList = resWorkMirrorFacade.listByRecommendId(recommendId).getData();
		if (CollectionUtils.isNotEmpty(workList)) {
			for (ResWorkMirrorDto loopEntity : workList) {
				CvWorkExperience cvWorkExperience = ResMirrorUtil.copyToCvWorkExperience(loopEntity);
				list.add(cvWorkExperience);
			}
		} else {
			// 标准版
			list = cvWorkExperienceRemoteService.listByResumeId(resumeId);
		}
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		List<WorkExperienceView> workExperienceViews = new ArrayList<>();
		final String YEAR_UNIT = MessageUtil.message("year.ch", messageSource);
		final String MONTH_UNIT = MessageUtil.message("month.ch", messageSource);
		Date now = new Date();
		int nowYear = DateTimeUtil.getYear(now);
		int nowMonth = DateTimeUtil.getMonth(now);
		for (CvWorkExperience cvWorkExperience : list) {
			WorkExperienceView workExperienceView = new WorkExperienceView();
			workExperienceViews.add(workExperienceView);
			workExperienceView.setTimeRange(getExperienceTimeRange(cvWorkExperience.getStartYear(),
					cvWorkExperience.getStartMonth(), cvWorkExperience.getEndYear(), cvWorkExperience.getEndMonth()));
			workExperienceView.setCompanyName(cvWorkExperience.getCompany());
			workExperienceView.setJobName(cvWorkExperience.getJobName());
			// 计算时长
			int endYear = cvWorkExperience.getEndYear();
			int endMonth = cvWorkExperience.getEndMonth();
			if (cvWorkExperience.getEndYear() == 9999) {
				endYear = nowYear;
				endMonth = nowMonth;
			}
			int months = CalcUtil.add(CalcUtil.mul(12, CalcUtil.sub(endYear, cvWorkExperience.getStartYear())),
					CalcUtil.sub(endMonth, cvWorkExperience.getStartMonth())).intValue() + 1;
			String duration = "";
			if (months < 12) {
				duration = months + MONTH_UNIT;
			} else {
				int years = CalcUtil.divForTen(months, 12).intValue();
				duration = years + YEAR_UNIT;
				int monthTemp = (int) CalcUtil.sub(months, CalcUtil.mul(years, 12));
				if (monthTemp > 0) {
					duration += monthTemp + MONTH_UNIT;
				}
			}
			workExperienceView.setDuration(duration);
			workExperienceView.setDepartment(cvWorkExperience.getDepartment());
			workExperienceView.setReportPeople(cvWorkExperience.getReportPeople());
			workExperienceView.setUnderPeople(
					cvWorkExperience.getUnderPeople() == null ? "0" : cvWorkExperience.getUnderPeople().toString());
			workExperienceView.setDimissionReason(cvWorkExperience.getDimissionReason());
			workExperienceView.setCompanyIntroduce(StringUtils.isEmpty(cvWorkExperience.getCompanyIntroduce())
					? StringUtils.EMPTY : cvWorkExperience.getCompanyIntroduce());
			workExperienceView.setWorkContent(StringUtils.isEmpty(cvWorkExperience.getDutyPerformance())
					? StringUtils.EMPTY : cvWorkExperience.getDutyPerformance());
			workExperienceView.setWorkAchievement(StringUtils.isEmpty(cvWorkExperience.getWorkGrade())
					? StringUtils.EMPTY : cvWorkExperience.getWorkGrade());
			workExperienceView.setWorkStatus(cvWorkExperience.getEndYear() == 9999 ? 1 : 0);
		}
		return workExperienceViews;
	}

	/**
	 * 获取教育经历
	 *
	 * @param resumeId
	 *            简历id
	 * @param resumeTabId
	 *            标记版简历id（有则表示查询标记版内容）
	 * @return
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	private List<EducationExperienceView> getEducationExperienceViews(String resumeId, Long recommendId) {
		if (StringUtils.isBlank(resumeId)) {
			return null;
		}
		List<CvEducationExperience> list = new ArrayList<>();
		List<ResEduMirrorDto> resEduMirrorList = resEduMirrorFacade.listByRecommendId(recommendId).getData();
		if (CollectionUtils.isNotEmpty(resEduMirrorList)) {
			for (ResEduMirrorDto loopEntity : resEduMirrorList) {
				CvEducationExperience cvEducationExperience = ResMirrorUtil.copyToCvEducationExperience(loopEntity);
				list.add(cvEducationExperience);
			}
		} else {
			// 标准版
			list = cvEducationExperienceRemoteService.list(resumeId);
		}
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		List<EducationExperienceView> educationExperienceViews = new ArrayList<>();
		for (CvEducationExperience cvEducationExperience : list) {

			String graducationSchool = cvEducationExperience.getGraduationSchool() == null ? StringUtils.EMPTY
					: cvEducationExperience.getGraduationSchool();
			if (cvEducationExperience.getNationalUnified() != null) {
				graducationSchool += "（"
						+ (cvEducationExperience.getNationalUnified() == true ? nationalUnified : notNationalUnified)
						+ "）";
			}
			AdvancedDegree advancedDegree = AdvancedDegree.getByValue(cvEducationExperience.getDegree());
			String degree = EnumUtil.getNameByEnum(advancedDegree, messageSource, null) == null ? StringUtils.EMPTY
					: EnumUtil.getNameByEnum(advancedDegree, messageSource, null);
			degree += cvEducationExperience.getCollegeToUniversity() == true ? "（" + collegeToUniversity + "）"
					: StringUtils.EMPTY;
			EducationExperienceView view = new EducationExperienceView();

			view.setTimeRange(
					getExperienceTimeRange(cvEducationExperience.getStartYear(), cvEducationExperience.getStartMonth(),
							cvEducationExperience.getEndYear(), cvEducationExperience.getEndMonth()));
			view.setGraduationSchool(graducationSchool);
			view.setProfessional(cvEducationExperience.getProfessional());

			view.setDegree(degree);

			educationExperienceViews.add(view);
		}
		return educationExperienceViews;
	}

	/**
	 * 获取项目经历
	 *
	 * @param resumeId
	 *            简历id
	 * @param recommendId
	 *            推荐id
	 * @return
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	private List<ProjectExperienceView> getProjectExperienceViews(String resumeId, Long recommendId) {
		if (StringUtils.isBlank(resumeId)) {
			return null;
		}
		List<CvProjectExperience> list = new ArrayList<>();
		List<ResProjectMirrorDto> resProjectMirrorList = resProjectMirrorFacade.listByRecommendId(recommendId)
				.getData();
		if (CollectionUtils.isNotEmpty(resProjectMirrorList)) {
			for (ResProjectMirrorDto loopEntity : resProjectMirrorList) {
				CvProjectExperience cvProjectExperience = ResMirrorUtil.copyToCvProjectExperience(loopEntity);
				list.add(cvProjectExperience);
			}
		} else {
			// 标准版
			list = cvProjectExperienceRemoteService.listByResumeId(resumeId);
		}
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		List<ProjectExperienceView> projectExperienceViews = new ArrayList<>();
		for (CvProjectExperience cvProjectExperience : list) {
			ProjectExperienceView view = new ProjectExperienceView();
			view.setProjectDesc(cvProjectExperience.getProjectDesc());
			view.setResponsibilityDesc(cvProjectExperience.getResponsibilityDesc());
			view.setTimeRange(
					getExperienceTimeRange(cvProjectExperience.getStartYear(), cvProjectExperience.getStartMonth(),
							cvProjectExperience.getEndYear(), cvProjectExperience.getEndMonth()));
			view.setProjectName(cvProjectExperience.getProjectName());
			projectExperienceViews.add(view);
		}
		return projectExperienceViews;
	}

	/**
	 * 获取培训经历
	 *
	 * @param resumeId
	 *            简历id
	 * @param resumeTabId
	 *            标记版简历id（有则表示查询标记版内容）
	 * @return
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	private List<TrainExperienceView> getTrainExperienceViews(String resumeId, Long recommendId) {
		if (StringUtils.isBlank(resumeId)) {
			return null;
		}
		List<CvTrainingExperience> list = new ArrayList<>();
		List<ResTrainingMirrorDto> resTrainingMirrorList = resTrainingMirrorFacade.listByRecommendId(recommendId)
				.getData();
		if (CollectionUtils.isNotEmpty(resTrainingMirrorList)) {
			for (ResTrainingMirrorDto loopEntity : resTrainingMirrorList) {
				CvTrainingExperience cvTrainingExperience = ResMirrorUtil.copyToCvTrainingExperience(loopEntity);
				list.add(cvTrainingExperience);
			}
		} else {
			// 标准版
			list = cvTrainingExperienceRemoteService.listByResumeId(resumeId);
		}
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		List<TrainExperienceView> trainExperienceViews = new ArrayList<>();
		for (CvTrainingExperience cvTrainingExperience : list) {
			TrainExperienceView view = new TrainExperienceView();
			view.setTimeRange(
					getExperienceTimeRange(cvTrainingExperience.getStartYear(), cvTrainingExperience.getStartMonth(),
							cvTrainingExperience.getEndYear(), cvTrainingExperience.getEndMonth()));
			view.setTrainingCertificate(cvTrainingExperience.getTrainingCertificate());
			view.setTrainingOrganization(cvTrainingExperience.getTrainingOrganization());
			view.setTrainingName(cvTrainingExperience.getTrainingName());
			view.setTrainingPlace(cvTrainingExperience.getTrainingPlace());
			view.setTrainingDesc(cvTrainingExperience.getTrainingDesc());
			trainExperienceViews.add(view);
		}
		return trainExperienceViews;
	}

	/**
	 * 获取语言能力views
	 *
	 * @param resumeId
	 *            简历id
	 * @param recommendId
	 *            推荐id
	 * @return
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	private List<LanguageView> getLanguageViews(String resumeId, Long recommendId) {
		if (StringUtils.isBlank(resumeId)) {
			return null;
		}
		List<CvLanguageAbility> list = new ArrayList<>();
		List<ResLanguageMirrorDto> resLanguageMirrorList = resLanguageMirrorFacade.listByRecommendId(recommendId)
				.getData();
		if (CollectionUtils.isNotEmpty(resLanguageMirrorList)) {
			for (ResLanguageMirrorDto loopEntity : resLanguageMirrorList) {
				CvLanguageAbility cvLanguageAbility = ResMirrorUtil.copyToCvLanguageAbility(loopEntity);
				list.add(cvLanguageAbility);
			}
		} else {
			// 标准版
			list = cvLanguageAbilityRemoteService.listByResumeId(resumeId);
		}
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		List<LanguageView> languageViews = new ArrayList<>();
		List<LanguageBean> languageBeans = baseDataService.getLanguageBeanList();
		for (CvLanguageAbility cvLanguageAbility : list) {
			LanguageView view = new LanguageView();
			if (StringUtils.isNotBlank(cvLanguageAbility.getOtherLanguage())) {
				view.setOtherLanguage(cvLanguageAbility.getOtherLanguage());
				view.setLanguageText(cvLanguageAbility.getOtherLanguage());
			} else {
				setLanguageColumn(view, languageBeans, cvLanguageAbility.getLanguage(),
						cvLanguageAbility.getLanguageLevel(), cvLanguageAbility.getLanguageProficiency());
			}
			languageViews.add(view);
		}
		return languageViews;
	}

	/**
	 * 根据HTML代码生成PDF文件
	 * 
	 * @param htmlStr
	 * @param out
	 * @throws Exception
	 * @author zhangshaoning
	 * @since [产品/模块版本](可选)
	 */
	private void generate(String htmlStr, OutputStream out) throws Exception {
		htmlStr = htmlStr.replaceAll("<br>", "<br/>");
		javax.xml.parsers.DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		org.w3c.dom.Document doc = builder.parse(new ByteArrayInputStream(htmlStr.getBytes("utf-8")));
		ITextRenderer renderer = new ITextRenderer();
		ITextFontResolver fontResolver = renderer.getFontResolver();
		fontResolver.addFont("/template/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
		renderer.setDocument(doc, null);
		renderer.layout();
		renderer.createPDF(out);
		// out.close();
	}

	/**
	 * 获取推荐人信息
	 *
	 * @param recommend
	 *            订单model
	 * @param recomEmployeeRole
	 *            推荐顾问角色
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	private Employee getReportRecomPerson(Recommend recommend, RecomEmployeeRole recomEmployeeRole) {
		if (recommend == null || recomEmployeeRole == null) {
			return null;
		}
		String employeeNo = "";
		if (recomEmployeeRole.getValue() == RecomEmployeeRole.POSITION_HOLDER.getValue()) {
			Job job = jobRemoteService.getJobById(recommend.getJobid());
			employeeNo = job == null ? null : job.getEmployeeno();
		} else if (recomEmployeeRole.getValue() == RecomEmployeeRole.CUSTOMER_HOLDER.getValue()) {
			Client client = clientRemoteService.getById(recommend.getClientid());
			employeeNo = client == null ? null : client.getEmployeeno();
		} else {
			employeeNo = recommend.getReferrerno();
		}
		if (StringUtils.isBlank(employeeNo)) {
			return null;
		}
		return employeeRemoteService.getByJobNumber(employeeNo);
	}

	private String generateFileName(Recommend recommend, Employee employee, String suffix) {
		StringBuffer fileName = new StringBuffer(50);
		String reportName = null;
		String reportNameEnd = null;
		if (EnumAwaCompany.getEnumByValue(brandId) == EnumAwaCompany.AIMSEN) {
			reportName = MessageUtil.message("report.name", messageSource);
			reportNameEnd = MessageUtil.message("report.name.end", messageSource);
		} else if (EnumAwaCompany.getEnumByValue(brandId) == EnumAwaCompany.WALRE) {
			reportName = MessageUtil.message("report.walre.name", messageSource);
			reportNameEnd = MessageUtil.message("report.walre.name.end", messageSource);
		} else if (EnumAwaCompany.getEnumByValue(brandId) == EnumAwaCompany.ANLLE) {
			reportName = MessageUtil.message("report.anlle.name", messageSource);
			reportNameEnd = MessageUtil.message("report.anlle.name.end", messageSource);
		} else {
			reportName = MessageUtil.message("report.name", messageSource);
			reportNameEnd = MessageUtil.message("report.name", messageSource);
		}
		fileName.append(reportName);
		fileName.append("_");
		fileName.append(recommend.getClientname());
		fileName.append("_");
		fileName.append(recommend.getJobname());
		fileName.append("_");
		fileName.append(recommend.getResumename());
		fileName.append("_");
		fileName.append(DateTimeUtil.dateString(new Date(), "yyyyMMdd"));
		if (employee != null) {
			fileName.append("_");
			fileName.append(employee.getFullname());
		}
		fileName.append(reportNameEnd);
		fileName.append(".");
		fileName.append(suffix);
		return fileName.toString();
	}

	private Template getTemplate4Word() throws IOException {
		Configuration configuration = new Configuration();
		configuration.setDefaultEncoding("utf-8");
		configuration.setClassForTemplateLoading(RecommendService.class, "/template");
		String templatePath = null;
		if (EnumAwaCompany.getEnumByValue(brandId) == EnumAwaCompany.AIMSEN) {
			templatePath = WORD_FTL;
		} else if (EnumAwaCompany.getEnumByValue(brandId) == EnumAwaCompany.WALRE) {
			templatePath = WORD_WALRE_FTL;
		} else if (EnumAwaCompany.getEnumByValue(brandId) == EnumAwaCompany.ANLLE) {
			templatePath = WORD_ANLLE_FTL;
		} else {
			templatePath = WORD_FTL;
		}
		Template template = configuration.getTemplate(templatePath);
		return template;
	}

	private Template getTemplate4Pdf() throws Exception {
		Configuration configuration = new Configuration();
		configuration.setDefaultEncoding("utf-8");
		configuration.setClassForTemplateLoading(RecommendService.class, "/template");
		Template template = configuration.getTemplate("candidate/candidateReportTabPdf.ftl");
		return template;
	}

}
