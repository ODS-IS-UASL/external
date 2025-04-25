# ドローン航路システム MQTTSブローカー構築手順書

## 概要

ドローン航路システム内の各コンテナ間通信で使用されるMQTTSメッセージブローカーを構築する。

### 手順概要

ActiveMQオフィシャルDockerイメージを使用してMQTTSブローカーコンテナを構築する。

### 手順実施後のブローカー状態

#### ActiveMQバージョン

Classic 5.18.4

#### 対応プロトコル

| プロトコル | ポート番号 | 公式イメージとの差異 |
| --- | --- | --- |
| openwire | 61616 | default |
| AMQP | 5672 | default |
| STOMP | 61613 | default |
| MQTT | 1883 | default |
| MQTTS | 8883 | 本手順により追加 |
| WS | 61614 | default |
| WSS | 61619 | 本手順により追加 |
| HTTP(管理画面) | 8161 | default |

#### ブローカーコンテナの接続情報

- **URI**: `ssl://xxx.{domain}:8883`
  ※ 本手順ではDockerHostのドメイン名を `xxx.{domain}` と仮定しています
  ※ 別途DNS取得などを行う際はサーバ証明書関連の設定を環境に合わせて実施ください

#### MQTTS接続時の認証情報

- **BASIC認証**: `admin/admin`
  ※ オフィシャルイメージデフォルト値
  ※ SSLクライアント認証: なし

## 手順詳細

### 1. Docker環境を用意

手順実施時はバージョン：25.0.5を使用

### 2. ActiveMQ-Classic 5.18.4のオフィシャルイメージをpull

```sh
$ docker pull apache/activemq-classic:5.18.4
$ docker images
```

※`docker images`による表示例
```sh
REPOSITORY                TAG       IMAGE ID       CREATED        SIZE
apache/activemq-classic   5.18.4    5f8992e89b91   3 months ago   370MB
```

### 3. MQTTS対応

#### 3-0. オフィシャルイメージを用いて起動

```sh
$ docker run --detach --name ini-container -p 61616:61616 -p 8161:8161 apache/activemq-classic:5.18.4
$ docker ps -a
```

#### 3-1. 設定ファイルをコンテナから取得、設定変更（mqtt+ssl）

##### 1. **コンテナ内の設定ファイルパスを確認**
```sh
$ docker exec -it ini-container /bin/bash
root@xx:/# ls -l /opt/apache-activemq/conf
root@xx:/# exit
```
※設定変更対象のファイル
```plaintext
=== 以下3ファイルを確認 ===
/opt/apache-activemq/conf/activemq.xml
/opt/apache-activemq/conf/broker.ks
/opt/apache-activemq/conf/broker.ts
======================
```

##### 2. **設定ファイルをコピー**
```sh
$ docker cp ini-container:/opt/apache-activemq/conf/activemq.xml .
```

##### 3. **設定ファイル(activemq.xml)を修正**

```xml
<!-- <transportConnectors>配下を以下のように設定 -->
<transportConnectors>
    <transportConnector name="openwire" uri="tcp://0.0.0.0:61616?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
    <transportConnector name="amqp" uri="amqp://0.0.0.0:5672?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
    <transportConnector name="stomp" uri="stomp://0.0.0.0:61613?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
    <!-- maxInactivityDurationを追加-->
    <transportConnector name="mqtt" uri="mqtt://0.0.0.0:1883?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600&amp;wireFormat.maxInactivityDuration=90000"/>
    <!-- MQTTSの設定を追加 -->
    <transportConnector name="mqtts" uri="mqtt+ssl://0.0.0.0:8883?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600&amp;transport.needClientAuth=false&amp;transport.verifyHostName=false&amp;wireFormat.maxInactivityDuration=90000"/>
    <!-- allowLinkStealingを追加 -->
    <transportConnector name="websocket" uri="ws://0.0.0.0:61614?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600&amp;allowLinkStealing=true&amp;wireFormat.maxInactivityDuration=90000"/>
    <!-- MQTT over WebSocket SSL/TLSを追加 -->
    <transportConnector name="secure_websocket" uri="wss://0.0.0.0:61619?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600&amp;allowLinkStealing=true&amp;wireFormat.maxInactivityDuration=90000"/>
</transportConnectors>
```
- `maxInactivityDuration`：アクティビティのない接続が停止状態と見なされる最大時間
- `transport.needClientAuth=false`：クライアント認証を無効化
- `transport.verifyHostName=false`：ホスト名検証を無効化
- `allowLinkStealing=true`：同一クライアントID接続時は後からの接続を有効とする

#### 3-2. サーバ証明書を含めたキーストアを配置

- `openssl` コマンドで作成⇒`keytool` コマンドで中間認証局（CA）などを追加
- ドメイン名アクセスが必須となるため、ホスト側のドメイン名でサーバ証明書を用意

##### 1. **ブローカーを稼働させるドメイン名を確定**
- Dockerホストのドメイン（`"xxx.{domain}"`）を用いる想定として記載

##### 2. **`openssl` 設定（`my_openssl.cnf`を作成）**
以下の内容で`my_openssl.cnf`を作成
```plaintext
[ req ]
distinguished_name = req_distinguished_name
req_extensions = req_ext
prompt = no

[ req_distinguished_name ]
CN = *.{domain}

[ req_ext ]
subjectAltName = @alt_names

[ alt_names ]
DNS.1 = *.{domain}
DNS.2 = xxx.{domain}
```

##### 3. **秘密鍵、CSR、CRT (`broker.crt`) を作成**
```sh
$ openssl genrsa -out broker.key 2048
$ openssl req -new -key broker.key -out broker.csr -config my_openssl.cnf
$ openssl x509 -req -in broker.csr -signkey broker.key \
    -out broker.crt -days 365 -extensions req_ext -extfile my_openssl.cnf
```

##### 4. **キーストア作成 (CRT, 秘密鍵からp12を作成 → キーストア`broker.ks` を作成）**
```sh
$ openssl pkcs12 -export -in broker.crt -inkey broker.key -out broker.p12 -name  broker-cert
Enter Export Password: ※p12用パスワードを指定（本手順では"password"に統一）※
Verifying - Enter Export Password: ※再入力※

$ keytool -importkeystore \
    -deststorepass password -destkeypass password -destkeystore broker.ks \
    -srckeystore broker.p12 -srcstoretype PKCS12 -srcstorepass password \
    -srcalias broker-cert
```

##### 5. **トラストストア作成 (CRTからトラストストア`broker.ts` を作成)**
```sh
$ keytool -importcert -alias broker-ca -file broker.crt -keystore broker.ts -storepass password
```

#### 3-3. SSLオプション、設定変更内容を指定してコンテナを起動

##### 1. **設定ファイル取得用のコンテナが起動していれば停止させる**

```sh
$ docker stop ini-container
```

##### 2. **以下オプション指定して起動**
- **オプション）環境変数`ACTIVEMQ_SSL_OPTS`としてキーストア参照を追加**
※`docker run` 時に以下（-e）オプションとして環境変数名を指定
※設定値はJVM起動オプションとして各種ストアファイル・パスワードを指定する

```sh
-e ACTIVEMQ_SSL_OPTS="-Djavax.net.ssl.keyStore=/opt/apache-activemq/conf/broker.ks \
    -Djavax.net.ssl.keyStorePassword=password \
    -Djavax.net.ssl.trustStore=/opt/apache-activemq/conf/broker.ts \
    -Djavax.net.ssl.trustStorePassword=password"
```

- **オプション）設定変更後のホスト側ファイル(\`pwd\`/)をコンテナ側でマウント**
```sh
-v `pwd`/activemq.xml:/opt/apache-activemq/conf/activemq.xml \
-v `pwd`/broker.ks:/opt/apache-activemq/conf/broker.ks \
-v `pwd`/broker.ts:/opt/apache-activemq/conf/broker.ts
```

- **dockerコンテナ起動**
```sh
$ docker run --detach \
    --name activemq-classic5.18 \
    -v `pwd`/activemq.xml:/opt/apache-activemq/conf/activemq.xml \
    -v `pwd`/broker.ks:/opt/apache-activemq/conf/broker.ks \
    -v `pwd`/broker.ts:/opt/apache-activemq/conf/broker.ts \
    -e ACTIVEMQ_SSL_OPTS="-Djavax.net.ssl.keyStore=/opt/apache-activemq/conf/broker.ks \
    -Djavax.net.ssl.keyStorePassword=password \
    -Djavax.net.ssl.trustStore=/opt/apache-activemq/conf/broker.ts \
    -Djavax.net.ssl.trustStorePassword=password" \
    -p 8883:8883 -p 1883:1883 -p 8161:8161 -p 61614:61614 -p 61619:61619 apache/activemq-classic:5.18.4

    #その他必要なポートがあれば-pオプションで追加マッピングしてください
```

### コンテナ疎通確認時注意点

**※サーバ証明書を自己署名証明書とする場合のJavaクライアント設定例です。
（Java側の制約でTLS/Handshakeに失敗するため）**
**※DNS登録などを別途実施する場合は不要となるため、環境に合わせて対応してください。**

- **JAVA実行環境の信頼できる証明機関(cacerts)に3-2_3)のサーバ証明書 (broker.crt) を追加**
    `$JAVA_HOME/lib/security/cacerts`
- **サーバ証明書(broker.crt)のimport**
```sh
$ keytool -importcert -file broker.crt \
    -alias xxx.{domain} \
    -keystore $JAVA_HOME/lib/security/cacerts \
    -storepass changeit
```
※`-keystore` オプションを使用する事で以下の警告が出る事があるが、動作上は問題なし
また、変更対象ファイルが明確になっている場合は `-cacerts` オプションでも問題ない。
```plaintext
Warning: use -cacerts option to access cacerts keystore
```
