<template>
  <div style="height:100%">
    <div style="padding-left:5px;padding-right:5px;padding-bottom:5px">
      <el-row :gutter="10">
        <el-col style="margin-top:5px" :xs="12" :sm="8" :md="8">
          <label>Kinh độ</label>
          <el-input-number
            controls-position="right"
            size="small"
            style="width:100%"
            v-model="input.lng"
            :min="-180"
            :max="180"
            :step="0.001"
          ></el-input-number>
        </el-col>
        <el-col style="margin-top:5px"  :xs="12" :sm="8" :md="8">
          <label>Vĩ độ</label>
          <el-input-number
            controls-position="right"
            size="small"
            style="width:100%"
            v-model="input.lat"
            :min="-180"
            :max="180"
            :step="0.001"
          ></el-input-number>
        </el-col>
        <el-col style="margin-top:5px"  :xs="24" :sm="8" :md="8">
          <label>Bán kính (m)</label>
          <el-input-number
            controls-position="right"
            size="small"
            style="width:calc(100% - 72px)"
            v-model="input.radius"
            :min="0"
            :step="1"
          ></el-input-number>
          <el-button style="float:right" type="primary" size="small" @click="$emit('valueSelected',input)">Chọn</el-button>
        </el-col>
      </el-row>
    </div>
    <div style="border:1px solid #dfe6e9;margin:5px">

    
    <GmapMap :center="center" :zoom="16" style="width:100%; height: 600px;">
      <template v-if="markers">
        <gmap-marker
        :icon="{ url: 'fd'}"
       
        :key="index"
        v-for="(m, index) in markers"
        :position="{ lat: m.Latitude, lng: m.Longitude }"
      ></gmap-marker>
      </template>
      

      <GmapCircle
        :draggable="true"
        :center="circle"
        @radius_changed="radiusChanged"
        @center_changed="centerChanged"
        :radius="circle.radius"
        :editable="true"
        :options="{fillColor:'#f1c40f',fillOpacity:0.1,strokeColor:'#16a085',strokeWeight:2}"
      ></GmapCircle>

    </GmapMap>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      center: { lat: 21.004988169511012, lng: 105.84349504177669 },
      circle: {
        lat: 0,
        lng: 0,
        radius: 0
      },
      input: {
        lat: 0,
        lng: 0,
        radius: 0
      }
    };
  },
  props:{
      circleData:Object,
      markers:Array,
  },
  created() {
      if(this.circleData){
          this.center.lat=this.circleData.lat,
          this.center.lng=this.circleData.lng,
          this.input.lat=this.circleData.lat,
          this.input.lng=this.circleData.lng,
          this.input.radius=this.circleData.radius
      }else{
          this.center.lat=21.004988169511012,
          this.center.lng=105.84349504177669,
          this.input.lat=21.004988169511012,
          this.input.lng=105.84349504177669,
          this.input.radius=500
      }
  },
  methods: {
    drawMarkers(){
      
    },
    radiusChanged(r) {
     

      this.input.radius = r;
    },
    centerChanged(e) {
      this.input.lat = e.lat();
      this.input.lng = e.lng();
    },
    radiusTextChanged() {
      this.circle.radius = Number(this.input.radius);
    },
    latTextChanged() {
      this.circle.lat = Number(this.input.lat);
    },
    lngTextChanged() {
      this.circle.lng = Number(this.input.lng);
    }
  },
  watch: {
    "input.radius"() {
      this.radiusTextChanged();
    },
    "input.lat"() {
      this.latTextChanged();
    },
    "input.lng"() {
      this.lngTextChanged();
    }
  }
};
</script>