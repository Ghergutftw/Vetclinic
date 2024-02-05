# Tagging discovery-server
docker tag discovery-server:1.1 ghergutmadalin/discovery-server:1.1

# Tagging api-gateway
docker tag api-gateway:1.1 ghergutmadalin/api-gateway:1.1

# Tagging animal-service
docker tag animal-service:1.1 ghergutmadalin/animal-service:1.1

# Tagging consultation-service
docker tag consultation-service:1.1 ghergutmadalin/consultation-service:1.1

# Tagging appointment-service
docker tag appointment-service:1.1 ghergutmadalin/appointment-service:1.1

# Tagging doctor-service
docker tag doctor-service:1.1 ghergutmadalin/doctor-service:1.1

# Tagging user-service
docker tag user-service:1.1 ghergutmadalin/user-service:1.1

docker tag adoption-service:1.1 ghergutmadalin/adoption-service:1.1

# Tagging front-end
docker tag 4bf4c2cb4878 ghergutmadalin/front-end:1.1

# Logging in to Docker Hub
docker login

# Pushing discovery-server
docker push ghergutmadalin/discovery-server:1.1

# Pushing api-gateway
docker push ghergutmadalin/api-gateway:1.1

# Pushing animal-service
docker push ghergutmadalin/animal-service:1.1

# Pushing consultation-service
docker push ghergutmadalin/consultation-service:1.1

# Pushing appointment-service
docker push ghergutmadalin/appointment-service:1.1

# Pushing doctor-service
docker push ghergutmadalin/doctor-service:1.1

# Pushing user-service
docker push ghergutmadalin/user-service:1.1

docker push ghergutmadalin/adoption-service:1.1

# Pushing front-end
docker push ghergutmadalin/front-end:1.1