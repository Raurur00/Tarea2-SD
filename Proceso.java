public class Proceso 
{
    public int id;

    public Proceso(int id)
    {
        id = this.id;
    }

    public static void main (String[] args)
    {
        String id = args[0];
        String[] id_vecinos = args[1].split(",");
        String isIni = args[2];

        if (isIni.equals("false"))
        {
            try
            {
                ProcesoServer P = new ProcesoServer();
                P.listen(id);
            }   catch (Exception e) {
               e.printStackTrace();
            }  
            
        }

        else if (isIni.equals("true"))
        {
            try
            {
                ProcesoClient P = new ProcesoClient();
                for (int i = 0;i < id_vecinos.length; i++)
                {
                    P.invocar(id_vecinos[i]);
                }
                
            }   catch (Exception e) {
               e.printStackTrace();
            }
        }

        else
        {
            System.out.println("tercer argumento invalido");
        }
    }
}