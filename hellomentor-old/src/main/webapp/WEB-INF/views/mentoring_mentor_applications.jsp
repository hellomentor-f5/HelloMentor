<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath }"/>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
</head>
<body>
<div class="my_right_title">멘토 신청 목록</div>
<div class="my_mentorapp_content1">
    <c:forEach var="i" begin="1" end="12">
        <div class="my_mentorapp_box">
            <div class="my_mentorapp_box_1">
                <div class="my_following_profile_img">
                    <img src="${pageContext.request.contextPath}/resources/img/profile-img.jpeg"/>
                </div>
            </div>
            <div class="my_mentorapp_box_2">
                <div class="my_mentorapp_name">
                    imjaelin
                    <div class="my_mentorapp_favorite" onclick="toggleColor(this)">
                        ★
                    </div>
                </div>
                <div class="my_mentorapp_title">
                    제목입니다~~~~~
                </div>
                <div class="my_mentorapp_intro">
                    저는 잘 할 수 있습니다 <br>
                    제발 믿어주세요
                </div>
                <div class="my_mentorapp_btnbox">
                    <button type="submit" class="my_mentorapp_btn">요청 취소</button>
                </div>
            </div>
        </div>
    </c:forEach>




</div>

<div class="my_following_content2">
    <div class="my_following_paging">
        <span id="previous-page" onclick="movePage(-1)">이전</span>
        <span id="page-numbers"></span>
        <span id="next-page" onclick="movePage(1)">다음</span>
    </div>
</div>

<script>


    var favoriteElements = document.querySelectorAll('.my_mentorapp_favorite');
    favoriteElements.forEach(function(favoriteElement) {
        favoriteElement.addEventListener('click', function() {
            if (favoriteElement.classList.contains('clicked')) {
                favoriteElement.classList.remove('clicked');
            } else {
                favoriteElement.classList.add('clicked');
            }

            favoriteElement.classList.add('scale');
            setTimeout(function() {
                favoriteElement.classList.remove('scale');
            }, 200);
        });
    });

</script>

<%--페이징처리 --%>
<script>
    var currentPage = 1;
    var itemsPerPage = 4;
    var numberOfPages = Math.ceil(document.querySelectorAll('.my_mentorapp_box').length / itemsPerPage);

    function updatePage() {
        var start = (currentPage - 1) * itemsPerPage;
        var end = start + itemsPerPage;

        document.querySelectorAll('.my_mentorapp_box').forEach((item, index) => {
            item.style.display = index >= start && index < end ? '' : 'none';
        });

        var pageNumberContainer = document.getElementById('page-numbers');
        pageNumberContainer.innerHTML = '';

        var startPage = currentPage - 5;
        var endPage = currentPage + 4;

        if (startPage < 1) {
            startPage = 1;
            endPage = Math.min(10, numberOfPages);
        }

        if (endPage > numberOfPages) {
            endPage = numberOfPages;
            startPage = Math.max(1, numberOfPages - 9);
        }

        for (var i = startPage; i <= endPage; i++) {
            var pageSpan = document.createElement('span');
            pageSpan.textContent = i;
            if (i === currentPage) {
                pageSpan.style.fontWeight = 'bold';
            } else {
                pageSpan.style.cursor = 'pointer';
                pageSpan.onclick = function () {
                    currentPage = parseInt(this.textContent);
                    updatePage();
                };
            }
            pageNumberContainer.appendChild(pageSpan);
        }

        document.getElementById('previous-page').style.display = currentPage === 1 ? 'none' : 'inline';
        document.getElementById('next-page').style.display = currentPage === numberOfPages ? 'none' : 'inline';
    }

    function movePage(direction) {
        currentPage += direction;

        if (currentPage < 1) currentPage = 1;
        if (currentPage > numberOfPages) currentPage = numberOfPages;

        updatePage();
    }

    updatePage();

</script>

</body>
</html>