export const mixin = {
  methods: {
    // 提示信息
    notify (title, type) {
      this.$notify({
        title: title,
        type: type
      })
    },
    // 根据相对地址获取绝对地址
    getUrl(url){
      return `${this.$store.state.HOST}/${url}`
    },
    attachBirth(birth){
      
      return birth.slice(0,10)
    },
    //上传图片之前的校验
    beforeAvatorUpload(file){
      const isJPG = (file.type === 'image/jpg')||(file.type === 'image/png')
      const isLt2M = (file.size / 1024 / 1024) < 2
      if(!isJPG){
        this.$message.error('上传头像图片只能是 JPG 格式或png格式!')
        return false;
      }
      if(!isLt2M){
        this.$message.error('上传头像图片大小不能超过 2MB!')
      }
    },
    //上传图片成功之后要做的工作
    handleAvatarSuccess(res,file){
      let _this=this;
      if(res.code==1){
        _this.getData();
        _this.notify({
          title:'上传成功',
          type:'success'
        });
      }else{
          _this.notify({
          title:'上传失败',
          type:'error'
        });
      };
    },
  }
}
