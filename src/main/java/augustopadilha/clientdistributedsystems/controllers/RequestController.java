package augustopadilha.clientdistributedsystems.controllers;

import java.io.PrintWriter;

public class RequestController {
    static void sendRequest(PrintWriter outToServer, String jsonRequest) {
        System.out.println("Enviando JSON para o servidor...");
        System.out.println("JSON enviado: " + jsonRequest);
        outToServer.println(jsonRequest);
        outToServer.flush();
    }
}
