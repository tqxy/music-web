<template>
  <div class="table">
    <div class="container">
      <div class="handle-box">
        <el-button type="primary" size="mini" @click="getData">刷新</el-button>
        <el-button type="primary" size="mini" @click="delAll">批量删除</el-button>
        <el-input v-model="select_word" size="mini" placeholder="筛选关键字" class="handle-input mr10"></el-input>
        <el-button type="primary" size="mini" @click="centerDialogVisible=true">添加歌单</el-button>
      </div>
    </div>
    <el-table size="mini" border style="width: 100%" height="600px" :data="data" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"></el-table-column>
      <el-table-column label="歌单图片" width="110" align="center">
        <template slot-scope="scope">
          <div class="songList-img">
            <img :src="getUrl(scope.row.pic)" style="width:100%"/>

          </div>
          <el-upload :action=uploadUrl(scope.row.id) :before-upload="beforeAvatarUpload" :on-success="handleAvatorSuccess">
            <el-button size="mini">更新图片</el-button>
          </el-upload>
        </template>
      </el-table-column> 
      <el-table-column prop="title" label="标题" width="110" align="center"></el-table-column>
      <el-table-column label="简介" width="500" align="center">
        <template slot-scope="scope">
          <p style="height: 100px;overflow: scroll;">{{ scope.row.introduction }}</p>
        </template>
      </el-table-column>
      <el-table-column prop="style" label="风格" width="110" align="center"></el-table-column>
      <el-table-column label="歌曲管理" width="110" align="center">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" icon="el-icon-edit" @click="songEdit(scope.row.id)">歌曲管理</el-button>
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
    <el-dialog title="添加歌单" :visible.sync="centerDialogVisible" width="400px" center>
      <el-form :model="registerForm" ref="registerForm" label-width="80px">
        <el-form-item label="标题" prop="title" size="mini">
          <el-input v-model="registerForm.title" placeholder="标题" />
        </el-form-item>
        <el-form-item label="简介" prop="introduction" size="mini">
          <el-input v-model="registerForm.introduction" placeholder="简介" type="textarea"/>
        </el-form-item>
        <el-form-item prop="style" label="风格" size="mini">
          <el-input v-model="registerForm.style" placeholder="风格" />
        </el-form-item>
      </el-form>
        <span slot="footer">
          <el-button size="mini" @click="centerDialogVisible=false">取消</el-button>
          <el-button size="mini" @click="addSongList">确定</el-button>
        </span>
    </el-dialog>
    <el-dialog title="修改歌单" :visible.sync="editVisible" width="400px" center>
      <el-form :model="form" ref="form" label-width="80px">
        <el-form-item label="标题" prop="title" size="mini">
          <el-input v-model="form.title" placeholder="标题" />
        </el-form-item>
        <el-form-item label="简介" prop="introduction" size="mini">
          <el-input v-model="form.introduction" placeholder="简介" type="textarea"/>
        </el-form-item>
        <el-form-item prop="style" label="风格" size="mini">
          <el-input v-model="form.style" placeholder="风格" />
        </el-form-item>
      </el-form>
        <span slot="footer">
          <el-button size="mini" @click='editVisible=false'>取消</el-button>
          <el-button size="mini" @click="editSave">确定</el-button>
        </span>
    </el-dialog>
    <el-dialog title="删除歌单" :visible.sync="delVisible" width="400px" center>
      <div align="center">
        <p>删除不可恢复，确定删除该歌单吗？</p>
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
import { setSongList,updateSongList,getAllSongList,deleteSongList} from '../api/index.js';
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
        title:'',
        introduction:'',
        style:''
      },
      form:{
        id:'',
        pic:'',
        title:'',
        introduction:'',
        style:''
      },
      tableData:[],
      tempData:[],
      select_word:'',
      pageSize:4,//分页每页大小
      currentPage:1,
      idx:-1,//选择项
      mutipleSelection:[],//哪些项被选中
    }
  },
  watch:{
    select_word(val){
      if(this.select_word==''){
        this.tableData=this.tempData;
      }else{
        this.tableData=[];
        for(let item of this.tempData){
          if(item.title.includes(this.select_word)){
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
    //查询所有歌单
    getData(){
      this.tableData=[];
      this.tempData=[];
      getAllSongList()
      .then(res => {
        this.tableData=res;
        this.tempData=res;
      })
      .catch(error=>{
        console.log(error);
      })
    },
    //添加歌单
    addSongList(){
      let params=new URLSearchParams();
      params.append('title',this.registerForm.title);
      params.append('pic','/img/songListPic/hhh.png');
      params.append('introduction',this.registerForm.introduction);
      params.append('style',this.registerForm.style);
      setSongList(params)
      .then(res => {
        if(res==true){
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
      return `${this.$store.state.HOST}/songList/updateSongListPic?id=${id}`
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
        title:row.title,
        introduction:row.introduction,
        pic:row.pic,
        style:row.style
      }
      console.log(row)
    },
    editSave(){
      let payload={
        id:this.form.id,
        title:this.form.title,
        pic:this.form.pic,
        introduction:this.form.introduction,
        style:this.form.style
      }
      
      updateSongList(JSON.stringify(payload))
      .then(res => {
        if(res==true){
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
    },
    //删除歌单
    deleteRow(){
      deleteSongList(this.idx)
      .then(res => {
        if(res==true){
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
    },
    //转向歌曲管理页面
    songEdit(id,name){
      this.$router.push({
        path:'/ListSong',
        query:{
          id
        }
      })
    }
  },
  components:{TheAside,TheHeader},
}
</script>
<style scoped>
  .handle-box{
    margin-bottom: 20px;
  }
  .songList-img{
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