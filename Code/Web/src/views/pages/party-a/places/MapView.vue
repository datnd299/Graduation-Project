<template>
  <v-card height="100%">
    <v-navigation-drawer absolute permanent>
      <template v-slot:prepend>
        <v-list-item one-line>
          <v-list-item-content>
            <v-list-item-title>Địa điểm</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </template>

      <v-divider></v-divider>

      <v-list :loading="isLoading" dense>
        <v-list-item>
          <v-list-item-content>
            <v-list-item-title v-for="(item,index) in tableData" :key="index">
              <v-checkbox
                class="nck"
                iiffff
                dense
                v-model="item.choosed"
                :label="item.name"
                color="indigo"
                hide-details
              ></v-checkbox>
              <v-list-item-subtitle style="margin-left:20px">Chi phí: {{item.price|numberF}} / {{item.time_unit|timeUnitText}}</v-list-item-subtitle>
              <v-list-item-subtitle style="margin-left:20px">Đã trả: {{item.report.fee|numberF}}đ</v-list-item-subtitle>
              <v-list-item-subtitle style="margin-left:20px">Nhiệm vụ: {{item.report.task}}, Hình ảnh: {{item.report.img}}</v-list-item-subtitle>
             
            </v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>
    <div
      style="position: absolute;
    width: calc(100% - 256px);
    right: 0px;
    height: 100%;"
    >
      <GmapMap :center="center" :zoom="7" style="width:100%; height: 100%;">
          <gmap-info-window
        :options="infoOptions"
        :position="infoWindowPos"
        :opened="infoWinOpen"
        @closeclick="infoWinOpen=false"
      >
      <div v-if="currentPlace.start" style="width:250px;" class="info-window-content">
        <div style="max-height:100px">
              <img style="    max-height: 100px;
    width: 100%;
    object-fit: contain;" :src="getFile(currentPlace.imgs[0])" alt="">
            </div>
          <div>
            <b>• {{currentPlace.name}}</b>
          </div>
          <div style="margin-left:10px" v-for="(sb, index) in currentPlace.signboards" :key="index">
            <b>• {{sb.name}}</b>
          </div>
          <div>
            <b >• {{Date.parse(currentPlace.start).toString('dd/MM/yyyy')}} <i class="fas fa-arrow-right"></i> {{Date.parse(currentPlace.end).toString('dd/MM/yyyy')}}</b>
          </div>
          <div>
            <b>• {{currentPlace.price|numberF}} / {{currentPlace.time_unit|timeUnitText}}</b>
          </div>
          <div>
              <b>• Đã trả: {{currentPlace.report.fee|numberF}}đ</b>
            </div>
            
      </div>
      </gmap-info-window>
        <gmap-marker v-for="(item, index) in tableDataFiltered" :key="index"
          :icon="{ url: '/assets/media/icons/svg/Map/Marker.png'}"
          :position="{ lat: item.lat_lng.lat, lng: item.lat_lng.lng }"
          @click="clickMarker(item,index)"
        ></gmap-marker>
      </GmapMap>
    </div>
  </v-card>
</template>
<script>
import { getPlacesReport } from "@/api/party-a/place";
import { BASE_API } from "@/utils/base";
export default {
  data() {
    return {
        center: { lat: 21.004988169511012, lng: 105.84349504177669 },
      items: [
        { title: "Home", icon: "mdi-home-city" },
        { title: "My Account", icon: "mdi-account" },
        { title: "Users", icon: "mdi-account-group-outline" }
      ],
      isLoading: false,
      tableData: [],
       infoWindowPos: null,
      infoWinOpen: true,
      infoContent: "",
      currentPlace:{
          
      }
    };
  },
  computed:{
      tableDataFiltered(){
          return this.tableData.filter(e=>{
              return e.choosed ==true;
          })
      }
  },
  methods:{
    getFile(img) {
      return BASE_API + "file/get/" + img;
    },
      clickMarker(place,index){
          console.log(index);
          
          this.infoWinOpen = true;
            this.infoWindowPos = {
            lat: Number(place.lat_lng.lat),
            lng: Number(place.lat_lng.lng)
            };
        this.currentPlace = place
      }

      
  },
  created() {
    this.isLoading = true;
    getPlacesReport().then(res => {
        res.data.forEach(e=>{
            e.choosed = true;
        })
      this.isLoading = false;
      this.tableData = res.data;
    });
  }
};
</script>
<style  scoped>
/deep/ .nck label {
  top: 4px;
  font-size: 12px;
}
</style>
<style lang="css" scoped>
    .info-window-content div {
  margin-top: 3px;
}
</style>