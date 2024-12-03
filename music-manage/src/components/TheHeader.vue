<template>
  <div class="header">
    <div class="collapse-btn" @click="collapseChange">
      <i class="el-icon-menu"></i>
    </div>
    <div class="logo">music后台管理</div>
    <div class="header-right">
      <div class="btn-fullscreen" @click="handleFullScreen">
        <el-tooltip :content="fullscreen? '退出全屏':'全屏'" placement='bottom'>
        <i class="el-icon-menu"></i>
        </el-tooltip>
      </div>
      <div class="user-avator">
        <img src="../assets/img/user.jpg"/>
      </div>
      <el-dropdown class="user-name" trigger="click" @command="handleCommand">
        <span class="el-dropdown-link">
          {{ userName }}
        </span>
        <i class="el-icon-caret-bottom"></i>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item command="logout">退出登录</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>
<script>
import bus from '../assets/js/bus'
export default {
  data(){
    return{
      Collapse:false,
      fullscreen:false
    }
  },
  computed:{
    userName(){
      return localStorage.getItem('userName')
    }
  },
  methods:{
    collapseChange(){
      this.Collapse = !this.Collapse
      bus.$emit('Collapse',this.Collapse)
    },
    handleFullScreen(){
      if (!document.fullscreenElement) {
        if (document.documentElement.requestFullscreen) {
          document.documentElement.requestFullscreen();
        } else if (document.documentElement.webkitRequestFullscreen) { /* Safari */
          document.documentElement.webkitRequestFullscreen();
        } else if (document.documentElement.msRequestFullscreen) { /* IE11 */
          document.documentElement.msRequestFullscreen();
        }
      } else {
        if (document.exitFullscreen) {
          document.exitFullscreen();
        } else if (document.webkitExitFullscreen) { /* Safari */
          document.webkitExitFullscreen();
        } else if (document.msExitFullscreen) { /* IE11 */
          document.msExitFullscreen();
        }
      }
      this.fullscreen = !this.fullscreen
    },
    handleCommand(command){
      if(command=="logout"){
        localStorage.removeItem('userName');
        this.$router.push("/");
      }
    }
  }
}
</script>
<style scoped>
.header {
  position:relative;
  background-color: #253041;
  box-sizing: border-box;
  height: 70px;
  width: 100%;
  font-size:22px;
  color:#ffffff;
  
}
.collapse-btn {
  float:left;
  padding:0 21px;
  cursor:pointer;
  line-height: 70px;
}
.header .logo {
  float:left;
  line-height:70px;

}
.header-right {
  float: right;
  padding-right: 50px;
  display: flex;
  height:70px;
  align-items: center;
}
.header-user-con {
  display: flex;
  height: 70px;
  align-items: center;
}
.btn-fullscreen {
  transform:rotate(45deg);
  margin-right:5px;
  font-size:24px;
  
}
.user-name {
  margin-left: 10px;
}
.user-avator {
  margin-left:20px;
}
.user-avator img {
  display:block;
  width:40px;
  height:40px;
  border-radius:50%;
  
}
.el-dropdown-link {
  color: #ffffff;
  cursor: pointer;
}
.el-dropdown-menu__item {
  text-align: center;
}
</style>
