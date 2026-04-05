import org.junit.platform.suite.api.IncludeClassNamePatterns;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;



@Suite
@SuiteDisplayName("Lanzar todos los tests")
@SelectPackages({"es.uji.al405104.algoritmos", "es.uji.al405104.csv", "es.uji.al405104.table"})
@IncludeClassNamePatterns(".*Test")
public class TestSuite {
}
