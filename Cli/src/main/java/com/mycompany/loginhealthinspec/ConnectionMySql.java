package com.mycompany.loginhealthinspec;

import org.apache.commons.dbcp2.BasicDataSource;

public class ConnectionMySql {
     private BasicDataSource dataSource;

    public ConnectionMySql() {
        dataSource = new BasicDataSource();
        

        dataSource​.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource​.setUrl("jdbc:mysql://172.20.20.4/health?useTimezone=true&serverTimezone=UTC");
        dataSource​.setUsername("root");
        dataSource​.setPassword("urubu100");
    }

    public BasicDataSource getDataSource() {    
        return dataSource;
    }
}
