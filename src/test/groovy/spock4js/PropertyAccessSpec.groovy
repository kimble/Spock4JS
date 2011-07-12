package spock4js

import spock.lang.Specification
import spock4js.JavascriptTestContext
import spock4js.WithJavascript

class PropertyAccessSpec extends Specification {

    @WithJavascript(["src/test/resources/scripts/properties.js"])
    JavascriptTestContext jsContext
    
    def "Should be able to access simple properties"() {
        expect: jsContext.someNumber == 123
    }
    
    def "Should be able to reach into complex objects"() {
        expect: jsContext.people.paal.age == 25
    }
    
}