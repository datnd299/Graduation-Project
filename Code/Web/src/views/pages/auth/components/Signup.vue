<template>
  <div class="slide-in-fwd-center">
    <div class="kt-login__head" style="text-align:right">
      <span class="kt-login__signup-label">
        <!-- {{$t("AUTH.GENERAL.NO_ACCOUNT")}} -->
        Đã có tài khoản có tài khoản
      </span>&nbsp;&nbsp;
      <router-link class="kt-link kt-login__signup-link" :to="{ name: 'login' }">
        <!-- {{ $t("AUTH.GENERAL.SIGNUP_BUTTON") }} -->
        Đăng nhập
      </router-link>
    </div>
    <v-list-item-content>
      <v-list-item-title class="headline mb-1">Đăng ký</v-list-item-title>
      <!-- <v-list-item-subtitle>Greyhound divisely hello coldly fonwderfully</v-list-item-subtitle> -->
    </v-list-item-content>
    <b-form>
      <b-form-group label="Bạn là:">
        <b-form-radio-group v-model="typeForm" name="radio-options-slots">
          <b-form-radio value="partyA">Bên thuê</b-form-radio>
          <b-form-radio value="partyB">Bên cho thuê</b-form-radio>
        </b-form-radio-group>
      </b-form-group>
    </b-form>

    <b-form class="kt-form" @submit.stop.prevent="onSubmit">
      <input style="height:0" type="text" name="username"/>
      <input style="height:0" type="password" name="password"/>
      <b-form-group id="example-input-group-1" label-for="example-input-1" label="Địa chỉ email">
        <b-form-input autocomplete="false"
          id="example-input-1"
          name="example-input-1"
          v-model="$v.form.email.$model"
          :state="validateState('email')"
          aria-describedby="input-1-live-feedback"
        ></b-form-input>
        <b-form-invalid-feedback
          id="input-1-live-feedback"
        >Email is required and a valid email address.</b-form-invalid-feedback>
      </b-form-group>
      <b-form-group id="example-input-group-2" label="Mật khẩu" label-for="example-input-2">
        <b-form-input
          type="password"
          id="example-input-2"
          name="example-input-2"
          v-model="$v.form.password.$model"
          :state="validateState('password')"
          aria-describedby="input-2-live-feedback"
        ></b-form-input>
        <b-form-invalid-feedback id="input-2-live-feedback">Password is required.</b-form-invalid-feedback>
      </b-form-group>
      <b-form-group id="example-input-group-3" label="Nhập lại mật khẩu" label-for="example-input-2">
        <b-form-input
          type="password"
          id="example-input-3"
          name="example-input-3"
          v-model="$v.form.passwordConfirm.$model"
          :state="validateState('passwordConfirm')"
          aria-describedby="input-3-live-feedback"
        ></b-form-input>
        <b-form-invalid-feedback id="input-3-live-feedback">Password is required.</b-form-invalid-feedback>
      </b-form-group>
      <b-form-group id="example-input-group-4" label="Tên đơn vị" label-for="example-input-2">
        <b-form-input
          type="text"
          id="example-input-4"
          name="example-input-4"
          v-model="$v.form.name.$model"
          :state="validateState('name')"
          aria-describedby="input-4-live-feedback"
        ></b-form-input>
        <b-form-invalid-feedback id="input-4-live-feedback">Nhập tên đơn vị</b-form-invalid-feedback>
      </b-form-group>
      <b-form-group id="example-input-group-5" label="SĐT liên hệ" label-for="example-input-2">
        <b-form-input
          type="text"
          id="example-input-5"
          name="example-input-5"
          v-model="$v.form.phone.$model"
    
        ></b-form-input>
      </b-form-group>
      <b-form-group id="example-input-group-6" label="Tỉnh / TP" label-for="example-input-2">
        <b-form-input
          type="text"
          id="example-input-6"
          name="example-input-6"
          v-model="$v.form.province.$model"
    
        ></b-form-input>
      </b-form-group>
      <b-form-group id="example-input-group-7" label="Quận / huyện" label-for="example-input-7">
        <b-form-input
          type="text"
          id="example-input-7"
          name="example-input-7"
          v-model="$v.form.district.$model"
        
        ></b-form-input>
      </b-form-group>
      <b-form-group id="example-input-group-8" label="Địa chỉ" label-for="example-input-8">
        <b-form-input
          type="text"
          id="example-input-8"
          name="example-input-8"
          v-model="$v.form.address.$model"
     
        ></b-form-input>
      </b-form-group>
      <b-form-group v-if="typeForm=='partyA'" id="example-input-group-9" label="Lĩnh vực" label-for="example-input-9">
        <b-form-input
          type="text"
          id="example-input-9"
          name="example-input-9"
          v-model="$v.form.industry.$model"
     
        ></b-form-input>
      </b-form-group>
      <!--begin::Action-->
      <div class="kt-login__actions">
        <b-button
          style="float:right"
          type="submit"
          id="kt_submit"
          class="btn btn-primary btn-elevate kt-login__btn-primary"
        >Đăng ký</b-button>
      </div>
      <!--end::Action-->
    </b-form>
  </div>
</template>
<script>

import { mapState } from "vuex";

import { validationMixin } from "vuelidate";
import { email, minLength, required } from "vuelidate/lib/validators";
import {signupPartyB,signupPartyA} from '@/api/users'

export default {
  mixins: [validationMixin],
  name: "login",
  data() {
    return {
      // Remove this dummy login info
      typeForm:'',
      form: {
        email: "",
        password: "",
        passwordConfirm:'',
        name:'',
        phone:'',
        province:'',
        district:'',
        address:'',
        industry:'',

      }
    };
  },
  validations: {

    
    form: {
      email: {
        required,
        email
      },
      password: {
        required,
        minLength: minLength(1)
      },
      passwordConfirm: {
        required,
        minLength: minLength(1),

      },
      name:{
        required
      },
      phone:{},province:{},district:{},address:{},industry:{}
    }
  },
  created() {
    this.typeForm = 'partyA'
  },
  methods: {
    validateState(name) {
      const { $dirty, $error } = this.$v.form[name];
      return $dirty ? !$error : null;
    },
    resetForm() {
      this.form = {
        email: null,
        password: null
      };

      this.$nextTick(() => {
        this.$v.$reset();
      });
    },
    onSubmit() {
      this.$v.form.$touch();
      if (this.$v.form.$anyError) {
        return;
      }

      
      // set spinner to submit button
      const submitButton = document.getElementById("kt_submit");
      submitButton.classList.add(
        "kt-spinner",
        "kt-spinner--light",
        "kt-spinner--right"
      );

      // dummy delay

      // send login request
      var req = this.form;
      if(this.typeForm=='partyA'){
        signupPartyA(req).then(res=>{
          submitButton.classList.remove(
            "kt-spinner",
            "kt-spinner--light",
            "kt-spinner--right"
          );
          console.log(res);
          this.$toast.open({
      message: 'Tạo tài khoản thành công, hãy đăng nhập',
      type: 'success',
      position:'top-right',
      duration:'3000'
      // all other options
  });
          
        }).catch(() => {
          submitButton.classList.remove(
            "kt-spinner",
            "kt-spinner--light",
            "kt-spinner--right"
          );
        });;
      }else{
        signupPartyB(req).then(res=>{
          submitButton.classList.remove(
            "kt-spinner",
            "kt-spinner--light",
            "kt-spinner--right"
          );
          console.log(res);
           this.$toast.open({
      message: 'Tạo tài khoản thành công, hãy đăng nhập',
      type: 'success',
      position:'top-right',
      duration:'3000'
      // all other options
  });
        }).catch(() => {
          submitButton.classList.remove(
            "kt-spinner",
            "kt-spinner--light",
            "kt-spinner--right"
          );
        });;
      }
    }
  },
  computed: {
    ...mapState({
      errors: state => state.auth.errors
    }),
    backgroundImage() {
      return process.env.BASE_URL + "assets/media/bg/bg-4.jpg";
    }
  }
};
</script>