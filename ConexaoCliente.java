import java.io.*;
import java.net.*;

public class ConexaoCliente implements Runnable {
    private Socket socket;
    private BufferedReader entrada;
    private PrintWriter saida;

    public String nome; // nome do cliente
    public boolean adm = false; // flag de administrador
    public String salaAtual = null; // nome da sala atual

    public ConexaoCliente(Socket socket) throws IOException {
        this.socket = socket;
        this.entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.saida = new PrintWriter(socket.getOutputStream(), true);
    }

    // metodo para enviar mensagem ao cliente
    public void enviar(String mensagem) {
        saida.println(mensagem);
    }

    public void run() {
        try {
            enviar("Bem-vindo! Use /login nome [adm]");
            String linha;

            // loop de comunicacao com o cliente
            while ((linha = entrada.readLine()) != null) {
                if (linha.startsWith("/login")) {

                    // login
                    String[] partes = linha.split(" ");
                    nome = partes[1];
                    adm = partes.length > 2 && partes[2].equalsIgnoreCase("adm");
                    enviar("Logado como: " + nome + (adm ? " (adm)" : ""));
                } else if (linha.equals("/salas")) {
                    enviar("Salas disponíveis: " + ChatServidor.listarSalas());
                } else if (linha.startsWith("/entrar")) {

                    // Entrar na sala
                    String sala = linha.split(" ")[1];
                    if (!ChatServidor.listarSalas().contains(sala)) {
                        enviar("Erro: A sala \"" + sala + "\" nao existe.");
                    } else {
                        if (salaAtual != null) ChatServidor.sairSala(salaAtual, this);
                        ChatServidor.entrarSala(sala, this);
                        salaAtual = sala;
                        enviar("Voce entrou na sala: " + sala);
                    }

                } else if (linha.equals("/sair")) {
                    //sair da sala
                    if (salaAtual != null) {
                        ChatServidor.sairSala(salaAtual, this);
                        enviar("Você saiu da sala: " + salaAtual);
                        salaAtual = null;
                    }

                } else if (linha.startsWith("/msg")) {
                    // envia mensagem para a sala atual
                    String msg = linha.substring(5);
                    if (salaAtual != null) {
                        ChatServidor.enviarParaSala(salaAtual, nome + ": " + msg, this);
                    } else {
                        enviar("Voce deve estar conectado a uma sala para enviar uma mensagem.");
                    }

                } else if (linha.startsWith("/criar") && adm) {
                    // cria sala (adm)
                    String sala = linha.split(" ")[1];
                    ChatServidor.criarSala(sala);
                    enviar("Sala criada: " + sala);

                } else if (linha.startsWith("/encerrar") && adm) {
                    // encerra sala (adm)
                    String sala = linha.split(" ")[1];
                    ChatServidor.encerrarSala(sala);
                    enviar("Sala encerrada: " + sala);

                } else if (linha.startsWith("/expulsar") && adm) {
                    // expulsa cliente (adm)
                    String alvo = linha.split(" ")[1];
                    ChatServidor.expulsarUsuario(alvo, salaAtual);

                } else if (linha.equals("/sairServidor")) {
                    // encerra conexao
                    enviar("Desconectado.");
                    break;
                } else {
                    enviar("Comando inválido.");
                }
            }
        } catch (IOException e) {
            System.err.println("Erro com cliente " + nome + ": " + e.getMessage());
        } finally {
            try {
                ChatServidor.desconectar(this);
                socket.close();
            } catch (IOException e) {}
        }
    }
}