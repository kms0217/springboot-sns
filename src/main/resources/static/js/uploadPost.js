const inputImage = document.getElementById("image")
inputImage.addEventListener("change", e => {
    readImage(e.target)
});

function upload() {
    let dataArray = $("#upload-form").serializeArray();
    let data = new FormData();
    if ($('input[name=image]')[0].files[0] != undefined) {
        data.append('image', $('input[name=image]')[0].files[0]);
    }
    dataArray.forEach(ele => data.append(ele.name, ele.value));
    $.ajax({
        type: "post",
        url: '/api/stories',
        data: data,
        contentType: false,
        processData: false,
        success: function () {
            alert("upload에 성공했습니다.")
            window.location.href = "http://localhost:9091/profile"
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