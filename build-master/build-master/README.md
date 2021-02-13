### 1. 사전 준비사항

모든 명령어는 반드시 `jenkins` 권한으로 실행이 되어야 함.

```sh
# sudo -u jenkins ...
```





### 2. Docker Build & Push

SSH 를 사용하여 해당 장비에 접속하여, 프로젝트 디렉토리(comm-datapf-api-converting)로 이동함.

```sh
# cd /webapp/baseimage/build/comm-datapf-api-converting/
# sudo -u jenkins make devclean devbuild devpush
```



### 3. Run Docker Image

#### 실행옵션

* **tag** : tag(20191021-01)는 현재 작업중인 tag를 입력함.
* **DOCKER_DEBUG** : 디버깅이 필요한 경우만 입력함.
* **PROFILE** : 개발(dev), 운영(prod)로 설정함.


##### 일반적인 실행 예제

```sh
# sudo -u jenkins PROFILE=dev /webapp/baseimage/build/comm-datapf-api-converting/comm-datapf-api-converting.sh 20191021-01 
```



##### 디버깅용 실행 예제

```sh
# sudo -u jenkins PROFILE=dev /webapp/baseimage/build/comm-datapf-api-converting/comm-datapf-api-converting.sh 20191021-01 DOCKER_DEBUG
```



### 4. Docker logs 확인

```sh
# docker logs --tail 1000 comm-datapf-api-converting
```



