;
$(document).ready(function() {
    initManageCourses();
});
function initManageCourses() {
    $("#disp-add-course").click(function() {
        $("#add-course-form").removeClass("d-none");
        $("#disp-add-course").addClass("d-none");
    });
    $("#hide-add-course").click(function () {
        $("#add-course-form").addClass("d-none");
        $("#disp-add-course").removeClass("d-none");
        $(this).find("#course-name").focus();
    });

    let addThemeModal = document.getElementById("add-theme-modal");
    let addThemeInput = $(".add-theme-input");

    addThemeModal.addEventListener("shown.bs.modal", function() {
        addThemeInput.focus()
    })

    $(".close-add-theme").click(function() {
        $(".theme-exists-message").addClass("d-none");
        $(".theme-empty-message").addClass("d-none");
    });

    const urlParams = new URLSearchParams(window.location.search);


    const addResult = urlParams.get("themeAdded");
    if (addResult != null) {
        $("#disp-add-course").click();
    }
    if (addResult === "themeAlreadyExists") {
        $("#add-theme-modal").modal("show");
        $("#theme-exists-message").removeClass("d-none");
        $("#close-add-theme").click(function() {
            $("#theme-exists-message").addClass("d-none");
        });
    }

    let removeThemeModal = document.getElementById("remove-theme-modal");

    $("#close-remove-theme").click(function() {
        $("#cant-delete-theme-message").addClass("d-none");
    });

    const removeResult = urlParams.get("themeRemoved");
    if (removeResult != null) {
        $("#disp-add-course").click();
    }
    if (removeResult === "cantBeDeleted") {
        $("#remove-theme-modal").modal("show");
        $("#cant-delete-theme-message").removeClass("d-none");
        $("#close-remove-theme").click(function() {
            $(".cant-delete-theme-msg").addClass("d-none");
        });
    }
}

