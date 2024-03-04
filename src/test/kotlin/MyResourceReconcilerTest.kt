import io.fabric8.kubernetes.api.model.ConfigMap
import io.fabric8.kubernetes.api.model.ObjectMetaBuilder
import io.fabric8.kubernetes.client.KubernetesClient
import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.RepeatedTest
import java.util.concurrent.TimeUnit

@QuarkusTest
class MyResourceReconcilerTest {

    @Inject
    lateinit var client: KubernetesClient

    @RepeatedTest(1000, failureThreshold = 1)
    fun `should not leave orphan configmap`() {
        Assertions.assertNull(getConfigMap())

        val resource = MyCustomResource().apply {
            metadata = ObjectMetaBuilder()
                .withName("test")
                .withNamespace("default")
                .build()
        }
        client.resource(resource).create()

        Thread.sleep(5)

        // delete the primary, and wait until it is deleted
        client.resources(MyCustomResource::class.java)
            .inNamespace("default")
            .withName("test")
            .withTimeout(5, TimeUnit.SECONDS)
            .delete()

        // there should be no ConfigMap left (it should be deleted when primary was deleted)
        Assertions.assertNull(getConfigMap())
    }

    private fun getConfigMap(): ConfigMap? {
        return client.resources(ConfigMap::class.java)
            .inNamespace("default")
            .withName("test")
            .get()
    }

}
