package spock4js

import spock.lang.Specification;
import spock4js.JavascriptTestContext;
import spock4js.WithJavascript;

class MockSpec extends Specification {

    @WithJavascript(["src/test/resources/scripts/greeting.js"])
    JavascriptTestContext jsContext
    
    def "Should be able to pass a mock into javascript"() {
        given:  NameFactory nf = Mock()
        when:   String greeting = jsContext.formatGreeting(nf)
        then:   1 * nf.generateName() >> "Kim"
        and:    greeting == "Hello Kim!"
    }
    
    
}

interface NameFactory {
    String generateName()
}