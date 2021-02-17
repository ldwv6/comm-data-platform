docker build --force-rm=false --no-cache=false --rm=true -t tour-air-api-nginx:latest .

docker tag tour-air-api-nginx:latest 121.254.135.234:5000/tour-air-api-nginx
docker push 121.254.135.234:5000/tour-air-api-nginx


