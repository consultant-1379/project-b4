FROM node:14-alpine

RUN npm config set @eui:registry https://arm.lmera.ericsson.se/artifactory/api/npm/proj-e-uisdk-npm-local/
RUN npm config set @eds:registry https://arm.rnd.ki.sw.ericsson.se/artifactory/api/npm/proj-eds-npm-local/
RUN npm config set proxy=http://www-proxy.lmera.ericsson.se:8080
RUN npm config set https-proxy=http://www-proxy.lmera.ericsson.se:8080

WORKDIR /home/retro_frontend

COPY package.json .npmrc ./

RUN npm install

COPY ./ ./

RUN npm build

ENTRYPOINT ["npm", "run", "start"]
