<template>
  <div>
    <el-row :gutter="20" class="mab20">
      <el-col :span="6">
        <el-card>

        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import TheAside from '../components/TheAside.vue';
import TheHeader from '../components/TheHeader.vue';
export default{
    components:{TheAside,TheHeader},
}
</script>

<style>

</style>