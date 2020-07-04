<template>
    <v-card class="elevation-2">
        <v-card-title class="headline">Nhiệm vụ trả chi phí <v-chip style="float:right"  small="" class="ma-2 text-right" :color="task.status|taskStatusColor" label text-color="white">
            <!-- <v-icon dense small left>{{task.status|taskTypeIcon}}</v-icon> -->
            {{task.status|taskStatusText}}
          </v-chip></v-card-title>
        <v-card-text><div>Thực hiện: <span
          v-for="(acc, index) in task.fee_task.accs"
          :key="index"
        >• {{acc.name}}</span></div>
        <div>Trả cho <b>{{task.fee_task.fee_detail.place_rental.time_unit|timeUnitText}} thứ {{task.fee_task.fee_detail.times.join(', ')}}</b> - Số tiền: <b>{{task.fee_task.fee_detail.amount|numberF}}đ</b></div>
        <div v-if="task.fee_task.fee_detail.report.em_confirm">
          <div ><span style="color:green">Nhân viên đã xác nhận</span> <span>{{task.fee_task.fee_detail.report.em_note}}</span></div>
        </div>
        <div v-else>
             <div ><span style="color:red">Nhân viên chưa xác nhận</span></div>
        </div>
        <div v-if="task.fee_task.fee_detail.report.pt_confirm">
          <div ><span style="color:green">Đối tác đã xác nhận</span> <span>{{task.fee_task.fee_detail.report.pt_note}}</span></div>
        </div>
        <div v-else>
             <div ><span style="color:red">Đối tác chưa xác nhận</span></div>
        </div>
        </v-card-text>
        
      </v-card>
</template>
<script>
export default {
    props:{
        task:{
            type:Object,
            required:true,
        }
    }
}
</script>