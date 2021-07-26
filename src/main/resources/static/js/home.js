function follow(userId, obj) {
    if ($(obj).text() === "팔로우") {
        $.ajax({
            type: "post",
            url: '/api/follows/' + userId,
            success: function () {
                $(obj).removeClass("recommend-follow");
                $(obj).addClass("recommend-unfollow");
                $(obj).text("언팔로우");
            },
            error: function (data) {
                if (data.status === 403)
                    location.replace("/login");
                if (data != undefined && data.responseJSON != undefined && data.responseJSON.message != undefined)
                    alert(data.responseJSON.message);
                else
                    alert("error가 발생했습니다.");
            }
        });
    } else {
        $.ajax({
            type: "delete",
            url: '/api/follows/' + userId,
            success: function () {
                $(obj).addClass("recommend-follow");
                $(obj).removeClass("recommend-unfollow");
                $(obj).text("팔로우");
            },
            error: function (data) {
                if (data.status === 403)
                    location.replace("/login");
                if (data != undefined && data.responseJSON != undefined && data.responseJSON.message != undefined)
                    alert(data.responseJSON.message);
                else
                    alert("error가 발생했습니다.");
            }
        });
    }
}