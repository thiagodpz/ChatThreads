import java.io.*; // I/O
import java.net.*; // Rede
import java.util.*; // Colecoes
import java.util.concurrent.*; // Concorrencia

public class ChatServidor {
    // Mapa de salas: nome da sala -> conjunto de clientes na sala
    private static Map<String, Set<ConexaoCliente>> salas = new ConcurrentHashMap<>();
    // Conjunto de todos os clientes conectados ao servidor
    private static Set<ConexaoCliente> clientes = ConcurrentHashMap.newKeySet();

    public static void main(String[] args) throws IOException {
        // Cria servidor -> porta: 12345
        ServerSocket servidor = new ServerSocket(1023);
        System.out.println("Servidor online. Tudo pronto para começar!");

        // loop infinito para aceitar conexoes de novos clientes
        while (true) {
            Socket cliente = servidor.accept();
            ConexaoCliente conexao = new ConexaoCliente(cliente); // cria conexao com o cliente
            clientes.add(conexao); // adiciona cliente a lista de clientes
            new Thread(conexao).start(); // inicia a thread de comunicacao
        }
    }

    // Envia mensagem para todos os clientes da sala (exceto o remetente)
    public static void enviarParaSala(String sala, String mensagem, ConexaoCliente remetente) {
        if (salas.containsKey(sala)) {
            for (ConexaoCliente cliente : salas.get(sala)) {
                if (cliente != remetente) {
                    cliente.enviar(mensagem);
                }
            }
        }
    }

    // Adiciona cliente a sala
    public static void entrarSala(String sala, ConexaoCliente cliente) {
        if (salas.containsKey(sala)) {
            salas.get(sala).add(cliente);
        }
    }

    // Remove cliente da sala
    public static void sairSala(String sala, ConexaoCliente cliente) {
        if (salas.containsKey(sala)) {
            salas.get(sala).remove(cliente);
        }
    }

    // Retorna lista com salas criadas
    public static Set<String> listarSalas() {
        return salas.keySet();
    }

    // Cria uma nova sala
    public static void criarSala(String sala) {
        salas.putIfAbsent(sala, ConcurrentHashMap.newKeySet());
    }

    // Fecha a sala e avisa aos usuarios que esta foi fechada
    public static void encerrarSala(String sala) {
        if (salas.containsKey(sala)) {
            for (ConexaoCliente ch : salas.get(sala)) {
                ch.enviar("A sala " + sala + " foi encerrada e todos os usuários foram desconectados.");
                ch.salaAtual = null;
            }
            salas.remove(sala);
        }
    }

    // Expulsa usuario da sala e o notifica da expulsao
    public static void expulsarUsuario(String nome, String sala) {
        if (salas.containsKey(sala)) {
            for (ConexaoCliente ch : salas.get(sala)) {
                if (ch.nome.equals(nome)) {
                    ch.enviar("Sua participação nesta sala foi encerrada por um administrador.");
                    sairSala(sala, ch);
                    ch.salaAtual = null;
                    break;
                }
            }
        }
    }

    // Remove cliente do servidor e da sala atual se estiver em uma
    public static void desconectar(ConexaoCliente cliente) {
        clientes.remove(cliente);
        if (cliente.salaAtual != null) sairSala(cliente.salaAtual, cliente);
    }
}