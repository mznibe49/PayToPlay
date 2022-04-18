<template>
  <div class="container">
    <header class="jumbotron">
      <h3>
        <strong>{{currentUser.username}}</strong> Profile
      </h3>
    </header>
    <p>
      <strong>Token:</strong>
      {{currentUser.accessToken.substring(0, 20)}} ... {{currentUser.accessToken.substr(currentUser.accessToken.length - 20)}}
    </p>
    <p>
      <strong>Id:</strong>
      {{currentUser.id}}
    </p>
    <p>
      <strong>Email:</strong>
      {{currentUser.email}}
    </p>
    <strong>Authorities:</strong>
    <ul>
      <li v-for="role in currentUser.roles" :key="role">{{role}}</li>
    </ul>
  </div>
</template>

<script>
import {mapState} from "vuex";

export default {
  name: 'Profile',
  computed: {
    // 1st one way to do it
    /* currentUser() {
      return this.$store.state.auth.user;
    } */
    // 2nd way to do it: more explicite one
    ...mapState({ currentUser: state => state.auth.user}),
  },
  mounted() {
    if (!this.currentUser) {
      this.$router.push('/login');
    }
  }
};
</script>
