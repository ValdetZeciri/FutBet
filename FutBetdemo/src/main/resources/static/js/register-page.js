// const loginForm = document.getElementById("login-form");
// const loginButton = document.getElementById("login-form-submit");
// const loginErrorMsg = document.getElementById("login-error-msg");
//
// loginButton.addEventListener("click", (e) => {
//     e.preventDefault();
//     const username = loginForm.username.value;
//     const pw = loginForm.password.value;
//
//     //check empty password field
//     if(pw == "") {
//         document.getElementById("login-error-msg-holder").innerHTML = "**Fill the password please!";
//         return false;
//     }
//
//     //minimum password length validation
//     if(pw.length < 8) {
//         document.getElementById("login-error-msg-holder").innerHTML = "**Password length must be atleast 8 characters";
//         return false;
//     }
//
// //maximum length of password validation
//     if(pw.length > 15) {
//         document.getElementById("login-error-msg-holder").innerHTML = "**Password length must not exceed 15 characters";
//         return false;
//     } else {
//         document.getElementById("login-error-msg-holder").innerHTML = "Login successfull";
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