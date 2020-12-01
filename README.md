## 서울 3기 3반 2조  : 김경재 , 서영우 , 윤지원 , 김우희 

# 👉 프로젝트 소개 

<div align=center><h1><img src = "https://lab.ssafy.com/s03-webmobile1-sub2/s03p12a302/uploads/63679883087144683bd8e380816ef481/youtube-video-social-media-play_icon-icons.com_59108__1_.png"><b>WETUBE</b></h1></div>

![위튜브](https://lab.ssafy.com/s03-webmobile1-sub3/s03p13a302/uploads/1e539509ce60316506bf87fcde6c6790/image.png)

## ✏ 사용언어 /기술스택

<div align=center><img src ="https://img.shields.io/badge/Platform-web-brightgreen">&nbsp;&nbsp;<img src="https://img.shields.io/badge/Framework-Spring / Vue.js-yellowgreen"> </div>

<div align=center><img src="https://img.shields.io/badge/Database-MariaDB / FireBase-blue">&nbsp;&nbsp;<img src="https://img.shields.io/badge/Server-AWS-orange">&nbsp;&nbsp;<img src="https://img.shields.io/badge/Language-Java / Javascript%20-lightgray"></div>



## 📢 페이지 /기능 소개 

![기능 소개 ](   https://lab.ssafy.com/s03-webmobile1-sub3/s03p13a302/uploads/8d06f68d8442342df71ea75e00c39cc7/image.png)



## 🛠 개발환경

-----------------------------------------

1. jdk 설치 (Zulu 8)

2. STS - 3.9.12 RELEASE  설치 [BACKEND]

   ​	Spring Boot Project - MAVEN   backend 폴더  import 

3. Docker + MariaDB [DATABASE]

   ​		1. Docker (https://docs.docker.com/docker-for-windows/install/) 설치

   ​		2. Maria DB 실행 

   ​				`docker run --name maria-db -p 3306:3306 -e MYSQL_ROOT_PASSWORD)={패스워드} -d mariadb `

   ​        3. Maria DB 접속 

   ​				`docker exec -it maria-db mysql -u root -p`

4. ORM - JPA  Repository 

5. vscode [FRONTEND]

   ``` bash
   cd frontend
   npm install
   yarn run serve
   ```

   



## ⚒ AWS 환경 설정

- OS: Ubuntu 18.04.1 LTS

- Web Server:   NGINX

  - NGINX 설치 및 배포 

  ```bash
  sudo apt-get install nginx    #EC2 접속 후 Nginx 설치 
  sudo service nginx status   #Nginx 잘 실행되었는지 확인 
  sudo -s # root 계정으로 변경 (root계정만 편집 가능 )
  sudo vim /etc/nginx/sites-available/default  
  		# root var/www/html/dist 로 변경 
  		# index index.html 로 변경 
  
  #프론트 엔드 폴더 위치에서 npm run build 후 dist 폴더 생성 
  #로컬에 있는 dist 폴더 aws 서버에 올리기 
  scp -i [pem file] -r [upload folder] [user id]@[ec2 public IP]:~/[transfer address]
  mv dist /var/www/html #dist 폴더 nginx root경로로 이동 
  # 서버 재 실행 
  ```

  - SSL 인증 

  ```bash
  sudo add-apt-repository ppa:certbot/certbot #certbot 최신버전을 받기위해 레포지토리 추가
  sudo apt install python-certbot-nginx # Cerbot Nginx 패키지 설치
  
  # /etc/nginx/sites-available/[해당되는 도메인]에서 Nginx 설정확인, 이번 경우 default 파일
  sudo nginx -t # 설정 테스트 -> 완료되면 이상없음
  sudo sysyem reload nginx # 테스트 통과 후 설정 업데이트를 위해 재부팅
  
  # ufw를 통해 방화벽을 설정해 주어야하지만 AWS의 경우 필요없으므로 스킵
  
  # SSL 인증서 가져오기
  $ sudo certbot --nginx -d example.com -d [www.example.com]
  # 설정 중 http 트래픽 리다이렉트 설정 옵션 선택 가능
  # 1번: http 리다이렉트 없음 ,  2번: http를 https로 리다이렉트(Nginx 자동설정)
  
  #SSL 인증서 갱신절차 테스트 (기본적으로 certbot이 자동적으로 인증서 갱신)
  $ sudo certbot renew --dry-run
  ```

  - 프록시 패스 설정
    - 아래와 같이 특정 경로(/api)로 들어오는 경우 서버ip(localhost:8080)으로 요청을 전달하도록 설정

![NGINX_프록시_패스](/uploads/24067fa8bbdc69b837fdbf27a284ba29/NGINX_프록시_패스.JPG)

  

- WAS: Tomcat8 (8.5.39)
  
  - 설치 및 배포
    
    ```bash
    sudo apt-get install tomcat8 # 접속 후에 Tomcat install 
    sudo /usr/share/tomcat8/bin/version.sh # 버전 확인으로 설치 여부 확인
    sudo ufw allow [톰캣 포트번호]/tcp # 톰캣 포트 설정
    sudo service tomcat8 start # 톰캣 실행 
    
    # 톰캣 경로 
    # /usr/share/tomcat8 -> 실행 & 라이브러리
    	# /var/lib/tomcat8/conf/server.xml : 69번째 줄에서 포트 변경 -> 재부팅 수행
    # /var/lib/tomcat8 -> conf & webapps
    
    # 이클립스에서 Export or STS에서 pom.xml에 <packaging>war</packaging>로 작성후 Run as -> Maven build 실행 후  scp를 이용하여 AWS에 업로드 
    mv [파일명].war /var/lib/tomcat8/webapps # webapps로 이동
    sudo service tomcat8 restart # 이동 후 서버 재기동 
    
    # 접속 방법
    http://[도메인주소]:[포트번호] # 톰캣 기본 화면, 동작확인 
    http://[도메인주소]:[포트번호]/[war 파일명] # war파일로 만든 프로젝트 root 경로
    http://[도메인주소]:[포트번호]/[war 파일명]/swagger-ui.html # war 파일의 스웨거 접속 
    ```
    
    
    
  - 내장 톰캣 사용(jar)
  
    ```bash
    # pom.xml에 <packaging>jar</packaging>로 작성후 Run as -> Maven build 하여 jar 생성
    # scp를 이용하여 파일 업로드
    scp -i [pem file]  [upload file] [user id]@[ec2 public IP]:~/[transfer address]
    
    # 실행, 백그라운드에서 실행 + nohup.out 파일에 로그 기록 
    nohup java -jar [파일명].jar &
    
    # 동작 확인(프로세스)
    ps ef | java or [파일명]
    
    # 정지 
    ps ef | java or [파일명] # PID 찾기
    kill [PID] # 프로세스 종료
    ```
  
    
  
  - Jar 파일의 내장 톰캣 사용  
  
    - 실행 방법: ssh로 접속 후 'nohup java -jar 파일명.jar '
    - 스웨거: http://i3a302.p.ssafy.io:8080/swagger-ui.html#/
  
    
  
- DB: Maria DB 설치  10.5 (참조: https://jimnong.tistory.com/744)

  ```bash
   sudo apt-get update 
   sudo apt-get upgrade
   sudo apt-get install mariadb-server
   sudo mysql_secure_installation
  ```

  

- 로컬에서 AWS로 파일 업로드 

  1.  서버에 접속

  2.  파일 업로드

     1.  scp -i [pem file] [upload file] [user id]@[ec2 public IP]:~/[transfer address] (파일 업로드)

        예) ``` scp -i /d/aws/I3A302T.pem /d/aws/webBlog-0.0.1-SNAPSHOT.jar buntu@i3a302.p.ssafy.io:~/```

     2.  scp -i [pem file] -r [upload folder] [user id]@[ec2 public IP]:~/[transfer address] (폴더 업로드)

        예) ```scp -i /d/aws/I3A302T.pem -r /d/aws/webBlog-0.0.1-SNAPSHOT.jar buntu@i3a302.p.ssafy.io:~/```



## 🔥Fire Base Storage 

#### 1. Firebase 프로젝트 생성 

- 아래 링크에서 구글 로그인 후 프로젝트 생성
- https://console.firebase.google.com/

#### 2. 프로젝트 overview에서 웹앱 추가

- 앱 닉네임 및 호스팅 설정(옵션) 지정 
- 그 후 페이지에 나오는 대로 수행

#### 3. Storage 생성

- Storage 보안규칙을 설정(allow read, write)
- 스토리지 위치를 asia-east2 로 선택

#### 4. VS code 에서 firebase 모듈 다운로드 

- sudo npm install -g firebase-tools  입력 
- 설치 후 firebase login  ->  구글 로그인으로 스토리지 사용 인증 수행 
- firebase init  ->  firebase 프로젝트 초기화

#### 5. 프론트엔드 프로젝트의 사용부분에 추가 

- import firebase로 모듈 추가 후 하위 모듈 사용 

#### 6. Storage 사용 

- Firebase.storage().ref() 로 storage의 레퍼런스를 가져옴 
- 레퍼런스.child("저장할 파일명") -> 레퍼런스가 가리키는 주소에 입력한 파일명으로 저장됨
- 파일 업로드 다운로드 
  - 파일 저장: ref().put(File객체 or blob).then().catch()
  - 파일 다운로드: ref("파일경로/파일명").getDownloadURL().then().catch() 
    - url을 가져와서 파일 다운로드 및 미리보기 가능

#### &#10071; 주의사항

- 배포시에 전체 모듈을 import 하지 말고 사용할 모듈을 개별적으로 import 하는것을 권장
  - import firebase ->  import firebase/app,  import firebase/storage



## 🔎favicon 및 브라우저 탭 제목 변경

- favicon
  1. 프로젝트 - public 경로의  favicon.ico변경 혹은 ico/png 파일을 넣기
  2. 동일경로의 index.html에서 favicon.ico의 경로 변경
- 브라우저 탭 제목 
  1. pakage.json 파일의 맨 윗부분의 name의 내용을 변경

####    **&#10071; 주의사항**

- 변경 후에 적용이 안될경우 브라우저의 캐시를 삭제 할것



## ⭐ 코딩 컨벤션 

#### 1. 폴더 규칙 

​	 ```1.  폴더 이름: 소문자로 작성 `ex ) com.web.blog.controller```

#### 2. 자바 파일

​	1.    각 기능에 관계된 단어 사용

​	2.    Class 파일: 파스칼 표기법  `ex) UserController `

​	3.    메서드: 카멜 표기법  `ex ) findByEmailAndPassword()`

​	4.    변수 명: 카멜 표기법  `ex) createDate`

​	5.    들여쓰기: tab (>>>>)

​	6.    `예외: JPA의 메서드 명은 JPA의 규칙에 따름`

#### 3.  DB

1.  테이블 명 : board_like
2.  칼럼 :  snake 표기법  `ex) create_date   ( jpa 에서 자동 변환 <->)`

#### 4. REST API 

 	1.      CRUD( POST,  GET,  PUT, DELETE) 
          1.      같은 범주의 기능의 경우 url주소 일치  ex) `/account/user (행위 포함 X)`
          2.       URL 마지막에 / 포함하지 않음 
          3.      소문자 사용 

#### 5. GIT  ⚡  

 	1. 브랜치 분리
      	1.  `Master – develop – feature/[기능명]`
	2. 커밋 메세지    `ex )  Edit login Function , SP01C12-1`
        	1. 영어만 사용하여 첫글자를 대문자로 하여 총 70자 이내
                  	2. JIRA 이슈 번호 마지막에 추가
                	3. 마침표 사용X, 명령어 방식으로 작성
	3. develop Merge 후 브랜치 삭제 



## 💾 파일 목록

```
📦s03p13a302
 ┣ 📂doc
 ┃ ┣ 📜A302_Database ERD.png
 ┃ ┣ 📜A302_REST API .xlsx
 ┃ ┣ 📜wetube.sql
 ┃ ┗ 📜A302_와이어프레임.pdf
 ┣ 📂image
 ┃ ┗ 📜ERD.png
 ┣ 📂WEB_PJT_BLOG
 ┃ ┣ 📂backend
 ┃ ┃ ┣ 📂src
 ┃ ┃ ┃ ┣ 📂main
 ┃ ┃ ┃ ┃ ┣ 📂java
 ┃ ┃ ┃ ┃ ┃ ┗ 📂com
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂web
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂blog
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂config
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜SwaggerConfig.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂account
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜AccountController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂board
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜BoardLikeController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜BoardListController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜BoardModifyController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂reply
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜ReplyController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂tag
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜TagController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dao
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂board
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜BoardLikeDao.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜BoardListDao.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜BoardModifyDao.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂reply
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜ReplyDao.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂tag
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜TagCountDao.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜TagDao.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂user
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜UserDao.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂board
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜Board.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜BoardCreateRequest.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜BoardDetail.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜BoardLike.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜BoardLikePK.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜BoardList.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜BoardModifyRequest.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂reply
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜Reply.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ReplyCreateRequest.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜ReplyModifyRequest.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂tag
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜Tag.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜TagCount.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜TagPK.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂user
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜AuthenticationRequest.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜SignupRequest.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜UpdateRequest.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜User.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜BasicResponse.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂board
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜BoardLikeService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜BoardLikeServiceImpl.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜BoardListService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜BoardListServiceImpl.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜BoardModifyService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜BoardModifyServiceImpl.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂reply
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ReplyService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜ReplyServiceImpl.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂tag
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜TagService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜TagServiceImpl.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂util
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜EmailService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜EmailServiceImpl.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜JwtInterceptor.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜JwtService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜WebBlogApplication.java
 ┃ ┃ ┃ ┃ ┗ 📂resources
 ┃ ┃ ┃ ┃ ┃ ┗ 📜application.properties
 ┃ ┃ ┣ 📜pom.xml
 ┃ ┃ ┗ 📜ssafy-sk.sql
 ┃ ┗ 📂frontend
 ┃ ┃ ┣ 📂config
 ┃ ┃ ┃ ┣ 📜dev.env.js
 ┃ ┃ ┃ ┣ 📜index.js
 ┃ ┃ ┃ ┣ 📜prod.env.js
 ┃ ┃ ┃ ┗ 📜test.env.js
 ┃ ┃ ┣ 📂public
 ┃ ┃ ┃ ┣ 📜favicon.ico
 ┃ ┃ ┃ ┗ 📜index.html
 ┃ ┃ ┣ 📂src
 ┃ ┃ ┃ ┣ 📂assets
 ┃ ┃ ┃ ┃ ┣ 📂css
 ┃ ┃ ┃ ┃ ┃ ┣ 📜board.css
 ┃ ┃ ┃ ┃ ┃ ┣ 📜common.css
 ┃ ┃ ┃ ┃ ┃ ┣ 📜common.css.map
 ┃ ┃ ┃ ┃ ┃ ┣ 📜common.scss
 ┃ ┃ ┃ ┃ ┃ ┣ 📜post.css
 ┃ ┃ ┃ ┃ ┃ ┣ 📜post.css.map
 ┃ ┃ ┃ ┃ ┃ ┣ 📜post.scss
 ┃ ┃ ┃ ┃ ┃ ┣ 📜style.css
 ┃ ┃ ┃ ┃ ┃ ┣ 📜style.css.map
 ┃ ┃ ┃ ┃ ┃ ┣ 📜style.scss
 ┃ ┃ ┃ ┃ ┃ ┣ 📜user.css
 ┃ ┃ ┃ ┃ ┃ ┣ 📜user.css.map
 ┃ ┃ ┃ ┃ ┃ ┗ 📜user.scss
 ┃ ┃ ┃ ┃ ┣ 📂img
 ┃ ┃ ┃ ┃ ┃ ┣ 📂category
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜cartoon.png
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜entertainment.png
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜fashion.png
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜food.png
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜game.png
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜health.png
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜humor.png
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜IT.png
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜love.png
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜military.png
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜movie.png
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜music.png
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜society.png
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜sport.png
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜study.png
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜vlog.png
 ┃ ┃ ┃ ┃ ┃ ┣ 📂icons
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜add_col_after.svg
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜add_col_before.svg
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜add_row_after.svg
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜add_row_before.svg
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜bold.svg
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜checklist.svg
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜code.svg
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜combine_cells.svg
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜delete_col.svg
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜delete_row.svg
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜delete_table.svg
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜github.svg
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜hr.svg
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜image.svg
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜italic.svg
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜link.svg
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜mention.svg
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ol.svg
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜paragraph.svg
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜quote.svg
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜redo.svg
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜remove.svg
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜strike.svg
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜table.svg
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ul.svg
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜underline.svg
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜undo.svg
 ┃ ┃ ┃ ┃ ┃ ┣ 📂toolbar
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜bold.svg
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜code.svg
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜h1.svg
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜h2.svg
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜h3.svg
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜h4.svg
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜image.svg
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜italic.svg
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜line.svg
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜link.svg
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜p.svg
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜quotation1.svg
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜quotation2.svg
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜strikethrough.svg
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜underline.svg
 ┃ ┃ ┃ ┃ ┃ ┣ 📜add.png
 ┃ ┃ ┃ ┃ ┃ ┣ 📜BasicProfileIcon.svg
 ┃ ┃ ┃ ┃ ┃ ┣ 📜boardWrite.png
 ┃ ┃ ┃ ┃ ┃ ┣ 📜categoryHeader.png
 ┃ ┃ ┃ ┃ ┃ ┣ 📜comment.png
 ┃ ┃ ┃ ┃ ┃ ┣ 📜delete.png
 ┃ ┃ ┃ ┃ ┃ ┣ 📜hashtag.png
 ┃ ┃ ┃ ┃ ┃ ┣ 📜heart.png
 ┃ ┃ ┃ ┃ ┃ ┣ 📜heart2.png
 ┃ ┃ ┃ ┃ ┃ ┣ 📜like_write.png
 ┃ ┃ ┃ ┃ ┃ ┣ 📜loadingCustom.gif
 ┃ ┃ ┃ ┃ ┃ ┣ 📜paper.png
 ┃ ┃ ┃ ┃ ┃ ┣ 📜search.png
 ┃ ┃ ┃ ┃ ┃ ┣ 📜tempHeader.png
 ┃ ┃ ┃ ┃ ┃ ┣ 📜up.png
 ┃ ┃ ┃ ┃ ┃ ┣ 📜user.png
 ┃ ┃ ┃ ┃ ┃ ┣ 📜wetubeBanner.png
 ┃ ┃ ┃ ┃ ┃ ┣ 📜writeHeader.jpg
 ┃ ┃ ┃ ┃ ┃ ┗ 📜zero.png
 ┃ ┃ ┃ ┃ ┣ 📂sass
 ┃ ┃ ┃ ┃ ┃ ┣ 📜editor.scss
 ┃ ┃ ┃ ┃ ┃ ┣ 📜main.scss
 ┃ ┃ ┃ ┃ ┃ ┣ 📜menubar.scss
 ┃ ┃ ┃ ┃ ┃ ┣ 📜menububble.scss
 ┃ ┃ ┃ ┃ ┃ ┗ 📜variables.scss
 ┃ ┃ ┃ ┃ ┗ 📜logo.png
 ┃ ┃ ┃ ┣ 📂components
 ┃ ┃ ┃ ┃ ┣ 📂common
 ┃ ┃ ┃ ┃ ┃ ┣ 📜BoardListTable.vue
 ┃ ┃ ┃ ┃ ┃ ┣ 📜Card.vue
 ┃ ┃ ┃ ┃ ┃ ┣ 📜Header.vue
 ┃ ┃ ┃ ┃ ┃ ┣ 📜SearchBar.vue
 ┃ ┃ ┃ ┃ ┃ ┣ 📜TagList.vue
 ┃ ┃ ┃ ┃ ┃ ┣ 📜TagsInput.vue
 ┃ ┃ ┃ ┃ ┃ ┗ 📜userModal.vue
 ┃ ┃ ┃ ┃ ┗ 📜HelloWorld.vue
 ┃ ┃ ┃ ┣ 📂lib
 ┃ ┃ ┃ ┃ ┣ 📜constants.js
 ┃ ┃ ┃ ┃ ┗ 📜FireBaseConfig.js
 ┃ ┃ ┃ ┣ 📂page
 ┃ ┃ ┃ ┃ ┣ 📂error
 ┃ ┃ ┃ ┃ ┃ ┣ 📜Error.vue
 ┃ ┃ ┃ ┃ ┃ ┗ 📜PageNotFound.vue
 ┃ ┃ ┃ ┃ ┣ 📂post
 ┃ ┃ ┃ ┃ ┃ ┣ 📂Icon
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜index.vue
 ┃ ┃ ┃ ┃ ┃ ┣ 📜BoardCreate.vue
 ┃ ┃ ┃ ┃ ┃ ┣ 📜BoardDetail.vue
 ┃ ┃ ┃ ┃ ┃ ┣ 📜Category.vue
 ┃ ┃ ┃ ┃ ┃ ┣ 📜comment.vue
 ┃ ┃ ┃ ┃ ┃ ┣ 📜Iframe.js
 ┃ ┃ ┃ ┃ ┃ ┣ 📜List.vue
 ┃ ┃ ┃ ┃ ┃ ┣ 📜Main.vue
 ┃ ┃ ┃ ┃ ┃ ┣ 📜Modal.vue
 ┃ ┃ ┃ ┃ ┃ ┗ 📜TempBoardList.vue
 ┃ ┃ ┃ ┃ ┗ 📂user
 ┃ ┃ ┃ ┃ ┃ ┣ 📜FindPw.vue
 ┃ ┃ ┃ ┃ ┃ ┣ 📜Information.vue
 ┃ ┃ ┃ ┃ ┃ ┣ 📜Join.vue
 ┃ ┃ ┃ ┃ ┃ ┗ 📜Login.vue
 ┃ ┃ ┃ ┣ 📂router
 ┃ ┃ ┃ ┃ ┗ 📜index.js
 ┃ ┃ ┃ ┣ 📂store
 ┃ ┃ ┃ ┃ ┗ 📜index.js
 ┃ ┃ ┃ ┣ 📜App.vue
 ┃ ┃ ┃ ┗ 📜main.js
 ┃ ┃ ┣ 📜.eslintignore
 ┃ ┃ ┣ 📜.eslintrc.js
 ┃ ┃ ┣ 📜.gitignore
 ┃ ┃ ┣ 📜babel.config.js
 ┃ ┃ ┣ 📜cors.json
 ┃ ┃ ┣ 📜index.html
 ┃ ┃ ┣ 📜package.json
 ┃ ┃ ┣ 📜README.md
 ┃ ┃ ┣ 📜test.html
 ┃ ┃ ┣ 📜vue.config.js
 ┃ ┃ ┗ 📜yarn.lock
 ┣ 📜.gitignore
 ┣ 📜package-lock.json
 ┗ 📜README.md
                         
```



