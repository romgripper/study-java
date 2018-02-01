<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<!DOCTYPE html> 
<html>
<head>
</head>
<body>
<h1><bean:write name="helloWorldForm" property="message" />
</h1>
</body>
</html>