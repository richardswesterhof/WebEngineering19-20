package nl.rug.shamzam.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers (ResourceHandlerRegistry registry) {
        System.out.println ("addResourceHandlers");
        registry.addResourceHandler ("/static/**")
                .addResourceLocations ("classpath:/static/");
        registry.addResourceHandler ("/images/**")
                .addResourceLocations ("classpath:/images/");
    }


    @Bean
    public ViewResolver getViewResolver() {
        System.out.println ("getViewResolver");
        InternalResourceViewResolver resolver
                = new InternalResourceViewResolver ();
        resolver.setViewClass (JstlView.class);
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(100*1000*1000);
        return multipartResolver;
    }
}
