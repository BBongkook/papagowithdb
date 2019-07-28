<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Translation DataBase Insert</title>

<title>Bootstrap Example</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
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
		

		function search() {
			var xhr = new XMLHttpRequest();
			xhr.open('GET', '/translations');
			xhr.onreadystatechange = function() {
				if (xhr.readyState == xhr.DONE && xhr.status == 200) {
					var res = JSON.parse(xhr.response);
					var html = '';
					for(var cl of res){
						html += '<tr>';
						html += '<td>' + cl.TH_NUM + '</td>';
						html += '<td>' + cl.TH_SOURCE + '</td>';
						html += '<td>' + cl.TH_TARGET + '</td>';
						html += '<td>' + cl.TH_TEXT + '</td>';
						html += '<td>' + cl.TH_RESULT + '</td>';
						html += '<td>' + cl.TH_COUNT + '</td>';
						html += '</tr>';
					}
					document.querySelector('#tBody').innerHTML = html;
				}
			}
			xhr.send();
		}
		search();
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
		<div id="rDiv">
			
		</div>
		<button onclick="search()">새로고침</button>
	</div>
	
<%-- 	<table border="1">
	<tr>
			<th>번호</th>
			<th>SOURCE</th>
			<th>TARGET</th>
			<th>번역 언어</th>
			<th>번역된 언어</th>
			<th>조회수</th>
		</tr>
<c:forEach var="co" items="${cList}" >
		<tr>
			<td>"${co.TH_NUM}"</td>
			<td>"${co.TH_SOURCE}"</td>
			<td>"${co.TH_TARGET}"</td>
			<td>"${co.TH_TEXT}"</td>
			<td>"${co.TH_RESULT}"</td>
			<td>"${co.TH_COUNT}"</td>
		</tr>
</c:forEach>
</table> --%>

<div class="container">
<table class="table" width="100%" align="center">
	<thead class="thead-dark">
		<th>번호</th>
		<th>SOURCE</th>
		<th>TARGET</th>
		<th>번역 언어</th>
		<th>번역된 언어</th>
		<th>조회수</th>
	</thead>
	<tbody id="tBody"></tbody>
	</thead>
</table>
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

