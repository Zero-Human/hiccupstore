# HiccupStore Project

## 목차

  * [프로젝트 개요](#프로젝트-개요)
      - [ 기술 스택](#기술-스택)
      - [ DB-Modeling](#db-modeling)
  * [프로젝트 기능 구현내용](#구현-기능-관련)
  * [배포](#배포)

## 프로젝트 개요

<img src="https://user-images.githubusercontent.com/91558193/195794514-d6f2a6c3-ab52-45f0-9842-5cdd57f8a140.png">
우리나라 전통술을 판매하는 웹사이트를 구현하는 것을 목표로 하고 있습니다.<br>
SSR 방식으로 Thymeleaf로 구현하였습니다.<br>
개발 기간 : 2022.08.01 ~ 2022.10.1(추가 수정 중)<br>
개발 인원 : 4명<br>
주소: https://www.hiccupstore.com/
<br/>

## 기술 스택
- Framework: spring boot
- language: JAVA, Javascript
- DB : mysql, mybatis
- AWS : EC2, S3, RDS
- etc: spring security, Oauth2, Thymeleaf,HTML, CSS
<br/>

### DB Modeling

<br/>

![ERD 다이어그램](https://user-images.githubusercontent.com/91558193/195801462-799b3c7a-ce69-4b80-8ea1-ef778e05aab8.png)


## 구현 기능 관련
 - 유저 회원가입 및 로그인
 - 관리자만 상품 관리 기능 추가
 - 1대1 문의
 - 상품 문의
 - 리뷰 기능
 - 장바구니 기능
 - 유저 찜 기능
 - 상품 결제 기능(실제 결제 X)
### 기여한 내용
 - 장바구니 기능 구현
 - 찜목록 기능 구현
  - jquery를 이용해서 Front와 Back간 통신 진행
 - 상품 관리 기능 구현
 - 상품 QnA 및 상품 리뷰 기능 구현
 - AWS에 배포
  - EC2, S3, RDS를 이용하여 배포 진행
  - 로드 벨런서와 route53을 이용한 Https를 설정


## 배포
 - EC2: ubuntu로 배포
 - RDS: mysql 사용
 - S3: 이미지 저장소로 사용
 - 주소: https://www.hiccupstore.com/
