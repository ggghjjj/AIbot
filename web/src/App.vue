<template>
  <div>
    <div>名称:{{name}}</div>
    <div>年龄:{{age}}</div>
   <div>性别:{{ sex}}</div>
  </div> 
  <router-view></router-view>
</template>

<script>

 import $ from 'jquery';
import { ref } from 'vue';

export default {
  name:"App",
  setup: ()=> {
      let name = ref("");
      let age = ref("");
      let sex = ref("");

      $.ajax({
        url:"http://localhost:3000/pk/boxinfo/",
        type:"get",
        success: resp => {
          name.value = resp.name;
          age.value = resp.age;
          sex.value = resp.sex;
        }
      });

      return {
        name,
        age,
        sex
  }
  }
}
</script>

<style>
body{
  background-image: url("@/assets/background.png");
  background-size:cover ;
}
</style>
