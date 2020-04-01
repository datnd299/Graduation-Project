<template>
    <v-card>
        <v-card-title class="headline">Thêm tài khoản mới</v-card-title>
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
          <input style="display:none" type="text" name="fakeusernameremembered"/>
<input style="display:none" type="password" name="fakepasswordremembered"/>
           <v-text-field
            v-model="form.email"
            :counter="20"
           
            label="Email"
            name="sdf"
            dense
            outlined=""
            required
          ></v-text-field>
           <v-text-field
            v-model="form.password"
            autocomplete="new-password"
            name="sdf"
            label="Mật khẩu"
            dense
            type="password"
            outlined=""
            required
          ></v-text-field>
           <v-text-field
            v-model="form.passwordConfirm"
            autocomplete="new-password"
            label="Nhập lại mật khẩu"
            name="sdf"
            dense
            type="password"
            outlined=""
            required
          ></v-text-field>
          <v-radio-group row dense v-model="form.role">
      <v-radio
        key="1"
        label="Admin"
        value="partyAAdmin"
      ></v-radio>
       <v-radio
        key="2"
        label="Nhân viên"
        value="partyAEm"
      ></v-radio>
    </v-radio-group>
        </div>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn outlined  color="red darken-1"  @click="$emit('close')">Đóng</v-btn>
          <v-btn outlined  color="green darken-1" :loading="updating"  @click="createAcc">Tạo mới</v-btn>
        </v-card-actions>
      </v-card>
</template>
<script>
import {createNew}  from "@/api/party-a/account";
export default {
    data() {
        return {
            form:{
                name:'',
                accName:'',
                email:'',
                password:'',
                passwordConfirm:'',
                role:'partyAEm'
            },
            updating:false
        }
    },
    methods:{
        createAcc(){
            createNew({
                name: this.form.name,
            email: this.form.email,
            acc_name:this.form.accName,
            password: this.form.password,
            role:this.form.role,
            password_confirm: this.form.passwordConfirm
            }).then(res=>{
                
                this.$emit('accCreated',res.data.acc)
            })
            
        }
    }
}
</script>