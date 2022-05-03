import ForgottenPasswordService from '../services/forgottenPassword.service';
import {
    REQUEST_FAILED_FPW,
    REQUEST_FAILED_UPW,
    SEND_REQUEST_FPW,
    SEND_REQUEST_UPW
} from "@/store/mutation-types";

const email = null;
const token = null;
const newPassword = null;

const initialState = {email, token, newPassword};

export const fpw = {
    namespaced: true,
    state: initialState,
    mutations: {
        [SEND_REQUEST_FPW](state, email){
            state.email = email;
            state.token = null;
            state.newPassword = null;
        },
        [REQUEST_FAILED_FPW](state) {
            state.email = null;
            state.token = null;
            state.newPassword = null;
        },
        [SEND_REQUEST_UPW](state, token, newPw){
            state.email = null;
            state.token = token;
            state.newPassword = newPw;
        },
        [REQUEST_FAILED_UPW](state){
            state.email = null;
            state.token = null;
            state.newPassword = null;
        }
    },
    actions: {
        forgetPassword({ commit }, email){
            return ForgottenPasswordService.forgetPassword(email).then(
                response => {
                    console.log(response.message);
                    commit(SEND_REQUEST_FPW, email);
                    return Promise.resolve(response);
                },
                error => {
                    commit(REQUEST_FAILED_FPW);
                    return Promise.reject(error);
                }
            );
        },
        updatePassword({ commit },data){

            return ForgottenPasswordService.updatePassword(data.token, data.newPassword).then(
                response => {
                    console.log(response.message);
                    commit(SEND_REQUEST_UPW, data.userToken, data.newPassword);
                    return Promise.resolve(response);
                },
                error => {
                    commit(REQUEST_FAILED_UPW);
                    return Promise.reject(error);
                }
            );
        }
    },
    getters: {}
};
