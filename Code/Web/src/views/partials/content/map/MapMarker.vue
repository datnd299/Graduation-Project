<template>
  <div style="height:100%">
    <div style="padding-left:5px;padding-right:5px;padding-bottom:5px">
      <v-row>
        <v-col xs="12" sm="6">
            <v-text-field dense outlined type="number"  v-model="input.lat"  label="Lat" required>
            </v-text-field>
        </v-col>
        <v-col xs="12" sm="6">
            <v-text-field dense outlined type="number" v-model="input.lng"   label="Lng" required>
             
            </v-text-field>
        </v-col>
      </v-row>
      <v-row style="margin-top:-30px">
        <v-col >
          <v-text-field dense outlined  v-model="input.name"  label="Địa chỉ" required>
             <div slot="append-outer" style="display:innline-block;    width: 133px;">
                <v-btn
              style="height: 41px;
    margin-top: -8px; margin-right:5px"
              color="green"
              dark
              extra-large
              @click="pick()"
            >
              <i style="font-size: 18px;" class="far fa-check-square"></i>
            </v-btn>
             <v-btn
              style="height: 41px;
    margin-top: -8px;"
              color="red accent-4"
              dark
              extra-large
               @click="cancel()"
            >
              <i style="font-size: 18px;" class="far fa-window-close"></i>
            </v-btn>
              </div>
            </v-text-field>
        </v-col>
        
      </v-row>
    </div>
    <div style="border:1px solid #dfe6e9;margin-top:-30px;margin-left:5px margin-right:5px">
    <GmapMap :center="center" :zoom="16" style="width:100%; height: 600px;" @click="mapClicked">
      
      <gmap-marker
        :icon="{ url: '/assets/media/icons/svg/Map/Marker.png'}"
       
       
        :position="{ lat: input.lat, lng: input.lng }"
      ></gmap-marker>

      
    </GmapMap>
    </div>
  </div>
</template>
<script>

export default {
  data() {
    return {
      center: { lat: 21.004988169511012, lng: 105.84349504177669 },
      
      input: {
        lat: 0,
        lng: 0,
        name:''
      }
    };
  },
  props:{
    location:{
      type:Object
    }
  },
  created() {
    if(this.location){
      this.input.lat = this.location.location.lat;
      this.input.lng = this.location.location.lng;
     this.input.name = this.location.address;
     if(this.location.location.lat&&this.location.location.lng){
       this.center= { lat: this.location.location.lat, lng: this.location.location.lng };
     }
     
    }
  },

  methods: {
   mapClicked(e){
     var lat = e.latLng.lat();
     var lng = e.latLng.lng();
  this.input.lat = lat;
  this.input.lng = lng;
     
   },
   pick(){
     this.$emit('picked',this.input);
   },
   cancel(){
     this.$emit('canceled',this.input);
   }
  },
  watch: {
   location(){
      if(this.location){
      this.input.lat = this.location.location.lat;
      this.input.lng = this.location.location.lng;
     this.input.name = this.location.address;
     this.center= { lat: this.location.location.lat, lng: this.location.location.lng };
    }
    }
  }
};
</script>