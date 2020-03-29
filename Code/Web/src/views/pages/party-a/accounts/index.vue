<template>
  <div>
      <v-dialog v-model="accModalVisible" persistent max-width="290">
      <account @close="accModalVisible=false" @accCreated="onAccCreated"></account>
    </v-dialog>
    <d-loading :isLoading="isLoading"></d-loading>
    <v-btn @click="toggleAccModal()" large color="primary"><i class="fas fa-plus"></i> &nbsp; &nbsp; Thêm tài khoản</v-btn>
    
    <v-data-table :headers="headers" :items="desserts" item-key="name" class="elevation-1" style="margin-top:20px">
      
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
    <template v-slot:item.status="{ item }">
      <v-chip
      class="ma-2"
      :color="item.status|partyStatusColor"
      label=""
      text-color="white"
    >
      {{item.status|partyStatusText}}
    </v-chip>
    </template>
      <template v-slot:item.acc_ac="{ item }">
        <v-btn-toggle
       dense color="#033"
          >
         
          
          <v-btn :loading="item.loading" v-if="item.status==0" depressed  small color="green" @click="approveAccount(item,2)" text-color="white">
            <i class="fas fa-check" style="color:white"></i>
            <!-- <v-icon style="color:red">fas fa-times</v-icon> -->
          </v-btn>
          <v-btn :loading="item.loading"  v-if="item.status!==0" depressed  small color="red" @click="approveAccount(item,0)"  text-color="white">
            <i class="fas fa-times" style="color:white"></i>
            <!-- <v-icon style="color:red">fas fa-times</v-icon> -->
          </v-btn>
          
        </v-btn-toggle>
      </template>
    </v-data-table>
  </div>
</template>

<script>
import { getAccs } from "@/api/party-a/account";
import {approveAcc} from "@/api/admin/users"
import { SET_BREADCRUMB } from "@/store/breadcrumbs.module";
import Account from './components/Account'

export default {
  data() {
    return {
      isLoading: false,

      headers: [
        { text: "TK", value: "acc_name" },
        { text: "Tên", value: "name" },
        { text: "Email", value: "email" },
        { text: "Quyền", value: "role",align:'center' },
        { text: "Trạng thái", value: "status",align:'center' },
        { text: "Actions", value: "acc_ac" }
      ],
      desserts: [],
      accModalVisible:false
    };
  },
  methods: {
      onAccCreated(ac){
          console.log(ac);
          
          this.desserts.unshift(ac);
          this.toggleAccModal();
      },
      approveAccount(acc,status){
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
      toggleAccModal(){
          this.accModalVisible = !this.accModalVisible;
      },
    fetchData() {
      this.isLoading = true;
      getAccs({
        p_id: this.pId
      }).then(res => {
        this.isLoading = false;
        if (res.data) {
            res.data.accs.forEach(e=>{
                e.isLoading = false;
            })
          this.desserts =  res.data.accs;
          this.$store.dispatch(SET_BREADCRUMB, [
            { title: "Bên thuê", route: "/party-a" },
            { title: res.data.name, route: "/party-a/accounts" },
            { title: "Tài khoản", route: "/party-a/accounts" }
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
  components: {
      Account
  },
  mounted() {
    this.fetchData();
  }
};
</script>
