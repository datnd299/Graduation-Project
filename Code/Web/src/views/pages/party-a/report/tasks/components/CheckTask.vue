<template>
  <v-card class="elevation-2">
    <v-card-title class="headline">
      Nhiệm vụ kiểm tra
      <v-chip
        style="float:right"
        small
        class="ma-2 text-right"
        :color="task.status|taskStatusColor"
        label
        text-color="white"
      >
        <!-- <v-icon dense small left>{{task.status|taskTypeIcon}}</v-icon> -->
        {{task.status|taskStatusText}}
      </v-chip>
    </v-card-title>
    <v-card-text>
      <div>
        Thực hiện:
        <span v-for="(acc, index) in task.check_task.accs" :key="index">• {{acc.name}}</span>
      </div>
      <div v-if="task.check_task_report.place_rental[0].signboards">
        <div
          v-for="(sb, index) in task.check_task_report.place_rental[0].signboards"
          :key="index"
          style="border: 1px dashed #95a5a6;
    padding: 10px;
    margin-top: 10px;"
        >
          <div style="font-weight:bold">Biển quảng cáo: {{sb.s_id.name}}</div>
          <div>
            <v-carousel height="150" hide-delimiter-background show-arrows-on-hover>
              <v-carousel-item v-for="(img, index) in sb.imgs" :key="index" >
                  <div>
<img style="width: 100%;
    object-fit: contain;" class="white--text align-end" height="150" :src="getUrl(img.name)">
                   <div style="margin-left:5px">
                      {{img.device}} - Cách mốc khoảng 50m
                      <i style="color:purple" class="fas fa-map-marked-alt"></i>
                    </div>
                  </div>
                
                 
              </v-carousel-item>
            </v-carousel>
            
            <b style="font-weight:bold">Đánh giá của nhân viên</b>
            <v-rating small background-color="orange lighten-3" color="orange"></v-rating>
            <div>Chất lượng biển tốt</div>
          </div>
        </div>
      </div>
    </v-card-text>
  </v-card>
</template>
<script>
import { BASE_API } from "@/utils/base";
export default {
  props: {
    task: {
      type: Object,
      required: true
    }
  },
  methods: {
    getUrl(img) {
      return BASE_API + "file/get/" + img;
    }
  }
};
</script>