<template>
  <div>
    <v-card class="mx-auto" style="padding:20px" outlined>
      <v-list-item-title class="headline mb-1">Báo cáo nhiệm vụ</v-list-item-title>
    

    <div v-for="(pl, index) in report.places" :key="index" class="place-payment">
        <span>
            {{pl.pl.name}}
        </span>
        <v-btn icon color="purple" small>
          <v-icon dense small>fas fa-map</v-icon>
        </v-btn>
        <div>
          <v-file-input
            outlined
            accept="image/*"
            chips
            multiple
            v-model="pl.files"
            label="Ảnh"
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
          <div v-if="pl.files" class="image-input-preview">
            <img v-for="(item, index) in pl.files" :key="index" :src="getUrl(item)" alt />
          </div>
          <v-progress-linear
            v-if="uploading"
            color="teal"
            buffer-value="0"
            :value="uploadVal"
            stream
          ></v-progress-linear>
        </div>
        <div>
            <b style="font-weight:bold">Đánh giá của bạn</b> 
            <v-rating background-color="orange lighten-3"
      color="orange" ></v-rating>
            <v-text-field dense="" outlined label="Nhận xét của bạn">
                <v-btn style="
    margin-top: -4px;" slot="append-outer" color="indigo" dark>
              <i style="font-size: 18px;" class="far fa-check-square"></i>
            </v-btn>
            </v-text-field>
        </div>
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

        places: [],

      }
    };
  },
  created(){
   this.task.check_task.place_rental.forEach(e => {
      this.report.places.push({
        pl:e,
        status:1,
        files:[]
      })
    });
  }
};
</script>
<style lang="css" scoped>
.place-payment{
        border: 1px dashed #b2bec3;
    border-radius: 5px;
    padding: 5px 20px;
    margin-bottom: 20px;
    position: relative;
}
.place-payment span{
    font-weight: bold;
}
</style>