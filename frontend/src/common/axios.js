import axios from 'axios';

// axios 객체 생성
export default axios.create({
    baseURL: 'http://localhost:8080',
    headers: {
        'Authorization': 'JWT ${jwt}',
        'Content-type': 'application/json',
    },
    withCredentials: true
})