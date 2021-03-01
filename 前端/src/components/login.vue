<template>
    <div id="login">
        <div id="login1">
            <h1>Login</h1>
            <input type="text" required="required" placeholder="用户名" name="u" id="username"/>
            <input type="password" required="required" placeholder="密码" name="p" id="password"/>
            <button type="button" class="but" @click="loginBtnClick">登录</button>
        </div>
    </div>
</template>

<script>
//import chat from '@/components/subcomponents/chat'
const axios = require('axios');
axios.defaults.headers.post['Content-Type'] = 'application/json; charset=UTF-8';
export default {
    name: 'login',
    methods: {
        loginBtnClick(){
            var data = JSON.stringify({
                'username': document.getElementById('username').value,
                'password': document.getElementById('password').value
            });
            if(sessionStorage.getItem('user') != null){
                axios.post('/user/logout?username=' + sessionStorage.getItem('user'));
                sessionStorage.removeItem('user');
            }
            axios.post('/user/login',data).then(function (response) {
                if(response.data) {
                    sessionStorage.setItem('user', document.getElementById('username').value);
                    sessionStorage.setItem('chatws', 'notready');
                    this.$router.push({name: 'mainPage'});
                    //chat.methods.createWebSocket();
                }
                else {
                    alert('用户名或密码错误！');
                }
            }.bind(this)).catch(function (error) {
                alert('登陆失败！');
            });
        }
    }
}
</script>

<style scoped>
#login {
    width: 100%;
    height: 100%;
    position: fixed;
    margin: 2px;
    background-color: #4A374A;
}
#login1 {
    position: absolute;
    top: 50%;
    left: 50%;
    margin: -150px 0 0 -150px;
    width: 300px;
    height: 300px;
    font-style: sans-serif;
    font-family: 'Open Sans',sans-serif;
    background-color: #4A374A;
}
#login1 h1{
    color: #fff;
    letter-spacing: 1px;
    text-align: center;
}
h1{
    font-size: 2em;
    margin: 0.67em 0;
}
input{
    width: 278px;
    height: 18px;
    margin-bottom: 10px;
    outline: none;
    padding: 10px;
    font-size: 13px;
    color: #fff;
    border-top: 1px solid #312E3D;
    border-left: 1px solid #312E3D;
    border-right: 1px solid #312E3D;
    border-bottom: 1px solid #56536A;
    border-radius: 4px;
    background-color: #2D2D3F;
}
.but{
    width: 300px;
    min-height: 20px;
    display: block;
    background-color: #4a77d4;
    border: 1px solid #3762bc;
    color: #fff;
    padding: 9px 14px;
    font-size: 15px;
    line-height: normal;
    border-radius: 5px;
    margin: 0;
}
</style>