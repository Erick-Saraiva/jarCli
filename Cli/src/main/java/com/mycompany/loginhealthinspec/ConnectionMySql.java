package com.mycompany.loginhealthinspec;

import org.apache.commons.dbcp2.BasicDataSource;

public class ConnectionMySql {
     private BasicDataSource dataSource;

    public ConnectionMySql() {
        dataSource = new BasicDataSource();
        

        dataSource​.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource​.setUrl("jdbc:mysql://localhost:3306/health");
        dataSource​.setUsername("root");
        dataSource​.setPassword("urubu100");
    }

    public BasicDataSource getDataSource() {
        return dataSource;
    }
}