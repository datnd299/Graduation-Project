<template>
    <div>
        <router-link to="/party-a/tasks/new-task">
            <v-btn  large color="primary"><i class="fas fa-plus"></i> &nbsp; &nbsp; Thêm nhiệm vụ</v-btn>
        </router-link>
        <v-data-table
      style="margin-top:10px"
      :loading="isLoading"
      :headers="headers"
      :items="tableData"
      item-key="_id"
      class="elevation-1"
    >
     <template v-slot:item.com_link="{ item }">
        <v-btn-toggle dense color="#033">
          <router-link :to="'/party-b/'+item._id+'/places'">
            <v-btn depressed small color="blue darken-1" text-color="white">
              <i class="fas fa-flag" style="color:white"></i>
              <!-- <v-icon style="color:red">fas fa-times</v-icon> -->
            </v-btn>
          </router-link>
        </v-btn-toggle>
      </template>
    </v-data-table>
    </div>
</template>
<script>
export default {
    isLoading: false,
      headers: [
        {
          text: "Điểm treo",
          align: "start",
          value: "name"
        },
        { text: "Loại nhiệm vụ", value: "count_places", align: "center" },
        { text: "Người thực hiện", value: "email" },
        { text: "Thời gian thực hiện", value: "phone" },
        { text: "Trạng thái", value: "province" },
        { text: "Links", value: "com_link", align: "center" },
        { text: "Acctions", value: "com_ac", align: "center" }
      ],
      tableData: []
}
</script>