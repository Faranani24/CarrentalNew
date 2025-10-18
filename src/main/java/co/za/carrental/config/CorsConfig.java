/*package co.za.carrental.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Value("${app.cors.allowed-origins:http://localhost:5173,http://localhost:5177,http://localhost:3000}")
    private String originsRaw;

    @Value("${app.cors.allowed-methods:GET,POST,PUT,DELETE,PATCH,OPTIONS}")
    private String methodsRaw;

    @Value("${app.cors.allowed-headers:Content-Type,Authorization}")
    private String headersRaw;

    @Value("${app.cors.allow-credentials:false}")
    private boolean allowCredentials;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOriginPatterns(split(originsRaw))
                .allowedMethods(split(methodsRaw))
                .allowedHeaders(split(headersRaw))
                .allowCredentials(allowCredentials)
                .maxAge(3600);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList(split(originsRaw)));
        configuration.setAllowedMethods(Arrays.asList(split(methodsRaw)));
        configuration.setAllowedHeaders(Arrays.asList(split(headersRaw)));
        configuration.setAllowCredentials(allowCredentials);
        configuration.setExposedHeaders(List.of("Authorization", "Content-Disposition"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    private String[] split(String csv) {
        if (!StringUtils.hasText(csv)) return new String[0];
        return StringUtils.commaDelimitedListToStringArray(csv.trim());
    }
}
*/