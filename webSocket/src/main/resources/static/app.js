var stompClient = null;

function setConnected(connected) {
    // connect 된 상태면 connect 버튼은 보이지 않고, !connected 상태면 disconnected 버튼은 보이지 않는다.
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

function connect() {
    // SockJS로 기본 endpoint를 작성? 여기서 SockJS 의 역할이 뭘까?
    // Stomp를 위에 SockJS를 올린다? => stompClient
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        // connect 를 true로 설정
        setConnected(true);
        console.log('Connected: ' + frame);   // 여기서 frame 뭔지 확인해보기
        // /topic/greetings 목적지를(?) 구독한다 => message가 브로드 캐스트 될!! Controller를 확인해보면 된다
        stompClient.subscribe('/topic/greetings', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});