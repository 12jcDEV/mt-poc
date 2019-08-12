package com.mtpoc.app.tenancy;


import org.hibernate.HibernateException;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static com.mtpoc.app.tenancy.MultiTenantConstants.DEFAULT_TENANT_ID;

@Component
public class MultiTenantConnectionProviderImpl implements MultiTenantConnectionProvider {


    @Autowired
    private DataSource dataSource;

    @Override
    public Connection getAnyConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        connection.close();
    }

    @Override
    public Connection getConnection(String tenantIdentifier) throws SQLException {

        final Connection connection = getAnyConnection();

        try {
            if (tenantIdentifier != null) {
                connection.setCatalog(tenantIdentifier);
                connection.setSchema(tenantIdentifier);
                connection.createStatement().execute("SET search_path to " + tenantIdentifier);
            } else {
                connection.setSchema(DEFAULT_TENANT_ID);
                connection.setCatalog(DEFAULT_TENANT_ID);
                connection.createStatement().execute("SET search_path to " + DEFAULT_TENANT_ID);
            }
        }
        catch ( SQLException e ) {
            throw new HibernateException(
                "Could not alter JDBC connection to specified schema [" + tenantIdentifier + "]",
                e
            );
        }

        return connection;
    }

    @Override
    public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
        try {
            connection.createStatement().execute( "USE " + DEFAULT_TENANT_ID );
        }
        catch ( SQLException e ) {
            throw new HibernateException(
                "Could not alter JDBC connection to specified schema [" + tenantIdentifier + "]",
                e
            );
        }
        connection.close();
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean isUnwrappableAs(Class unwrapType) {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> unwrapType) {
        return null;
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return true;
    }
}
