<template>
  <div class="table">
    <div class="container">
      <div class="handle-box">
        <el-button type="primary" size="mini" @click="centerDialogVisible=true">添加歌手</el-button>
      </div>
    </div>
    <el-dialog title="添加歌手" :visible.sync="centerDialogVisible" width="400px" center>
      <el-form :model="registerForm" ref="registerForm" label-width="80px">
        <el-form-item label="歌手名" prop="name" size="mini">
          <el-input v-model="registerForm.name" placeholder="歌手名" />
        </el-form-item>

      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import TheAside from '../components/TheAside.vue';
import TheHeader from '../components/TheHeader.vue';
export default{
  data(){
    return{
      centerDialogVisible: false,
      registerForm:{
        name:''
      }
    }
  },
    components:{TheAside,TheHeader},
}
</script>

<style scoped>
  .handle-box{
    margin-bottom: 20px;
  }
</style>