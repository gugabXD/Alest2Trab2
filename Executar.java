import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Executar {

    private Grafo g;
    public void leitor(){
        int linha, coluna;
        BufferedReader leitor;
        Scanner in = new Scanner(System.in);
        try{
            System.out.println("Insira o local de arquivo");
            String caminho = in.nextLine();
            leitor = new BufferedReader(new FileReader(caminho));
            String primeira = leitor.readLine();
            String res[] = primeira.split(" ", 0);
            linha = Integer.parseInt(res[0]); coluna = Integer.parseInt(res[1]);
            g = new Grafo(linha, coluna);
            String line = leitor.readLine();
            for(int i=0; i<linha; i++){
                ler(line);
                line = leitor.readLine();
            }
            g.atribuir();
            System.out.println(g);
            //g.direcoes();
            //System.out.println(g.dijkstra());
        }catch(FileNotFoundException e){
            System.out.print("Arquivo não encontrado");
        }
        catch(Exception e){
            System.err.print("Erro: "+e);
        }
    }

    public void ler(String line){
        for(int i=0; i<line.length(); i++){
            g.createNodo(line.charAt(i));
        }
    }


}
