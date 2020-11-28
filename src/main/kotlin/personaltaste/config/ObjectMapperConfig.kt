package personaltaste.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder

/**
 * Created by dom.alpha on 2020/11/27.
 */

@Configuration
class ObjectMapperConfig {

    @Bean
    fun snakeCaseObjectMapper(): ObjectMapper {
        return Jackson2ObjectMapperBuilder.json()
            .failOnEmptyBeans(false)
            .failOnUnknownProperties(false)
            .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .modules(JavaTimeModule(), KotlinModule())
            .propertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE).build()
    }

}