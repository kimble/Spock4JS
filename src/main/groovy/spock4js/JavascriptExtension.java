package spock4js;

import org.spockframework.runtime.extension.AbstractAnnotationDrivenExtension;
import org.spockframework.runtime.model.FeatureInfo;
import org.spockframework.runtime.model.FieldInfo;
import org.spockframework.runtime.model.MethodInfo;
import org.spockframework.runtime.model.SpecInfo;

/**
 *
 * @author Kim A. Betti
 */
public class JavascriptExtension extends AbstractAnnotationDrivenExtension<WithJavascript> {

    @Override
    public void visitFieldAnnotation(WithJavascript annotation, FieldInfo field) {
        SpecInfo spec = field.getParent();
        for (FeatureInfo feature : spec.getAllFeatures()) {
            addInterceptor(annotation, feature.getFeatureMethod(), field);
        }
    }

    private void addInterceptor(WithJavascript annotation, MethodInfo featureMethod, FieldInfo field) {
        featureMethod.addInterceptor(new JavascriptInterceptor(annotation, field));
    }

}
