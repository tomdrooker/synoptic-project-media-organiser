const addBtn = document.querySelectorAll(".add-btn");
const playlistTable = document.querySelector("#playlist-table-body");
const fileInfo = document.getElementById("playlist-table-body").getElementsByClassName("file-info");
const savePlaylistBtn = document.querySelector("#playlist-save-btn");

const addEvents = () => {
    addBtn.forEach(file => {
        file.onclick = event => {
            let row = event.target.parentElement.parentElement;
            let rowCopy = row.cloneNode(true);
            let minusSign = document.createElement("td");
            minusSign.innerHTML = "<i class='bi bi-dash remove-btn'></i>";
            rowCopy.querySelector(".add-btn").remove();
            rowCopy.appendChild(minusSign);
            playlistTable.append(rowCopy);
            return playlistTable;
        }
    });
}

playlistTable.addEventListener("click", function(event) {
    let element = event.target;
    if (element.className === "bi bi-dash remove-btn") {
        event.target.parentElement.parentElement.remove()
    }
});

savePlaylistBtn.onclick = event => {

    let idArray = [];
    for (let i = 0; i < fileInfo.length; i++) {
        idArray.push(fileInfo.item(i).textContent);
    }

    let playlistName = document.querySelector("#playlist-name");

    let data = {
        playlistname: playlistName.value,
        playlistFiles: idArray
    };

    fetch("/save-playlist", {
        method: "POST",
        headers: {
            'Content-type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => {
            if (response.ok) {
                return response.json();
            }
            throw new Error("Request failed!")
        }, networkError => console.log(networkError.message));
    playlistTable.innerHTML = "";
    document.querySelector("#playlist-name-div").innerHTML = '<input type="text" placeholder="New playlist name" class="form-control" id="playlist-name" aria-label="Large" aria-describedby="inputGroup-sizing-sm">';
};

addEvents();


