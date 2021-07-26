let created_at;
let page_num = 0;
let height = document.body.scrollHeight;

window.onload = getStory();

function getStory() {
    $.ajax({
        type: "get",
        url: "/api/stories/?page=" + page_num,
        dataType: "Json",
        success: function (data) {
            (data.content).forEach(content => {
                $("#home-content").append(homeView(content));
            });
            page_num = page_num + 1;
        },
        error: function (data) {
            if (data.status === 403)
                location.replace("/login");
        }
    });
}

$(window).scroll(function() {
    if (Math.floor(($(window).scrollTop() / ($(document).height() - $(window).height())) * 100) > 50)
        getStory();
})

function homeView(content) {
    let view = `<div>
        <div class="post-item" user_id="${content.user.userId} story_id="${content.storyId}">
            <div class="post-item-header" style="background-color: white; height: 60px;">
                <div class="container row" style="padding-top: 13px;">
                    <disv class="post-profile-img col-1" style="width: 40px; float: left;">
                        <img src="/file/profile/${content.user.profileImageUrl}"
                             style="border-radius: 50%; width: 32px; height: 32px;">
                    </disv>
                    <div class="col-10" style="padding-top: 5px;padding-left: 20px;">
                        <p>${content.user.username}</p>
                    </div>
                    <div class="col-1" style="padding-top: 5px;">
                        <button
                            style="width: 24px; height: 24px; border: 0; margin-left: 30px; background-color: white"><i
                            class="fa fa-ellipsis-h"></i></button>
                    </div>
                </div>
            </div>
            <div class="post-item-image" style="height: 614px;">
                <img class="post-image" src="/file/post/${content.imageUrl}"
                     style="width: 100%; height: 100%;">
            </div>
            <div class="post-nav row" style="height:40px;">
                <div class="like-button-wrapper col-1">
                    <button class="post-btn">
                        <i class="far fa-heart fa-lg"></i>
                    </button>
                </div>
                <div class="like-button-wrapper col-1">
                    <button class="post-btn">
                        <i class="far fa-comments fa-lg"></i>
                    </button>
                </div>
                <div class="like-button-wrapper col-1">
                    <button class="post-btn">
                        <i class="far fa-paper-plane fa-lg"></i>
                    </button>
                </div>
            </div>
            <div class="post-like" style="height: 24px;">
                <p class="post-like-count">n명이 좋아합니다.</p>
            </div>
            <div class="post-comment">
                <p class="comment-text">username 어쩌구저쩌구</p>
            </div>
            <div class="post-date" style="height:19px;">
                <p class="comment-date">7월 3일</p>
            </div>
            <div class="post-comment-input-wrapper row" style="height: 56px;">
                <div class="col-1 imoz-wrapper">
                    <button class="imoz">
                        <i class="far fa-smile fa-2x"></i>
                    </button>
                </div>
                <div class="col-9">
                    <input class="post-comment-input" placeholder="댓글 달기..." style="width: 100%; height: 100%;">
                </div>
                <div class="col-2 comment-button-wrapper">
                    <button class="comment-button"><p>게시</p></button>
                </div>
            </div>
        </div>
    </div>`

    return view;
}

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