<template>
  <div>
    <el-row :gutter="20" class="mgb20">
      <el-col :span="6">
        <el-card>
          <div class="grid-content">
            <div class="grid-content-center">
              <div class="grid-num">{{consumerCount}}</div>
              <div>用户总数</div>
            </div>
        
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div class="grid-content">
            <div class="grid-content-center">
              <div class="grid-num">{{songCount}}</div>
              <div>歌曲总数</div>
            </div>
        
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div class="grid-content">
            <div class="grid-content-center">
              <div class="grid-num">{{singerCount}}</div>
              <div>歌手数量</div>
            </div>
        
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div class="grid-content">
            <div class="grid-content-center">
              <div class="grid-num">{{songListCount}}</div>
              <div>歌单数量</div>
            </div>
        
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20" class="mgb20">
      <el-col :span="12">
        <el-card>
          <h3>用户性别比例</h3>
          <div style="background-color: white;">
            <ve-pie :data="consumerSex" :settings="{colors:['#f56c6c','#e6a23c','#6f7ad3']}"></ve-pie>
          </div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <h3>歌单风格比对</h3>
          <div style="background-color: white;">
            <ve-histogram :data="songListStyle" :settings="{colors:['#f56c6c','#e6a23c','#6f7ad3']}"></ve-histogram>
          </div>
        </el-card>
      </el-col>
      
    </el-row>
  </div>
</template>

<script>
import { getAllConsumer,getAllSinger,getAllSongList,getAllSong } from '../api/index.js';
export default{
    data(){
      return{
        consumerCount:0,
        songCount:0,
        singerCount:0,
        songListCount:0,
        consumer:[],
        consumerSex:{
          columns:['性别','总数'],
          rows:[

              {'性别':'未知','总数':0},
              {'性别':'男','总数':0},
              {'性别':'女','总数':0},
            
          ]
        },
        songListStyle:{
          columns:['风格','总数'],
          rows:[

              {'风格':'华语','总数':0},
              {'风格':'粤语','总数':0},
              {'风格':'欧美','总数':0},
              {'风格':'日韩','总数':0},
              {'风格':'BGM','总数':0},
              {'风格':'轻音乐','总数':0},
              {'风格':'乐器','总数':0},
            
          ]
        }
      }
      
    },
    created(){
      
    },
    mounted(){
      this.getData();
    },
    methods:{
      getData(){
        let _this=this;
        getAllConsumer()
        .then(res=>{
          this.consumerCount=res.length;
          this.consumer=res;
          this.consumerSex.rows[1]['总数']=_this.setSex(1,this.consumer);
          this.consumerSex.rows[2]['总数']=_this.setSex(0,this.consumer);
          this.consumerSex.rows[0]['总数']=_this.setSex(2,this.consumer);
        }),
        
        getAllSinger()
        .then(res=>{
          this.singerCount=res.length;
        })
        getAllSongList()
        .then(res=>{
          this.songListCount=res.length;
          for(let item of res){
            this.setStyle(item)
          }
        })
        getAllSong()
        .then(res=>{
          this.songCount=res.length;
          
        })
      },
      setSex(sex,consumer){
        let count=0;
        for(let item of consumer){
          if(item.sex==sex){
            count++;
          }
          //console.log(item);
        }
        return count;
      },
      setStyle(songList){
        console.log(songList);
        for(let item of this.songListStyle.rows){
          console.log(songList.style);
          console.log(item['风格']);
          if(songList.style==item['风格']){
            item['总数']++;
            return;
          }
        }
      }
    }
}
</script>

<style>
.grid-content{
  display: flex;
  align-items: center;
  height:50px;

}
.grid-content-center{
  flex:1;
  text-align: center;
  font-size: 14px;
  color:rgb(31, 29, 29);
}
.grid-num{
  font-size: 18px;
  font-weight: bold;
}
</style>