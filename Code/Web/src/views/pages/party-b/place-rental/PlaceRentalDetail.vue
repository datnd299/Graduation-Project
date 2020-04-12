<template>
    <div>
       <v-card class="mx-auto" outlined>
      <v-list-item >
        <v-list-item-content class="tk">
          <v-list-item-title class="headline mb-1">Trạng thái
             <v-chip style="    float: right;"
          class="ma-2"
          :color="detail.status|placeRentalStatusColor"
          label
          text-color="white"
        >{{detail.status|placeRentalStatusText}}</v-chip>
          </v-list-item-title>
        </v-list-item-content>
      </v-list-item>
       </v-card>
        <v-card class="mx-auto" outlined style="margin-top:10px">
      <v-list-item three-line>
        <v-list-item-content class="tk">
          <v-list-item-title class="headline mb-1">Chủ địa điểm</v-list-item-title>
          <p>
            • Tên:
            <b>{{ptB.name}}</b>
          </p>
          <p>
            • Email:
            <b>{{ptB.email}}</b>
          </p>
          <p>
            • SDT:
            <b>{{ptB.phone}}</b>
          </p>
           <p>
            • Địa chỉ:
            <b>{{ptB.address}} {{ptB.district}} {{ptB.province}}</b>
          </p>
          
        </v-list-item-content>
      </v-list-item>
    </v-card>
    <v-card class="mx-auto" outlined style="margin-top:10px">
      <v-list-item three-line>
        <v-list-item-content class="tk">
          <v-list-item-title class="headline mb-1">Bên thuê</v-list-item-title>
          <p>
            • Tên:
            <b>{{ptA.name}}</b>
          </p>
          <p>
            • Email:
            <b>{{ptA.email}}</b>
          </p>
          <p>
            • SDT:
            <b>{{ptA.phone}}</b>
          </p>
           <p>
            • Địa chỉ:
            <b>{{ptA.address}} {{ptA.district}} {{ptA.province}}</b>
          </p>
          <p>
            • Lĩnh vực:
            <b>{{ptA.industry}}</b>
          </p>
        </v-list-item-content>
      </v-list-item>
    </v-card>
    <v-card class="mx-auto" outlined style="margin-top:10px">
      <v-list-item three-line>
        <v-list-item-content class="tk">
          <v-list-item-title class="headline mb-1">Thông tin địa điểm</v-list-item-title>
          <p>
            • Tên:
            <b>{{pl.name}}</b>
          </p>
          <p>
            • Vị trí:
            <b>{{pl.address}}</b>
          </p>
          <p>
            • Chi phí:
            <b>{{detail.price}} / {{detail.time_unit|timeUnitText}}</b>
          </p>
          <div>
             <v-row v-if="pl.imgs">
            <v-col
              xs="12" sm="6" md="4"
              v-for="(item, index) in pl.imgs" :key="index"
            >
              <v-card flat tile class="d-flex" style="height:200px">
                <v-img
                  :src="getFile(item)"
                  aspect-ratio="1"
                  class="grey lighten-2"
                >
                  <template v-slot:placeholder>
                    <v-row
                      class="fill-height ma-0"
                      align="center"
                      justify="center"
                    >
                      <v-progress-circular indeterminate color="grey lighten-5"></v-progress-circular>
                    </v-row>
                  </template>
                </v-img>
              </v-card>
            </v-col>
          </v-row>
          </div>
         
        </v-list-item-content>
      </v-list-item>
    </v-card>
    <v-card class="mx-auto" outlined style="margin-top:10px">
      <v-list-item three-line>
        <v-list-item-content class="tk">
          <v-list-item-title class="headline mb-1">Thông tin bên thuê yêu cầu</v-list-item-title>
          <p>
            • Tên:
            <b>{{detail.name}}</b>
          </p>
          <p>
            • Vị trí:
            <b>{{detail.address}}</b>
          </p>
          <p>
            • Chi phí đề nghị:
            <b>{{detail.price}} / {{detail.time_unit|timeUnitText}}</b>
          </p>
          <p>
            • Thời gian thuê:
            <b>{{Date.parse(detail.start).toString('dd/MM/yyyy')}} <i class="fas fa-arrow-right"></i> {{Date.parse(detail.end).toString('dd/MM/yyyy')}}</b>
          </p>
          
        </v-list-item-content>
      </v-list-item>
      <v-card-actions>
        <v-btn :loading="approving" @click="approvePlaceRental(0)" class="ma-2" tile outlined text color="red">
          <v-icon left>fas fa-ban</v-icon>Hủy bỏ
        </v-btn>
        <v-btn :loading="approving" @click="approvePlaceRental(2)" class="ma-2" tile outlined text color="success">
          <v-icon left>fas fa-check</v-icon>Chấp nhận yêu cầu
        </v-btn>
      </v-card-actions>
    </v-card>
    </div>
</template>
<script>
import {getPlaceRetailDetail,approvePlaceRental} from '@/api/party-b/place-for-rent'
import { BASE_API } from "@/utils/base";

export default {
    computed:{
        rentalID(){
            return this.$route.params.id;
        }
    },
    data() {
        return {
            detail:{

            },
            ptB:{

            },
            ptA:{

            },
            pl:{

            },
            approving:false,
        }
    },
    methods:{
        getFile(img) {
      return BASE_API + "file/get/" + img;
    },
    approvePlaceRental(status){
      this.approving = true;
      approvePlaceRental({
        id:this.rentalID,
        status:status
      }).then(res=>{
        console.log(res);
        
        this.approving = false;
        this.detail.status = status;
      })
    }
    },
    created(){
        getPlaceRetailDetail({
            id:this.rentalID
        }).then(res=>{
            this.detail = res.data;
            this.pl = res.data.place_id;
            this.ptB=res.data.place_id.owner;
            this.ptA = res.data.party_renter;
            
            
        })
    }
}
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