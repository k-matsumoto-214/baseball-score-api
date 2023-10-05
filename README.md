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
![stamen](https://github.com/k-matsumoto-214/baseball-score/assets/91876695/e47c8e3c-5f76-4d23-b689-029d43be2ae8)
![HR](https://github.com/k-matsumoto-214/baseball-score/assets/91876695/5fa1db78-3c3a-476f-b8a1-62f246b7f154)
![steal](https://github.com/k-matsumoto-214/baseball-score/assets/91876695/6249c40b-ea70-4f60-b1cc-839c498a8e93)
![score](https://github.com/k-matsumoto-214/baseball-score/assets/91876695/a651de65-9945-47a6-a7e3-05c2d34cc68c)

