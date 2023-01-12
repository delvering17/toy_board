const signIn = {
    init: function () {
        const _this = this;
        $('#btn-signin').on('click', function () {
            _this.signin();
        });
    },
    signin: function () {
        const data =  {
            email: $('#email').val(),
            userName: $('#userName').val(),
            password: $('#password').val(),
            passwordConfirm: $('#passwordConfirm').val()
        };

        $.ajax({
            type: 'POST',
            url: "/api/member/save",
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data:JSON.stringify(data)
        }).done(function () {
            alert('회원가입 되었습니다.');
            window.location.href = '/';
        }).fail(function (response) {
            console.log(response);
            if (response.status === 400 && response.responseJSON.errorDetails != null) {
                $.each(response.responseJSON.errorDetails, function (index, errorDetails) {
                    let errorSpan =  document.querySelector(".error-" + errorDetails.field);
                    errorSpan.textContent = errorDetails.message;
                });
            } else {
                alert(response.responseJSON.message);
            }
        })
    }


}

signIn.init();