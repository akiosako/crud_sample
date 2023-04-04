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

・createMsg()  
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
①指定されたidが存在する場合は指定されたidとメッセージ、HTTPステータスコード200返す。  
②指定されたidが存在しない場合はエラーメッセージとHTTPステータスコード404を返す。

【POST】  
・createMsg()  
①1~20文字のメッセージを入力すると、自動採番されたidと登録されたメッセージ、HTTPステータスコード201を返す。  
②21文字以上のメッセージ、null、空文字を入力するとエラーメッセージとHTTPステータスコード400を返す。

【PATCH】  
・updateMsg()  
①指定されたidが存在する場合は、入力したメッセージで更新する。HTTPステータスコード204を返す。  
②指定されたidが存在しない場合はエラーメッセージとHTTPステータスコード404を返す。  
③メッセージの入力は1~20文字まで可能。null及び空文字を入力すると、HTTPステータスコード400を返す。

【DELETE】  
・deleteMsg()  
①指定されたidが存在している場合は、指定されたidのメッセージを削除し、204を返す。  
②指定されたidが存在しない場合は、エラーメッセージとHTTPステータスコード404を返す。
