package spock4js

import spock.lang.Specification;
import spock4js.WithJavascript;

class JavascriptMethodInvocationSpec extends Specification {

    @WithJavascript(["src/test/resources/scripts/adder-function.js"])
    def jsContext
    
    def "JavascriptContext should be injected into specification instance"() {
        expect: jsContext != null
    }
    
    def "Should be able to invoke javascript function"() {
        expect: jsContext.adder(1, 2) == 3
    }
    
}
