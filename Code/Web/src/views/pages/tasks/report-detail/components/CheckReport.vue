<template>
  <div>
    <v-card class="mx-auto" style="padding:20px" outlined>
      <v-list-item-title class="headline mb-1">Báo cáo nhiệm vụ</v-list-item-title>

      <div
        v-for="(pl, index) in task.check_task_report.place_rental"
        :key="index"
        class="place-payment"
      >
        <div class="pl-name" style="text-align:center">
          {{pl.pl_id.name}}
          <v-btn icon color="purple" small>
            <v-icon dense small>fas fa-map</v-icon>
          </v-btn>
        </div>
        <div v-if="pl.signboards">
          <div
            v-for="(sb, index) in pl.signboards"
            :key="index"
            style="border: 1px dashed #95a5a6;
    padding: 10px;
    margin-top: 10px;"
          >
            <div style="font-weight:bold">Biển quảng cáo: {{sb.s_id.name}}</div>
            <div>
              <v-row>
                <v-col v-for="(img, index) in sb.imgs" :key="index" xs="12" sm="6" md="4">
                  <ImageViewer :location="pl.pl_id.lat_lng" :img="img"></ImageViewer>
                </v-col>
              </v-row>
              <b style="font-weight:bold">Đánh giá của nhân viên</b>
              <v-rating background-color="orange lighten-3" readonly="" v-model="sb.rating" color="orange"></v-rating>
              <v-text-field dense outlined v-model="sb.note"  readonly="" label="Nhận xét của nhân viên">
                <v-btn style="
    margin-top: -4px;" slot="append-outer" color="indigo" dark>
                  <i style="font-size: 18px;" class="far fa-check-square"></i>
                </v-btn>
              </v-text-field>
            </div>
          </div>
        </div>
      </div>
      <v-text-field v-model="task.check_task_report.note" outlined label="Báo cáo"></v-text-field>

    </v-card>
  </div>
</template>
<script>
import ImageViewer from '../../components/ImageViewer'
import {BASE_API} from '@/utils/base'
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