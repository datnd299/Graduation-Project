<template>
  <div>
    <v-dialog v-model="mapModalVisible" persistent max-width="800">
      <v-card style="padding:10px">
        <map-marker :location="form" @picked="onLocationPicked" @canceled="onCanceled"></map-marker>
      </v-card>
    </v-dialog>
    <v-card class="mx-auto" style="padding:20px" outlined>
      <v-list-item-title class="headline mb-1">Chi tiết Địa điểm</v-list-item-title>
      <br />
      <br />

      <div style="width: 100%;
      margin-bottom:10px;
    min-height: 164px;">
        <v-card outlined>
          <div class="d-flex flex-no-wrap justify-space-between">
            <div>
              <v-card-title
                v-if="placeB.code"
                class="headline"
                v-text="'Code: '+'PLB-'+placeB.code"
              ></v-card-title>
              <v-card-title v-else class="headline" v-text="'Chưa có mã'"></v-card-title>

              <v-card-subtitle v-text="placeB.name"></v-card-subtitle>
              <v-card-subtitle v-if="isMineB">
                <v-btn
                  :loading="genning"
                  @click="genNewCode()"
                  class="ma-2"
                  tile
                  outlined
                  color="success"
                >
                  <v-icon left>mdi-pencil</v-icon>Tạo mã mới
                </v-btn>
              </v-card-subtitle>
            </div>

            <v-avatar v-if="placeB.code" class="ma-3" size="125" tile>
              <v-img
                lazy-src="/assets/loading2.gif"
                :src="'https://api.qrserver.com/v1/create-qr-code/?size=117x117&data='+qrString"
              ></v-img>
            </v-avatar>
          </div>
        </v-card>
      </div>

      <v-form ref="form">
        <v-text-field
          :readonly="!isMineB"
          outlined
          v-model="form.name"
          :counter="150"
          label="Tên địa điểm"
          required
        ></v-text-field>
        <div>
          <v-text-field
            :readonly="!isMineB"
            outlined
            v-model="form.address"
            label="Địa chỉ"
            required
          >
            <v-btn
              style="height: 56px;
    margin-top: -17px;"
              slot="append-outer"
              color="purple"
              dark
              extra-large
              @click="mapModalVisible=true"
            >
              <i style="font-size: 24px;" class="fas fa-map-marker-alt"></i>
            </v-btn>
          </v-text-field>
          <v-row style="margin-top:-35px">
            <v-col xs="12" sm="6">
              <v-text-field
                :readonly="!isMineB"
                dense
                outlined
                type="number"
                v-model="form.location.lat"
                label="Lat"
                required
              ></v-text-field>
            </v-col>
            <v-col xs="12" sm="6">
              <v-text-field
                :readonly="!isMineB"
                dense
                outlined
                type="number"
                v-model="form.location.lng"
                label="Lng"
                required
              ></v-text-field>
            </v-col>
          </v-row>
        </div>

        <v-text-field
          :readonly="!isMineB"
          outlined
          type="number"
          v-model="form.price"
          label="Chi phí đề nghị"
          required
        >
          <v-select
            style="margin-top: -17px;"
            :readonly="!isMineB"
            :items="timeUnits"
            v-model="form.timeUnit"
            label="Đơn vị"
            outlined
            slot="append-outer"
          ></v-select>
        </v-text-field>
        <div v-if="isMineB">
          <v-file-input
            outlined
            accept="image/*"
            chips
            multiple
            v-model="form.files"
            label="Ảnh minh họa"
          ></v-file-input>
          <div v-if="form.files" class="image-input-preview">
            <img v-for="(item, index) in form.files" :key="index" :src="getUrl(item)" alt />
          </div>
          <v-progress-linear
            v-if="uploading"
            color="teal"
            buffer-value="0"
            :value="uploadVal"
            stream
          ></v-progress-linear>
        </div>
        <div v-else>
          <div v-if="placeB.imgs" class="image-input-preview">
            <!-- <v-menu v-for="(item, index) in placeB.imgs" :key="index" open-on-hover top offset-y>
              <template v-slot:activator="{ on }">
                <img :src="getFile(item)" alt />
              </template>

              <div>sfd</div>
            </v-menu>-->

            <v-row>
              <v-col v-for="(item, index) in placeB.imgs" :key="index" xs="12" sm="6" md="4" lg="4">
                <v-img :src="getFile(item)"></v-img>
              </v-col>
            </v-row>
          </div>
        </div>
        <div style="text-align:right">
          <v-btn
            v-if="!getAdmin.includes('partyA')"
            style="margin-top:20px"
            color="indigo"
            dark
            large
            @click="createPlace"
            :loading="loading"
          >Sửa địa điểm</v-btn>
          <v-btn
            v-else
            style="margin-top:20px"
            color="indigo"
            dark
            large
            @click="toogleYCAForm"
            :loading="loading"
          >Yêu cầu làm đối tác</v-btn>
        </div>
        <div v-show="showYCA" class="scale-up-ver-top">
          <p class="display-1 text--primary">Nhập thông tin bạn đề nghị</p>
          <v-form ref="form2">
            <v-text-field
              outlined
              v-model="form2.name"
              :counter="150"
              label="Tên địa điểm"
              required
            ></v-text-field>
            <div>
              <v-text-field outlined v-model="form2.address" label="Địa chỉ" required>
                <v-btn
                  style="height: 56px;
    margin-top: -17px;"
                  slot="append-outer"
                  color="purple"
                  dark
                  extra-large
                  @click="mapModalVisible=true"
                >
                  <i style="font-size: 24px;" class="fas fa-map-marker-alt"></i>
                </v-btn>
              </v-text-field>
              <v-row style="margin-top:-35px">
                <v-col xs="12" sm="6">
                  <v-text-field
                    dense
                    outlined
                    type="number"
                    v-model="form2.location.lat"
                    label="Lat"
                    required
                  ></v-text-field>
                </v-col>
                <v-col xs="12" sm="6">
                  <v-text-field
                    dense
                    outlined
                    type="number"
                    v-model="form2.location.lng"
                    label="Lng"
                    required
                  ></v-text-field>
                </v-col>
              </v-row>
            </div>

            <v-text-field
              outlined
              type="number"
              v-model="form2.price"
              label="Chi phí đề nghị"
              required
            >
              <v-select
                style="margin-top: -17px;"
                :items="timeUnits"
                v-model="form2.timeUnit"
                label="Đơn vị"
                outlined
                slot="append-outer"
              ></v-select>
            </v-text-field>
            <v-row>
              <v-col cols="12" lg="6" sm="6">
                <v-text-field
                    dense
                    outlined
                    type="date"
                    v-model="form2.start"
                    label="Từ"
                    required
                  ></v-text-field>
              </v-col>
              <v-col cols="12" lg="6" sm="6">
                <v-text-field
                    dense
                    outlined
                    type="date"
                    v-model="form2.end"
                    label="Đến"
                    required
                  ></v-text-field>
              </v-col>
            </v-row>

            <div style="text-align:right">
              <v-btn
                @click="rentPlace()"
                style="margin-top:20px"
                color="indigo"
                dark
                large
              >Gửi yêu cầu đến người cho thuê</v-btn>
            </div>
          </v-form>
        </div>
      </v-form>
    </v-card>
  </div>
</template>
<script>
import { SET_BREADCRUMB } from "@/store/breadcrumbs.module";
import MapMarker from "@/views/partials/content/map/MapMarker.vue";
import { upload } from "@/api/file/file";
import { rentPlace } from "@/api/party-a/place.js";
import {
  getDetail,
  createPlace,
  genNewCode
} from "@/api/party-b/place-for-rent.js";
import { GetRole } from "@/utils/auth";
import { BASE_API } from "@/utils/base";
export default {
  created() {},
  components: {
    MapMarker
  },
  computed: {
    pId() {
      return this.$route.params.id;
    },
    qrString() {
      if (this.placeB.code) {
        return "PLB-" + this.placeB.code;
      }
      return "";
    },
    getAdmin() {
      return GetRole();
    }
  },

  mounted() {
    this.fetchData();
  },
  methods: {
    getFile(img) {
      return BASE_API + "file/get/" + img;
    },
    genNewCode() {
      this.genning = true;
      genNewCode({
        id: this.pId
      })
        .then(res => {
          console.log(res);
          this.placeB.code = res.data.code;
          this.genning = false;
        })
        .catch(() => {
          this.genning = false;
        });
    },
    rentPlace() {
      var req = this.form2;
      req.time_unit = this.form2.timeUnit;
      req.p_id = this.pId;
      rentPlace(req).then(res => {
        console.log(res);
        this.$toast.open({
      message: "Đã gửi yêu cầu thuê",
      type: 'success',
      position:'top-right',
      duration:3000
  });
      });
    },
    createPlace() {
      var formData = new FormData();
      for (let i in this.form.files) {
        formData.append(i, this.form.files[i]);
      }

      this.uploading = true;
      this.loading = true;
      upload(formData, this.uploadProgress)
        .then(res1 => {
          this.uploading = false;
          var fileLst = res1.data;
          var imgs = [];
          for (let i in fileLst) {
            imgs.push(fileLst[i]);
          }
          var req = this.form;
          req.imgs = imgs;
          req.time_unit = this.form.timeUnit;
          createPlace(req)
            .then(res2 => {
              console.log(res2);
              this.loading = false;
            })
            .catch(() => {
              this.uploading = false;
              this.loading = false;
            });
        })
        .catch(() => {
          this.uploading = false;
          this.loading = false;
        });
    },
    fetchData() {
      getDetail({
        id: this.pId
      }).then(res => {
        console.log(res);
        if (!res.data.pl_b.code) {
          res.data.pl_b.code = "";
        }
        this.placeB = res.data.pl_b;
        this.isMineB = res.data.is_mine_b;
        this.form.name = this.placeB.name;
        this.form.address = this.placeB.address;
        this.form.location.lat = this.placeB.lat_lng.lat;
        this.form.location.lng = this.placeB.lat_lng.lng;
        this.form.price = this.placeB.price;
        this.form.timeUnit = this.placeB.time_unit;

        this.form2.name = this.placeB.name;
        this.form2.address = this.placeB.address;
        this.form2.location.lat = this.placeB.lat_lng.lat;
        this.form2.location.lng = this.placeB.lat_lng.lng;
        this.form2.price = this.placeB.price;
        this.form2.timeUnit = this.placeB.time_unit;

        this.$store.dispatch(SET_BREADCRUMB, [
          { title: "Địa điểm", route: "" },
          {
            title: res.data.pl_b.name,
            route: "/place/" + this.pId + "/details"
          },
          { title: "Chi tiết", route: "/place/" + this.pId + "/details" }
        ]);
      });
    },
    uploadProgress(e) {
      this.uploadVal = (e.loaded / e.total) * 100;
    },
    getUrl(file) {
      return URL.createObjectURL(file);
    },
    onLocationPicked(lo) {
      this.form.address = lo.name;
      this.form.location.lat = lo.lat;
      this.form.location.lng = lo.lng;

      this.mapModalVisible = false;
    },
    onCanceled() {
      this.mapModalVisible = false;
    },
    toogleYCAForm() {
      this.showYCA = !this.showYCA;
    }
  },
  data() {
    return {
      mapModalVisible: false,
      uploading: false,
      loading: false,
      uploadVal: 0,
      genning: false,
      isMineB: false,
      showYCA: false,
      timeUnits: [
        {
          text: "Ngày",
          value: "day"
        },
        {
          text: "Tuần",
          value: "week"
        },
        {
          text: "Tháng",
          value: "moth"
        },
        {
          text: "Năm",
          value: "year"
        }
      ],
      placeB: {},
      form: {
        name: "",
        address: "",
        location: {
          lat: null,
          lng: null
        },
        timeUnit: null,
        price: null,
        files: null
      },
      form2: {
        name: "",
        address: "",
        location: {
          lat: null,
          lng: null
        },
        timeUnit: null,
        price: null,
        files: null,
        start:'',
        end:''
      }
    };
  }
};
</script>