FROM node:12

COPY main.js package.json package-lock.json /app/
WORKDIR /app
RUN npm install

CMD node main.js
EXPOSE 8000
