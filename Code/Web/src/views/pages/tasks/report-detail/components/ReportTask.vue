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
        <span class="span-bold">Thực hiện: Đối tác {{task.report_task.place_rental.place_id.owner.name}}</span>
        
      </div>
      <div>
        <span class="span-bold">Tại điểm treo: {{task.report_task.place_rental.name}}</span> <v-btn icon color="purple" small>
            <v-icon dense small>fas fa-map</v-icon>
          </v-btn>
      </div>
      <v-row>
        <v-col v-for="(sb, index) in task.report_task.place_rental.signboards" :key="index" xs="12" sm="6" md="4">
          <v-card class="mx-auto" max-width="300">
            <v-img class="white--text align-end" height="150" :src="getUrl(sb.imgs)">
              <v-card-title style="color:black">{{sb.name}}</v-card-title>
            </v-img>
          </v-card>
        </v-col>
      </v-row>
      <div>
        <span class="span-bold">Loại nhiệm vụ: </span> <v-chip
      class="ma-2"
      small=""
      :color="task.report_task.repeat_type|repeatTypeColor"
      outlined
    >
      {{task.report_task.repeat_type|repeatTypeText}}
    </v-chip>
        </div>
        <div style="margin-left:10px" v-if="task.report_task.repeat_type=='repeat'">
        <div>
            <span class="span-bold">Đơn vị tần suất: </span>{{task.report_task.repeat_detail.fz_unit|timeUnitText}}
        </div>
        <div v-if="task.report_task.repeat_detail.fz_unit=='day'">
            <span class="span-bold">Mỗi:</span> {{task.report_task.repeat_detail.fz_vals[0]}} {{task.report_task.repeat_detail.fz_unit|timeUnitText}}
        </div>
        <div>
            <span class="span-bold">Yêu cầu ngẫu nhiên: </span>{{(task.report_task.repeat_detail.random?'Có':'Không')}}
        </div>
        <div v-if="task.report_task.repeat_detail.random" style="margin-left:10px">
            <span class="span-bold">Thực hiện trong vòng: </span>{{task.report_task.repeat_detail.time_to_complete/60}} phút
            <span class="span-bold">Thông báo trước: </span>{{task.report_task.repeat_detail.remind_before/60}} phút
        </div>
        <div >
            <span class="span-bold">Thực hiện từ: </span>{{task.report_task.repeat_detail.start}} -> {{task.report_task.repeat_detail.end}} trong ngày
        </div>
        </div>
      <div v-if="task.report_task.repeat_type=='repeat'">
        <span class="span-bold" >Thời gian lặp lại:</span>
        {{Date.parse(task.start).toString('dd/MM/yyyy')}} -> {{Date.parse(task.end).toString('dd/MM/yyyy')}}
      </div>
      <div v-else>
        <span class="span-bold" >Thời gian thực hiện:</span>
        {{Date.parse(task.start).toString('dd/MM/yyyy HH:mm')}} -> {{Date.parse(task.end).toString('dd/MM/yyyy HH:mm')}}
      </div>
    </v-list>
  </div>
</template>
<script>
import {BASE_API} from '@/utils/base'
export default {
    methods:{
        getUrl(imgs){
      return BASE_API+'file/get/'+imgs[0]
    },
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
    .span-bold{
        font-size:14px;font-weight:bold
    }
</style>