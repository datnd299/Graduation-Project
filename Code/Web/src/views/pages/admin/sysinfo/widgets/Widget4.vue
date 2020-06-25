<template>
  <div class="kt-widget4">
    <perfect-scrollbar style="max-height: 100vh; position: relative;">
      <template v-for="(item, i) in datasrc">
        <div class="kt-widget4__item item-small" :key="`item-${i}`">
          <div class="kt-widget4__pic kt-widget4__pic--pic">
            <!-- <img :src="item.image" alt="" /> -->
          </div>
          <div class="kt-widget4__info">
            <a href="#" class="kt-widget4__username">{{ i }}</a>
          </div>
          <span style="color:#5578eb" class="btn" v-bind:class="item">{{
            item
          }}</span>
        </div>
      </template>
    </perfect-scrollbar>
  </div>
</template>

<script>
export default {
  name: "widget-4",
  components: {},
  props: {
    datasrc: Object
  }
};
</script>
<style lang="css">
  .item-small{
    padding-top: 0px !important;
    padding-bottom: 0px !important;
  }
</style>
