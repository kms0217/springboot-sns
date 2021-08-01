let created_at;
let next_page_num = 0;
let total_page_num = 0;

window.onload = getProfileStory();

function getProfileStory() {
    let user_id = document.querySelector('main').dataset.user_id;
    if (user_id == undefined)
        return;
    $.ajax({
        type: "get",
        url: "/api/users/" + user_id + "/stories?page=" + next_page_num,
        dataType: "Json",
        success: function (data) {
            if (data.content)
                (data.content).forEach(content => {
                    $("#story-list").append(profileView(content));
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
        getProfileStory();
}

function profileView(content) {
    let view = `<div>
    <div class="gallery-item" tabindex="0" data-story-id="${content.storyId}" onclick="showStoryModal(${content.storyId})">
        <img src="/file/post/${content.imageUrl}" class="gallery-image" style="display: block;">
        <div class="gallery-item-info">
            <ul>
                <li class="gallery-like"><span class="visually-hidden">Likes:</span>
                    <i class="fas fa-heart" aria-hidden="true"></i> ${content.likeNum}
                </li>
                <li class="gallery-comments"><span class="visually-hidden">Comments:</span>
                    <i class="fas fa-comment" aria-hidden="true"></i> ${content.commentNum}
                </li>
            </ul>
        </div>
    </div></div>`;
    return view;
}

function follow(userId, obj) {
    if ($(obj).text() === "언팔로우") {
        $.ajax({
            type: "delete",
            url: "/api/follows/" + userId,
            success: function () {
                $(obj).text("팔로우");
                $(obj).removeClass("unfollow");
                $(obj).addClass("follow")
            },
            error: function (data) {
                errorHandle(data);
            }
        })
    } else {
        $.ajax({
            type: "post",
            url: "/api/follows/" + userId,
            success: function () {
                $(obj).text("언팔로우");
                $(obj).removeClass("follow");
                $(obj).addClass("unfollow")
            },
            error: function (data) {
                errorHandle(data);
            }
        })
    }
}

function followerModalShow(userId) {
    $('.f-item').remove();
    $("#profile-follow-modal").modal('show');
    $.ajax({
        type: "get",
        url: '/api/follows/' + userId + "/followee",
        dataType: "JSON",
        success: function (data) {
            if (data)
                data.forEach(profileModalDto => {
                    $("#profile-follow-modal-item").append(createView(profileModalDto));
                })
        },
        error: function (data) {
            errorHandle(data);
        }
    });
}

function followeeModalShow(userId) {
    $('.f-item').remove();
    $("#profile-follower-modal").modal('show');
    $.ajax({
        type: "get",
        url: '/api/follows/' + userId + "/follower",
        dataType: "JSON",
        success: function (data) {
            if (data)
                data.forEach(profileModalDto => {
                    $("#profile-follower-modal-item").append(createView(profileModalDto));
                })
        },
        error: function(data) {
            errorHandle(data);
        }
    });
}

function profileFollowerModalClose() {
    $("#profile-follower-modal").modal("hide");
}

function profileFollowModalClose() {
    $("#profile-follow-modal").modal("hide");
}

function createView(profileModalDto) {
    let item = `<div class="row f-item" style="width: inherit">
                <div class="col-2">
                <a href="/profile/${profileModalDto.username}">
                    <img src="/file/profile/${profileModalDto.profileImageUrl}" style="border-radius: 50%;width: 32px;">
                </a>
                </div>
                <div class="col-6" style="font-size: 15px">
                    <p class="f-modal-username" >${profileModalDto.username}</p>
                </div>
                <div class="col-4">`;
    if (profileModalDto.me)
        item += `</div></div>`;
    else if (profileModalDto.following)
        item += `<button class="unfollow" style="font-size: 11px; margin-top: 5px" onclick="follow(${profileModalDto.userId}, this)">언팔로우</button></div></div>`;
    else
        item += `<button class="follow"  style="font-size: 11px; margin-top: 5px" onclick="follow(${profileModalDto.userId}, this)">팔로우</button></div></div>`;
    return item;
}