<template>
  <v-card height="100%">
    <v-navigation-drawer absolute permanent>
      <template v-slot:prepend>
        <v-list-item one-line>
          <v-list-item-content>
            <v-list-item-title>Biển quảng cáo</v-list-item-title>
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
                :color="item.color.name"
                hide-details
              ></v-checkbox>
              <v-list-item-subtitle style="margin-left:20px">Số biển: 2 - Chi phí: 10,000,000đ</v-list-item-subtitle>
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
          :position="infoWindowPos"
          :opened="infoWinOpen"
          @closeclick="infoWinOpen=false"
        >
          <div v-if="currentPlace.start" style="width:250px;" class="info-window-content">
            <div style="max-height:100px">
              <img style="    max-height: 100px;
    width: 100%;
    object-fit: contain;" :src="getFile(currentPlace.s.imgs[0])" alt="">
            </div>
            <div>
              <b>• {{currentPlace.s.name}}</b>
            </div>
            <div>
              <b>• {{currentPlace.name}}</b>
            </div>

            <div>
              <b>
                • {{Date.parse(currentPlace.start).toString('dd/MM/yyyy')}}
                <i class="fas fa-arrow-right"></i>
                {{Date.parse(currentPlace.end).toString('dd/MM/yyyy')}}
              </b>
            </div>
            <div>
              <b>• {{currentPlace.price|numberF}} / {{currentPlace.time_unit|timeUnitText}}</b>
            </div>
            <div>
              <b>• Đã trả: 5,000,000đ</b>
            </div>
          </div>
        </gmap-info-window>
        <gmap-marker
          v-for="(item, index) in places"
          :key="index"
          :icon="{ url: item.s.urlIcon}"
          :position="{ lat: item.lat_lng.lat, lng: item.lat_lng.lng }"
          @click="clickMarker(item,index)"
        ></gmap-marker>
      </GmapMap>
    </div>
  </v-card>
</template>
<script>
import { getSignboards } from "@/api/party-a/signboard";
import { getPlaces } from "@/api/party-a/place";
import { ColorLst } from "@/utils/mapping";
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
      places: [],
      infoWindowPos: null,
      infoWinOpen: true,
      infoContent: "",
      currentPlace: {}
    };
  },
  computed: {
    tableDataFiltered() {
      return this.tableData.filter(e => {
        return e.choosed == true;
      });
    }
  },
  methods: {
    getFile(img) {
      return BASE_API + "file/get/" + img;
    },
    clickMarker(place, index) {
      console.log(index);

      this.infoWinOpen = true;
      this.infoWindowPos = {
        lat: Number(place.lat_lng.lat),
        lng: Number(place.lat_lng.lng)
      };
      this.currentPlace = place;
    }
  },
  created() {
    this.isLoading = true;
    getSignboards().then(res => {
      res.data.forEach((e, i) => {
        e.choosed = true;
        e.color = ColorLst[i];
      });
      this.isLoading = false;
      this.tableData = res.data;
      getPlaces().then(res2 => {
        res2.data.forEach(e => {
          if (e.signboards) {
            e.signboards.forEach(s1 => {
              res.data.forEach(s2 => {
                if (s1._id == s2._id) {
                  s1.color = s2.color;
                  console.log(
                    "/map/marker/" + s1.color.name.replace(" ", "_") + ".svg"
                  );

                  s1.urlIcon =
                    "/map/marker/" + s1.color.name.replace(" ", "_") + ".svg";
                }
              });
            });
          }
        });
        this.places = [];
        res2.data.forEach(e => {
          if (e.signboards) {
            e.signboards.forEach(s => {
              e.s = s;
              this.places.push(e);
            });
          }
        });
      });
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