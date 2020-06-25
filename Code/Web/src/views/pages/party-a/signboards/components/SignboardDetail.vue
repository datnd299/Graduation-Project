<template>
  <div>
    <v-card class="mx-auto" style="padding:20px" outlined>
      <v-list-item-title class="headline mb-1">Thông tin biển quảng cáo</v-list-item-title>
      <br />
      <br />
      <v-form ref="form">
        <v-text-field outlined v-model="form.name" :counter="150" label="Tên biển" required></v-text-field>

        <v-text-field outlined type="text" v-model="form.code" label="Mã biển" required></v-text-field>
        <v-text-field outlined type="text" v-model="form.note" label="Ghi chú" required></v-text-field>
        <v-row>
        <v-col v-for="(img, index) in form.imgs" :key="index" xs="12" sm="6" md="4">
          <v-card class="mx-auto" max-width="300" style="position:relative">
            <v-img  class="white--text align-end" height="150" :src="getUrlImage(img)">
        
            </v-img>
            <v-btn @click="removeImg(img)" style="position:absolute;top: 0;
    right: 0;" icon color="red">
              <v-icon>fas fa-times-circle</v-icon>
              </v-btn>
            
          </v-card>
        </v-col>
      </v-row>
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
          ><template v-if="id">
            Sửa thông tin
          </template>
          <template v-else>
            Thêm biển quảng cáo
          </template> </v-btn>
        </div>
      </v-form>
    </v-card>
  </div>
</template>
<script>
import { SET_BREADCRUMB } from "@/store/breadcrumbs.module";
import { createNew,getSignboardByID } from "@/api/party-a/signboard";
import { upload } from "@/api/file/file";
import {BASE_API} from '@/utils/base'
export default {
  data() {
    return {
      id:null,
      form: {
        name: "",
        code: "",
        note:"",
        files:null,
        imgs:[]
      },
      uploading: false,
      loading: false
    };
  },
  created() {
    this.id = this.$route.params.id;
    this.fetchInfo();
    
    this.$store.dispatch(SET_BREADCRUMB, [
      { title: "Bên thuê", route: "/party-a" },
      { title: "Biển quảng cáo", route: "/party-a/signboards" },
      { title: "Tạo mới", route: "/party-a/signboards/new-signboard" }
    ]);
  },
  methods: {
    removeImg(img){
      this.form.imgs.splice(this.form.imgs.indexOf(img), 1);
    },
    getUrlImage(img){
      return BASE_API+'file/get/'+img
    },
    fetchInfo(){
      if(this.id){
        getSignboardByID({
        id:this.id
      }).then(res=>{
        this.form.name = res.data.name;
        this.form.code = res.data.code;
        this.form.note = res.data.note;
        this.form.imgs = res.data.imgs;
        console.log(res);
        
      })
      }
      
    },
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
