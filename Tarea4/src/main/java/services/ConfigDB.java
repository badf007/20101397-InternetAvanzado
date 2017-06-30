package services;

import org.h2.tools.Server;

import java.sql.SQLException;

/**
 * Created by Leny96 on 30/6/2017.
 */
public class ConfigDB {
    private static ConfigDB instancia;



    public static ConfigDB getInstancia(){
        if(instancia == null){
            instancia=new ConfigDB();
        }
        return instancia;
    }

    public void startDb() {
        try {
            Server.createTcpServer("-tcpPort",
                    "4500",
                    "-tcpAllowOthers",
                    "-tcpDaemon").start();
        }catch (SQLException ex){
            System.out.println("Problema con la base de datos: "+ex.getMessage());
        }
    }

}
