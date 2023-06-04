import java.util.*;

public class Grafo {

    private Node[][] nodos;
    int linhas, colunas, posicao;

    public Grafo(int linhas, int colunas){
        nodos = new Node[linhas][colunas];
        this.linhas = linhas;
        this.colunas = colunas;
    }
    private class Node{
        private boolean navegavel;
        private int valor;
        private int posicao;

        private int distancia;
        public Node(boolean navegavel, int valor, int posicao, int distancia){
            this.navegavel = navegavel;
            this.valor = valor;
            this.distancia = distancia;
            this.posicao = posicao;
        }

    }

    public void createNodo(char res){
        Node nodo;
        switch(res){
            case('.') -> nodo = new Node(true, 0, posicao, Integer.MAX_VALUE);
            case('*') -> nodo = new Node(false, 0, posicao, Integer.MAX_VALUE);
            default -> {
                nodo = new Node(true, res-48, posicao, Integer.MAX_VALUE);
            }
        }
        nodos[posicao/colunas][posicao%colunas]= nodo;
        posicao++;
    }
    public int dijkstra(){
        int dist=0,saida, destino=2, res, bloqueado=-1;
        for(saida=1; saida<=9; saida++) {
            if(saida==bloqueado) saida++;
            res = dijkstra(saida, destino);
            if(res<Integer.MAX_VALUE) dist += res;
            else{
                saida--;
                bloqueado = destino;
            }
            if(destino==1) break;
            destino++;
            if(destino==10) destino=1;
        }
        return dist;
    }
    private int dijkstra(int saida, int destino) {
        int res;
        Queue<Node> aux = new LinkedList();
        Node nodo = null;
        int alt, posicao, nlinha, ncoluna;
        for (int j = 0; j < linhas * colunas; j++) {
            Node n = nodos[j / colunas][j % colunas];
            if (n.valor == saida) {
                nodo = n;
            }
            if (n.navegavel) {
                n.distancia = Integer.MAX_VALUE;
            }
        }
        nodo.distancia = 0;
        aux.add(nodo);
        System.out.print(" " + saida + " " + destino + " ");
        while (nodo != null) {
            posicao = nodo.posicao;
            nlinha = posicao / colunas;
            ncoluna = posicao % colunas;
            if (ncoluna > 0) {
                alt = nodo.distancia + 1;
                Node oeste = nodos[nlinha][ncoluna - 1];
                if (alt < oeste.distancia && oeste.navegavel) {
                    oeste.distancia = alt;
                    aux.add(oeste);
                }
            }
            if (ncoluna < colunas - 1) {
                alt = nodo.distancia + 1;
                Node leste = nodos[nlinha][ncoluna + 1];
                if (alt < leste.distancia && leste.navegavel) {
                    leste.distancia = alt;
                    aux.add(leste);
                }
            }
            if (nlinha > 0) {
                alt = nodo.distancia + 1;
                Node norte = nodos[nlinha - 1][ncoluna];
                if (alt < norte.distancia && norte.navegavel) {
                    norte.distancia = alt;
                    aux.add(norte);
                }

            }
            if (nlinha < linhas - 1) {
                alt = nodo.distancia + 1;
                Node sul = nodos[nlinha + 1][ncoluna];
                if (alt < sul.distancia && sul.navegavel) {
                    sul.distancia = alt;
                    aux.add(sul);
                }
            }
            nodo = aux.poll();
        }
        res = valor(destino);
        System.out.println(res);
        return res;
    }


//C:\Users\User\Desktop\teste.txt
        public int valor(int objetivo){
            for(Node n[]: nodos){
                for(Node nodo: n){
                    if(nodo.valor==objetivo) return nodo.distancia;
                }
            }
            return -1;
        }
}
