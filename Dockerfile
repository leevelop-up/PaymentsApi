# 선택한 기본 이미지 (OpenJDK 11) 사용
FROM openjdk:11-jre-slim

# 작업 디렉토리 설정
WORKDIR /app

# 애플리케이션 코드 및 파일을 컨테이너로 복사
COPY . /app

# JDK 설치 및 환경 변수 설정
RUN apt-get update && apt-get install -y openjdk-11-jdk
ENV JAVA_HOME /usr/lib/jvm/java-11-openjdk-amd64
ENV PATH $PATH:$JAVA_HOME/bin

# 필요한 패키지 또는 라이브러리 설치 (Gradle은 이미지 내에서 설치되어 있다고 가정)
# RUN apt-get update && apt-get install -y gradle

# 애플리케이션 빌드 (예: Gradle 빌드)
RUN chmod +x ./gradlew
RUN ./gradlew build

# 컨테이너 내에서 사용할 포트 노출
EXPOSE 9090

# 컨테이너 실행 명령
CMD ["java", "-jar", "build/libs/PaymentsApi.jar"]
