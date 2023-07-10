<template>
    <div class="matchground">
        <div class="row">
            <div class="col-4">
                <div class="user-photo">
                    <img :src="$store.state.user.photo" alt="">
                </div>
                <div class="user-username">
                    {{ $store.state.user.username }}
                </div>
            </div>
            <div class="col-4">
                <div class="user-select-bot">
                    <select v-model="select_bot" class="form-select" aria-label="Default select example">
                        <option value="-1" selected>亲自出马</option>
                        <option v-for="bot in bots" :key="bot.id" :value="bot.id">
                            {{ bot.title }}
                        </option>
                    </select>
                </div>

            </div>
            <div class="col-4">
                <div class="user-photo">
                    <img :src="$store.state.pk.opponent_photo" alt="">
                </div>
                <div class="user-username">
                    {{ $store.state.pk.opponent_username }}
                </div>

                <!-- <div style="text-align: center; padding-top: 15vh;">
                <button @click="click_select_btn" type="button" class="btn btn-warning btn-lg">{{ select_btn_info }}</button>


                
            </div> -->

            <div  v-if="status === 1" class="btn-group" role="group" aria-label="Button Group" style="padding-left: 10vh ;padding-top: 5vh;">
            <button type="button" class="btn btn-custom" :class="{ 'active': select_btn_info == 1 }" @click="click_select_btn(1)">玩家对战</button>
            <button type="button" class="btn btn-custom" :class="{ 'active': select_btn_info == 2 }" @click="click_select_btn(2)">AI对战</button>
            </div>


            </div>
            <div class="col-12" style="text-align: center; padding-top: 15vh;">
                <button @click="click_match_btn" type="button" class="btn btn-warning btn-lg">{{ match_btn_info }}</button>
            </div>
        </div>
    </div>
</template>

<script>

import { ref } from 'vue'
import {useStore} from 'vuex'
import $ from "jquery";
export default {
    setup() {
        const store = useStore();
        let match_btn_info = ref("开始匹配");
        let select_bot = ref("-1")
        let bots = ref([]);
        let select_btn_info = ref(1);
        let status = ref(1)
        
        const userId = store.state.user.id;


        status.value = 1;
        const click_match_btn = () => {
               
            if (match_btn_info.value === "开始匹配") {
                status.value = 0;
                console.log(status.value);
                match_btn_info.value = "取消";
                if(select_btn_info.value==1) {
                  store.state.pk.socket.send(JSON.stringify({
                    event:"start-matching",
                    bot_id:select_bot.value,
                }))
            }else {
                console.log("和机器人对战吧");
                $.ajax({
                url: "https://app488.acapp.acwing.com.cn/api/pk/start/gameai/",
                type: "post",
                headers: {
                        Authorization: "Bearer " + store.state.user.token,
                    },
                data: {
                    id: userId,
                    bot_id: select_bot.value,
                },
                success(resp) {
                   console.log(resp);
                }
            });

            }
            } else {
                status.value = 1;
                match_btn_info.value = "开始匹配";
                console.log(status.value);
                store.state.pk.socket.send(JSON.stringify({
                    event:"stop-matching",
                    
                }))
            }
        }

        const click_select_btn = Value => {
            console.log(Value);
            select_btn_info.value = Value;
            if(select_btn_info.value==2) {
                store.commit("updateOpponent",{
                username:"AI机器人",
                photo:"https://cdn.acwing.com/media/article/image/2022/10/05/113094_88bb9bb744-1-1Z1301113190-L.jpg",
                })
            }else {
                store.commit("updateOpponent",{
                username:"我的对手",
                photo:"https://cdn.acwing.com/media/article/image/2022/08/09/1_1db2488f17-anonymous.png",
                })
            }
        }

        const refresh_bots = () => {
            console.log("bot");
            $.ajax({
                    url: "https://app488.acapp.acwing.com.cn/api/user/bot/getlist/",
                    type: "get",
                    headers: {
                        Authorization: "Bearer " + store.state.user.token,
                    },
                    success(resp) {
                        bots.value = resp;
                    },
                });
        }

         refresh_bots();

        return {
            match_btn_info,
            click_match_btn,
            refresh_bots,
            select_bot,
            bots,
            select_btn_info,
            click_select_btn,
            status,
            userId
            
        }
    }
}
</script>

<style scoped>
div.matchground { 
    width: 60vw;
    height: 70vh;
    margin: 40px auto;
    /* background-color: rgba(50, 50, 50, 0.5); */
}
div.user-photo {
    text-align: center;
    padding-top: 10vh;
}
div.user-photo > img {
    border-radius: 50%;
    width: 20vh;
}
div.user-username {
    text-align: center;
    font-size: 24px;
    font-weight: 600;
    color: white;
    padding-top: 2vh;
}

div.user-select-bot {
    padding-top: 20vh;
}

div.user-select-bot > select {
    width: 60%;
    margin: 0 auto;
}
.btn-custom {
  background-color: #f8f9fa;
  border-color: #f8f9fa;
  color: #212529;
}

.btn-custom.active,
.btn-custom:active,
.btn-custom:focus,
.btn-custom:hover {
  background-color: #007bff;
  border-color: #007bff;
  color: #fff;
}

</style>
