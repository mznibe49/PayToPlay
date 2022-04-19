import ForgottenPasswordService from '../services/forgottenPassword.service';
import {REQUEST_FAILED, SEND_REQUEST} from "@/store/mutation-types";

const email = null;
const initialState = email
    ? { status: { readyToSendRequest: false }, email }
    : { status: { readyToSendRequest: true }, email: null };

export const fpw = {
    namespaced: true,
    state: initialState,
    mutations: {
        [SEND_REQUEST](state, email){
            state.status.readyToSendRequest = false;
            state.email = email;
        },
        [REQUEST_FAILED](state) {
            state.status.readyToSendRequest = true;
            state.email = null;
        },
    },
    actions: {
        forgetPassword({ commit }, email){
            return ForgottenPasswordService.forgetPassword(email).then(
                response => {
                    console.log(response.message);
                    commit(SEND_REQUEST, email);
                    return Promise.resolve(response);
                },
                error => {
                    console.log("Error in fpw action");
                    commit(REQUEST_FAILED);
                    return Promise.reject(error);
                }
            );
        }
    },
    getters: {}
};
