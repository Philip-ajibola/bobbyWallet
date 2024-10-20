package africa.semicolon.bobbywallet.infrastructure.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ModelMapperConfig {
    private ModelMapper mapper;

    @Bean
    public ModelMapper modelMapper() {
        mapper = new ModelMapper();
        return mapper;
    }
}
