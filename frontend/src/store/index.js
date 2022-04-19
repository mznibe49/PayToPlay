import { createStore } from "vuex";
import { auth } from "./auth.module";
import { fpw } from "./forgottenPassWord.module";

const store = createStore({
    modules: {
        auth, // for authentification
        fpw,  // forgotten password
    },
});

export default store;
