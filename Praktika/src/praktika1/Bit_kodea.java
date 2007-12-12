package praktika1;

///////////////////////////////////////////////////
// Praktika Raul Barrenak partzialki euskaratuta //
///////////////////////////////////////////////////

import java.util.Vector;


public class Bit_kodea {

   private Vector vector;
   private int sig_inst;
   private String inst;



   Bit_kodea(){
   
      vector= new Vector();
      sig_inst = 0;
      
   }//end Bit_kodea


   final public void Ag_gehitu (String ins){  
   
      vector.addElement(sig_inst+":"+ins);
      sig_inst++;
      
   }//end Ag_gehitu


   final public void Ag_osatu (Vector lista_referencias,int referencia){  

	    int Indice;
	    String n_inst;


	    while ((lista_referencias.size()!=0)){
	    
    		Indice=((Integer)lista_referencias.firstElement()).intValue(); 	
        inst=(String)vector.elementAt(Indice);
        n_inst=(inst+referencia+";");
        vector.removeElementAt(Indice);
	    	vector.insertElementAt(n_inst,Indice);
        lista_referencias.removeElementAt(0);	    
	    }
      
   }//end Ag_osatu

   final public int Obten_ref_codigo(){
   
	   return(sig_inst);
     
   }//end Obten_ref_codigo



   public Vector Devolver_codigo() {
   
      return vector;	
      
   }//end Devolver_codigo

}//end Bit_kodea