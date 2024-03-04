import io.javaoperatorsdk.operator.api.reconciler.Context
import io.javaoperatorsdk.operator.api.reconciler.ControllerConfiguration
import io.javaoperatorsdk.operator.api.reconciler.Reconciler
import io.javaoperatorsdk.operator.api.reconciler.UpdateControl
import io.javaoperatorsdk.operator.api.reconciler.dependent.Dependent

@ControllerConfiguration(dependents = [Dependent(type = ConfigMapDependentResource::class)])
class MyCustomResourceReconciler : Reconciler<MyCustomResource> {
    override fun reconcile(
        resource: MyCustomResource,
        context: Context<MyCustomResource>
    ): UpdateControl<MyCustomResource> {
        return UpdateControl.noUpdate()
    }
}
