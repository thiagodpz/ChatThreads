import java.io.*;
import java.net.*;

public class ChatCliente {
    public static void main(String[] args) {
        try {
            // Conecta ao servidor local
            Socket socket = new Socket("localhost", 1023);

            // Prepara leitura e escrita com o servidor
            BufferedReader entradaServidor = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter saidaServidor = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));

            // Thread para ouvir mensagens do servidor
            Thread ouvir = new Thread(() -> {
                String msg;
                try {
                    while ((msg = entradaServidor.readLine()) != null) {
                        System.out.println(msg);
                    }
                } catch (IOException e) {
                    System.out.println("Conexão encerrada.");
                }
            });

            ouvir.start();

            // Enviar comandos do usuário
            String linha;
            while ((linha = teclado.readLine()) != null) {
                saidaServidor.println(linha);
                if (linha.equals("/sairServidor")) break; //sai quando usuario digita /sairServidor
            }

            socket.close(); // fecha o socket quando sai
        } catch (IOException e) {
            System.out.println("Erro ao conectar com o servidor: " + e.getMessage());
        }
    }
}
