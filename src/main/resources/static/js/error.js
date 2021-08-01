function errorHandle(data) {
    if (data.status === 401)
        location.replace("/login");
    if (data != undefined && data.responseJSON != undefined && data.responseJSON.myMessage != undefined)
        alert(data.responseJSON.myMessage);
    else
        alert("error가 발생했습니다.");
}