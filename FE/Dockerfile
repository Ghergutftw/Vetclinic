## Use the official Node image as the base image
FROM node:14 AS builder
#
## Set the working directory inside the container
#WORKDIR /app
#
## Copy package.json and package-lock.json to the container
#COPY package*.json ./
#
## Install dependencies
#RUN npm install
#
## Copy the entire project to the container
#COPY . .
#
## Build the Angular app
#RUN npm run build
#
## Use a smaller image as the final image
#FROM nginx:alpine
#
## Copy the built app from the builder stage to the nginx container
#COPY --from=builder /app/dist /usr/share/nginx/html
#
## Expose the default port for Nginx
#EXPOSE 80
#
## Start Nginx when the container runs
#CMD ["nginx", "-g", "daemon off;"]

FROM node:16-alpine3.11 as angular

WORKDIR /app

COPY . .
RUN npm install
RUN npm run build

FROM httpd:alpine3.15

WORKDIR /usr/local/apache2/htdocs

COPY --from=angular /app/dist/fe-vet-clinic .


