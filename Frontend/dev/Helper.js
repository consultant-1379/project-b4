const axios = require('axios');

module.exports = {
    basepath: 'http://localhost:8000',
    async get(url) {
        try {
            const response = await axios.get(`${this.basepath}${url}`);
            if (response.status === 200) {
                return response.data;
            }
            return { error: `Status ${response.status}` };
        } catch (e) {
            return { error: e.message };
        }
    },

    async post(url, payload) {
        try {
            console.log("this is the payload : " , payload);
            const response = await axios.post(`${this.basepath}${url}`, JSON.stringify(payload));
            if (response.status === 200) {
                return response.data;
            }
            return { error: `Status ${response.status}` };
        } catch (e) {
            return { error: e.message };
        }
    },

    async put(url, payload) {
        try {
            const response = await axios.put(`${this.basepath}${url}`, payload);
            if (response.status === 200) {
                return response.data;
            }
            return { error: `Status ${response.status}` };
        } catch (e) {
            return { error: e.message };
        }
    },

    async delete(url, payload) {
        try {
            const response = await axios.put(`${this.basepath}${url}`, payload);
            if (response.status === 200) {
                return response.data;
            }
            return { error: `Status ${response.status}` };
        } catch (e) {
            return { error: e.message };
        }
    },


};
