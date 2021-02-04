const inputDiv = document.querySelector(".category-text-input");

const addInput = () => {
    inputDiv.onclick = event => {
        if (event.target.classList.contains("add-category-btn")) {
            let element = document.createElement("div");
            element.innerHTML = '<label for="newcategory" class="form-label">New category</label>' +
                '<input type="text" name="category" id="newcategory" class="form-control">' +
                '<i class="bi bi-plus-square add-category-btn"></i>';
            inputDiv.append(element);
        }
    }
};

addInput();