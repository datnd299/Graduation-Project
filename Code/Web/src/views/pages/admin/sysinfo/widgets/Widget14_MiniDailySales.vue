<template>
  <div class="kt-widget14">
    <div class="kt-widget14__header kt-margin-b-30">
      <h3 class="kt-widget14__title">
        File
      </h3>
      <span class="kt-widget14__desc">
        Check out each collumn for more details
      </span>
      <div style="color: #595d6e;
    margin-top: 20px;
    font-size: 18px;">
        Tổng file: {{fileInfo.count}}
      </div>
      <div style="color: #595d6e;
    margin-top: 5px;
    font-size: 18px;">
        Tổng dung lượng: {{fileInfo.size}}
      </div>
    </div>
    <div class="kt-widget14__chart">
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  name: "widget-14-1",
  components: {
  },
  data() {
    return {

    };
  },
  props: {
    fileInfo:Object
  },
  mounted() {
  
  },
  computed: {
    ...mapGetters(["layoutConfig"])
  }
};
</script>
