function errorHandle(data) {
    if (data.status === 403)
        location.replace("/login");
    if (data != undefined && data.responseJSON != undefined && data.responseJSON.myMessage != undefined)
        alert(data.responseJSON.myMessage);
    else
        alert(data);
}