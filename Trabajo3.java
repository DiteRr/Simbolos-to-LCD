import java.util.*;
import java.io.*;
public class Trabajo3{
  public static void main(String[] args){
    char Digit[][] = GuardaDigitos();
    String FormatoCompleto = FormatoLCD(Digit);
    System.out.println(FormatoCompleto);  
  }

  //Busca en el archivo los digitos y los guarda en un array para trabajarlos.
  public static char[][] GuardaDigitos(){
    String NombreArchivo = ValidaNom();
    char Digitos[][] = BuscaDigitos(NombreArchivo);
    return Digitos;
  }

  ////////////////////////////////////////////////////////GuardaDigitos...
  //Pide y valida el nombre de el archivo donde se encuentran los digitos utilizando try catch{}.
  public static String ValidaNom(){
    boolean tof = true;
    String Nom = "";  
    Scanner OtOT = new Scanner(System.in);
    Exception Uno = new Exception("Nada ingresó, o sólo espacios!!");
    while(tof){
      try{
        System.out.print("Ingrese nombre del archivo: ");
        Nom = OtOT.nextLine().trim();
        if(Nom.length() == 0){
          throw Uno;
        }
 	else{
	  Scanner Arch = new Scanner(new File(Nom));
   	  tof = false;
        }
      }
      catch(FileNotFoundException ex){
    	System.out.println("Nombre de archivo inexistente!!. Deberá intentar de nuevo");
      }
      catch(Exception ex){
  	System.out.println(ex.getMessage());
      }
    }
    return Nom;
  }
  
  /////////////////////////////////////////////////////////////////////////GuardaDigitos...
  //Abre el Archivo, busca el largo de filas del array y luego las guarda en un array de dos dimensiones.
  public static char[][] BuscaDigitos(String Arch){
    int LargoArray = BuscaLargoArray(Arch);
    char Array[][] = GuardaDigitArray(Arch, LargoArray);
    return Array;
  }

  ///////////////////////////////////////////////////////////////////////BuscaDigitos...
  ///////////////////////////////////////////////////////////////////////
  //Busca el largo del Array buscando la ocurrencia del |0 0|, y utiliza try catch para manejar el archivo.
  public static int BuscaLargoArray(String Arch){
    String lineaActual = "";
    int k = -1;
    try{
      Scanner OtOA = new Scanner(new File(Arch));
      while(OtOA.hasNextLine()){
        lineaActual = OtOA.nextLine().trim();
        if(lineaActual != "0 0"){
    	  k = k + 1;
  	}
      }
      OtOA.close();
    }
    catch(FileNotFoundException ex){
      System.out.println("No se encuentra el Archivo, de seguro el error esta en el metodo anteanterior a este!!");
    }
    return k;
  }

  /////////////////////////////////////////////////////////////////////////BuscaDigitos...
  /////////////////////////////////////////////////////////////////////////
  //Abre el archivo con try catch y busca en el los digitos los que guarda en un array y lo envia a BuscaDigitos.
  public static char[][] GuardaDigitArray(String Arch, int L){
    int Colum = Arch.length()-2;
    char Array[][] = new char[L][Colum];
    int J = 0;
    String LineaActual = "";
    try{
      Scanner OtOA = new Scanner(new File(Arch));
      for(int i = 0; i < L; i++){
      LineaActual = OtOA.nextLine().trim();
        for(int j = 0; j < Colum; j++){
	  if(j == 0){
	    Array[i][j] = buscaCol(LineaActual, 0);
 	  }
	  else{
            J = j+1;
  	    Array[i][j] = buscaCol(LineaActual, J);
   	  }
	}
      }
      OtOA.close();
    }
    catch(FileNotFoundException ex){
      System.out.println("No esta bien escrito el nombre del archivo, error en el metodo de nomArchivo!!");
    }
    return Array;
  }

  //////////////////////////////////////////////////GuardaDigitosArray...
  //////////////////////////////////////////////////
  ///////////////////////////////////////////
  // Busca el primer digito asociado a la linea actual.
  public static char buscaCol(String L, int j){
    char Digit = L.charAt(j);           
    return Digit;
    }
  ////Este sera el String que contendra la forma en que se imprimirá los numeros.
  public static String FormatoLCD(char[][] Matriz){
    String FormatoCompleto ="";
    int filas = Matriz.length;
    int columnas = Matriz[0].length;
    String tam = "";
    int tamaño = 0;
    for(int z=0; z<filas;z++){
      String GuionesArriba="";String PalitosArriba =""; String GuionesEnMedio="";String PalitosAbajo="";String GuionesAbajo=""; String Formato = "";
      for(int j=0; j<columnas;j++){
        if(j==0){
         tam =Character.toString(Matriz[z][j]);
         tamaño= Integer.parseInt(tam);
       }
        else{
   	//La idea de cada metodo, es ir guardando los segmentos que contenga respectivo numero de la fila correspondiente, y  
        // la cantidad de cada segmento dependera del tamaño. 
        // Un numero se podria decir que  se subdivide en 5 filas de string.
          GuionesArriba =MetodoFormatoLCDGuionesArriba(Matriz[z][j],tamaño,GuionesArriba);
           // 1 fila del string  se encuentra solamente el segmento 'a'  
          PalitosArriba = MetodoFormatoLCDPalitosArriba(Matriz[z][j],tamaño,PalitosArriba);
   	 // 2 fila del String se encuentan los segmentos 'b' y 'f' 
       GuionesEnMedio =MetodoFormatoLCDGuionesEnMedio(Matriz[z][j],tamaño,GuionesEnMedio);
         // 3 fila del String se ecuentra solamente el segmento 'g'.
         PalitosAbajo = MetodoFormatoLCDPalitosAbajo(Matriz[z][j],tamaño,PalitosAbajo);
   	 // 4 fila del String se encuentran los segmento 'c' y 'e'.
        GuionesAbajo = MetodoFormatoLCDGuionesAbajo(Matriz[z][j],tamaño,GuionesAbajo);
         // 5 fila del String se encuentra solamente el segmento 'd'.
        }
      }
      PalitosArriba =MultiplicadorPalitosArriba(PalitosArriba, tamaño);
      PalitosAbajo = MultiplicadorPalitosAbajo(PalitosAbajo, tamaño);
      // Aqui adjunta todos los segmentos para cada fila correspondiente 
      Formato=GuionesArriba.concat("\n"+PalitosArriba+"\n"+GuionesEnMedio+"\n"+PalitosAbajo+"\n"+GuionesAbajo).concat("\n"+"\n");
      // Aqui almacena todos los segmentos.
      FormatoCompleto=FormatoCompleto.concat(Formato);
    }
    return FormatoCompleto;
}

  ////Los numeros que contengan el segmento 'a', lo imprimira con guiones y los que no con espacios.
 ////La cantidad de espacios o guiones que imprimira dependera del tamaño.
  public static String MetodoFormatoLCDGuionesArriba(char num,int tamaño,String GuionesArriba){
    int z =0;
    String a = "";
    if(num=='0' || num=='2' || num=='3' || num=='5' || num=='6' || num=='7' || num=='8' || num=='9' || num=='A' || num=='C' || num=='E' || num=='F'){
      for(z=0; z<tamaño;z++){ 
        a=a.concat("-");
      }
    }
    else{
      for(z=0; z<tamaño;z++){
        a=a.concat(" ");
      }
    } 
    if(GuionesArriba.length()==0){
      GuionesArriba ="  ".concat(a);
    }
    else{
      GuionesArriba =GuionesArriba.concat("     ").concat(a);
    }
    return GuionesArriba;
  }

  //Los numeros que contengan los segmentos 'b' y 'f', los imprimira con palitos y lo que no con espacios.
 ////La cantidad de espacios o guiones que imprimira dependera del tamaño.
 public static String MetodoFormatoLCDPalitosArriba(char num,int tamaño,String PalitosArriba){
    String b= "";String f="";String spaces ="";
    if(num=='0' || num=='4' || num=='5' || num=='6' || num=='8'|| num=='9' || num=='A' || num=='B' || num=='C' || num=='E' || num=='F' ){
      f="|";
    }
    else{
      f=" ";
    }
    if(num=='0' || num=='1' || num=='2' || num=='3' || num=='4'|| num=='7' || num=='8' || num=='9' || num=='A'|| num=='D' ){
      b="|";
    }
    else{
      b=" "; ;
    }
    for(int z=0; z<tamaño;z++){
      spaces=spaces.concat(" "); 
    }
    if(PalitosArriba.length()==0){
      PalitosArriba =" ".concat(f).concat(spaces).concat(b);
    }
    else{
      PalitosArriba=PalitosArriba.concat("   ").concat(f).concat(spaces).concat(b);
    }
    return PalitosArriba;
  }

  ///Los numeros que contengan el segmento 'g', los imprimira con guiones y los que no con espacios.
  public static String MetodoFormatoLCDGuionesEnMedio(char num,int tamaño,String GuionesEnMedio){
    String g=""; int z=0;
    if(num=='2' || num=='3' || num=='4' || num=='5' || num=='6'|| num=='8' || num=='9' || num=='A' || num=='B' || num=='D' || num=='E' || num=='F'){
      for(z=0; z<tamaño;z++){
        g=g.concat("-");
      }
    }
    else{
      for(z=0;z<tamaño;z++){
        g=g.concat(" ");
      }
    } 
    if(GuionesEnMedio.length()==0){
      GuionesEnMedio ="  ".concat(g);
    }
    else{
      GuionesEnMedio =GuionesEnMedio.concat("     ").concat(g);
    }
    return GuionesEnMedio;
  }

  //// Los numeros que contengan los segmento 'e' y 'c' los imprimira con palitos y los que no con espacios.
  public static String MetodoFormatoLCDPalitosAbajo(char num ,int tamaño,String PalitosAbajo){
    String e=""; String c="";String spaces="";
    if(num=='0' || num=='2' || num=='6' || num=='8'|| num=='A'|| num=='B' || num=='C' || num=='D' || num=='E' || num=='F'){
      e="|";
    }
    else{
      e= " ";
    }
    if(num=='0' || num=='1' || num=='3' || num=='4' || num=='5' || num=='6' || num=='7' || num=='8'|| num=='9'|| num=='A' || num=='B' || num=='D'){
      c="|";
    }
    else{
      c= " ";
    }
    for(int z=0; z<tamaño;z++){
      spaces=spaces.concat(" ");
    }
    if(PalitosAbajo.length()==0){
      PalitosAbajo=" ".concat(e).concat(spaces).concat(c);
    }
    else{
      PalitosAbajo=PalitosAbajo.concat("   ").concat(e).concat(spaces).concat(c);
    }
    return PalitosAbajo;
  }

  ////Guarda la Seccion de guiones que habra en el final:
  public static String MetodoFormatoLCDGuionesAbajo(char num,int tamaño,String GuionesAbajo){
    String d = "";int z=0;
    if(num=='0' || num=='2' || num=='3' || num=='5' || num=='6' || num=='8' || num=='9'|| num=='B' || num=='C' || num=='D' || num=='E'){
      for(z=0; z<tamaño;z++){ 
        d=d.concat("-");
      }
    }
    else{
      for( z=0; z<tamaño;z++){ 
        d=d.concat(" ");
      }
    }
    if(GuionesAbajo.length()==0){
      GuionesAbajo="  ".concat(d);
    }
    else{
      GuionesAbajo=GuionesAbajo.concat("     ").concat(d);
    }
    return GuionesAbajo;
  }

  ////Al tener los segmentos completos de "b" y "f", para una fila de numeros que nos da el archivo,
  ///hay que multiplicarlos a su respectivo tamaño.

  public static String MultiplicadorPalitosArriba(String PalitosArriba, int tamaño){
    String PalitosArribaCompleto = "";
    for(int z=0; z<tamaño;z++){
      if(z==tamaño-1){
        PalitosArribaCompleto =PalitosArribaCompleto.concat(PalitosArriba);
      }
      else{
        PalitosArribaCompleto =PalitosArribaCompleto.concat(PalitosArriba+"\n");
      }
    }
    return PalitosArribaCompleto;
  }

  ///// del anterior solo que para los segmentos 'c' y 'e'.
  public static String MultiplicadorPalitosAbajo(String PalitosAbajo, int tamaño){
    String PalitosAbajoCompleto = "";
    for(int z=0; z<tamaño;z++){
      if(z==tamaño-1){
        PalitosAbajoCompleto =PalitosAbajoCompleto.concat(PalitosAbajo);
      }
      else{
        PalitosAbajoCompleto =PalitosAbajoCompleto.concat(PalitosAbajo+"\n");
      }
    }
    return PalitosAbajoCompleto;
  }

   //Imprime la matriz para verla antes de graficarla:
  public static void ImprimirMatriz(int [][] Matriz){
    int filas = Matriz.length;
    int columnas = Matriz[0].length;
    for(int z=0; z<filas; z++){
      for(int j=0; j<columnas; j++){
        System.out.print(Matriz[z][j]);
      }
      System.out.println(); 
    } 
  }
}

