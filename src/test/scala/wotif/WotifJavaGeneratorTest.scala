import com.wordnik.swagger.codegen.model._
import com.wordnik.swagger.codegen.util._
import com.wordnik.swagger.codegen.language._
import com.wordnik.swagger.codegen.PathUtil

import com.wotif.swagger.codegen.WotifJavaGenerator

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

import scala.beans.BeanProperty

@RunWith(classOf[JUnitRunner])
class WotifJavaGeneratorTest extends FlatSpec with ShouldMatchers {
  val config = new WotifJavaGenerator

  behavior of "WotifJavaGenerator"

  /*
   * returns the invoker package from the config
   */
  it should "get the invoker package" in {
    config.invokerPackage should be (Some("com.wotif.client.common"))
  }

  /*
   * returns the model package
   */
  it should "get the model package" in {
    config.modelPackage should be (Some("com.wotif.client.model"))
  }

  /*
   * returns the api package
   */
  it should "get the api package" in {
    config.apiPackage should be (Some("com.wotif.client.api"))
  }

  /*
   * A response of type "void" will turn into a declaration of None
   * for the template generator
   */
  it should "process a response declaration" in {
  	config.processResponseDeclaration("void") should be (None)
  }

  /*
   * Lists and Arrays without inner type are preserved
   */
  it should "process an upper-case array" in {
    config.processResponseDeclaration("Array") should be (Some("List"))
  }

  it should "process an array" in {
    config.processResponseDeclaration("array") should be (Some("List"))
  }

  it should "process a List" in {
    config.processResponseDeclaration("List") should be (Some("List"))
  }

  /*
   * Maps without inner type are preserved
   */
  it should "process a Map" in {
    config.processResponseDeclaration("Map") should be (Some("Map"))
  }

  /*
   * BigDecimal with capitalization variations is preserved
   */

  it should "process a pascal-case BigDecimal" in {
    config.processResponseDeclaration("BigDecimal") should be (Some("BigDecimal"))
  }

  it should "process a camel-case BigDecimal" in {
    config.processResponseDeclaration("bigDecimal") should be (Some("BigDecimal"))
  }

  it should "process a sentence-case BigDecimal" in {
    config.processResponseDeclaration("Bigdecimal") should be (Some("BigDecimal"))
  }

  it should "process a lower-case BigDecimal" in {
    config.processResponseDeclaration("bigdecimal") should be (Some("BigDecimal"))
  }

  /*
   * swagger booleans are turned into scala Booleans
   */
  it should "process a boolean response" in {
    config.processResponseDeclaration("boolean") should be (Some("Boolean"))
  }

  /*
   * swagger strings are turned into scala Strings
   */
  it should "process a string response" in {
  	config.processResponseDeclaration("string") should be (Some("String"))
  }

  /*
   * swagger int is turned into scala Int
   */
  it should "process an int response" in {
    config.processResponseDeclaration("int") should be (Some("Integer"))
  }

  /*
   * swagger float is turned into scala Float
   */
  it should "process a float response" in {
    config.processResponseDeclaration("float") should be (Some("Float"))
  }

  /*
   * swagger long is turned into scala Long
   */
  it should "process a long response" in {
    config.processResponseDeclaration("long") should be (Some("Long"))
  }

  /*
   * swagger short is turned into scala Short
   */
  it should "process a short response" in {
    config.processResponseDeclaration("short") should be (Some("Short"))
  }

  /*
   * swagger char is turned into scala String
   */
  it should "process a char response" in {
    config.processResponseDeclaration("char") should be (Some("String"))
  }

  /*
   * swagger double is turned into scala Double
   */
  it should "process a double response" in {
    config.processResponseDeclaration("double") should be (Some("Double"))
  }

  /*
   * swagger object is turned into scala Object
   */
  it should "process an object response" in {
    config.processResponseDeclaration("object") should be (Some("Object"))
  }

  /*
   * should preserve Map<String,String>
   */
  it should "process a Map<String,String>" in {
    config.processResponseDeclaration("Map<string,string>") should be (Some("Map<String,String>"))
  }

  /*
   * should preserve Map<String,Int>
   */
  it should "process a Map<String,Int>" in {
    config.processResponseDeclaration("Map<string,int>") should be (Some("Map<String,Integer>"))
  }

  /*
   * should preserve Map<String,Boolean>
   */
  it should "process a Map<String,Boolean>" in {
    config.processResponseDeclaration("Map<string,boolean>") should be (Some("Map<String,Boolean>"))
  }

  /*
   * should preserve Map<String,Node>
   */
  it should "process a Map<String,Node>" in {
    config.processResponseDeclaration("Map<string,Node>") should be (Some("Map<String,Node>"))
  }

  /*
   * should preserve List<Map<String,String>>
   */
  it should "process a List<Map<String,String>>" in {
    config.processResponseDeclaration("List<Map<string,string>>") should be (Some("List<Map<String,String>>"))
  }

  /*
   * codegen should honor special imports to avoid generating
   * classes
   */
  it should "honor the import mapping" in {
    config.importMapping("File") should be ("java.io.File")
    config.importMapping("BigDecimal") should be ("java.math.BigDecimal")
    config.importMapping("Date") should be ("java.util.Date")
    config.importMapping("Array") should be ("java.util.*")
    config.importMapping("ArrayList") should be ("java.util.*")
    config.importMapping("List") should be ("java.util.*")
    config.importMapping("DateTime") should be ("org.joda.time.*")
    config.importMapping("LocalDateTime") should be ("org.joda.time.*")
    config.importMapping("LocalDate") should be ("org.joda.time.*")
    config.importMapping("LocalTime") should be ("org.joda.time.*")
    config.importMapping("Map[string,string]") should be ("java.util.*")
    config.importMapping("Map[string,int]") should be ("java.util.*")
    config.importMapping("List[Map[string,string]]") should be ("java.util.*")
    config.importMapping("Map[string,Node]") should be ("java.util.*")
    config.importMapping("Timestamp") should be ("java.sql.Timestamp")
    config.importMapping("Set") should be ("java.util.*")
  }

  /*
   * arrays look nice
   */
  it should "process a string array" in {
    config.processResponseDeclaration("array[String]") should be (Some("List<String>"))
  }

  it should "process an upper-case string array" in {
    config.processResponseDeclaration("Array[String]") should be (Some("List<String>"))
  }

  /*
   * sets look nice
   */
  it should "process a string set" in {
    config.processResponseDeclaration("Set[String]") should be (Some("Set<String>"))
  }

  /*
   * declarations are used in models, and types need to be
   * mapped appropriately
   */
  it should "convert to a declaration" in {
    val expected = Map(
      "string" -> ("String", "null"),
      "int" -> ("Integer", "null"),
      "float" -> ("Float", "null"),
      "long" -> ("Long", "null"),
      "double" -> ("Double", "null"),
      "object" -> ("Object", "null"),
      "BigDecimal" -> ("BigDecimal", "null"),
      "Boolean" -> ("Boolean", "null"),
      "Integer" -> ("Integer", "null"),
      "Long" -> ("Long", "null"),
      "Short" -> ("Short", "null"),
      "Float" -> ("Float", "null"),
      "Double" -> ("Double", "null"),
      "List" -> ("List", "new ArrayList()"),
      "Set" -> ("Set", "new HashSet()"),
      "Map" -> ("Map", "new HashMap()"),
      "Map<String,String>" -> ("Map<String,String>", "new HashMap<String,String>()"),
      "Map<String,Integer>" -> ("Map<String,Integer>", "new HashMap<String,Integer>()"),
      "Map<String,Boolean>" -> ("Map<String,Boolean>", "new HashMap<String,Boolean>()"),
      "List<Map<String,String>>" -> ("List<Map<String,String>>", "new ArrayList<Map<String,String>>()"),
      "Map<String,Node>" -> ("Map<String,Node>", "new HashMap<String,Node>()"))
    expected.map(e => {
      val model = ModelProperty(e._1, "nothing")
      config.toDeclaration(model) should be (e._2)
    })
  }

  /*
   * support list declarations with string inner value and the correct default value
   */
   it should "create a declaration with a List of strings" in {
      val property = ModelProperty(
        `type` = "Array", 
        qualifiedType = "nothing",
        items=Some(ModelRef(`type`= "string")))
      val m = config.toDeclaration(property)
      m._1 should be ("List<String>")
      m._2 should be ("new ArrayList<String>()")
   }

  /*
   * support list declarations with int inner value and the correct default value
   */
   it should "create a declaration with a List of ints" in {
      val property = ModelProperty(
        `type` = "Array", 
        qualifiedType = "nothing",
        items=Some(ModelRef(`type`= "int")))
      val m = config.toDeclaration(property)
      m._1 should be ("List<Integer>")
      m._2 should be ("new ArrayList<Integer>()")
   }

  /*
   * support list declarations with float inner value and the correct default value
   */
   it should "create a declaration with a List of floats" in {
      val property = ModelProperty(
        `type` = "Array", 
        qualifiedType = "nothing",
        items=Some(ModelRef(`type`= "float")))
      val m = config.toDeclaration(property)
      m._1 should be ("List<Float>")
      m._2 should be ("new ArrayList<Float>()")
   }

  /*
   * support list declarations with double inner value and the correct default value
   */
   it should "create a declaration with a List of doubles" in {
      val property = ModelProperty(
        `type` = "Array", 
        qualifiedType = "nothing",
        items=Some(ModelRef(`type`= "double")))
      val m = config.toDeclaration(property)
      m._1 should be ("List<Double>")
      m._2 should be ("new ArrayList<Double>()")
   }

  /*
   * support list declarations with complex inner value and the correct default value
   */
   it should "create a declaration with a List of complex objects" in {
      val property = ModelProperty(
        `type` = "Array", 
        qualifiedType = "nothing",
        items=Some(ModelRef(`type`= "User")))
      val m = config.toDeclaration(property)
      m._1 should be ("List<User>")
      m._2 should be ("new ArrayList<User>()")
   }

  /*
   * support set declarations with string inner value and the correct default value
   */
   it should "create a declaration with a Set of strings" in {
      val property = ModelProperty(
        `type` = "Set", 
        qualifiedType = "nothing",
        items=Some(ModelRef(`type`= "string")))
      val m = config.toDeclaration(property)
      m._1 should be ("Set<String>")
      m._2 should be ("new HashSet<String>()")
   }

  /*
   * support set declarations with int inner value and the correct default value
   */
   it should "create a declaration with a Set of ints" in {
      val property = ModelProperty(
        `type` = "Set", 
        qualifiedType = "nothing",
        items=Some(ModelRef(`type`= "int")))
      val m = config.toDeclaration(property)
      m._1 should be ("Set<Integer>")
      m._2 should be ("new HashSet<Integer>()")
   }

  /*
   * support set declarations with float inner value and the correct default value
   */
   it should "create a declaration with a Set of floats" in {
      val property = ModelProperty(
        `type` = "Set", 
        qualifiedType = "nothing",
        items=Some(ModelRef(`type`= "float")))
      val m = config.toDeclaration(property)
      m._1 should be ("Set<Float>")
      m._2 should be ("new HashSet<Float>()")
   }

  /*
   * support set declarations with double inner value and the correct default value
   */
   it should "create a declaration with a Set of doubles" in {
      val property = ModelProperty(
        `type` = "Set", 
        qualifiedType = "nothing",
        items=Some(ModelRef(`type`= "double")))
      val m = config.toDeclaration(property)
      m._1 should be ("Set<Double>")
      m._2 should be ("new HashSet<Double>()")
   }

  /*
   * support set declarations with complex inner value and the correct default value
   */
   it should "create a declaration with a Set of complex objects" in {
      val property = ModelProperty(
        `type` = "Set", 
        qualifiedType = "nothing",
        items=Some(ModelRef(`type`= "User")))
      val m = config.toDeclaration(property)
      m._1 should be ("Set<User>")
      m._2 should be ("new HashSet<User>()")
   }
}
