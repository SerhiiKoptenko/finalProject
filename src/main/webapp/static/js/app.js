;function showAddCourseForm() {
    $("#add-course-form").removeClass("d-none");
    $("#disp-add-course").addClass("d-none");
};

function hideAddCourseForm() {
    $("#add-course-form").addClass("d-none");
    $("#disp-add-course").removeClass("d-none");
};

$(function() {
    $("#disp-add-course").click(showAddCourseForm);
    $("#hide-add-course").click(hideAddCourseForm);
})