import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.google.gson.Gson;


public class App {
   

    public static void main(String[] args) throws Exception {

        //variáveis
        int i;
        float notaUser;
        String respUser;
        Scanner lerNotaUser = new Scanner(System.in);
        Scanner lerRespUser = new Scanner(System.in);        
       
        //emojis
        var estrela = "⭐";
        var feliz = "😀";
        var triste = "😢";
       
        //acessar a classe LerArquivos para retornar a key da API.      
        var chave = LerArquivos.getProp();
        String key = chave.getProperty("chave.codigo");      
       
        // Conexão com a API
        String url = "https://mocki.io/v1/"+key;
        URI endereco = URI.create(url);
        HttpClient cliente = HttpClient.newHttpClient();
        HttpRequest requisicao = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = cliente.send(requisicao, BodyHandlers.ofString());

        // Retornando os dados do JSON
        String body = response.body();

        /* JsonParser parser = new JsonParser(); */      

        // Utilizando Gson para parsear o JSON
        Gson gson = new Gson();
        Imdb f = gson.fromJson(body, Imdb.class);

        List<Map<String, String>> listarFilmes = f.items;

       
        // retorno dos dados
        for (Map<String,String> filme : listarFilmes) {
           
            var titulo = filme.get("title");
            var imagem = filme.get("image");
            var classificacao = filme.get("imDbRating");
           
            //conversão de String para float
            float nota = Float.parseFloat(classificacao);
           
            //converão de Float para Int
            int notaClassificacao = Math.round(nota);    

            //variáveis cores
            var negrito = "\u001b[1m";
            var italico = "\u001b[3m";
            var resetCor = "\u001B[0m";
            var fonteBranco = "\u001b[37m";
            var fundoVermelho = "\u001b[41m";
            var fundoVermelhoAzul = "\u001b[44m";
            var fonteVermelho = "\u001b[31m";
           
            //Exibindo Filme
            System.out.println(fonteVermelho + negrito + "Título: " + resetCor + negrito + titulo + resetCor  );
           
            //Exibindo link da imagem
            System.out.println(fonteVermelho + negrito + "Poster: " + resetCor + italico + negrito  + imagem + resetCor);          
           
            //Condições para exibir as personalizações da Classificação.
            if(notaClassificacao > 6){                
                System.out.println( fonteBranco + fundoVermelhoAzul + feliz + feliz +  "  Classificação: " + negrito  + notaClassificacao + "  " + feliz + feliz + resetCor);
               
            } else {
                System.out.println(fonteBranco + fundoVermelho + triste + triste + "Classificação: " + negrito  + notaClassificacao + "  " + triste + triste + resetCor);
            }

            for (i=0; i<notaClassificacao; i++){
                System.out.print(estrela);                
            }
           
            //Opção do Usuário avaliar o filme
            System.out.println("\n\nQual sua avaliação sobre o filme " + fonteVermelho + titulo + "?" + resetCor);
            notaUser = lerNotaUser.nextFloat();

            //Convertendo Float em inteiro
            int avaliacaoUser = Math.round(notaUser);


            if(avaliacaoUser > 6){                
                System.out.println(fonteBranco + fundoVermelhoAzul + feliz + feliz + fonteBranco + fundoVermelhoAzul + "  Avaliação do Usuário: " + negrito  + avaliacaoUser + "  " + feliz + feliz + resetCor);
               
            } else {
                System.out.println(fonteBranco + fundoVermelho + triste + triste + "  Avaliação do Usuário: "  + negrito  + avaliacaoUser +  "  " + triste + triste + resetCor);
            }

            for (i=0; i<avaliacaoUser; i++){
                System.out.print(estrela);                
            }
           
            System.out.println("\n");
           
            System.out.println("-------------------------------------------");

            // Dando opção para o usuário continuar ou não avaliando os filmes
            System.out.println(fonteVermelho + negrito + "\nDeseja avaliar outro filme ? " + resetCor + "S-Sim, N-Não" );
           
            respUser = lerRespUser.next();

            if ("N".equals(respUser) ||  "n".equals(respUser)){
                break;
            }
           
        }
    }
   
}