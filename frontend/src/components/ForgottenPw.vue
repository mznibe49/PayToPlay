<template>
  <div class="col-md-12">
    <div class="card card-container">

      <img
          id="profile-img"
          src="//ssl.gstatic.com/accounts/ui/avatar_2x.png"
          class="profile-img-card"
      />
      <Form @submit="HandleForgottenPassword" :validation-schema="schema">
        <div v-if="!successful">

          <div class="form-group">
            <label for="email">We will be sending a reset password link to your e-mail</label>
            <!-- <Field name="email" type="email" class="form-control" /> -->
            <Field id ="email" name="email" type="email" class="form-control" />
            <ErrorMessage name="email" class="error-feedback" />
          </div>

          <div class="form-group">
            <button class="btn btn-primary btn-block" :disabled="loading">
                <span
                    v-show="loading"
                    class="spinner-border spinner-border-sm"
                ></span>
              Send
            </button>
          </div>
        </div>
       <!--  <div class="form-group">
          <div v-if="message" class="alert alert-danger" role="alert">
            {{ message }}
          </div>
        </div> -->
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

export default {
  name: "ForgottenPw",
  components: {
    Form,
    Field,
    ErrorMessage,
  },
  data(){
    const schema = yup.object().shape({
      email: yup
          .string()
          .required("Email is required!")
          .email("Email is invalid!")
          .max(50, "Must be maximum 50 characters!")
    });

    return {
      successful: false,
      loading: false,
      message: "",
      schema,
    };
  },
  computed: {
    /*addingPlaceHolder(){
      return
    }*/
  },
  methods: {
    HandleForgottenPassword() {
      this.message = "";
      this.successful = false;
      this.loading = true;

      /*this.$store.dispatch("auth/login", user).then(
          () => {
            this.$router.push("/profile");
          },
          (error) => {
            this.loading = false;
            this.message =
                (error.response &&
                    error.response.data &&
                    error.response.data.message) ||
                error.message ||
                error.toString();
          }
      );*/
    }
  },
  mounted() {
    document.getElementById("email").placeholder = "Enter your e-mail";
  }
}
</script>

<style scoped>
label {
  display: block;
  margin-top: 10px;
  text-align: center;
}
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
