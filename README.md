# 野球スコア記録WEBアプリ(https://baseball.keismats.com/)
テストアカウント（下記は入力済みです。そのままログインボタンを押下すればログイン可能です。）
- accountId: testtest  
- password: password

### 目的
- 趣味の草野球の試合結果・打席結果を記録できるサービスが欲しかった
  - 単なるスコア・ヒット数などの記録だけでは満足できなかった
  - 同様のサービスもあったが操作が複雑・カウントの記録が詳細すぎるという不満があった
  - 試合の流れを速報形式で振り返られるようにしたかった（[スポナビさんの一球速報](https://baseball.yahoo.co.jp/npb/game/2021019604/text)のように）
  
### 使用技術  
- API
  - Java
  - SpringBoot
- [フロント](https://github.com/k-matsumoto-214/baseball-score)
  - Vue.js
  - Nuxt.js
  - Vuetify
- DB
  - MySQL

### できること
- チームの新規登録・ログイン
  - バックエンドでJWTを発行してのログイン管理 
- 打席の結果の記録
  - 打席結果によって進塁したランナー・得点などを記録
  - 打席中に起こった盗塁・暴投・捕逸などを記録
  - 打席結果の打球方向を記録
- 選手交代の記録
  - 代打・代走の記録
  - 守備交代・守備位置変更の記録
- 試合結果・内容の閲覧
  - 試合中にも随時速報形式で閲覧可能
  - 打席前後のランナー進塁等も閲覧可能 

### 動作イメージ
![login](https://github.com/k-matsumoto-214/baseball-score/assets/91876695/b9d51b1f-d770-4a29-a2e6-f96707b45a6f)
![3BHIT](https://github.com/k-matsumoto-214/baseball-score/assets/91876695/87c52ed5-84af-44ba-a0dd-1d022e08e54e)
![steal](https://github.com/k-matsumoto-214/baseball-score/assets/91876695/b3f6a267-09c1-4022-993b-b9b445357758)
![game-score](https://github.com/k-matsumoto-214/baseball-score/assets/91876695/0698e082-5ae0-450c-b895-f9bc2d789121)
![stamen](https://github.com/k-matsumoto-214/baseball-score-api/assets/91876695/92ca0dec-d01e-4147-89f2-f6fbf1e06595)

