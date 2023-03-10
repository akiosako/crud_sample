### メモアプリの作成

### ■概要

昔の駅にあったような「伝言板」のようなアプリケーションを作成する。

・全員管理者である（自由に記入出来、自由に削除することが出来る）

### ■API使用書

#### GET

・findAll()...全件検索http://localhost:8080/msg

・findById()...id検索http://localhost:8080/msg/{id}

#### POST

・creatMsg()...メッセージの新規登録http://localhost:8080/msg

#### PATCH

・updateForm()...メッセージの更新http://localhost:8080/msg/{id}

#### DELETE

・deleteMsg()...メッセージの削除http://localhost:8080/msg/{id}

### ■例外処理およびバリデーションの実装

GET  
・findByIdにおいて、指定されたidが見つからない場合はステータスコード404を返す。

POST  
・30字以上のメッセージ、及びnullの投稿は制限されている。  
・リクエストされた場合はステータスコード400とメッセージを返す。

PATCH  
・updateForm()において、指定されたidが見つからない場合は404を返す。
・30字以上のメッセージの更新は制限されている。
・30字以上のメッセージで投稿された場合はステータスコード400を返す。

DELETE  
・deleteMsg()において、指定されたidが見つからない場合は404を返す。

### ■POSTMANによる実行画面キャプチャ

### GET

findAll()  
![img.png](img.png)

findById()...idを25に指定し、検索する
![img_1.png](img_1.png)

findById():存在しないidを入力し、検索する
![img_2.png](img_2.png)

### POST

createMsg()...メッセージを入力する→「message successfully created」と表示、201を返す
![img_3.png](img_3.png)
header...Locationに自動採番されたidを取得する
![img_4.png](img_4.png)
文字数オーバーの時...@Sizeバリデーションが機能し、400を返す
![img_5.png](img_5.png)
nullの時...@Not NUllが機能し、400を返す
![img_6.png](img_6.png)

### PATCH

updateForm()実行前...id=26の「おはよう」を「おやすみ」に変更する  
![img_7.png](img_7.png)  
上記実行結果...findById()でid=26を指定し、更新されていることを確認  
![img_8.png](img_8.png)  
存在しないidを指定した場合、404を返す  
![img_9.png](img_9.png)

### DELETE

deleteMsg()実行前...id=25を指定し、message:「hello」を削除する  
![img_10.png](img_10.png)  
上記実行後...findById()でid=25を指定し、確認。
![img_11.png](img_11.png)
指定したidは存在しないため404を返す(成功)
![img_12.png](img_12.png)

