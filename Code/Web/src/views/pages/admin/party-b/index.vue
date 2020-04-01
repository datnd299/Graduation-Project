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
  <template v-slot:item.status="{ item }">
    <v-chip
      class="ma-2"
      :color="item.status|partyStatusColor"
      label
      small=""
      text-color="white"
    >
      {{item.status|partyStatusText}}
    </v-chip>
    <v-tooltip v-if="item.status!=2&&item.status!==0" bottom>
      <template v-slot:activator="{ on }">
        <v-chip v-on="on"
      class="ma-2"
      color="pink"
      label
      small=""
      text-color="white"
    >
      {{dayRemain(item.createdAt)}}
    </v-chip>
      </template>
      <span>Thời gian dùng thử còn lại</span>
    </v-tooltip>
    
  </template>
  <template v-slot:item.com_link="{ item }">
    <v-btn-toggle
       dense color="#033"
          >
          <router-link :to="'/party-b/'+item['_id']+'/places'">
            <v-btn small color="primary">
            <i class="fas fa-sign" style="color:white"></i>
            <!-- <v-icon>fas fa-users</v-icon> -->
          </v-btn>
          </router-link>
    </v-btn-toggle>
  </template>
    <template v-slot:item.com_acc="{ item }">
      <v-btn-toggle
       dense color="#033"
          >
          
          

          <v-btn :loading="item.loading" v-if="item.status!=2" depressed  small color="green" @click="approvePartyB(item,2)" text-color="white">
            <i class="fas fa-check" style="color:white"></i>
            <!-- <v-icon style="color:red">fas fa-times</v-icon> -->
          </v-btn>
          <v-btn :loading="item.loading"  v-if="item.status!==0" depressed  small color="red" @click="approvePartyB(item,0)"  text-color="white">
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
import { approvePartyB } from "@/api/admin/users";
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
          { text: 'Trạng thái', value: 'status',align:'center' },
          { text: 'Links', value: 'com_link',align:'center' },
          { text: 'Actions', value: 'com_acc',align:'center' },
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
        res.data.forEach(e=>{
          e.loading=false;
        })
        this.desserts = res.data;
        this.isLoading = false;
      })
    },
    approvePartyB(party,nStatus){
      party.loading = true;
      approvePartyB({
        p_id:party._id,
        status:nStatus
      }).then(res=>{
          party.loading = false;
        party.status = nStatus;
        console.log(res);
        
      })
    },
    dayRemain(time){
      if(time){

      
      var datediff = Date.today().getTime()-Date.parse(time).getTime();
      return (30-((datediff / (24*60*60*1000))+1)).toFixed(0);
      }
      return '30'
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
