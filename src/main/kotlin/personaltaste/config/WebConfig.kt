package personaltaste.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import personaltaste.resolver.PTUserExtractResolver

@Configuration
class WebConfig(
    private val pTUserExtractResolver: PTUserExtractResolver
): WebMvcConfigurer {

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(pTUserExtractResolver)
        super.addArgumentResolvers(resolvers)
    }

}