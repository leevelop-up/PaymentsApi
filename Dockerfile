# 선택한 기본 이미지 (예: OpenJDK 11) 사용
FROM openjdk:11-jre-slim

# 작업 디렉토리 설정
WORKDIR /app

# 애플리케이션 코드 및 파일을 컨테이너로 복사
COPY . /app

# 필요한 패키지 또는 라이브러리 설치
RUN apt-get update && apt-get install -y gradle

# 애플리케이션 빌드 (예: Gradle 빌드)
RUN chmod +x ./gradlew
RUN ./gradlew build

# 컨테이너 내에서 사용할 포트 노출
EXPOSE 9090

# 컨테이너 실행 명령
CMD ["java", "-jar", "PaymentsApi.jar"]
