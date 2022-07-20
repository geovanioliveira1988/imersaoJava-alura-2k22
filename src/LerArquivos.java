import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class LerArquivos {
    public static Properties getProp() throws IOException{
        Properties key = new Properties();
        FileInputStream file = new FileInputStream("C:/Alura/alura-dia1/alura-stickers/properties/keyApi.properties");
        key.load(file);
        return key;        
    }    
}