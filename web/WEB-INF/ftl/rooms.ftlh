<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, appUser-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Rooms</title>
    <script
            src="https://code.jquery.com/jquery-3.4.1.js"
            integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
            crossorigin="anonymous"></script>

    <script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>

    <script>
        var newRoomId = Math.floor(Math.random() * 1000000000);
        function redirect(roomID) {
            window.location.href = 'http://localhost:8080/room/' + roomID;
        }
    </script>
</head>


<body>
<h1>Login - ${login}</h1>
<h1>Rooms</h1>
    <#list roomsId as r>
        <a href="/room/${r}" onclick="redirect('${r}','${login}')">Room ID - ${r}</a> <br>
    </#list>
        <button onclick="redirect(newRoomId, '${login}')">New Room</button>
</body>
</html>