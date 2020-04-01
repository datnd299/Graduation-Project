<template>
  <div>
    <d-loading :isLoading="isLoading"></d-loading>
    <v-data-table :headers="headers" :items="desserts" item-key="name" class="elevation-1">
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
      {{dayRemain(com.createdAt)}}
    </v-chip>
      </template>
      <span>Thời gian dùng thử còn lại</span>
    </v-tooltip>
    
  </template>
    <template v-slot:item.role="{ item }">
      <v-chip
      class="ma-2"
      :color="item.role|roleStatusColor"
      label=""
      text-color="white"
    >
      {{item.role|roleStatusText}}
    </v-chip>
    </template>
      <template v-slot:item.acc_ac="{ item }">
       <v-btn-toggle
       dense color="#033"
          >
         
          
          <v-btn :loading="item.isLoading" v-if="item.status!=2" depressed  small color="green" @click="approveAcc(item,2)" text-color="white">
            <i class="fas fa-check" style="color:white"></i>
            <!-- <v-icon style="color:red">fas fa-times</v-icon> -->
          </v-btn>
          <v-btn :loading="item.isLoading"  v-if="item.status!==0" depressed  small color="red" @click="approveAcc(item,0)"  text-color="white">
            <i class="fas fa-times" style="color:white"></i>
            <!-- <v-icon style="color:red">fas fa-times</v-icon> -->
          </v-btn>
          
        </v-btn-toggle>
      </template>
    </v-data-table>
  </div>
</template>

<script>
import { getPartyAAccs,approveAcc } from "@/api/admin/users";

import { SET_BREADCRUMB } from "@/store/breadcrumbs.module";

export default {
  data() {
    return {
      isLoading: false,
      com:{},
      headers: [
        { text: "TK", value: "acc_name" },
        { text: "Tên", value: "name" },
        { text: "Email", value: "email" },
        { text: "Quyền", value: "role",align:'center' },
        { text: 'Trạng thái', value: 'status',align:'center' },
        { text: "Actions", value: "acc_ac" }
      ],
      desserts: []
    };
  },
  methods: {
    dayRemain(time){
      if(time){

      
      var datediff = Date.today().getTime()-Date.parse(time).getTime();
      return (30-((datediff / (24*60*60*1000))+1)).toFixed(0);
      }
      return '30'
    },
    approveAcc(acc,status){
       acc.isLoading  = true
          approveAcc({
              id:acc._id,
              status:status
          }).then(res=>{
              acc.isLoading  = false
            console.log(res);
            acc.status =status;
            
          })
    },
    fetchData() {
      this.isLoading = true;
      getPartyAAccs({
        p_id: this.pId
      }).then(res => {
        this.isLoading = false;
        if (res.data) {
          this.com = res.data;
          res.data.accs.forEach(e=>{
            e.isLoading = false;
          })
          this.desserts =  res.data.accs;
          this.$store.dispatch(SET_BREADCRUMB, [
            { title: "Admin", route: "" },
            { title: "QL Bên thuê", route: "/admin/party-a" },
            { title: res.data.name, route: "/admin/party-a/"+this.pId },
            { title: "Tài khoản", route: "/admin/party-a/"+this.pId+"/users" }
          ]);
        }
      });
    }
  },
  computed: {
    pId() {
      return this.$route.params.id;
    }
  },
  components: {},
  mounted() {
    this.fetchData();
  }
};
</script>
