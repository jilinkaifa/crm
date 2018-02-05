<%@ page language="java" contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page isELIgnored="false"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<div style="width: 60%; margin: 20px auto;">
		<form action="${website}up/upload"  enctype="multipart/form-data" method="post" class="common-form">
			<input type="file" name="item" >文件上传</input>
			<input type="hidden" name="path" value="/aaa/bbb/" />
			<input type="submit" value="提交">
		</form>
	</div>
</body>
</html>