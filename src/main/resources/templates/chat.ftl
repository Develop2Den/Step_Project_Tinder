<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Chat</title>
    <!-- Bootstrap core CSS -->
    <link href="/static/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/style.css">
</head>
<body>
<div class="container">
    <div class="d-flex justify-content-between align-items-center my-4">
        <form action="/logout" method="post">
            <button class="btn btn-danger">Logout</button>
        </form>
    </div>
    <div class="chat-main col-6 offset-3">
        <div class="col-md-12 chat-header">
            <div class="row header-one text-white p-1">
                <div class="col-md-6 name pl-2">
                    <i class="fa fa-comment"></i>
                    <h6 class="ml-1 mb-0">${userProfile.name}</h6>
                </div>
                <div class="col-md-6 options text-right pr-0">
                    <i class="fa fa-window-minimize hide-chat-box hover text-center pt-1"></i>
                    <p class="arrow-up mb-0">
                        <i class="fa fa-arrow-up text-center pt-1"></i>
                    </p>
                    <i class="fa fa-times hover text-center pt-1"></i>
                </div>
            </div>
            <div class="row header-two w-100">
                <div class="col-md-6 options-left pl-1">
                    <i class="fa fa-video-camera mr-3"></i>
                    <i class="fa fa-user-plus"></i>
                </div>
                <div class="col-md-6 options-right text-right pr-2">
                    <i class="fa fa-cog"></i>
                </div>
            </div>
        </div>
        <div class="chat-content">
            <div class="col-md-12 chats pt-3 pl-2 pr-3 pb-3">
                <ul class="p-0">
                    <#list messages as message>
                        <#if message.senderId == currentUserId>
                            <li class="send-msg float-right mb-2">
                                <p class="pt-1 pb-1 pl-2 pr-2 m-0 rounded bg-primary text-white">
                                    ${message.content}
                                </p>
                                <span class="send-msg-time">${message.timestamp}</span>
                            </li>
                        <#else>
                            <li class="receive-msg float-left mb-2">
                                <div class="sender-img">
                                    <img src="${userProfile.photo}" class="float-left" alt="Sender Image">
                                </div>
                                <div class="receive-msg-desc float-left ml-2">
                                    <p class="bg-white m-0 pt-1 pb-1 pl-2 pr-2 rounded">
                                        ${message.content}
                                    </p>
                                    <span class="receive-msg-time">${message.senderUsername}, ${message.timestamp}</span>
                                </div>
                            </li>
                        </#if>
                    </#list>
                </ul>
            </div>
            <form class="msg-box" method="post">
                <input type="hidden" name="senderId" value="${currentUserId}">
                <input type="hidden" name="receiverId" value="${userProfile.id}">
                <div class="row">
                    <div class="col-md-2 options-left">
                        <i class="fa fa-smile-o"></i>
                    </div>
                    <div class="col-md-7 pl-0">
                        <input type="text" name="content" placeholder="Send message" class="form-control">
                    </div>
                    <div class="col-md-3 text-right options-right">
                        <i class="fa fa-picture-o mr-2"></i>
                        <button class="btn btn-primary" type="submit">Send</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
