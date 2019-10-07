package nl.rug.comgrafic.auth.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class AppConfig {

    @Bean
    public DataSource dataSource () {
        return DataSourceBuilder
                .create()
                .username ("root")
                .password ("root")
                .url ("jdbc:mysql://localhost:3306/shamzam?useSSL=false&serverTimezone=UTC&createDatabaseIfNotExist=true")
                .driverClassName ("com.mysql.cj.jdbc.Driver")
                .build ();
    }
}
