// Modal
$(document).ready(function () {
    $('#startShiftbtnModal').click(function () {
        //functions to open modals
        $('#startShiftmodal').modal('show')
    });
    $('#stopShiftbtnModal').click(function () {
        $('#stopShiftmodal').modal('show')
    });
    $('#startJobbtnModal').click(function () {
        $('#startJobmodal').modal('show')
    });
    $('#stopJobbtnModal').click(function () {
        $('#stopJobmodal').modal('show')
    });
});

/**
 *
 */
function showSpecification() {
    var x = document.getElementById('specification');
    var jobType = document.getElementById('jobTypeName');
    if (jobType.value === 'Otros') {
        x.style.display = 'block';
    } else {
        x.style.display = 'none';
    }
}