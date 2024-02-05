FROM node:18.17 as angular

WORKDIR /app

COPY . .

# Update npm to the specified version
RUN npm install -g npm@10.4.0

RUN npm install
RUN npm run build

FROM httpd:alpine3.15

WORKDIR /usr/local/apache2/htdocs

COPY --from=angular /app/dist/fe-vet-clinic .

LABEL name="front-end" \
      version="1.1"

#docker login
#docker tag e931d5634f7b ghergutmadalin/front-end:1.1
#docker push ghergutmadalin/front-end:1.1
