import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
    vus: 250,
    duration: '30s',

    thresholds: {
        http_req_failed: ['rate<0.01'],
        http_req_duration: ['p(95)<1000'],
    },
};

const BASE_URL = 'http://host.docker.internal:8081';

export default function () {

    const payload = JSON.stringify({
        email: 'yash@example.com',
        password: 'Password@123'
    });

    const params = {
        headers: {
            'Content-Type': 'application/json',
        },
    };

    const response = http.post(
        `${BASE_URL}/api/v1/auth/login`,
        payload,
        params
    );

    const success = check(response, {
        'status is 200': (r) => r.status === 200,
    });

    if (!success) {
        console.log('---------------------------');
        console.log(`Status : ${response.status}`);
        console.log(`Body   : ${response.body}`);
        console.log('---------------------------');
    }

}