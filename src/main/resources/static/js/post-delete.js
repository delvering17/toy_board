const postDelete = {
    init: function () {
        const _this = this;
        $('#btn-post-delete').on('click', function () {
            _this.postDelete();
        });
    },
    postDelete: function () {
        let postId = $('#id').val();
        const data = {
            postId: postId
        };

        $.ajax({
            type: 'POST',
            url: "/api/post/" + postId + "/delete",
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function (response) {
            console.log(response.content);
            alert('글이 삭제되었습니다.');
            window.location.href = response.content;
        }).fail(function (response) {
            console.log(response);
            alert('오류!');
        })

    }

}

postDelete.init();