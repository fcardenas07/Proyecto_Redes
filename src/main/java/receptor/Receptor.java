package receptor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Receptor {
    public static void main(String[] args) {
        int puerto = 5050;

        try {
            while (!isDatabaseAvailable("dbapp", 3306)) {
                System.out.println("Esperando a que la base de datos esté disponible...");
                Thread.sleep(1000);
            }

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection(
                    "jdbc:mysql://dbapp:3306/sistema", "root", "pwdb");

            ServerSocket receptorSocket = new ServerSocket(puerto);
            System.out.println("Escuchando en el puerto " + puerto);

            while (true) {
                Socket socket = receptorSocket.accept();
                String tipoEntidad = obtenerTipoEntidad(socket);

                if ("camara".equals(tipoEntidad)) {
                    CamaraHandler camaraHandler = new CamaraHandler(conexion, socket);
                    new Thread(camaraHandler).start();
                } else if ("cliente".equals(tipoEntidad)) {
                    ClienteHandler clienteHandler = new ClienteHandler(conexion, socket);
                    new Thread(clienteHandler).start();
                } else {
                    socket.close();
                }
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public static String obtenerTipoEntidad(Socket socket) {
        try {
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            return entrada.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isDatabaseAvailable(String host, int port) {
        try (Socket socket = new Socket(host, port)) {
            // Si se puede establecer la conexión, la base de datos está disponible
            return true;
        } catch (IOException e) {
            // Si hay una excepción, la base de datos no está disponible
            return false;
        }
    }

}