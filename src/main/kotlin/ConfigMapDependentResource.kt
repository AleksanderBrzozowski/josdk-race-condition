import io.fabric8.kubernetes.api.model.ConfigMap
import io.fabric8.kubernetes.api.model.ConfigMapBuilder
import io.fabric8.kubernetes.api.model.ObjectMetaBuilder
import io.javaoperatorsdk.operator.api.reconciler.Context
import io.javaoperatorsdk.operator.api.reconciler.dependent.Deleter
import io.javaoperatorsdk.operator.processing.dependent.Creator
import io.javaoperatorsdk.operator.processing.dependent.Updater
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.KubernetesDependent
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.KubernetesDependentResource
import io.javaoperatorsdk.operator.processing.event.source.SecondaryToPrimaryMapper
import io.javaoperatorsdk.operator.processing.event.source.informer.Mappers

@KubernetesDependent
class ConfigMapDependentResource :
    KubernetesDependentResource<ConfigMap, MyCustomResource>(ConfigMap::class.java),
    Creator<ConfigMap, MyCustomResource>,
    Updater<ConfigMap, MyCustomResource>,
    Deleter<MyCustomResource>,
    SecondaryToPrimaryMapper<ConfigMap> by Mappers.fromDefaultAnnotations() {

    override fun desired(primary: MyCustomResource, context: Context<MyCustomResource>): ConfigMap {
        val metadata = ObjectMetaBuilder()
            .withName(primary.metadata.name)
            .withNamespace("default")
            .build()
        return ConfigMapBuilder()
            .withMetadata(metadata)
            .build()
    }
}
