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

			//read from database
			$query = "select * from log where username = '$user_name' limit 1";
			$result = mysqli_query($con, $query);

			if($result)
			{
				if($result && mysqli_num_rows($result) > 0)
				{

					$user_data = mysqli_fetch_assoc($result);
					
					if($user_data['password'] === $password)
					{

						$_SESSION['id'] = $user_data['id'];
						header("Location: index.php");
						die;
					}
				}
			}
			
			echo "wrong username or password!";
		}else
		{
			echo "wrong username or password!";
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
  <h1>Login</h1>
  <form method="post">
    <div class="textbox">
      <i class="fas fa-user"></i>
      <input id="text" type="text" name="user_name" class="form-control" placeholder="Username">
    </div>

    <div class="textbox">
      <i class="fas fa-lock"></i>
      <input id="text" type="password" name="password" placeholder="Password" class="form-control">
    </div>
    <input id="button" type="submit" value="Login" class="btn">

	<a href="signup.php" class="btn">Click to Signup</a>
	</form>
</div>
  </body>
</html>