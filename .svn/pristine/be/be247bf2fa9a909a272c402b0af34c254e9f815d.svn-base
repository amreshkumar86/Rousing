package com.oen.api.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class DatabaseConfig {
	  
	  @Value("${spring.datasource.driver-class-name}")
	  private String DB_DRIVER;
	  
	  @Value("${spring.datasource.password}")
	  private String DB_PASSWORD;
	  
	  @Value("${spring.datasource.url}")
	  private String DB_URL;
	  
	  @Value("${spring.datasource.username}")
	  private String DB_USERNAME;
	
	  @Value("${spring.jpa.properties.hibernate.dialect}")
	  private String HIBERNATE_DIALECT;
	  
	  @Value("${spring.jpa.show-sql}")
	  private String HIBERNATE_SHOW_SQL;
	  
//	  @Value("${hibernate.hbm2ddl.auto}")
//	  private String HIBERNATE_HBM2DDL_AUTO;
	
	  @Value("${entitymanager.packagesToScan}")
	  private String ENTITYMANAGER_PACKAGES_TO_SCAN;
	  
	  @Value("${spring.jpa.properties.hibernate.c3p0.max_size}")
	  private String CONN_POOL_MAX_SIZE;
	
	  @Value("${spring.jpa.properties.hibernate.c3p0.min_size}")
	  private String CONN_POOL_MIN_SIZE;
	
	  @Value("${spring.jpa.properties.hibernate.c3p0.idle_test_period}")
	  private String CONN_POOL_IDLE_PERIOD;
	  
	  public DatabaseConfig() {
	  }
	  
	  @Bean
	  public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(DB_DRIVER);
		dataSource.setUrl(DB_URL);
		dataSource.setUsername(DB_USERNAME);
		dataSource.setPassword(DB_PASSWORD);
		return dataSource;
	  }

	    /**
	     * The method that configures the entity manager factory
	     * */
	    @Bean
	    LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
	        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
	        entityManagerFactoryBean.setDataSource(dataSource);
	        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
	        entityManagerFactoryBean.setPackagesToScan(ENTITYMANAGER_PACKAGES_TO_SCAN);
	        
	        Properties jpaProperties = new Properties();
	        jpaProperties.put("hibernate.dialect", HIBERNATE_DIALECT);
//	        jpaProperties.put("hibernate.hbm2ddl.auto", env.getRequiredProperty("hibernate.hbm2ddl.auto"));
	        jpaProperties.put("hibernate.show_sql", HIBERNATE_SHOW_SQL);
//	        jpaProperties.put("hibernate.format_sql", env.getRequiredProperty("hibernate.format_sql"));
	        entityManagerFactoryBean.setJpaProperties(jpaProperties);

	        return entityManagerFactoryBean;
	    }

	    /**
	     * The method that configures the transaction manager
	     * */
	    @Bean
	    JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
	        JpaTransactionManager transactionManager = new JpaTransactionManager();
	        transactionManager.setEntityManagerFactory(entityManagerFactory);
	        return transactionManager;
	    }
	  
	  
	  
	  /*@Bean
	  public ComboPooledDataSource dataSource() {
	      // a named datasource is best practice for later jmx monitoring
	  ComboPooledDataSource dataSource = new ComboPooledDataSource("jupiter");
	
	  try {
	      dataSource.setDriverClass(DB_DRIVER);
	  } catch (PropertyVetoException pve){
	      System.out.println("Cannot load datasource driver (" + DB_DRIVER +") : " + pve.getMessage());
	          return null;
	      }
	      dataSource.setJdbcUrl(DB_URL);
	      dataSource.setUser(DB_USERNAME);
	      dataSource.setPassword(DB_PASSWORD);
	      dataSource.setMinPoolSize(Integer.parseInt(CONN_POOL_MIN_SIZE));
	      dataSource.setMaxPoolSize(Integer.parseInt(CONN_POOL_MAX_SIZE));
	      dataSource.setMaxIdleTime(Integer.parseInt(CONN_POOL_IDLE_PERIOD));
	
	      return dataSource;
	  }
	
	  @Bean
	  public LocalSessionFactoryBean sessionFactory() {
	    LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
	    sessionFactoryBean.setDataSource(dataSource());
	    sessionFactoryBean.setPackagesToScan(ENTITYMANAGER_PACKAGES_TO_SCAN);
	    Properties hibernateProperties = new Properties();
	    hibernateProperties.put("hibernate.dialect", HIBERNATE_DIALECT);
		hibernateProperties.put("hibernate.show_sql", HIBERNATE_SHOW_SQL);
//		hibernateProperties.put("hibernate.hbm2ddl.auto", HIBERNATE_HBM2DDL_AUTO);
	    sessionFactoryBean.setHibernateProperties(hibernateProperties);
	    
	    return sessionFactoryBean;
	  }
	
	  @Bean
	  public HibernateTransactionManager transactionManager() {
	    HibernateTransactionManager transactionManager = 
	        new HibernateTransactionManager();
	    transactionManager.setSessionFactory(sessionFactory().getObject());
	    return transactionManager;
	  }*/
}
