<template>
    <div>
        <!-- <v-dialog v-model="placeModalVisible" persistent max-width="700">

      <v-card >
        <v-card-title class="headline">Địa điểm cho thuê treo biển</v-card-title>
        <v-card-text>Let Google help apps determine location. This means sending anonymous location data to Google, even when no apps are running.</v-card-text>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="green darken-1" text @click="togglePlaceModal()">Disagree</v-btn>
          <v-btn color="green darken-1" text @click="togglePlaceModal()">Agree</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog> -->
        <router-link to="/party-b/new-place-for-rent">
            <v-btn @click="togglePlaceModal()" large color="primary"><i class="fas fa-plus"></i> &nbsp; &nbsp; Thêm địa điểm</v-btn>
        </router-link>
         
        <v-row >
            <v-col v-for="(item,index) in tableData" :key="index" xs="12" sm="6" md="4" lg="3">
                <single-place :place="item"></single-place>
            </v-col>
        </v-row>
    </div>
</template>
<script>
import SinglePlace from './components/SinglePlace'
import { SET_BREADCRUMB } from "@/store/breadcrumbs.module";
import {getMine} from '@/api/party-b/place-for-rent.js'
export default {
    components:{
        SinglePlace
    },
    data() {
        return {
            placeModalVisible:false,
            tableData:[],
            isLoading :false
        }
    },
    created(){
        this.$store.dispatch(SET_BREADCRUMB, [
      { title: "Đối tác", route: "" },
      { title: "Địa điểm của tôi", route: "/party-b/places-for-rent" }
      
    ]);
    this.fetchData();
    },
    methods: {
        togglePlaceModal(){
            this.placeModalVisible = !this.placeModalVisible
        },
        fetchData(){
            this.isLoading = true;
            getMine().then(res=>{
                this.isLoading = false;
                this.tableData = res.data;
                
            })
        }
    }

}
</script>
