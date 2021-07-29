function update(userId) {
    let dataArray = $("#update-form").serializeArray();
    let data = new FormData();
    if ($('input[name=image]')[0].files[0] != undefined) {
        data.append('image', $('input[name=image]')[0].files[0]);
    }
    dataArray.forEach(ele => data.append(ele.name, ele.value));
    $.ajax({
        type: "put",
        url: '/api/users/' + userId,
        data: data,
        contentType: false,
        processData: false,
        success: function () {
            alert("변경에 성공하였습니다.");
            window.location.href = "http://localhost:9091/profile";
        },
        error: function (data) {
            errorHandle(data);
        }
    });
}

const inputImage = document.getElementById("image")
inputImage.addEventListener("change", e => {
    readImage(e.target)
})