<template>
  <div style="padding:10px">
    <v-select v-model="form.fz" :items="timeUnitSelect" label="Tần suất" outlined></v-select>
    <v-text-field v-if="form.fz=='day'" label="Mỗi" type="Number" outlined>
      <span slot="append">Ngày</span>
    </v-text-field>
    <div>
      <div v-if="form.fz=='week'" class="d-oulined-box-input">
        <span class="d-box-title">Vào các thứ</span>
        <v-btn-toggle
          multiple
          style="display: -webkit-inline-box;"
          tile
          color="deep-purple accent-3"
          group
        >
          <v-btn v-for="(item, index) in weekdays" :key="index" :value="index">{{item.text}}</v-btn>
        </v-btn-toggle>
      </div>
      <div v-if="form.fz=='moth'" class="d-oulined-box-input">
        <span class="d-box-title">Vào các ngày</span>
        <v-btn-toggle
          multiple
          style="display: -webkit-inline-box;"
          tile
          color="deep-purple accent-3"
          group
        >
          <v-btn v-for="(item, index) in days" :key="index" :value="index">{{item.text}}</v-btn>
        </v-btn-toggle>
      </div>
      <div class="d-oulined-box-input" style="padding:5px">
        <v-switch color="indigo darken-3" v-model="form.random" label="Yêu cầu ngẫu nhiên"></v-switch>
        <v-text-field v-if="form.random" label="Thực hiện trong vòng" type="Number" outlined>
          <span slot="append">Phút</span>
        </v-text-field>
      </div>
    </div>
    <v-text-field label="Thông báo trước" type="Number" outlined>
      <v-select style="margin-top: -17px;"
          :items="timeSmallUnitSelect"
          v-model="form.timeUnit"
          label="Đơn vị"
          outlined
           slot="append-outer" 
        ></v-select>
    </v-text-field>
  </div>
</template>
<script>
import { TimeUnit,TimeSmallUnit } from "@/utils/mapping";
export default {
  data() {
    return {
        form:{
            fz:'day',
            random:false
        }
    };
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
      for (var t in TimeSmallUnit) {
        if (t != "year") {
          times.push({
            text: TimeSmallUnit[t].text,
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
          text: i < 7 ? "Th " + (i + 1) : "CN"
        });
      }
      return ws;
    },
    days() {
      var ws = [];
      for (let i = 1; i < 31; i++) {
        ws.push({
          text: i
        });
      }
      return ws;
    }
  }
};
</script>
