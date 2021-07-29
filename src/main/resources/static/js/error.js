function errorHandle(data) {
    if (data.status === 403)
        location.replace("/login");
    if (data != undefined && data.responseJSON != undefined && data.responseJSON.message != undefined)
        alert(data.responseJSON.message);
    else
        alert("error");
}