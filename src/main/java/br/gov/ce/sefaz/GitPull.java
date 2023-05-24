/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.gov.ce.sefaz;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author gilmario
 */
public class GitPull {

//    static Runtime run = Runtime.getRuntime();
    private ProcessBuilder build = new ProcessBuilder();

    public String pull(String... comando) throws IOException {
        Path p = Paths.get("/home/gilmario/NetBeansProjects/gilmario/kubernetes-client/kube-build");
        build.directory(p.toFile());
        Process process = build.command(comando).start();

//        Process process = run.exec("cd /home/gilmario/NetBeansProjects/gilmario/kubernetes-client/kube-build");
//        while (process.isAlive()) {
//            System.out.println("Ativo");
//        }
        String resposta = new String(new ByteArrayInputStream(process.getInputStream().readAllBytes()).readAllBytes());

        System.out.println("Terminou");

        return resposta;
//        run.exec("git pull");
    }
}
