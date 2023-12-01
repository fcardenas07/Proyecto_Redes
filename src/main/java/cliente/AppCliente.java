package cliente;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;


public class AppCliente {
    public static void main(String[] args) {
        try {
            Socket socketTCP = new Socket("receptor", 5050);
            OutputStream salida = socketTCP.getOutputStream();

            new Thread(() -> handleMessages(socketTCP)).start();

            menu(salida);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void menu(OutputStream salida) {
        mostrarMenu();
        switch (recuperarRespuesta()) {
            case 1 -> buscarPatente(salida);
            case 2 -> System.exit(0);
            default -> System.out.println("Opción no válida");
        }
        menu(salida);
    }

    public static void buscarPatente(OutputStream salida) {
        System.out.println("Ingrese la patente que desea buscar:");
        Scanner scanner = new Scanner(System.in);
        String patente = scanner.nextLine();

        try {
            // Envía la patente al servidor para la búsqueda
            salida.write(patente.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int recuperarRespuesta() {
        int respuesta = -1;
        try {
            respuesta = new Scanner(System.in).nextInt();
        } catch (Exception e) {
            System.out.println("Ingresa un número");
        }
        return respuesta;
    }

    public static void mostrarMenu() {
        System.out.println("Menú\n1) Buscar Patente\n2) Salir");
    }

    private static void handleMessages(Socket socket) {
        try {
            Scanner scanner = new Scanner(socket.getInputStream());
            while (true) {
                if (scanner.hasNextLine()) {
                    String message = scanner.nextLine();
                    System.out.println(message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
