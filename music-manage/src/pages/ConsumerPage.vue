<template>
  <div class="table">
    <div class="container">
      <div class="handle-box">
        <el-button type="primary" size="mini" @click="getData">刷新</el-button>
        <el-button type="primary" size="mini" @click="delAll">批量删除</el-button>
        <el-input v-model="select_word" size="mini" placeholder="筛选相关用户" class="handle-input mr10"></el-input>
        <el-button type="primary" size="mini" @click="centerDialogVisible=true">添加新用户</el-button>
      </div>
    </div>
    <el-table size="mini" border style="width: 100%" height="600px" :data="data" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"></el-table-column>
      <el-table-column label="用户头像" width="110" align="center">
        <template slot-scope="scope">
          <div class="avator-img">
            <img :src="getUrl(scope.row.avator)" style="width:100%"/>

          </div>
          <el-upload :action=uploadUrl(scope.row.id) :before-upload="beforeAvatarUpload" :on-success="handleAvatorSuccess">
            <el-button size="mini">更新图片</el-button>
          </el-upload>
        </template>
      </el-table-column> 
      <el-table-column prop="username" label="用户名" width="110" align="center"></el-table-column>
      <el-table-column label="性别" width="80" align="center">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.sex==1">男</el-tag>
          <el-tag v-else-if="scope.row.sex==0">女</el-tag>
          <el-tag v-else-if="scope.row.sex==2">不愿透露</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="phoneNum" label="手机号" width="110" align="center"></el-table-column>
      <el-table-column prop="email" label="电子邮箱" width="110" align="center"></el-table-column>
      <el-table-column prop="birth" label="生日" width="110" align="center">
        <template slot-scope="scope">
          {{attachBirth(scope.row.birth)}}
        </template>
      </el-table-column>
      <el-table-column prop="introduction" label="签名" width="110" align="center"></el-table-column>
      <el-table-column prop="location" label="地区" width="110" align="center"></el-table-column>
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
    <el-dialog title="添加新用户" :visible.sync="centerDialogVisible" width="400px" center>
      <el-form :model="registerForm" ref="registerForm" label-width="80px" :rules="rules">
        <el-form-item label="用户名" prop="username" size="mini">
          <el-input v-model="registerForm.username" placeholder="用户名" />
        </el-form-item>
        <el-form-item label="密码" prop="password" size="mini">
          <el-input type="password" v-model="registerForm.password" placeholder="密码" />
        </el-form-item>
        <el-form-item label="性别" prop="sex" size="mini">
          <el-radio-group v-model="registerForm.sex">
            <el-radio :label="1">男</el-radio>
            <el-radio :label="0">女</el-radio>
            <el-radio :label="2">不愿透露</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="手机号" prop="phoneNum" size="mini">
          <el-input v-model="registerForm.phoneNum" placeholder="手机号" />
        </el-form-item>
        <el-form-item label="电子邮箱" prop="email" size="mini">
          <el-input v-model="registerForm.email" placeholder="电子邮箱" />
        </el-form-item>
        <el-form-item label="生日" prop="birth" size="mini">
          <el-date-picker type="date" placeholder="选择日期" v-model="registerForm.birth" style="width: 100%;"/>
        </el-form-item>
        <el-form-item label="地区" prop="location" size="mini">
          <el-input v-model="registerForm.location" placeholder="地区" />
        </el-form-item>
        <el-form-item label="签名" prop="introduction" size="mini">
          <el-input v-model="registerForm.introduction" placeholder="简介" type="textarea"/>
        </el-form-item>
      </el-form>
        <span slot="footer">
          <el-button size="mini" @click="centerDialogVisible=false">取消</el-button>
          <el-button size="mini" @click="addConsumer">确定</el-button>
        </span>
    </el-dialog>
    <el-dialog title="修改用户" :visible.sync="editVisible" width="400px" center>
      <el-form :model="form" ref="form" label-width="80px" :rules="rules">
        <el-form-item label="用户名" prop="username" size="mini">
          <el-input v-model="form.username" placeholder="用户名" />
        </el-form-item>
        <el-form-item label="密码" prop="password" size="mini">
          <el-input type="password" v-model="form.password" placeholder="密码" />
        </el-form-item>
        <el-form-item label="性别" prop="sex" size="mini">
          <el-radio-group v-model="form.sex">
            <el-radio :label="1">男</el-radio>
            <el-radio :label="0">女</el-radio>
            <el-radio :label="2">不愿透露</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="手机号" prop="phoneNum" size="mini">
          <el-input v-model="form.phoneNum" placeholder="手机号" />
        </el-form-item>
        <el-form-item label="电子邮箱" prop="email" size="mini">
          <el-input v-model="form.email" placeholder="电子邮箱" />
        </el-form-item>
        <el-form-item label="生日" prop="birth" size="mini">
          <el-date-picker type="date" placeholder="选择日期" v-model="form.birth" style="width: 100%;"/>
        </el-form-item>
        <el-form-item label="地区" prop="location" size="mini">
          <el-input v-model="form.location" placeholder="地区" />
        </el-form-item>
        <el-form-item label="签名" prop="introduction" size="mini">
          <el-input v-model="form.introduction" placeholder="简介" type="textarea"/>
        </el-form-item>
      </el-form>
        <span slot="footer">
          <el-button size="mini" @click='editVisible=false'>取消</el-button>
          <el-button size="mini" @click="editSave">确定</el-button>
        </span>
    </el-dialog>
    <el-dialog title="删除用户" :visible.sync="delVisible" width="300px" center>
      <div align="center">
        <p>删除不可恢复，确定删除该用户吗？</p>
      </div>
      <span slot="footer">
        <el-button size="mini" @click='delVisible=false'>取消</el-button>
        <el-button size="mini" @click="deleteRow">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { Upload } from 'element-ui';
import {getAllConsumer,setConsumer,updateConsumer,deleteConsumer} from '../api/index.js';
import TheAside from '../components/TheAside.vue';
import TheHeader from '../components/TheHeader.vue';
import { mixin } from '../mixins/index.js'
import { template } from 'babel-core';

export default{
  mixins:[mixin],
  data(){
    return{
      centerDialogVisible: false,//添加弹窗是否显示
      editVisible:false,//编辑弹窗是否显示
      delVisible:false,//删除弹窗是否显示
      registerForm:{
        username:'',
        password:'',
        sex:2,
        phoneNum:'',
        email:'',
        birth:'',
        location:'',
        introduction:''
      },
      form:{
        id:'',
        username:'',
        password:'',
        sex:'',
        phoneNum:'',
        email:'',
        birth:'',
        location:'',
        introduction:''
      },
      tableData:[],
      tempData:[],
      select_word:'',
      pageSize:4,//分页每页大小
      currentPage:1,
      idx:-1,//选择项
      mutipleSelection:[],//哪些项被选中
      rules:{
        username:[{
          required: true, message: '请输入用户名', trigger:'blur'}
        ],
        password:[{
          required: true, message: '请输入密码', trigger:'blur'}
        ],
        phoneNum:[{
          required: true, message: '请输入手机号', trigger:'blur'}
        ],
        email:[{
          required: true, message: '请输入电子邮箱', trigger:'blur'},
          {type:'email',message:'请输入正确的电子邮箱',trigger:['blur','change']}
        ],

      },
    }
  },
  watch:{
    select_word(val){
      if(this.select_word==''){
        this.tableData=this.tempData;
      }else{
        this.tableData=[];
        for(let item of this.tempData){
          if(item.username.includes(this.select_word)){
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
    //查询所有用户
    getData(){
      this.tableData=[];
      getAllConsumer()
      .then(res => {
        this.tableData=res;
        this.tempData=res;
      })
      .catch(error=>{
        console.log(error);
      })
    },
    //添加用户
    addConsumer(){
      this.$refs['registerForm'].validate(valid => {
        if(valid){
          let d=this.registerForm.birth;
          let datetime=d.getFullYear()+'-'+(d.getMonth()+1)+'-'+d.getDate();
          let payload={
            username:this.registerForm.username,
            password:this.registerForm.password,
            sex:this.registerForm.sex,
            phoneNum:this.registerForm.phoneNum,
            email:this.registerForm.email,
            birth:d,
            location:this.registerForm.location,
            introduction:this.registerForm.introduction
          }
          
          setConsumer(JSON.stringify(payload))
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
        }
      });
    },
    //更新图片
    uploadUrl(id){
      //console.log(`${this.$store.state.HOST}/consumer/updateConsumerPic?id=${id}`)
      return `${this.$store.state.HOST}/consumer/updateConsumerAvator?id=${id}`
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
        username:row.username,
        password:row.password,
        sex:row.sex,
        phoneNum:row.phoneNum,
        email:row.email,
        birth:row.birth,
        location:row.location,
        introduction:row.introduction
      }
      console.log(row)
    },
    editSave(){
      this.$refs['form'].validate(valid =>{
        if(valid){
          let d=new Date(this.form.birth);
          //let datetime=d.getFullYear()+'-'+(d.getMonth()+1)+'-'+d.getDate();
          let formattedDate = d.toISOString()
          //console.log(formattedDate)
          let payload={
            id:this.form.id,
            username:this.form.username,
            password:this.form.password,
            sex:this.form.sex,
            phoneNum:this.form.phoneNum,
            email:this.form.email,
            birth:d,
            location:this.form.location,
            introduction:this.form.introduction
          }
          
          updateConsumer(JSON.stringify(payload))
          .then(res => {
            if(res.code==1){
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
      });
      

    },
    //删除用户
    deleteRow(){
      deleteConsumer(this.idx)
      .then(res => {
        if(res==ture){
          this.getData();
          this.notify('删除成功', 'success');
        }else{
          this.notify('删除失败', 'error');
        }
      })
      .catch(error=>{
        this.notify('删除失败', 'error');
        console.log(error);
      })
      this.delVisible=false;
    },
    delAll(){
      //console.log(this.multipleSelection);
      const deletePromises = this.multipleSelection.map(item => {
        this.idx = item.id; // 如果你需要idx用于其他操作，可以在这里设置
        return this.deleteRow(item.id); // 传递每个项的id
      });
      Promise.all(deletePromises)
        .then(() => {
          this.getData(); // 重新获取数据
          this.notify('批量删除成功', 'success');
        })
        .catch(error => {
          this.notify('批量删除失败', 'error');
          console.log(error);
        });
    }
  },
  components:{TheAside,TheHeader},
}
</script>
<style scoped>
  .handle-box{
    margin-bottom: 20px;
  }
  .avator-img{
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