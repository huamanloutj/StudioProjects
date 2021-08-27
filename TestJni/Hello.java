public class Hello{
   public static void main(String[] args){
       String libraryDirs = System.getProperty("java.library.path");

       System.out.println(libraryDirs + " ");
   }
}
