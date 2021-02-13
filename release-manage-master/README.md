# 배포 번호 관리


## 배포 번호 생성

#### 정규 배포 
```
$ bin/relno release
```

#### 긴급 반영 
```
$ bin/relno new "QA 지원을 위한 긴급반영 #3"
RELNO: 20181014-05
COMMENT: QA 지원을 위한 긴급반영 #3
[master 0805a47] QA 지원을 위한 긴급반영 #3
 2 files changed, 2 insertions(+), 2 deletions(-)
Counting objects: 9, done.
Delta compression using up to 32 threads.
Compressing objects: 100% (4/4), done.
Writing objects: 100% (5/5), 543 bytes | 0 bytes/s, done.
Total 5 (delta 0), reused 0 (delta 0)
To git@git.interparktour.com:rplatformcm/release-manage.git
   282223d..0805a47  master -> master
20181014-05
$
```

새로 생성된 배포번호를 바로 사용

```
relno=`relno new "무슨무슨 장애로 인한 긴급반영" 2>/dev/null`
```

## 번호 생성 룰

1. 정규반영 (relno.sh release)
``` 날짜-00 ```

2. 긴급반영 (relno.sh new "배포 사유 #1")
```날짜-숫자(0~9) ```

## 현재 배포 번호 조회
```
relno get
relno json

relno=`relno get`

relno=`curl "http://git.interparktour.com/rplatformcm/release-manage/raw/master/data/relno.txt" 2>/dev/null`
```

