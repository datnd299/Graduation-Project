<template>
  <div class="kt-widget5">
    <perfect-scrollbar style="max-height: 100vh; position: relative;">
      <template v-for="(item, i) in meta">
        <div class="kt-widget5__item" :key="i">
          <div class="kt-widget5__content">
            <!-- <div class="kt-widget5__pic">
              <img class="kt-widget7__img" :src="item.image" alt="" />
            </div> -->
            <div class="kt-widget5__section">
              <a href="#" style="font-size:18px" class="kt-widget5__title">
                {{ item.text }}
              </a>
              <!-- <p class="kt-widget5__desc">
                {{ item.text }}
              </p>
              <div class="kt-widget5__info" v-html="item.info"></div> -->
            </div>
          </div>
          <div class="kt-widget5__content" style="font-size:18px;color:#00b894" >{{datasrc[item.code]}}</div>
        </div>
      </template>
    </perfect-scrollbar>
  </div>
</template>

<script>
export default {
  name: "widget-5",
  components: {},
  props: {
    datasrc: Object
  },
  data() {
    return {
      meta:[
        {
          code:'a',
          text: "Bên thuê"
        },
        {
          code:'b',
          text: "Đối tác"
        },
        {
          code:'acc',
          text: "Tài khoản"
        },
        {
          code:'online',
          text: "Bên thuê"
        }]
      }
    }
  
};
</script>
