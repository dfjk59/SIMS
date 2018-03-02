/*
Originally found at https://cssdeck.com/labs/login-form-using-html5-and-css3

by: https://cssdeck.com/user/kamalchaneman




*/
function checkInput() {
    var password_input = document.getElementById('password');
    var password_md5 = document.getElementById('password_md5');

    // set password
    password_md5.value =  md5(password_input.value);
    return true;
}