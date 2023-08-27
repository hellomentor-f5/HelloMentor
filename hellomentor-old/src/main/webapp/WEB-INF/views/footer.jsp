<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Footer</title>
    <!-- header 스타일 -->
    <link href="${pageContext.request.contextPath}/resources/css/footer-style.css" rel="stylesheet" />
    <!-- Pretendard 폰트 cdn -->
    <link
            rel="stylesheet"
            as="style"
            crossorigin
            href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.8/dist/web/variable/pretendardvariable.css"
    />
    <!-- jquery cdn -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
</head>
<body>
<!-- footer -->
<!-- 로그인 전 후 모두 같으나 로그인 전에 community, helper의 링크들 클릭시 로그인이 필요하다는 alert 메세지 띄우기-->
<footer>
    <div class="footer-wrap">
        <div class="footer-navi">
            <div class="footer-content1">
                <div class="footer-logo-container">
                    <img
                            class="footer-logo-img"
                            src="../resources/img/HELLO_MENTO_LOGO.png"
                            onclick="location.href=''"
                    />
                </div>
                <div class="social-bar">
                    <img
                            id="insta-logo"
                            src="../resources/img/Instagram.png"
                            onclick="location.href=''"
                    />
                    <img
                            id="twitter-logo"
                            src="../resources/img/Twitter.png"
                            onclick="location.href=''"
                    />
                    <img
                            id="facebook-logo"
                            src="../resources/img/Facebook.png"
                            onclick="location.href=''"
                    />
                </div>
            </div>
            <div class="footer-content2">
                <div class="footer-menu1 footer-menu">
                    <ul>
                        Community
                        <li>
                            <a href="">자유게시판</a>
                        </li>
                        <li>
                            <a href="">지식인</a>
                        </li>
                        <li>
                            <a href="">스터디모집</a>
                        </li>
                        <li>
                            <a href="">멘토링</a>
                        </li>
                    </ul>
                </div>
                <div class="footer-menu2 footer-menu">
                    <ul>
                        Helper
                        <li>
                            <a href="">공지사항</a>
                        </li>
                        <li>
                            <a href="">문의하기</a>
                        </li>
                        <li>
                            <a href="">FAQ</a>
                        </li>
                    </ul>
                </div>
                <div class="footer-menu3 footer-menu">
                    <ul>
                        About Us
                        <li>
                            <a href="">상세정보</a>
                        </li>
                        <li>
                            <a href="">채용정보</a>
                        </li>
                        <li>
                            <a href="">광고문의</a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="copyright">
            <div id="copy-content">
                <p>
                    상호명: (주)헬로우멘토 | 대표명: 신용수, 정승훈, 김정찬, 임재린,
                    이찬우<br />
                    사업자등록번호: 111-22-3333 | 통신판매업신고번호: 제
                    2023-서울강남-00001호 | 직업정보 제공사업 신고번호:
                    A00001000001<br />
                    주소: 서울 강남구 테헤란로14길 6 남도빌딩 2층, 3층, 4층(06234) |
                    고객센터 : info@hellomento.kr (영업시간 평일 10:00~17:00) 주말 ·
                    공휴일 휴무<br />
                    © 2023 (주)헬로우멘토, Inc. All rights reserved.
                </p>
            </div>
        </div>
    </div>
</footer>
</body>
</html>
