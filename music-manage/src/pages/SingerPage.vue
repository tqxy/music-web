<template>
  <div class="table">
    <div class="container">
      <div class="handle-box">
        <el-input v-model="select_word" size="mini" placeholder="请输入歌手名" class="handle-input mr10"></el-input>
        <el-button type="primary" size="mini" @click="centerDialogVisible=true">添加歌手</el-button>
      </div>
    </div>
    <el-table size="mini" border style="width: 100%" height="600px" :data="data">
      <el-table-column label="歌手图片" width="110" align="center">
        <template slot-scope="scope">
          <div class="singer-img">
            <img :src="getUrl(scope.row.pic)" style="width:100%"/>

          </div>
          <el-upload :action=uploadUrl(scope.row.id) :before-upload="beforeAvatarUpload" :on-success="handleAvatorSuccess">
            <el-button size="mini">更新图片</el-button>
          </el-upload>
        </template>
      </el-table-column> 
      <el-table-column prop="name" label="歌手名" width="110" align="center"></el-table-column>
      <el-table-column label="性别" width="80" align="center">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.sex==1">男</el-tag>
          <el-tag v-else-if="scope.row.sex==0">女</el-tag>
          <el-tag v-else-if="scope.row.sex==2">组合</el-tag>
          <el-tag v-else-if="scope.row.sex==3">未知</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="birth" label="生日" width="110" align="center">
        <template slot-scope="scope">
          {{attachBirth(scope.row.birth)}}
        </template>
      </el-table-column>
      <el-table-column prop="location" label="地区" width="110" align="center"></el-table-column>
      <el-table-column label="简介" width="500" align="center">
        <template slot-scope="scope">
          <p style="height: 100px;overflow: scroll;">{{ scope.row.introduction }}</p>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="150" align="center">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" icon="el-icon-edit" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button type="danger" size="mini" icon="el-icon-delete" @click="handleDelete(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="pageination">
      <el-pagination
        background
        layout="total,prev, pager, next"
        :current-page="currentPage"
        :page-size="pageSize"
        :total="tableData.length"
        @current-change="handleCurrentChange"
      ></el-pagination>
    </div>
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
          <el-button size="mini" @click="centerDialogVisible=false">取消</el-button>
          <el-button size="mini" @click="addSinger">确定</el-button>
        </span>
    </el-dialog>
    <el-dialog title="修改歌手" :visible.sync="editVisible" width="400px" center>
      <el-form :model="form" ref="form" label-width="80px">
        <el-form-item label="歌手名" prop="name" size="mini">
          <el-input v-model="form.name" placeholder="歌手名" />
        </el-form-item>
        <el-form-item label="性别" prop="sex" size="mini">
          <el-radio-group v-model="form.sex">
            <el-radio :label="1">男</el-radio>
            <el-radio :label="0">女</el-radio>
            <el-radio :label="2">组合</el-radio>
            <el-radio :label="3">不明</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="生日" prop="birth" size="mini">
          <el-date-picker type="date" placeholder="选择日期" v-model="form.birth" style="width: 100%;"/>
        </el-form-item>
        <el-form-item label="地区" prop="location" size="mini">
          <el-input v-model="form.location" placeholder="地区" />
        </el-form-item>
        <el-form-item label="简介" prop="introduction" size="mini">
          <el-input v-model="form.introduction" placeholder="简介" type="textarea"/>
        </el-form-item>
      </el-form>
        <span slot="footer">
          <el-button size="mini" @click='editVisible=false'>取消</el-button>
          <el-button size="mini" @click="editSave">确定</el-button>
        </span>
    </el-dialog>
  </div>
</template>

<script>
import { Upload } from 'element-ui';
import {setSinger,getAllSinger,updateSinger} from '../api/index.js';
import TheAside from '../components/TheAside.vue';
import TheHeader from '../components/TheHeader.vue';
import { mixin } from '../mixins/index.js'
import { template } from 'babel-core';

export default{
  mixins:[mixin],
  data(){
    return{
      centerDialogVisible: false,//添加弹窗是否显示
      //编辑弹窗是否显示
      editVisible:false,
      registerForm:{
        name:'',
        sex:'',
        birth:'',
        location:'',
        introduction:''
      },
      form:{
        id:'',
        name:'',
        sex:'',
        birth:'',
        location:'',
        introduction:''
      },
      tableData:[],
      tempData:[],
      select_word:'',
      pageSize:4,//分页每页大小
      currentPage:1
    }
  },
  watch:{
    select_word(val){
      if(this.select_word==''){
        this.tableData=this.tempData;
      }else{
        this.tableData=[];
        for(let item of this.tempData){
          if(item.name.includes(this.select_word)){
            this.tableData.push(item);
          }
        }
      }
    }
  },
  computed:{
    //计算当前搜索结果表的数据
    data(){
      return this.tableData.slice((this.currentPage-1)*this.pageSize,this.currentPage*this.pageSize);
    }
  },
  created(){
    this.getData();
  },
  methods:{
    //查询所有歌手
    getData(){
      this.tableData=[];
      getAllSinger()
      .then(res => {
        this.tableData=res;
        this.tempData=res;
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
          this.getData();
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
    },
    //更新图片
    uploadUrl(id){
      //console.log(`${this.$store.state.HOST}/singer/updateSingerPic?id=${id}`)
      return `${this.$store.state.HOST}/singer/updateSingerPic?id=${id}`
    },
    //获取当前页
    handleCurrentChange(val){
      this.currentPage=val;
    },
    //弹出编辑页面
    handleEdit(row){
      this.editVisible=true;
      this.form={
        id:row.id,
        name:row.name,
        sex:row.sex,
        birth:row.birth,
        location:row.location,
        introduction:row.introduction
      }
      console.log(row)
    },
    editSave(){
      let d=new Date(this.form.birth);
      //let datetime=d.getFullYear()+'-'+(d.getMonth()+1)+'-'+d.getDate();
      let formattedDate = d.toISOString()
      //console.log(formattedDate)
      let params=new URLSearchParams();
      let payload={
        id:this.form.id,
        name:this.form.name,
        pic:this.form.pic,
        sex:this.form.sex,
        birth:formattedDate,
        location:this.form.location,
        introduction:this.form.introduction
      }
      
      updateSinger(JSON.stringify(payload))
      .then(res => {
        if(res="true"){
          this.getData();
          this.notify('修改成功', 'success');
        }else{
          this.notify('修改失败', 'error');
        }
      })
      .catch(error=>{
        this.notify('修改失败', 'error');
        console.log(error);
      })
      this.editVisible=false;

    }
  },
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
  .handle-input{
    width: 300px;
    display: inline-block;
  }
  .pagination{
    display: flex;
    justify-content: center;
  }
</style>