let sock;
let stomp;
window.onload = getChatRoom;

function getChatRoom() {
    $.ajax({
        type: "get",
        url: "/api/chatrooms",
        dataType: "Json",
        success: function (data) {
            if (data)
                data.forEach(chatRoomDto => {
                    $("#chat-room-box").append(createChatRoomView(chatRoomDto));
                })
        },
        error: function (data) {
            errorHandle(data);
        }
    });
}

function showSearchModal() {
    $(".search-result-box").remove();
    $("#search-modal").modal("show");
}

function createChatView(messages, chatRoomId, otherUserId) {
    let view = `<div class="h-100 chatroom">
                    <div class="chatroom-header"></div>
                 <div id="chat-list" style="height: calc(100% - 120px); overflow-y: scroll;">`;
    if (messages){
        messages.forEach(message => {
            if (message.user.userId === otherUserId)
                view += createRecvChatView(message);
            else
                view += createSendChatView(message);
        });
    }
    view += `</div>
                <div class="dm-input-wrapper">
                    <input type="text" id="input-chat" placeholder="메시지입력..."
                    onKeyUp="if(window.event.keyCode==13){sendMessage(${chatRoomId})}"
                    class="form-control rounded-0 border-0 py-4 bg-light dm-input-text"/>
                </div>
            </div>`;
    return view;
}

function sendMessage(chatRoomId) {
    let inputVal = $("#input-chat").val();
    $("#input-chat").val("");
    if (inputVal === "")
        return;
    stomp.send("/pub/chatroom", {},
        JSON.stringify({
            chatRoomId: chatRoomId,
            content: inputVal
        }));
}

function createRecvChatView(message) {
    return `<div class="chat">
                <div style="width: 70%; margin-bottom: 3px; padding-left: 10px;">
                    <div>
                        <img src="/file/profile/${message.user.profileImageUrl}" style="width: 15px; height: 15px; border-radius: 50%"/>
                        <span class="small" style="font-size: 9px;">${message.user.username}</span>
                    </div>
                    <div class="rounded" style="padding: 3px 2px; border: 1px solid #bdbdbd; border-radius: 25px">
                        <p style="font-size: 0.9rem; margin-bottom: 0; padding: 5px; color: #262626; word-break: break-all;">
                            ${message.content}
                        </p>
                    </div>
                <p class="small" style="font-size: 9px;">${message.createdAt}</p>
                </div>
            </div>`;
}

function createSendChatView(message) {
    return `<div class="chat">
                <div style="width: 70%; margin-right: 10px; margin-bottom: 3px; margin-left: auto;">
                    <div class="send-media-body" style="padding-top: 3px;">
                        <div class="rounded" style="padding: 3px 2px; border: 1px solid #bdbdbd; border-radius: 25px; ">
                            <p style="font-size: 0.9rem; margin-bottom: 0; padding: 5px; color: #262626; word-break: break-all;">
                                ${message.content}
                            </p>
                        </div>
                    <p class="small" style="font-size: 9px;">${message.createdAt}</p>
                    </div>
                </div>
            </div>`;
}

function createChatRoomView(data) {
    return `<div id="chat-room-${data.otherUser.userId}" onclick="enterChatRoom(${data.chatRoomId}, ${data.otherUser.userId})">
                <div class="row" style="width: 100%;">
                    <div class="col-3" style="margin-left: 10px; margin-top: 10px;">
                        <img src="/file/profile/${data.otherUser.profileImageUrl}" style="width: 46px; height: 46px; border-radius: 50%;">
                    </div>
                    <div class="col-4" style="margin-left: 0px; margin-top: 20px; padding-left: 0;">
                        <p style="font-size: 12px; margin-left: 0;">${data.otherUser.username}</p>
                    </div>
                </div>
            </div>`;
}

function createSearchResultView(user) {
    return `<div class="row search-result-box" style="--bs-gutter-x: 0" onclick="createChatRoom(${user.userId})">
                <div class="col-2">
                    <img src="/file/profile/${user.profileImageUrl}" style="border-radius: 50%; width: 32px; height: 32px;">
                </div>
                <div class="col-8">
                    <p>${user.username}</p>
                </div>
            </div>`;
}

function recvMessage(recv, otherUserId) {
    if (recv.user.userId === otherUserId)
        $("#chat-list").append(createRecvChatView(recv));
    else
        $("#chat-list").append(createSendChatView(recv));
}

function enterChatRoom(chatRoomId, otherUserId) {
    try {
        stomp.disconnect();
    } catch (e) {
    }
    sock = new SockJS("/chatroom");
    stomp = Stomp.over(sock);
    stomp.connect({}, function () {
        stomp.subscribe("/sub/chatroom/" + chatRoomId, function (message) {
            let recv = JSON.parse(message.body);
            recvMessage(recv, otherUserId);
        });
    });
    renderChat(chatRoomId, otherUserId);
}

function renderChat(chatRoomId, otherUserId) {
    $.ajax({
        type: "get",
        url: "/api/chatrooms/" + chatRoomId + "/messages",
        dataType: "Json",
        success: function (data) {
            $(".chat").remove();
            $(".chatroom").remove();
            $(".chat-box").append(createChatView(data, chatRoomId, otherUserId));
        },
        error: function (data) {
            errorHandle(data);
        }
    })
}

function createChatRoom(userId) {
    $("#search-modal").modal("hide");
    let chatroom = $("#chat-room-" + userId);
    if (chatroom.length){
        console.log(chatroom);
        chatroom.trigger("onclick");
        return;
    }
    $.ajax({
        type: "post",
        url: "api/chatrooms",
        data: {targetUserId: userId},
        success: function (data, status, xhr) {
            addChatListAndEnterRoom(xhr.getResponseHeader("Location"));
        },
        error: function (data) {
            errorHandle(data);
        }
    });
}

function addChatListAndEnterRoom(url) {
    $.ajax({
        type: "get",
        url: url,
        dataType: "Json",
        success: function (data) {
            $("#chat-room-box").prepend(createChatRoomView(data));
            enterChatRoom(data.chatRoomId, data.otherUser.userId);
        },
        error: function (data) {
            errorHandle(data);
        }
    });
}

function search() {
    //append to #search-result
    let filter = $("#search-input").val();
    $("#search-input").val("");
    if (filter === "")
        return;
    $(".search-result-box").remove();
    $.ajax({
        type: "get",
        url: "/api/search?filter=" + filter,
        dataType: "Json",
        success: function (data) {
            if (data)
                data.forEach(user => {
                    $("#search-result").append(createSearchResultView(user));
                })
        },
        error: function (data) {
            errorHandle(data);
        }
    });
}