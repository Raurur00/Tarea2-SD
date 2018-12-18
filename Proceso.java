public class Proceso 
{
    public static void main (String[] args)
    {
        String id = args[0];//ID proceso actual
        String[] id_vecinos = args[1].split(",");//IDs vecinos
        String isIni = args[2];//True si es iniciador
        ProcesoClient PClient;

        try{
            PClient = new ProcesoClient(Integer.parseInt(id), id_vecinos.length);
            //Si es iniciador
            if (isIni.equals("true"))
            {
                PClient.idCoordinador = PClient.id;
                //send id's
                for (int i = 0;i < id_vecinos.length; i++)
                {
                    PClient.invocar(id_vecinos[i]);
                }
            }

            //Espera algun mensaje de un vecino
            while (true)
            {
                ProcesoClient.listen(id, PClient);
                //Si recibio un mensaje con id mayor al actual
                if (PClient.sendExplorer)
                {
                    //envia mensajes a vecinos, sin incluir el vecino del msj entrante
                    for (int i = 0;i < id_vecinos.length; i++)
                    {
                        if (Integer.parseInt(id_vecinos[i]) != PClient.FL)
                            PClient.invocar(id_vecinos[i]);
                    }
                    PClient.sendExplorer = false;
                }
                if (PClient.sendEco)
                {
                    
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}