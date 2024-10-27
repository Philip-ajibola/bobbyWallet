package africa.semicolon.ppay.infrastructure.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CloudinaryConfig {
    @Value("${cloud.api.name}")
    private String cloudName;
    @Value("${cloud.api.key}")
    private String cloudKey;
    @Value("${cloud.api.secret}")
    private String cloudSecret;
    @Bean
    public Cloudinary cloudinary(){
        Map<?,?> map = ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", cloudKey,
                "api_secret",cloudSecret
        );
        return new Cloudinary(map);
    }
}
