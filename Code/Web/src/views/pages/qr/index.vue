<template>
<v-app class="v-wrap" xasdf>
  <div style="padding:20px">
      <v-alert v-if="error" type="error">
      {{ error }}
    </v-alert>
   


    <div style="width:100%">
        <div class="mx-auto" style="max-width:500px">
            <v-text-field
            label="Mã"
            v-model="result"
            outlined
          >
          <v-btn
              style="height: 56px;
    margin-top: -17px;"
              slot="append-outer"
              color="indigo"
              dark
              extra-large
              
              @click="go()"
            >
              <i style="font-size: 24px;" class="fas fa-play"></i>
            </v-btn>
          </v-text-field>
        </div>
        <div class="mx-auto" style="max-width:500px">
        <qrcode-stream  @decode="onDecode" @init="onInit" />
        </div>
    
    </div>
    
  </div>
</v-app>
</template>

<script>
import { QrcodeStream } from 'vue-qrcode-reader'
import { SET_BREADCRUMB } from "@/store/breadcrumbs.module";
export default {

  components: { QrcodeStream },

  data () {
    return {
      result: '',
      error: ''
    }
  },
    watch:{
        
    },
    created(){
        this.$store.dispatch(SET_BREADCRUMB, [
      { title: "Nhập code", route: "/qr-scanner" }
  
      
    ]);
    },
  methods: {
    onDecode (result) {
      
      
      this.result = result
      this.go();
    },
    go(){
        if(this.result.includes("PLB-")){
          this.$router.push({path:'/place/'+this.result.substr(4)+'/details'})
        }else{
            alert("Mã không hợp lệ")
        }
    },

    async onInit (promise) {
      try {
        await promise
        this.error = ''
      } catch (error) {
        if (error.name === 'NotAllowedError') {
          this.error = "ERROR: you need to grant camera access permisson"
        } else if (error.name === 'NotFoundError') {
          this.error = "ERROR: no camera on this device"
        } else if (error.name === 'NotSupportedError') {
          this.error = "ERROR: secure context required (HTTPS, localhost)"
        } else if (error.name === 'NotReadableError') {
          this.error = "ERROR: is the camera already in use?"
        } else if (error.name === 'OverconstrainedError') {
          this.error = "ERROR: installed cameras are not suitable"
        } else if (error.name === 'StreamApiNotSupportedError') {
          this.error = "ERROR: Stream API is not supported in this browser"
        }
      }
    }
  }
}
</script>
