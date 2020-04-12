<template>
  <div>
    <v-data-table
      style="margin-top:10px"
      :loading="isLoading"
      :headers="headers"
      :items="tableData"
      item-key="_id"
      class="elevation-1"
    >
     <template v-slot:item.com_link="{ item }">
        <v-btn-toggle dense color="#033">
          <router-link :to="'/party-b/'+item._id+'/places'">
            <v-btn depressed small color="blue darken-1" text-color="white">
              <i class="fas fa-flag" style="color:white"></i>
              <!-- <v-icon style="color:red">fas fa-times</v-icon> -->
            </v-btn>
          </router-link>
        </v-btn-toggle>
      </template>
    </v-data-table>
  </div>
</template>
<script>
import {getPartner} from '@/api/party-a/partner'
export default {
  data() {
    return {
      isLoading: false,
      headers: [
        {
          text: "Đối tác",
          align: "start",
          value: "name"
        },
        { text: "Email", value: "email" },
        { text: "SĐT", value: "phone" },
        { text: "Địa chỉ", value: "province" },
        { text: "Số lượng thuê", value: "count_places", align: "center" },
        { text: "Links", value: "com_link", align: "center" },
        { text: "Acctions", value: "com_ac", align: "center" }
      ],
      tableData: []
    };
  },
  created(){
    this.isLoading = true;
      getPartner().then(res=>{
        this.isLoading = false;
        this.tableData = res.data;
      })
  }
};
</script>