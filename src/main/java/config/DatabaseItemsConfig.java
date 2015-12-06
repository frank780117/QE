package config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DatabaseItemsConfig{
    
    @Bean(name="executeDataSource")
    @ConfigurationProperties(prefix="datasource.execute")
    public DataSource executeDataSource(){
        return DataSourceBuilder
                .create()
                .build();
    }
    
    @Bean
    @Autowired
    public JdbcTemplate executeJdbcTemplate(@Qualifier("executeDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
    
}
