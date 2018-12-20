public class Proceso
{
    public static void main (String[] args)
    {
        String id = args[0];/*ID proceso actual */
        String[] id_vecinos = args[1].split(",");/*IDs vecinos */
        String isIni = args[2];/*True si es iniciador */
        ProcesoClient PClient;
        String ruta_archivo = "";
        String ip_server = "";


        try{
            PClient = new ProcesoClient(Integer.parseInt(id), id_vecinos.length);
            /*Si es iniciador */
            if (isIni.equals("true"))
            {
                ruta_archivo = args[3];
                ip_server = args[4];
                PClient.idCoordinador = PClient.id;
                PClient.iniciador = true;
                /*El iniciador envia explorer a sus vecinos */
                for (int i = 0;i < id_vecinos.length; i++)
                {
                    PClient.invocar(id_vecinos[i], 0, "");
                }
            }

            /*Espera algun mensaje de un vecino */
            while (true)
            {
                ProcesoClient.listen(id, PClient);
                if (PClient.coordinador)
                {
                    PClient.Descifrar(ruta_archivo,ip_server);
                    System.out.println(PClient.mensajeDescifrado);
                     for (int i = 0;i < id_vecinos.length; i++)
                    {
                        PClient.invocar(id_vecinos[i], 2, PClient.mensajeDescifrado);
                    }
                    break;

                } else if (PClient.sendMensaje){
                    for (int i = 0;i < id_vecinos.length; i++)
                    {
                        if (Integer.parseInt(id_vecinos[i]) != PClient.FL)
                            PClient.invocar(id_vecinos[i], 2,PClient.mensajeDescifrado);
                    }
                    PClient.sendMensaje = false;
                }
                if (PClient.sendExplorer)
                {
                    /*Aqui se envian explorer a los vecinos */
                    for (int i = 0;i < id_vecinos.length; i++)
                    {
                        if (Integer.parseInt(id_vecinos[i]) != PClient.FL)
                            PClient.invocar(id_vecinos[i], 0, "");
                    }
                    PClient.sendExplorer = false;
                }
                if (PClient.sendEco)
                {
                    /*Aqui se envia el eco */
                    PClient.invocar(Integer.toString(PClient.FL), 1, "");
                    PClient.sendEco = false;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}