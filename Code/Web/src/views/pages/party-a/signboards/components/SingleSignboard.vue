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
      {{signboard.name}}
    </v-card-title>

    <v-card-subtitle>
      {{signboard.code|codeSignboard}}
    </v-card-subtitle>

     
      
      
    <v-card-actions>
     
        <router-link :to="'/party-a/signboards/'+signboard._id">
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
    signboard:{
      type:Object,
      default(){
        return {}
      }
    },
    
  },
  computed: {
      thumbImg(){
        console.log(this.signboard);
        
        if(this.signboard&&this.signboard.imgs&&this.signboard.imgs.length>0){
          return BASE_API+'file/get/'+this.signboard.imgs[0]
        }
        return 'sdf'
      }
    },
}
</script>