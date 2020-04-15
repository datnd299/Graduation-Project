<template>
  <div style="padding:10px">
    <v-select v-model="detail.fzUnit" :items="timeUnitSelect" label="Tần suất" outlined></v-select>

    
    <v-text-field v-if="detail.fzUnit=='day'" v-model="detail.fzValDay" label="Mỗi" type="Number" outlined>
      <span slot="append">Ngày</span>
    </v-text-field>
    <div>
      <div v-if="detail.fzUnit=='week'"  class="d-oulined-box-input">
        <span class="d-box-title">Vào các thứ</span>
        <v-btn-toggle
          multiple
          style="display: -webkit-inline-box;"
          tile
          color="deep-purple accent-3"
          group
          v-model="detail.fzVals"
        >
          <v-btn v-for="(item, index) in weekdays" :key="index" :value="item.value">{{item.text}}</v-btn>
        </v-btn-toggle>
      </div>
      <div v-if="detail.fzUnit=='moth'" class="d-oulined-box-input">
        <span class="d-box-title">Vào các ngày</span>
        <v-btn-toggle
          multiple
          v-model="detail.fzVals"
          style="display: -webkit-inline-box;"
          tile
          color="deep-purple accent-3"
          group
        >
          <v-btn v-for="(item, index) in days" :key="index" :value="item.value">{{item.text}}</v-btn>
        </v-btn-toggle>
      </div>
      <div class="d-oulined-box-input" style="padding:5px">
        <v-switch color="indigo darken-3" v-model="detail.random" label="Yêu cầu ngẫu nhiên"></v-switch>
        <v-text-field v-if="detail.random" v-model="detail.timeToComplete" label="Thực hiện trong vòng" type="Number" outlined>
          <span slot="append">Phút</span>
        </v-text-field>
      </div>
    </div>
    <v-text-field v-model="detail.remindBeforeVal" label="Thông báo trước" type="Number" outlined>
      <v-select style="margin-top: -17px;"
          :items="timeSmallUnitSelect"
          v-model="detail.remindBeforeUnit"
          label="Đơn vị"
          outlined
           slot="append-outer" 
        ></v-select>
    </v-text-field>
    </div>
 
</template>
<script>
import { TimeUnit,SmallTimeUnit } from "@/utils/mapping";
export default {
  data() {
    return {
        form:{
            fz:'day',
            random:false
        }
    };
  },
  props:{
    detail:{
      type:Object,
      required:true
    }
  },
  computed: {
    timeUnitSelect() {
      var times = [];
      for (var t in TimeUnit) {
        if (t != "year") {
          times.push({
            text: TimeUnit[t].text,
            value: t
          });
        }
      }
      return times;
    },
    timeSmallUnitSelect() {
      var times = [];
      for (var t in SmallTimeUnit) {
        if (t != "year") {
          times.push({
            text: SmallTimeUnit[t].text,
            value: t
          });
        }
      }
      return times;
    },
    weekdays() {
      var ws = [];
      for (let i = 1; i < 8; i++) {
        ws.push({
          text: i < 7 ? "Th " + (i + 1) : "CN",
          value:i
        });
      }
      return ws;
    },
    days() {
      var ws = [];
      for (let i = 1; i <= 31; i++) {
        ws.push({
          text: i,
          value:i
        });
      }
      return ws;
    }
  }
};
</script>
