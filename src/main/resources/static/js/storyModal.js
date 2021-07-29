function createCommentView(comment) {
    return `<a href="/profile/${comment.user.username}" class="comment-username">${comment.user.username}</a>
            <p class="comment-text"> ${comment.commentMsg}</p><br>`;
}

function createStoryModalItem(story) {
    $("#story-modal-img").append(`<img class="story-modal-img" src="/file/post/${story.imageUrl}" width="100%" height="100%">`);
    $("#story-modal-comment-owner").append(createModalOwner(story.user));
    (story.comments).forEach(comment => {
        $("#story-modal-comment-list").append(createModalCommentView(comment));
    })
    $("#story-modal-input").append(createModalInputView(story.storyId));
}

function createModalOwner(user) {
    return `<div class="story-modal-owner-box row" style="width: 100%; padding-top: 10px">
                <div class="col-3" style="margin-left: 10px; margin-top: 10px;">
                <a href="/profile/${user.username}">
                    <img class="story-modal-owner-img" src="/file/profile/${user.profileImageUrl}">
                </a>
                </div>
                <div class="col-4" style="margin-left: 0px; margin-top: 20px; padding-left: 0;">
                    <p style="font-size: 12px; margin-left: 0;">${user.username}</p>
                </div>
            </div>`;
}

function createModalCommentView(comment) {
    return `<div class="story-modal-comment-box" style="width: 100%">
                <a href="/profile/${comment.user.username}" style="text-decoration: none;">
                    <img class="story-modal-comment-img" src="/file/profile/${comment.user.profileImageUrl}" href="/profile/comment.user.username">
                </a>
                <p style="display: inline-block; font-size: 13px;">${comment.user.username}</p>
                <span class="story-modal-comment" >${comment.commentMsg}</span>
            </div>`;
}

function createModalInputView(storyId) {
    return `<div class="col-10 story-modal-input-box">
                <input placeholder="댓글 달기..." id="story-modal-input-${storyId}" style="width: 100%; height: 100%; border: 0;"
                       onKeyUp="if(window.event.keyCode==13){ModalCommentAdd(${storyId})}">
            </div>
            <div class="col-2 story-modal-input-box">
                <button class="comment-button"  onClick="ModalCommentAdd(${storyId})">
                    <p style="font-size: 13px!important;">게시</p></button>
            </div>`;
}

function ModalCommentAdd(storyId) {
    let inputVal = $("#story-modal-input-" + storyId).val();
    $("#story-modal-input-" + storyId).val("");
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
        success: function (data) {
            $("#story-comment-list-" + storyId).append(createCommentView(data));
            $("#story-modal-comment-list").append(createModalCommentView(data));
        },
        error: function (data) {
            errorHandle(data);
        }
    });
}

function showStoryModal(storyId) {
    $('.story-modal-img').remove();
    $('.story-modal-input-box').remove();
    $('.story-modal-comment-box').remove();
    $('.story-modal-owner-box').remove();
    $("#story-modal").modal("show");
    $.ajax({
        type: "get",
        url: "/api/stories/" + storyId,
        dataType: "Json",
        success: function (data) {
            createStoryModalItem(data);
        },
        error: function (data) {
            errorHandle(data);
        }
    });
}