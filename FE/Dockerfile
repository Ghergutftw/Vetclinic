# Stage 1: Build Angular app
FROM node:14 AS builder

WORKDIR /app

COPY package.json package-lock.json ./

RUN npm install

COPY . .

RUN npm run build

# Stage 2: Create a smaller image with just the built app
FROM nginx:latest

COPY --from=builder /app/dist /usr/share/nginx/html

EXPOSE 4200

CMD ["nginx", "-g", "daemon off;"]
