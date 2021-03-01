<template>
    <div style="width:100%;height:100%">
        <div id="msg" class="chatdiv"></div>
        <div id="user" class="chatdiv"></div>
        <div id="input" class="chatdiv">
            <textarea style="width:100%;height:70%" id="textInput"/>
            <button type="button" @click="sendBtnClick" id="send">发送</button>
            <button type="button" @click="toggleBtnClick" id="toggleBtn">历史记录</button>
        </div>
    </div>
</template>

<script>
const axios = require('axios');
axios.defaults.headers.post['Content-Type'] = 'application/json; charset=UTF-8';
var socket;
function getUserStatus() {
    axios.get('/user/getAll').then(function(response) {
        var userdiv = document.getElementById('user');
        var userRecordList = response.data.data;
        userdiv.innerHTML = '';
        var jo,status;
        for(var i = 0; i < userRecordList.length; i++){
            jo = userRecordList[i];
            status = jo.status;
            if(status > 0) {
                userdiv.innerHTML += '<text style="color:#00ff00">● </text>';
            } else {
                userdiv.innerHTML += '● ';
            }
            userdiv.innerHTML += jo.username + '<br/>';
        }
    }.bind(this)).catch(function(error) {
        console.log(error);
    });
}
function createWebSocket() {
    if (typeof (WebSocket) == 'undefined') {
        console.log('很遗憾：您的浏览器不支持WebSocket');
    } else {
        socket = new WebSocket('ws://82.156.87.36:8080/ws/chat');
        socket.onopen = function() {
           console.log('Socket已打开');
           sessionStorage.setItem('chatws','ready');
        };
        socket.onmessage = function(msg) {
            if(msg.data == 'usc'){
                getUserStatus();
            } else {
                var data = msg.data.substring(5);
                var jo = JSON.parse(data);
                if(jo.user == sessionStorage.getItem('user')){
                    axios.post('/chat/add', data).then(function (response) {
                        if(response.data.message == 'send chat message failed') {
                            alert('发送失败！');
                        } else {
                            console.log(data);
                            var msgdiv = document.getElementById('msg');
                            var time = new Date(jo.time);
                            var timeFormat = time.getFullYear() + '-' + (time.getMonth() + 1) + '-' + time.getDate() + ' ' + time.getHours() + ':' + time.getMinutes() + ":" + time.getSeconds();
                            msgdiv.innerHTML += jo.user + ' ' + timeFormat + '<br/>' + jo.content + '<br/><br/>';
                        }
                    }.bind(this)).catch(function (error) {
                        alert('发送失败！');
                    });
                }
            }
        };
        socket.onclose = function() {
            console.log('Socket已关闭');
        };
        socket.onerror = function() {
            alert('Socket发生了错误');
        };
    }
}
sessionStorage.setItem('chatStatus', '0');
export default {
    name: 'chat',
    methods: {
        sendBtnClick(){
            var data = JSON.stringify({'user':sessionStorage.getItem('user'),'content':document.getElementById('textInput').value});
            socket.send(data);
            document.getElementById('textInput').value = '';
        },
        toggleBtnClick(){
            switch(sessionStorage.getItem('chatStatus')){
                case '0':
                    axios.get('/chat/getAll').then(function (response) {
                        var chatRecordList = response.data.data;
                        var msgdiv = document.getElementById('msg');
                        msgdiv.innerHTML = '';
                        var jo,time,timeFormat;
                        for(var i = 0; i < chatRecordList.length; i++){
                            jo = chatRecordList[i];
                            time = new Date(jo.time);
                            timeFormat = time.getFullYear() + '-' + (time.getMonth() + 1) + '-' + time.getDate() + ' ' + time.getHours() + ':' + time.getMinutes() + ":" + time.getSeconds();
                            msgdiv.innerHTML += jo.user + ' ' + timeFormat + '<br/>' + jo.content + '<br/><br/>';
                        }
                        msgdiv.scrollTop = msgdiv.scrollHeight;
                        sessionStorage.setItem('chatStatus', '1');
                        document.getElementById('toggleBtn').innerText = '返回';
                        document.getElementById('send').disabled = true;
                    }.bind(this)).catch(function (error) {
                        alert('查询历史记录失败！');
                    });
                    break;
                case '1':
                    sessionStorage.setItem('chatStatus', '0');
                    document.getElementById('toggleBtn').innerText = '历史记录';
                    document.getElementById('msg').innerHTML = '';
                    document.getElementById('send').disabled = false;
                    break;
            }
        },
        getUserStatus(){
            getUserStatus();
        },
        createChatWebSocket(){
            createWebSocket();
        },
        closeChatWebSocket(){
            socket.close();
        }
    }
}
</script>

<style scoped>
.chatdiv{
    border:1px solid #000;
}
#msg{
    width:80%;
    height:80%;
    overflow:auto;
}
#user{
    width:20%;
    height:80%;
    position:absolute;
    left:80%;
    top:0%;
    overflow:auto;
}
#input{
    width:100%;
    height:20%;
    position:absolute;
    left:0%;
    top:80%;
}
</style>