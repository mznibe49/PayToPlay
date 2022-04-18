<template>
  <div class="col-md-12">
    <div class="card card-container">
      <img
          id="profile-img"
          src="//ssl.gstatic.com/accounts/ui/avatar_2x.png"
          class="profile-img-card"
      />
      <!-- <button class="btn twitch btn-block" :disabled="loading"><img class="twitch-img" src="../assets/twitch.png" alt="twitch">
        <span class="twitch-text-login">Login with Twitch</span>
      </button>

      <button class="btn discord btn-block" :disabled="loading">
        <img class="discord-img" src="../assets/ds.png" alt="discord">
        <span>Login with Discord</span>
      </button> -->

      <Form @submit="handleLogin" :validation-schema="schema">
        <div class="form-group">
          <label for="username">Username</label>
          <Field name="username" type="text" class="form-control" />
          <ErrorMessage name="username" class="error-feedback" />
        </div>
        <div class="form-group">
          <label for="password">Password</label>
          <Field name="password" type="password" class="form-control" />
          <ErrorMessage name="password" class="error-feedback" />
        </div>

        <div class="form-group">
          <button class="btn btn-primary btn-block" :disabled="loading">
            <span
                v-show="loading"
                class="spinner-border spinner-border-sm"
            ></span>
            <span>Sign In</span>
          </button>
        </div>

        <div class="form-group forgotten-password">
          <a class="text-primary" href="/forgotten_password">Forgot your password ?</a>
        </div>

        <div class="form-group">
          <div v-if="message" class="alert alert-danger" role="alert">
            {{ message }}
          </div>
        </div>
      </Form>
    </div>
  </div>
</template>

<script>
import { Form, Field, ErrorMessage } from "vee-validate";
import * as yup from "yup";
import {mapActions, mapState} from "vuex";

export default {
  name: "Login",
  components: {
    Form,
    Field,
    ErrorMessage,
  },
  data() {
    const schema = yup.object().shape({
      username: yup.string().required("Username is required!"),
      password: yup.string().required("Password is required!"),
    });

    return {
      loading: false,
      message: "",
      schema,
    };
  },
  computed: {
    ...mapState({ loggedIn: state => state.auth.status.loggedIn}),
  },
  created() {
    if (this.loggedIn) {
      this.$router.push("/profile");
    }
  },
  methods: {
    ...mapActions(["auth/login"]),
    handleLogin(user) {
      this.loading = true;

      this.login(user).then(
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
      );
    },
  },
};
</script>

<style scoped>
label {
  display: block;
  margin-top: 10px;
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

.twitch {
  color: white;
  background-color : #9146ff;
}

.discord {
  color: white;
  background-color: #2c2f33;
}

.discord:hover {
  color: #5865F2;
}

.discord-img {
  margin-right: 32px;
}

.twitch-img {
  margin-right: 30px;
}

.twitch-text-login {
  margin-right: 25px;
}

Form {
  margin-top: 30px;
}

div.forgotten-password {
  text-align: center;
}
</style>
