<template>
    <div class="container">
        <div class="row">
            <div class="col-3">
                <div class="card" style="margin-top:20px;">
                    <div class="card-body">
                        <img :src="$store.state.user.photo" alt="" style="width: 100%;">
                    </div>
                </div>
            </div>

            <div class="col-9">
                 <div class="card" style="margin-top:20px;">
                    <div class="card-header">
                        <span style-size="130%">我的bot</span>
                        <button type="button" class="btn btn-primary float-end"   data-bs-toggle="modal" data-bs-target="#add-bot-btn">创建bot</button>
                    </div>

                 <!-- Modal -->
                    <div class="modal fade" id="add-bot-btn" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLabel">创建bot</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <div class="mb-3">
                            <label for="add_bot_title" class="form-label" >bot名称</label>
                            <input v-model="botadd.title" type="email" class="form-control" id="add_bot_title" placeholder="请输入bot名称">
                            </div>
                            <div class="mb-3">
                            <label for="add_bot_description" class="form-label">bot简介</label>
                            <textarea  v-model="botadd.description" class="form-control" id="add_bot_description" rows="1" placeholder="请输入bot简介"></textarea>
                            </div>
                            <div class="mb-3">
                            <label for="add_bot_code" class="form-label">bot代码</label>
                           <VAceEditor
                            v-model:value="botadd.content"
                            @init="editorInit"
                            lang="c_cpp"
                            theme="textmate"
                            style="height: 300px" />
                            </div>
                        </div>
                        <div class="modal-footer">
                            <div class="error_message">{{botadd.error_message}}</div>
                            <button type="button" class="btn btn-primary" @click="add_bot">创建</button>
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">关闭</button>
                            
                        </div>
                        </div>
                    </div>
                    </div>

                     <div class="card-body">
                         <table class="table table-striped table-hover">
                            <thead>
                                <tr>
                                    <th>名称</th>
                                    <th>创建时间</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr v-for="bot in bots" :key="bot.id">
                                    <td>{{bot.title}}</td>
                                    <td>{{bot.createtime}}</td>
                                    <td>
                                        <button type="button" class="btn btn-secondary" data-bs-toggle="modal" :data-bs-target="'#update-bot-modal-'+bot.id">修改</button>
                                        <button type="button" class="btn btn-danger" style="margin-left:10px;" @click="remove_bot(bot)">删除</button>
                                    
                                            <div class="modal fade" :id="'update-bot-modal-'+bot.id" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLabel">创建bot</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <div class="mb-3">
                            <label for="add_bot_title" class="form-label" >bot名称</label>
                            <input v-model="bot.title" type="email" class="form-control" id="add_bot_title" placeholder="请输入bot名称">
                            </div>
                            <div class="mb-3">
                            <label for="add_bot_description" class="form-label">bot简介</label>
                            <textarea  v-model="bot.description" class="form-control" id="add_bot_description" rows="1" placeholder="请输入bot简介"></textarea>
                            </div>
                            <div class="mb-3">
                            <label for="add_bot_code" class="form-label">bot代码</label>
                                   <VAceEditor
                            v-model:value="bot.content"
                            @init="editorInit"
                            lang="c_cpp"
                            theme="textmate"
                            style="height: 300px" />
                            </div>
                        </div>
                        <div class="modal-footer">
                            <div class="error_message">{{botadd.error_message}}</div>
                            <button type="button" class="btn btn-primary" @click="update_bot(bot)">修改</button>
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">关闭</button>
                            
                        </div>
                        </div>
                    </div>
                    </div>

                                    </td>
                                </tr>
                            </tbody>

                         </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>

import { useStore } from 'vuex'
import $ from 'jquery'
import {ref,reactive} from 'vue'
import { Modal } from 'bootstrap/dist/js/bootstrap'
import { VAceEditor } from 'vue3-ace-editor';
import ace from 'ace-builds';
export default {
   components: {
    VAceEditor,
  },


    setup() {
             ace.config.set(
      "basePath",
      "https://cdn.jsdelivr.net/npm/ace-builds@" +
        require("ace-builds").version +
        "/src-noconflict/"
    );



        const store = useStore();
        let bots = ref([]);

        const botadd = reactive({
            title:"",
            description:"",
            content:"",
            error_message:"",
        });

        const refresh_bots = () => {
            console.log("bot_getlist");
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
         
        const add_bot =() =>{
            
            console.log("bot_add");
            botadd.error_message = "";
             $.ajax({
             url: "https://app488.acapp.acwing.com.cn/api/user/bot/add/",
                type: "POST",
                data: {
                    title: botadd.title,
                    description: botadd.description,
                    content: botadd.content,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    if(resp.error_message==="success") {
                         console.log(resp);
                        botadd.title="";
                        botadd.description="";
                        botadd.content="";
                        Modal.getInstance("#add-bot-btn").hide();
                         refresh_bots();
                    }else {
                     
                        
                         botadd.error_message = resp.error_message;
                    }
                },
        
            });
        }

        const update_bot = (bot) => {
            console.log("bt_update");
            botadd.error_message = "";
             $.ajax({
             url: "https://app488.acapp.acwing.com.cn/api/user/bot/update/",
                type: "POST",
                data: {
                    bot_id:bot.id,
                    title: bot.title,
                    description: bot.description,
                    content: bot.content,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    if(resp.error_message==="success") {
                         console.log(resp);
                        Modal.getInstance("#update-bot-modal-" + bot.id).hide();
                         refresh_bots();
                    }else {
                     
                        
                         bot.error_message = resp.error_message;
                    }
                },
        
            });

        }

       const get_Bot=()=> {

        $.ajax({
             url: "https://app488.acapp.acwing.com.cn/api/user/bot/code/",
            type: "get",
            headers: {
                Authorization: "Bearer " + store.state.user.token,
            },
            success(resp) {
                botadd.content = resp.code;
               if(resp.error_message==="success") {
                 botadd.content = resp.code;
                }
            }

    //   axios.get('/api/bot') // 示例：假设后端接口路径为 /api/bot
    //     .then(response => {
    //       // 处理后端返回的结果
    //       console.log(response.data);
    //     })
    //     .catch(error => {
    //       // 处理错误
    //       console.error(error);
    //     });
             });
    }

    get_Bot();

        const remove_bot = (bot) => {
            console.log("bot_remove");
            $.ajax({
             url: "https://app488.acapp.acwing.com.cn/api/user/bot/remove/",
            type: "post",
            data: {
                bot_id:bot.id,
            },
            headers: {
                Authorization: "Bearer " + store.state.user.token,
            },
            success(resp) {
               if(resp.error_message==="success") {
                 refresh_bots();
                }
            },
           
        });
        }

        return{
            bots,
            refresh_bots,
            botadd,
            add_bot,
            remove_bot,
            update_bot,
            get_Bot
        }
    }
     
}


</script>

<style scoped>
.error_message{
    color:red;
}

</style>
