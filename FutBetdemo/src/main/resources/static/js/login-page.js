// const loginForm = document.getElementById("login-form");
// const oginButton = document.getElementById("login-form-submit");
// const loginErrorMsg = document.getElementById("login-error-msg");
//
// oginButton.addEventListener("click", (e) => {
//     e.preventDefault();
//     const username = loginForm.username.value;
//     const pw = loginForm.password.value;
//
//     //check empty password field
//     if(pw == "") {
//         document.getElementById("login-error-msg-holder").innerHTML = "Fill the password please!";
//         return false;
//     }
//
//     // if (username === "user" && password === "web_dev") {
//     //     alert("You have successfully logged in.");
//     //     location.reload();
//     // } else {
//     //     loginErrorMsg.style.opacity = 1;
//     // }
//
//
//     var xhr = new XMLHttpRequest();
//
//     xhr.onreadystatechange = function () {
//         if (xhr.readyState == XMLHttpRequest.DONE) {
//             if (xhr.status == 200) {
//                 document.body.innerText = xhr.responseText;
//             } else {
//                 document.body.innerText = 'Error: ' + xhr.status;
//             }
//         }
//     };
//
//     xhr.open('POST', '/login-page/login', true);
//     xhr.send(username +";" + pw);
// })