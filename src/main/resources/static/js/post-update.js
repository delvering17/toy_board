const postUpdate = {
    init: function () {
        const _this = this;
        $('#btn-post-update').on('click', function () {
            _this.postUpdate();
        });
    },
    postUpdate: function () {
        let postId = $('#postId').val();
        const data = {
            title: $('#title').val(),
            category: $('#category').val(),
            content: $('#content').val()
        };

        $.ajax({
            type: 'POST',
            url: "/api/post/" + postId + "/update",
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function (response) {
            console.log(response.content);
            alert('글이 수정되었습니다.');
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

postUpdate.init();