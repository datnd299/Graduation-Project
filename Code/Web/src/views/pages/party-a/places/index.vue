<template>
  <div>
    <div>
      <router-link to="/qr-scanner">
      <v-btn large color="primary">
        <v-icon small="" left="">fas fa-plus</v-icon>Thêm Địa điểm của đối tác
      </v-btn>
    </router-link>
<router-link style="float:right" to="/party-a/places/map" >
      
      <v-btn large color="primary">
      Bản đồ Địa điểm
    </v-btn>
    </router-link>
    </div>
    
    <v-data-table style="margin-top:10px"
      :loading="isLoading"
      :headers="headers"
      :items="tableData"
      item-key="_id"
      class="elevation-1"
    >
      <template v-slot:item.pt_b_name="{ item }">
        <span>{{item.place_id.owner.name}}</span>
      </template>
      <template v-slot:item.com_time="{ item }">
        <span>
          {{Date.parse(item.start).toString('dd/MM/yyyy')}}
          <i class="fas fa-arrow-right"></i>
          {{Date.parse(item.end).toString('dd/MM/yyyy')}}
        </span>
      </template>
      <template v-slot:item.price="{ item }">
        <span>{{item.price|numberF}} / {{item.time_unit|timeUnitText}}</span>
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
            <v-btn depressed small color="blue darken-1" text-color="white">
              <i class="fas fa-ellipsis-h" style="color:white"></i>
              <!-- <v-icon style="color:red">fas fa-times</v-icon> -->
            </v-btn>
          </router-link>
          <router-link :to="'/party-a/report/place/'+item._id+'/tasks'">
            <v-btn depressed small color="purple" text-color="white">
              <i class="fas fa-file-medical-alt" style="color:white"></i>
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
import { getPlaces } from "@/api/party-a/place";
export default {
  data() {
    return {
      isLoading: false,
      headers: [
        {
          text: "Địa điểm",
          align: "start",
          value: "name"
        },
        { text: "Đối tác", value: "pt_b_name" },
        { text: "Thời gian thuê", value: "com_time" },
        { text: "Chi phí", value: "price" },
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
    getPlaces().then(res => {
      this.isLoading = false;
      this.tableData = res.data;
    });
    this.$store.dispatch(SET_BREADCRUMB, [
      { title: "Bên thuê", route: "/party-a" },
      { title: "Địa điểm", route: "/party-a/places" }
    ]);
  }
};
</script>