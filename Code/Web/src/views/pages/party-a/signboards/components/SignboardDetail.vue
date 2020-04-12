<template>
  <div>
    <v-card class="mx-auto" style="padding:20px" outlined>
      <v-list-item-title class="headline mb-1">Nhập thông tin điểm treo</v-list-item-title>
      <br />
      <br />
      <v-form ref="form">
        <v-text-field outlined v-model="form.name" :counter="150" label="Tên biển" required></v-text-field>

        <v-text-field outlined type="text" v-model="form.code" label="Mã biển" required></v-text-field>
        <v-text-field outlined type="text" v-model="form.note" label="Ghi chú" required></v-text-field>
        <div>
          <v-file-input outlined accept="image/*" chips multiple v-model="form.files" label="Ảnh"></v-file-input>
          <div v-if="form.files" class="image-input-preview">
            <img v-for="(item, index) in form.files" :key="index" :src="getUrl(item)" alt />
          </div>
          <v-progress-linear
            v-if="uploading"
            color="teal"
            buffer-value="0"
            :value="uploadVal"
            stream
          ></v-progress-linear>
        </div>
        <div style="text-align:right">
          <v-btn
            style="margin-top:20px"
            color="indigo"
            dark
            large
            @click="createSignboard"
            :loading="loading"
          >Thêm địa điểm</v-btn>
        </div>
      </v-form>
    </v-card>
  </div>
</template>
<script>
import { SET_BREADCRUMB } from "@/store/breadcrumbs.module";
import { createNew } from "@/api/party-a/signboard";
import { upload } from "@/api/file/file";
export default {
  data() {
    return {
      form: {
        name: "",
        code: "",
        note:"",
        files:null
      },
      uploading: false,
      loading: false
    };
  },
  created() {
    this.$store.dispatch(SET_BREADCRUMB, [
      { title: "Bên thuê", route: "/party-a" },
      { title: "Biển quảng cáo", route: "/party-a/signboards" },
      { title: "Tạo mới", route: "/party-a/signboards/new-signboard" }
    ]);
  },
  methods: {
    getUrl(file) {
      return URL.createObjectURL(file);
    },
    createSignboard(){
      var formData = new FormData();
      for(let i in this.form.files)
      {
        formData.append(i,this.form.files[i])
      }
        
      this.uploading = true;
      this.loading =true;
      upload(formData,this.uploadProgress).then(res1=>{
        this.uploading = false;
        var fileLst = res1.data;
        var imgs=[]
        for(let i in fileLst){
          imgs.push(fileLst[i]);
        }
        var req = this.form;
        req.imgs = imgs;
        createNew(req).then(res2=>{
            console.log(res2);
            this.loading =false;
            this.$toast.open({
      message: 'Tạo biển thành công',
      type: 'success',
      position:'top-right',
      duration:3000
      // all other options
  });
            
        }).catch(()=>{
        this.uploading = false;
        this.loading =false;
      })
        
      }).catch(()=>{
        this.uploading = false;
        this.loading =false;
      })
    },
    uploadProgress(e){
      this.uploadVal = e.loaded/e.total*100;
    },
  }
};
</script>
