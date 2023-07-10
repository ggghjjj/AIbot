<template>
    <el-card shadow="always">
      <h3>AI Bots 是什么？<small>网站功能</small></h3>
      <p>AI Bots 是一款在线的以贪吃蛇为背景的在线联机对战游戏，玩家可以编写AI代码,实现人机对战，人人对战,机机对战</p>
      <p>
        <strong>游戏规则：</strong> 使用<strong>W A S D</strong>控制机器人小蛇移动,注意不要撞到障碍物和双方的身体，最终导致对面不能走即可获取胜利
      </p>
      <p>
        <strong>注意</strong> 机器人小蛇会随着移动步数而增长,每回合你的下达指令时间是<strong>5</strong>秒钟,超过该时间未下达指令也会输
      </p>
      <el-divider />
      <h3>我不知道怎么用 AI Bots啊？<small>使用方法</small></h3>
      <ol>
        <li>注册 AI Bots 账户</li>
        <li>
          点击
          对战即可开始对战
          对战可选择人机对战和匹配玩家对战，机器人代码由 <el-link type="primary" href="https://github.com/ggghjjj/AIbot" class="card-link">游戏说明</el-link>给出，玩家也可以编写自己的代码，根据出战列表选择亲自上阵或者自己的AI机器人对战
        </li>
        <li>想要制定自己的bot?  点击右上角账户名，进入我的 Bot 页面，创建 Bot 并上传程序</li>
        <li>点击排行榜，查看全网玩家积分</li>
        <li>点击对战列表,查看全网玩家游戏录像</li>
        <li>
          更多详情请点击
          <el-link type="primary" href="https://github.com/ggghjjj/AIbot" class="card-link">游戏说明</el-link>
          查看游戏原理,按照游戏说明写自己的 AI 程序
        </li>
      </ol>
    </el-card>
  </template>
  
  <script>
  export default {
    name: "HomeMessage"
  }
  </script>
  
  <style scoped>
  
  </style>