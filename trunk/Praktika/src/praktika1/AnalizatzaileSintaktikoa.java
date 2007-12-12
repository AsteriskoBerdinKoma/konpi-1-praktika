package praktika1;

import java.io.IOException;
import java.util.Vector;

public class AnalizatzaileSintaktikoa {

	public class Asintak{

		int id;
	  Bit_kodea codigo;
	  Vector erantzuna;
	  Token look;


//////////////////////////////////////////////////////////////////////////////	/
////////////////////////////	/ KLASE LAGUNTZAILEAK /////////////////////////////
//////////////////////////////////////////////////////////////////////////////	/



	         public class A {   // Adierazpena

	             Atrib adierazpena_true;
	             Atrib adierazpena_false;


	                 A() {
	                     adierazpena_true = new Atrib();
	                     adierazpena_false = new Atrib();
	                     }

	                 
	                 public Atrib getAdierazpena_true() {
	                     
	                         return adierazpena_true;
	                 }

	                 public Atrib getAdierazpena_false() {
	                     
	                         return adierazpena_false;
	                 }
	                 
	                 public void setAdierazpena_true(int erreferentzia) {
	                        
	                        adierazpena_true.hasi_lista(erreferentzia);
	                 }

	                 public void setAdierazpena_false(int erreferentzia) {
	                    
	                        adierazpena_false.hasi_lista(erreferentzia);
	                 }

	         }//end A



	        public class Atrib {

	                Vector lcont;

	                Atrib() {
	                        lcont= new Vector();
	                }

	                public Vector lcont() {
	                        return lcont;
	                }
	                public void hasi_lista(Integer ref){
	                        lcont.addElement(ref);
	                }
	                public void Copiar_lcont(Vector v){
	                        lcont=v;
	                }
	        }//end Atrib

////////////////////////////	 END KLASE LAGUNTZAILEAK ////////////////////////////


	  Asintak (String nom_fich) throws IOException{//Constructor
	    id= 0;
	    look= new Token(nom_fich);
	    look.Next_token(); // lehenengo tokena eskuratu
	    erantzuna = new Vector();
	    codigo = new Bit_kodea();

	  }//end Asintak


//////////////////////////////////////////////////////////////////////////////	/
////////////////////////////	/ FUNTZIO LAGUNTZAILEAK ////////////////////////////
//////////////////////////////////////////////////////////////////////////////	/


	        private void Emparejar(String token) {
	        	if (!look.getTokenMota().equals(token) && !look.getTokenIzena().equals(token)){
	                   System.out.print("Errore sintaktikoa lerro zenbakia: "+look.Linea()+".");
	                   System.out.println(" Espero genuen: "+token+" eta hauxe da daukaguna: "+look.getTokenIzena());
	                }
	            look.Next_token();
	        }//end Emparejar


	        private void erazagupenak_gehitu(Vector lista, String tipo){ // Generar_id == erazagupenak_gehitu

	           String elemento;

	           while (!(lista.isEmpty())){
	             elemento=lista.firstElement().toString();
	             codigo.Ag_gehitu(tipo+" "+elemento+";");
	             lista.removeElementAt(0);
	           }

	        }   //  end erazagupenak_gehitu


	         private void parametro_zerrenda_gehitu(Vector ident_zerrenda_izenak, String Parmota_mota, String mota_mota){ // Berria //

	          String azken_param_mota = new String();
	      
	          azken_param_mota = Parmota_mota + "_" + mota_mota; 
	          erazagupenak_gehitu(ident_zerrenda_izenak, azken_param_mota);

	         }  //  end parametro_zerrenda_gehitu


	        private Vector Bildu(Vector v1, Vector v2) {   

	            while (!(v2.isEmpty())){
	              v1.addElement(v2.firstElement());
	              v2.removeElementAt(0);
	            }
	            return v1;

	        }   //  end Bildu


	        private String Nuevoid() {

	            String nid;

	            nid="_t"+id;
	            id++;
	            return nid;

	        }//end Nuevoid


//////////////////////////	/ END FUNTZIO LAGUNTZAILEAK //////////////////////////


//////////////////////////////////////////////////////////////////////////////	/
////////////////	/ S.Z.I.E.n OINARRITUTAKO ITZULTZAILEAREN KODEA //////////////////
//////////////////////////////////////////////////////////////////////////////	/


	        private void Aukerazko_id() { 

	          String id_izena;

	          if (look.getTokenMota().equals("identificador")) {
	              Emparejar("identificador");
	          }

	        }//end Aukerazko_id


	        private String Aldagaia()   {
	        
	            String aldagaia_izena, id_izena;

	            id_izena = look.Str_token();
	            Emparejar("identificador");
	            aldagaia_izena = id_izena;
	            
	            return aldagaia_izena;
	        
	        }   //  end Aldagaia

	        private String Faktore()    {
	        
	        	String id_izena, zenb_osoko_izena, zenb_erreal_izena, adierazpen_bakuna_izena;
	    		String faktore_izena = "";

	    		if (look.getTokenMota().equals("ID")) {
	    			id_izena = look.getTokenIzena();
	    			Match("ID");
	    			faktore_izena = id_izena;
	    		} else if (look.getTokenMota().equals("OSOKOA")) {
	    			zenb_osoko_izena = look.getTokenIzena();
	    			Match("OSOKOA");
	    			faktore_izena = zenb_osoko_izena;
	    		} else if (look.getTokenMota().equals("ERREALA")) {
	    			zenb_erreal_izena = look.getTokenIzena();
	    			Match("ERREALA");
	    			faktore_izena = zenb_erreal_izena;
	    		} else {
	    			Match("(");
	    			adierazpen_bakuna_izena = Adierazpen_bakuna();
	    			Match(")");
	    			faktore_izena = adierazpen_bakuna_izena;
	    		}

	    		return faktore_izena;	        
	        }   //end Faktore
	    

	        private String Gaia_prima(String gaia_prima_hi) {
	        
	        String gaia_prima_izena, gaia_prima1_hi, faktore_izena, gaia_prima1_izena;

	            if (look.T_token().equals("*")) {
	              Emparejar("*");
	              faktore_izena = Faktore();
	              gaia_prima1_hi = Nuevoid();
	              codigo.Ag_gehitu(gaia_prima1_hi+":= "+gaia_prima_hi+" * "+faktore_izena+";");
	              gaia_prima1_izena = Gaia_prima(gaia_prima1_hi);
	              gaia_prima_izena = gaia_prima1_izena;
	            }

	            else if (look.T_token().equals("/")) {
	                Emparejar("/");
	                faktore_izena = Faktore();
	                gaia_prima1_hi = Nuevoid();
	                codigo.Ag_gehitu(gaia_prima1_hi+":= "+gaia_prima_hi+" / "+faktore_izena+";");
	                gaia_prima1_izena = Gaia_prima(gaia_prima1_hi);
	                gaia_prima_izena = gaia_prima1_izena;
	            }
	            else { // produkzio hutsa
	               gaia_prima_izena = gaia_prima_hi;
	             }
	        
	        return gaia_prima_izena;
	        
	        }   //  end Gaia_prima


	        private String Gaia()   {
	        
	            String gaia_izena, gaia_prima_hi, faktore_izena, gaia_prima_izena;
	            
	            faktore_izena = Faktore();
	            gaia_prima_hi = faktore_izena;
	            gaia_prima_izena = Gaia_prima(gaia_prima_hi);
	            gaia_izena = gaia_prima_izena;
	        
	        return gaia_izena;
	        }   //  end Gaia

	        private String Ad_bakuna_prima(String ad_bakuna_prima_hi)    {
	        
	                String ad_bakuna_prima_izena, ad_bakuna_prima1_hi, gaia_izena, ad_bakuna_prima1_izena;


	                if (look.getTokenMota().equals("+")) {
	                  Emparejar("+");
	                  gaia_izena = Gaia();
	                  ad_bakuna_prima1_hi = Nuevoid();
	                  codigo.Ag_gehitu(ad_bakuna_prima1_hi+":= "+ad_bakuna_prima_hi+" + "+gaia_izena+";");
	                  return Ad_bakuna_prima(ad_bakuna_prima1_hi);
	                }
	                else if (look.getTokenMota().equals("-")) {
	                    Emparejar("-");
	                    gaia_izena = Gaia();
	                    ad_bakuna_prima1_hi = Nuevoid();
	                    codigo.Ag_gehitu(ad_bakuna_prima1_hi+":= "+ad_bakuna_prima_hi+" - "+gaia_izena+";");
	                    return Ad_bakuna_prima(ad_bakuna_prima1_hi);
	                }
	                else {
	                    return ad_bakuna_prima_hi;
	                }
	        }   //  end Ad_bakuna_prima

	        private String Adierazpen_bakuna() {  

	                String adierazpen_bakuna_izena, ad_bakuna_prima_hi, gaia_izena, ad_bakuna_prima_izena;
	                
	                gaia_izena = Gaia();
	                ad_bakuna_prima_hi = gaia_izena;
	                ad_bakuna_prima_izena = Ad_bakuna_prima(ad_bakuna_prima_hi);
	                adierazpen_bakuna_izena = ad_bakuna_prima_izena;

	                return adierazpen_bakuna_izena;

	        }   //  end Adierazpen_bakuna


	        private String Erag_erl()   {
	        
	            String erag_erl_mota;

	            erag_erl_mota=look.Str_token();

	            if (erag_erl_mota.equals("=")) Emparejar("=");
	            else if (erag_erl_mota.equals(">")) Emparejar(">");
	            else if(erag_erl_mota.equals("<")) Emparejar("<");
	            else if(erag_erl_mota.equals(">=")) Emparejar(">=");
	            else Emparejar("<=");
	  
	            return erag_erl_mota;
	        
	        }   // end Erag_erl

	        private A Adierazpena() {
	        
	            A adierazpena  = new A();
	            int erreferentzia1, erreferentzia2;
	            String adierazpen_bakuna1_izena, adierazpen_bakuna2_izena, erag_erl_mota;
	            
	            adierazpen_bakuna1_izena = Adierazpen_bakuna();
	            erag_erl_mota = Erag_erl();
	            adierazpen_bakuna2_izena = Adierazpen_bakuna();
	           
	            erreferentzia1 = codigo.Obten_ref_codigo();
	            adierazpena.setAdierazpena_true(erreferentzia1);
	            codigo.Ag_gehitu("if "+adierazpen_bakuna1_izena+" "+ erag_erl_mota+" "+adierazpen_bakuna2_izena+" goto ");
	            erreferentzia2 = codigo.Obten_ref_codigo();
	            adierazpena.setAdierazpena_false(erreferentzia2);            
	            codigo.Ag_gehitu("goto ");  
	            
	            return adierazpena;
	            
	        }   // end Adierazpena

	         private Atrib S_break() {

	                 Atrib sententzia_break, sententzia_prima_break;

	                 Emparejar("break");
	                 sententzia_prima_break = Sententzia_prima();
	                 Emparejar(";");
	                 sententzia_break = sententzia_prima_break;
	                 
	                 return sententzia_break;

	         }//end S_break


	        private Atrib Sententzia_prima()    {
	        
	                Atrib sententzia_prima_break, adierazpena_true, adierazpena_false;
	                A adierazpena;
	                int erreferentzia;
	                
	                if (look.T_token().equals("if"))
	                  {
	                  Emparejar("if");
	                  adierazpena = Adierazpena();
	                  adierazpena_true = adierazpena.getAdierazpena_true();
	                  adierazpena_false = adierazpena.getAdierazpena_false();
	                  sententzia_prima_break = adierazpena_true;
	                  erreferentzia = codigo.Obten_ref_codigo();
	                  codigo.Ag_osatu(adierazpena_false.lcont(),erreferentzia);
	                  }
	                else {
	                  // hasi_lista(lortu_erref())
	                  erreferentzia = codigo.Obten_ref_codigo();
	                  sententzia_prima_break = new Atrib();
	                  sententzia_prima_break.hasi_lista(erreferentzia);
	                  // end hasi_lista(lortu_erref())
	                  codigo.Ag_gehitu("goto ");
	                }
	            
	                return sententzia_prima_break;
	        
	        } //end Sententzia_prima

	        private Atrib S_get() {

	                Atrib sententzia_break = new Atrib();
	                String aldagaia_izena;

	                Emparejar("get");
	                Emparejar("(");
	                aldagaia_izena = Aldagaia();
	                Emparejar(")");
	                Emparejar(";");
	                codigo.Ag_gehitu("read "+aldagaia_izena+";");
	                
	                return sententzia_break;

	        } //end S_get


	        private Atrib S_put_line() {

	                Atrib sententzia_break = new Atrib();
	                String adierazpen_bakuna_izena;

	                Emparejar("put_line");
	                Emparejar("(");
	                adierazpen_bakuna_izena = Adierazpen_bakuna();
	                Emparejar(")");
	                Emparejar(";");
	                codigo.Ag_gehitu("write "+adierazpen_bakuna_izena+";");
	                codigo.Ag_gehitu("writeln;");
	                
	                return sententzia_break;

	        }//end S_put_line


	        private Atrib S_repeat() {

	                int M1_erref, M2_erref, M3_erref;
	                A adierazpena;
	                Atrib adierazpena_true, adierazpena_false;
	                Atrib sententzia_break = new Atrib();
	                Atrib sententzia_zerrenda_break = new Atrib();

	                Emparejar("repeat");
	                M1_erref = M();
	                Emparejar("{");
	                sententzia_zerrenda_break = Sententzia_zerrenda();
	                Emparejar("}");
	                Emparejar("while");
	                M2_erref = M();                
	                adierazpena = Adierazpena();
	                adierazpena_true = adierazpena.getAdierazpena_true();
	                adierazpena_false = adierazpena.getAdierazpena_false();
	                Emparejar(";");
	                M3_erref = M();
	                codigo.Ag_osatu(adierazpena_true.lcont(),M1_erref);
	                codigo.Ag_osatu(adierazpena_false.lcont(),M3_erref);
	                codigo.Ag_osatu(sententzia_zerrenda_break.lcont(),M2_erref);

	                return sententzia_break;

	        }//end S_repeat


	        private int M()     {
	        
	            int M_erref;
	            
	            M_erref = codigo.Obten_ref_codigo();
	            
	            return M_erref;
	        
	        }   // end M


	         private Atrib N()     {
	         
	             int erreferentzia;
	             Atrib N_hur = new Atrib();
	             
	             erreferentzia = codigo.Obten_ref_codigo();
	             // hasi_lista //
	             N_hur.hasi_lista(erreferentzia);  
	             codigo.Ag_gehitu("goto ");
	             
	             return N_hur;
	         
	         }   // end N

	        private Atrib S_if() {


	                int M1_erref, M2_erref, M3_erref;
	                A adierazpena;
	                Atrib adierazpena_true, adierazpena_false;
	                Atrib sententzia_break = new Atrib();
	                Atrib sententzia_zerrenda1_break = new Atrib();
	                Atrib sententzia_zerrenda2_break = new Atrib();
	                Atrib N_hur = new Atrib();
	                
	                Atrib lis1, lis2, lis_if;
	                lis1=new Atrib();
	                lis2=new Atrib();
	                lis_if=new Atrib();

	                Emparejar("if");
	                adierazpena = Adierazpena();
	                adierazpena_true = adierazpena.getAdierazpena_true();
	                adierazpena_false = adierazpena.getAdierazpena_false();
	                Emparejar("then");
	                M1_erref = M();
	                Emparejar("{");
	                sententzia_zerrenda1_break = Sententzia_zerrenda();
	                Emparejar("}");
	                Emparejar("else");
	                N_hur = N();
	                M2_erref = M();
	                Emparejar("{");
	                sententzia_zerrenda2_break = Sententzia_zerrenda();
	                Emparejar("}");
	                Emparejar(";");
	                M3_erref = M();    
	                codigo.Ag_osatu(adierazpena_true.lcont(),M1_erref);
	                codigo.Ag_osatu(adierazpena_false.lcont(),M2_erref);
	                codigo.Ag_osatu(N_hur.lcont(),M3_erref);
	                sententzia_break.Copiar_lcont(Bildu(sententzia_zerrenda1_break.lcont(), sententzia_zerrenda2_break.lcont()));
	                return sententzia_break;

	        }//end S_if

	        private Atrib Sententzia()   
	          {

	                Atrib sententzia_break = new Atrib();
	                String aldagaia_izena, adierazpen_bakuna_izena;

	                if (look.T_token().equals("identificador"))
	                  {
	                  aldagaia_izena = Aldagaia(); // aldagaia_izena = look.Str_token();
	                  Emparejar(":=");
	                  adierazpen_bakuna_izena = Adierazpen_bakuna();
	                  Emparejar(";");
	                  codigo.Ag_gehitu(aldagaia_izena+":="+adierazpen_bakuna_izena+";");
	                  }
	                else if (look.Str_token().equals("if"))  sententzia_break = S_if();
	                else if (look.Str_token().equals("repeat")) sententzia_break = S_repeat();
	                else if (look.Str_token().equals("break")) sententzia_break = S_break();
	                else if (look.Str_token().equals("get")) sententzia_break = S_get();
	                else if (look.Str_token().equals("put_line")) sententzia_break = S_put_line();
	                return sententzia_break;
	          }//end Sententzia


	        private Atrib Sententzia_zerrenda() { 

	                Atrib sententzia_zerrenda_break = new Atrib();
	                Atrib sententzia_break = new Atrib();
	                Atrib sententzia_zerrenda1_break = new Atrib();
	                
	                // int m_ref;
	                if ((look.Str_token().equals("if")) || (look.Str_token().equals("repeat")) ||
	                    (look.Str_token().equals("break")) || (look.Str_token().equals("get")) ||
	                    (look.Str_token().equals("put_line")) || (look.T_token().equals("identificador")))
	                    {
	                    sententzia_break = Sententzia(); 
	                    sententzia_zerrenda1_break = Sententzia_zerrenda(); 
	                    sententzia_zerrenda_break.Copiar_lcont(Bildu(sententzia_break.lcont(),sententzia_zerrenda1_break.lcont()));
	                    }
	                return sententzia_zerrenda_break; 

	        }//end Sententzia_zerrenda
	  
	        private void Parametro_zerrendaren_bestea() {

	    		String par_mota_balioa = new String();
	    		String mota_mota = new String();
	    		Vector<String> ident_zerrenda_izenak = new Vector<String>();

	    		if (look.getTokenIzena().equals(";")) {
	    			match(";");
	    			ident_zerrenda_izenak = Ident_zerrenda();

	    			match(":");

	    			par_mota_balioa = Par_mota();
	    			mota_mota = Mota();

	    			parametroak_gehitu(par_mota_balioa,	mota_mota, ident_zerrenda_izenak);

	    			Parametro_zerrendaren_bestea();

	    		}

	    	}// end Parametro_zerrendaren_bestea

	    	private String Par_mota_prima() {

	    		String par_mota_prima_balioa = new String("");

	    		if (look.getTokenIzena().equals("out")) {
	    			match("out");
	    			par_mota_prima_balioa = "ref_";
	    		} else
	    			par_mota_prima_balioa = "val_";

	    		return par_mota_prima_balioa;

	    	}// end Par_mota_prima

	    	private String Par_mota() {

	    		String par_mota_balioa = new String();

	    		if (look.getTokenIzena().equals("in")) {
	    			match("in");
	    			par_mota_balioa = Par_mota_prima();
	    		} else { // if (look.getTokenIzena().equals("out"))
	    			match("out");
	    			par_mota_balioa = "ref_";
	    		}

	    		return par_mota_balioa;

	    	}// end Par_mota

	    	private void Parametro_zerrenda() {

	    		String par_mota_balioa = new String();
	    		String mota_mota = new String();
	    		Vector<String> ident_zerrenda_izenak = new Vector<String>();

	    		ident_zerrenda_izenak = Ident_zerrenda();

	    		match(":");

	    		par_mota_balioa = Par_mota();
	    		mota_mota = Mota();

	    		parametroak_gehitu(par_mota_balioa, mota_mota, ident_zerrenda_izenak);

	    		Parametro_zerrendaren_bestea();

	    	}// end Parametro_zerrenda

	    	private void Argumentuak() {

	    		look.Next_token();
	    		if (look.getTokenIzena().equals("(")) {
	    			match("(");
	    			Parametro_zerrenda();
	    			match(")");
	    		}

	    	}// end Argumentuak


	        private void Goiburukoa() {   

	          String izena = new String();

	          Emparejar("procedure");
	          Emparejar("ID");
	          izena = look.getTokenIzena();
	          codigo.Ag_gehitu("proc "+izena+";");
	          Argumentuak();
	          Emparejar("is");
	        }//end Goiburukoa


	        private void Azpiprogramaren_erazagupena() { 


	          Goiburukoa();
	          Erazagupenak();
	          Emparejar("begin");
	          Sententzia_zerrenda();
	          Emparejar("end_procedure");
	          Aukerazko_id();
	          Emparejar(";");
	          codigo.Ag_gehitu(";");

	        }//end Azpiprogramaren_erazagupena


	        private void Azpiprogramen_erazagupena(){ 

	          if (look.getTokenIzena().equals("procedure")){
	            Azpiprogramaren_erazagupena();
	            Azpiprogramen_erazagupena();
	          }

	        }//end Azpiprogramen_erazagupena


	        private String Mota() {

	                String mota_mota = new String();

	                if (look.getTokenIzena().equals("OSOKOA")) {
	                    Emparejar("OSOKOA");
	                    mota_mota = "int";
	                }
	                else {
	                    //if (look.Str_token().equals("float")) 
	                    Emparejar("ERREALA");
	                    mota_mota = "real";
	                }
	                return mota_mota;
	        }//end Mota


	        private Vector Ident_zerrendaren_bestea() { 

	                Vector ident_zerrendaren_bestea_izenak = new Vector(); // ident_zerrendaren_bestea.izenak := lista_hutsa() //
	                String id_izena;

	                if (look.getTokenIzena().equals(",")) {
	                    Emparejar(",");
	                    id_izena = look.getTokenIzena();
	                    Emparejar("ID");
	                    ident_zerrendaren_bestea_izenak = Ident_zerrendaren_bestea();
	                    ident_zerrendaren_bestea_izenak.insertElementAt(id_izena,0); // gehitu(id.izena, ident_zerrendaren_bestea.izenak) //
	                }

	                return ident_zerrendaren_bestea_izenak;

	        }//end Ident_zerrendaren_bestea


	        private Vector Ident_zerrenda() { 

	                String id_izena;
	                Vector ident_zerrenda_izenak = new Vector();

	                   id_izena = look.getTokenIzena();
	                   Emparejar("ID");
	                   ident_zerrenda_izenak = Ident_zerrendaren_bestea();
	                   ident_zerrenda_izenak.insertElementAt(id_izena,0);  // gehitu(id.izena, ident_zerrendaren_bestea.izenak) //

	                return ident_zerrenda_izenak;

	        }//end Ident_zerrenda



	 private void Eraz_bestea() { 

	         Vector ident_zerrenda_izenak = new Vector();
	         String mota_mota;

	         if ((look.getTokenIzena().equals("var")))
	              {
	               Emparejar("var");
	               ident_zerrenda_izenak = Ident_zerrenda();
	               Emparejar(":");
	               mota_mota = Mota();
	               Emparejar(";");
	               erazagupenak_gehitu(ident_zerrenda_izenak,mota_mota);
	               Eraz_bestea();

	            }

	 }//end Eraz_bestea


	 private void Erazagupenak() { 

	         Vector ident_zerrenda_izenak = new Vector();
	         String mota_mota;

	         if ((look.getTokenIzena().equals("var")))
	              {
	               Emparejar("var");
	               ident_zerrenda_izenak = Ident_zerrenda();
	               Emparejar(":");
	               mota_mota = Mota();
	               Emparejar(";");
	               erazagupenak_gehitu(ident_zerrenda_izenak,mota_mota);
	               Eraz_bestea();
	            }

	 }//end Erazagupenak

	 
	 
	        public Vector Programa() {

	            String izena;

	                Emparejar("program");
	                Emparejar("ID");
	                izena=look.getTokenIzena();
	                Emparejar("identificador");
	                Emparejar("is");
	                codigo.Ag_gehitu("prog "+izena+";");

	                Erazagupenak(); 

	                Azpiprogramen_erazagupena(); 
	                Emparejar("begin");
	                Sententzia_zerrenda(); 
	                Emparejar("end_program");
	                Aukerazko_id(); 
	                Emparejar(";");
	                codigo.Ag_gehitu("halt;");

	                erantzuna = codigo.Devolver_codigo();
	                return erantzuna;

	         }//end Programa

//////////////////////////	/ END ITZULTZAILEAREN KODEA //////////////////////////


	}//end Asintak

