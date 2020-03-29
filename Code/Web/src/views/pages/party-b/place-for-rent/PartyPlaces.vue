<template>
    <div>
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
import {getByParty} from '@/api/party-b/place-for-rent.js'
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
        
    this.fetchData();
    },
    computed:{
        pID(){
            return this.$route.params.id;
        }
    },
    methods: {
        togglePlaceModal(){
            this.placeModalVisible = !this.placeModalVisible
        },
        fetchData(){
            this.isLoading = true;
            getByParty({
                p_id:this.pID
            }).then(res=>{
                this.isLoading = false;
                this.tableData = res.data.places;
                this.$store.dispatch(SET_BREADCRUMB, [
      { title: "Đối tác", route: "" },
      { title: res.data.pt_b.name, route: "/party-b/"+this.pID+"/places" },
      { title: "Địa điểm cho thuê", route: "/party-b/"+this.pID+"/places"  }
      
    ]);
            })
        }
    }

}
</script>
