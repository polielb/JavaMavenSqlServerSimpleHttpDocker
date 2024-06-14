package com.example;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class App {
    public static void main(String[] args) throws IOException {
        // Iniciar el servidor en el puerto 8081
        HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);
        server.createContext("/DameLaHora", new Handler());
        server.createContext("/", new RootHandler());
        server.setExecutor(null); // use the default executor
        server.start();
        System.out.println("Servidor escuchando en el puerto 8081");
    }

    static class Handler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
			
			 // Configuración CORS
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");

            //String connectionUrl = "jdbc:sqlserver://DESKTOP-GMUPP2V\\SQLEXPRESS:64577;databaseName=master;integratedSecurity=true";			
			//String connectionUrl = "jdbc:sqlserver://DESKTOP-GMUPP2V\\SQLEXPRESS:64577;databaseName=master;user=root;password=root1";
            String connectionUrl = "jdbc:sqlserver://192.168.1.10:1433;databaseName=master;user=root;password=root1";


            String result;
            try (Connection con = DriverManager.getConnection(connectionUrl)) {
                System.out.println("Conexión exitosa");

                // Obtener la fecha actual de SQL Server
                try (Statement stmt = con.createStatement()) {
                    ResultSet rs = stmt.executeQuery("SELECT GETDATE()");
                    if (rs.next()) {
                        result = "Fecha actual de SQL Server: " + rs.getTimestamp(1);
                    } else {
                        result = "No se pudo obtener la fecha de SQL Server.";
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                result = "Error al conectar a SQL Server: " + e.getMessage();
            }

            exchange.sendResponseHeaders(200, result.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(result.getBytes());
            os.close();
        }
    }

    static class RootHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = "Servidor escuchando en el puerto 8081";
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
