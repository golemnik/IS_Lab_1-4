//package com.golem.lab.util;
//
//import org.eclipse.persistence.config.PersistenceUnitProperties;
//import org.springframework.beans.factory.ObjectProvider;
//import org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration;
//import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
//import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
//
//import javax.sql.DataSource;
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//@EnableJpaRepositories("com.golem.lab.repository")
//public class EclipseLinkJpaConfiguration extends JpaBaseConfiguration {
//
//    protected EclipseLinkJpaConfiguration(DataSource dataSource, JpaProperties properties, ObjectProvider<org.springframework.transaction.jta.JtaTransactionManager> jtaTransactionManager) {
//        super(dataSource, properties, jtaTransactionManager);
//    }
//
//    @Override
//    protected AbstractJpaVendorAdapter createJpaVendorAdapter() {
//        return new EclipseLinkJpaVendorAdapter();
//    }
//
//    @Override
//    protected Map<String, Object> getVendorProperties() {
//        HashMap<String, Object> map = new HashMap<>();
//        map.put(PersistenceUnitProperties.WEAVING, "false");
//        map.put(PersistenceUnitProperties.DDL_GENERATION, "drop-and-create-tables");
//        map.put(PersistenceUnitProperties.DDL_DATABASE_GENERATION, "database");
//        return map;
//    }
//
//}