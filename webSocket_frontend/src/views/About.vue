<template>
  <div class="about">
    <!-- 시작 -->
    <div id="main-content" class="container">
        <div class="row">
            <div class="col-md-6">
              <div class="form-group">
                  <label for="connect">WebSocket connection:</label>
                  <button id="connect" class="btn btn-default" @click="onClickConnectBtn">Connect</button>
                  <button id="disconnect" class="btn btn-default" @click="onClickDisconnecedtBtn">Disconnect</button>
              </div>

            </div>
            <div class="col-md-6">
              <div class="form-group">
                  <label for="name">What is your name?</label>
                  <input type="text" id="name" v-model="state.name_value"  class="form-control" placeholder="Your name here...">
              </div>
              <button id="send" class="btn btn-default" @click="onClickSendBtn" >Send</button>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <table id="conversation" class="table table-striped">
                    <thead>
                    <tr>
                        <th>Greetings</th>
                    </tr>
                    </thead>
                    <tbody id="greetings">
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <!-- 끝 -->
  </div>
</template>

<script>
import {reactive} from 'vue'
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'

export default {
  name : "About",

  setup() {

    const state = reactive({
      name_value : '',
      stompClient : ''
    })

    function connect() {
        // SockJS로 기본 endpoint를 작성? 여기서 SockJS 의 역할이 뭘까?
        // Stomp를 위에 SockJS를 올린다? => stompClient
        var socket = new SockJS('http://localhost:8080/gs-guide-websocket');
        state.stompClient = Stomp.over(socket);
        state.stompClient.connect({}, function (frame) {
            // connect 를 true로 설정
            console.log('Connected: ' + frame);   // 여기서 frame 뭔지 확인해보기
            // /topic/greetings 목적지를(?) 구독한다 => message가 브로드 캐스트 될!! Controller를 확인해보면 된다
            state.stompClient.subscribe('/topic/greetings', function (greeting) {
              showGreeting(JSON.parse(greeting.body).content);
            });
        });
    }

    function disconnect() {
        if (state.stompClient !== null) {
            state.stompClient.disconnect();
        }
        console.log("Disconnected");
    }

    function sendName() {
      state.stompClient.send()
      state.stompClient.send("/app/hello", JSON.stringify({'name': state.name_value}));
    }

    function showGreeting(message) {
      console.log(message)
    }


    function onClickConnectBtn() {
      connect()
    }

    function onClickDisconnecedtBtn() {
      disconnect()
    }

    function onClickSendBtn() {
      sendName()
    }

    return {
      state,

      onClickConnectBtn,
      onClickDisconnecedtBtn,
      onClickSendBtn
    }
  }

}
</script>

<style>

</style>