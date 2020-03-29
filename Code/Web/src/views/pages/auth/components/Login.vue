<template>
    <div class="slide-in-fwd-center">
         <div class="kt-login__head" style="text-align:right">
                <span class="kt-login__signup-label">
                  <!-- {{$t("AUTH.GENERAL.NO_ACCOUNT")}} -->
                  Chưa có tài khoản
                </span>&nbsp;&nbsp;
                <router-link class="kt-link kt-login__signup-link" :to="{ name: 'signup' }">
                  <!-- {{ $t("AUTH.GENERAL.SIGNUP_BUTTON") }} -->
                  Đăng ký
                </router-link>
              </div>
              <v-list-item-content>
                <v-list-item-title class="headline mb-1">Đăng nhập</v-list-item-title>
                <!-- <v-list-item-subtitle>Greyhound divisely hello coldly fonwderfully</v-list-item-subtitle> -->
              </v-list-item-content>

              <b-form class="kt-form" @submit.stop.prevent="onSubmit">
                <div role="alert" class="alert alert-info">
                  <div class="alert-text">
                    Use account
                    <strong>admin@demo.com</strong> and password
                    <strong>demo</strong> to continue.
                  </div>
                </div>
                <div
                  role="alert"
                  v-bind:class="{ show: errors.length }"
                  class="alert fade alert-danger"
                >
                  <div class="alert-text" v-for="(error, i) in errors" :key="i">{{ error }}</div>
                </div>

                <b-form-group id="example-input-group-1" label label-for="example-input-1">
                  <b-form-input placeholder="Email hoặc tài khoản đăng nhập"
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

                <b-form-group id="example-input-group-2" label label-for="example-input-2">
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

                <!--begin::Action-->
                <div class="kt-login__actions">
                  <a href="#" class="kt-link kt-login__link-forgot">Quên mật khẩu</a>
                  <b-button
                    style="float:right"
                    type="submit"
                    id="kt_submit"
                    class="btn btn-primary btn-elevate kt-login__btn-primary"
                  >Đăng nhập</b-button>
                </div>
                <!--end::Action-->
              </b-form>
    </div>
</template>
<script>
import { mapState } from "vuex";
import { LOGIN, LOGOUT } from "@/store/auth.module";

import { validationMixin } from "vuelidate";
import {  minLength, required } from "vuelidate/lib/validators";

export default {
  mixins: [validationMixin],
  name: "login",
  data() {
    return {
      // Remove this dummy login info
      form: {
        email: "doitac@gm.com",
        password: "1"
      }
    };
  },
  validations: {
    form: {
      email: {
        required,
      },
      password: {
        required,
        minLength: minLength(1)
      }
    }
  },
  created() {},
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

      const email = this.$v.form.email.$model;
      const password = this.$v.form.password.$model;

      // clear existing errors
      this.$store.dispatch(LOGOUT);

      // set spinner to submit button
      const submitButton = document.getElementById("kt_submit");
      submitButton.classList.add(
        "kt-spinner",
        "kt-spinner--light",
        "kt-spinner--right"
      );

      // dummy delay

      // send login request
      this.$store
        .dispatch(LOGIN, { email, password })
        // go to which page after successfully login
        .then(() => {
          this.$router.push({ name: "dashboard" });
          submitButton.classList.remove(
            "kt-spinner",
            "kt-spinner--light",
            "kt-spinner--right"
          );
        })
        .catch(() => {
          submitButton.classList.remove(
            "kt-spinner",
            "kt-spinner--light",
            "kt-spinner--right"
          );
        });
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