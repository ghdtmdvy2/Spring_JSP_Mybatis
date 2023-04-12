<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<!-- 제이쿼리 불러오기 -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<head>
	<title>Home</title>
	<meta name="_csrf" content="${_csrf.token}" />
    <meta name="_csrf_header" content="${_csrf.headerName}" />
</head>
<body>
<form action="/user/logout.do" method="post">
	<button type="submit">로그아웃</button>
</form>
<P>  The time on the server is ${serverTime}. </P>
	<c:forEach var="item" items="${articleList}">
		<div id="article_${item.id}">
			<a href="${item.id}/article.do">상세 페이지</a>
			<form id="myForm_${item.id}" action="/article.do" method="post">
				<input type="hidden" name = "_method" value = "put"/> 
				<span>id : </span><input id="id_${item.id}" name="id" value="${item.id}" readOnly>
					</br>
				<span>username : </span><input id="username_${item.id}" value="${item.username}" readOnly>
					</br>
				<span>usersId : </span><input id="usersId_${item.id}" value="${item.usersId}" readOnly>
			  	  	</br>
				<span>subject :</span><input id="subject_${item.id}" name="subject" value="${item.subject}" readOnly>
				  	</br>
				<span>content : </span><input id="content_${item.id}" name="content" value="${item.content}" readOnly>
					</br>
				<c:if test="${item.usersId == userDto.id}">
					<button id="myButton_${item.id}" type="button" onclick="articleModify(${item.id})">수정</button>
				</c:if>	
	        </form>
	        	<c:if test="${item.usersId == userDto.id}">
			        <form id="myForm_delete_${item.id}"  method="post">
			        	<input type="hidden" name = "_method" value = "delete"/> 
			        	<input name="id" value=${item.id} hidden>
			        	<button type="submit">삭제</button>
			        	<%-- <button id="myButton_delete_${article.id}" type="button" onclick="articleDelete(${item.id})">삭제</button> --%>
			         </form>
		         </c:if>
	         
         </div>
	</c:forEach>
    <button onclick="getMoreArticle()">더보기</button>
    
	<form action="/article.do" method="post">
	            제목 : <input name="subject" value="test">
	            내용 : <input name="content" value="test">
    	<button type="submit">작성</button>
    </form>
</body>
 <script>
        function articleModify(articleId){
            const pathForm = document.getElementById("myForm_"+articleId);
            const button = document.getElementById("myButton_"+articleId);
            const parent = document.querySelector("#article_"+articleId);
            console.log(parent);
            const inputList = parent.querySelectorAll("input");
            // const articleContent = inputList[3];
            // const articleSubject = inputList[4];
            const articleContent = document.querySelector("#content_"+articleId);
            const articleSubject = document.querySelector("#subject_"+articleId);
            console.log(articleContent);
            console.log(articleSubject);
            const newButton = document.createElement('button');
            // id = "button_${articleId}"
            newButton.setAttribute('id', 'button_' + articleId);
         	// type = "submit"
            newButton.setAttribute('type','submit');
         	// onclick = "button_submit(${articleId})"
            // newButton.setAttribute('onclick','button_submit('+ articleId + ')');
            newButton.innerHTML = '작성';
            if (button.innerText == "수정 취소") {
                button.innerText = "수정"
                articleContent.readOnly = true;
                articleSubject.readOnly = true;
                const deleteButton = document.getElementById('button_' + articleId);
                deleteButton.remove();
            } else {
                button.innerText = "수정 취소"
                articleContent.readOnly = false;
                articleSubject.readOnly = false;
                pathForm.appendChild(newButton);
            }
        }
        function articleDelete(articleId){
            const parent = document.querySelector("#article_"+articleId);
            parent.remove();
            $.ajax({
                type : 'delete',           // 타입 (get, post, put 등등)
                url : '/article/' ,           // 요청할 서버 url
                async : true,            // 비동기화 여부 (default : true)
                data : {
                    articleId : articleId
                }
                // csrf 적용 시 csrf 토큰 발급. 
                // ,
                // beforeSend: function(xhr) {
                //    const token = $("meta[name='_csrf']").attr("content");
                //    const header = $("meta[name='_csrf_header']").attr("content");
                //    xhr.setRequestHeader(header, token);
                // }
                ,
                success : function(result) { // 결과 성공 콜백함수
                    console.log(result);
                },
                error : function(request, status, error) { // 결과 에러 콜백함수
                    console.log(error)
                }
            })
        }
        var displayNum = 0; // 게시글 보여주기 수
        function getMoreArticle(){
        	const addDisplayNum = 3; // 추가 게시글 보여주기 숫자
        	displayNum += addDisplayNum;
        	$.ajax({
                type : 'get',           // 타입 (get, post, put 등등)
                dataType : 'json',
                url : '/article.do' ,           // 요청할 서버 url
                async : true,            // 비동기화 여부 (default : true)
                data : {
                	startIdx : displayNum
                }
                // csrf 적용 시 csrf 토큰 발급. 
                // ,
                // beforeSend: function(xhr) {
                //    const token = $("meta[name='_csrf']").attr("content");
                //    const header = $("meta[name='_csrf_header']").attr("content");
                //    xhr.setRequestHeader(header, token);
                // }
                ,
                success : function(result) { // 결과 성공 콜백함수
                	// const articleList = result.articles;
                    // console.log(articleList);
                    console.log("success");
                },
                error : function(request, status, error) { // 결과 에러 콜백함수
                    console.log(error)
                }
            })
        }
    </script>
</html>
