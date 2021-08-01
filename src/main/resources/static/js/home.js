let created_at;
let next_page_num = 0;
let total_page_num = 0;

window.onload = getStory;

function getStory() {
    $.ajax({
        type: "get",
        url: "/api/stories/home/?page=" + next_page_num,
        dataType: "Json",
        success: function (data) {
            if (data.content)
                (data.content).forEach(content => {
                    $("#home-content").append(homeView(content));
                });
            total_page_num = data.totalPages;
            next_page_num = data.pageable.pageNumber + 1;
            if (next_page_num > total_page_num - 1)
                window.removeEventListener("scroll", checkScroll);
        },
        error: function (data) {
            errorHandle(data);
        }
    });
}

window.addEventListener('scroll', checkScroll);

function checkScroll() {
    if (Math.floor(($(window).scrollTop() / ($(document).height() - $(window).height())) * 100) > 50)
        getStory();
}

function homeView(content) {
    let view = `<div>
        <div class="story-item" user_id="${content.user.userId} data-story-id="${content.storyId}">
            <div class="story-item-header" style="background-color: white; height: 60px;">
                <div class="container row" style="padding-top: 13px;">
                    <disv class="story-profile-img col-1" style="width: 40px; float: left;">
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
            <div class="story-item-image" style="height: 614px;">
                <img class="story-image" src="/file/post/${content.imageUrl}"
                     style="width: 100%; height: 100%;">
            </div>
           
            <div class="story-nav row" style="height:40px;">
                <div class="like-button-wrapper col-1">
                    <button class="story-btn" onclick="like(${content.storyId})" >`;
    if (content.likeStatus)
        view += `<i class="fas fa-heart fa-lg" id="like-btn-${content.storyId}"></i>`;
    else
        view += `<i class="far fa-heart fa-lg" id="like-btn-${content.storyId}"></i>`;

    view += `</button>
                </div>
                <div class="like-button-wrapper col-1">
                    <button class="story-btn">
                        <i class="far fa-comments fa-lg"></i>
                    </button>
                </div>
                <div class="like-button-wrapper col-1">
                    <button class="story-btn">
                        <i class="far fa-paper-plane fa-lg"></i>
                    </button>
                </div>
            </div>
            <div class="story-like" style="height: 24px;">
                <p class="story-like-count" id="like-count-${content.storyId}">${content.likeNum}</p>
                <p class="story-like-count-text"> 명이 좋아합니다.</p><br>
            </div>
            <a href="/profile/${content.user.username}" class="comment-username">${content.user.username}</a>
            <p class="comment-text"> ${content.caption}</p><br>`;
    if (content.comments.length > 3) {
        view += `<p class="show-all" onclick="showStoryModal(${content.storyId})">댓글 모두보기</p>`;
        view += `<div class="story-comment" id="story-comment-list-${content.storyId}">`;
        (content.comments).slice(-2).forEach(comment => {
            view += createCommentView(comment);
        });
    } else {
        view += `<div class="story-comment" id="story-comment-list-${content.storyId}">`;
        if (content.comments)
            (content.comments).forEach(comment => {
                view += createCommentView(comment);
            });
    }
    view += `</div><div class="story-date" style="height:19px;">
                <p class="comment-date">${content.createdAt}</p>
            </div>
            <div class="story-comment-input-wrapper row" style="height: 56px;">
                <div class="col-1 imoz-wrapper">
                    <button class="imoz">
                        <i class="far fa-smile fa-2x"></i>
                    </button>
                </div>
                <div class="col-9">
                    <input class="story-comment-input" placeholder="댓글 달기..." id="story-input-${content.storyId}" 
                    style="width: 100%; height: 100%;" onkeyup="if(window.event.keyCode==13){postComment(${content.storyId})}">
                </div>
                <div class="col-2 comment-button-wrapper">
                    <button class="comment-button" onclick="postComment(${content.storyId})"><p>게시</p></button>
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
                errorHandle(data);
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
                errorHandle(data);
            }
        });
    }
}

function postComment(storyId) {
    let inputVal = $("#story-input-" + storyId).val();
    $("#story-input-" + storyId).val("");
    if (inputVal === "") {
        alert("댓글을 입력해 주세요.");
        return;
    }
    let data = {commentMsg: inputVal, storyId: storyId};
    $.ajax({
        type: "post",
        url: "/api/comments",
        data: JSON.stringify(data),
        contentType: "application/json",
        success: function (data, status, xhr) {
            addComment(xhr.getResponseHeader("Location"), storyId);
        },
        error: function (data) {
            errorHandle(data);
        }
    });
}

function addComment(url, storyId) {
    $.ajax({
        type: "get",
        url: url,
        dataType: "Json",
        success: function (data) {
            $("#story-comment-list-" + storyId).append(createCommentView(data));
        },
        error: function (data) {
            errorHandle(data);
        }
    })
}

function like(storyId) {
    let current_status = $("#like-btn-" + storyId).attr("class");
    if (current_status.includes("far")) {
        $.ajax({
            type: "post",
            url: "/api/likes/" + storyId,
            dataType: "Json",
            success: function () {
                $("#like-btn-" + storyId).removeClass("far");
                $("#like-btn-" + storyId).addClass("fas");
                $("#like-count-" + storyId).text(parseInt($("#like-count-" + storyId).text()) + 1);
            },
            error: function (data) {
                errorHandle(data);
            }
        })
    } else {
        $.ajax({
            type: "delete",
            url: "/api/likes/" + storyId,
            dataType: "Json",
            success: function () {
                $("#like-btn-" + storyId).removeClass("fas");
                $("#like-btn-" + storyId).addClass("far");
                $("#like-count-" + storyId).text(parseInt($("#like-count-" + storyId).text()) - 1);
            },
            error: function (data) {
                errorHandle(data);
            }
        })
    }
}
