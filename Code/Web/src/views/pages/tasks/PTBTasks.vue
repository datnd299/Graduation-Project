<template>
  <div>
    <router-link to="/party-a/tasks/new-task">
      <v-btn v-if="isAdmin" large color="primary">
        <i class="fas fa-plus"></i> &nbsp; &nbsp; Thêm nhiệm vụ
      </v-btn>
    </router-link>
    <v-data-table
      style="margin-top:10px"
      :loading="isLoading"
      :headers="headers"
      :items="tableData"
      item-key="_id"
      class="elevation-1"
    >
      <template v-slot:item.place_rental="{ item }">
        <span>{{placesOfMision(item)}}</span>
      </template>
      <template v-slot:item.accs="{ item }">
        <span>{{accsOfMision(item)}}</span>
      </template>
      <template
        v-slot:item.times="{ item }"
      >{{Date.parse(item.start).toString('dd/MM/yy HH:mm')}} - {{Date.parse(item.end).toString('dd/MM/yy HH:mm')}}</template>
      <template v-slot:item.type="{ item }">
        <v-chip class="ma-2" :color="item.type|taskTypeColor" label text-color="white">
          <v-icon dense small left>{{item.type|taskTypeIcon}}</v-icon>
          {{item.type|taskTypeText}}
        </v-chip>
      </template>
      <template v-slot:item.status="{ item }">
        <v-chip class="ma-2" :color="item.status|taskStatusColor" label text-color="white">
            <!-- <v-icon dense small left>{{task.status|taskTypeIcon}}</v-icon> -->
            {{item.status|taskStatusText}}
          </v-chip>
        
      </template>
      <template v-slot:item.com_link="{ item }">
        <v-btn-toggle dense color="#033">
          <router-link :to="'/party-a/tasks/'+item._id+'/detail'">
            <v-btn depressed small color="blue darken-1" text-color="white">
              <i class="fas fa-ellipsis-h" style="color:white"></i>
              <!-- <v-icon style="color:red">fas fa-times</v-icon> -->
            </v-btn>
          </router-link>
        </v-btn-toggle>
      </template>
    </v-data-table>
  </div>
</template>
<script>
import { getAllOfMyPT } from "@/api/task/task";
import { SET_BREADCRUMB } from "@/store/breadcrumbs.module";
export default {
  data() {
    return {
      isLoading: false,
      isAdmin: false,
      headers: [
        {
          text: "Điểm treo",
          align: "start",
          value: "place_rental"
        },
        { text: "Loại nhiệm vụ", value: "type", align: "center" },
        { text: "Người thực hiện", value: "accs" },
        { text: "Thời gian thực hiện", value: "times" },
        
        { text: "Trạng thái", value: "status" },
        { text: "Links", value: "com_link", align: "center" },
        { text: "Acctions", value: "com_ac", align: "center" }
      ],
      tableData: []
    };
  },
  methods: {
    placesOfMision(task) {
      if (task.type == "setup") {
        return task.setup_task.place_rental.name;
      } else if (task.type == "check") {
        return task.check_task.place_rental
          .map(function(item) {
            return item["name"];
          })
          .join(", ");
      } else if (task.type == "fee") {
        return task.fee_task.fee_detail
          .map(function(item) {
            return item["place_rental"].name;
          })
          .join(", ");
      } else if (task.type == "report") {
        return task.report_task.place_rental.name;
      } else {
        return "";
      }
    },
    accsOfMision(task) {
      if (task.type == "setup") {
        return task.setup_task.accs
          .map(function(item) {
            return item["name"];
          })
          .join(", ");
      } else if (task.type == "check") {
        return task.check_task.accs
          .map(function(item) {
            return item["name"];
          })
          .join(", ");
      } else if (task.type == "fee") {
        return task.fee_task.accs
          .map(function(item) {
            return item["name"];
          })
          .join(", ");
      } else if (task.type == "report") {
        return task.report_task.place_rental.name;
      } else {
        return "";
      }
    }
  },
  created() {
    this.isLoading = true;
    getAllOfMyPT().then(res => {
      this.isLoading = false;
      this.tableData = res.data;
      if (res.acc.role.includes("Admin")) {
        this.isAdmin = true;
      } else {
        this.isAdmin = false;
      }
      this.$store.dispatch(SET_BREADCRUMB, [
        { title: "Đối tác", route: "/party-b" },
        {
          title: "Nhiệm vụ của tôi",
          route: "/party-b/tasks"
        }
      ]);
    });
  }
};
</script>