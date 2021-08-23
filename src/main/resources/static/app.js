let wedulStompClient1 = null;
let wedulStompClient2 = null;
let chulStompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect(user) {
    let socket = new SockJS('/gs-guide-websocket');
    let client = Stomp.over(socket);
    client.heartbeat.outgoing = 0;
    client.heartbeat.incoming = 0;
    client.connect({
        user
    }, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        // user 메시지
        client.subscribe('/user/topic/data', function (message) {
            $('.' + user).text(JSON.parse(message.body).count);
        })

        // broadcast message
        client.subscribe('/topic/message', function (message) {
            console.log(JSON.parse(message.body).message);
            alert(message);
        });

        // 에러메시지 핸들링
        client.subscribe('/user/queue/errors', function (message) {
            console.log(message);
            alert("[error] receive User : " + user + " "  + message);
        });
        client.heartbeat.outgoing = 0;
    });

    return client;
}

function disconnect(client, user) {
    if (client !== null) {
        client.disconnect();
    }
    setConnected(false);
    console.log(user + " Disconnected");
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

function showPrivateMessage(message) {
    $("#greetings").append("<tr><td>" + "private message : " + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() {
        wedulStompClient1 = connect("wedul");
        wedulStompClient2 = connect("wedul");
        chulStompClient = connect("chul");
    });
    $( "#disconnect" ).click(function() {
        disconnect(wedulStompClient1, "wedul");
        disconnect(wedulStompClient2, "wedul");
        disconnect(chulStompClient, "chul");
    });
});