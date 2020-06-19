<template>
  <div style="position:relative">
    <v-text-field style="    height: 31px;" v-model="text" @change="handleChange"
     :placeholder="(side=='party-a'?'Đối tác':'Bên thuê')" filled rounded dense :loading="isLoading">
      <i style="margin-top: 2px;" slot="prepend-inner" class="fa fa-search"></i>
    </v-text-field>
    <div v-if="showResult" style="position: fixed;
    top: 75px; z-index:100;">
      <v-card class="mx-auto" style="width:223px">
          <div style="height:10px">
              <v-chip style="    color: red;
    float: right;
    cursor: pointer;
    padding: 8px;"
    @click="showResult=false"
      
      x-small
    >
      <i class="fas fa-times"></i>
    </v-chip>
          </div>
          
        <v-list>
            <template v-if="result.length>0" >
                <v-list-item v-for="(item, index) in result" :key="index" class="d-list-item" @click="chooseItem(item)">
            <v-list-item-avatar>
              <v-img src="/assets/parnter.png"></v-img>
            </v-list-item-avatar>
            <v-list-item-content>
              <v-list-item-title v-text="item.name"></v-list-item-title>
            </v-list-item-content>
          </v-list-item>
            </template>
            <template v-else>
                <v-list-item-content>
              <v-list-item-title style="    text-align: center;" v-text="'Không tìm thấy dữ liệu'"></v-list-item-title>
            </v-list-item-content>
            </template>
          
        </v-list>
      </v-card>
    </div>
  </div>
</template>
<script>

import {debounce_func} from '@/utils/index'
import {getPartner} from '@/api/party-a/partner'
export default {
  data() {
    return {
      isLoading: false,
      text: "",
      showResult:false,
      result:[],
    };
  },
  created() {
   
  },
  props:{
    side:String
  },
  methods: {
      handleChange(){
          console.log('a');
          
      },
      chooseItem(item){
        this.$emit('choosed',item);
        this.showResult = false;
      }
  },
  watch: {
    text:debounce_func(function (newVal) {
        this.isLoading = true;
       
        getPartner({
            q:newVal
        }).then(res=>{
            this.isLoading = false;
            this.showResult = true;
            this.result = res.data;
            
        })
        
      }, 500)
  }
};
</script>
<style lang="css" scoped>
.d-list-item:hover {
  background: #ecf0f1;
  cursor: pointer;
}
</style>