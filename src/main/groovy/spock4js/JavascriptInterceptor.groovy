package spock4js;

import java.io.File;

import org.codehaus.groovy.runtime.InvokerHelper;
import org.mozilla.javascript.Context;
import org.spockframework.runtime.extension.IMethodInterceptor;
import org.spockframework.runtime.extension.IMethodInvocation;
import org.spockframework.runtime.model.FieldInfo;
import spock4js.exception.Spock4JsException;

/**
 *
 * @author Kim A. Betti
 */
public class JavascriptInterceptor implements IMethodInterceptor {

    private final WithJavascript annotation;
    private final FieldInfo field;

    public JavascriptInterceptor(WithJavascript annotation, FieldInfo field) {
        this.annotation = annotation;
        this.field = field;
    }

    @Override
    public void intercept(IMethodInvocation invocation) throws Throwable {
        verifyThatJavascriptFilesExists(annotation);

        try {
            Context cx = Context.enter();
            JavascriptTestContext jsContext = createJavascriptContext(cx);
            injectJavascriptContextIntoSpec(invocation.getTarget(), jsContext);
            jsContext.evaluateScript(concatinateScriptFiles(annotation.value()))
            
            invocation.proceed();
        } finally {
            Context.exit();
        }
    }

    private void verifyThatJavascriptFilesExists(WithJavascript annotation) {
        for (String scriptPath : annotation.value()) {
            File scriptFile = new File(scriptPath);
            if (!scriptFile.exists()) {
                String exMsg = scriptFile.getAbsolutePath() + " does not exist";
                throw new Spock4JsException(exMsg);
            }
        }
    }

    private JavascriptTestContext createJavascriptContext(Context cx) {
        return new JavascriptTestContext(cx);
    }

    private String concatinateScriptFiles(String[] scriptPaths) {
        StringBuilder str = new StringBuilder(3000);
        for (String scriptPath : scriptPaths) {
            File scriptFile = new File(scriptPath);
            str << scriptFile.getText()
        }

        return str.toString();
    }

    private void injectJavascriptContextIntoSpec(Object target, JavascriptTestContext jsContext) {
        InvokerHelper.setProperty(target, field.getName(), jsContext);
    }

}
