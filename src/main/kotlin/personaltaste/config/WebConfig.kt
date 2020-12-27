package personaltaste.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import personaltaste.resolver.UserExtractResolver

@Configuration
class WebConfig(
    private val pTUserExtractResolver: UserExtractResolver
): WebMvcConfigurer {

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(pTUserExtractResolver)
        super.addArgumentResolvers(resolvers)
    }

}