import axios from 'axios';

function localAxios() {
    return axios.create({
        baseURL: 'http://localhost:8080/api/',
        headers: {
            'Content-Type': 'application/json;charset=utf-8',
        },
    });
}

export { localAxios };