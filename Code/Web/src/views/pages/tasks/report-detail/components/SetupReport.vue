<template>
  <div>
    <v-card class="mx-auto" style="padding:20px" outlined v-if="task.setup_task_report">
      <v-list-item-title class="headline mb-1">Báo cáo nhiệm vụ</v-list-item-title>
      <v-row>
        <v-col xs="12" sm="6">
          <v-text-field readonly dense outlined v-model="task.setup_task_report.new_lat_lng.lat"  type="number" label="Vị trí: Lat" required></v-text-field>
        </v-col>
        <v-col xs="12" sm="6">
          <v-text-field readonly dense outlined v-model="task.setup_task_report.new_lat_lng.lng" type="number" label="Vị trí: Lng" required>
            <v-btn style="
    margin-top: -4px;" slot="append-outer" color="purple" dark>
              <i style="font-size: 18px;" class="fas fa-map-marked-alt"></i>
            </v-btn>
          </v-text-field>
        </v-col>
      </v-row>
      <!-- <v-checkbox
        style="margin-top:-20px"
        label="Cần căn chỉnh lại vị trí"
        color="indigo darken-3"
       v-model="report.editLocation"

        hide-details
      ></v-checkbox> -->
      <!-- <v-row v-if="report.editLocation">
        <v-col xs="12" sm="6">
          <v-text-field readonly dense outlined v-model="task.setup_task.place_rental.lat_lng.lat" type="number" label="Vị trí: Lat" required></v-text-field>
        </v-col>
        <v-col xs="12" sm="6">
          <v-text-field readonly dense outlined v-model="task.setup_task.place_rental.lat_lng.lng" type="number" label="Vị trí: Lng" required>
            <v-btn style="
    margin-top: -4px;" slot="append-outer" color="purple" dark>
              <i style="font-size: 18px;" class="fas fa-map-marker-alt"></i>
            </v-btn>
          </v-text-field>
        </v-col>
        </v-row> -->
        <!-- <div style="width:100%" v-for="(item, index) in report.signboards" :key="index">
          <v-file-input
            outlined
            accept="image/*"
            chips
            multiple
            v-model="item.files"
            :label="'Ảnh biển: '+item.sb.name"
          >
            <v-btn
              style="height: 56px;
    margin-top: -17px;"
              slot="append-outer"
              color="blue"
              dark
              extra-large
            >
              <i style="font-size: 24px;" class="fa fa-camera"></i>
            </v-btn>
          </v-file-input>
          <div v-if="item.files" class="image-input-preview">
            <img v-for="(item, index) in report.files" :key="index" :src="getUrl(item)" alt />
          </div>
          <v-progress-linear
            v-if="uploading"
            color="teal"
            buffer-value="0"
            :value="uploadVal"
            stream
          ></v-progress-linear>
        </div> -->
        <div v-for="(sb, index) in task.setup_task_report.signboards" :key="index" style="    border: 1px dashed #2c3e50;
    border-radius: 5px;
    padding: 10px;
    margin-bottom: 20px;">
         <div style="font-weight: 700; margin-left:20px;
    color: #34495e;">Biển quảng cáo: {{sb.s_id.name}}</div>
          <v-row>
        <v-col v-for="(img, index) in sb.imgs" :key="index" xs="12" sm="6" md="4">
          <v-card class="mx-auto" max-width="300">
            <v-img class="white--text align-end" height="150" :src="getUrl(img.name)">
              <!-- <v-card-title style="color:black"></v-card-title> -->
            </v-img>
            <div style="margin-left:5px">{{img.device}} - Cách mốc 50m <i style="color:purple" class="fas fa-map-marked-alt"></i></div>
          </v-card>
        </v-col>
      </v-row>
        </div>
        
        <v-text-field v-model="task.setup_task_report.note" outlined label="Báo cáo"></v-text-field>
        <div 
        >
        <v-btn
              color="red"
              dark
              class="ma-2"
              large
            
            >
              <v-icon small="" left>fas fa-ban</v-icon>&nbsp;  Không duyệt nhiệm vụ
            </v-btn>
    
        <v-btn style="float:right"
              color="teal"
              dark
              class="ma-2"
              large
            
            >
              <v-icon small="" left>fas fa-check-square</v-icon>&nbsp;  Duyệt nhiệm vụ
            </v-btn>
        </div>
    </v-card>
  </div>
</template>
<script>
import {BASE_API} from '@/utils/base'
export default {
  props: {
    task: {
      type: Object,
      default() {
        return {};
      },
      required:true
    }
  },
  methods:{
    getUrl(img){
      return BASE_API+'file/get/'+img
    },
  },
  data() {
    return {
      uploading: false,
      uploadVal: false,
      report: {
        editLocation: false,
        newLat: 0,
        newLng: 0,
        signboards: [],
        note: ""
      }
    };
  },
  created(){
    this.report.newLat = this.task.setup_task.place_rental.lat_lng.lat;
    this.report.newLng = this.task.setup_task.place_rental.lat_lng.lng;
    this.task.setup_task.signboards.forEach(e => {
      this.report.signboards.push({
        sb:e,
        files:[]
      })
    });
  }
};
</script>