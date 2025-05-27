# ベースイメージをAlpineでなくDebianスリムにする（arm64対応）
FROM eclipse-temurin:17-jdk-jammy

# 作業ディレクトリ作成
WORKDIR /app

# jarファイルをコンテナにコピー
COPY target/portfolio-0.0.1-SNAPSHOT.jar app.jar

# ポート指定
EXPOSE 8080

# 実行コマンド
ENTRYPOINT ["java", "-jar", "app.jar"]
