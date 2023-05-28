import java.util.ArrayList;
public class Grafo {

    private ArrayList<Node> nodos;
    int linhas, colunas;

    public Grafo(int linhas, int colunas){
        nodos = new ArrayList<>();
        this.linhas = linhas;
        this.colunas = colunas;
    }
    private class Node{
        private Node[] direcoes;
        private boolean navegavel;
        private int valor;

        private int distancia;
        public Node(boolean navegavel, int valor){
            this.navegavel = navegavel;
            this.valor = valor;
            direcoes = new Node[4];
        }

    }

    public void createNodo(char res){
        Node nodo;
        switch(res){
            case('.') -> nodo = new Node(true, 0);
            case('*') -> nodo = new Node(false, 0);
            default -> {
                nodo = new Node(false, res-48);
            }

        }
        nodos.add(nodo);
    }

    public void atribuir(){
        for(int i=0; i<linhas*colunas-1; i++){
            Node n = nodos.get(i);
            if(i>colunas) n.direcoes[0] = nodos.get(i-colunas);
            if(i%colunas!=0) n.direcoes[3] = nodos.get(i-1);
            if(i%colunas!=(colunas-1)) n.direcoes[2] = nodos.get(i+1);
            if(i/colunas!=(linhas-1)) n.direcoes[1] = nodos.get(i+colunas);
        }
    }

    public int dijkstra(){
        Heap aux = new Heap(nodos.size());
        Node nodo=null;
        int alt;
        for(Node n: nodos){
            n.distancia = 1000000000;
            if(n.navegavel) aux.insert(n);
            else if(n.valor==1) nodo = n;
        }
        nodo.distancia = 0;
        int obj = 2;
        while(!aux.isEmpty()) {
            for (Node n : nodo.direcoes) {
                if(n!=null) {
                    if (aux.existe(n)) {
                        alt = nodo.distancia + 1;
                        if (alt < n.distancia) n.distancia = alt;

                    }
                    if (n.valor == obj) {
                        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
                        alt = nodo.distancia + 1;
                        if (alt < n.distancia) n.distancia = alt;
                    }
                }
            }
            aux.heapify();
            aux.print();
            nodo = aux.get();
        }
        return valor(2);
    }
//C:\Users\User\Desktop\teste.txt
    public int valor(int valor){
        for(Node n: nodos){
            if(n.valor == valor){
                return n.distancia;
            }
        }
        return -1;
    }


    public String toString(){
        String s = "";
        for(int i=0; i<nodos.size(); i++){
            if(i%colunas==0 && i!=0) s+="\n";
            Node nodo = nodos.get(i);
            if(nodo.navegavel) s+=".";
            else if(nodo.valor!=0) s+=nodo.valor;
            else s+="*";
        }
        return s;
    }

    public void direcoes(){
        int i = 0;
        for(Node n: nodos){
            System.out.print("-");
            if (n.navegavel) System.out.print(".");
            else if (n.valor != 0) System.out.print(n.valor);
            else System.out.print("*");
            System.out.println("-");
            for(Node nodo: n.direcoes){
                switch(i){
                    case 0-> System.out.print(" N");
                    case 1-> System.out.print(" S");
                    case 2-> System.out.print(" L");
                    case 3-> System.out.print(" O");
                }
                if(nodo!=null) {
                    if (nodo.navegavel) System.out.print(".");
                    else if (nodo.valor != 0) System.out.print(nodo.valor);
                    else System.out.print("*");
                }
                i++;
            }
            System.out.println();
            System.out.println();
            i=0;
        }
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
            for( j = 0; j < used; j++ ) System.out.print( v[j].distancia + " " );
            System.out.println( "" );

            while ( true ) {
                for( j = 0; j <= sp / 2; j++ ) System.out.print( " " );
                for( i = b; i < b + elem; i++ ) {
                    if ( i == used ) return;
                    int aux = len(v[i].valor);
                    System.out.print( v[i].distancia );
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
