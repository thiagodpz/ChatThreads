Servidor de Chat em Java
Este projeto implementa um servidor e cliente de chat em Java, permitindo múltiplos usuários conectados simultaneamente, com suporte a salas, mensagens e administração de salas.

📌 Funcionalidades
✅ Suporte a múltiplos clientes conectados simultaneamente
✅ Criação e gerenciamento de salas de chat
✅ Envio de mensagens para salas
✅ Controle de administrador (criar, encerrar sala, expulsar usuário)
✅ Listagem de salas disponíveis
✅ Conexão por terminal (via linha de comando)

🚀 Como executar
1️⃣ Compile os arquivos
Abra o terminal (ou PowerShell no Windows) e navegue até o diretório onde estão os arquivos .java.

Compile todos os arquivos:

bash
javac ChatServidor.java ChatCliente.java ConexaoCliente.java

2️⃣ Inicie o servidor

bash
java ChatServidor
O servidor estará online na porta 12345.

3️⃣ Conecte um ou mais clientes
Em outro terminal (ou mais de um, para simular vários usuários):

bash
java ChatCliente
💬 Comandos do Cliente
Após conectar, use os comandos abaixo:

Comando	Descrição
/login NOME [adm] -> Faz login com o nome informado. Adicione adm ao final para login como administrador.
/salas -> Lista as salas disponíveis no servidor.
/criar SALA (adm) -> Cria uma nova sala de chat.
/entrar SALA -> Entra na sala especificada.
/sair -> Sai da sala atual.
/encerrar SALA (adm) -> Encerra a sala informada e desconecta seus usuários.
/expulsar NOME (adm) -> Expulsa o usuário especificado da sala atual.
/msg TEXTO -> Envia uma mensagem para todos na sala atual.
/sairServidor -> Desconecta o cliente do servidor.

📝 Estrutura dos Arquivos
ChatServidor.java
Gerencia as conexões de clientes, salas, envio de mensagens e comandos administrativos.

ChatCliente.java
Interface de linha de comando para o usuário interagir com o servidor.

ConexaoCliente.java
Responsável pela comunicação individual com cada cliente conectado (thread).

⚠️ Observações
O servidor e o cliente se comunicam via localhost na porta 1023.

Para testes em rede real, altere o localhost no ChatCliente.java pelo IP da máquina do servidor.

Administradores são definidos pelo comando de login.

💡 Exemplos de uso

/login Thiago adm
/criar sala1
/entrar sala1
/msg Olá pessoal!

/login Pedro
/salas
/entrar sala1
/msg Olá

