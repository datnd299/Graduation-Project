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
        <div>Trả cho <b>Tháng thứ 1</b> - Số tiền: <b>100,000đ</b></div>
        <div style="color:green">Nhân viên đã xác nhận</div>
        <div style="color:green">Đối tác đã xác nhận</div>
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