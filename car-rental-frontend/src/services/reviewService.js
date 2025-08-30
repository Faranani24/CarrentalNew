import { api } from './api';

export function submitReview(reviewDetails) {
    return api.post('/reviews', reviewDetails).then(res => res.data);
}