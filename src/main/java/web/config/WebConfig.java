package web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;


@Configuration
@EnableWebMvc
@ComponentScan("web")
public class WebConfig implements WebMvcConfigurer {

    private final ApplicationContext applicationContext;

    @Autowired
    public WebConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }


    // Создание и конфигурация объекта для разрешения шаблонов Thymeleaf, основанных на ресурсах Spring
    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);// Установка контекста приложения для разрешения ресурсов
        templateResolver.setPrefix("/WEB-INF/view/");// Установка префикса для путей к шаблонам
        templateResolver.setSuffix(".html");// Установка суффикса для файлов шаблонов
        return templateResolver;// Возвращение сконфигурированного объекта шаблонного разрешителя
    }


    // Создание и конфигурация движка шаблонов Thymeleaf
    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());// Установка шаблонного разрешителя
        templateEngine.setEnableSpringELCompiler(true);// Включение компиляции Spring Expression Language (Spring EL)
        return templateEngine;// Возвращение сконфигурированного объекта движка шаблонов
    }


    // Конфигурация резольвера представлений для Thymeleaf
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine()); // Установка движка шаблонов для резольвера
        registry.viewResolver(resolver);// Регистрация резольвера в реестре резольверов представлений
    }

    // Указывает Spring MVC обрабатывать запросы, начинающиеся с /static/, и искать соответствующие файлы в папке static
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }

    // Создает и настраивает фильтр для принудительного использования кодировки UTF-8 для всех HTTP-запросов и ответов.
    @Bean
    public CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        return filter;
    }


    // Настройка для валидации
    @Bean
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }

    @Override
    public Validator getValidator() {
        return validator();
    }
}


