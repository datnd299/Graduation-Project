<template>
    <v-card>
        <v-card-title class="headline">Cập nhật thông tin tài khoản</v-card-title>
        <div autocomplete="off" style="padding:10px">
             <v-text-field
            v-model="form.name"
            :counter="20"
            label="Tên"
            dense
            outlined=""
            required
          ></v-text-field>
          <v-text-field
            v-model="form.accName"
            :counter="20"
            label="Tên đăng nhập"
            dense
            outlined=""
            required
          ></v-text-field>
          <div>
              <v-checkbox
      v-model="form.changePass"
      label="Đổi mật khẩu"
    ></v-checkbox>
          </div>
        <div v-if="form.changePass">
          
           <v-text-field
            v-model="form.oldPass"
          browser-autocomplete="off"
            label="Mật khẩu cũ"
            dense
            type="password"
            outlined=""
            required
          ></v-text-field>
           <v-text-field
            v-model="form.password"
     
            
            label="Mật khẩu"
            dense
            type="password"
            outlined=""
            required
          ></v-text-field>
           <v-text-field
            v-model="form.passwordConfirm"
          
            label="Nhập lại mật khẩu"
          
            dense
            type="password"
            outlined=""
            required
          ></v-text-field>
            
        </div>
          
        </div>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn outlined  color="red darken-1"  @click="$emit('close')">Đóng</v-btn>
          <v-btn outlined  color="green darken-1" :loading="updating"  @click="updateAcc">Cập nhật</v-btn>
        </v-card-actions>
      </v-card>
</template>
<script>
import {updateAcc}  from "@/api/users";
export default {
    data() {
        return {
            form:{
                name:'',
                accName:'',
                phone:'',
                changePass:false,
                oldPass:'',
                password:'',
                passwordConfirm:''
            },
            updating:false
        }
    },
    props:{
        acc:{
            type:Object,
            default(){
                return {}
            }
        }
    },
    mounted(){
        if(this.acc){
            this.form.name = this.acc.name;
            this.form.accName = this.acc.acc_name;
            this.form.phone = this.acc.phone;
        }
    },
    watch:{
        acc(){
            this.form.name = this.acc.name;
            this.form.accName = this.acc.acc_name;
            this.form.phone = this.acc.phone;
            

        }
    },
    methods:{
        updateAcc(){
            this.updating = true;
            updateAcc({
                name: this.form.name,
            acc_name:this.form.accName,
            change_pass:this.form.changePass,
            old_pass:this.form.oldPass,
            password: this.form.password,
            password_confirm: this.form.passwordConfirm,
            }).then(res=>{
                this.updating = false;
                this.acc.name = this.form.name;
                this.acc.acc_name = this.form.accName;
                this.$emit('close',res.data.acc)
            })
            
        }
    }
}
</script>