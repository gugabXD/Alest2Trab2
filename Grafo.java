import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

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
        Heap aux = new Heap(linhas*colunas);
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
        aux.put(nodo);
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
                    aux.put(oeste);
                }
            }
            if (ncoluna < colunas - 1) {
                alt = nodo.distancia + 1;
                Node leste = nodos[nlinha][ncoluna + 1];
                if (alt < leste.distancia && leste.navegavel) {
                    leste.distancia = alt;
                    aux.put(leste);
                }
            }
            if (nlinha > 0) {
                alt = nodo.distancia + 1;
                Node norte = nodos[nlinha - 1][ncoluna];
                if (alt < norte.distancia && norte.navegavel) {
                    norte.distancia = alt;
                    aux.put(norte);
                }

            }
            if (nlinha < linhas - 1) {
                alt = nodo.distancia + 1;
                Node sul = nodos[nlinha + 1][ncoluna];
                if (alt < sul.distancia && sul.navegavel) {
                    sul.distancia = alt;
                    aux.put(sul);
                }
            }
            nodo = aux.get();
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












    private class Heap {

        private Node v[];
        private int used;

        public Heap(int quant) {
            used = 0;
            v = new Node[quant];
        }

        private int left ( int i )   { return 2 * i + 1; }
        private int right ( int i )  { return 2 * i + 2; }
        private int parent ( int i ) { return (i-1) / 2; }

        public boolean isEmpty(){
            return used==0;
        }
        public boolean existe(Node n){
            if(n==null) return false;
            for(Node nodo: v){
                if(nodo==n) return true;
            }
            return false;
        }
        private void sift_up ( int pos ) {
            //if(pos==0) return;
            int parent = parent(pos);
            if(v[pos].distancia<v[parent].distancia) {
                Node temp = v[parent];
                v[parent]=v[pos];
                v[pos] = temp;
                sift_up(parent);
            }
            //sift_up(parent);
        }

        public void put( Node data ) {
            v[used] = data;
            sift_up( used );
            used++;
        }

        public void insert(Node data){
            v[used] = data;
            used++;
        }

        public void heapify(){
            for(int i = parent(used-1); i>=0; i--){
                sift_down(i);
            }
        }

        public void sort(){
            int i; int oldsize = used;
            Node temp;
            for(i=0; i<oldsize; i++){
                used--;
                temp = v[0];
                v[0] = v[used];
                v[used] = temp;
                sift_down(0);
            }
            used = oldsize;
        }

        private void sift_down ( int pos ) {
            int left = left(pos);
            int right = right(pos);
            Node temp;
            if(left>=used) return;
            if(left==used-1){
                if(v[pos].distancia>v[left].distancia) {
                    temp = v[pos];
                    v[pos] = v[left];
                    v[left] = temp;
                }
                return;
            }
            if(v[left].distancia<v[right].distancia){
                if(v[pos].distancia>v[left].distancia){
                    temp = v[pos];
                    v[pos] = v[left];
                    v[left] = temp;
                    sift_down(left);
                }
            }
            else{
                if(v[pos].distancia>v[right].distancia){
                    temp = v[pos];
                    v[pos] = v[right];
                    v[right] = temp;
                    sift_down(right);
                }
            }
        }
        public Node get( ) {
            if(used==0) return null;
            Node res = v[0];
            v[0] = v[--used];
            sift_down( 0 );
            return res;
        }

        private int len( int a ) {
            int res = 0;
            while ( a > 0 ) {
                res++;
                a /= 10;
            }
            return res;
        }

        private void print( int b, int elem, int sp )  {
            int i, j;

            System.out.println( "" );
            for( j = 0; j < used; j++ ) {
                if(!v[j].navegavel) System.out.print("* ");
                else if(v[j].valor!=0) System.out.print(v[j].valor+" ");
                else System.out.print(". ");
            }
            System.out.println( "" );

            while ( true ) {
                for( j = 0; j <= sp / 2; j++ ) System.out.print( " " );
                for( i = b; i < b + elem; i++ ) {
                    if ( i == used ) return;
                    int aux = len(v[i].valor);
                    if(!v[i].navegavel) System.out.print("* ");
                    else if(v[i].valor!=0) System.out.print(v[i].valor+" ");
                    else System.out.print(". ");
                    System.out.print(v[i].posicao+" ");
                    for( j = 0; j < sp - aux; j++ ) System.out.print( " " );
                }
                System.out.println( "" );
                b = b + elem;
                elem = 2 * elem;
                sp = sp / 2;
            }
        }

        public void print( )  {
            System.out.println( "" );
            print( 0, 1, 64 );
            System.out.println( "" );
        }
        }

}
