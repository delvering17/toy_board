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
            if (response.status === 400) {
                $.each(response.responseJSON.errorDetails, function (index, errorDetails) {
                    $('input[id=' + errorDetails.field + ']').after('<span class="error">' + errorDetails.message + '</span>');
                });
            } else {
                console.log("여기 들어")
                alert(response.responseJSON.message);
            }
        })

    }

};

login.init();