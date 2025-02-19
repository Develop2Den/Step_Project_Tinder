<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="img/favicon.ico">

    <title>Like page</title>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.13/css/all.css" integrity="sha384-DNOHZ68U8hZfKXOrtjWvjxusGo9WQnrNx2sqG0tfsghAvtVlRW3tvkXWZh58N9jp" crossorigin="anonymous">
    <!-- Bootstrap core CSS -->
    <link href="/static/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/style.css">
</head>
<body style="background-color: #f5f5f5;">

<div class="container">
    <div class="row mt-3">
        <div class="col-12 text-right">
            <p>Привет, ${currentUser.name}!</p>
            <form method="post" action="/logout" style="display:inline;">
                <button type="submit" class="btn btn-secondary">Logout</button>
            </form>
            <a href="/liked" class="btn btn-primary">View Liked Profiles</a>
        </div>
    </div>
    <div class="col-4 offset-4">
        <div class="card">
            <div class="card-body">
                <div class="row">
                    <div class="col-12 col-lg-12 col-md-12 text-center">
                        <img src="${user.photo}" alt="" class="mx-auto rounded-circle img-fluid">
                        <h3 class="mb-0 text-truncated">${user.name}</h3>
                        <br>
                    </div>
                    <div class="col-12 col-lg-6">
                        <form method="post" action="/users">
                            <button type="submit" name="action" value="dislike" class="btn btn-outline-danger btn-block">
                                <span class="fa fa-times"></span> Dislike
                            </button>
                        </form>
                    </div>
                    <div class="col-12 col-lg-6">
                        <form method="post" action="/users">
                            <button type="submit" name="action" value="like" class="btn btn-outline-success btn-block">
                                <span class="fa fa-heart"></span> Like
                            </button>
                        </form>
                    </div>
                    <!--/col-->
                </div>
                <!--/row-->
            </div>
            <!--/card-block-->
        </div>
    </div>
</div>

</body>
</html>
