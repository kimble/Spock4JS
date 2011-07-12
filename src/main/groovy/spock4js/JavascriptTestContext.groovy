package spock4js

import org.mozilla.javascript.Context
import org.mozilla.javascript.Function;
import org.mozilla.javascript.NativeObject;
import org.mozilla.javascript.Scriptable

import spock4js.exception.Spock4JsException;

import static org.mozilla.javascript.Context.*
import static org.mozilla.javascript.Scriptable.*


class JavascriptTestContext {

    private final Context cx
    private final Scriptable scope 
    
    private Object result
    
    public JavascriptTestContext(Context cx) {
        this.scope = cx.initStandardObjects();
        this.cx = cx;
    }
    
    public void evaluateScript(String script) {
        cx.setLanguageVersion(VERSION_1_6)
        result = cx.evaluateString(scope, script, "test-source", 1, null)
    }
    
    def methodMissing(String name, args) {
        Object function = scope.get(name, scope)
        if (function instanceof Function) {
            return function.call(cx, scope, scope, args);
        } else {
            throw new Spock4JsException(name + " does not exists or is not a function")
        }
    }
    
    def propertyMissing(String name) {
        Object property = scope.get(name, scope)
        if (property instanceof NativeObject) {
            return new NativeObjectWrapper(property)
        } else {
            return property
        }
    }
    
}

class NativeObjectWrapper {
    
    @Delegate
    final NativeObject nativeObject

    public NativeObjectWrapper(Object nativeObject) {
        this.nativeObject = nativeObject;
    }    
    
    def propertyMissing(String name) {
        Object property = nativeObject.get(name, nativeObject)
        if (property instanceof NativeObject) {
            return new NativeObjectWrapper(property)
        } else {
            return property
        }
    }
    
}