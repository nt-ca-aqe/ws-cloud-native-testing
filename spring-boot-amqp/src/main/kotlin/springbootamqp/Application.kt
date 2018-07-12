package springbootamqp

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Component
import springbootamqp.foo.FooCreated
import springbootamqp.foo.FooDeleted
import springbootamqp.foo.FooEventDispatcher
import java.util.*

@SpringBootApplication
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}

@Component
class ApplicationDemo(
        private val eventDispatcher: FooEventDispatcher
) : CommandLineRunner {

    override fun run(vararg args: String) {
        eventDispatcher.dispatch(FooCreated(UUID.randomUUID()))
        eventDispatcher.dispatch(FooCreated(UUID.randomUUID()))
        eventDispatcher.dispatch(FooDeleted(UUID.randomUUID()))
        eventDispatcher.dispatch(FooCreated(UUID.randomUUID()))
        eventDispatcher.dispatch(FooDeleted(UUID.randomUUID()))
        eventDispatcher.dispatch(FooDeleted(UUID.randomUUID()))
        eventDispatcher.dispatch(FooCreated(UUID.randomUUID()))
        eventDispatcher.dispatch(FooCreated(UUID.randomUUID()))
        eventDispatcher.dispatch(FooCreated(UUID.randomUUID()))
        eventDispatcher.dispatch(FooDeleted(UUID.randomUUID()))
        eventDispatcher.dispatch(FooDeleted(UUID.randomUUID()))
        eventDispatcher.dispatch(FooCreated(UUID.randomUUID()))
        eventDispatcher.dispatch(FooDeleted(UUID.randomUUID()))
    }

}