<template>
    <v-card class="mx-auto" max-width="300">
        <ImageViewer ref="viewer" />
              <v-img class="white--text align-end" height="150" :src="getUrl(img.name)">
                <!-- <v-card-title style="color:black"></v-card-title> -->
              </v-img>
              <div style="margin-left:5px">
                  <template v-if="img.status==2">
                      {{img.device}} - <span v-if="img.lat_lng.lat!==0">Cách mốc {{getDistance()}}m</span> <span style="color:red" v-else>Vị trí giả</span>&nbsp;
                      <a target="_blank" :href="'https://www.google.com/maps/search/?api=1&query='+img.lat_lng.lat+','+img.lat_lng.lng"><i style="color:purple" class="fas fa-map-marked-alt"></i></a> &nbsp;
                  </template>
                  <template v-else>
                      {{img.status|sImageStatusText}} &nbsp;
                  </template>
                &nbsp;
                <v-btn x-small="" icon=""  @click="viewImage()"  color="pink" >
                    <v-icon>far fa-image</v-icon>
                </v-btn>
                &nbsp;
                <v-menu open-on-hover bottom="" offset-y v-if="img.status==2">
      <template v-slot:activator="{ on, attrs }">
        <v-btn x-small="" icon=""
          color="primary"
          dark
          v-bind="attrs"
          v-on="on"
        >
          <v-icon>fas fa-info-circle</v-icon>
        </v-btn>
      </template>

      <div style="padding:5px;background:white">
          <div>
              • Tên thiết bị: {{img.device}}
          </div>
          <div v-if="img.lat_lng.lat!==0">
              • Cách mốc: {{getDistance()}}m
          </div>
          <div style="color:red" v-else>
              • Vị trí giả
          </div>
          <div>
              • Thời gian chụp: {{Date.parse(img.time).add({ hours: 15 }).toString("dd/MM/yyyy HH:mm")}}
          </div>
          <div>
              • Thời gian upload: {{Date.parse(img.createdAt).toString("dd/MM/yyyy HH:mm")}}
          </div>
          <div>
              • Trạng thái: <v-chip
      class="ma-2"
      :color="img.status|sImageStatusColor"
      text-color="white"
      x-small=""
    >
      {{img.status|sImageStatusText}}
    </v-chip>
          </div>
      </div>
    </v-menu>
              </div>
            </v-card>
</template>
<script>
import ImageViewer from '@/views/partials/widgets/ImageViewer.vue'
import { BASE_API } from "@/utils/base";
import {calcCrow} from "@/utils/index"
export default {
    props:{
        img:Object,
        location:Object
    },
    components:{
        ImageViewer
    },
    methods: {
        getUrl(img) {
            return BASE_API + "file/get/" + img;
        },
        getDistance(){
            if (this.img.lat_lng.lat===0) {
                return 'Vị trí giả'
            }
            return calcCrow(this.location.lat,this.location.lng,this.img.lat_lng.lat,this.img.lat_lng.lng);
        },
        viewImage(){
            var arr =[{
        thumbnail: this.getUrl(this.img.name),
        source: this.getUrl(this.img.name)
      }]
            this.$refs.viewer.show(arr, 0);
        }
    },
    
}
</script>