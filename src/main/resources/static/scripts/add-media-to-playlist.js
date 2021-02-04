const revealLink = document.querySelector("#reveal-link");
const revealArrow = document.querySelector(".reveal-arrow");
const mediaFileTable = document.querySelector(".media-library");
let addFileButtons = document.querySelectorAll(".add-new-file-btn")
const playlistFileTable = document.querySelector("#playlist-files-table");
const saveChangesButton = document.querySelector("#save-changes");
const fileInfo = document.getElementById("playlist-files-table").getElementsByClassName("file-info");

const revealMediaTable = () => {
    revealLink.onclick = event => {
        revealArrow.style.transform = "rotate(90deg)";
        mediaFileTable.style.display = "block";
        hideMediaTable();
    }
};

const hideMediaTable = () => {
    revealLink.onclick = event => {
        revealArrow.style.transform = "rotate(0deg)";
        mediaFileTable.style.display = "none";
        revealMediaTable();
    }
};

const addFileToPlaylist = () => {
    addFileButtons.forEach(file => {
        file.onclick = event => {
            let row = event.target.parentElement.parentElement;
            let rowCopy = row.cloneNode(true);
            let minusSign = document.createElement("td");
            minusSign.innerHTML = "<i class='bi bi-dash remove-btn'></i>";
            rowCopy.querySelector(".add-new-file-btn").remove();
            rowCopy.appendChild(minusSign);
            playlistFileTable.append(rowCopy);
            addSaveChangesButton()
            return playlistFileTable;
        }
    });
};

const removeFile = () => {
    playlistFileTable.addEventListener("click", function(event) {
        let element = event.target;
        if (element.className === "bi bi-dash remove-btn") {
            event.target.parentElement.parentElement.remove()
        }
    });
};

const addSaveChangesButton = () => {
    saveChangesButton.style.display = "block";
}


saveChangesButton.onclick = event => {

    let idArray = [];
    for (let i = 0; i < fileInfo.length; i++) {
        idArray.push(fileInfo.item(i).textContent);
    }

    const playlistId = playlistFileTable.getAttribute("data");

    let data = {
        playlistFiles: idArray,
        playlistId: playlistId
    };

    fetch("/save-changes-to-playlist", {
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
}

revealMediaTable();
addFileToPlaylist();
removeFile();
