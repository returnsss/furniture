function checkForm(action) {

    const frmMemberInsert = document.frmMemberInsert; // 폼을 들고옴.

    if (!frmMemberInsert.userId.value) {
        alert("아이디를 입력하세요.");
        frmMemberInsert.userId.focus();
        return false;
    }

    if (frmMemberInsert.userId.value.length < 5 || frmMemberInsert.userId.value.length > 12) {
        alert("아이디는 5자 이상 12자 이하로 작성해주세요.");
        frmMemberInsert.userId.focus();
        return false;
    }


    if (!frmMemberInsert.password.value) {
        alert("비밀번호를 입력하세요.");
        frmMemberInsert.password.focus();
        return false;
    }

    //비밀번호 영문자+숫자+특수조합(8~25자리 입력) 정규식
    const pwdCheck = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,25}$/;

    if (!pwdCheck.test(frmMemberInsert.password.value)) {
        alert("비밀번호는 영문자+숫자+특수문자 조합으로 8~25자리 사용해야 합니다.");
        password.focus();
        return false;
    }
    ;

    // 같은 문자 연속 4번 있는지
    const pwdCheck2 = /(\w)\1\1\1/;

    if (pwdCheck2.test(frmMemberInsert.password.value)) {
        alert("같은 문자를 4번 이상 연속해서 사용하실 수 없습니다.");
        password.focus();
        return false;
    }
    ;

    // \s : 띄어쓰기
    // password.value.search(n) : n의 인덱스 반환, -1이면 없다는 뜻
    if (frmMemberInsert.password.value.search(" ") != -1) {
        alert("비밀번호는 공백을 포함할 수 없습니다");
        password.focus();
        return false;
    }

    if (frmMemberInsert.password.value != frmMemberInsert.password_confirm.value) {
        alert("비밀번호를 동일하게 입력하세요.");
        frmMemberInsert.password_confirm.focus();
        return false;
    }


    if (!frmMemberInsert.name.value) {
        alert("이름을 입력하세요.");
        frmMemberInsert.name.focus();
        return false;
    }

    //이름 : 한글 검사
    const regExpName = /^[가-힣]*$/;

    if (!regExpName.test(frmMemberInsert.name.value)) {
        alert("이름은 한글만 입력해 주세요.");
        frmMemberInsert.name.focus();
        return false;
    }


    const num_check = /[0-9]/;	// 숫자


    if (!frmMemberInsert.birthyy.value) {
        alert("태어난 해를 입력하세요.");
        frmMemberInsert.birthyy.focus();
        return false;
    }

    if (!frmMemberInsert.birthmm.value) {
        alert("태어난 월를 입력하세요.");
        frmMemberInsert.birthmm.focus();
        return false;
    }

    if (!frmMemberInsert.birthdd.value) {
        alert("태어난 일을 입력하세요.");
        frmMemberInsert.birthdd.focus();
        return false;
    }

    if (num_check.test(frmMemberInsert.birthyy.value) == false) {
        alert("숫자만 입력할 수 있습니다.");
        frmMemberInsert.birthyy.focus();
        return false;
    }

    if (num_check.test(frmMemberInsert.birthdd.value) == false) {
        alert("숫자만 입력할 수 있습니다.");
        frmMemberInsert.birthdd.focus();
        return false;
    }


    if (!frmMemberInsert.mail1.value) {
        alert("이메일을 입력하세요.");
        frmMemberInsert.mail1.focus();
        return false;
    }

    const exptext = /^[A-Za-z0-9_\.\-]+/;

    if (exptext.test(frmMemberInsert.mail1.value) == false) {

        //이메일 형식이 알파벳+숫자@알파벳+숫자.알파벳+숫자 형식이 아닐경우

        alert("이메일은 영어와 숫자만 조합할 수 있습니다.");

        frmMemberInsert.mail1.focus();

        return false;
    }

    if (!frmMemberInsert.address1.value) {
        alert("주소를 입력하세요.");
        frmMemberInsert.address1.focus();
        return false;
    }

    if (!frmMemberInsert.address2.value) {
        alert("상세 주소를 입력하세요.");
        frmMemberInsert.address2.focus();
        return false;
    }


    if (frmMemberInsert.phone2.value.length < 4) {
        alert("휴대폰 번호 4자리를 입력하세요.");
        frmMemberInsert.phone2.focus();
        return false;
    }

    if (frmMemberInsert.phone3.value.length < 4) {
        alert("휴대폰 번호 4자리를 입력하세요.");
        frmMemberInsert.phone3.focus();
        return false;
    }


    if (num_check.test(frmMemberInsert.phone2.value) === false) {
        alert("숫자만 입력할 수 있습니다.");
        frmMemberInsert.phone2.focus();
        return false;
    }

    if (num_check.test(frmMemberInsert.phone3.value) === false) {
        alert("숫자만 입력할 수 있습니다.");
        frmMemberInsert.phone3.focus();
        return false;
    }

    if (frmMemberInsert.agreement.value === "no") {
        alert("약관에 동의해 주세요.");
        return false;
    }

    upsertMember(action,frmMemberInsert);


}

function upsertMember(action,frmMemberInsert){
    let confirmMsg = "";
    let method = "";
    let returnMsgType = "";


    if (action === 'join') {

        confirmMsg = "가입하시겠습니까?";
        method = 'POST';
        returnMsgType = '1';

    } else if (action === 'update') {

        confirmMsg = "수정하시겠습니까?";
        method = 'PATCH';
        returnMsgType = '0';

    }else {
        return ;
    }



    const payload = new FormData(frmMemberInsert);
    const jsonData = {};

    // FormData 객체의 값을 JSON 객체로 변환
    for (const [key, value] of payload.entries()) {
        jsonData[key] = value;
    }

    if (confirm(confirmMsg)) {


        fetch('/api/member', {
            method: method,
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(jsonData)
        })
            .then(data => {
                console.log(data); // 서버에서 받은 응답 데이터 처리
                window.location.href = `/member/resultMember?msg=${returnMsgType}`;

            })
            .catch(error => {
                console.error('Error:', error);
            });

    }
}