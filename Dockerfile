# 公式のJava 17環境を使う
FROM eclipse-temurin:17-jdk-alpine

# アプリを置く作業フォルダを作成
WORKDIR /app

# Mavenで作ったjarファイルをコピー
COPY target/portfolio-0.0.1-SNAPSHOT.jar app.jar

# Spring Bootのデフォルトポートを開ける
EXPOSE 8080

# アプリを起動するコマンド
ENTRYPOINT ["java", "-jar", "app.jar"]
