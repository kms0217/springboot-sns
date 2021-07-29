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
            errorHandle(data);
        }
    });
}