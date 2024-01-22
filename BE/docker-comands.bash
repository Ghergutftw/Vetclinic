# Tagging discovery-server
docker tag discovery-server:0.0.1-SNAPSHOT ghergutmadalin/discovery-server:0.0.1-SNAPSHOT

# Tagging api-gateway
docker tag api-gateway:0.0.1-SNAPSHOT ghergutmadalin/api-gateway:0.0.1-SNAPSHOT

# Tagging animal-service
docker tag animal-service:0.0.1-SNAPSHOT ghergutmadalin/animal-service:0.0.1-SNAPSHOT

# Tagging consultation-service
docker tag consultation-service:0.0.1-SNAPSHOT ghergutmadalin/consultation-service:0.0.1-SNAPSHOT

# Tagging appointment-service
docker tag appointment-service:0.0.1-SNAPSHOT ghergutmadalin/appointment-service:0.0.1-SNAPSHOT

# Tagging doctor-service
docker tag doctor-service:0.0.1-SNAPSHOT ghergutmadalin/doctor-service:0.0.1-SNAPSHOT

# Tagging user-service
docker tag user-service:0.0.1-SNAPSHOT ghergutmadalin/user-service:0.0.1-SNAPSHOT

# Tagging front-end
docker tag front-end:latest ghergutmadalin/front-end:0.0.1-SNAPSHOT

# Logging in to Docker Hub
docker login

# Pushing discovery-server
docker push ghergutmadalin/discovery-server:0.0.1-SNAPSHOT

# Pushing api-gateway
docker push ghergutmadalin/api-gateway:0.0.1-SNAPSHOT

# Pushing animal-service
docker push ghergutmadalin/animal-service:0.0.1-SNAPSHOT

# Pushing consultation-service
docker push ghergutmadalin/consultation-service:0.0.1-SNAPSHOT

# Pushing appointment-service
docker push ghergutmadalin/appointment-service:0.0.1-SNAPSHOT

# Pushing doctor-service
docker push ghergutmadalin/doctor-service:0.0.1-SNAPSHOT

# Pushing user-service
docker push ghergutmadalin/user-service:0.0.1-SNAPSHOT

# Pushing front-end
docker push ghergutmadalin/front-end:0.0.1-SNAPSHOT
