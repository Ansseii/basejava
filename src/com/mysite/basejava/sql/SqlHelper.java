package com.mysite.basejava.sql;

public class SqlHelper {

    private final ConnectionFactory connectionFactory;

    public SqlHelper(final ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

}
