<template>
      <PlayGround v-if="$store.state.pk.status === 'playing'" />
      <MatchGround v-if="$store.state.pk.status === 'matching'" />
       <ResultBoard v-if="$store.state.pk.loser != 'none'" />
</template>

<script>

import PlayGround from '../../components/PlayGround.vue'
import MatchGround from '../../components/MatchGround.vue'
import { onMounted,onUnmounted} from 'vue'
import { useStore } from 'vuex'
import ResultBoard from '@/components/ResultBoard.vue'
export default {
    components: {
      PlayGround,
      MatchGround,
      ResultBoard
    },
    setup() {
      const store = useStore();
      const socketurl =`ws://127.0.0.1:3000/websocket/${store.state.user.token}/`;

      let socket = null;
      onMounted(()=>{
        socket = new WebSocket(socketurl);

        socket.onopen = () => {
          console.log("connected");
          store.commit("updateSocket",socket);
        }

        socket.onmessage = msg => {
          const data = JSON.parse(msg.data);
        
          if(data.event === "start-matching") {
            store.commit("updateOpponent",{
              username:data.opponent_username,
              photo:data.opponent_photo,
            })

            setTimeout(()=>{
                 store.commit("updateStatus","playing");
            },2000);

            store.commit("updateGame",data.game);
           
          }else if(data.event === "move") {
            const game = store.state.pk.gameObject;
            const [snake0, snake1] = game.snakes;
         
            snake0.set_direction(data.a_direction);
            snake1.set_direction(data.b_direction);
          }else if(data.event === "result") {
            console.log(data);
              const game = store.state.pk.gameObject;
                const [snake0, snake1] = game.snakes;
                if(data.loser === "all") {
                  snake0.status = "die";
                  snake1.status = "die";
                }else if(data.loser ==='A') {
                   snake0.status = "die";
                }else if(data.loser ==='B') {
                    snake1.status = "die";
                }
                store.commit("updateLoser",data.loser);
          }
         
        }

        socket.onclose = () => {
          console.log("disconnect");
        }
      })

      onUnmounted(()=>{
        socket.close();
      })
      

    }
}
</script>

<style scoped>
</style>