<template>
  <div>
      <v-card
    class="mx-auto"
    style="padding:20px"
    outlined
  >
 <v-list-item-title class="headline mb-1">Nhập thông tin điểm treo</v-list-item-title>
 <br>
 <br>
    <v-form ref="form" v-model="valid">
      <v-text-field outlined v-model="form.name" :counter="150" label="Tên địa điểm" required></v-text-field>
        <div>
            <v-text-field outlined v-model="form.address"  label="Địa chỉ" required>
                <v-btn style="height: 56px;
    margin-top: -17px;" slot="append-outer" color="purple" dark extra-large><i style="font-size: 24px;" class="fas fa-map-marker-alt"></i></v-btn>
            </v-text-field>
        </div>
      
      <v-text-field outlined v-model="form.price"  label="Giá đề nghị" required></v-text-field>
      <div>
          <v-file-input outlined chips multiple v-model="form.files"  label="Ảnh minh họa"></v-file-input>
      <div v-if="form.files" class="image-input-preview">
          <img v-for="(item, index) in form.files" :key="index" :src="getUrl(item)" alt="">
      </div>
      </div>
      <div style="text-align:right">
<v-btn style="margin-top:20px" color="indigo"   dark large>Thêm địa điểm</v-btn>
      </div>
      
    </v-form>
    <map-marker></map-marker>
      </v-card>
  </div>
</template>
<script>
import { SET_BREADCRUMB } from "@/store/breadcrumbs.module";
import MapMarker from '@/views/partials/content/map/MapMarker.vue'
export default {
  created() {
    this.$store.dispatch(SET_BREADCRUMB, [
      { title: "Đối tác", route: "" },
      { title: "Thêm điểm treo", route: "/party-b/new-place-for-rent" }
    ]);
  },
  components:{
      MapMarker
  },
  methods:{
      getUrl(file){
          return URL.createObjectURL(file)
      }
  },
  data() {
      return {
          form:{
          name:'',
          address:'',
          price:null,
          files:null
          
        }
      }
  },
};
</script>