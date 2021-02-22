package comm.datapf.web.boadmin.config;

import comm.datapf.web.boadmin.interceptor.FrontInterceptor;
import lombok.extern.slf4j.Slf4j;
import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.resource.ResourceUrlEncodingFilter;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;


@Slf4j
@Configuration
public class ViewConfig extends WebMvcConfigurerAdapter {

    @Value("${spring.messages.basename}")
    private String[] messagesBasename;

    @Value("${spring.messages.encoding}")
    private String messagesEncoding;

    @Value("${spring.messages.cache-seconds}")
    private int messagesCacheSeconds;

    @Bean
    @SuppressWarnings("Duplicates")
    public ReloadableResourceBundleMessageSource messageSource(){
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames(messagesBasename);
        messageSource.setDefaultEncoding(messagesEncoding);
        messageSource.setCacheSeconds(messagesCacheSeconds);
        return messageSource;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //log.info("profiles {}", profiles);
        CorsRegistration corsRegistration = registry.addMapping("/api/**");
        corsRegistration.allowedOrigins("*").allowedMethods("GET", "POST", "OPTIONS", "PUT")
                .allowedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials","Content-Type" , "X-Requested-With", "accept", "Origin", "Access-Control-Request-Method",
                        "Access-Control-Request-Headers")
                .exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials")
                .allowCredentials(true).maxAge(3600);
    }

    /*@Override
    @SuppressWarnings("Duplicates")
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        boolean devMode = this.env.acceptsProfiles("local", "dev");

        Integer cachePeriod = devMode ? 0 : (60 * 60 * 24 * 365);

        log.debug("StaticResourceConfig - devMode     : {}", devMode);
        log.debug("StaticResourceConfig - cachePeriod : {}", cachePeriod);

        VersionResourceResolver versionResourceResolver = new VersionResourceResolver()
                .addVersionStrategy(new ContentVersionStrategy(), "/**");

        // src/main/resources/static의 경로를 정적리소스를 /{web context}/static/** 로 매핑
        registry.addResourceHandler("/styles/**").addResourceLocations("classpath:/static/styles/")
                .setCachePeriod(cachePeriod)
                .resourceChain(false)
                .addResolver(versionResourceResolver)
                .addTransformer(new CssLinkResourceTransformer());

        registry.addResourceHandler("/scripts/**").addResourceLocations("classpath:/static/js/")
                .setCacheControl(CacheControl.maxAge(7, TimeUnit.DAYS))
                .resourceChain(false)
                .addResolver(versionResourceResolver);

        registry.addResourceHandler("/scripts/dist/**").addResourceLocations(vuePath)
                .setCacheControl(CacheControl.maxAge(7, TimeUnit.DAYS))
                .resourceChain(false)
                .addResolver(versionResourceResolver);

        log.debug("vuePath==============================================");
        log.debug(vuePath);
        // swagger 매핑
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");

    }*/

    @Bean
    public ResourceUrlEncodingFilter resourceUrlEncodingFilter() {
        return new ResourceUrlEncodingFilter();
    }

    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setCacheable(false);
        templateResolver.setPrefix("classpath:/templates/");
        templateResolver.setSuffix(".html");
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
        springTemplateEngine.addTemplateResolver(templateResolver());
        springTemplateEngine.addDialect(new LayoutDialect());           // layout decorate 적용을 위한 추가
        return springTemplateEngine;
    }

    @Bean
    public ThymeleafViewResolver viewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        viewResolver.setCharacterEncoding("UTF-8");
        viewResolver.setOrder(1);
        return viewResolver;
    }

    @Bean
    public FrontInterceptor frontInterceptor() {
        return new FrontInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(frontInterceptor()).addPathPatterns("/**").excludePathPatterns("/static/**").excludePathPatterns("/templates/**");
//        registry.addInterceptor(frontInterceptor()).addPathPatterns("/**").excludePathPatterns("/static/**");
    }

}
