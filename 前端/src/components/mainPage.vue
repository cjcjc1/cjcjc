<template>
    <div id="main">
        <div id="left">
            <div class="title"><div class="menutext">MyWebsite</div></div>
            <div class="menu" type="button" @click="switchPlate('management')"><div class="menutext">用户管理</div></div>
            <div class="menu" type="button" @click="switchPlate('chat')"><div class="menutext">聊天</div></div>
            <div class="menu" type="button" @click="switchPlate('sokoban')"><div class="menutext">推箱子</div></div>
            <div class="menu" type="button" @click="switchPlate('swl')"><div class="menutext">斗地主</div></div>
            <div class="menu" type="button" @click="switchPlate('simulationbp')"><div class="menutext">模拟pb</div></div>
        </div>
        <div id="right">
            <component :is="currentPlate"></component>
        </div>
    </div>
</template>

<script>
import management from '@/components/subcomponents/management'
import chat from '@/components/subcomponents/chat'
import sokoban from '@/components/subcomponents/sokoban'
import swl from '@/components/subcomponents/swl'
import simulationbp from '@/components/subcomponents/simulationbp'
const axios = require('axios');
window.onbeforeunload = function() {
    chat.methods.closeChatWebSocket();
    sessionStorage.removeItem('chatws');
};
export default {
    name: 'mainPage',
    components: {
        management,
        chat,
        sokoban,
        swl,
        simulationbp
    },
    data() {
        return {
            currentPlate: 'management'
        }
    },
    methods: {
        switchPlate(name){
            this.currentPlate = name;
            if(name == 'chat') {
                if(sessionStorage.getItem('chatws') == null || sessionStorage.getItem('chatws') == 'notready'){
                    chat.methods.createChatWebSocket();
                }
                chat.methods.getUserStatus();
            }
        }
    }
}
</script>

<style scoped>
#main {
    width: 100%;
    height: 100%;
    position: fixed;
    margin: 0;
}
#left {
    width: 10%;
    height: 100%;
    position: absolute;
    border: 1px solid #3B6273;
    background-color: #4d3030;
    margin: 0;
}
#right {
    width: 90%;
    height: 100%;
    position: absolute;
    left: 10%;
    border: 1px solid #3B6273;
    background-color: #ffffff;
    margin: 0;
}
.menu {
    width: 100%;
    height: 8%;
    color: #b5b5b5;
}
.menu:hover {
    color: #fff;
    background: #007aff;
}
.title {
    width: 100%;
    height: 8%;
    color: #fff;
}
.menutext {
    width: 85%;
    height: 100%;
    left: 15%;
    position: relative;
    font-family: Avenir,Helvetica,Arial,sans-serif;
    cursor: pointer;
    align-items: center;
    display: flex;
}
</style>