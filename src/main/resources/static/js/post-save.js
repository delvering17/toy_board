const postSave = {
    init: function () {
        const _this = this;
        $('#btn-post-save').on('click', function () {
            _this.postSave();
        });
    },
    postSave: function () {
        const data = {
            title: $('#title').val(),
            category: $('#category').val(),
            content: $('#content').val()
        };

        $.ajax({
            type: 'POST',
            url: "/api/post/save",
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function (response) {
            console.log(response.content);
            alert('글이 등록되었습니다.');
            window.location.href = '/post/' + response.content;
        }).fail(function (response) {
            console.log(response);
            if (response.status === 400 && response.responseJSON.errorDetails != null) {
                $.each(response.responseJSON.errorDetails, function (index, errorDetails) {
                    let errorSpan =  document.querySelector(".error-" + errorDetails.field);
                    errorSpan.textContent = errorDetails.message;
                });

            }
        })

    }

}

postSave.init();