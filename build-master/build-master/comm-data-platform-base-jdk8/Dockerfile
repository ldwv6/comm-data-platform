FROM 121.254.135.234:5000/centos7-java8:1.8.0_181
MAINTAINER yusuji@interpark.com

RUN mkdir -p /webapp/logs ; \
    mkdir -p /webapp/gc ; \
    mkdir -p /webapp/dev_config
ADD . /
RUN (cd /etc; \
     for f in passwd group shadow gshadow; do \
       cat $f.in >>$f && \
       rm -f $f.in; \
     done);  \
    chown -R ec:ec /webapp; \
    chown -R root:root /webapp/Damo /webapp/Damo_dev /webapp/Damo_stag; \
    chown -R atom:atom /webapp/Damo/log /webapp/Damo_dev/log /webapp/Damo_stag/log /webapp/config/scouter.conf /webapp/config  /webapp/logs /var/log/shop; \
    chmod 755 /usr/local/bin/bootstrap.sh; \
    chmod -R 777 /webapp/config
      
# Application Owner : ec (uid=300)
# Anonymous User : atom  (gid=500)
#USER 500:500
USER atom
WORKDIR /webapp

ENV LD_LIBRARY_PATH=/webapp/Damo
ENV LANG=ko_KR.UTF-8

VOLUME [ "/webapp/logs" ]
ENTRYPOINT [ "/usr/local/bin/bootstrap.sh" ] 
CMD ["start"]
