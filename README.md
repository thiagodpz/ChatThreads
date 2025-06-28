# 🗨️ Chat em Java (Servidor e Cliente)

Este projeto é um sistema simples de chat em rede desenvolvido em Java. Ele permite múltiplos clientes se conectarem a um servidor, criarem salas de bate-papo, trocarem mensagens e administrarem as salas.

---

## 📂 Estrutura do projeto

- `ChatServer.java` — código do servidor que gerencia conexões, salas e mensagens.
- `ChatClient.java` — cliente que permite o usuário interagir via terminal.
- `ClientHandler.java` — gerencia a conexão individual de cada cliente no servidor.

---

## 🚀 Como usar (PowerShell no Windows)

### 1️⃣ Compile os arquivos
No PowerShell, dentro da pasta onde estão os arquivos `.java`:
```powershell
javac ChatServer.java ChatClient.java ClientHandler.java
2️⃣ Inicie o servidor
powershell
Copiar
Editar
java ChatServer
➡ O servidor será iniciado na porta 12345.

3️⃣ Inicie um ou mais clientes
Abra novas janelas do PowerShell e rode:

powershell
Copiar
Editar
java ChatClient
➡ Os clientes irão se conectar ao servidor no localhost:12345.

📝 Comandos disponíveis (no cliente)
Login
pgsql
Copiar
Editar
/login <nome> [admin]
Exemplo:

pgsql
Copiar
Editar
/login Thiago
/login Ana admin
(Opcional: use admin no final para ter permissões de administrador.)

Salas e mensagens
bash
Copiar
Editar
/salas                  # Lista as salas disponíveis
/criar <sala>           # Cria uma nova sala (apenas admin)
/entrar <sala>          # Entra em uma sala
/sair                   # Sai da sala atual
/msg <mensagem>         # Envia mensagem para a sala
Administração (apenas admin)
bash
Copiar
Editar
/encerrar <sala>        # Encerra uma sala
/expulsar <nome>        # Expulsa um usuário da sala atual
Desconexão
bash
Copiar
Editar
/sairServidor           # Sai do servidor e encerra o cliente
🌐 Testes em rede
Se quiser testar em rede local, altere no ChatClient.java:

java
Copiar
Editar
Socket socket = new Socket("IP_DO_SERVIDOR", 12345);
Substitua "IP_DO_SERVIDOR" pelo IP da máquina onde o servidor está rodando.

📌 Notas
✅ As mensagens enviadas com /msg são vistas pelos outros usuários da mesma sala.
✅ O usuário não vê a própria mensagem enviada (isso pode ser ajustado no código se quiser).
✅ O servidor suporta múltiplos clientes ao mesmo tempo usando threads.

