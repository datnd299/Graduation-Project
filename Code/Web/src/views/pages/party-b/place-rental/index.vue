<template>
  <div>
    
    
    <v-data-table
      :loading="isLoading"
      :headers="headers"
      :items="tableData"
      item-key="_id"
      class="elevation-1"
    >
      <template v-slot:item.createdAt="{ item }">
        <span>{{Date.parse(item.createdAt).toString('dd/MM/yyyy HH:mm')}}</span>
      </template>
      <template v-slot:item.status="{ item }">
        <v-chip
          class="ma-2"
          :color="item.status|placeRentalStatusColor"
          label
          text-color="white"
        >{{item.status|placeRentalStatusText}}</v-chip>
      </template>
      <template v-slot:item.com_link="{ item }">
        <v-btn-toggle dense color="#033">
            <router-link :to="'/party-b/places-rental/'+item._id+'/details'">
                <v-btn
         
            depressed
            small
            color="blue darken-1"
            
            text-color="white"
          >
            <i class="fas fa-ellipsis-h" style="color:white"></i>
            <!-- <v-icon style="color:red">fas fa-times</v-icon> -->
          </v-btn>
            </router-link>
          
        </v-btn-toggle>
      </template>
      <template v-slot:item.com_ac="{ item }">
        <v-btn-toggle dense color="#033">
          <v-btn
            v-if="item.status!='0'"
            depressed
            small
            color="red"
            @click="approvePlaceRental(item,0)"
            text-color="white"
          >
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
import { getMyPlacesRental } from "@/api/party-b/place-for-rent";
export default {
  data() {
    return {
      isLoading: false,
      headers: [
        {
          text: "Địa điểm",
          align: "start",
          value: "place_name"
        },
        { text: "Bên thuê", value: "pt_a_name" },
        { text: "Thời gian yêu cầu", value: "createdAt" },
        { text: "Trạng thái", value: "status", align: "center" },
        { text: "Links", value: "com_link", align: "center" },
        { text: "Acctions", value: "com_ac", align: "center" }
      ],
      tableData: []
    };
  },
  methods: {
    approvePlaceRental(item, status) {
      console.log(item);
      console.log(status);
    }
  },
  created() {
    this.isLoading = true;
    getMyPlacesRental().then(res => {
      this.isLoading = false;
      this.tableData = res.data;
    });
    this.$store.dispatch(SET_BREADCRUMB, [
      { title: "Đối tác", route: "/party-b" },
      { title: "Yêu cầu / hợp đồng cho thuê", route: "/party-b/places-rental" }
    ]);
  }
};
</script>