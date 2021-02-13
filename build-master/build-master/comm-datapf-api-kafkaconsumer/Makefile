REPO	= 121.254.135.234:5000
NAME	= comm-datapf-api-kafkaconsumer
BOOTAPP			= $(NAME).jar
RELNO			=
PARENT			= comm-data-platform
PROD_PROFILE	= prod
DEV_PROFILE		= dev
SERVERPORT		=
PROD_SCOUTER_IP = 211.233.74.136
DEV_SCOUTER_IP  = 180.70.96.105
GITPROD			= /webapp/prod/release-manage

.PHONY: build test clean push

TOKEN = dUTfGr4ygsQ2TJyNKnsL
PROD_RELNO_TXT_URL   = http://gitprod.interparktour.com/comm-data-platform/release-manage/raw/master/data/relno.txt
PROD_RELNO_JSON_URL  = http://gitprod.interparktour.com/comm-data-platform/release-manage/raw/master/data/relno.json

DEV_RELNO_TXT_URL    = http://gitprod.interparktour.com/comm-data-platform/release-manage/raw/dev/data/relno.txt
DEV_RELNO_JSON_URL    = http://gitprod.interparktour.com/comm-data-platform/release-manage/raw/dev/data/relno.json

build:
	if [ -z "$(RELNO)" ]; then \
		relno=`curl -H 'PRIVATE-TOKEN: $(TOKEN)' "$(PROD_RELNO_TXT_URL)" 2>/dev/null`; \
	else \
	  relno=$(RELNO); \
	fi; \
	if docker images --format="{{.Repository}}:{{.Tag}}" | grep -q '^$(PARENT)/$(PROD_PROFILE)/$(NAME):$${relno}$$' >/dev/null 2>&1; then \
	  echo "$(NAME):$${relno} is already exists"; \
	else \
	  docker build --no-cache \
	    -t $(PARENT)/$(PROD_PROFILE)/$(NAME):$${relno} \
	    --build-arg bootapp=$(NAME).jar \
	    --build-arg relno=$${relno} \
	    --build-arg profile=$(PROFILE) \
	    --build-arg serverport=$(SERVERPORT) \
	    --build-arg scouter_ip=$(PROD_SCOUTER_IP) \
	    .; \
	fi; \
	docker images | fgrep $(PARENT)/$(PROD_PROFILE)/$(NAME)

test: build
	if [ -z "$(RELNO)" ]; then \
		relno=`curl -H 'PRIVATE-TOKEN: $(TOKEN)' "$(PROD_RELNO_TXT_URL)" 2>/dev/null`; \
	else \
	  relno=$(RELNO); \
	fi; \
	docker run -it \
	  --rm \
	  --name "TEST-$(NAME)" \
	  --hostname "TEST-$(NAME)" \
	  $(PARENT)/$(PROD_PROFILE)/$(NAME):$${relno} \
	  /bin/bash

clean:
	if [ -z "$(RELNO)" ]; then \
		relno=`curl -H 'PRIVATE-TOKEN: $(TOKEN)' "$(PROD_RELNO_TXT_URL)" 2>/dev/null`; \
	else \
	  relno=$(RELNO); \
	fi; \
	if docker images --format="{{.Repository}}:{{.Tag}}" | grep -q '^$(PARENT)/$(PROD_PROFILE)/$(NAME):$${relno}$$' >/dev/null 2>&1; then \
		docker rmi $(PARENT)/$(PROD_PROFILE)/$(NAME):$${relno}; \
	fi; \
	if docker images --format="{{.Repository}}:{{.Tag}}" | grep -q '^$(PARENT)/$(PROD_PROFILE)/$(NAME):latest' >/dev/null 2>&1; then \
		docker rmi $(PARENT)/$(PROD_PROFILE)/$(NAME):latest; \
	fi; \
	if docker images --format="{{.Repository}}:{{.Tag}}" | grep -q '^$(REPO)/$(PARENT)/$(PROD_PROFILE)/$(NAME):$${relno}$$' >/dev/null 2>&1; then \
		docker rmi $(REPO)/$(PARENT)/$(PROD_PROFILE)/$(NAME):$${relno}; \
	fi; \
	if docker images --format="{{.Repository}}:{{.Tag}}" | grep -q '^$(REPO)/$(PARENT)/$(PROD_PROFILE)/$(NAME):latest' >/dev/null 2>&1; then \
		docker rmi $(REPO)/$(PARENT)/$(PROD_PROFILE)/$(NAME):latest; \
	fi; \

push:
	if [ -z "$(RELNO)" ]; then \
		relno=`curl -H 'PRIVATE-TOKEN: $(TOKEN)' "$(PROD_RELNO_TXT_URL)" 2>/dev/null`; \
	else \
	  relno=$(RELNO); \
	fi; \
	docker tag $(PARENT)/$(PROD_PROFILE)/$(NAME):$${relno} $(REPO)/$(PARENT)/$(PROD_PROFILE)/$(NAME):$${relno} && \
	docker tag $(PARENT)/$(PROD_PROFILE)/$(NAME):$${relno} $(REPO)/$(PARENT)/$(PROD_PROFILE)/$(NAME):latest && \
	docker push $(REPO)/$(PARENT)/$(PROD_PROFILE)/$(NAME):$${relno} && \
	docker push $(REPO)/$(PARENT)/$(PROD_PROFILE)/$(NAME):latest 

devbuild:
	if [ -z "$(RELNO)" ]; then \
		relno=`curl -H 'PRIVATE-TOKEN: $(TOKEN)' "$(DEV_RELNO_TXT_URL)" 2>/dev/null`; \
	else \
	  relno=$(RELNO); \
	fi; \
	  docker build --no-cache \
	    -t $(PARENT)/$(DEV_PROFILE)/$(NAME):$${relno} \
	    --build-arg bootapp=$(NAME).jar \
	    --build-arg relno=$${relno} \
	    --build-arg profile=$(DEV_PROFILE) \
	    --build-arg serverport=$(SERVERPORT) \
	    --build-arg scouter_ip=$(DEV_SCOUTER_IP) \
          .; \
	docker images | fgrep $(PARENT)/$(DEV_PROFILE)/$(NAME)

devtest: build
	if [ -z "$(RELNO)" ]; then \
		relno=`curl -H 'PRIVATE-TOKEN: $(TOKEN)' "$(DEV_RELNO_TXT_URL)" 2>/dev/null`; \
	else \
	  relno=$(RELNO); \
	fi; \
	docker run -it \
	  --rm \
	  --name "TEST-$(NAME)" \
	  --hostname "TEST-$(NAME)" \
	  -e PROFILE="dev" \
	  -e SPRINGBOOT_OPT="-Dspring.profiles.active=dev" \
	  -v /var/log/comm-datapf:/var/log/comm-datapf \
	  -v /usr/local/comm-datapf-sys-cloudconfig/gc:/webapp/gc \
	  $(PARENT)/$(DEV_PROFILE)/$(NAME):$${relno} \
	  /bin/bash

devclean:
	if [ -z "$(RELNO)" ]; then \
		relno=`curl -H 'PRIVATE-TOKEN: $(TOKEN)' "$(DEV_RELNO_TXT_URL)" 2>/dev/null`; \
	else \
	  relno=$(RELNO); \
	fi; \
	if docker images --format="{{.Repository}}:{{.Tag}}" | grep -q '^$(PARENT)/$(DEV_PROFILE)/$(NAME):$${relno}$$' >/dev/null 2>&1; then \
		docker rmi $(PARENT)/$(DEV_PROFILE)/$(NAME):$${relno}; \
	fi; \
	if docker images --format="{{.Repository}}:{{.Tag}}" | grep -q '^$(PARENT)/$(DEV_PROFILE)/$(NAME):latest' >/dev/null 2>&1; then \
		docker rmi $(PARENT)/$(DEV_PROFILE)/$(NAME):latest; \
	fi; \
	if docker images --format="{{.Repository}}:{{.Tag}}" | grep -q '^$(REPO)/$(PARENT)/$(DEV_PROFILE)/$(NAME):$${relno}$$' >/dev/null 2>&1; then \
		docker rmi $(REPO)/$(PARENT)/$(DEV_PROFILE)/$(NAME):$${relno}; \
	fi; \
	if docker images --format="{{.Repository}}:{{.Tag}}" | grep -q '^$(REPO)/$(PARENT)/$(DEV_PROFILE)/$(NAME):latest' >/dev/null 2>&1; then \
		docker rmi $(REPO)/$(PARENT)/$(DEV_PROFILE)/$(NAME):latest; \
	fi;

devpush:
	if [ -z "$(RELNO)" ]; then \
		relno=`curl -H 'PRIVATE-TOKEN: $(TOKEN)' "$(DEV_RELNO_TXT_URL)" 2>/dev/null`; \
	else \
	  relno=$(RELNO); \
	fi; \
	docker tag $(PARENT)/$(DEV_PROFILE)/$(NAME):$${relno} $(REPO)/$(PARENT)/$(DEV_PROFILE)/$(NAME):$${relno} && \
	docker push $(REPO)/$(PARENT)/$(DEV_PROFILE)/$(NAME):$${relno}

