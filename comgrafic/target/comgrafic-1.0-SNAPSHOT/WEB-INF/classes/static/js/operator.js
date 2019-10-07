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
function showExtraInput() {
    let jobType = document.getElementById('jobTypeName');

    let x = document.getElementById('specification');
    if (jobType.value === 'Otros') {
        x.style.display = 'block';
    } else {
        x.style.display = 'none';
    }

    x = document.getElementById('numberOfPlates');
    if (jobType.value === 'Cambio planchas') {
        x.style.display = 'block';
    } else {
        x.style.display = 'none';
    }

    x = document.getElementById('tiraje_div');
    if (jobType.value === 'Tiraje') {
        x.style.display = 'block';
    } else {
        x.style.display = 'none';
    }
}

function confirmLogout (names) {
    alert("TEST");
    alert ("WARNING: These operators are still working on a shift/job:"+ names);
    console.log ("WARNING: These operators are still working on a shift/job:"+ names);
    names.forEach(function (element) {
        console.log("busy operator: "+element);
        alert("busy operator: "+element);
    });
}