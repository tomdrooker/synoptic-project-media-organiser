const playerDiv = document.querySelector("#media-player");
let  playButtons = document.querySelectorAll(".play-button");

playButtons.forEach(button => {
    button.onclick = event => {

        let fileType = button.parentElement.className
            .substring(button.parentElement.className.lastIndexOf('.') + 1,
                button.parentElement.className.length);

        if (fileType === "mp4" || fileType === "mov") {
            let html = '<video controls width="500" autoplay>' +
                '    <source src=' + button.parentElement.className +
                '    Your browser does not support the media player' +
                '</video><br/>';
            if (button.parentElement.getAttribute("data") != null) {
                html += '<img src="' + button.parentElement.getAttribute("data") + '" class="img-thumbnail">'
            }
            playerDiv.innerHTML = html;
        }

        else {
            let html = '<audio' +
                '        controls autoplay' +
                '        src=' + button.parentElement.className + '>' +
                '            Your browser does not support the media player' +
                '    </audio><br/>';
            if (button.parentElement.getAttribute("data") != null) {
                html += '<img src="' + button.parentElement.getAttribute("data") + '" class="img-thumbnail">'
            }
            playerDiv.innerHTML = html;
        }
    };
});