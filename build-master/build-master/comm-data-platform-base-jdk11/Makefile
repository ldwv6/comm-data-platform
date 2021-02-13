REPO	= 121.254.135.234:5000
NAME	= comm-data-platform-bootapp-base-jdk11
PARENT  = comm-data-platform
TAG	= $(NAME):latest
LOCAL_IMAGE_PATH = $(NAME):latest
REPO_IMAGE_PATH = $(REPO)/$(PARENT)/base/$(NAME):latest


.PHONY: build test clean push

build:
	if docker images --format="{{.Repository}}:{{.Tag}}" | fgrep -q '^$(TAG)' >/dev/null 2>&1; then \
	  echo "$(TAG): already exists"; \
	else \
	  docker build -t $(TAG) .; \
	fi; \
	docker images | fgrep $(NAME)

test: build
	docker run -it --rm --name $(NAME) --hostname "TEST-$(NAME)" $(TAG) sh

clean:
	if docker images --format="{{.Repository}}:{{.Tag}}" | grep -q '^$(LOCAL_IMAGE_PATH)' >/dev/null 2>&1; then \
		docker rmi $(LOCAL_IMAGE_PATH);  \
	else \
	    echo "no such image : $(LOCAL_IMAGE_PATH)"; \
	fi;

push:
	docker tag $(TAG) $(REPO_IMAGE_PATH) && \
    docker push $(REPO_IMAGE_PATH)

