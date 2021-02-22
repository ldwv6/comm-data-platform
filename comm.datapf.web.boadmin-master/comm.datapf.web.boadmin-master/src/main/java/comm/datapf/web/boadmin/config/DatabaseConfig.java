package comm.datapf.web.boadmin.config;

import comm.datapf.web.boadmin.utils.SecurityUtils;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

    @Configuration
    public class DatabaseConfig {

    @Bean
    @Profile({"local", "LOCAL", "dev", "DEV"})
    public DataSource dataSourceDev(Environment environment) throws Exception {

        BasicDataSource dataSource = new BasicDataSource();

        //TODO Damo로 변경해야함.
        String driverClassName = "bmV0LnNmLmxvZzRqZGJjLnNxbC5qZGJjYXBpLkRyaXZlclNweQ==";
        String url = "amRiYzpsb2c0amRiYzpvcmFjbGU6dGhpbjpAMTcyLjI1LjkuMzI6MTU3MDpDRElURVNU";
        String userName = "Q0RJ";
        String password = "Q0RJMTIjJA==";

        dataSource.setDriverClassName(SecurityUtils.tDecript(driverClassName));
        dataSource.setUrl(SecurityUtils.tDecript(url));
        dataSource.setUsername(SecurityUtils.tDecript(userName));
        dataSource.setPassword(SecurityUtils.tDecript(password));

        /*String driverClassName = "net.sf.log4jdbc.sql.jdbcapi.DriverSpy";
        String url = "jdbc:log4jdbc:oracle:thin:@172.25.9.32:1570:CDITEST";
        String userName = "CDI";
        String password = "CDI12#$";

        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);*/

        return dataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory (DataSource dataSource) throws Exception{

        //SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();

        //sqlSessionFactory.setDataSource(dataSource);
        //sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/**/*.xml"));
        //return sqlSessionFactory.getObject();

        SqlSessionFactoryBean oracleSqlSessionFactoryBean = new SqlSessionFactoryBean();
        oracleSqlSessionFactoryBean.setVfs(SpringBootVFS.class);
        oracleSqlSessionFactoryBean.setDataSource(dataSource);
        oracleSqlSessionFactoryBean.setConfigLocation(new ClassPathResource("config/sql-map-config.xml"));
        oracleSqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:/mapper/**/*.xml"));
        return oracleSqlSessionFactoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSession (SqlSessionFactory sqlSessionFactory) {

        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
