<?php 
session_start();

	include("connection.php");
	include("functions.php");


	if($_SERVER['REQUEST_METHOD'] == "POST")
	{
		//something was posted
		$user_name = $_POST['user_name'];
		$password = $_POST['password'];

		if(!empty($user_name) && !empty($password) && !is_numeric($user_name))
		{
			$sql="select username from log where username = '$user_name' limit 1";
			$result=mysqli_query($con, $sql);


			$user_data = mysqli_fetch_assoc($result);

			if($user_name == $user_data['username']){
				echo "<h1 style=\"text-align:center; color:white;\">Error</h1>";
			}else
			{
				//save to database
				$user_id = random_num(20);
				$query = "insert into log (username,password) values ('$user_name','$password')";

				mysqli_query($con, $query);

				header("Location: login.php");
				echo "succes";
				die;
			}
		}else
		{
			echo "Please enter some valid information!";
		}
	}
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
  <h1>Signup</h1>
  <form method="post">
    <div class="textbox">
      <i class="fas fa-user"></i>
      <input id="text" type="text" name="user_name" class="form-control" placeholder="Username">
    </div>

    <div class="textbox">
      <i class="fas fa-lock"></i>
      <input id="text" type="password" name="password" placeholder="Password" class="form-control">
    </div>
    <input id="button" type="submit" value="Signup" class="btn">

	<a href="login.php" class="btn">Click to Login</a>
	</form>
</div>
  </body>
</html>