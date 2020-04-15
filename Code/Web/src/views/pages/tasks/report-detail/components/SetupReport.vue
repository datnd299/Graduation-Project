<template>
  <div>
    <v-card class="mx-auto" style="padding:20px" outlined>
      <v-list-item-title class="headline mb-1">Báo cáo nhiệm vụ</v-list-item-title>
      <v-row>
        <v-col xs="12" sm="6">
          <v-text-field readonly dense outlined v-model="task.setup_task.place_rental.lat_lng.lat"  type="number" label="Vị trí: Lat" required></v-text-field>
        </v-col>
        <v-col xs="12" sm="6">
          <v-text-field readonly dense outlined v-model="task.setup_task.place_rental.lat_lng.lng" type="number" label="Vị trí: Lng" required>
            <v-btn style="
    margin-top: -4px;" slot="append-outer" color="purple" dark>
              <i style="font-size: 18px;" class="fas fa-map-marker-alt"></i>
            </v-btn>
          </v-text-field>
        </v-col>
      </v-row>
      <v-checkbox
        style="margin-top:-20px"
        label="Cần căn chỉnh lại vị trí"
        color="indigo darken-3"
       v-model="report.editLocation"

        hide-details
      ></v-checkbox>
      <v-row v-if="report.editLocation">
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
        </v-row>
        <div style="width:100%" v-for="(item, index) in report.signboards" :key="index">
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
        </div>
        <v-text-field v-model="report.note" outlined label="Báo cáo"></v-text-field>
        <div style="text-align:right"
        >
        <v-btn
              color="teal"
              dark
              class="ma-2"
              large
            >
              <v-icon small="">fas fa-paper-plane</v-icon>&nbsp; Báo cáo nhiệm vụ
            </v-btn>
        </div>
    </v-card>
  </div>
</template>
<script>
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