<template>
  <div>
    <router-link to="/party-a/signboards/new-signboard">
      <v-btn large color="primary">
        <i class="fas fa-plus"></i> &nbsp; &nbsp; Thêm biển quảng cáo
      </v-btn>
    </router-link>
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