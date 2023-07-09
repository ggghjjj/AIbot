<template>
    <ContentField>
      <el-row class="full-width-row"  :gutter="12">
        <el-col :xs="24" :sm="24">
          <el-alert title="欢迎来到 AI Bots" type="success" description="你可以创建游戏桌来进行对局。" center/>
          <HomeMessage/>
        </el-col>
  
      </el-row>
    </ContentField>
  </template>
  
  <script>
  import ContentField from '@/components/ContentField.vue';
  import HomeMessage from "@/components/HomeMessage";
  import router from "@/router";
import {useStore} from "vuex";
  export default {
    name: 'HomeView',
    components:  {HomeMessage, ContentField},
    setup() {
    const store = useStore();
    const jwt_token=localStorage.getItem("jwt_token");
    if(jwt_token){
      store.commit("updateToken",jwt_token);
      store.dispatch("getinfo",{
        success(){
          router.push({name:"home"});
          store.commit("updatePullingInfo",false);
        },
        error(){
          store.commit("updatePullingInfo",false);
        }
      })
    }else{
      store.commit("updatePullingInfo",false);
    }
  }
  }
  </script>
  
  <style scoped>

.full-width-row {
  display: flex;
  width: 100%;
}
  </style>