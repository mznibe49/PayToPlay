import axios from 'axios';

const API_URL = 'http://localhost:8080/api/';

class ForgottenPasswordService {
    forgetPassword(emailForm) {
        return axios
            .post(API_URL + 'forget_password', {
                email: emailForm.email,
            })
            .then(response => {
                return response.data;
            });
    }
}

export default new ForgottenPasswordService();
