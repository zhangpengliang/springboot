<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"></meta>
    <title>Title</title>
    <style>  
		body{  
   			 font-family: SimSun;  
		}  
</style> 
</head>
<body>
	this is index page！name:${name},sex:
						<#if sex = 1 >
									男
						<#else>
									女
						</#if>
 	<div style="heigth:80px">
		<!--<img src="http://pic29.nipic.com/20130601/12122227_123051482000_2.jpg"></img>-->
 	</div>
  	<div style="height:70px;margin:0 10px ">
            <h4 style="float:right;margin-top:45px;font-family:SimSun">机遇成就价值</h4>
    </div>
  
  <!-- 1、导出使用网络上的图片连接可以直接下载显式，2、使用base64不行,必须使用绝对路径,但是这样html页面展示就没法展示了。-->
   <img style="float: left;margin-top:15px;width:250px;height:40px" 
			src="file:///C:/java/workspace/boot-demo/target/classes/img/baobao.jpg"/>

	<div>
		freemarker的内置函数：${func}
	</div>		
	
			
			
			
 
</body>
</html>