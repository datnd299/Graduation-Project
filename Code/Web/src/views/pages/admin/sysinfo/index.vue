<template>
  <div>
    

    

    <div class="row">
      

      <div class="col-xl-4 col-lg-4 order-lg-2 order-xl-1">
        <KTPortlet
          v-bind:class="'kt-portlet--height-fluid'"
          v-bind="{ bodyFit: true }"
        >
          <template v-slot:body>
            <Widget14_2 v-if="info" :cpuInfo="info.cpu"></Widget14_2>
          </template>
        </KTPortlet>
      </div>

      <div class="col-xl-4 col-lg-4 order-lg-2 order-xl-1">
        <KTPortlet
          v-bind:class="'kt-portlet--height-fluid'"
          v-bind="{ bodyFit: true }"
        >
          <template v-slot:body>
            <Widget14_3 v-if="info" :memInfo="info.mem"></Widget14_3>
          </template>
        </KTPortlet>
      </div>
      <div class="col-xl-4 col-lg-4 order-lg-2 order-xl-1">
        <KTPortlet
          v-bind:class="'kt-portlet--height-fluid'"
          v-bind="{ bodyFit: true }"
        >
          <template v-slot:body>
            <Widget14_1 v-if="info" :fileInfo="info.file"></Widget14_1>
          </template>
        </KTPortlet>
      </div>
    </div>
    <div class="row">
      <div class="col-xl-6 col-lg-6 order-lg-3 order-xl-1">
        <KTPortlet v-bind:title="'NGƯỜI DÙNG'">
          
          <template v-slot:body>
            <b-tabs class="kt-hide-tabs" v-model="tabIndex">
        
                <Widget5 v-if="info" v-bind:datasrc="info.user"></Widget5>
             
            </b-tabs>
          </template>
        </KTPortlet>
      </div>

      <div class="col-xl-6 col-lg-6 order-lg-3 order-xl-1">
        <KTPortlet
          v-bind:title="'DATABASE'"
          v-bind:class="'kt-portlet--tabs kt-portlet--height-fluid'"
        >
          
          <template v-slot:body>
           
                <Widget4 v-if="info" v-bind:datasrc="info.db"></Widget4>
              
          </template>
        </KTPortlet>
      </div>
    </div>
  </div>

</template>

<script>
import { SET_BREADCRUMB } from "@/store/breadcrumbs.module";
import KTPortlet from "@/views/partials/content/Portlet.vue";

import Widget4 from "./widgets/Widget4.vue";
import Widget5 from "./widgets/Widget5.vue";
import Widget14_1 from "./widgets/Widget14_MiniDailySales.vue";
import Widget14_2 from "./widgets/Widget14_MiniProfitShare.vue";
import Widget14_3 from "./widgets/Widget14_MiniRevenueChange.vue";


/**
 * Sample widgets data source
 */
import timelines from "@/common/mock/widget-timeline.json";
import widget4 from "@/common/mock/widget-4.json";
import widget5 from "@/common/mock/widget-5.json";
import {getSysinfo} from "@/api/admin/users"

export default {
  name: "dashboard",
  components: {
    KTPortlet,
    
    Widget4,
    Widget5,
    Widget14_1,
    Widget14_2,
    Widget14_3,
  
   
  },
  data() {
    return {
      timelines: timelines,
      widget4: widget4,
      widget5: widget5,
      tabIndex: 0,
      tabIndex2: 0,
      info:null,
    };
  },
  mounted() {
      getSysinfo().then(res=>{
        this.info = res.data;
        
    });
    this.$store.dispatch(SET_BREADCRUMB, [{ title: "Dashboard" }]);
    
  },
  methods: {
  
  }
};
</script>
