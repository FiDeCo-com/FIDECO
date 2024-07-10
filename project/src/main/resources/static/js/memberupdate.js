document.addEventListener('DOMContentLoaded', function() {
    // 토큰 확인 및 권한 필드 표시 여부 결정
    const token = getCookie('jwtToken');
    if (token) {
        const decodedToken = parseJwt(token);
        const userRole = decodedToken.auth;
        if (userRole === 'ROLE_ADMIN') {
            document.getElementById('authField').style.display = 'block';
        }
    }

    // 비밀번호 확인 이벤트 추가
    document.getElementById('memberPw').addEventListener('input', validatePassword);
    document.getElementById('memberPw').addEventListener('input', checkPasswordMatch);
    document.getElementById('confirmMemberPw').addEventListener('input', checkPasswordMatch);

    // 비밀번호 확인 함수
    function checkPasswordMatch() {
        const password = document.getElementById('memberPw').value;
        const confirmPassword = document.getElementById('confirmMemberPw').value;
        const confirmPwMessageElement = document.getElementById('confirmPwMessage');
        const updateButton = document.getElementById('updateButton');

        if (password === '' || confirmPassword === '') {
            confirmPwMessageElement.textContent = '';
            updateButton.disabled = true;
            return;
        }

        if (password !== confirmPassword) {
            confirmPwMessageElement.classList.add('text-danger');
            confirmPwMessageElement.textContent = '비밀번호가 일치하지 않습니다.';
            updateButton.disabled = true;
        } else {
            confirmPwMessageElement.classList.remove('text-danger');
            confirmPwMessageElement.classList.add('text-success');
            confirmPwMessageElement.textContent = '비밀번호가 일치합니다.';
            validatePassword();
        }
    }

    // 비밀번호 유효성 검사 함수
    function validatePassword() {
        const password = document.getElementById('memberPw').value;
        const pwMessageElement = document.getElementById('pwMessage');
        const updateButton = document.getElementById('updateButton');

        const pattern = /^(?=.*[a-zA-Z])((?=.*\d)|(?=.*\W)).{8,20}$/;
        if (password === '') {
            pwMessageElement.textContent = '';
            return;
        }

        if (!pattern.test(password)) {
            pwMessageElement.classList.add('text-danger');
            pwMessageElement.textContent = '비밀번호는 대소문자 + 숫자 + 특수문자 조합으로 8 ~ 20자리이어야 합니다.';
            updateButton.disabled = true;
        } else {
            pwMessageElement.textContent = '';
            validateForm();
        }
    }

    // 폼 전체 유효성 검사 함수
    function validateForm() {
        const memberName = document.getElementById('memberName').value.trim();
        const memberPw = document.getElementById('memberPw').value.trim();
        const confirmMemberPw = document.getElementById('confirmMemberPw').value.trim();
        const memberAddress = document.getElementById('memberAddress').value.trim();
        const memberPhone = document.getElementById('memberPhone').value.trim();
        const memberEmail = document.getElementById('memberEmail').value.trim();
        const memberAuth = document.getElementById('memberAuth').value.trim();
        const updateButton = document.getElementById('updateButton');

        if (memberName && memberPw && confirmMemberPw && memberAddress && memberPhone && memberEmail && memberAuth && memberPw === confirmMemberPw) {
            updateButton.disabled = false;
        } else {
            updateButton.disabled = true;
        }
    }

    // JWT 토큰을 파싱하여 JSON 객체로 변환하는 함수
    function parseJwt(token) {
        try {
            const base64Url = token.split('.')[1];
            const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
            const jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
                return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
            }).join(''));

            return JSON.parse(jsonPayload);
        } catch (e) {
            console.error('토큰 파싱 중 오류 발생:', e);
            return null;
        }
    }

    // 쿠키에서 특정 이름의 값을 가져오는 함수
    function getCookie(name) {
        const value = `; ${document.cookie}`;
        const parts = value.split(`; ${name}=`);
        if (parts.length === 2) return parts.pop().split(';').shift();
        return null;
    }

    // 다른 필드 변경 시 폼 유효성 검사 호출
    document.getElementById('memberName').addEventListener('input', validateForm);
    document.getElementById('memberAddress').addEventListener('input', validateForm);
    document.getElementById('memberPhone').addEventListener('input', validateForm);
    document.getElementById('memberEmail').addEventListener('input', validateForm);
    document.getElementById('memberAuth').addEventListener('input', validateForm);
});
