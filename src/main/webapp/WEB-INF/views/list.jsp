<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Translation DataBase Insert</title>
</head>
<style>
#th {
	background: #ccccff;
}

#table {
	border-radius: 25px;
	border: 2px solid #73AD21;
	padding: 20px;
	width: 200px;
	height: 150px;
}
</style>
<body>
	<script>
		function Translation() {
			var source = document.querySelector('#source');
			var traget = document.querySelector('#target');
			var text = document.querySelector('#text');
			var url = '/translation/' + target.value + '/' + source.value + '/'
					+ text.value;
			//url += encodeURI(text.value);

			var xhr = new XMLHttpRequest();
			xhr.open('GET', url);
			xhr.onreadystatechange = function() {
				if (xhr.readyState == xhr.DONE && xhr.status == 200) {
					console.log(xhr.response);
					var res = JSON.parse(xhr.response);
					console.log(res);
					console.log(res.tgtext);
					var result = document.querySelector('#result');
					result.value = res.TH_RESULT;
				}
			}
			xhr.send();
		}

		/* function check() {
			var selectObj1 = document.querySelector('#source');
			var selectObj2 = document.querySelector('#target');
			var text = document.querySelector('#text');
			if (selectObj1.value == selectObj2.value) {
				alert('번역할 언어와 번역 될 언어가 같습니다.\n 다시 선택해주세요.');
				return false;
			}
			var textObj = document.querySelector('#text');
			if (textObj.value.length >= 100) {
				alert('번역할 내용은 100글자 이상이므로 번역할수없습니다.');
				return false;
			}

			return true;
		} */
	</script>
	<div align="center">
		<h1>Translation DB</h1>
	</div>
	<div align="center">
		<table border="1" id="table">
			<tr>
				<th id="th">번역할언어</th>
				<td align="center"><select name="source" id="source"
					style="width: 200px; height: 30px;">
						<option value="en">영어
						<option value="ko">한국어
				</select></td>
				<td rowspan="2"><button onclick="Translation()">번역</button></td>
				<th id="th">번역될언어</th>
				<td align="center"><select name="target" id="target"
					style="width: 200px; height: 30px;">
						<option value="ko">한국어
						<option value="en">영어
						<option value="zh-TW">중국어번체
						<option value="es">스페인어
						<option value="fr">프랑스어
						<option value="ru">러시아어
				</select></td>
			</tr>
			<tr>
				<td colspan="2"><textarea name="text" id="text"
						style="width: 500px; height: 500px;">${param.text}</textarea></td>
				<td colspan="2"><textarea id="result"
						style="width: 500px; height: 500px;"></textarea></td>
			</tr>
		</table>
		${oList};
	</div>
	

</body>
</html>

<!-- 
code
('한국어','ko')
('영어','en')
('일본어','jp')
('중국어간체','zn-CN')
('중국어번체','zh-TW')
('스페인어','es')
('프랑스어','fr')
('러시아어','ru')
('베트남어','vi')
('태국어','th')
('인도네시아어','id')
('독일어','de')
('이탈리아어','it')
 -->

