<template>
    <div style="width:100%;height:100%">
        <div id="usernameDisplay">当前用户：{{username}}</div>
        <div id="changeUsername">
            修改名称
            <a type="button" @click="changeUsername">▶</a>
            <a id="a1" style="display:none">输入新名称：</a>
            <input type="text" required="required" placeholder="用户名" name="u" id="newUsername" style="display:none"/>
            <button id="changeUsernameApply" type="button" @click="changeUsernameApply" style="display:none">确定</button>
        </div>
        <div id="changePassword">
            修改密码
            <a type="button" @click="changePassword">▶</a>
            <a id="a2" style="display:none">输入新密码：</a>
            <input type="password" required="required" placeholder="密码" name="p" id="newPassword" style="display:none"/>
            <button id="changePasswordApply" type="button" @click="changePasswordApply" style="display:none">确定</button>
        </div>
        <div id="logout" >
            <button type="button" @click="logoutBtnClick">退出</button>
        </div>
    </div>
</template>

<script>
import chat from '@/components/subcomponents/chat'
const axios = require('axios');
axios.defaults.headers.post['Content-Type'] = 'application/json; charset=UTF-8';
export default {
    name: "management",
    data () {
        return {
            username: sessionStorage.getItem('user')
        }
    },
    methods: {
        logoutBtnClick(){
            var username = sessionStorage.getItem('user');
            axios.post('/user/logout?username=' + username).then(function (response) {
                sessionStorage.removeItem('user');
                chat.methods.closeChatWebSocket();
                this.$router.push({name: 'login'});
            }.bind(this)).catch(function (error) {
                alert('退出失败！');
            });
        },
        changeUsername(){
            document.getElementById('a1').style.display = 'inline';
            document.getElementById('newUsername').style.display = 'inline';
            document.getElementById('changeUsernameApply').style.display = 'inline';
            document.getElementById('newUsername').value = '';
        },
        changePassword(){
            document.getElementById('a2').style.display = 'inline';
            document.getElementById('newPassword').style.display = 'inline';
            document.getElementById('changePasswordApply').style.display = 'inline';
            document.getElementById('newPassword').value = '';
        },
        changeUsernameApply(){
            var data = JSON.stringify({
                'username': sessionStorage.getItem('user'),
                'newUsername': document.getElementById('newUsername').value
            });
            axios.post('/user/changeUsername', data).then(function (response) {
                if(response.data.message == 'change username success') {
                    document.getElementById('a1').style.display = 'none';
                    document.getElementById('newUsername').style.display = 'none';
                    document.getElementById('changeUsernameApply').style.display = 'none';
                    sessionStorage.setItem('user', document.getElementById('newUsername').value);
                    document.getElementById('usernameDisplay').innerText = '当前用户：' + document.getElementById('newUsername').value;
                    alert('修改名称成功！');
                }
                else {
                    alert('修改名称失败！');
                }
            }.bind(this)).catch(function (error) {
                alert('修改名称失败！');
            });
        },
        changePasswordApply(){
            var data = JSON.stringify({
                'username': sessionStorage.getItem('user'),
                'password': document.getElementById('newPassword').value
            });
            axios.post('/user/changePassword', data).then(function (response) {
                if(response.data.message == 'change password success') {
                    document.getElementById('a2').style.display = 'none';
                    document.getElementById('newPassword').style.display = 'none';
                    document.getElementById('changePasswordApply').style.display = 'none';
                    alert('修改密码成功！');
                }
                else {
                    alert('修改密码失败！');
                }
            }.bind(this)).catch(function (error) {
                alert('修改密码失败！');
            });
        }
    }
}
</script>

<style scoped>
#usernameDisplay {
    width: 35%;
    height: 15%;
    position: relative;
    left: 15%;
    top: 10%;
}
#changeUsername {
    width: 35%;
    height: 15%;
    position: relative;
    left: 15%;
    top: 12%;
}
#changePassword {
    width: 35%;
    height: 15%;
    position: relative;
    left: 15%;
    top: 14%;
}
#logout {
    width: 35%;
    height: 15%;
    position: relative;
    left: 15%;
    top: 16%;
}
</style>