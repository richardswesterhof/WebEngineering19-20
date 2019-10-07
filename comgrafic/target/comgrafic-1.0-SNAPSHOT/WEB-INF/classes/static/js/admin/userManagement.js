function checkPassword() {
    var pw = document.getElementById("newPassword")
        , pwC = document.getElementById("confirm_newPassword");
    // alert(pw.value+"/"+pwC.value);
    pwC.setCustomValidity('');
    if (pw.value === '')
        pw.setCustomValidity("Enter a password");
    if (pwC.value === '')
        pwC.setCustomValidity("Enter a password");

    if (pw.value !== pwC.value) {
        pwC.setCustomValidity("Passwords don't match");
        alert("NO MATCH");
        return false;
    }
    alert("YES MATCH");
    pwC.setCustomValidity('');
    return true;
}