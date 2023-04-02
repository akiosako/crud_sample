### メモアプリの作成

### ■概要

・20字以下のメッセージの読み書き、更新、削除が可能  
・全員管理者である（自由に記入出来、自由に削除することが出来る）

### ■API仕様書

#### GET

・findAll()  
全件検索  
http://localhost:8080/msg

・findById()  
idで検索  
http://localhost:8080/msg/{id}

#### POST

・creatMsg()  
メッセージの新規登録  
http://localhost:8080/msg

#### PATCH

・updateForm()  
id指定によるメッセージの更新  
http://localhost:8080/msg/{id}

#### DELETE

・deleteMsg()  
id指定によるメッセージの削除  
http://localhost:8080/msg/{id}

### ■システムの仕様

【GET】  
・findAll()  
①登録された全てのメッセージとHTTPステータスコード200を返す。  
②データベースにメッセージの登録がない場合には、空の配列とHTTPステータスコード200を返す。

・findById()  
①指定されたidが存在する場合は指定されたidのメッセージとHTTPステータスコード200返す。  
②指定されたidが存在しない場合はエラーメッセージとHTTPステータスコード404を返す。

【POST】  
・createMsg()  
--修正中--

【PATCH】  
・updateMsg()  
--修正中--

【DELETE】  
・deleteMsg()  
①指定されたidが存在している場合は、指定されたidのメッセージを削除し、204を返す。  
②指定されたidが存在しない場合は、エラーメッセージとHTTPステータスコード404を返す。
