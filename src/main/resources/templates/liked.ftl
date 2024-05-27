<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="img/favicon.ico">

    <title>Liked Profiles</title>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.13/css/all.css" integrity="sha384-DNOHZ68U8hZfKXOrtjWvjxusGo9WQnrNx2sqG0tfsghAvtVlRW3tvkXWZh58N9jp" crossorigin="anonymous">
    <!-- Bootstrap core CSS -->
    <link href="/static/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/style.css">
</head>
<body style="background-color: #f5f5f5;">
<div class="container">
    <div class="row mt-3">
        <div class="col-12 text-right">
            <form method="post" action="/logout" style="display:inline;">
                <button type="submit" class="btn btn-secondary">Logout</button>
            </form>
        </div>
    </div>
    <div class="row">
        <#list likedProfiles as user>
            <div class="col-md-4">
                <div class="card mb-4 shadow-sm">
                    <img class="card-img-top" src="${user.photo}" alt="Profile Photo">
                    <div class="card-body">
                        <h5 class="card-title">${user.name}</h5>
                        <a href="/messages/${user.id}" class="btn btn-primary">Chat</a>
                    </div>
                </div>
            </div>
        </#list>
    </div>
</div>
</body>
</html>
