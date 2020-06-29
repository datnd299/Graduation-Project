<template>
  <div>
    <v-card class="mx-auto" style="padding:20px" outlined v-if="task.setup_task_report">
      <v-list-item-title class="headline mb-1">Báo cáo nhiệm vụ</v-list-item-title>
      <v-row>
        <v-col xs="12" sm="6">
          <v-text-field
            readonly
            dense
            outlined
            v-model="task.setup_task_report.new_lat_lng.lat"
            type="number"
            label="Vị trí: Lat"
            required
          ></v-text-field>
        </v-col>
        <v-col xs="12" sm="6">
          <v-text-field
            readonly
            dense
            outlined
            v-model="task.setup_task_report.new_lat_lng.lng"
            type="number"
            label="Vị trí: Lng"
            required
          >
          
            <v-btn  style="
    margin-top: -4px;" slot="append-outer" color="purple" dark>
            <a style="color:white" target="_blank" :href="'https://www.google.com/maps/search/?api=1&query='+task.setup_task_report.new_lat_lng.lat+','+task.setup_task_report.new_lat_lng.lng">
<i style="font-size: 18px;" class="fas fa-map-marked-alt"></i>
            </a>
              
            </v-btn>
          </v-text-field>
        </v-col>
      </v-row>
      <div
        v-for="(sb, index) in task.setup_task_report.signboards"
        :key="index"
        style="    border: 1px dashed #2c3e50;
    border-radius: 5px;
    padding: 10px;
    margin-bottom: 20px;"
      >
        <div
          style="font-weight: 700; margin-left:20px;
    color: #34495e;"
        >Biển quảng cáo: {{sb.s_id.name}}</div>
        <v-row>
          <v-col v-for="(img, index) in sb.imgs" :key="index" xs="12" sm="6" md="4">
            <ImageViewer :location="task.setup_task_report.new_lat_lng" :img="img"></ImageViewer>
            
          </v-col>
        </v-row>
      </div>

      <v-text-field v-model="task.setup_task_report.note" outlined label="Báo cáo"></v-text-field>
      <div>
        <v-btn :loading="approving" @click="approve(0)" v-if="task.status>0" color="red" dark class="ma-2" large>
          <v-icon small left>fas fa-ban</v-icon>&nbsp; Không duyệt nhiệm vụ
        </v-btn>

        <v-btn :loading="approving" @click="approve(3)" v-if="task.status==2" style="float:right" color="teal" dark class="ma-2" large>
          <v-icon small left>fas fa-check-square</v-icon>&nbsp; Duyệt nhiệm vụ
        </v-btn>
      </div>
    </v-card>
  </div>
</template>
<script>
import ImageViewer from '../../components/ImageViewer'
import { BASE_API } from "@/utils/base";
import {approveTask} from'@/api/task/task'
export default {
  components:{
    ImageViewer
  },
  props: {
    task: {
      type: Object,
      default() {
        return {};
      },
      required: true
    }
  },

  methods: {
    getUrl(img) {
      return BASE_API + "file/get/" + img;
    },
    approve(status){
      this.approving = true;
      approveTask({
        id:this.task._id,
        status:status
      }).then(res=>{
        console.log(res);
        this.approving = false;
        this.task.status = status;
      })
    }
  },
  data() {
    return {
      approving:false,
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
  created() {
    this.report.newLat = this.task.setup_task.place_rental.lat_lng.lat;
    this.report.newLng = this.task.setup_task.place_rental.lat_lng.lng;
    this.task.setup_task.signboards.forEach(e => {
      this.report.signboards.push({
        sb: e,
        files: []
      });
    });
  }
};
</script>