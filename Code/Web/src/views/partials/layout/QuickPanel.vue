<template>
  <!-- begin::Quick Panel -->
  <div id="kt_quick_panel" ref="kt_quick_panel" class="kt-quick-panel">
    <audio id="alertSound" ref="alertSound">
      <source src="definite.ogg" type="audio/ogg" />
      <source src="definite.mp3" type="audio/mpeg" />
    </audio>
    <a href="#" class="kt-quick-panel__close" id="kt_quick_panel_close_btn">
      <i class="flaticon2-delete"></i>
    </a>

    <div class="kt-quick-panel__nav">
      <ul
        class="nav nav-tabs nav-tabs-line nav-tabs-bold nav-tabs-line-3x nav-tabs-line-brand kt-notification-item-padding-x"
        role="tablist"
      >
        <li class="nav-item active">
          <a
            class="nav-link active"
            data-toggle="tab"
            href="#kt_quick_panel_tab_notifications"
            role="tab"
          >Thông báo</a>
        </li>
      </ul>
    </div>

    <div class="kt-quick-panel__content">
      <div class="tab-content">
        <div
          class="tab-pane fade show kt-scroll active"
          id="kt_quick_panel_tab_notifications"
          role="tabpanel"
        >
          <div class="kt-notification">
            <notification-item v-for="(item, index) in notifies" :item="item" :key="index"></notification-item>
          </div>
        </div>
      </div>
    </div>
  </div>
  <!-- end::Quick Panel -->
</template>

<script>
import KTOffcanvas from "@/assets/js/offcanvas.js";
import NotificationItem from "./components/NotificationItem";
import { FCM, BChanel } from "@/utils/firebase-fcm.js";
import {getMine} from '@/api/notification'
export default {
  name: "KTQuickPanel",
  props: {},
  components: {
    NotificationItem
  },
  computed: {},
  mounted() {
    new KTOffcanvas(this.$refs["kt_quick_panel"], {
      overlay: true,
      baseClass: "kt-quick-panel",
      closeBy: "kt_quick_panel_close_btn",
      toggleBy: "kt_quick_panel_toggler_btn"
    });
  },
  data() {
    return {
      notifies: [],
      audio: null
    };
  },
  created() {
    getMine().then(res=>{
      this.notifies=res.data;
      
    })
    
    this.audio = new Audio("/definite.mp3");
    if(FCM){
       FCM.fcmInit();
    var self = this;
    FCM.onMessage(function(payload) {
      self.reciveNotification(payload.data);

      self.playSound();
    });
    BChanel.addEventListener("message", event => {
      self.reciveNotification(event.data.data);
    });
    }
   

    
  },
  methods: {
    playSound() {
      this.audio.play();
    },
    reciveNotification(mess) {
      this.$toast.open({
        message: "1 thông báo mới",
        type: "success",
        position: "top-right",
        duration: 3000
        // all other options
      });
      console.log(mess);
      
      this.$store
        .dispatch('settings/upNumNotification')
      this.notifies.unshift({
        title: mess.title,
        content: mess.body,
        Img: null,
        NStatus: 1
      });
    }
  }
};
</script>
