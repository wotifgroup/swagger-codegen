import com.wordnik.swagger.codegen.Codegen
import com.wordnik.swagger.codegen.model._

import com.wotif.swagger.codegen.WotifJavaGenerator

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

import scala.beans.BeanProperty
import scala.collection.mutable.{ HashMap, LinkedHashMap }

/**
 * We have made some small changes to Codegen.scala to expose extra information to the mustache templates
 * during code generation. These tests are to ensure the changes are retained during a Swagger Codegen update.
 */
@RunWith(classOf[JUnitRunner])
class CodegenModificationsTest extends FlatSpec with ShouldMatchers {

  val subject = new Codegen(new WotifJavaGenerator)
  
  val testOp = new Operation("GET",
      "List All Contacts",
      "",
      "Array[ContactData]",
      "listContacts",
      0,
      List.empty,
      List.empty,
      List.empty,
      List.empty,
      //query param
      List(new Parameter("Name", Some("name"), Some("null"), false, false, "String", AnyAllowableValues, "query", None)),
      List.empty,
      None)

  val testModel = new Model("Contact",
    "Contact",
    "Contact",
    //required field
    LinkedHashMap("Name" -> new ModelProperty("String", "String", 0, true, None, AnyAllowableValues, None)),
    None,
    None,
    None)

  behavior of "Codegen"

  /*
   * Field first on the query param should be true
   */
  it should "have a first field on first query param and should be true" in {
    val map = subject.apiToMap("/contacts", testOp)
    map("queryParams").asInstanceOf[List[HashMap[String, String]]].head("first") should be ("true")
  }

  /*
   * Field hasRequiredParams should be true
   */
  it should "have a hasRequiredParams field and should be true" in {
    val map = subject.modelToMap("Contact", testModel)
    map("hasRequiredParams") should be ("true")
  }
}
