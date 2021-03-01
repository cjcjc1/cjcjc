<template>
    <div style="width:100%;height:100%">
        <button type="button" id="startBtn" style="width:10%;height:10%;left:45%;top:45%;position:relative" @click="startBtnClick()">开始游戏</button>
        <div id="nextPlayerDiv" class="swldiv">
            <div id="npInfo" style="width:15%;height:100%;left:0%;top:0%;position:absolute">👤等待玩家加入</div>
            <div id="npHandArea" style="width:75%;height:50%;left:15%;top:0%;position:absolute"></div>
            <div id="npDisplayArea" style="width:75%;height:50%;left:15%;top:50%;position:absolute"></div>
        </div>
        <div id="prevPlayerDiv" class="swldiv" style="left:50%">
            <div id="ppInfo" style="width:15%;height:100%;right:0%;top:0%;position:absolute">👤等待玩家加入</div>
            <div id="ppHandArea" style="width:75%;height:50%;right:15%;top:0%;position:absolute"></div>
            <div id="ppDisplayArea" style="width:75%;height:50%;right:15%;top:50%;position:absolute"></div>
        </div>
        <div id="myDiv" class="swldiv" style="width:100%;top:50%">
            <div id="btn1" style="width:13%;height:33%;left:0%;top:0%;position:absolute">
                <div class="subbtndiv">
                    <button type="button" class="swlBtn" style="display:none" @click="lordOrApplyBtnClick($event)" id="loaBtn1">叫地主</button>
                </div>
                <div class="subbtndiv">
                    <button type="button" class="swlBtn" style="display:none" @click="lordOrApplyBtnClick($event)" id="loaBtn2">不叫</button>
                </div>
            </div>
            <div id="displayArea" style="width:87%;height:33%;left:13%;top:0%;position:absolute">
                <div id="msgArea" style="width:85%;text-align:center"></div>
            </div>
            <div id="myInfo" style="width:13%;height:33%;left:0%;top:33%;position:absolute"></div>
            <div id="preparationArea" style="width:87%;height:33%;left:13%;top:33%;position:absolute"></div>
            <div id="btn2" style="width:13%;height:33%;left:0%;top:66%;position:absolute">
                <div class="subbtndiv">
                    <button type="button" class="swlBtn" @click="prepareBtnClick" id="prepareBtn">准备</button>
                </div>
                <div class="subbtndiv">
                    <button type="button" class="swlBtn" @click="exitBtnClick" id="exitBtn">退出</button>
                </div>
            </div>
            <div id="handArea" style="width:87%;height:33%;left:13%;top:66%;position:absolute"></div>
        </div>
    </div>
</template>

<script>
var socket;
var status = 0;
var myPosition = 0;
var np,pp;
var npLeftCardNum = 17,ppLeftCardNum = 17;
var myCard = [],lordCard = [],preparationCard = [],apCard = [];
var lordPosition,activePlayerPosition;
var prevCardMain = [0,0];
var prevCardNum = 0;
var card = ['s3','h3','c3','d3','s4','h4','c4','d4','s5','h5','c5','d5','s6','h6','c6','d6','s7','h7','c7','d7','s8','h8','c8','d8','s9','h9','c9','d9','s10','h10','c10','d10','s11','h11','c11','d11','s12','h12','c12','d12','s13','h13','c13','d13','s1','h1','c1','d1','s2','h2','c2','d2','j1','j2'];
function refreshCard(card, div){
    div.innerHTML = '';
    var cardNum = card.length;
    for(var i = 0; i < cardNum; i++){
        var cardDiv = document.createElement('div');
        cardDiv.style = 'width:36px;height:60px;border:1px solid #000;margin:1px;display:inline-block;';
        if(card[i] == 'j1'){
            cardDiv.innerHTML = 'joker';
        } else if(card[i] == 'j2'){
            cardDiv.innerHTML = '<text style="color:#ff0000">joker</text>';
        } else{
            var c = card[i].charAt(0);
            var n = card[i].substring(1);
            var c1,n1;
            switch(n){
                case '1':
                    n1 = 'A';
                    break;
                case '11':
                    n1 = 'J';
                    break;
                case '12':
                    n1 = 'Q';
                    break;
                case '13':
                    n1 = 'K';
                    break;
                default:
                    n1 = n;
                    break;
            }
            switch(c){
                case 'h':
                    c1 = '♥';
                    cardDiv.innerHTML = '<text style="color:#ff0000">' + c1 + n1 + '</text>';
                    break;
                case 's':
                    c1 = '♠';
                    cardDiv.innerHTML = c1 + n1;
                    break;
                case 'd':
                    c1 = '♦';
                    cardDiv.innerHTML = '<text style="color:#ff0000">' + c1 + n1 + '</text>';
                    break;
                case 'c':
                    c1 = '♣';
                    cardDiv.innerHTML = c1 + n1;
                    break;
                default:
                    cardDiv.innerHTML = '<text style="color:#0000ff">??</text>';
                    break;
            }
        }
        div.append(cardDiv);
    }
}
function coveredCard(n){
    var cc = [];
    for(var i = 0; i < n; i++){
        cc[i] = 'uc';
    }
    return cc;
}
function getLordCard(){
    for(var i = 0; i < 3; i++){
        addCard(myCard,lordCard[i]);
    }
}
function addCard(cardArray, c){
    if(card.indexOf(cardArray[cardArray.length-1]) < card.indexOf(c)){
        cardArray.splice(cardArray.length,0,c);
    } else {
        for(var j = 0; j < cardArray.length; j++){
            if(card.indexOf(cardArray[j]) > card.indexOf(c)){
                cardArray.splice(j,0,c);
                break;
            }
        }
    }
}
function removeCard(cardArray, c){
    var i = cardArray.indexOf(c);
    cardArray.splice(i,1);
}
function innerHTMLToCard(t){
    var n;
    if(t == 'joker'){
        return 'j1';
    }
    if(t == '<text style="color:#ff0000">joker</text>'){
        return 'j2';
    }
    if(t.charAt(0) == '♠' || t.charAt(0) == '♣'){
        switch(t.substring(1)){
            case 'A':
                n = '1';
                break;
            case 'J':
                n = '11';
                break;
            case 'Q':
                n = '12';
                break;
            case 'K':
                n = '13';
                break;
            default:
                n = t.substring(1);
                break;
        }
        if(t.charAt(0) == '♠'){
            return 's'+n;
        }
        if(t.charAt(0) == '♣'){
            return 'c'+n;
        }
    }
    if(t.charAt(28) == '♥' || t.charAt(28) == '♦'){
        switch(t.substring(29,30)){
            case 'A':
                n = '1';
                break;
            case 'J':
                n = '11';
                break;
            case 'Q':
                n = '12';
                break;
            case 'K':
                n = '13';
                break;
            case '1':
                if(t.charAt(30) == '0'){
                    n = '10';
                } else {
                    n = '1';
                }
                break;
            default:
                n = t.substring(29,30);
                break;
        }
        if(t.charAt(28) == '♥'){
            return 'h'+n;
        }
        if(t.charAt(28) == '♦'){
            return 'd'+n;
        }
    }
}
function cardToNum(c){
    if(c == 'j1'){
        return 16;
    }
    if(c == 'j2'){
        return 17;
    }
    var n = parseInt(c.substring(1));
    if(n == 1){
        return 14;
    }
    if(n == 2){
        return 15;
    } else {
        return n;
    }
}
function cardArrToNumArr(c){
    var na = [];
    for(var i = 0; i < c.length; i++){
        na[i] = cardToNum(c[i]);
    }
    return na;
}
function handAreaOnclick(){
    var ha = document.getElementById('handArea');
    var hc = ha.childNodes;
    for (var i = 0; i < hc.length; i++){
		hc[i].onclick = function(){
            var c = innerHTMLToCard(this.innerHTML);
            addCard(preparationCard, c);
            removeCard(myCard,c);
            refreshCard(preparationCard,document.getElementById('preparationArea'));
            refreshCard(myCard,ha);
            handAreaOnclick();
            preparationAreaOnclick();
        };
	};
}
function preparationAreaOnclick(){
    var pa = document.getElementById('preparationArea');
    var pc = pa.childNodes;
    for (var i = 0; i < pc.length; i++){
		pc[i].onclick = function(){
            var c = innerHTMLToCard(this.innerHTML);
            removeCard(preparationCard, c);
            addCard(myCard,c);
            refreshCard(preparationCard,pa);
            refreshCard(myCard,document.getElementById('handArea'));
            handAreaOnclick();
            preparationAreaOnclick();
        };
	};
}
function getMain(c){
    switch(c.length){
        case 1:
            return [c[0],1];
        case 2:
            if((c[0] == c[1])){
                return [c[0],2];
            } else if (c[0] == 16){
                return [16,6];
            } else{
                return [0,0];
            }
        case 3:
            if(c[0] == c[2]){
                return [c[0],3];
            } else{
                return [0,0];
            }
        case 4:
            if(c[0] == c[3]){
                return [c[0],4];
            } else if((c[1] == c[2]) && ((c[0] == c[1]) || (c[3] == c[1]))){
                return [c[1],3];
            } else{
                return [0,0];
            }
        case 5:
            if((c[4] < 15) && (c[1] - c[0] == 1) && (c[2] - c[1] == 1) && (c[3] - c[2] == 1) && (c[4] - c[3] == 1)){
                return [c[0],5];
            } else if(((c[0] == c[1]) && (c[3] == c[4])) && ((c[2] == c[0]) || (c[2] == c[3]))){
                return [c[2],3];
            } else{
                return [0,0];
            }
        case 6:
            if((c[5] < 15) && (c[1] - c[0] == 1) && (c[2] - c[1] == 1) && (c[3] - c[2] == 1) && (c[4] - c[3] == 1) && (c[5] - c[4] == 1)){
                return [c[0],5];
            } else if((c[2] == c[3]) && (((c[0] == c[2]) && (c[1] == c[2])) || ((c[1] == c[2]) && (c[4] == c[2])) || ((c[4] == c[2]) && (c[5] == c[2])))){
                return [c[2],4];
            } else if((c[5] < 15) && (c[0] == c[1]) && (c[0] == c[2]) && (c[0] + 1 == c[3]) && (c[3] == c[4]) && (c[3] == c[4])){
                return [c[0],3];
            } else if((c[5] < 15) && (c[0] == c[1]) && (c[0] + 1 == c[2]) && (c[2] == c[3]) && (c[0] + 2 == c[4]) && (c[4] == c[5])){
                return [c[0],2];
            } else{
                return [0,0];
            }
        case 7:
            if((c[6] < 15) && (c[1] - c[0] == 1) && (c[2] - c[1] == 1) && (c[3] - c[2] == 1) && (c[4] - c[3] == 1) && (c[5] - c[4] == 1) && (c[6] - c[5] == 1)){
                return [c[0],5];
            } else{
                return [0,0];
            }
        case 8:
            if((c[7] < 15) && (c[1] - c[0] == 1) && (c[2] - c[1] == 1) && (c[3] - c[2] == 1) && (c[4] - c[3] == 1) && (c[5] - c[4] == 1) && (c[6] - c[5] == 1) && (c[7] - c[6] == 1)){
                return [c[0],5];
            } else if((c[7] < 15) && (c[1] == c[0]) && (c[2] - c[1] == 1) && (c[3] == c[2]) && (c[4] - c[3] == 1) && (c[5] == c[4]) && (c[6] - c[5] == 1) && (c[7] == c[6])){
                return [c[0],2];
            } else if((c[7] < 15) && (c[7] - c[0] == 1)){
                return [c[0],3.5];
            } else if((c[5] < 15) && (((c[1] == c[3]) && (c[4] - c[3] == 1) && (c[4] == c[6])) || ((c[0] == c[2]) && (c[3] - c[2] == 1) && (c[3] == c[5])) || ((c[2] == c[4]) && (c[5] - c[4] == 1) && (c[5] == c[7])))){
                return [c[2],3];
            } else if((c[0] == c[3]) && (c[4] == c[5]) && (c[6] == c[7])){
                return [c[0],4];
            } else if((c[0] == c[1]) && (c[2] == c[3]) && (c[4] == c[7])){
                return [c[4],4];
            } else if((c[0] == c[1]) && (c[2] == c[5]) && (c[6] == c[7])){
                return [c[2],4];
            } else{
                return [0,0];
            }
        case 9:
            if((c[8] < 15) && (c[1] - c[0] == 1) && (c[2] - c[1] == 1) && (c[3] - c[2] == 1) && (c[4] - c[3] == 1) && (c[5] - c[4] == 1) && (c[6] - c[5] == 1) && (c[7] - c[6] == 1) && (c[8] - c[7] == 1)){
                return [c[0],5];
            } else if((c[8] < 15) && (c[0] == c[2]) && (c[3] - c[2] == 1) && (c[3] == c[5]) && (c[6] - c[5] == 1) && (c[6] == c[8])){
                return [c[0],3];
            } else{
                return [0,0];
            }
        case 10:
            if((c[9] < 15) && (c[1] - c[0] == 1) && (c[2] - c[1] == 1) && (c[3] - c[2] == 1) && (c[4] - c[3] == 1) && (c[5] - c[4] == 1) && (c[6] - c[5] == 1) && (c[7] - c[6] == 1) && (c[8] - c[7] == 1) && (c[9] - c[8] == 1)){
                return [c[0],5];
            } else if((c[9] < 15) && (c[1] == c[0]) && (c[2] - c[1] == 1) && (c[3] == c[2]) && (c[4] - c[3] == 1) && (c[5] == c[4]) && (c[6] - c[5] == 1) && (c[7] == c[6]) && (c[8] - c[7] == 1) && (c[9] == c[8])){
                return [c[0],2];
            } else if((c[5] < 15) && (c[0] == c[2]) && (c[3] == c[5]) && (c[3] - c[2] == 1) && (c[6] == c[7]) && (c[8] == c[9])){
                return [c[0],3];
            } else if((c[5] < 15) && (c[0] == c[1]) && (c[2] == c[4]) && (c[5] == c[7]) && (c[5] - c[4] == 1) && (c[8] == c[9])){
                return [c[2],3];
            } else if((c[9] < 15) && (c[0] == c[1]) && (c[2] == c[3]) && (c[4] == c[6]) && (c[7] == c[9]) && (c[7] - c[6] == 1)){
                return [c[4],3];
            } else{
                return [0,0];
            }
        case 11:
            if((c[10] < 15) && (c[1] - c[0] == 1) && (c[2] - c[1] == 1) && (c[3] - c[2] == 1) && (c[4] - c[3] == 1) && (c[5] - c[4] == 1) && (c[6] - c[5] == 1) && (c[7] - c[6] == 1) && (c[8] - c[7] == 1) && (c[9] - c[8] == 1) && (c[10] - c[9] == 1)){
                return [c[0],5];
            } else{
                return [0,0];
            }
        case 12:
            if((c[0] == 3) && (c[1] == 4) && (c[2] == 5) && (c[3] == 6) && (c[4] == 7) && (c[5] == 8) && (c[6] == 9) && (c[7] == 10) && (c[8] == 11) && (c[9] == 12) && (c[10] == 13) && (c[11] == 14)){
                return [c[0],5];
            } else if((c[11] < 15) && (c[1] == c[0]) && (c[2] - c[1] == 1) && (c[3] == c[2]) && (c[4] - c[3] == 1) && (c[5] == c[4]) && (c[6] - c[5] == 1) && (c[7] == c[6]) && (c[8] - c[7] == 1) && (c[9] == c[8]) && (c[10] - c[9] == 1) && (c[11] == c[10])){
                return [c[0],2];
            } else if((c[11] < 15) && (c[0] == c[2]) && (c[3] - c[2] == 1) && (c[3] == c[5]) && (c[6] - c[5] == 1) && (c[6] == c[8]) && (c[9] - c[8] == 1) && (c[9] == c[11])){
                return [c[0],3];
            } else {
                for(var i = 0; i < 4; i++){
                    if((c[i+4] - c[i] == 1) && (c[i+8] - c[i] == 2) && (c[i+8] < 15)){
                        if(((c[i] == c[i+2]) && (c[i+3] == c[i+5]) && (c[i+6] == c[i+8])) || ((c[i] == c[i+3]) && (c[i+4] == c[i+6]) && (c[i+7] == c[i+9])) || ((c[i] == c[i+2]) && (c[i+3] == c[i+6]) && (c[i+7] == c[i+9])) || ((c[i] == c[i+3]) && (c[i+4] == c[i+7]) && (c[i+8] == c[i+10]))){
                            return [c[i],31];
                        }
                    }
                }
                return [0,0];
            }
        case 14:
            if((c[13] < 15) && (c[1] == c[0]) && (c[2] - c[1] == 1) && (c[3] == c[2]) && (c[4] - c[3] == 1) && (c[5] == c[4]) && (c[6] - c[5] == 1) && (c[7] == c[6]) && (c[8] - c[7] == 1) && (c[9] == c[8]) && (c[10] - c[9] == 1) && (c[11] == c[10]) && (c[12] - c[11] == 1) && (c[13] == c[12])){
                return [c[0],2];
            } else{
                return [0,0];
            }
        case 15:
            if((c[14] < 15) && (c[0] == c[2]) && (c[3] - c[2] == 1) && (c[3] == c[5]) && (c[6] - c[5] == 1) && (c[6] == c[8]) && (c[9] - c[8] == 1) && (c[9] == c[11]) && (c[12] - c[11] == 1) && (c[12] == c[14])){
                return [c[0],3];
            } else if((c[14] < 15) && (c[6] == c[8]) && (c[9] - c[8] == 1) && (c[9] == c[11]) && (c[12] - c[11] == 1) && (c[12] == c[14]) && (c[0] == c[1]) && (c[2] == c[3]) && (c[4] == c[5])){
                return [c[6],32];
            } else if((c[12] < 15) && (c[4] == c[6]) && (c[7] - c[6] == 1) && (c[7] == c[9]) && (c[10] - c[9] == 1) && (c[10] == c[12]) && (c[0] == c[1]) && (c[2] == c[3]) && (c[13] == c[14])){
                return [c[4],32];
            } else if((c[10] < 15) && (c[2] == c[4]) && (c[5] - c[4] == 1) && (c[5] == c[7]) && (c[8] - c[7] == 1) && (c[8] == c[10]) && (c[0] == c[1]) && (c[11] == c[12]) && (c[13] == c[14])){
                return [c[2],32];
            } else if((c[8] < 14) && (c[0] == c[2]) && (c[3] - c[2] == 1) && (c[3] == c[5]) && (c[6] - c[5] == 1) && (c[6] == c[8]) && (c[9] == c[10]) && (c[11] == c[12]) && (c[13] == c[14])){
                return [c[0],32];
            } else{
                return [0,0];
            }
        case 16:
            if((c[15] < 15) && (c[1] == c[0]) && (c[2] - c[1] == 1) && (c[3] == c[2]) && (c[4] - c[3] == 1) && (c[5] == c[4]) && (c[6] - c[5] == 1) && (c[7] == c[6]) && (c[8] - c[7] == 1) && (c[9] == c[8]) && (c[10] - c[9] == 1) && (c[11] == c[10]) && (c[12] - c[11] == 1) && (c[13] == c[12]) && (c[14] - c[13] == 1) && (c[15] == c[14])){
                return [c[0],2];
            } else{
                for(var i = 0; i < 5; i++){
                    if((c[i] == c[i+2]) && (c[i+3] - c[i+2] == 1) && (c[i+3] == c[i+5]) && (c[i+7] - c[i+5] == 1) && (c[i+7] == c[i+8]) && (c[i+11] - c[i+8] == 1) && (c[i+11] < 15)){
                        if(((c[i+6] - c[i] == 2) && (c[i+9] - c[i] == 3)) || ((c[i+9] - c[i] == 2) && (c[i+10] - c[i] == 3) && (c[i+12] - c[i] == 3)) || ((c[i+10] - c[i] == 2) && (c[i+13] - c[i] == 3))){
                            return [c[i],31];
                        }
                    }
                }
                return [0,0];
            }
        case 18:
            if((c[17] < 15) && (c[1] == c[0]) && (c[2] - c[1] == 1) && (c[3] == c[2]) && (c[4] - c[3] == 1) && (c[5] == c[4]) && (c[6] - c[5] == 1) && (c[7] == c[6]) && (c[8] - c[7] == 1) && (c[9] == c[8]) && (c[10] - c[9] == 1) && (c[11] == c[10]) && (c[12] - c[11] == 1) && (c[13] == c[12]) && (c[14] - c[13] == 1) && (c[15] == c[14]) && (c[16] - c[15] == 1) && (c[17] == c[16])){
                return [c[0],2];
            } else if((c[17] < 15) && (c[0] == c[2]) && (c[3] - c[2] == 1) && (c[3] == c[5]) && (c[6] - c[5] == 1) && (c[6] == c[8]) && (c[9] - c[8] == 1) && (c[9] == c[11]) && (c[12] - c[11] == 1) && (c[12] == c[14]) && (c[15] - c[14] == 1) && (c[15] == c[17])){
                return [c[0],3];
            } else{
                return [0,0];
            }
        case 20:
            if((c[19] < 15) && (c[1] == c[0]) && (c[2] - c[1] == 1) && (c[3] == c[2]) && (c[4] - c[3] == 1) && (c[5] == c[4]) && (c[6] - c[5] == 1) && (c[7] == c[6]) && (c[8] - c[7] == 1) && (c[9] == c[8]) && (c[10] - c[9] == 1) && (c[11] == c[10]) && (c[12] - c[11] == 1) && (c[13] == c[12]) && (c[14] - c[13] == 1) && (c[15] == c[14]) && (c[16] - c[15] == 1) && (c[17] == c[16]) && (c[18] - c[17] == 1) && (c[19] == c[18])){
                return [c[0],2];
            } else if((c[19] < 15) && (c[8] == c[10]) && (c[11] - c[10] == 1) && (c[11] == c[13]) && (c[14] - c[13] == 1) && (c[14] == c[16]) && (c[17] - c[16] == 1) && (c[17] == c[19]) && (c[0] == c[1]) && (c[2] == c[3]) && (c[4] == c[5]) && (c[6] == c[7])){
                return [c[8],32];
            } else if((c[17] < 15) && (c[6] == c[8]) && (c[9] - c[8] == 1) && (c[9] == c[11]) && (c[12] - c[11] == 1) && (c[12] == c[14]) && (c[15] - c[14] == 1) && (c[15] == c[17]) && (c[0] == c[1]) && (c[2] == c[3]) && (c[4] == c[5]) && (c[18] == c[19])){
                return [c[6],32];
            } else if((c[15] < 15) && (c[4] == c[6]) && (c[7] - c[6] == 1) && (c[7] == c[9]) && (c[10] - c[9] == 1) && (c[10] == c[12]) && (c[13] - c[12] == 1) && (c[13] == c[15]) && (c[0] == c[1]) && (c[2] == c[3]) && (c[16] == c[17]) && (c[18] == c[19])){
                return [c[4],32];
            } else if((c[13] < 14) && (c[2] == c[4]) && (c[5] - c[4] == 1) && (c[5] == c[7]) && (c[8] - c[7] == 1) && (c[8] == c[10]) && (c[11] - c[10] == 1) && (c[11] == c[13]) && (c[0] == c[1]) && (c[15] == c[14]) && (c[16] == c[17]) && (c[18] == c[19])){
                return [c[2],32];
            } else if((c[11] < 14) && (c[0] == c[2]) && (c[3] - c[2] == 1) && (c[3] == c[5]) && (c[6] - c[5] == 1) && (c[6] == c[8]) && (c[9] - c[8] == 1) && (c[9] == c[11]) && (c[13] == c[12]) && (c[15] == c[14]) && (c[16] == c[17]) && (c[18] == c[19])){
                return [c[0],32];
            } else{
                for(var i = 0; i < 6; i++){
                    if((c[i] == c[i+2]) && (c[i+3] - c[i] == 1) && (c[i+5] == c[i+3])){
                        if(((c[i+6] == c[i+8]) && (c[i+9] == c[i+11]) && (c[i+12] == c[i+14]) && (c[i+12] - c[i] == 4)) || ((c[i+7] == c[i+10]) && (c[i+11] == c[i+14]) && (c[i+15] == c[i+17]) && (c[i+15] - c[i] == 4))){
                            return [c[i],31];
                        } else if((c[i+13] == c[i+15]) && (c[i+13] - c[i] == 4)){
                            if((c[i+10] == c[i+12]) && ((c[i+6] - c[i] == 2) || (c[i+9] - c[i] == 2))){
                                return [c[i],31];
                            }
                        } else if((c[i+14] == c[i+16]) && (c[i+14] - c[i] == 4) && (c[i+11] == c[i+13])){
                            return [c[i],31];
                        }
                    }
                }
                return [0,0];
            }
        default:
            return [0,0];
    }
}
function init(){
    status = 0;
    myPosition = 0;
    np='';
    pp='';
    npLeftCardNum = 17;
    ppLeftCardNum = 17;
    myCard = [];
    lordCard = [];
    preparationCard = [];
    apCard = [];
    lordPosition = -1;
    activePlayerPosition = -1;
    prevCardMain = [0,0];
    prevCardNum = 0;
    document.getElementById('startBtn').style.display = 'block';
    document.getElementById('nextPlayerDiv').style.display = 'none';
    document.getElementById('prevPlayerDiv').style.display = 'none';
    document.getElementById('myDiv').style.display = 'none';
    document.getElementById('displayArea').onclick = function(){};
    document.getElementById('npInfo').innerHTML = '👤等待玩家加入';
    document.getElementById('ppInfo').innerHTML = '👤等待玩家加入';
    document.getElementById('npHandArea').innerHTML = '';
    document.getElementById('ppHandArea').innerHTML = '';
    document.getElementById('npDisplayArea').innerHTML = '';
    document.getElementById('ppDisplayArea').innerHTML = '';
    document.getElementById('loaBtn1').innerHTML = '叫地主';
    document.getElementById('loaBtn2').innerHTML = '不叫';
    document.getElementById('loaBtn1').style.display = 'none';
    document.getElementById('loaBtn2').style.display = 'none';
    document.getElementById('displayArea').innerHTML = '<div id="msgArea" style="width:85%;text-align:center"></div>';
    document.getElementById('myInfo').innerHTML = '';
    document.getElementById('preparationArea').innerHTML = '';
    document.getElementById('prepareBtn').innerHTML = '准备';
    document.getElementById('exitBtn').innerHTML = '退出';
    document.getElementById('prepareBtn').style.display = 'block';
    document.getElementById('exitBtn').style.display = 'block';
    document.getElementById('exitBtn').disabled = false;
    document.getElementById('handArea').innerHTML = '';
}
export default {
    name: 'swl',
    methods: {
        startBtnClick(){
            if (typeof (WebSocket) == 'undefined') {
                console.log('很遗憾：您的浏览器不支持WebSocket');
            } else {
                socket = new WebSocket('ws://82.156.87.36:8080/ws/swl');
                socket.onopen = function() {
                    console.log('SwlSocket已打开');
                };
                socket.onmessage = function(msg) {
                    var data = JSON.parse(msg.data);
                    switch(data.code){
                        case 'startSuccess':
                            socket.send(JSON.stringify({'phase':1,'player':sessionStorage.getItem('user')}));
                            document.getElementById('startBtn').style.display = 'none';
                            document.getElementById('nextPlayerDiv').style.display = 'block';
                            document.getElementById('prevPlayerDiv').style.display = 'block';
                            document.getElementById('myDiv').style.display = 'block';
                            document.getElementById('myInfo').innerHTML = '👤' + sessionStorage.getItem('user') + '<br/>' + '等待...';
                            break;
                        case 'startFailed':
                            alert('人数已满，请稍等...');
                            socket.close();
                            break;
                        case 'receiveMyInfo':
                            if(status == 1){
                                status = 0;
                                document.getElementById('exitBtn').disabled = false;
                                document.getElementById('prepareBtn').innerHTML = '准备';
                                document.getElementById('myInfo').innerHTML = '👤' + sessionStorage.getItem('user') + '<br/>' + '等待...';
                            } else if(status == 0){
                                status = 1;
                                document.getElementById('exitBtn').disabled = true;
                                document.getElementById('prepareBtn').innerHTML = '取消';
                                document.getElementById('myInfo').innerHTML = '👤' + sessionStorage.getItem('user') + '<br/>' + '已准备';
                            }
                            break;
                        case 'playerInfo':
                            np = data.nextPlayer;
                            pp = data.prevPlayer;
                            var nps = data.nextPlayerStatus;
                            var pps = data.prevPlayerStatus;
                            var statusList = ['等待...','已准备'];
                            document.getElementById('npInfo').innerHTML = '👤' + np + '<br/>' + statusList[nps];
                            document.getElementById('ppInfo').innerHTML = '👤' + pp + '<br/>' + statusList[pps];
                            break;
                        case 'gameStart':
                            document.getElementById('msgArea').innerHTML = '......';
                            document.getElementById('prepareBtn').style.display = 'none';
                            document.getElementById('exitBtn').style.display = 'none';
                            document.getElementById('npInfo').innerHTML = '👤' + np;
                            document.getElementById('ppInfo').innerHTML = '👤' + pp;
                            myPosition = data.yourPosition;
                            refreshCard(coveredCard(17), document.getElementById('npHandArea'));
                            refreshCard(coveredCard(17), document.getElementById('ppHandArea'));
                            switch(myPosition){
                                case 1:
                                    myCard = data.p1;
                                    refreshCard(data.p1, document.getElementById('handArea'));
                                    break;
                                case 2:
                                    myCard = data.p2;
                                    refreshCard(data.p2, document.getElementById('handArea'));
                                    break;
                                case 0:
                                    myCard = data.p3;
                                    refreshCard(data.p3, document.getElementById('handArea'));
                                    break;
                            }
                            lordCard = data.lc;
                            refreshCard(coveredCard(3), document.getElementById('displayArea'));
                            break;
                        case 'electLord1':
                            if(data.firstELPlayer == myPosition){
                                document.getElementById('loaBtn1').style.display = 'block';
                                document.getElementById('loaBtn1').innerText = '叫地主';
                                document.getElementById('loaBtn2').style.display = 'block';
                                document.getElementById('loaBtn2').innerText = '不叫';
                            }
                            break;
                        case 'electLord2':
                            var prevElPlayerPos = (data.secondELPlayer + 2)%3;
                            switch((prevElPlayerPos - myPosition + 3)%3){
                                case 0:
                                    document.getElementById('myInfo').innerHTML = '👤' + sessionStorage.getItem('user') + '<br/>' + data.message;
                                    break;
                                case 1:
                                    document.getElementById('npInfo').innerHTML = '👤' + np + '<br/>' + data.message;
                                    break;
                                case 2:
                                    document.getElementById('ppInfo').innerHTML = '👤' + pp + '<br/>' + data.message;
                                    break;
                            }
                            if(data.secondELPlayer == myPosition){
                                if(data.message == '叫地主' || data.message == '抢地主' || data.message == '不抢'){
                                    document.getElementById('loaBtn1').innerText = '抢地主';
                                    document.getElementById('loaBtn2').innerText = '不抢';
                                }
                                if(data.message == '不叫'){
                                    document.getElementById('loaBtn1').innerText = '叫地主';
                                    document.getElementById('loaBtn2').innerText = '不叫';
                                }
                                document.getElementById('loaBtn1').style.display = 'block';
                                document.getElementById('loaBtn2').style.display = 'block';
                            }
                            break;
                        case 'lord':
                            lordPosition = data.lordPosition;
                            switch((lordPosition - myPosition + 3)%3){
                                case 0:
                                    getLordCard();
                                    refreshCard(myCard, document.getElementById('handArea'));
                                    document.getElementById('myInfo').innerHTML = '👤' + sessionStorage.getItem('user') + '<br/>' + '地主';
                                    document.getElementById('npInfo').innerHTML = '👤' + np + '<br/>' + '农民';
                                    document.getElementById('ppInfo').innerHTML = '👤' + pp + '<br/>' + '农民';
                                    break;
                                case 1:
                                    refreshCard(coveredCard(20), document.getElementById('npHandArea'));
                                    document.getElementById('npInfo').innerHTML = '👤' + np + '<br/>' + '地主';
                                    document.getElementById('ppInfo').innerHTML = '👤' + pp + '<br/>' + '农民';
                                    document.getElementById('myInfo').innerHTML = '👤' + sessionStorage.getItem('user') + '<br/>' + '农民';
                                    npLeftCardNum = 20;
                                    break;
                                case 2:
                                    refreshCard(coveredCard(20), document.getElementById('ppHandArea'));
                                    document.getElementById('ppInfo').innerHTML = '👤' + pp + '<br/>' + '地主';
                                    document.getElementById('myInfo').innerHTML = '👤' + sessionStorage.getItem('user') + '<br/>' + '农民';
                                    document.getElementById('npInfo').innerHTML = '👤' + np + '<br/>' + '农民';
                                    ppLeftCardNum = 20;
                                    break;
                            }
                            refreshCard(lordCard, document.getElementById('displayArea'));
                            setTimeout(function(){
                                    document.getElementById('displayArea').innerHTML = '';
                                    document.getElementById("loaBtn1").style.display = 'block';
                                    document.getElementById('loaBtn1').innerText = '出牌';
                                    document.getElementById("loaBtn2").style.display = 'block';
                                    document.getElementById('loaBtn2').innerText = '不要';
                                    document.getElementById('loaBtn2').disabled = true;
                                    if(lordPosition == myPosition){
                                        document.getElementById('loaBtn1').disabled = false;
                                    } else {
                                        document.getElementById('loaBtn1').disabled = true;
                                    }
                                    handAreaOnclick();
                                    preparationAreaOnclick();
                                },5000);
                            break;
                        case 'card':
                            activePlayerPosition = data.activePlayerPosition;
                            apCard = data.card;
                            prevCardNum = apCard.length;
                            var apcn = cardArrToNumArr(apCard);
                            prevCardMain = getMain(apcn);
                            switch((activePlayerPosition - myPosition + 3)%3){
                                case 0:
                                    preparationCard = [];
                                    refreshCard(preparationCard,document.getElementById('preparationArea'));
                                    refreshCard(apCard,document.getElementById('displayArea'));
                                    document.getElementById('loaBtn1').disabled = true;
                                    document.getElementById('loaBtn2').disabled = true;
                                    break;
                                case 1:
                                    refreshCard(apCard,document.getElementById('npDisplayArea'));
                                    npLeftCardNum -= apCard.length;
                                    refreshCard(coveredCard(npLeftCardNum), document.getElementById('npHandArea'));
                                    break;
                                case 2:
                                    refreshCard(apCard,document.getElementById('ppDisplayArea'));
                                    ppLeftCardNum -= apCard.length;
                                    refreshCard(coveredCard(ppLeftCardNum), document.getElementById('ppHandArea'));
                                    document.getElementById('loaBtn1').disabled = false;
                                    document.getElementById('loaBtn2').disabled = false;
                                    break;
                            }
                            break;
                        case 'abandon':
                            activePlayerPosition = data.activePlayerPosition;
                            switch((activePlayerPosition - myPosition + 3)%3){
                                case 0:
                                    document.getElementById('displayArea').innerHTML = '<div style="width:85%;text-align:center">不要</div>';
                                    document.getElementById('loaBtn1').disabled = true;
                                    document.getElementById('loaBtn2').disabled = true;
                                    break;
                                case 1:
                                    document.getElementById('npDisplayArea').innerHTML = '<div style="width:100%;text-align:center">不要</div>';
                                    break;
                                case 2:
                                    document.getElementById('ppDisplayArea').innerHTML = '<div style="width:100%;text-align:center">不要</div>';
                                    document.getElementById('loaBtn1').disabled = false;
                                    document.getElementById('loaBtn2').disabled = false;
                                    break;
                            }
                            break;
                        case 'turnEnd':
                            activePlayerPosition = data.activePlayerPosition;
                            document.getElementById('displayArea').innerHTML = '';
                            document.getElementById('npDisplayArea').innerHTML = '';
                            document.getElementById('ppDisplayArea').innerHTML = '';
                            prevCardNum = 0;
                            prevCardMain = [0,0];
                            if((activePlayerPosition - myPosition + 3)%3 == 2){
                                document.getElementById('loaBtn2').disabled = true;
                            }
                            break;
                        case 'gameOver':
                            document.getElementById('npDisplayArea').innerHTML = '';
                            document.getElementById('ppDisplayArea').innerHTML = '';
                            document.getElementById('loaBtn1').disabled = true;
                            document.getElementById('loaBtn2').disabled = true;
                            if(data.winner == 'lord' && myPosition == lordPosition){
                                document.getElementById('displayArea').innerHTML = '<div style="width:85%;text-align:center">地主获胜，你赢了！<br/>点击继续</div>';
                            }
                            if(data.winner == 'lord' && myPosition != lordPosition){
                                document.getElementById('displayArea').innerHTML = '<div style="width:85%;text-align:center">地主获胜，你输了。。<br/>点击继续</div>';
                            }
                            if(data.winner == 'farmer' && myPosition == lordPosition){
                                document.getElementById('displayArea').innerHTML = '<div style="width:85%;text-align:center">农民获胜，你输了。。<br/>点击继续</div>';
                            }
                            if(data.winner == 'farmer' && myPosition != lordPosition){
                                document.getElementById('displayArea').innerHTML = '<div style="width:85%;text-align:center">农民获胜，你赢了！<br/>点击继续</div>';
                            }
                            document.getElementById('displayArea').onclick = function(){
                                init();
                            }
                            break;
                        case 'gameOver2':
                            document.getElementById('displayArea').innerHTML = '<div style="width:85%;text-align:center">' + data.escapePlayer + '逃跑了，游戏结束。。<br/>点击继续</div>';
                            document.getElementById('displayArea').onclick = function(){
                                init();
                            }
                            break;
                    }
                };
                socket.onclose = function() {
                    console.log('SwlSocket已关闭');
                };
                socket.onerror = function() {
                    alert('SwlSocket发生了错误');
                };
                window.unload = function() {
                    socket.close();
                };
            }
        },
        exitBtnClick(){
            socket.send(JSON.stringify({'phase':2,'player':sessionStorage.getItem('user')}));
            socket.close();
        },
        prepareBtnClick(){
            socket.send(JSON.stringify({'phase':3,'player':sessionStorage.getItem('user'),'status':status}));
        },
        lordOrApplyBtnClick(event){
            var bit = event.target.innerText;
            if(bit == '出牌'){
                var na = cardArrToNumArr(preparationCard);
                var m = getMain(na);
                var cn = na.length;
                if((prevCardNum == 0 && m[0] != 0) || (prevCardNum == cn && prevCardMain[1] == m[1] && prevCardMain[0] < m[0]) || (!((prevCardNum == 4 && prevCardMain[1] == 4) || prevCardMain[1] == 6) && (cn == 4 && m[1] == 4)) || m[1] == 6){
                    socket.send(JSON.stringify({'phase':5,'activePlayerPosition':myPosition,'card':preparationCard}));
                }else{
                    alert('出牌无效！');
                }
            } else if(bit == '不要') {
                socket.send(JSON.stringify({'phase':6,'activePlayerPosition':myPosition}));
            } else {
                socket.send(JSON.stringify({'phase':4,'playerPosition':myPosition,'message':bit}));
                document.getElementById('loaBtn1').style.display = 'none';
                document.getElementById('loaBtn2').style.display = 'none';
            }
        }
    }
}
</script>

<style scoped>
.swldiv{
    width:50%;
    height:50%;
    position:absolute;
    top:0%;
    display:none;
}
.card{
    width:36px;
    height:60px;
    border:1px solid #000;
    margin:1px;
    display:inline-block;
}
.swlBtn{
    left:50%;
    top:50%;
    position: absolute;
    -webkit-transform: translate(-50%, -50%);   
    -moz-transform: translate(-50%, -50%);   
    -ms-transform: translate(-50%, -50%);   
    -o-transform: translate(-50%, -50%);   
    transform: translate(-50%, -50%);  
}
.subbtndiv{
    width:100%;
    height:50%;
    position:relative;
}
</style>