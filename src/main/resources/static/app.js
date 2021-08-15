let wedulStompClient = null;
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
        client.subscribe('/topic/greetings', function (greeting) {
            console.log(greeting)
            showGreeting("[hello] receive User : " + user + " " + JSON.parse(greeting.body).content);
        });

        // direct로 topic에게 보내기
        client.subscribe('/direct/people', function (greeting) {
            console.log(greeting)
            showGreeting("[direct] receive User : " + user + " " + JSON.parse(greeting.body).content);
        });

        // user 메시지
        // user 메시지
        client.subscribe('/user/topic/data', function (greeting) {
            console.log(greeting)
            showPrivateMessage("[specific user - annotation] receive User : " + user + ", message : " + JSON.parse(greeting.body).content);
        })

        // user 메시지
        client.subscribe(`/user/topic/message`, function (n) {
            console.log(n);
            showPrivateMessage("[specific user - messaging] receive User : " + user + ", message : " + JSON.parse(n.body).message);
        })

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

// common send
function sendName(client) {
    // app prefix를 달고 있고 MessageMapping에 hello로 되어있는곳에 추가
    client.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));
}

function sendDirect(client) {
    // 특정 토픽에 다이렉트로 보내기
    client.send("/direct/people", {}, JSON.stringify({'content': $("#name").val()}));
}

function sendUserDirectPath(client, user) {
    client.send(`/user/${user}/message`, {}, JSON.stringify({
        'message': $("#message").val()
    }));
}

function sendUserAnnotation(client, user) {
    client.send(`/app/message`, {}, JSON.stringify({
        'message': $("#message").val(),
        'name': user
    }));
}

function sendMessageTemplateToUser(client, user) {
    client.send("/app/message/sendToUser", {}, JSON.stringify({
        'message': $("#message").val(),
        'targetUserName': user
    }));
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
        wedulStompClient = connect("wedul");
        chulStompClient = connect("chul");
    });
    $( "#disconnect" ).click(function() {
        disconnect(wedulStompClient, "wedul");
        disconnect(chulStompClient, "chul");
    });
    $( "#send" ).click(function() {
        sendName(wedulStompClient);
        sendName(chulStompClient);
    });
    $( "#sendDirect" ).click(function() {
        sendDirect(wedulStompClient);
        sendDirect(chulStompClient);
    });
    $("#sendMessageDirectPath").click(function () {
        let sendUserName = $("#sendUserName option:selected").val();
        let receiveUserName = $("#receiveUserName option:selected").val();

        let client = sendUserName === 'wedul' ? wedulStompClient : chulStompClient;
        sendUserDirectPath(client, receiveUserName);
    });
    $("#sendMessageTemplateToUser").click(function () {
        let sendUserName = $("#sendUserName option:selected").val();
        let receiveUserName = $("#receiveUserName option:selected").val();

        let client = sendUserName === 'wedul' ? wedulStompClient : chulStompClient;
        sendMessageTemplateToUser(client, receiveUserName);
    });
    $("#sendUserAnnotation").click(function () {
        let sendUserName = $("#sendUserName option:selected").val();
        let receiveUserName = $("#receiveUserName option:selected").val();

        let client = sendUserName === 'wedul' ? wedulStompClient : chulStompClient;
        sendUserAnnotation(client, receiveUserName);
    });
});