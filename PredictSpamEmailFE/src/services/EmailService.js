import axios from 'axios';

const EMAIL_API_BASE_URL = "http://localhost:8082/api/v1/email";

class EmailService {

    getEmails(){
        return axios.get(`${EMAIL_API_BASE_URL}/emails`);
    }

    createEmail(email){
        return axios.post(`${EMAIL_API_BASE_URL}/create`, email);
    }

    getEmailById(emailId){
        return axios.get(EMAIL_API_BASE_URL + '/detail-email?emailId='+ emailId);
    }
    deleteEmail(emailId){
        return axios.delete(`${EMAIL_API_BASE_URL}/delete?emailId=` + emailId);
    }
}

export default new EmailService()