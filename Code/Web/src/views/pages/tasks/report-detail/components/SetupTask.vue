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
          style="font-size:14px;font-weight:bold"
        >Địa điểm: {{task.setup_task.place_rental.name}}</span>
      </div>

      <div>
        Địa chỉ: {{task.setup_task.place_rental.address}}
        <v-btn icon color="purple" small>
          <v-icon dense small>fas fa-map</v-icon>
        </v-btn>
      </div>

      <div>
        <span style="font-size:14px;font-weight:bold">Lắp đặt:</span>
      </div>
      <v-row>
        <v-col v-for="(sb, index) in task.setup_task.signboards" :key="index" xs="12" sm="6" md="4">
          <v-card class="mx-auto" max-width="300">
            <v-img class="white--text align-end" height="150" :src="getUrl(sb.imgs)">
              <v-card-title style="color:black">{{sb.name}}</v-card-title>
            </v-img>
          </v-card>
        </v-col>
      </v-row>
      <div>
        <span style="font-size:14px;font-weight:bold">Thực hiện:</span>
        <div
          v-for="(acc, index) in task.setup_task.accs"
          :key="index"
          style="margin-left:10px"
        >• {{acc.name}}</div>
      </div>
      <div>
        <span style="font-size:14px;font-weight:bold">Thời gian:</span>
        {{Date.parse(task.start).toString('dd/MM/yyyy HH:mm')}} - {{Date.parse(task.end).toString('dd/MM/yyyy HH:mm')}}
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