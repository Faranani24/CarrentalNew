package co.za.carrental.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Value("${app.cors.allowed-origins:http://localhost:5173}")
    private String originsRaw;

    @Value("${app.cors.allowed-methods:GET,POST,PUT,DELETE,PATCH,OPTIONS}")
    private String methodsRaw;

    @Value("${app.cors.allowed-headers:Authorization,Content-Type}")
    private String headersRaw;

    @Value("${app.cors.allow-credentials:true}")
    private boolean allowCredentials;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String[] origins = split(originsRaw);
        String[] methods = split(methodsRaw);
        String[] headers = split(headersRaw);

        boolean wildcard = origins.length == 1 && "*".equals(origins[0]);
        boolean effectiveAllowCredentials = allowCredentials && !wildcard;

        registry.addMapping("/api/**")
                .allowedOrigins(origins)
                .allowedMethods(methods)
                .allowedHeaders(headers)
                .allowCredentials(effectiveAllowCredentials)
                .maxAge(3600);
    }

    private String[] split(String csv) {
        if (!StringUtils.hasText(csv)) return new String[0];
        return StringUtils.commaDelimitedListToStringArray(csv.trim());
    }
}