<template>
  <div v-if="task">
    <v-list class="transparent">
      <v-list-item>
        <v-list-item-title class="headline mb-2">
          <span>Người tạo: {{task.acc_created.name}}</span>
        </v-list-item-title>
        <v-list-item-subtitle class="text-right">
          <v-chip class="ma-2" :color="task.status|taskStatusColor" label text-color="white">
            <!-- <v-icon dense small left>{{task.status|taskTypeIcon}}</v-icon> -->
            {{task.status|taskStatusText}}
          </v-chip>
        </v-list-item-subtitle>
      </v-list-item>

      <div>
        <span
          class="span-bold"
        >Thực hiện: Đối tác {{task.report_task.place_rental.place_id.owner.name}}</span>
      </div>
      <div>
        <span class="span-bold">Tại Địa điểm: {{task.report_task.place_rental.name}}</span>
        <v-btn icon color="purple" small>
          <v-icon dense small>fas fa-map</v-icon>
        </v-btn>
      </div>
      <v-row>
        <v-col
          v-for="(sb, index) in task.report_task.place_rental.signboards"
          :key="index"
          xs="12"
          sm="6"
          md="4"
        >
          <v-card class="mx-auto" max-width="300">
            <v-img class="white--text align-end" height="150" :src="getUrl(sb.imgs)">
              <v-card-title style="color:black">{{sb.name}}</v-card-title>
            </v-img>
          </v-card>
        </v-col>
      </v-row>
      <div>
        <span class="span-bold">Loại nhiệm vụ:</span>
        <v-chip
          class="ma-2"
          small
          :color="task.report_task.repeat_type|repeatTypeColor"
          outlined
        >{{task.report_task.repeat_type|repeatTypeText}}</v-chip>
      </div>
      <div style="margin-left:10px" v-if="task.report_task.repeat_type=='repeat'">
        <div>
          <span class="span-bold">Đơn vị tần suất:</span>
          {{task.report_task.repeat_detail.fz_unit|timeUnitText}}
        </div>
        <div v-if="task.report_task.repeat_detail.fz_unit=='day'">
          <span class="span-bold">Mỗi:</span>
          {{task.report_task.repeat_detail.fz_vals[0]}} {{task.report_task.repeat_detail.fz_unit|timeUnitText}}
        </div>
        <div v-if="task.report_task.repeat_detail.fz_unit=='moth'">
          <span class="span-bold">Vào các ngày:</span>
          {{task.report_task.repeat_detail.fz_vals.join(', ')}}
        </div>
        <div v-if="task.report_task.repeat_detail.fz_unit=='week'">
          <span class="span-bold">Vào các thứ:</span>
          {{task.report_task.repeat_detail.fz_vals.map(o=>{
          if(o==7) return 'CN';
          return o+1;
          }).join(', ')}}
        </div>
        <div>
          <span class="span-bold">Yêu cầu ngẫu nhiên:</span>
          {{(task.report_task.repeat_detail.random?'Có':'Không')}}
        </div>
        <div v-if="task.report_task.repeat_detail.random" style="margin-left:10px">
          <span class="span-bold">Thực hiện trong vòng:</span>
          {{task.report_task.repeat_detail.time_to_complete/60}} phút
          <span
            class="span-bold"
          >Thông báo trước:</span>
          {{task.report_task.repeat_detail.remind_before/60}} phút
        </div>
        <div>
          <span class="span-bold">Thực hiện từ:</span>
          {{task.report_task.repeat_detail.start}} -> {{task.report_task.repeat_detail.end}} trong ngày
        </div>
      </div>
      <div v-if="task.report_task.repeat_type=='repeat'&&task.status == -1">
        <span class="span-bold">Thời gian lặp lại:</span>
        {{Date.parse(task.start).toString('dd/MM/yyyy')}} -> {{Date.parse(task.end).toString('dd/MM/yyyy')}}
        
      </div>
      <div v-else>
        <div>
<span class="span-bold">Thời gian thực hiện:</span>
        {{Date.parse(task.start).toString('dd/MM/yyyy HH:mm')}} -> {{Date.parse(task.end).toString('dd/MM/yyyy HH:mm')}}
        </div>
        
        <div  v-if="task.report_task.root_task"><router-link :to="'/party-a/tasks/'+task.report_task.root_task+'/detail'">Nhiệm vụ gốc</router-link></div>
      </div>
    </v-list>
    <div v-if="task.status == -2||task.status == -1||task.status == 1">
      <v-btn :loading="app" color="orange" dark class="ma-2" large @click="approve(-3)">
        <v-icon small left>fas fa-ban</v-icon>&nbsp; Hủy
      </v-btn>
    </div>
  </div>
</template>
<script>
import { BASE_API } from "@/utils/base";
import {approveTask} from'@/api/task/task'
export default {
  methods: {
    getUrl(imgs) {
      return BASE_API + "file/get/" + imgs[0];
    },
    data() {
      return {
        approving : false
      }
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
  props: {
    task: {
      type: Object,
      default() {
        return {};
      },
      required: true
    }
  }
};
</script>
<style lang="css" scoped>
.span-bold {
  font-size: 14px;
  font-weight: bold;
}
</style>