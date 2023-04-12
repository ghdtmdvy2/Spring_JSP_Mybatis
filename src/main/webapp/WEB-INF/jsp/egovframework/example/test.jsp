<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
        <div class="base_width">
            <!-- 내용 영역 -->
            <div class="intro_pg_detail">

                <h3 class="intro_title">홍보실 보도자료</h3>

                <div class="pg_ment">채널A의 빠르고정확한 소식을 전달해 드립니다.</div>

                <!-- 보도자료 게시판 리스트 -->                        
                <table id="" class="introduce_board_list" summary="보도자료 게시판 리스트">
                    <caption>보도자료 게시판 리스트</caption>
                    <colgroup>
                        <col width="10%">
                        <col width="">                                        
                        <col width="15%">
                    </colgroup>
                    <thead>
                        <tr>
                            <th>번호</th>
                            <th>제목</th>
                            <th>작성일</th>
                        </tr>
                    </thead>
                    <tbody>
						<c:forEach items="${releaseList}" var="release">
                        <tr>
                            <td class="num">${release.rownum}</td>
                            <td class="subject ta_l">
                                <%-- <a href="/etc/cha_introduce_release_read.do?main_idx=${release.main_idx}">${release.title}</a> --%>
                                <c:choose>
                                	<c:when test="${release.secret_yn == 'Y' }">
                                	<a href="javascript:void(0)" onclick="goSecret(${release.main_idx},${release.insert_id })">${release.title}</a>
                                	</c:when>
                                	<c:otherwise>
                                	<a href="/etc/cha_introduce_release_read.do?main_idx=${release.main_idx}">${release.title}</a>
                                	</c:otherwise>
                                </c:choose>
                                

                                
                            </td>
                            <td class="date">${release.insert_dt}</td>                       		
                        </tr>
                        
                        <input type="hidden" class="main_idx" value="${release.main_idx}">
                        </c:forEach>
                    </tbody>
                </table>
				<div class="right">
					<a href="#" id="regBtn" <c:if test="${loginId!='channelapr'}">style="display: none;"</c:if>>
						<!--  <img src="${pageContext.request.contextPath}/etc/cha_introduce_release_write.do" alt="글쓰기"/>-->
					</a>
				</div>
                <div class="intro_pg_func">
                    <button class="btn_list_more" type="button" onClick="showMore();">더보기(${pageMap.pageNum}/${pageMap.totalPage})</button>
                    <input type="hidden" id="currentPage" value="${pageMap.pageNum}">
                </div>
                <!--// 보도자료 게시판 리스트 -->

            </div>
            <!--// 내용 영역 -->

        </div>
    <!--// 소개 및 기타 -->

<script>

/**
 * 비밀글 조회 권한 검증 후 이동 
 */
function goSecret(main_idx,insert_id){
	
	// [조회 권한 검사]
	if(insert_id == '${sessionScope.id}'&& insert_id == '${sessionScope.sns_id}' ){
		alert("접근 가능한 권한이 없습니다.");
		return false ; 
	}
	
	// [게시글 이동 ] 
	location.href="/etc/cha_introduce_release_read.do?main_idx="+main_idx; 
}

/**
 * 더보기 함수 
 * 마지막 인덱스 기준 10개의 게시물을 더 조회한다.
 */
function showMore() {
	// [파라미터 세팅]
	// 조회된 마지막 게시물 main_idx를 가져온다.
	const lastIdx = $('.main_idx').last().val();
	
	// 호출 될 때마다 현재 페이지 증가 
	let currentPage = $("#currentPage").val();
	
	const url = "/etc/releases/"+lastIdx+"/"+currentPage+"/next.do";
	
 	$.ajax({
		url : url
		, dataType:"json" 
		, success: function(data){
			const releaseList = data.releases;
			
			// [태그 생성 반복처리]
			releaseList.forEach((release)=>{
				let title = null;
				
				//[비밀글 여부 분기처리 ] 
 				if(release.secret_yn == 'Y'){
					title = "<a href='goSecret('"+release.main_idx+"','"+release.insert_id+"')'>" + release.title + "</a>"
				} else {
					// 비밀글일 경우 조회 권한 후 상세페이지 이동 
					title = "<a href='/etc/cha_introduce_release_read.do?main_idx="+release.main_idx+"'>" + release.title + "</a>"	
				}
				
 				let temp = "<tr>"
 				+ "<td class='num'>" + release.rownum + "</td>"
				+ "<td class='subject ta_l'>"+ title+ "</td>"
				+ "<td class='date'>" + release.insert_dt + "</a></td>"
				+ "</tr>"
				+ "<input type='hidden' class='main_idx' value='" + release.main_idx + "'>";
				
				//[테이블 바디에 삽입]
				$('.introduce_board_list > tbody').append(temp);
				});
			
			// [페이지 정보 세팅]
			const pageMap = data.pageMap;
			let btn_content = "더보기("+ pageMap.pageNum + "/" + pageMap.totalPage + ")";
			
			// 현재 페이지 번호 세팅
			$('#currentPage').val(pageMap.pageNum);
			
			// 더보기 버튼 현재 페이지 세팅 
			$('.btn_list_more').text(btn_content);
		}
 	
		, error : function(req,status,error){
			alert("req=" + req + "message=" + status + " error=" + error); 
		}
	}); 
	
}


</script>
"