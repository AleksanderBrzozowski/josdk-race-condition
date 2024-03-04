import io.fabric8.kubernetes.api.model.Namespaced
import io.fabric8.kubernetes.client.CustomResource
import io.fabric8.kubernetes.model.annotation.Group
import io.fabric8.kubernetes.model.annotation.Kind
import io.fabric8.kubernetes.model.annotation.Version
import io.fabric8.openshift.client.dsl.internal.authorization.RoleBindingOperationsImpl.GROUP

@Kind("MyCustomResource")
@Group("example.com")
@Version("v1alpha1")
class MyCustomResource : CustomResource<Map<String, Any>, Map<String, Any>>(), Namespaced
