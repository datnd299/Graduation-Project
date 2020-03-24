<template>
<div>


  <v-data-table
    v-model="selected"
    :headers="headers"
    :items="desserts"
    :single-select="singleSelect"
    item-key="name"
    class="elevation-1"
  >
    <template v-slot:item.acc_ac="{ item }">
      <v-btn-toggle v-model="toggle_none" 
       dense color="#033"
          >
          <!-- <router-link :to="'/admin/party-a/'+item.com_id+'/users'">
            <v-btn small>
            <i class="fas fa-users"></i>
          </v-btn>
          </router-link> -->
          

          <v-btn small>
            <i class="fas fa-times"></i>
            <!-- <v-icon style="color:red">fas fa-times</v-icon> -->
          </v-btn>
          
        </v-btn-toggle>
    </template>
  </v-data-table>
  </div>
</template>

<script>

import { SET_BREADCRUMB } from "@/store/breadcrumbs.module";

export default {
  data() {
    return {
        singleSelect: false,
        selected: [],
        headers: [
          { text: 'TK', value: 'acc_name' },
          { text: 'Tên', value: 'acc_fullname' },
          { text: 'Số điện thoại', value: 'acc_phone' },
          { text: 'Email', value: 'accemail' },
          { text: 'Actions', value: 'acc_ac' },
        ],
        desserts: [
         {
           acc_id:1,
           acc_name:'admin1',
           acc_fullname:'Công ty 1',
           acc_phone:'0123456412',
           acc_email:'ct1@ct1.com',
           acc_role:1,
           acc_status:2
         }
        ],
      }
    
  },
  components: {
 
  },
  mounted() {
    this.$store.dispatch(SET_BREADCRUMB, [
      { title: "Admin", route: "" },
      { title: "QL Bên thuê", route: "/admin/party-a" },
      { title: "Công ty 1", route: "/admin/party-a/1" },
      { title: "Tài khoản", route: "/admin/party-a/1/users" }
    ]);
  }
};
</script>
