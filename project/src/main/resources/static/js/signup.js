// 초기 상태 설정
let isIdChecked = false;
let isEmailVerified = false;
let isPasswordMatch = false;
let isPasswordValid = false;

// 아이디 중복확인
function checkDuplicate() {
    var memberId = document.getElementById("memberId").value.trim();
    var memberIdMessageElement = document.getElementById("memberIdMessage");

    if (memberId === '') {
        alert('아이디를 입력하세요.');
        return;
    }

    fetch('/member/id-check', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ memberId: memberId })
    })
    .then(response => response.json())
    .then(data => {
        switch (data.code) {
            case 'VF':
                alert('아이디를 입력하세요.');
                break;
            case 'DI':
                memberIdMessageElement.classList.add('text-danger');
                memberIdMessageElement.textContent = '이미 사용중인 아이디입니다.';
                document.getElementById("memberId").readOnly = false;
                isIdChecked = false;
                break;
            case 'DBE':
                alert('데이터베이스 오류입니다.');
                break;
            case 'SU':
                memberIdMessageElement.classList.remove('text-danger');
                memberIdMessageElement.classList.add('text-success');
                memberIdMessageElement.textContent = '사용가능한 아이디입니다.';
                document.getElementById("memberId").readOnly = true;
                isIdChecked = true;
                break;
            default:
                break;
        }
        updateSignupButtonState();
    })
    .catch(error => console.error('Error:', error));
}

// 이메일 인증번호 전송
function sendEmailVerification() {
    var memberId = document.getElementById('memberId').value; 
    var memberEmail = document.getElementById('memberEmail').value;

    if (!isIdChecked) {
        alert('먼저 아이디 중복확인을 하세요.');
        return;
    }

    if (memberEmail.trim() === '') {
        alert('이메일을 입력하세요.');
        return;
    }

    var data = {
        memberId: memberId,
        memberEmail: memberEmail
    };

    fetch('/member/email-certification', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    })
    .then(data => {
        if (data.code === 'SU') {
            alert('이메일 인증번호가 성공적으로 발송되었습니다.');
            document.getElementById('memberEmail').readOnly = true; // 이메일 입력창 읽기 전용으로 변경
            isEmailVerified = true;
        } else {
            alert('이메일 인증번호 발송에 실패했습니다.');
            isEmailVerified = false;
        }
        updateSignupButtonState();
    })
    .catch(error => {
        console.error('Error:', error);
        alert('서버와의 통신 중 오류가 발생했습니다.');
    });
}

// 이메일 인증 확인
function checkCertification() {
    var memberId = document.getElementById("memberId").value.trim();
    var memberEmail = document.getElementById("memberEmail").value.trim();
    var certificationNumber = document.getElementById("certificationNumber").value.trim();
    var certificationMessageElement = document.getElementById("certificationMessage");

    if (memberId === '' || memberEmail === '' || certificationNumber === '') {
        alert('인증번호를 입력하세요.');
        return;
    }

    fetch('/member/check-certification', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            memberId: memberId,
            memberEmail: memberEmail,
            certificationNumber: certificationNumber
        })
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('네트워크 응답이 올바르지 않음');
        }
        return response.json();
    })
    .then(data => {
        switch (data.code) {
            case 'SU':
                certificationMessageElement.classList.remove('text-danger');
                certificationMessageElement.classList.add('text-success');
                certificationMessageElement.textContent = '인증에 성공하였습니다.';
                isEmailVerified = true;
                break;
            case 'CF':
                certificationMessageElement.classList.add('text-danger');
                certificationMessageElement.textContent = '인증에 실패하였습니다.';
                isEmailVerified = false;
                break;
            case 'DBE':
                alert('데이터베이스 오류입니다.');
                isEmailVerified = false;
                break;
            default:
                break;
        }
        updateSignupButtonState();
    })
    .catch(error => {
        console.error('Error:', error);
        alert('인증에 실패하였습니다.');
        isEmailVerified = false;
    });
}

// 비밀번호 유효성 검사
function validatePassword() {
    var password = document.getElementById("memberPw").value;
    var pwMessageElement = document.getElementById("pwMessage");

    var pattern = /^(?=.*[a-zA-Z])((?=.*\d)|(?=.*\W)).{8,20}$/;
    if (!pattern.test(password)) {
        pwMessageElement.classList.add('text-danger');
        pwMessageElement.textContent = '비밀번호는 대소문자 + 숫자 + 특수문자 조합으로 8 ~ 20자리이어야 합니다.';
        isPasswordValid = false;
    } else {
        pwMessageElement.textContent = '';
        isPasswordValid = true;
    }
    updateSignupButtonState();
}

// 비밀번호 확인
function checkPasswordMatch() {
    var password = document.getElementById("memberPw").value;
    var confirmPassword = document.getElementById("confirmMemberPw").value;
    var confirmPwMessageElement = document.getElementById("confirmPwMessage");

    if (password !== confirmPassword) {
        confirmPwMessageElement.classList.add('text-danger');
        confirmPwMessageElement.textContent = '비밀번호가 일치하지 않습니다.';
        isPasswordMatch = false;
    } else {
        confirmPwMessageElement.classList.remove('text-danger');
        confirmPwMessageElement.classList.add('text-success');
        confirmPwMessageElement.textContent = '비밀번호가 일치합니다.';
        isPasswordMatch = true;
    }
    updateSignupButtonState();
}

// 회원가입 버튼 활성화 조건 확인
function updateSignupButtonState() {
    const memberId = document.getElementById('memberId').value.trim();
    const memberEmail = document.getElementById('memberEmail').value.trim();
    const certificationNumber = document.getElementById('certificationNumber').value.trim();
    const memberPw = document.getElementById('memberPw').value.trim();
    const confirmMemberPw = document.getElementById('confirmMemberPw').value.trim();
    const memberName = document.getElementById('memberName').value.trim();
    const memberAddress = document.getElementById('memberAddress').value.trim();
    const memberPhone = document.getElementById('memberPhone').value.trim();

    if (isIdChecked && isEmailVerified && isPasswordMatch && isPasswordValid &&
        memberId && memberEmail && certificationNumber && memberPw && confirmMemberPw &&
        memberName && memberAddress && memberPhone) {
        document.getElementById('signupButton').disabled = false;
    } else {
        document.getElementById('signupButton').disabled = true;
    }
}

// 폼 필드 변경 시 회원가입 버튼 상태 업데이트
document.getElementById('memberPw').addEventListener('input', validatePassword);
document.getElementById('memberPw').addEventListener('input', checkPasswordMatch);
document.getElementById('confirmMemberPw').addEventListener('input', checkPasswordMatch);

// 폼 전송 시 JSON 형식으로 데이터를 생성하여 서버로 전송
document.getElementById('signupForm').addEventListener('submit', function (event) {
    event.preventDefault();

    var formData = {
        memberId: document.getElementById('memberId').value,
        memberName: document.getElementById('memberName').value,
        memberPw: document.getElementById('memberPw').value,
        memberAddress: document.getElementById('memberAddress').value,
        memberPhone: document.getElementById('memberPhone').value,
        memberEmail: document.getElementById('memberEmail').value,
        certificationNumber: document.getElementById('certificationNumber').value
    };

    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/member/signup', true);
    xhr.setRequestHeader('Content-Type', 'application/json;charset=UTF-8');
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                console.log('Signup successful');
                window.location.href = '/log-in';
            } else {
                console.error('Error signing up:', xhr.status);
                // 오류 발생 시 처리를 추가할 수 있습니다.
            }
        }
    };
    xhr.send(JSON.stringify(formData));
});
