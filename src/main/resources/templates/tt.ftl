<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"></meta>
  <title>anlle-推荐报告模板</title>
  <style type="text/css">  
		body{  
   			 font-family: SimSun;  
		}  
 </style>
</head>
<body style="background: #eee;">
<div style="width: 595px;min-height: 200px;background-color: #fff;margin: 0 auto;">
  <!--header-->
  <div style="position: relative;height: 140px;">
    <div style="width: 100%;height: 60px;border: 1px dashed #eee;">
      <img src="" alt="" width="84" height="34" style="position: absolute;top: 16px;left: 32px;"></img>
      <p style="position: absolute;top: 12px;right: 44px;font-size: 8px;color: #666;">
        推荐顾问：${employeeName!''} - ${mobile!''} <span style="margin-left: 10px;">${recomDate!''}</span>
      </p>
    </div>
    <div style="height: 83px;text-align: center;overflow: hidden;margin-top: 10px;position: relative;">
      <strong style="font-size: 24px;color: #333333;letter-spacing: 4px;">候选人推荐报告</strong>
      <p style="font-size: 8px;color: #666;margin-top: 4px;">推荐职位：<span>${jobName!''}
      <#if jobAddress??>
      	（${jobAddress}）
      </#if>
      </span></p>
      <img src="" alt="" width="50" height="50" style="position: absolute;top: 6px;right: 140px;"></img>
    </div>
  </div>
  <!--base-->
  <div style="margin: 0 43px 0 52px;position: relative;min-height: 100px;overflow: hidden;">
    <img src="" alt="" width="500" height="26"></img>
    <div style="margin: 0 0 24px 22px;min-height: 120px;width: 100%;overflow: hidden;">
      <div style="width: 240px;font-size: 12px;color: #333;float: left;margin-top: -4px;">
        <p style="line-height: 30px;">
          <span style="display:block; width: 60px;float: left;text-align: right;">姓名：</span>
          <span style="display:block; width: 170px;float: left;padding-left: 10px;">${resumeInfo.name!''}</span>
        </p>
        <p style="line-height: 30px;">
          <span style="display:block; width: 60px;float: left;text-align: right;">年龄：</span>
          <span style="display:block; width: 170px;float: left;padding-left: 10px;">${resumeInfo.age!''}</span>
        </p>
        <p style="line-height: 30px;">
          <span style="display:block; width: 60px;float: left;text-align: right;">所在城市：</span>
          <span style="display:block; width: 170px;float: left;padding-left: 10px;">${resumeInfo.workCity!''}</span>
        </p>
        <#if resumeInfo.hometownCity??>
        <p style="line-height: 30px;">
          <span style="display:block; width: 60px;float: left;text-align: right;">户籍地址：</span>
          <span style="display:block; width: 170px;float: left;padding-left: 10px;">${resumeInfo.hometownCity!''}</span>
        </p>
        </#if>
      </div>
      <div style="width: 150px;font-size: 12px;color: #333;float: left;margin-top: -4px;">
        <p style="line-height: 30px;">
          <span style="display:block; width: 60px;float: left;text-align: right;">性别：</span>
          <span style="display:block; width: 80px;float: left;padding-left: 10px;">${resumeInfo.gender!''}</span>
        </p>
        <p style="line-height: 30px;">
          <span style="display:block; width: 60px;float: left;text-align: right;">婚姻状态：</span>
          <span style="display:block; width: 80px;float: left;padding-left: 10px;">${resumeInfo.maritalState!''}</span>
        </p>
        <p style="line-height: 30px;">
          <span style="display:block; width: 60px;float: left;text-align: right;">工作年限：</span>
          <span style="display:block; width: 80px;float: left;padding-left: 10px;">${resumeInfo.workYears!''}</span>
        </p>
      </div>
      <div style="margin-top: 15px;float:left;">
      	<#if resumeInfo.headImg??>
      		<img src="data:image/png;base64,${resumeInfo.headImg!''}" alt="" width="76" height="76" style="margin-left: 12px;"></img>
      	</#if>
      </div>
    </div>
  </div>
  <!--actuality-->
  <div style="margin: 0 43px 0 52px;position: relative;min-height: 100px;overflow: hidden;">
    <img src="" alt="" width="500" height="26"></img>

<table cellspacing="0" cellpadding="0" style="font-size: 12px;min-height: 540px;margin: -5px 9px 20px 9px;">
      <tbody>
      <tr style="width: 100%;height: 24px;line-height: 24px;">
        <td style="width: 90px;text-align: center;"><strong>目前年薪：</strong></td>
        <td style="width: 178px;padding: 5px;">${resumeReport.curSalary!''}</td>
        <td style="width: 90px;text-align: center;"><strong>希望薪资：</strong></td>
        <td style="width: 178px;padding: 5px;">${resumeReport.expSalary!''}</td>
      </tr>
      <tr style="width: 100%;height: 24px;line-height: 24px;">
        <td style="width: 90px;text-align: center;"><strong>上岗时间：</strong></td>
        <td style="width: 178px;padding: 5px;">${resumeReport.expectEntryTime!''}</td>
        <td style="width: 90px;text-align: center;"><strong>面试时间：</strong></td>
        <td style="width: 178px;padding: 5px;">${resumeReport.expectInterviewTime!''}</td>
      </tr>
      <tr style="width: 100%;height: 24px;line-height: 24px;">
        <td style="width: 90px;text-align: center;"><strong>薪酬结构：</strong></td>
        <td colspan="3" style="width: 178px;padding: 5px;line-height: 20px;">${resumeReport.remunerationAnalysis!''}</td>
      </tr>
      <tr style="width: 100%;height: 24px;line-height: 24px;">
        <td style="width: 90px;text-align: center;"><strong>核心优势：</strong></td>
        <td colspan="3"  style="width: 178px;padding: 5px;line-height: 22px;">${resumeReport.advantageAnalysis!''}</td>
      </tr>
      <tr style="width: 100%;height: 24px;line-height: 24px;">
        <td style="width: 90px;text-align: center;"><strong>职业经历：</strong></td>
        <td colspan="3" style="width: 178px;padding: 5px;line-height: 22px;">${resumeReport.jobAnalysis!''}</td>
      </tr>
      <tr style="width: 100%;height: 24px;line-height: 24px;">
        <td style="width: 90px;text-align: center;"><strong>专业能力：</strong></td>
        <td colspan="3" style="width: 178px;padding: 5px;line-height: 22px;">${resumeReport.professionalAnalysis!''}</td>
      </tr>
      <tr style="width: 100%;height: 24px;line-height: 24px;">
        <td  style="width: 90px;text-align: center;"><strong>职业发展：</strong></td>
        <td colspan="3" style="width: 178px;padding: 5px;line-height: 22px;">${resumeReport.jobdevelopmentAnalysis!''}</td>
      </tr>
      <tr style="width: 100%;height: 24px;line-height: 24px;">
        <td style="width: 90px;text-align: center;"><strong>职业意向：</strong></td>
        <td colspan="3" style="width: 178px;padding: 5px;line-height: 22px;">${resumeReport.jobintentionAnalysis!''}</td>
      </tr>
      <tr style="width: 100%;height: 24px;line-height: 24px;">
        <td style="width: 90px;text-align: center;"><strong>性格特点：</strong></td>
        <td colspan="3" style="width: 178px;padding: 5px;line-height: 22px;">${resumeReport.characterAnalysis!''}</td>
      </tr>
      <tr style="width: 100%;height: 24px;line-height: 24px;">
        <td  style="width: 90px;text-align: center;"><strong>家庭情况：</strong></td>
        <td colspan="3" style="width: 178px;padding: 5px;line-height: 22px;">${resumeReport.familyAnalysis!''}</td>
      </tr>
      <#if resumeReport.offlineOffer??>
      <tr style="width: 100%;height: 24px;line-height: 24px;">
        <td style="width: 90px;text-align: center;"><strong>已有offer：</strong></td>
        <td colspan="3" style="width: 178px;padding: 5px;line-height: 22px;">${resumeReport.offlineOffer!''}</td>
      </tr>
      </#if>
      </tbody>
    </table>
  </div>
  <!--education-->
  <div style="margin: 0 43px 0 52px;position: relative;min-height: 100px;overflow: hidden;">
    <img src="" alt="" width="500" height="26"></img>
	<#if educationExperiences?? && (educationExperiences?size > 0)>
    <#list educationExperiences as educationExperience>
    <div style="margin: 0 75px  30px 10px;">
      <div style="width: 100%;min-height: 40px;margin: 10px 0 14px 10px;">
        <div style="color: #333;position: relative;margin-left: 6px; font-size: 14px;">
          <img src="" alt="" width="6" height="6" style="position: absolute;top: 6px;left: -12px;"></img>
          <strong>${educationExperience.graduationSchool!''}</strong>
          <strong style="margin-left: 10px;">${educationExperience.timeRange!''}</strong>
        </div>
        <div style="margin-top: 10px;color: #555;margin-left: 6px;font-size: 12px;">
          ${educationExperience.degree!''}
          <span style="margin-left: 24px;">${educationExperience.professional!''}</span>
        </div>
      </div>
    </div>
    </#list>
    </#if>
  </div>
  <!--work-->
  <div style="margin: 0 43px 0 52px;position: relative;min-height: 100px;overflow: hidden;">
    <img src="" alt="" width="500" height="26"></img>
<#if workExperiences?? && (workExperiences?size > 0)>
     <#list workExperiences as workExperiences>
    <div style="margin: 0 75px  30px 0px;">
      <div style=" min-height: 80px;margin: 10px 0 0 28px;">
        <div style="margin-bottom: 5px;position: relative;">
          <img src="" alt="" width="6" height="6" style="position: absolute;top: 8px;left: -12px;"></img>
          <strong style="color: #333;font-size: 14px;position: relative;">${workExperiences.timeRange!''}</strong>
          <span  style=" margin-left: 10px;color: #999;font-size: 12px;">(${workExperiences.duration!''})</span>
        </div>
        <div  style="font-size: 14px; color: #333;">
          <strong>${workExperiences.companyName!''}</strong>
          <span style="margin-left: 15px;">${workExperiences.jobName}</span>
        </div>
        <div style="width: 438px;margin: 10px 0;font-size: 12px;color: #333;line-height: 24px;">${workExperiences.companyIntroduce!''}</div>
        <#if workExperiences.department?? && workExperiences.reportPeople?? && workExperiences.underPeople??>
        <div style="font-size: 12px;color: #333;">
          <strong>工作关系：</strong>
          <ul style="width: 100%;height: 20px;margin: 8px 0 15px -40px;">
          	<#if workExperiences.department??>
            <li style="float: left;width: 32%;list-style: none;">
              <span>所属部门：</span>
              <span>${workExperiences.department!''}</span>
            </li>
            </#if>
            <#if workExperiences.reportPeople??>
            <li style="width: 32%;float: left;list-style: none;">
              <span>汇报对象：</span>
              <span>${workExperiences.reportPeople!''}</span>
            </li>
            </#if>
            <#if workExperiences.underPeople??>
            <li style="width: 32%;float: left;list-style: none;">
              <span>下属人数：</span>
              <span>${workExperiences.underPeople!''}</span>
            </li>
            </#if>
          </ul>
        </div>
        </#if>
        <#if workExperiences.workContent??>
        <div style="font-size: 12px;color: #333;">
          <strong>工作职责：</strong>
          <div style="margin: 8px 0 15px 0;color: #555;line-height: 24px;">
            ${workExperiences.workContent!''}
          </div>
        </div>
        </#if>
        <#if workExperiences.workAchievement??>
        <div style="font-size: 12px; color: #333;">
          <strong>工作业绩：</strong>
          <p style="margin: 8px 0 15px 0;">${workExperiences.workAchievement!''}</p>
        </div>
        </#if>
        <#if workExperiences.dimissionReason?? && workExperiences.dimissionReason??>
        <div style="font-size: 12px;margin-bottom: 14px;color: #333;">
          <strong style="margin-bottom: 8px;">
			 <#if workExperiences.timeRange?? && workExperiences.timeRange?contains("至今")>
            	求职动机：
            <#else>
            	离职原因：
            </#if>
		  </strong>
          <p>${workExperiences.dimissionReason!''}</p>
        </div>
        </#if>
      </div>
    </div>
    </#list>
    </#if>
  </div>
  <!--project-->
  <#if projectExperiences?? && (projectExperiences?size > 0)>
  <div style="margin: 0 43px 0 52px;position: relative;min-height: 100px;overflow: hidden;">
    <img src="" alt="" width="500" height="26"></img>
    <#list projectExperiences as projectExperiences>
    <div  style="margin: 0 75px  30px 0px;">
      <div  style="min-height: 80px;margin: 10px 0 0 28px;">
        <div style="margin-bottom: 5px;color: #333;font-size: 14px;position: relative;">
          <img src="" alt="" width="6" height="6" style="position: absolute;top: 6px;left: -12px;"></img>
          <strong  style="position: relative;">${projectExperiences.timeRange!''}</strong>
          <strong style="margin-left: 10px;">${projectExperiences.projectName!''}</strong>
        </div>
        <div style="margin: 10px 0;font-size: 12px;color: #333;line-height: 24px;">${projectExperiences.projectDesc!''}</div>
        <div style="font-size: 12px;">
          <strong style="color: #333;">项目职责：</strong>
          <div style="margin: 8px 0 15px 0;color: #555;line-height: 24px;">
            ${projectExperiences.responsibilityDesc!''}
          </div>
        </div>
      </div>
    </div>
    </#list>
  </div>
  </#if>
  <!--train-->
  <#if trainExperiences?? && (trainExperiences?size > 0)>
  <div style="margin: 0 43px 0 52px;position: relative;min-height: 100px;overflow: hidden;">
    <img src="" alt="" width="500" height="26"></img>
    <#list trainExperiences as trainExperiences>
    <div  style="margin: 0 75px  30px 0px;">
      <div  style="min-height: 20px;margin: 10px 0 0 28px;">
        <div style="margin-bottom: 5px;color: #333;font-size: 14px;position: relative;">
          <img src="" alt="" width="6" height="6" style="position: absolute;top: 6px;left: -12px;"></img>
          <strong  style="position: relative;">${trainExperiences.timeRange!''}</strong>
          <strong style="margin-left: 10px;">${trainExperiences.trainingName!''}</strong>
        </div>
        <div style="font-size: 12px;color: #333;margin-top: -6px;">
          <div>
            <p style="width: 49%;display: inline-block;vertical-align: top;line-height: 20px;"><span style="width: 62px;display: table-cell;">培训证书：</span><span style="display: table-cell;">${trainExperiences.trainingCertificate}</span></p>
            <p style="width: 49%;display: inline-block;vertical-align: top;line-height: 20px;"><span style="width: 62px;display: table-cell;">培训机构：</span><span style="display: table-cell;">${trainExperiences.trainingOrganization!''}</span></p>
          </div>
          <#if trainExperiences.trainingPlace??><div style="width: 100%;margin-bottom: 15px;height: 16px;">培训地点：<span>${trainExperiences.trainingPlace!''}</span></div></#if>
          <#if trainExperiences.trainingDesc><div style="margin-bottom: 15px;">培训描述：<span>${trainExperiences.trainingDesc!""}</span></div></#if>
        </div>
      </div>
    </div>
    </#list>
  </div>
  </#if>
  <!--language-->
  <#if language??>
  <div style="margin: 0 43px 0 52px;position: relative;min-height: 100px;overflow: hidden;">
    <img src="" alt="" width="500" height="26"></img>
    <div  style="margin: 0 75px  30px 0px;">
      <div style="min-height: 20px;margin: 10px 0 30px 18px;position: relative">
        <p style="font-size: 12px;color: #333;">${language!''}</p>
      </div>
    </div>
  </div>
  </#if>
</div>
</body>
</html>
