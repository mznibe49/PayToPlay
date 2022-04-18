import AuthService from '../services/auth.service';
import {LOGIN_FAILURE, LOGIN_SUCCESS, LOGOUT, REGISTER_FAILURE, REGISTER_SUCCESS} from "@/store/mutatuin-types";

const user = JSON.parse(localStorage.getItem('user'));
const initialState = user
    ? { status: { loggedIn: true }, user }
    : { status: { loggedIn: false }, user: null };

export const auth = {
    namespaced: true,
    state: initialState,
    // actions are asynchronous
    // one action can call multiple mutations
    // that's way every time we call dispatch function that triggers actions
    // we should use "await" before every dispatch call
    // and use "async" before the method that contains a dispatch call
    // for example check "handleRegister" in methods existing in Register components
    actions: {
        login({ commit }, user) {
            return AuthService.login(user).then(
                user => {
                    commit(LOGIN_SUCCESS, user);
                    return Promise.resolve(user);
                },
                error => {
                    commit(LOGIN_FAILURE);
                    return Promise.reject(error);
                }
            );
        },
        logout({ commit }) {
            AuthService.logout();
            commit(LOGOUT);
        },
        register({ commit }, user) {
            return AuthService.register(user).then(
                response => {
                    commit(REGISTER_SUCCESS);
                    return Promise.resolve(response.data);
                },
                error => {
                    commit(REGISTER_FAILURE);
                    return Promise.reject(error);
                }
            );
        }
    },
    // mutations are synchronous
    mutations: {
        [LOGIN_SUCCESS](state, user) {
            state.status.loggedIn = true;
            state.user = user;
        },
        [LOGIN_FAILURE](state) {
            state.status.loggedIn = false;
            state.user = null;
        },
        [LOGOUT](state) {
            state.status.loggedIn = false;
            state.user = null;
        },
        [REGISTER_SUCCESS](state) {
            state.status.loggedIn = false;
        },
        [REGISTER_FAILURE](state) {
            state.status.loggedIn = false;
        }
    }
};
