function toggle(source, type) {
    let checkboxes;
    if (type === "JOB")
        checkboxes = document.getElementsByName('job_months[]');
    else if (type === "SHIFT")
        checkboxes = document.getElementsByName('shift_months[]');
    else if (type === "OVERWORK")
        checkboxes = document.getElementsByName('overwork_months[]');
    checkboxes.forEach(function (checkbox) {
        checkbox.checked = source.checked;
    });
}