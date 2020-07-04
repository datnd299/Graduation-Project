<template>
    <v-card class="elevation-2">
        <v-card-title class="headline">Nhiệm vụ lắp đặt <v-chip style="float:right"  small="" class="ma-2 text-right" :color="task.status|taskStatusColor" label text-color="white">
            <!-- <v-icon dense small left>{{task.status|taskTypeIcon}}</v-icon> -->
            {{task.status|taskStatusText}}
          </v-chip></v-card-title>
        <v-card-text><div>Thực hiện:
            <span
          v-for="(acc, index) in task.setup_task.accs"
          :key="index"
        >• {{acc.name}}</span>
        </div>
        <div
            v-for="(sb, index) in task.setup_task_report.signboards"
            :key="index"
            style="border: 1px dashed #95a5a6;
    padding: 10px;
    margin-top: 10px;"
          >
            <div style="font-weight:bold">Biển quảng cáo: {{sb.s_id.name}}</div>
            <div style="margin-top:20px">
              
              <v-carousel height="220" hide-delimiter-background show-arrows-on-hover>
              <v-carousel-item v-for="(img, index) in sb.imgs" :key="index" >
                <ImageViewer :location="task.setup_task.place_rental.lat_lng" :img="img"></ImageViewer>
                  
                
                 
              </v-carousel-item>
            </v-carousel>
          
            </div>
          </div>
        </v-card-text>
        
      </v-card>
</template>
<script>
import {BASE_API} from '@/utils/base'
import ImageViewer from '@/views/pages/tasks/components/ImageViewer.vue'
export default {
    props:{
        task:{
            type:Object,
            required:true,
        }
    },
    components:{
      ImageViewer
    },
    methods:{
        getUrl(img){
      return BASE_API+'file/get/'+img
    },
    }
}
</script>