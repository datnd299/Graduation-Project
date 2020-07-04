<template>
    <div>
         <v-timeline>
    <v-timeline-item
      v-for="(item, index) in tableData" :key="index"
      large
    >
      <template v-slot:icon>
        <v-avatar :color="item.type|taskTypeColor">
               
          <v-icon dense small style="color:white">{{item.type|taskTypeIcon}}</v-icon>
         
        </v-avatar>
      </template>
      <template v-slot:opposite>
        <span>{{Date.parse(item.start).toString('dd/MM/yyyy')}}</span>
      </template>
      <fee-task v-if="item.type=='fee'" :task="item"></fee-task>
      <report-task v-if="item.type=='report'" :task="item"></report-task>
      <check-task v-if="item.type=='check'" :task="item"></check-task>
      <setup-task v-if="item.type=='setup'" :task="item"></setup-task>
    </v-timeline-item>
  </v-timeline>
    </div>
</template>
<script>
import { SET_BREADCRUMB } from "@/store/breadcrumbs.module";
import { getTaskByPlace } from "@/api/task/task";
import  FeeTask from './components/FeeTask';
import ReportTask from './components/ReportTask';
import CheckTask from './components/CheckTask';
import SetupTask from './components/SetupTask';
export default {
    components:{
        FeeTask,
        ReportTask,
        CheckTask,
        SetupTask
    },
    data() {
        return {
          id:null,
            tableData:[],
        }
    },
    methods: {
        fetchData(){
            getTaskByPlace({
              id:this.id
            }).then(res=>{
                this.tableData = res.data;
            })
        }
    },
    created(){
      this.id = this.$route.params.id;
        this.fetchData()
        this.$store.dispatch(SET_BREADCRUMB, [
      { title: "Bên thuê", route: "/party-a" },
      { title: "Điểm treo", route: "/party-a/places" },
      { title: "Nhà hàng Phúc Lộc", route: "/party-a/places" },
      { title: "DS Nhiệm vụ", route: "/party-a/places" }
    ]);
    }
}
</script>