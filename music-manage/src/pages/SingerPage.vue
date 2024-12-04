<template>
  <div class="table">
    <div class="container">
      <div class="handle-box">
        <el-button type="primary" size="mini" @click="centerDialogVisible=true">添加歌手</el-button>
      </div>
    </div>
    <el-table size="mini" border style="width: 100%" height="500px" :data="tableData">
      <el-table-column label="歌手图片" width="110" align="center">
        <template slot-scope="scope">
          <div class="singer-img">
            <img :src="getUrl(scope.row.pic)" style="width:100%"/>

          </div>
        </template>
      </el-table-column> 
    </el-table>
    <el-dialog title="添加歌手" :visible.sync="centerDialogVisible" width="400px" center>
      <el-form :model="registerForm" ref="registerForm" label-width="80px">
        <el-form-item label="歌手名" prop="name" size="mini">
          <el-input v-model="registerForm.name" placeholder="歌手名" />
        </el-form-item>
        <el-form-item label="性别" prop="sex" size="mini">
          <el-radio-group v-model="registerForm.sex">
            <el-radio :label="1">男</el-radio>
            <el-radio :label="0">女</el-radio>
            <el-radio :label="2">组合</el-radio>
            <el-radio :label="3">不明</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="生日" prop="birth" size="mini">
          <el-date-picker type="date" placeholder="选择日期" v-model="registerForm.birth" style="width: 100%;"/>
        </el-form-item>
        <el-form-item label="地区" prop="location" size="mini">
          <el-input v-model="registerForm.location" placeholder="地区" />
        </el-form-item>
        <el-form-item label="简介" prop="introduction" size="mini">
          <el-input v-model="registerForm.introduction" placeholder="简介" type="textarea"/>
        </el-form-item>
      </el-form>
        <span slot="footer">
          <el-button size="mini" centerDialogVisible="false">取消</el-button>
          <el-button size="mini" @click="addSinger">确定</el-button>
        </span>
    </el-dialog>
  </div>
</template>

<script>
import {setSinger,getAllSinger} from '../api/index.js';
import TheAside from '../components/TheAside.vue';
import TheHeader from '../components/TheHeader.vue';
import { mixin } from '../mixins/index.js'

export default{
  mixins:[mixin],
  data(){
    return{
      centerDialogVisible: false,
      registerForm:{
        name:'',
        sex:'',
        birth:'',
        location:'',
        introduction:''
      },
      tableData:[]
    }
  },
  created(){
    this.getData();
  },
  methods:{
    //查询所有歌手
    getData(){
      getAllSinger()
      .then(res => {
        this.tableData=res.data;
      })
      .catch(error=>{
        console.log(error);
      })
    },
    //添加歌手
    addSinger(){
      let d=this.registerForm.birth;
      let datetime=d.getFullYear()+'-'+(d.getMonth()+1)+'-'+d.getDate();
      let params=new URLSearchParams();
      params.append('name',this.registerForm.name);
      params.append('sex',this.registerForm.sex);
      params.append('pic','/img/singerPic/hhh.png');
      params.append('location',this.registerForm.location);
      params.append('birth',datetime);
      params.append('introduction',this.registerForm.introduction);
      setSinger(params)
      .then(res => {
        if(res.code==1){
          this.notify('添加成功', 'success');
        }else{
          this.notify('添加失败', 'error');
        }
      })
      .catch(error=>{
        this.notify('添加失败', 'error');
        console.log(error);
      })
      this.centerDialogVisible=false;
  }},
  components:{TheAside,TheHeader},
}
</script>

<style scoped>
  .handle-box{
    margin-bottom: 20px;
  }
  .singer-img{
    width:100%;
    height:80px;
    border-radius: 5px;
    margin-bottom: 5px;
    overflow: hidden;
  }
</style>