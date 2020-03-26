<template>
  <div>
    <d-loading :isLoading="isLoading"></d-loading>
    <v-data-table :headers="headers" :items="desserts" item-key="name" class="elevation-1">
      
    <template v-slot:item.role="{ item }">
      <v-chip
      class="ma-2"
      :color="item.role|roleStatusColor"
      label=""
      text-color="white"
    >
      {{item.role|roleStatusText}}
    </v-chip>
    </template>
      <template v-slot:item.acc_ac="{ item }">
        <v-btn-toggle dense color="#033">


          <v-btn small>
            <i class="fas fa-times"></i>
          </v-btn>
        </v-btn-toggle>
      </template>
    </v-data-table>
  </div>
</template>

<script>
import { getPartyAAccs } from "@/api/admin/users";
import { SET_BREADCRUMB } from "@/store/breadcrumbs.module";

export default {
  data() {
    return {
      isLoading: false,

      headers: [
        { text: "TK", value: "acc_name" },
        { text: "Tên", value: "name" },
        { text: "Email", value: "email" },
        { text: "Quyền", value: "role",align:'center' },
        { text: "Actions", value: "acc_ac" }
      ],
      desserts: []
    };
  },
  methods: {
    fetchData() {
      this.isLoading = true;
      getPartyAAccs({
        p_id: this.pId
      }).then(res => {
        this.isLoading = false;
        if (res.data) {
          this.desserts =  res.data.accs;
          this.$store.dispatch(SET_BREADCRUMB, [
            { title: "Admin", route: "" },
            { title: "QL Bên thuê", route: "/admin/party-a" },
            { title: res.data.name, route: "/admin/party-a/"+this.pId },
            { title: "Tài khoản", route: "/admin/party-a/"+this.pId+"/users" }
          ]);
        }
      });
    }
  },
  computed: {
    pId() {
      return this.$route.params.id;
    }
  },
  components: {},
  mounted() {
    this.fetchData();
  }
};
</script>
