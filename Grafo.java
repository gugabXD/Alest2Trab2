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

        private String key;
        private int valor;
        private int distancia;

        private int posicao;
        public Node(boolean navegavel, int valor, int posicao, int distancia){
            this.navegavel = navegavel;
            this.valor = valor;
            this.distancia = distancia;
            this.posicao = posicao;
            key = posicao/colunas+"/"+posicao%colunas;
        }

        public String toString(){
            String s="";
            if(!navegavel) s ="*";
            else if(valor!=0) s+=valor;
            else s=".";
            return s;
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
        nodos[posicao/colunas][posicao%colunas] = nodo;
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
    private int dijkstra(int saida, int destino){
        int res;
        PriorityQueue<Node> aux = new PriorityQueue<>(Comparator.comparingInt(nodo -> nodo.distancia));
        Node nodo=null;
        int alt, posicao, nlinha, ncoluna;
            for (int j = 0; j < linhas * colunas; j++) {
                Node n = nodos[j / colunas][j % colunas];
                if(n.valor==saida) {
                    nodo = n;
                }
                if (n.navegavel) {
                    n.distancia = Integer.MAX_VALUE;
                }
            }
            nodo.distancia = 0;
            aux.offer(nodo);
            System.out.print(" "+saida+" "+destino+" ");
            while(nodo!=null){
                posicao = nodo.posicao;
                nlinha = posicao/colunas;
                ncoluna = posicao%colunas;
                if(ncoluna>0) {
                    alt = nodo.distancia+1;
                    Node oeste = nodos[nlinha][ncoluna-1];
                    if(alt<oeste.distancia && oeste.navegavel)
                        {
                            oeste.distancia=alt;
                            aux.offer(oeste);
                        }
                }
                if(ncoluna<colunas-1) {
                    alt = nodo.distancia+1;
                    Node leste = nodos[nlinha][ncoluna+1];
                    if(alt<leste.distancia && leste.navegavel){
                        leste.distancia=alt;
                        aux.offer(leste);
                    }
                }
                if(nlinha>0) {
                    alt = nodo.distancia+1;
                    Node norte = nodos[nlinha-1][ncoluna];
                    if(alt<norte.distancia && norte.navegavel) {
                        norte.distancia=alt;
                        aux.offer(norte);
                    }

                }
                if(nlinha<linhas-1) {
                    alt = nodo.distancia + 1;
                    Node sul = nodos[nlinha+1][ncoluna];
                    if (alt < sul.distancia && sul.navegavel) {
                        sul.distancia = alt;
                        aux.offer(sul);
                    }
                }
                nodo = aux.poll();
            }
            res = valor(destino);
        System.out.println(res);
        return res;
        }
    /*public int dijkstraTest(int saida, int destino){
        int res;
        PriorityQueue<Node> aux = new PriorityQueue<>(Comparator.comparingInt(nodo -> nodo.distancia));
        Node nodo=null;
        int alt, posicao, nlinha, ncoluna;
        for (int j = 0; j < linhas * colunas; j++) {
            Node n = nodos[j / colunas][j % colunas];
            if(n.valor==saida) {
                nodo = n;
            }
            if (n.navegavel) {
                n.distancia = Integer.MAX_VALUE;
            }
        }
        nodo.distancia = 0;
        aux.offer(nodo);
        System.out.println(" "+saida+" "+destino+" ");
        Set<String> visto = new HashSet<>();
        while(true){
            if(nodo==null) break;
            posicao = nodo.posicao;
            nlinha = posicao/colunas;
            ncoluna = posicao%colunas;
            if(visto.contains(nodo.key) && nodo.valor!=saida){
                continue;
            }
            visto.add(nodo.key);
            if(ncoluna>0) {
                alt = nodo.distancia+1;
                Node oeste = nodos[nlinha][ncoluna-1];
                if(alt<oeste.distancia && oeste.navegavel)
                {
                    oeste.distancia=alt;
                    aux.offer(oeste);
                    System.out.println(oeste.posicao);
                }
            }
            if(ncoluna<colunas-1) {
                alt = nodo.distancia+1;
                Node leste = nodos[nlinha][ncoluna+1];
                if(alt<leste.distancia && leste.navegavel){
                    leste.distancia=alt;
                    aux.offer(leste);
                    System.out.println(leste.posicao);
                }
            }
            if(nlinha>0) {
                alt = nodo.distancia+1;
                Node norte = nodos[nlinha-1][ncoluna];
                if(alt<norte.distancia && norte.navegavel) {
                    norte.distancia=alt;
                    aux.offer(norte);
                    System.out.println(norte.posicao);
                }

            }
            if(nlinha<linhas-1) {
                alt = nodo.distancia + 1;
                Node sul = nodos[nlinha + 1][ncoluna];
                if (alt < sul.distancia && sul.navegavel) {
                    sul.distancia = alt;
                    aux.offer(sul);
                    System.out.println(sul.posicao);
                }
            }
            nodo = aux.poll();
            direcoes(nlinha,ncoluna);
        }
        res = valor(destino);
        System.out.println(res);
        return res;
    }*/

        public int valor(int objetivo){
            for(Node n[]: nodos){
                for(Node nodo: n){
                    if(nodo.valor==objetivo) return nodo.distancia;
                }
            }
            return -1;
        }

//C:\Users\User\Desktop\teste.txt

    public void direcoes(int linha, int coluna){
        System.out.println("NODO:"+nodos[linha][coluna]+" POSICAO:"+nodos[linha][coluna].posicao);
        if(linha>0)System.out.print("N:"+nodos[linha-1][coluna].posicao);
        if(linha<linhas-1)System.out.print("/S:"+nodos[linha+1][coluna].posicao);
        if(coluna<colunas-1)System.out.print("/L:"+nodos[linha][coluna+1].posicao);
        if(coluna>0)System.out.println("/O:"+nodos[linha][coluna-1].posicao);
    }
    public String toString(){
        String s = "";
        for(int i=0; i<linhas*colunas; i++){
            Node n = nodos[i/colunas][i%colunas];
            if(i%colunas==0) s+="\n";
            if(!n.navegavel) s+="*";
            else if(n.valor!=0) s+=n.valor;
            else s+=".";
        }
        return s;
    }


}
