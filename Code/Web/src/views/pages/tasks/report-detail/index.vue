<template>
  <div>
    <v-card class="mx-auto" style="padding:20px" :loading="isLoading" outlined>
      <v-list-item-title v-if="task" class="headline mb-1">
        Nhiệm vụ
        <v-chip class="ma-2" :color="task.type|taskTypeColor" label text-color="white">
          <v-icon dense small left>{{task.type|taskTypeIcon}}</v-icon>
          {{task.type|taskTypeText}}
        </v-chip>
      </v-list-item-title>
      <setup-task :task="task" v-if="task.type=='setup'"></setup-task>
      <fee-task :task="task" v-if="task.type=='fee'"></fee-task>
      <check-task :task="task" v-if="task.type=='check'"></check-task>
      <report-task :task="task" v-if="task.type=='report'"></report-task>
    </v-card>
    <div style="margin-top:10px" >
        <setup-report :task="task" v-if="task.type=='setup'"></setup-report>
        <fee-report :task="task" v-if="task.type=='fee'"></fee-report>
        <check-report :task="task" v-if="task.type=='check'"></check-report>
        <report-report :task="task" v-if="task.type=='report'"></report-report>
        
        <v-card class="mx-auto" style="padding:20px" outlined v-if="role()=='partyAAdmin'">
        <v-btn :loading="approving" @click="approve(0)" v-if="task.status>0" color="red" dark class="ma-2" large>
          <v-icon small left>fas fa-ban</v-icon>&nbsp; Không duyệt nhiệm vụ
        </v-btn>

        <v-btn :loading="approving" @click="approve(3)" v-if="task.status>0" style="float:right" color="teal" dark class="ma-2" large>
          <v-icon small left>fas fa-check-square</v-icon>&nbsp; Duyệt nhiệm vụ
        </v-btn>
        </v-card>
    </div>
    
  </div>
</template>
<script>
import { getById } from "@/api/task/task";
import SetupReport from './components/SetupReport'
import SetupTask from './components/SetupTask'

import FeeTask from './components/FeeTask'
import FeeReport from './components/FeeReport'

import CheckTask from './components/CheckTask'
import CheckReport from './components/CheckReport'

import ReportTask from './components/ReportTask'
import ReportReport from './components/ReportReport'
import {BASE_API} from '@/utils/base'

import { SET_BREADCRUMB } from "@/store/breadcrumbs.module";
import {approveTask} from'@/api/task/task'

import {GetRole} from '@/utils/auth'
export default {
  computed: {
    tId() {
      return this.$route.params.id;
    }
  },
  components:{
      SetupReport,
      SetupTask,
      FeeTask,
      FeeReport,
      CheckTask,
      CheckReport,
      ReportTask,
      ReportReport
  },
  data() {
    return {
      task: {},
      isLoading:false,
      approving:false
    };
  },
  methods:{
    role(){
      console.log(GetRole());
      
      return GetRole();
    },
    getUrl(imgs){
      return BASE_API+'file/get/'+imgs[0]
    },
    approve(status){
      this.approving = true;
      approveTask({
        id:this.task._id,
        status:status
      }).then(res=>{
        console.log(res);
        this.approving = false;
        this.task.status = status;
      })
    }
 
  },
  created() {
    this.$store.dispatch(SET_BREADCRUMB, [
        { title: "Bên thuê", route: "/party-a" },
        {
          title: "Nhiệm vụ",
          route: "/party-a/tasks"
        },
        {
          title: "Chi tiết",
          route: '/party-a/tasks/'+this.tId+'/detail'
        }
      ]);
      this.isLoading = true;
    getById({
      id: this.tId
    }).then(res => {
this.isLoading = false;
      this.task = res.data;
    });
  }
};
</script>