<template>
  <div>
    <div
      v-if="!room"
      style="position: absolute;
    width: calc(100% - 256px);
    right: 0px;
    height: 100%; background:white"
    ></div>

    <div
      v-if="room"
      style="position: absolute;
    width: calc(100% - 256px);
    right: 0px;
    height: 100%; background:white"
    >
      <v-list-item one-line>
        <v-list-item-content>
          <span>
            <v-avatar color="indigo">
              <v-icon dark>mdi-account-circle</v-icon>
            </v-avatar>
            <span
              style="font-size: 16px;
    margin-left: 10px;
    font-weight: 700;"
            >{{(room?room.name:'')}}</span>
          </span>
        </v-list-item-content>
      </v-list-item>
      <v-divider></v-divider>
      <div
        style="    height: calc(100% - 100px);
    padding: 10px;
    width: 100%;    position: relative;"
      >
        <div
          class="message-field"
          id="d-message-field"
          style="max-width: 100%;
    height: calc(100% - 45px);
    overflow-y: scroll;"
        >
          <Message v-for="(item, index) in messageLst" :message="item" :key="index"></Message>
        </div>
        <div
          style="    position: absolute;     margin-bottom: -30px;
    bottom: 0px;
    width: calc(100% - 20px);background: white;"
          class="input-field"
        >
          <div>
            <v-textarea
              v-model="text"
              outlined
              rows="1"
              name="input-7-4"
              label="Nhập tin nhắn"
              @keyup.enter.exact="sendMessage($event)"
            >
              <v-select
                style="    margin-top: -16px;
    width: 275px;"
                slot="append-outer"
                :items="roles"
                label="Tư cách"
                v-model="role"
                outlined
              >
                <v-btn
                  style="    font-size: 35px;
    margin-top: -11px;"
                  large
                  icon
                  color="pink"
                  slot="prepend"
                >
                  <i class="fas fa-paperclip"></i>
                </v-btn>
                <v-btn
                  slot="append-outer"
                  style="    margin-top: -16px;
    height: 54px;
    font-size: 24px;"
                  depressed
                  large
                  color="primary"
                  @click="sendMessage()"
                >
                  <i class="fas fa-paper-plane"></i>
                </v-btn>
              </v-select>
            </v-textarea>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import Message from "./Message";
import { getMessages } from "@/api/chat/chat";
export default {
  data() {
    return {
      roles: [
        { text: "Riêng tư", value: "private" },
        { text: "Công khai", value: "public" }
      ],
      role: "public",
      text: "",
      messageLst: [],
      isLoading: false
    };
  },

  props: {
    room: Object,
    side: String
  },
  // sockets: {
  //       connect: function () {
  //           console.log('socket connected')
  //       },
  //       newMessageSended: function (data) {
  //         console.log(data);
          
  //           console.log('this method was fired by the socket server. eg: io.emit("customEmit", data)')
  //       }
  //   },
    mounted(){
      this.sockets.listener.subscribe('newMessageSended', (data) => {
        this.messageLst.push(data);

        
          this.scrollBot()
      
        
    
    
  });
    },
  created() {

    this.fetchData();
  },
  watch: {
    room() {
      this.fetchData();
    }
  },
  methods: {
    scrollBot(){
      setTimeout(() => {
        var field = document.getElementById('d-message-field');
      field.scrollTop=field.scrollHeight;
      console.log(field);
      }, 0);
      
      
    },
    fetchData() {
      if (this.room) {
        getMessages({ pt_id: this.room._id }).then(res => {
          this.messageLst = res.data;
          this.scrollBot();
        });
      }
    },
    sendMessage() {
      var sef = this;

      this.$socket.emit(
        "newMessage",
        { content: this.text, to_pt: this.room._id, type: this.role },
        data => {
          console.log(data);
        }
      );
      sef.text = "";
    }
  },
  components: {
    Message
  }
};
</script>