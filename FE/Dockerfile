# Use the official Node.js image for building Angular app
FROM node:16-alpine3.11 as angular

WORKDIR /app

COPY . .
RUN npm install
RUN npm run build

# Use the official Apache HTTP Server image for serving the Angular app
FROM httpd:alpine3.15

WORKDIR /usr/local/apache2/htdocs

# Copy the built Angular app from the previous stage
COPY --from=angular /app/dist/fe-vet-clinic .

# Set metadata for the image
LABEL name="front-end" \
      version="1.1"
