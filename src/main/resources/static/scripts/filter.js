let checkboxes = document.querySelectorAll(".filter-check");
const table = document.querySelector("#media-library-table");
let categoryColumns = document.querySelectorAll(".category-column");

const checkboxEvents = () => checkboxes.forEach(check => {
    check.addEventListener("change", () => {
        let categoriesChecked = document.querySelectorAll("input:checked");
        if (categoriesChecked.length >= 1) {
            filterTable(categoriesChecked);
        }
        else if (categoriesChecked.length == 0) {
            showAll();
        }
    });
});

const filterTable = categories => {
    let selectedCategories = [];
    categories.forEach(category => selectedCategories.push(category.value.trim()));

    categoryColumns.forEach(column => {
        for (let i = 0; i < selectedCategories.length; i++) {
            if (column.textContent.includes(selectedCategories[i])) {
                column.parentElement.hidden = false;
            }
            else if (!column.textContent.includes(selectedCategories[i])) {
                column.parentElement.hidden = true;
            }
        }
    });
};

const showAll = () => {
    categoryColumns.forEach(column => column.parentElement.hidden = false);
}

checkboxEvents();