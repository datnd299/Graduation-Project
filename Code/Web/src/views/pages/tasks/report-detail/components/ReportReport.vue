<template>
  <div>
    <v-card class="mx-auto" style="padding:20px" outlined>
      <v-list-item-title class="headline mb-1">Báo cáo nhiệm vụ</v-list-item-title>
        
        <div>
          <div
            v-for="(sb, index) in task.report_task_report.signboards"
            :key="index"
            style="border: 1px dashed #95a5a6;
    padding: 10px;
    margin-top: 10px;"
          >
            <div style="font-weight:bold">Biển quảng cáo: {{sb.s_id.name}}</div>
            <div>
              <v-row>
                <v-col v-for="(img, index) in sb.imgs" :key="index" xs="12" sm="6" md="4">
                  <ImageViewer :location="task.report_task.place_rental.lat_lng" :img="img"></ImageViewer>
                </v-col>
              </v-row>
              <div>
                  <b style="font-weight:bold">Lịch sử báo cáo</b>
              </div>
              <b style="font-weight:bold">Đánh giá của đối tác</b>
              <v-rating v-model="sb.rating" readonly="" background-color="orange lighten-3" color="orange"></v-rating>
              <v-text-field v-model="sb.note" readonly dense outlined label="Báo cáo của đối tác">
                <v-btn style="
    margin-top: -4px;" slot="append-outer" color="indigo" dark>
                  <i style="font-size: 18px;" class="far fa-check-square"></i>
                </v-btn>
              </v-text-field>
            </div>
          </div>
        </div>
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
import ImageViewer from '../../components/ImageViewer'
export default {
  props: {
    task: {
      type: Object,
      default() {
        return {};
      },
      required: true
    }
  },
  components:{
    ImageViewer,
  },
  methods:{
    getUrl(img){
      return BASE_API+'file/get/'+img
    },
  },
  data() {
    return {
      uploading: false
    };
  },
  created() {}
};
</script>
<style lang="css" scoped>
.place-payment {
  border: 1px dashed #b2bec3;
  border-radius: 5px;
  padding: 5px 20px;
  margin-bottom: 20px;
  position: relative;
}
.place-payment .pl-name {
  font-weight: bold;
}
</style>