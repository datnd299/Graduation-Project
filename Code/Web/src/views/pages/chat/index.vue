<template>
  <div>
    <div height="100%">
      <v-navigation-drawer absolute permanent>
        <template v-slot:prepend>
          <v-list-item one-line>
            <v-list-item-content>
              <!-- <v-list-item-title style="font-size: 15px;
              font-weight: 400;">Tin nhắn</v-list-item-title>-->
              <SearchInput :side="side" @choosed="onChoosed"></SearchInput>
            </v-list-item-content>
          </v-list-item>
        </template>

        <v-divider></v-divider>
        <v-list>
          <v-list-item
            @click="chooseRooms(item)"
            :class="(item.actived?'d-list-item actived':'d-list-item')"
            v-for="(item, index) in rooms"
            :key="index"
          >
            <v-list-item-avatar>
              <v-img src="/assets/parnter.png"></v-img>
            </v-list-item-avatar>
            <v-list-item-content>
              <v-list-item-title v-text="item.name"></v-list-item-title>
              <v-list-item-subtitle v-text="item.address"></v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>
        </v-list>
      </v-navigation-drawer>

      <ChatField :side="side" :room="currentRoom"></ChatField>
    </div>
  </div>
</template>

<script>
import { SET_BREADCRUMB } from "@/store/breadcrumbs.module";
import SearchInput from "./components/SearchInput";
import ChatField from "./components/ChatField";
import { getMyRooms } from "@/api/chat/chat";
export default {
  data() {
    return {
      isLoading: false,
      rooms: [
      ],
      side:'',
      currentRoom: null
    };
  },
  components: {
    SearchInput,
    ChatField
  },

  created() {
    if(this.$route.path.includes('party-b')){
      this.side = 'party-b'
    }
    if(this.$route.path.includes('party-a')){
      this.side = 'party-a'
    }
    
    this.$store.dispatch(SET_BREADCRUMB, [
      { title: "Tin nhắn", route: "/party-a/chat" }
    ]);
    this.fetchData();
  },
  methods: {
    fetchData() {
      getMyRooms().then(res => {
        this.rooms = res.data;
      });
    },
    onChoosed(item) {
      var exist = false;
      this.rooms.forEach(e => {
        if (e._id == item._id) {
          exist = true;
          e.actived = true;
        } else {
          e.actived = false;
        }
      });
      item.actived = true;
      if (!exist) {
        this.rooms.push(item);
      }
    },
    chooseRooms(item) {
      this.rooms.forEach(e => {
        if (e._id == item._id) {
          e.actived = true;
          this.currentRoom = e;
        } else {
          e.actived = false;
        }
      });
    }
  }
};
</script>
<style lang="css" scoped>
.d-list-item:hover {
  background: #ecf0f1;
  cursor: pointer;
}
.d-list-item.actived {
  background: #ecf0f1;
}
</style>
