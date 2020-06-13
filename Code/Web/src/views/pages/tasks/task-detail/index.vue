<template>
  <div>
    <v-card class="mx-auto" style="padding:20px" outlined>
      <v-list-item-title class="headline mb-1">Nhiệm vụ mới</v-list-item-title>
      <br />
      <br />
      <v-form ref="form">
        <div
          style="border: 1px solid;
    border-radius: 4px;
    border-color: #b2bec3;
    margin-bottom: 30px;"
        >
          <v-btn-toggle
            style="display: -webkit-inline-box;"
            v-model="form.type"
            tile
            color="deep-purple accent-3"
            group
          >
            <v-btn v-for="(item, index) in taskTypes" :key="index" :value="index">
              <v-icon left>{{item.icon}}</v-icon>
              {{item.text}}
            </v-btn>
          </v-btn-toggle>
        </div>

        <v-select
          :loading="accLoading"
          :items="emLstSelecst"
          multiple
          chips
          v-model="form.accs"
          label="Người thực hiện"
          v-if="form.type!='report'"
          outlined
        ></v-select>

        <v-select
          :loading="placeLoading"
          v-model="form.places"
          :multiple="form.type!='setup'&&form.type!='report'"
          :items="placeLstSelecst"
          label="Điểm treo"
          outlined
        ></v-select>
        <v-select
          v-if="form.type=='setup'"
          :loading="sbLoading"
          :items="sbLstSelecst"
          multiple
          v-model="form.signboards"
          chips
          label="Lắp đặt biển"
          outlined
        ></v-select>

        <div v-if="form.type=='report'">
          <div
            style="border: 1px solid;
    border-radius: 4px;
    border-color: #b2bec3;
    margin-bottom: 30px;"
          >
            <v-btn-toggle
              style="display: -webkit-inline-box;"
              v-model="form.typeRepeat"
              tile
              color="deep-purple accent-3"
              group
            >
              <v-btn v-for="(item, index) in repeatTypes" :key="index" :value="index">
                <v-icon left>{{item.icon}}</v-icon>
                {{item.text}}
              </v-btn>
            </v-btn-toggle>
            <repeat-setup :detail="form.repeatDetail" v-if="form.typeRepeat=='repeat'"></repeat-setup>
          </div>
        </div>
        <div v-if="form.type=='fee'">
          <v-row style="margin-top: -20px;" v-for="(pl, index) in form.feeDetail" :key="index">
            <v-col xs="12" sm="6">
              <v-select
                dense
                @change="timeUnitSelectChanged(pl)"
                :loading="placeLoading"
                v-model="pl.times"
                :items="times"
                multiple
                :label="pl.unit|timeUnitText"
                outlined
              ></v-select>
            </v-col>
            <v-col xs="12" sm="6">
              <v-text-field
                dense
                :label="pl.name+' - Chi phí'"
                type="Number"
                outlined
                v-model="pl.amount"
              ></v-text-field>
            </v-col>
          </v-row>
          <p style="text-align:right;margin-top: -20px;font-weight:bold">Tổng: {{sumFee|numberF}}</p>
        </div>

        <v-row>
          <v-col xs="12" sm="6">
            <v-text-field
              v-model="form.start"
              dense
              outlined
              :type="(form.typeRepeat=='repeat'?'time':'datetime-local')"
              label="Thực hiện từ"
              required
            ></v-text-field>
          </v-col>
          <v-col xs="12" sm="6">
            <v-text-field
              v-model="form.end"
              dense
              outlined
              :type="(form.typeRepeat=='repeat'?'time':'datetime-local')"
              label="Đến"
              required
            ></v-text-field>
          </v-col>
        </v-row>

        <v-text-field label="Ghi chú" outlined v-model="form.note"></v-text-field>
        <div style="text-align:right">
          <v-btn large color="primary" @click="createTask()">
            <i class="fas fa-plus"></i> &nbsp; &nbsp; Tạo nhiệm vụ
          </v-btn>
        </div>
      </v-form>
    </v-card>
  </div>
</template>
<script>
import { TaskType, RepeatType } from "@/utils/mapping";
import { getAccs } from "@/api/party-a/account";
import { getPlaces } from "@/api/party-a/place";
import { getSignboards } from "@/api/party-a/signboard";
import RepeatSetup from "./components/RepeatSetup";

import { createNew } from "@/api/task/task";
export default {
  components: {
    RepeatSetup
  },
  data() {
    return {
      emLst: [],
      placeLst: [],
      times: [1, 2, 3, 4, 5, 6, 7, 8, 9],
      sbLst: [],
      accLoading: false,
      placeLoading: false,
      sbLoading: false,

      form: {
        accs: null,
        type: "setup",
        places: null,
        signboards: null,
        note: null,
        start: null,
        end: null,
        feeDetail: [],
        typeRepeat: "repeat",
        repeatDetail: {
          fzUnit: "day",
          fzVals: [],
          fzValDay: 1,
          random: false,
          remindBeforeVal: 60,
          remindBeforeUnit: "minute",
          timeToComplete: 60
        }
      }
    };
  },
  methods: {
    createTask() {
      createNew(this.form);
    },
    timeUnitSelectChanged(pl) {
      if (pl.times) {
        pl.amount = pl.times.length * pl.price;
      } else {
        pl.amount = 0;
      }
    }
  },
  computed: {
    taskTypes() {
      return TaskType;
    },
    sumFee() {
      var sum = 0;
      if (this.form.feeDetail) {
        this.form.feeDetail.forEach(e => {
          sum += e.amount;
        });
      }
      return sum;
    },
    repeatTypes() {
      return RepeatType;
    },
    emLstSelecst() {
      return this.emLst.map(e => {
        return {
          text: e.name,
          value: e._id
        };
      });
    },
    currentTimeUnit() {
      var u = "";
      if (this.form.places) {
        this.placeLst.forEach(e => {
          if (e._id == this.form.places) {
            console.log(e);

            u = this.$options.filters.timeUnitText(e.time_unit) + " thứ";
          }
        });
      }
      return u;
    },
    currentPlace() {
      var u = null;
      if (this.form.places) {
        this.placeLst.forEach(e => {
          if (e._id == this.form.places) {
            u = e;
          }
        });
      }
      return u;
    },
    placeLstSelecst() {
      return this.placeLst.map(e => {
        return {
          text: e.name,
          value: e._id
        };
      });
    },
    sbLstSelecst() {
      return this.sbLst.map(e => {
        return {
          text: e.name,
          value: e._id
        };
      });
    }
  },
  watch: {
    // "form.times"() {
    //   if(this.form.times&&this.currentPlace){
    //     this.form.fee = this.form.times.length * this.currentPlace.price;
    //   }
    // }
    "form.places"() {
      this.form.feeDetail = [];
      this.form.places.forEach(e => {
        var pl = {};
        this.placeLst.forEach(o => {
          if (o._id == e) {
            pl = o;
          }
        });
        this.form.feeDetail.push({
          place_rental: pl._id,
          times: null,
          price: pl.price,
          unit: pl.time_unit,
          amount: 0,
          name: pl.name
        });
      });
    }
  },
  created() {
    this.accLoading = true;
    getAccs().then(res => {
      this.emLst = res.data.accs;
      this.accLoading = false;
    });
    this.placeLoading = true;
    getPlaces().then(res => {
      this.placeLoading = false;
      this.placeLst = res.data;
    });

    this.sbLoading = true;
    getSignboards().then(res => {
      this.sbLst = res.data;
      this.sbLoading = false;
    });
  }
};
</script>