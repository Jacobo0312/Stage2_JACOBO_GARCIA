# Login to Docker Hub to authenticate and push Docker images
docker login

# Build a Docker image with the specified tag (-t) and Dockerfile in the current directory
sudo docker build -t jacobo0312/front-stage2:latest .

# Tag the locally built image with the latest tag before pushing it to the repository
docker tag jacobo0312/front-stage2:latest jacobo0312/front-stage2:latest

# Push the locally built and tagged image to the Docker Hub repository
docker push jacobo0312/front-stage2:latest
