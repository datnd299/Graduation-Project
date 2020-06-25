<template>
  <div>
    <div>
      <router-link to="/party-a/signboards/new-signboard">
      <v-btn large color="primary">
        <v-icon small="" left="">fas fa-plus</v-icon>Thêm biển quảng cáo
      </v-btn>
    </router-link>
    <router-link style="float:right" to="/party-a/report/signboards">
      <v-btn large color="primary">
        <v-icon small="" left="">fas fa-scroll</v-icon>Bản đồ biển quảng cáo
      </v-btn>
    </router-link>
    </div>
    
    <v-row >
            <v-col v-for="(item,index) in tableData" :key="index" xs="12" sm="6" md="4" lg="3">
                <single-signboard :signboard="item"></single-signboard>
            </v-col>
        </v-row>
  </div>
</template>
<script>
import SingleSignboard from './components/SingleSignboard'
import { getSignboards } from "@/api/party-a/signboard";
import { SET_BREADCRUMB } from "@/store/breadcrumbs.module";
export default {
  data() {
    return {
      tableData: []
    };
  },
  components:{
      SingleSignboard
  },
  created() {
    this.$store.dispatch(SET_BREADCRUMB, [
      { title: "Bên thuê", route: "/party-a" },
      { title: "Biển quảng cáo", route: "/party-a/signboards" }
    ]);
    getSignboards().then(res => {
      this.tableData = res.data;
    });
  }
};
</script>