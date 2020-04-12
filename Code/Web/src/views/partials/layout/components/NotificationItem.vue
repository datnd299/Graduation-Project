<template>
    <a href="/#/party-b/places-rental" class="kt-notification__item">
              <div class="kt-notification__item-icon">
                <i class="fas fa-hand-paper"></i>
              </div>
              <div class="kt-notification__item-details">
                <div class="kt-notification__item-title">
                  {{item.title}}
                </div>
                <div class="kt-notification__item-time">
                  {{item.content}}
                </div>
              </div>
            </a>
</template>
<script>
export default {
  props:{
    item:{
      type:Object,
      defautl(){
        return {}
      }
    }
  }
}
</script>