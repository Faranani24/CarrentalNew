import { api } from './api';

export function createPayment(paymentDetails) {
    return api.post('/payments', paymentDetails).then(res => res.data);
}