public class Proceso 
{
    public int id;
    public int num_vecinos;
    public int exp_recibidos;

    public Proceso(int id, int num_vecinos)
    {
        this.id = id;
        this.num_vecinos = num_vecinos;
        this.exp_recibidos = 0;
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
                Proceso P = new Proceso(Integer.parseInt(id), id_vecinos.length);
                ProcesoServer P1 = new ProcesoServer(Integer.parseInt(id), id_vecinos.length);
                ProcesoClient P2 = new ProcesoClient(Integer.parseInt(id));
                InterfaceProceso k = new ProcesoServer(Integer.parseInt(id), id_vecinos.length);

                //espera algun mensaje de un vecino
                //while (P1.exp_recibidos == k.getExpRec())
                while (P.exp_recibidos < id_vecinos.length)
                {
                    P1.listen(id, k);
                    //si recibio un mensaje con id mayor al actual
                    if (k.ifgetMensaje())
                    {
                        P.id = k.getId();
                        P.exp_recibidos++;

                        //envia mensajes a vecinos, sin incluir el vecino del msj entrante
                        for (int i = 0;i < id_vecinos.length; i++)
                        {
                            P2.invocar(id_vecinos[i]);
                        }
                    }
                }
                
            }   catch (Exception e) {
               e.printStackTrace();
            } 
            
        }

        else if (isIni.equals("true"))
        {
            try
            {
                Proceso P = new Proceso(Integer.parseInt(id), id_vecinos.length);
                ProcesoClient P1 = new ProcesoClient(Integer.parseInt(id));
                ProcesoServer P2 = new ProcesoServer(Integer.parseInt(id), id_vecinos.length);
                InterfaceProceso k = new ProcesoServer(Integer.parseInt(id), id_vecinos.length);

                //send id's
                for (int i = 0;i < id_vecinos.length; i++)
                {
                    P1.invocar(id_vecinos[i]);
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