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
                PClient.iniciador = true;
                //El iniciador envia explorer a sus vecinos
                for (int i = 0;i < id_vecinos.length; i++)
                {
                    PClient.invocar(id_vecinos[i]);
                }
            }

            //Espera algun mensaje de un vecino
            while (true)
            {
                ProcesoClient.listen(id, PClient);
                if (PClient.sendExplorer)
                {
                    //Aqui se envian explorer a los vecinos
                    for (int i = 0;i < id_vecinos.length; i++)
                    {
                        if (Integer.parseInt(id_vecinos[i]) != PClient.FL)
                            PClient.invocar(id_vecinos[i]);
                    }
                    PClient.sendExplorer = false;
                }
                if (PClient.sendEco)
                {
                    //Aqui se envia el eco
                    PClient.invocarEco(Integer.toString(PClient.FL));
                    PClient.sendEco = false;
                }
                if (PClient.coordinador){
                    //Aqui se hace la conexion al server cuando se elige coordinador
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}