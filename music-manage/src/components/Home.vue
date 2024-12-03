<template>
  <div>
    <the-header></the-header>
    <the-aside></the-aside>
    <div class="content-box" :class="{ 'content-collapse': Collapse }"><router-view></router-view></div>
  </div>
</template>

<script>
import TheHeader from './TheHeader.vue'
import TheAside from './TheAside.vue'
import bus from '../assets/js/bus'

export default {
  components:{
    TheHeader,
    TheAside
  },
  data(){
    return{
      Collapse:false
    }
  },
  created(){
    bus.$on('Collapse',(msg)=>{
      this.Collapse = msg
    })
  }
}
</script>

<style>
</style>
