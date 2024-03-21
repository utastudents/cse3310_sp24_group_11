package uta.cse3310;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import net.freeutils.httpserver.HTTPServer;
import net.freeutils.httpserver.HTTPServer.ContextHandler;
import net.freeutils.httpserver.HTTPServer.FileContextHandler;
import net.freeutils.httpserver.HTTPServer.Request;
import net.freeutils.httpserver.HTTPServer.Response;
import net.freeutils.httpserver.HTTPServer.VirtualHost;

public class HttpServer {
    //Sets up and runs the HTTP server
    int port;
    String dirname;

    public HttpServer(int portNum, String dirName) {
        // Creates instance of HttpServer
    }

    public void start() {
        // Initializes and starts HttpServer
    }
}
