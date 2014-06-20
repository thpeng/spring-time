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

import ch.thp.proto.spring.time.infra.e2e.E2ELoader;
import ch.thp.proto.spring.time.infra.e2e.E2ETestEntity;
import java.time.LocalDate;
import java.util.Properties;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author thierry
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "ch.thp.proto.spring.time.infra.e2e")
@EnableMBeanExport
public class DatabaseConfig {

    @Bean
    public DataSource dataSource(Environment env) throws Exception {
        return  new EmbeddedDatabaseBuilder().setType(H2).build();
    }

    @Autowired
    @Bean
    public DataSourceInitializer dataSourceInitializer(final DataSource dataSource, E2ELoader loader) {

        final DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(databasePopulator());
        initializer.setDatabaseCleaner(databaseCleaner());
        
        loader.load();
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

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(Environment env) throws Exception {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
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
    public HibernateJpaDialect hibernateJpaDialect() {
        return new HibernateJpaDialect();
    }

    Properties jpaProperties() {
        Properties props = new Properties();
        //jpa 2.1 compliant ddl generation
        props.put("javax.persistence.schema-generation.database.action", "create");
        props.put("javax.persistence.schema-generation.scripts.action", "drop-and-create");
        props.put("javax.persistence.schema-generation.scripts.drop-target", "target/mydrop.ddl");
        props.put("javax.persistence.schema-generation.scripts.create-target", "target/mycreate.ddl");
        return props;
    }
}
