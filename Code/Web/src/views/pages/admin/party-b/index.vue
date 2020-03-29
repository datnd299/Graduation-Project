<template>
<div>
<d-loading :isLoading="isLoading"></d-loading>
  <v-data-table
    v-model="selected"
    :headers="headers"
    :items="desserts"
    :single-select="singleSelect"
    item-key="name"
    class="elevation-1"
  >
  <template v-slot:item.address="{ item }">
    {{item.province}} - {{item.district}} - {{item.address}}
  </template>
    <template v-slot:item.com_acc="{ item }">
      <v-btn-toggle
       dense color="#033"
          >
          <router-link :to="'/party-b/'+item['_id']+'/places'">
            <v-btn small color="primary">
            <i class="fas fa-sign" style="color:white"></i>
            <!-- <v-icon>fas fa-users</v-icon> -->
          </v-btn>
          </router-link>
          

          <v-btn depressed  small color="red" text-color="white">
            <i class="fas fa-times" style="color:white"></i>
            <!-- <v-icon style="color:red">fas fa-times</v-icon> -->
          </v-btn>
          
        </v-btn-toggle>
    </template>
  </v-data-table>
  </div>
</template>

<script>

import { SET_BREADCRUMB } from "@/store/breadcrumbs.module";
import {getPartyBs} from '@/api/admin/users'
export default {
  data() {
    return {
      isLoading:false,
        singleSelect: false,
        selected: [],
        headers: [
          
          { text: 'Tên', value: 'name' },
          { text: 'Địa chỉ', value: 'address' },
          { text: 'Số điện thoại', value: 'phone' },
          { text: 'Email', value: 'email' },
          { text: 'Actions', value: 'com_acc' },
        ],
        desserts: [
        
        ],
      }
    
  },
  methods:{
    fetchData(){
      this.isLoading = true;
      getPartyBs().then(res=>{
        console.log(res);
        this.desserts = res.data;
        this.isLoading = false;
      })
    }
  },
  components: {
 
  },
  mounted() {
    this.$store.dispatch(SET_BREADCRUMB, [
      { title: "Admin", route: "" },
      { title: "QL Bên thuê", route: "party-a" }
    ]);
    this.fetchData();
  }
};
</script>
