<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <script
            src="https://code.jquery.com/jquery-3.4.1.js"
            integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
            crossorigin="anonymous"></script>

    <script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
    <script>
        var webSocket;
        function connect() {
            // webSocket = new WebSocket('ws://localhost:8080/chat');
            webSocket = new SockJS('http://localhost:8080/chat');
            document.cookie = 'X-Authorization=' + '123456' + ';path=/';

            webSocket.onmessage = function receiveMessage(response) {
                let data = response['data'];
                let json = JSON.parse(data);
                $('#messages').append("<li><b>" + json['login'] + '</b> ' + json['text'] + "</li>")
            }
        }

        function sendMessage(roomId, login, text) {
            let message = {
                "roomId":roomId,
                "login": login,
                "text": text
            };

            webSocket.send(JSON.stringify(message));
        }
    </script>
</head>
<body onload="connect(); setTimeout(sendMessage, 2*1000, '${roomId}', '${login}', '${login} connected')">
<h1>Room ID - ${roomId}</h1>

<div>
    <ul id="messages">

    </ul>
</div>
<div>
    <label for="message"></label>
    <input name="message" id="message" placeholder="Message">
    <button onclick="sendMessage('${roomId}', '${login}', $('#message').val())">Send</button>
</div>
<div>

</div>
</body>
</html>