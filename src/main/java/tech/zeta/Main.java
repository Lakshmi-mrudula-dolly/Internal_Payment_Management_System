package tech.zeta;

import tech.zeta.util.DBUtil;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        Connection connection= DBUtil.getConnection();
        if(connection!=null) System.out.println("Sucessful");
        else System.out.println("Failed");
    }
}
