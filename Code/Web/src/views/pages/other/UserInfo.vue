<template>
  <div>
    <v-card class="mx-auto" outlined>
      <v-list-item three-line>
        <v-list-item-content class="tk">
          <v-list-item-title class="headline mb-1">Thông tin tài khoản</v-list-item-title>
          <p>
            • Tài khoản đăng nhập:
            <b>{{acc.acc_name}}</b>
          </p>
          <p>
            • Email đăng nhập:
            <b>{{acc.email}}</b>
          </p>
          <p>
            • Tên:
            <b>{{acc.name}}</b>
          </p>
          <div>
            <v-chip
              class="ma-2"
              :color="acc.role|roleStatusColor"
              label
              text-color="white"
            >{{acc.role|roleStatusText}}</v-chip>
            <v-chip
              class="ma-2"
              :color="acc.status|partyStatusColor"
              label
              text-color="white"
            >{{acc.status|partyStatusText}}</v-chip>
          </div>
        </v-list-item-content>

        <v-list-item-avatar tile size="80" color="grey">
          <img src="https://cdn.vuetifyjs.com/images/john.jpg" alt="John" />
        </v-list-item-avatar>
      </v-list-item>

      <v-card-actions>
        <v-btn class="ma-2" tile outlined text color="success" @click="accModalVisible = true">
          <v-icon left>mdi-pencil</v-icon>Đổi thông tin tài khoản
        </v-btn>
      </v-card-actions>
    </v-card>
    <v-card style="margin-top:20px" class="mx-auto" outlined v-if="acc.role=='partyB'">
      <v-list-item three-line>
        <v-list-item-content class="tk">
          <v-list-item-title class="headline mb-1">Thông tin đơn vị</v-list-item-title>
          <p>
            • Tên đơn vị:
            <b>{{ptB.name}}</b>
          </p>
          <p>
            • Email:
            <b>{{ptB.email}}</b>
          </p>
          <p>
            • SĐT:
            <b>{{ptB.phone}}</b>
          </p>
          <p>
            • Địa chỉ:
            <b>{{ptB.address}}</b>
          </p>
          <div>
            <v-chip
              class="ma-2"
              :color="acc.status|partyStatusColor"
              label
              text-color="white"
            >{{ptB.status|partyStatusText}}</v-chip>
          </div>
        </v-list-item-content>
      </v-list-item>
      <v-card-actions>
        <v-btn class="ma-2" tile outlined text color="success">
          <v-icon left>mdi-pencil</v-icon>Đổi thông tin đơn vị
        </v-btn>
      </v-card-actions>
    </v-card>
    <v-card style="margin-top:20px" class="mx-auto" outlined v-if="acc.role&&acc.role.includes('partyA')">
      <v-list-item three-line>
        <v-list-item-content class="tk">
          <v-list-item-title class="headline mb-1">Thông tin đơn vị</v-list-item-title>
          <p>
            • Tên đơn vị:
            <b>{{ptA.name}}</b>
          </p>
          <p>
            • Email:
            <b>{{ptA.email}}</b>
          </p>
          <p>
            • SĐT:
            <b>{{ptA.phone}}</b>
          </p>
          <p>
            • Địa chỉ:
            <b>{{ptA.address}}</b>
          </p>
          <p>
            • Lĩnh vực:
            <b>{{ptA.industry}}</b>
          </p>
          <div>
            <v-chip
              class="ma-2"
              :color="ptA.status|partyStatusColor"
              label
              text-color="white"
            >{{ptA.status|partyStatusText}}</v-chip>
          </div>
        </v-list-item-content>
      </v-list-item>
      <v-card-actions v-if="acc.role=='partyAAdmin'">
        <v-btn class="ma-2" tile outlined text color="success">
          <v-icon left>mdi-pencil</v-icon>Đổi thông tin đơn vị
        </v-btn>
      </v-card-actions>
    </v-card>
    <v-dialog v-model="accModalVisible" persistent max-width="400">
      <account :acc="acc" @close="accModalVisible=false" @accUpdated="onAccUpdated"></account>
    </v-dialog>
  </div>
</template>
<script>
import { getMyInfo } from "@/api/users";
import { SET_BREADCRUMB } from "@/store/breadcrumbs.module";
import Account from './components/Account'
export default {
  data() {
    return {
      acc: {},
      ptA: {},
      ptB: {},
      accModalVisible:false,
    };
  },
  methods:{
      onAccUpdated(){

      }
  },
  created() {
    getMyInfo().then(res => {
      this.acc = res.data.acc;
      if (res.data.ptA) {
        this.ptA = res.data.ptA;
      }
      if (res.data.ptB) {
        this.ptB = res.data.ptB;
      }

      this.$store.dispatch(SET_BREADCRUMB, [
        { title: "Thông tin tài khoản", route: "" },
        { title: res.data.acc.acc_name, route: "/my-info" }
      ]);
    });
  },
  components:{
      Account
  }
};
</script>
<style lang="css" scoped>
.tk p {
  font-size: 15px;
  line-height: 1.4;
}
.tk b {
  font-weight: bold;
}
</style>