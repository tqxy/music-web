<template>
    <div class="table">
        <div class="crumbs">
            <i class="el-icon-tickes">歌曲信息</i>
        </div>
      <div class="container">
        <div class="handle-box">
          <el-button type="primary" size="mini" @click="getData">刷新</el-button>
          <el-button type="primary" size="mini" @click="delAll">批量删除</el-button>
          <el-input v-model="select_word" size="mini" placeholder="请输入歌曲名" class="handle-input mr10"></el-input>
          <el-button type="primary" size="mini" @click="centerDialogVisible=true">添加歌曲</el-button>
          <SongAudio class="song-audio"></SongAudio>
        </div>
      </div>
      <el-table size="mini" border style="width: 100%" height="600px" :data="data" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center"></el-table-column>
        <el-table-column label="歌曲图片" width="110" align="center">
          <template slot-scope="scope">
            <div class="song-img">
              <img :src="getUrl(scope.row.pic)" style="width:100%"/>
            </div>
            <div class="play" @click="setSongUrl(scope.row.url,scope.row.name)">
              <div v-if="toggle==scope.row.name">
                <svg class="icon">
                  <symbol id="icon-zanting"></symbol>
                  <use href="#icon-zanting"></use>
                </svg>
              </div>
              <div v-if="toggle!=scope.row.name">
                <svg class="icon">
                  <symbol id="icon-bofanganniu"></symbol>
                  <use href="#icon-bofanganniu"></use>
                </svg>
              </div>
            </div>
          </template>
        </el-table-column> 
        <el-table-column prop="name" label="歌手-歌名" width="110" align="center"></el-table-column>
        <el-table-column prop="introduction" label="专辑" width="150" align="center"></el-table-column>
        <el-table-column label="歌词" align="center">
        <template slot-scope="scope">
            <ul style="height: 100px;overflow: scroll;">
                <li v-for="(item,index) in formatLyric(scope.row.lyric)" :key="index">
                    {{ item }}
                </li>
            </ul>
        </template>
        </el-table-column>
        <el-table-column label="资源更新" align="center">
          <template slot-scope="scope">
            <el-upload :action=uploadUrl(scope.row.id) :before-upload="beforeAvatarUpload" :on-success="handleAvatorSuccess">
              <el-button size="mini">更新图片</el-button>
            </el-upload>
            <el-upload :action=updateSongUrl(scope.row.id) :before-upload="beforeSongUpload" :on-success="handleSongSuccess">
              <el-button size="mini">更新歌曲</el-button>
            </el-upload>
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
      <el-dialog title="添加歌曲" :visible.sync="centerDialogVisible" width="400px" center>
        <el-form :model="registerForm" ref="registerForm" label-width="80px" action="" id="tf">
          <div>
                <label>歌曲名</label>
                <el-input type="text" name="name" placeholder="歌曲名" />
          </div>
          <div>
                <label>专辑</label>
                <el-input type="text" name="introduction" placeholder="歌曲名" />
          </div>
          <div>
                <label>歌词</label>
                <el-input type="textarea" name="lyric" placeholder="歌曲名" />
          </div>
          <div>
                <label>歌曲上传</label>
                <el-input type="file" name="file" />
          </div>
        </el-form>
          <span slot="footer">
            <el-button size="mini" @click="centerDialogVisible=false">取消</el-button>
            <el-button size="mini" @click="addSong">确定</el-button>
          </span>
      </el-dialog>
      <el-dialog title="修改歌曲" :visible.sync="editVisible" width="400px" center>
        <el-form :model="form" ref="form" label-width="80px">
          <el-form-item label="歌手-歌名" prop="name" size="mini">
            <el-input v-model="form.name" placeholder="歌手-歌名" />
          </el-form-item>

          <el-form-item label="专辑" prop="introduction" size="mini">
            <el-input v-model="form.introduction" placeholder="专辑" />
          </el-form-item>
          <el-form-item label="歌词" prop="lyric" size="mini">
            <el-input v-model="form.lyric" placeholder="歌词" type="textarea"/>
          </el-form-item>
        </el-form>
          <span slot="footer">
            <el-button size="mini" @click='editVisible=false'>取消</el-button>
            <el-button size="mini" @click="editSave">确定</el-button>
          </span>
      </el-dialog>
      <el-dialog title="删除歌曲" :visible.sync="delVisible" width="400px" center>
        <div align="center">
          <p>删除不可恢复，确定删除该歌手吗？</p>
        </div>
        <span slot="footer">
          <el-button size="mini" @click='delVisible=false'>取消</el-button>
          <el-button size="mini" @click="deleteRow">确定</el-button>
        </span>
      </el-dialog>
    </div>
  </template>
  
  <script>
  import { mapGetters } from 'vuex'
  import '@/assets/js/iconfont.js'
  import { Upload } from 'element-ui';
  import { template } from 'babel-core';
  import { songOfSingerId,updateSong,deleteSong } from '../api/index.js';
  import TheAside from '../components/TheAside.vue';
  import TheHeader from '../components/TheHeader.vue';
  import { mixin } from '../mixins/index.js'
  import SongAudio from '../components/SongAudio.vue'


  export default{
    mixins:[mixin],
    data(){
      return{
        singerId:'',
        singerName:'',
        centerDialogVisible: false,//添加弹窗是否显示
        editVisible:false,//编辑弹窗是否显示
        delVisible:false,//删除弹窗是否显示
        registerForm:{
          name:'',
          singerName:'',
          introduction:'',
          lyric:''
        },
        form:{
          id:'',
          name:'',
          singerName:'',
          introduction:'',
          lyric:''
        },
        tableData:[],
        tempData:[],
        select_word:'',
        pageSize:4,//分页每页大小
        currentPage:1,
        idx:-1,//选择项
        mutipleSelection:[],//哪些项被选中
        toggle:false
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
        this.singerId=this.$route.query.singerId;
        this.singerName=this.$route.query.singerName;
        console.log('Singer ID:', this.singerId);
        console.log('Singer Name:', this.singerName);
        this.getData();
    },
    destroyed(){
      this.$store.commit('setIsPlay',false);
    },
    methods:{
      //查询所有歌手
      getData(){
        this.tableData=[];
        this.tempData=[];
        songOfSingerId(this.singerId).then(res=>{
          this.tempData=res;
          this.tableData=res;
          this.currentPage=1;
        })
      },
      //添加歌曲
      addSong(){
        let _this=this;
        var form=new FormData(document.getElementById('tf'));
        form.append('singerId',this.singerId);
        form.set('name',this.singerName+'-'+form.get('name'));
        if(!form.get('lyric')){
            form.set('lyric','暂无歌词');
        }
        var req=new XMLHttpRequest();
        req.onreadystatechange=function(){
            //req.readyState==4 获取到返回的完整数据
            if(req.readyState==4&&req.status==200){
                let res=JSON.parse(req.response);
                if(res.code==1){
                    _this.getData();
                    _this.registerForm={};
                    _this.notify(
                      '添加成功',
                      'success'
                    );
                }else{
                    _this.notify(
                      '添加失败',
                      'error'
                    );
                }
            }
        }
        req.open('POST',`${this.$store.state.HOST}/song/add`,true);
        req.send(form);
        this.centerDialogVisible=false;
      },
      //更新图片
      uploadUrl(id){
        //console.log(`${this.$store.state.HOST}/singer/updateSingerPic?id=${id}`)
        return `${this.$store.state.HOST}/song/updateSongPic?id=${id}`
      },
      //获取当前页
      handleCurrentChange(val){
        this.currentPage=val;
      },
      //弹出编辑页面
      handleEdit(row){
        console.log(row)
        this.editVisible=true;
        this.form={
          id:row.id,
          singerId:row.singerId,
          url:row.url,
          pic:row.pic,
          createTime:row.createTime,
          name:row.name,
          introduction:row.introduction,
          lyric:row.lyric
        }
        console.log(this.form)
      },
      editSave(){
        let params=new URLSearchParams();
        let payload=this.form;
        
        updateSong(JSON.stringify(payload))
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
      //删除歌手
      deleteRow(){
        deleteSong(this.idx)
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
      formatLyric(lyric){
        let lines=lyric.split('\n');
        let pattern = /\[\d{2}:\d{2}\.{\d{3}|\d{2}}\]/g;
        let result=[];
        for(let item of lines){
            let value=item.replace(pattern,'').trim();
            result.push(value);
        }
        console.log(result)
        return result;
      },
      beforeSongUpload(file){
        var testMsg=file.name.substring(file.name.lastIndexOf('.')+1);
        //console.log(testMsg)
        const extension = (testMsg=='mp3'||'flac')?true:false;
        if(!extension){
          this.$message.error('上传歌曲只能是 MP3 / flac 格式!');
          return false;
        }
        return true;
      },
      handleSongSuccess(res,file){
        if(res.code==1){
          this.getData();
          this.notify(
            '上传成功',
            'success'
          );
        }else{
          this.notify(
            '上传失败',
            'error'
          );
        }
      },
      updateSongUrl(id){
        return `${this.$store.state.HOST}/song/updateSongUrl?id=${id}`
      },
      //切换播放歌曲
      setSongUrl(url,name){
        this.toggle=name;
        this.$store.commit('setUrl',this.$store.state.HOST+url);
        console.log(this.$store.state.HOST+url)
        if(this.isPlay){
          this.$store.commit('setIsPlay',false);
        }else{
          this.$store.commit('setIsPlay',true);
        }
      }
    },
    components:{TheAside,TheHeader,SongAudio},
  }
  </script>
  <style scoped>
    .handle-box{
      margin-bottom: 20px;
    }
    .song-img{
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
    .play{
      position:absolute;
      z-index:100;
      width:80px;
      height: 80px;
      display: flex;
      align-items: center;
      justify-content: center;
      cursor: pointer;
      top:18px;
      left:15px;
    }
    .icon{
      width:2em;
      height:2em;
      color:red;
      fill:currentColor;
      overflow: hidden;
    }
  </style>