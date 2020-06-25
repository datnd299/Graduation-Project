<template>
    <v-card class="elevation-2">
        <v-card-title class="headline">Nhiệm vụ báo cáo tình trạng <v-chip style="float:right"  small="" class="ma-2 text-right" :color="task.status|taskStatusColor" label text-color="white">
            <!-- <v-icon dense small left>{{task.status|taskTypeIcon}}</v-icon> -->
            {{task.status|taskStatusText}}
          </v-chip></v-card-title>
        <v-card-text><div>Thực hiện: Nhà hàng Phúc Lộc
            <!-- <span
          v-for="(acc, index) in task.fee_task.accs"
          :key="index"
        >• {{acc.name}}</span> -->
        </div>
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
                <v-col v-for="(img, index) in sb.imgs" :key="index" xs="12" sm="12" md="12">
                  <v-card class="mx-auto" max-width="300">
                    <v-img class="white--text align-end" height="150" :src="getUrl(img.name)">
                      <!-- <v-card-title style="color:black"></v-card-title> -->
                    </v-img>
                    <div style="margin-left:5px">
                      {{img.device}} - Cách mốc khoảng 50m
                      <i style="color:purple" class="fas fa-map-marked-alt"></i>
                    </div>
                  </v-card>
                </v-col>
              </v-row>
              <b style="font-weight:bold">Đánh giá của đối tác</b>
              <v-rating small="" background-color="orange lighten-3" color="orange"></v-rating>
              <div>Biển tốt</div>
            </div>
          </div>
        </v-card-text>
        
      </v-card>
</template>
<script>
import {BASE_API} from '@/utils/base'
export default {
    props:{
        task:{
            type:Object,
            required:true,
        }
    },
    methods:{
        getUrl(img){
      return BASE_API+'file/get/'+img
    },
    }
}
</script>