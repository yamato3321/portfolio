# TODO 管理アプリ

このアプリは Spring Boot を用いて開発した、ログイン・カテゴリ管理・検索・ソート・ページネーションのタスク管理アプリです。未経験からエンジニアを目指す中で、学習の成果として開発しました。

## 🔧 使用技術

- Java 17
- Spring Boot 3
- Spring Security
- Spring Data JPA
- Thymeleaf
- postgreDB
- CSS（カスタム）
- GitHub Actions（CI/CD 準備中）

## 🎯 主な機能

- ユーザー登録・ログイン機能
- タスクの CRUD（作成・更新・削除）
- カテゴリごとの色分けと絞り込み
- 締切が近いタスクにバッジ表示
- タスクのソート（タイトル・期限・作成日）
- ページネーション（1 ページ 5 件）
- バリデーション（サーバー & クライアント）
- プロフィール編集、パスワード変更機能
- 共通カテゴリとユーザー専用カテゴリの区別
- 例外ページ（403/404）

## 🔐 認証とセキュリティ

- Spring Security によるログイン管理
- 自動ログイン機能（RememberMe）
- CSRF トークンの適用
- アクセス制御（未認証時のリダイレクト）

## 📁 フォルダ構成（簡略）

```
src
├── main
│   ├── java/com/example/portfolio
│   │   ├── controller
│   │   ├── entity
│   │   ├── repository
│   │   ├── service
│   │   └── config
│   └── resources
│       ├── templates
│       └── static/css
└── test
    └── java/com/example/portfolio
```

## 🚀 起動方法
- URL：https://portfolio-yjxu.onrender.com
- 
- ## 🧪 ログイン情報（デモ用）
- メールアドレス：`guest@email`
- パスワード：`11111111`

※ 初回読み込みに数秒かかる場合があります

## 📌 補足
- テストコードは今後追加予定です
