package br.com.blas.controletarefas;

import java.util.Locale;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;

@SpringBootApplication
@EnableCaching
public class ControleTarefasApplication {

    public static void main(String[] args) {
	SpringApplication.run(ControleTarefasApplication.class, args);
    }

    @Bean
    public LocaleResolver localeResolver() {
	return new FixedLocaleResolver(new Locale("pt", "BR"));
    }

    @Configuration
    public static class MvcConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
	    registry.addRedirectViewController("/", "/tarefas");
	}

    }

}
