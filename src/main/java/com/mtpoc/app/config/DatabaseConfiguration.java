package com.mtpoc.app.config;

import com.codahale.metrics.MetricRegistry;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Arrays;


@Configuration
@EnableJpaRepositories("com.mtpoc.app.repository")
@EnableJpaAuditing(auditorAwareRef = "springSecurityAuditorAware")
@EnableTransactionManagement
public class DatabaseConfiguration implements EnvironmentAware {

    private final Logger log = LoggerFactory.getLogger(DatabaseConfiguration.class);

    //private RelaxedPropertyResolver propertyResolver;

    private Environment env;

    private DataSource dataSource;

    @Autowired(required = false)
    private MetricRegistry metricRegistry;

    @Override
    public void setEnvironment(Environment env) {
        this.env = env;
    }

/*

    @Bean
    //@ConditionalOnMissingClass(name = "com.yjiky.mt.config.HerokuDatabaseConfiguration")
    public DataSource dataSource() {
        log.debug("Configuring Datasource");
        if (env.getProperty("url") == null && env.getProperty("databaseName") == null) {
            log.error("Your database connection pool configuration is incorrect! The application" +
                    "cannot start. Please check your Spring profile, current profiles are: {}",
                Arrays.toString(env.getActiveProfiles()));

            throw new ApplicationContextException("Database connection pool is not configured correctly");
        }
        HikariConfig config = new HikariConfig();
        config.setDataSourceClassName(env.getProperty("dataSourceClassName"));
        if (env.getProperty("url") == null || "".equals(env.getProperty("url"))) {
            config.addDataSourceProperty("databaseName", env.getProperty("databaseName"));
            config.addDataSourceProperty("serverName", env.getProperty("serverName"));
        } else {
            config.addDataSourceProperty("url", env.getProperty("url"));
        }
        config.addDataSourceProperty("user", env.getProperty("username"));
        config.addDataSourceProperty("password", env.getProperty("password"));

        if (metricRegistry != null) {
            config.setMetricRegistry(metricRegistry);
        }
        return new HikariDataSource(config);
    }
*/



    @Bean
    //@ConditionalOnMissingClass(name = "com.mycompany.myapp.config.HerokuDatabaseConfiguration")
    //@Profile("!" + Constants.SPRING_PROFILE_CLOUD)
    public DataSource dataSource() {

        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");


        log.debug("Configuring Datasource");


        System.out.println("URL: " + env.getProperty("spring.datasource.databaseName"));

        if (env.getProperty("spring.datasource.url") == null && env.getProperty("spring.datasource.databaseName") == null) {
            log.error("Your database connection pool configuration is incorrect! The application" +
                    "cannot start. Please check your Spring profile, current profiles are: {}",
                Arrays.toString(env.getActiveProfiles()));

            throw new ApplicationContextException("Database connection pool is not configured correctly");
        }
        HikariConfig config = new HikariConfig();
        config.setDataSourceClassName(env.getProperty("spring.datasource.dataSourceClassName"));
        if (env.getProperty("spring.datasource.url") == null || "".equals(env.getProperty("spring.datasource.url"))) {
            config.addDataSourceProperty("databaseName", env.getProperty("spring.datasource.databaseName"));
            config.addDataSourceProperty("serverName", env.getProperty("spring.datasource.serverName"));
        } else {
            config.addDataSourceProperty("url", env.getProperty("spring.datasource.url"));
        }
        config.addDataSourceProperty("user", env.getProperty("spring.datasource.username"));
        config.addDataSourceProperty("password", env.getProperty("spring.datasource.password"));

        //MySQL optimizations, see https://github.com/brettwooldridge/HikariCP/wiki/MySQL-Configuration
/*        if ("com.mysql.jdbc.jdbc2.optional.MysqlDataSource".equals(env.getProperty("dataSourceClassName"))) {
            config.addDataSourceProperty("cachePrepStmts", env.getProperty("cachePrepStmts", "true"));
            config.addDataSourceProperty("prepStmtCacheSize", env.getProperty("prepStmtCacheSize", "250"));
            config.addDataSourceProperty("prepStmtCacheSqlLimit", env.getProperty("prepStmtCacheSqlLimit", "2048"));
            config.addDataSourceProperty("useServerPrepStmts", env.getProperty("useServerPrepStmts", "true"));
        }*/
        if (metricRegistry != null) {
            config.setMetricRegistry(metricRegistry);
        }
        dataSource = new HikariDataSource(config);
        return dataSource;
    }


}
