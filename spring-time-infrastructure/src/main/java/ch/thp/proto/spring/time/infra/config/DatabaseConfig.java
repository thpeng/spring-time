/*
 * Copyright 2014 caleb.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.thp.proto.spring.time.infra.config;

import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.h2.jdbcx.JdbcDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaDialect;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * @author thierry
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "ch.thp.proto.spring.time.infra.e2e")
@EnableMBeanExport
public class DatabaseConfig {

    private static final String H2_IN_MEMORY_DB = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    


    @Bean
    public DataSource dataSource(Environment env) throws Exception {
        return createH2DataSource();
    }

    @Autowired
    @Bean
    public DataSourceInitializer dataSourceInitializer(final DataSource dataSource) {

        final DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(databasePopulator());
        initializer.setDatabaseCleaner(databaseCleaner());
        return initializer;
    }

    private DatabasePopulator databasePopulator() {
        final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        return populator;
    }

    private DatabasePopulator databaseCleaner() {
        final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        return populator;
    }

    private DataSource createH2DataSource() {
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL(H2_IN_MEMORY_DB);
        ds.setUser("sa");
        ds.setPassword("");

        return ds;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(Environment env) throws Exception {

        EclipseLinkJpaVendorAdapter vendorAdapter = new EclipseLinkJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(Boolean.TRUE);
        vendorAdapter.setShowSql(Boolean.TRUE);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setPersistenceUnitName("sample");
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("ch.thp.proto.spring.time.infra.e2e");
        factory.setDataSource(dataSource(env));

        factory.setJpaProperties(jpaProperties());

        return factory;
    }

    @Bean
    public EclipseLinkJpaDialect eclipseLinkJpaDialect() {
        return new EclipseLinkJpaDialect();
    }

    Properties jpaProperties() {
        Properties props = new Properties();
//        props.put("eclipselink.ddl-generation", "drop-and-create-tables");
        props.put("eclipselink.create-ddl-jdbc-file-name", "createDDL_ddlGeneration.jdbc");
        props.put("eclipselink.drop-ddl-jdbc-file-name", "dropDDL_ddlGeneration.jdbc");
        props.put("eclipselink.ddl-generation.output-mode", "both");
        props.put("eclipselink.weaving", "static");
//        props.put("eclipselink.logging.parameters", "true");
//        props.put("eclipselink.logging.level.sql", "FINE");

        return props;
    }
}
