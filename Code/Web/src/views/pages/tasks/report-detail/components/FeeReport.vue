<template>
  <div>
    <v-card class="mx-auto" style="padding:20px" outlined>
      <v-list-item-title class="headline mb-1">Báo cáo nhiệm vụ</v-list-item-title>
    

    <div v-for="(pl, index) in report.places" :key="index" class="place-payment">
        <span>
            {{pl.pl.place_rental.name}}
        </span>
        <v-btn icon color="purple" small>
          <v-icon dense small>fas fa-map</v-icon>
        </v-btn>
        <span> - Số tiền: </span>
        <span>{{pl.pl.amount|numberF}}</span>
        <v-btn style="color: white;float:right" class="ma-2" tile color="indigo" small>
          <v-icon dense x-small="">far fa-check-square</v-icon>
        </v-btn>
        <div>
          <span>Nhân viên đã xác nhận:</span> <v-checkbox readonly="" class="shrink mr-2 mt-0" hide-details style="display: inline-block;" v-model="pl.pl.report.em_confirm"></v-checkbox>
          <span>Ghi chú: {{pl.pl.report.em_note}}</span>
        </div> 
        <div>
          <span>Đối tác đã xác nhận:</span> <v-checkbox readonly="" class="shrink mr-2 mt-0" hide-details style="display: inline-block;" v-model="pl.pl.report.pt_confirm"></v-checkbox>
          <span>Ghi chú: {{pl.pl.report.pt_note}}</span>
        </div>
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
   this.task.fee_task.fee_detail.forEach(e => {
      this.report.places.push({
        pl:e,
        status:1
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