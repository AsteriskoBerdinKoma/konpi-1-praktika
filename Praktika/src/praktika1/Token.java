package praktika1;


///////////////////////////////////////////////////
// Praktika Raul Barrenak partzialki euskaratuta //
///////////////////////////////////////////////////

import java.io.IOException;


public class Token {
    String tokenIzena;
    String tokenMota;
    Analex analex;

    //Eraikitzailea
    public Token(String fitxIzena) throws IOException{

      analex= new Analex(fitxIzena);
      tokenIzena="";
      tokenMota="";
      
    }


    public String getTokenIzena() {
            return this.tokenIzena;        
    }


    public String getTokenMota() {
            return this.tokenMota;           
    }


    public void Next_token() {

      try{
        analex.Next_token();
        tokenIzena=analex.getTokenIzena();
        tokenMota=analex.getTokenMota();
        
        // iruzkinak saltatzen ditugu //
        // OHARRA: Analizatzaile lexikoa forgatzeko hurrengo lerro hau
        // iruzkin bezala utzi behar da
        //if (tokenMota.compareTo("komentarioak") == 0) { Next_token(); }

      }

      catch(IOException exception){
        System.out.println("ERROR, NextToken");
      }

      catch(Fitx_bukaera e){
        tokenIzena=";";
        tokenMota="EOF";
      }

    }

    public int Linea() {

      return analex.Linea();
      
    }
}
