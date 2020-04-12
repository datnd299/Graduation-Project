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
          label="Người thực hiện"
          v-if="form.type!='report'"
          outlined
        ></v-select>

        <v-select
          :loading="placeLoading"
          v-model="form.place_id"
          :items="placeLstSelecst"
          label="Điểm treo"
          outlined
        ></v-select>
        <v-select
          v-if="form.type=='setup'"
          :loading="sbLoading"
          :items="sbLstSelecst"
          multiple
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
            <repeat-setup></repeat-setup>
          </div>
          
          
        </div>
        <v-row>
          <v-col xs="12" sm="6">
            <v-text-field dense outlined type="datetime-local" label="Thực hiện từ" required></v-text-field>
          </v-col>
          <v-col xs="12" sm="6">
            <v-text-field dense outlined type="datetime-local" label="Đến" required></v-text-field>
          </v-col>
        </v-row>
        <v-row v-if="form.type=='fee'">
          <v-col xs="12" sm="6">
            <v-text-field label="Chi phí" type="Number" outlined v-model="form.fee"></v-text-field>
          </v-col>
          <v-col xs="12" sm="6">
            <v-select
              :loading="placeLoading"
              v-model="form.times"
              :items="times"
              multiple
              :label="currentTimeUnit"
              outlined
            ></v-select>
          </v-col>
        </v-row>

        <v-text-field label="Ghi chú" outlined v-model="form.note"></v-text-field>
        <div style="text-align:right">
          <v-btn large color="primary">
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
import RepeatSetup from './components/RepeatSetup'
export default {
    components:{
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
        type: "setup",
        place_id: null,
        times: null,
        fee: 0,
        typeRepeat: null
      }
    };
  },
  computed: {
    taskTypes() {
      return TaskType;
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
      if (this.form.place_id) {
        this.placeLst.forEach(e => {
          if (e._id == this.form.place_id) {
            u = this.$options.filters.timeUnitText(e.time_unit) + " thứ";
          }
        });
      }
      return u;
    },
    currentPlace() {
      var u = null;
      if (this.form.place_id) {
        this.placeLst.forEach(e => {
          if (e._id == this.form.place_id) {
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
    "form.times"() {
      this.form.fee = this.form.times.length * this.currentPlace.price;
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