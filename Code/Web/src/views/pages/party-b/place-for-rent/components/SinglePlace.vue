<template>
    <v-card
    class="mx-auto"
    max-width="344"
  >
    <v-img
      :src="thumbImg"
      height="200px"
    ></v-img>

    <v-card-title>
      {{place.name}}
    </v-card-title>

    <v-card-subtitle>
      {{place.address}}
    </v-card-subtitle>
     <v-card-subtitle style="margin-top:-20px">
       <v-btn small="" icon color="purple">
              <v-icon>fas fa-map-marked-alt</v-icon>
            </v-btn>
       
    <v-chip
    small=""
    style="float:right"
      class="ma-2"
      color="indigo darken-3"
      outlined
    >
      <v-icon small left>fas fa-dollar-sign</v-icon>
      {{place.price}} / {{place.time_unit}}
    </v-chip>
     </v-card-subtitle>
     
      
      
    <v-card-actions>
      <v-chip
      small
      class="ma-2"
      color="blue lighten-1"
      label
      text-color="white"
    >
   
      Chưa cho thuê
    </v-chip>

      <v-btn
        color="purple"
        text
      >
        Chi tiết
      </v-btn>

      <v-spacer></v-spacer>

      
    </v-card-actions>

    
  </v-card>
</template>
<script>
export default {
  props:{
    place:{
      type:Object,
      default(){
        return {}
      }
    },
    
  },
  computed: {
      thumbImg(){
        console.log(this.place);
        
        if(this.place&&this.place.imgs&&this.place.imgs.length>0){
          return 'http://localhost:8086/api/v1/file/get/'+this.place.imgs[0]
        }
        return 'sdf'
      }
    },
}
</script>