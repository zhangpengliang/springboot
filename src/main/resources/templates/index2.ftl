<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"></meta>
  <title>walre-推荐报告模板</title>
  <style type="text/css">  
		body{  
   			 font-family: SimSun;  
		}  
 </style>
</head>
<body style="background: #eee;">
<div style="width: 595px;min-height: 200px;background-color: #fff;margin: 0 auto;">
  <!--header-->
  <div style="width: 100%;height: 136px;text-align: center;overflow: hidden;position: relative;">
    <p style="font-size: 26px;color: #222;letter-spacing: 4px;margin-top: 32px;">
      <strong> <span style="display: inline-block;width: 210px;padding-bottom: 4px;border-bottom: 1px solid #313131;">候选人推荐报告</span></strong>
    </p>
    <img src="" alt="" width="150" height="24" style="position: absolute;top: 78px;left: 220px;"></img>
  </div>
  <!--base-->
  <div style="margin: 0 65px 24px 56px;width: 100%;min-height: 300px;overflow: hidden;">
      <div>
        <img src="" alt="" width="80" height="20"></img>
      </div>
      <div style="margin-left: 8px;border: 1px dashed #B6C1CC;width: 467px;height: 312px;margin-top: 10px;position: relative;">
        <div style="width: 60px;height:75px;text-align: center;position: absolute;top: -30px;right: 10px;">
          <img src="" alt="" width="60" height="60"></img>
          <img src="" alt="" width="42" height="16"></img>
        </div>
        <div style="width: 400px;height: 76px;position: absolute;top: 32px;left: 32px;">
        <#if resumeInfo.headImg??>
         	<img src="data:image/png;base64,${resumeInfo.headImg!''}" alt="" width="76" height="76" style="margin-right: 20px;float: left;"></img>
        </#if>
          <div style="float: left;width: 300px;padding:0;">
            <p style="width: 300px;font-size: 18px;color: #222;line-height: 20px;margin:18px 0 0 0;"><strong>${resumeInfo.name!''}</strong></p>
            <p style="width: 300px;font-size: 14px;color: #222;line-height: 20px;margin: 10px 0 0 0;">推荐职位：<span>${jobName!''}</span></p>
          </div>
        </div>
        <div style="margin-left: 32px;margin-top: 120px;">
          <div style="width: 220px;float: left;font-size: 12px;color: #333;">
            <p style="line-height: 30px;">
              <span style="display: block;width: 60px;float: left;">年<span style="margin-left: 24px;">龄：</span></span>
              <span style="display:block;width: 160px;float: left;">${resumeInfo.age!''}</span>
            </p>
            <p style="line-height: 30px;">
              <span style="display: block;width: 60px;float: left;">工作年限：</span>
              <span style="display:block;width: 160px;float: left;">${resumeInfo.workYears!''}</span>
            </p>
            <p style="line-height: 30px;">
              <span style="display: block;width: 60px;float: left;">所在城市：</span>
              <span style="display:block;width: 160px;float: left;">${resumeInfo.workCity!''}</span>
            </p>
            <p style="line-height: 30px;">
              <span style="display: block;width: 60px;float: left;">目前薪资：</span>
              <span style="display:block;width: 160px;float: left;">${resumeReport.curSalary!''}</span>
            </p>
            <p style="line-height: 30px;">
              <span style="display: block;width: 60px;float: left;">面试时间：</span>
              <span style="display:block;width: 160px;float: left;">${resumeReport.expectInterviewTime!''}</span>
            </p>
          </div>
          <div style="width: 200px;float: left;font-size: 12px;color: #333;">
            <p style="line-height: 30px;">
              <span style="display: block;width: 60px;float: left;">性 <span style="margin-left: 15px;">别：</span></span>
              <span style="display:block;width: 140px;float: left;">${resumeInfo.gender!''}</span>
            </p>
            <p style="line-height: 30px;">
              <span style="display: block;width: 60px;float: left;">婚姻状态：</span>
              <span style="display:block;width: 140px;float: left;">${resumeInfo.maritalState!''}</span>
            </p>
            <#if resumeInfo.hometownCity??>
            <p style="line-height: 30px;">
              <span style="display: block;width: 60px;float: left;">户籍地址：</span>
              <span style="display:block;width: 140px;float: left;">${resumeInfo.hometownCity!''}</span>
            </p>
            </#if>
            <p style="line-height: 30px;">
              <span style="display: block;width: 60px;float: left;">期望薪资：</span>
              <span style="display:block;width: 140px;float: left;">${resumeReport.expSalary!''}</span>
            </p>
            <p style="line-height: 30px;">
              <span style="display: block;width: 60px;float: left;">上岗时间：</span>
              <span style="display:block;width: 140px;float: left;">${resumeReport.expectEntryTime!''}</span>
            </p>
          </div>
        </div>
      </div>
    </div>
  <!--education-->
  <#if educationExperiences?? && (educationExperiences?size > 0)>
  <div style="margin: 0 63px 24px 54px;min-height: 40px;overflow: hidden;">
    <img src="" alt="" width="478" height="22"></img>
     <#list educationExperiences as educationExperience>
    <div>
      <div style="width: 100%;min-height: 40px;margin: 10px 0 14px 12px;">
        <div style="color: #333;position: relative;margin-left: 6px; font-size: 14px;">
          <img src="" alt="" width="6" height="6" style="position: absolute;top: 7px;left: -10px;"></img>
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
  </div>
  </#if>
  <!--actuality-->
  <div style="margin: 0 63px 24px 54px;min-height: 152px;overflow: hidden;">
    <img src="" alt="" width="478" height="22"></img>
    <div style="margin: 10px 0 0 20px;font-size: 12px;color: #333;">
      <strong>薪酬结构：	</strong>
      <p>${resumeReport.remunerationAnalysis!''}</p>
      <strong>核心优势：	</strong>
      <p>${resumeReport.advantageAnalysis!''}</p>
      <strong>职业经历：</strong>
      <p>${resumeReport.jobAnalysis!''}</p>
      <strong>专业能力：</strong>
      <p>${resumeReport.professionalAnalysis!''}</p>
      <strong>职业发展：</strong>
      <p>${resumeReport.jobdevelopmentAnalysis!''}</p>
      <strong>职业意向：</strong>
      <p>${resumeReport.jobintentionAnalysis!''}</p>
      <strong>性格特点：</strong>
      <p>${resumeReport.characterAnalysis!''}</p>
      <strong>家庭情况：</strong>
      <p>${resumeReport.familyAnalysis!''}</p>
      <#if resumeReport.offlineOffer??>
      <strong>已有offer：</strong>
      <p>${resumeReport.offlineOffer!''}</p>
      </#if>
    </div>
  </div>
  <!--work-->
  <div style="margin: 0 63px 24px 54px;min-height: 152px;overflow: hidden;">
    <img src="" alt="" width="478" height="22"></img>
 <#if workExperiences?? && (workExperiences?size > 0)>
    <#list workExperiences as workExperiences>
    <div>
      <div style=" min-height: 80px;margin: 10px 0 0 16px;">
        <div style="margin-bottom: 5px;position: relative;">
          <img src="" alt="" width="6" height="6" style="position: absolute;top: 8px;left: -10px;"></img>
          <strong style="color: #333;font-size: 14px;position: relative;">${workExperiences.timeRange!''}</strong>
          <span  style=" margin-left: 10px;color: #999;font-size: 12px;">(${workExperiences.duration!''})</span>
        </div>
        <div  style="font-size: 14px;">
          <strong style=" color: #333;">${workExperiences.companyName!''}</strong>
          <span  style=" color: #333;margin-left: 15px;">${workExperiences.jobName}</span>
        </div>
        <div style="width: 438px;margin: 15px 0;font-size: 12px;color: #333;line-height: 24px;">${workExperiences.companyIntroduce!''}</div>
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
        <div style="font-size: 12px;color: #333;">
          <strong>工作业绩：</strong>
          <p style="margin: 8px 0 15px 0;">${workExperiences.workAchievement!''}</p>
        </div>
        </#if>
        <#if workExperiences.dimissionReason?? && workExperiences.dimissionReason??>
        <div style="color: #333; font-size: 12px;margin-bottom: 14px;">
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
  <div style="margin: 0 63px 24px 54px;min-height: 152px;overflow: hidden;">
    <img src="" alt="" width="478" height="22"></img>
     <#list projectExperiences as projectExperiences>
    <div>
      <div  style="min-height: 80px;margin: 10px 0 0 16px;">
        <div style="margin-bottom: 5px;color: #333;font-size: 14px;position: relative;">
          <img src="" alt="" width="6" height="6" style="position: absolute;top: 6px;left: -10px;"></img>
          <strong  style="position: relative;">${projectExperiences.timeRange!''}</strong>
          <strong style="margin-left: 10px;">${projectExperiences.projectName!''}</strong>
        </div>
        <#if projectExperiences.projectDesc??>
        <div style="margin: 15px 0;font-size: 12px;color: #333;line-height: 24px;">${projectExperiences.projectDesc!''}</div>
        </#if>
        <#if projectExperiences.responsibilityDesc??>
        <div style="font-size: 12px;color: #333;">
          <strong>项目职责：</strong>
          <div style="margin: 8px 0 15px 0;color: #555;line-height: 24px;">
             ${projectExperiences.responsibilityDesc!''}
          </div>
        </div>
        </#if>
      </div>
    </div>
    </#list>
  </div>
  </#if>
  <!--train-->
  <#if trainExperiences?? && (trainExperiences?size > 0)>
  <div style="margin: 0 63px 24px 54px;min-height: 152px;overflow: hidden;">
    <img src="" alt="" width="478" height="22"></img>
    <#list trainExperiences as trainExperiences>
    <div>
      <div  style="min-height: 20px;margin: 10px 0 0 16px;">
        <div style="margin-bottom: 5px;color: #333;font-size: 14px;position: relative;">
          <img src="" alt="" width="6" height="6" style="position: absolute;top: 6px;left: -10px;"></img>
          <strong  style="position: relative;">${trainExperiences.timeRange!''}</strong>
          <strong style="margin-left: 10px;">${trainExperiences.trainingName!''}</strong>
        </div>
        <div style="font-size: 12px;color: #333;">
          <div style="height: 40px;">
            <p style="width: 49%;display: inline-block;">培训证书：<span>${trainExperiences.trainingCertificate}</span></p>
            <p style="width: 49%;display: inline-block;">培训机构：${trainExperiences.trainingOrganization!''}</p>
          </div>
          <#if trainExperiences.trainingPlace??>
          <div style="width: 100%;margin-bottom: 15px;height: 16px;">培训地点：<span>${trainExperiences.trainingPlace!''}</span></div>
          </#if>
          <#if trainExperiences.trainingDesc??>
          <div style="margin-bottom: 15px;">培训描述：<span>${trainExperiences.trainingDesc!""}</span></div>
          </#if>
        </div>
      </div>
    </div>
    </#list>
  </div>
  </#if>
  <!--language-->
  <#if language??>
  <div style="margin: 0 63px 24px 54px;min-height: 20px;overflow: hidden;">
    <img src="" alt="" width="478" height="22"></img>
    <div>
      <div style="min-height: 20px;margin: 10px 0 30px 18px;position: relative">
        <p style="font-size: 12px;color: #333;">${language!''}</p>
      </div>
    </div>
  </div>
  </#if>
  <!--gwMsg-->
  <div style="height: 40px;margin: 0 76px 0 0;">
    <p style="font-size: 12px;color: #333;float: right;">推荐顾问：${employeeName!''} - ${mobile!''}<span style="margin-left: 10px;">${recomDate!''}</span></p>
  </div>
</div>
</body>
</html>
