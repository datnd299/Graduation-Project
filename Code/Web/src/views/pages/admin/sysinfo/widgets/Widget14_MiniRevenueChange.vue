<template>
  <div class="kt-widget14">
    <div class="kt-widget14__header">
      <h3 class="kt-widget14__title">
        Memory
      </h3>
      <span class="kt-widget14__desc">
        RAM
      </span>
    </div>
    <div class="kt-widget14__content">
      <div class="kt-widget14__chart">
        <div class="kt-widget14__stat">45</div>
        <Chart1
          ref="chart"
          v-bind:options="chartOptions"
          height="150"
          width="150"
        ></Chart1>
      </div>
      <div class="kt-widget14__legends">
        <div class="kt-widget14__legend">
          <span class="kt-widget14__bullet kt-bg-brand"></span>
          <span class="kt-widget14__stats">Tổng: {{memInfo.totalMemMb}} MB</span>
        </div>
        <div class="kt-widget14__legend">
          <span class="kt-widget14__bullet kt-bg-warning"></span>
          <span class="kt-widget14__stats">Sử dụng: {{memInfo.usedMemMb}} MB - {{Math.round(memInfo.usedMemMb*100.0/memInfo.totalMemMb)}}%</span>
        </div>
        <div class="kt-widget14__legend">
          <span class="kt-widget14__bullet kt-bg-success"></span>
          <span class="kt-widget14__stats">Trống: {{memInfo.freeMemMb}} MB - {{Math.round(memInfo.freeMemMb*100.0/memInfo.totalMemMb)}}%</span>
        </div>
        
        
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import Chart1 from "@/views/partials/widgets/Chart1.vue";

export default {
  name: "widget-14-3",
  components: {
    Chart1
  },
  data() {
    return {
      chartOptions: {}
    };
  },
  props: {
    memInfo:Object
  },
  mounted() {
    var sef = this;
    this.chartOptions = {
      type: "doughnut",
      data: {
        datasets: [
          {
            data: [sef.memInfo.usedMemMb, sef.memInfo.freeMemMb],
            backgroundColor: [
              this.layoutConfig("colors.state.danger"),
              this.layoutConfig("colors.state.success")
            ]
          }
        ],
        labels: ["Memory Usage (MB)", "Memory Free (MB)"]
      },
      options: {
        cutoutPercentage: 75,
        responsive: true,
        maintainAspectRatio: false,
        legend: {
          display: false,
          position: "top"
        },
        title: {
          display: false,
          text: "Technology"
        },
        animation: {
          animateScale: true,
          animateRotate: true
        },
        tooltips: {
          enabled: true,
          intersect: false,
          mode: "nearest",
          bodySpacing: 5,
          yPadding: 10,
          xPadding: 10,
          caretPadding: 0,
          displayColors: false,
          backgroundColor: this.layoutConfig("colors.state.brand"),
          titleFontColor: "#ffffff",
          cornerRadius: 4,
          footerSpacing: 0,
          titleSpacing: 0
        }
      }
    };
  },
  computed: {
    ...mapGetters(["layoutConfig"])
  }
};
</script>
