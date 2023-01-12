const login = {
    init: function () {
        const _this = this;
        $('#btn-login').on('click', function () {
            _this.login();
        });

    },
    login: function () {
        const data = {
            email: $('#email').val(),
            password: $('#password').val()
        };

        $.ajax({
            type: 'POST',
            url: "/api/login",
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('로그인 되었습니다.');
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

};

login.init();