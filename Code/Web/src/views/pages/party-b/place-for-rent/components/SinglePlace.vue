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
      <v-icon small left>fas fa-money-bill</v-icon>
      {{place.price|numberF}}đ / {{place.time_unit|timeUnitText}}
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
        <router-link :to="'/place/'+place._id+'/details'">
      <v-btn
        color="purple"
        text
      >
        Chi tiết
      </v-btn>
      </router-link>

      <v-spacer></v-spacer>

      
    </v-card-actions>

    
  </v-card>
</template>
<script>
import {BASE_API} from '@/utils/base'
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
          return BASE_API+'file/get/'+this.place.imgs[0]
        }
        return 'sdf'
      }
    },
}
</script>