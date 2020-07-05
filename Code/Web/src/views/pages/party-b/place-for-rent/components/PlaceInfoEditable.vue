<template>
  <div>
    <v-dialog v-model="mapModalVisible" persistent max-width="800">
      <v-card style="padding:10px">
        <map-marker :location="form" @picked="onLocationPicked" @canceled="onCanceled"></map-marker>
      </v-card>
    </v-dialog>
    <v-card class="mx-auto" style="padding:20px" outlined>
      <v-list-item-title class="headline mb-1">Nhập thông tin địa điểm</v-list-item-title>
      <br />
      <br />
      <v-form ref="form" >
        <v-text-field outlined v-model="form.name" :counter="150" label="Tên địa điểm" required></v-text-field>
        <div>
          <v-text-field outlined v-model="form.address" label="Địa chỉ" required>
            <v-btn
              style="height: 56px;
    margin-top: -17px;"
              slot="append-outer"
              color="purple"
              dark
              extra-large
              @click="mapModalVisible=true"
            >
              <i style="font-size: 24px;" class="fas fa-map-marker-alt"></i>
            </v-btn>
          </v-text-field>
          <v-row style="margin-top:-35px">
            <v-col xs="12" sm="6">
              <v-text-field
                dense
                outlined
                type="number"
                v-model="form.location.lat"
                label="Lat"
                required
              ></v-text-field>
            </v-col>
            <v-col xs="12" sm="6">
              <v-text-field
                dense
                outlined
                type="number"
                v-model="form.location.lng"
                label="Lng"
                required
              ></v-text-field>
            </v-col>
          </v-row>
        </div>

        <v-text-field outlined type="number"  v-model="form.price" label="Chi phí đề nghị" required>
         
            <v-select style="margin-top: -17px;"
          :items="timeUnits"
          v-model="form.timeUnit"
          label="Đơn vị"
          outlined
           slot="append-outer" 
        ></v-select>
     
        </v-text-field>
        <div style="width:100%">
          <v-file-input outlined accept="image/*" chips multiple v-model="form.files" label="Ảnh Địa điểm">
          
          </v-file-input>
          <div v-if="form.files" class="image-input-preview">
            <img v-for="(item, index) in form.files" :key="index" :src="getUrl(item)" alt />
          </div>
          <v-progress-linear v-if="uploading"
      color="teal"
      buffer-value="0"
      :value="uploadVal"
      stream
    ></v-progress-linear>
        </div>
        <div style="text-align:right">
          <v-btn style="margin-top:20px" color="indigo" dark large @click="createPlace" :loading="loading">Thêm địa điểm</v-btn>
        </div>
      </v-form>
    </v-card>
  </div>
</template>
<script>
import { SET_BREADCRUMB } from "@/store/breadcrumbs.module";
import MapMarker from "@/views/partials/content/map/MapMarker.vue";
import {upload} from '@/api/file/file'
import {createPlace} from '@/api/party-b/place-for-rent.js'
export default {
  created() {
    this.$store.dispatch(SET_BREADCRUMB, [
      { title: "Đối tác", route: "" },
      { title: "Thêm Địa điểm", route: "/party-b/new-place-for-rent" }
    ]);
  },
  components: {
    MapMarker
  },
  methods: {
    createPlace(){
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
        req.time_unit = this.form.timeUnit;
        createPlace(req).then(res2=>{
            console.log(res2);
            this.loading =false;
            
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
    getUrl(file) {
      return URL.createObjectURL(file);
    },
    onLocationPicked(lo) {
      this.form.address = lo.name;
      this.form.location.lat = lo.lat;
      this.form.location.lng = lo.lng;
    
      this.mapModalVisible = false;
    },
    onCanceled() {
      this.mapModalVisible = false;
    }
  },
  data() {
    return {
      mapModalVisible: false,
      uploading:false,
      loading:false,
      uploadVal:0,
      timeUnits:[
        {
          text:'Ngày',
          value:'day'
        },
        {
          text:'Tuần',
          value:'week'
        },
        {
          text:'Tháng',
          value:'moth'
        },
        {
          text:'Năm',
          value:'year'
        }
      ],
      form: {
        name: "",
        address: "",
        location: {
          lat: null,
          lng: null
        },
        timeUnit:null,
        price: null,
        files: null
      }
    };
  }
};
</script>