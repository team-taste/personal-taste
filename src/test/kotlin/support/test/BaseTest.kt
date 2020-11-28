package support.test

import org.mockito.Mockito


open class BaseTest {
    protected fun <T> any(type: Class<T>): T = Mockito.any<T>(type)
}