<template>
  <div class="col-md-12">
    <div class="card card-container">
      <img
          id="profile-img"
          src="//ssl.gstatic.com/accounts/ui/avatar_2x.png"
          class="profile-img-card"
      />
      <Form @submit="ResetPassword" :validation-schema="schema">
          <div class="form-group">
            <label for="password">Password</label>
            <Field name="password" type="password" class="form-control" />
            <ErrorMessage name="password" class="error-feedback" />
          </div>
          <div class="form-group">
            <label for="conf_password">Password Confirmation</label>
            <Field name="conf_password" type="password" class="form-control" />
            <ErrorMessage name="conf_password" class="error-feedback" />
          </div>

          <div class="form-group">
            <button class="btn btn-primary btn-block" :disabled="loading">
                <span
                    v-show="loading"
                    class="spinner-border spinner-border-sm"
                ></span>
              Update
            </button>
          </div>
      </Form>

      <div
          v-if="message"
          class="alert"
          :class="successful ? 'alert-success' : 'alert-danger'"
      >
        {{ message }}
      </div>

    </div>
  </div>
</template>

<script>
import { Form, Field, ErrorMessage } from "vee-validate";
import * as yup from "yup";
import {mapActions} from "vuex";

export default {
  name: "ForgottenPw",
  components: {
    Form,
    Field,
    ErrorMessage,
  },
  data(){
    const schema = yup.object().shape({
      password: yup
          .string()
          .required("Password is required!")
          .min(6, "Must be at least 6 characters!")
          .max(40, "Must be maximum 40 characters!"),
      conf_password: yup
          .string()
          .required("Password is required!")
          .oneOf([yup.ref('password'), null], 'Password must match')
    });

    return {
      successful: false,
      loading: false,
      message: "",
      schema,
    };
  },
  computed: {
    userToken(){
      return this.$route.params.token;
    }
  },
  methods: {
     ...mapActions('fpw',['updatePassword']),
    async ResetPassword(passwordForm) {

      this.message = "";
      this.successful = false;
      this.loading = true;

      let newPassword = passwordForm.password;

      // this.$store.dispatch("fpw/updatePassword", email).then(
      await this.updatePassword({token : this.userToken, newPassword : newPassword}).then(
          (data) => {
            this.message = data.message;
            console.log("data msg : ",this.message);
            this.successful = true;
            this.loading = false;
          },
          (error) => {
            console.log("data msg : ",error.message);

            this.message =
                (error.response &&
                    error.response.data &&
                    error.response.data.message) ||
                error.message ||
                error.toString();
            this.successful = false;
            this.loading = false;
          }
      );
    }
  },
  mounted() {
  }
}
</script>

<style scoped>

.card-container.card {
  max-width: 350px !important;
  padding: 40px 40px;
}
.card {
  background-color: #f7f7f7;
  padding: 20px 25px 30px;
  margin: 50px auto 25px;
  -moz-border-radius: 2px;
  -webkit-border-radius: 2px;
  border-radius: 2px;
  -moz-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
  -webkit-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
  box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
}
.profile-img-card {
  width: 96px;
  height: 96px;
  margin: 0 auto 10px;
  display: block;
  -moz-border-radius: 50%;
  -webkit-border-radius: 50%;
  border-radius: 50%;
}
.error-feedback {
  color: red;
}
</style>
