# Use the official Node.js image as the base image
FROM node:14-alpine as builder

# Set the working directory in the container
WORKDIR /app

# Copy package.json and package-lock.json to the container
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy the application files to the container
COPY . .

# Build the Angular app
RUN npm run build

# Use a smaller base image for the final image
FROM nginx:alpine

# Copy the built Angular app from the builder stage to the nginx public directory
COPY --from=builder /app/dist /usr/share/nginx/html

# Expose port 80 for the application
EXPOSE 80

# CMD to start Nginx when the container starts
CMD ["nginx", "-g", "daemon off;"]
