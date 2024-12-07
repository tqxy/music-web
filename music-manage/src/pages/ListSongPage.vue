<template>
    <div class="table">
        <div class="crumbs">
            <i class="el-icon-tickets">歌单歌曲信息</i>
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
        <el-table-column label="操作" width="150" align="center">
          <template slot-scope="scope">
            <el-button type="danger" size="mini" icon="el-icon-delete" @click="handleDelete(scope.row.listSongId)">删除</el-button>
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
          <el-form-item label="歌手名字" prop="singerName" size="mini">
            <el-input v-model="registerForm.singerName" placeholder="歌手名字" />
          </el-form-item>
          <el-form-item label="歌曲名字" prop="songName" size="mini">
            <el-input v-model="registerForm.songName" placeholder="歌曲名字" />
          </el-form-item>
        </el-form>
        </el-form>
          <span slot="footer">
            <el-button size="mini" @click="centerDialogVisible=false">取消</el-button>
            <el-button size="mini" @click="getSongId">确定</el-button>
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
  import '@/assets/js/iconfont.js'
  import { getSongIdOfSongList,listSongAdd,songOfId, songOfSongName,deleteListSong } from '../api/index.js';
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
          singerName:'',
          songName:''
        },
        tableData:[],
        tempData:[],
        select_word:'',
        pageSize:4,//分页每页大小
        currentPage:1,
        idx:-1,//选择项
        mutipleSelection:[],//哪些项被选中
        toggle:false,
        songListId:'',
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
        this.songListId=this.$route.query.id;
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
        getSongIdOfSongList(this.songListId).then(res=>{
          for(let item of res){
            this.getSong(item.songId,item.id);
            //console.log(item)
          }
          
          this.currentPage=1;
        })
        console.log(this.tableData)
      },
      //根据歌曲ID查询歌曲列表，放到tableData中
      getSong(songId,listSongId){
        let _this=this;
        songOfId(songId).then(res=>{
          res.listSongId=listSongId;
          //console.log(res);
          _this.tableData.push(res);
          _this.tempData.push(res);
        })
      },
      //添加歌曲前的准备，获取到歌曲ID
      getSongId(){
        let _this=this;
        var songOfName=_this.registerForm.singerName+'-'+_this.registerForm.songName;
        songOfSongName(songOfName)
        .then(res=>{
          this.addSong(res[0].id)
        })
      },
      //添加歌曲
      addSong(songId){
        let params=new URLSearchParams();
        params.append('songId',songId);
        params.append('songListId',this.songListId);
        
        listSongAdd(params)
        .then(res=>{
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
      //获取当前页
      handleCurrentChange(val){
        this.currentPage=val;
      },
      //删除歌手
      deleteRow(){
        deleteListSong(this.idx)
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
        console.log(this.multipleSelection);
        const deletePromises = this.multipleSelection.map(item => {
          this.idx = item.listSongId; // 如果你需要idx用于其他操作，可以在这里设置
          return this.deleteRow(item.listSongId); // 传递每个项的id
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