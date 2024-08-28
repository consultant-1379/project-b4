// eslint-disable-next-line import/no-extraneous-dependencies
const proxy = require('express-http-proxy');
// eslint-disable-next-line import/no-extraneous-dependencies
const bodyParser = require('body-parser');

const axios = require('axios');
const express = require("express");

const euisdkRoutes = require('./euisdk-routes');

module.exports = (app) => {

    app.use(bodyParser.json());
    app.use(express.json());
    app.use(express.urlencoded());

    app.get(/^\/api\/(.*)/, async (req, res) => {
        console.log("API endpoints");
        try {
            const url = 'http://backend:8080/' + req.url.split("/api/")[1];
            const response = await axios.get(url);

            if (response.status === 200) {
                res.send(JSON.stringify({"data": response.data}));
            } else {
                res.send(JSON.stringify({error: `Status ${response.status}`}));
            }
        } catch (e) {
            res.send({ error: e.message });
        }
    });

    app.post(/^\/api\/(.*)/, async (req, res) => {
        console.log("API endpoints");
        try {
            const url = 'http://backend:8080/' + req.url.split("/api/")[1];
            const payload = req.body
            const response = await axios.post(url, payload);

            if (response.status === 200) {
                res.send(JSON.stringify({"data": response.data}));
            } else {
                res.send(JSON.stringify({error: `Status ${response.status}`}));
            }
        } catch (e) {
            res.send({error: e.message});
        }
    });


    app.put(/^\/api\/(.*)/, async (req, res) => {
        console.log("API endpoints");
        try {
            const url = 'http://backend:8080/' + req.url.split("/api/")[1];
            const payload = req.body
            const response = await axios.put(url,payload);

            if (response.status === 200) {
                res.send(JSON.stringify({"data": response.data}));
            } else {
                res.send(JSON.stringify({error: `Status ${response.status}`}));
            }
        } catch (e) {
            res.send({error: e.message()});
        }
    });

    app.delete(/^\/api\/(.*)/, async (req, res) => {
        console.log("API endpoints");
        try {
            const url = 'http://backend:8080/' + req.url.split("/api/")[1];
            const response = await axios.delete(url);

            if (response.status === 200) {
                res.send(JSON.stringify({"data": response.data}));
            } else {
                res.send(JSON.stringify({error: `Status ${response.status}`}));
            }
        } catch (e) {
            res.send({error: e.message()});
        }
    });


    app.get('/getIssues', async (req, res) => {

        try {
            const response = await axios.get('http://backend:8080/issues');
            if (response.status === 200) {
                res.send(JSON.stringify({"data" : response.data}));
            } else {
                res.send(JSON.stringify({error: `Status ${response.status}`}));
            }
        } catch (e) {
            res.send({ error: e.message });
        }
    });

    app.get('/hello', (req, res) => {
        res.send(JSON.stringify({
            get: 'done',
        }));
    });

    app.post('/register', async (req, res) => {
        try {
            const response = await axios.post('http://backend:8080/signup', req.body);
            if (response.status === 200) {
                res.send(JSON.stringify({"data" : response.data}));
            } else {
                res.send(JSON.stringify({ error: `Status ${response.status}` }));
            }
        } catch (e) {
            res.send({ error: e.message });
        }
    });

    app.post('/login', async (req, res) => {
        try {
            const response = await axios.post('http://backend:8080/login', req.body);
            if (response.status === 200) {
                res.send(JSON.stringify({"data" : response.data}));
            } else {
                res.send(JSON.stringify({ error: `Status ${response.status}` }));
            }
        } catch (e) {
            res.send({ error: e.message });
        }
    });

    app.post('/addItem', async (req, res) => {
        try {
            console.log("this is the request ", req);
            const response = await axios.post('http://backend:8080/addItem', req.body);
            if (response.status === 200) {
                res.send(JSON.stringify({"data" : response.data}));
            } else {
                res.send(JSON.stringify({ error: `Status ${response.status}` }));
            }
        } catch (e) {
            res.send({ error: e.message });
        }
    });

    app.get('/api/users', (req, res) => {
        res.send(JSON.stringify({
            response: {
                body: [{
                    username: 'calvin',
                    userid: 1,
                },
                    {
                        username: 'rupal',
                        userid: 2,
                    },
                    {
                        username: 'amrutha',
                        userid: 3,
                    },
                    {
                        username: 'shruti',
                        userid: 4,
                    },
                ],
            },
        }));
    });


    app.post('/hello', (req, res) => {
        res.send(JSON.stringify({
            post: 'done',
        }));
    });


    app.put('/hello', (req, res) => {
        res.send(JSON.stringify({
            put: 'done',
        }));
    });

    app.delete('/hello', (req, res) => {
        res.send(JSON.stringify({
            delete: 'done',
        }));
    });

    /**
     * Initialize E-UI SDK routes
     */
    euisdkRoutes.init(app);

    // ------------------------------------------------------------
    // Custom Routes
    // ------------------------------------------------------------

    // ------------------------------------------------------------
    // Mocking server responses...
    // ------------------------------------------------------------

    /**
     * List all routes that should be proxied here.
     *
     * A route definition must be defined as follows:
     * { path: <path (path can be a regular expression)> }
     *
     * i.e.
     * const routes = [{ path: '/my/first/path' }]
     *
     * See documentation for further details
     *
     * http://presentation-layer.lmera.ericsson.se/euisdkdocs/#docs?chapter=tools&section=proxy
     */
    const routes = [];

    /**
     * Proxy all routes via the server specified in the start script.
     *
     * To start the project with a proxy setup to handle routes from
     * the "routes" array the following command is used:
     *
     * npm start -- --server=<path-to-you-application-server>
     *
     * eg.
     * npm start -- --server=http://localhost:3000
     *
     * Only use a proxy for the paths listed in the routes array.
     */
    process.argv.forEach((arg) => {
        if (arg.startsWith('--server')) {
            const _proxy = arg.substring(arg.indexOf('=') + 1);
            app.use('/', proxy(_proxy, {
                filter: req => routes.some(_route => RegExp(_route.path)
                    .test(req.path)),
            }));
        }
    });
};
