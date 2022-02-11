<?php 
session_start();

	include("connection.php");
	include("functions.php");

	$user_data = check_login($con);

?>

<!DOCTYPE html>
<html lang="en" dir="ltr">
  <head>
    <meta charset="utf-8">
    <title></title>
    <link rel="stylesheet" href="style.css">
  </head>
  <body>
<div class="login-box">
  <h1>Login</h1>
  <form action="connect.php" method="post">
    <div class="textbox">
      <i class="fas fa-user"></i>
      <input type="username" placeholder="Username" class="form-control" id="username" name="username">
    </div>

    <div class="textbox">
      <i class="fas fa-lock"></i>
      <input type="password" placeholder="Password" class="form-control" id="password" name="password">
    </div>
    <input type="submit" class="btn" value="Sign in">
  </form>
</div>
  </body>
</html>
