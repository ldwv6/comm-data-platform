docker stop tour-air-api-nginx
docker rm tour-air-api-nginx


docker run -d \
--name tour-air-api-nginx \
--restart unless-stopped \
--network host \
--log-driver json-file \
--log-opt max-size=10m \
--log-opt max-file=7 \
-p 80:80/tcp \
-p 443:443/tcp \
-e TZ=Asia/Seoul \
-e LANG=ko_KR.UTF-8 \
-v /var/log/nginx:/var/log/nginx \
tour-air-api-nginx:dev
