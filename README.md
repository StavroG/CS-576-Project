# CS576 Project: VPN Encryption 


## src folder structure
```
src
├─ main
│  ├─ java
│  │  ├─ app
│  │  │  ├─ Client.java
│  │  │  └─ Server.java
│  │  ├─ data
│  │  │  ├─ aes
│  │  │  │  └─ AES.java
│  │  │  ├─ camellia
│  │  │  │  └─ Camellia.java
│  │  │  ├─ chacha20
│  │  │  │  └─ CHACHA20.java
│  │  │  ├─ threedes
│  │  │  │  └─ ThreeDES.java
│  │  │  └─ cipher.java
│  │  ├─ fileGenerator
│  │  │  └─ TextFileGenerator.java
│  │  └─ socket
│  │     ├─ client
│  │     │  ├─ SocketClient.java
│  │     │  ├─ TcpClient.java
│  │     │  └─ UdpClient.java
│  │     └─ server
│  │        ├─ SocketServer.java
│  │        ├─ TcpServer.java
│  │        └─ UdpServer.java
│  └─ resources
│     ├─ 1MB.txt
│     ├─ 100MB.txt
│     └─ 1GB.txt
└─ test
   └─ java
      └─ CipherTest.java
```
