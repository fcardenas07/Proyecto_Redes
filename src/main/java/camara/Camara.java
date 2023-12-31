package camara;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Random;

public class Camara {
    public static void main(String[] args) {
        try {
            Socket socketTCP = new Socket("receptor", 5050);
            OutputStream salida = socketTCP.getOutputStream();
            salida.write("camara.Camara\n".getBytes());

            while (true) {
                String enviarPatente = generarPatenteChilena() + "\n";
                salida.write(enviarPatente.getBytes());

                int tiempoEspera = new Random().nextInt(36000);
                Thread.sleep(tiempoEspera);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String generarPatenteChilena() {
        char[] letras = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J',
                'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
                'W', 'X', 'Y', 'Z'};
        int[] numeros = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

        StringBuilder patente = new StringBuilder();

        for (int i = 0; i < 3; i++) {
            char letra = letras[new Random().nextInt(letras.length)];
            patente.append(letra);
        }

        for (int i = 0; i < 3; i++) {
            int numero = numeros[new Random().nextInt(numeros.length)];
            patente.append(numero);
        }

        return patente.toString();
    }

}
