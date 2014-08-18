package com.wotif.swagger.codegen

import com.wordnik.swagger.codegen.BasicJavaGenerator
import com.wordnik.swagger.model._

object WotifJavaGenerator extends WotifJavaGenerator {
  def main(args: Array[String]) = {
    if (args.length < 3) {
      throw new RuntimeException("Need to specify target API name")
    }
    val packageName = args(2)
    invokerPackageOverride = "com.wotif." + packageName + ".client.common"
    modelPackageOverride = "com.wotif." + packageName + ".client.model"
    apiPackageOverride = "com.wotif." + packageName + ".client.api"
    generateClient(args)
  }
}

class WotifJavaGenerator extends BasicJavaGenerator {

  var invokerPackageOverride = "com.wotif.client.common"
  var modelPackageOverride = "com.wotif.client.model"
  var apiPackageOverride = "com.wotif.client.api"  

  override def typeMapping = Map(
    "Array" -> "List",
    "array" -> "List",
    "List" -> "List",
    "BigDecimal" -> "BigDecimal",
    "bigDecimal" -> "BigDecimal",
    "Bigdecimal" -> "BigDecimal",
    "bigdecimal" -> "BigDecimal",
    "boolean" -> "Boolean",
    "string" -> "String",
    "int" -> "Integer",
    "float" -> "Float",
    "long" -> "Long",
    "short" -> "Short",
    "char" -> "String",
    "double" -> "Double",
    "object" -> "Object",
    "integer" -> "Integer",
    "Map<string,string>" -> "Map<String,String>",
    "Map<string,int>" -> "Map<String,Integer>",
    "Map" -> "Node",
    "Map" -> "Map",
    "List<Map<string,string>>" -> "List<Map<String,String>>",
    "Map<string,Node>" -> "Map<String,Node>")

  // import/require statements for specific datatypes
  override def importMapping = Map(
    "File" -> "java.io.File",
    "BigDecimal" -> "java.math.BigDecimal",
    "Date" -> "java.util.Date",
    "Array" -> "java.util.*",
    "ArrayList" -> "java.util.*",
    "List" -> "java.util.*",
    "DateTime" -> "org.joda.time.*",
    "LocalDateTime" -> "org.joda.time.*",
    "LocalDate" -> "org.joda.time.*",
    "LocalTime" -> "org.joda.time.*",
    "Map[string,string]" -> "java.util.*",
    "Map[string,int]" -> "java.util.*",
    "List[Map[string,string]]" -> "java.util.*",
    "Map[string,Node]" -> "groovy.util.*",
    "Map" -> "java.util.*"
  )

  // location of templates
  override def templateDir = "Wotif/Java"

  // where to write generated code
  override def destinationDir = "generated-code/java/src/main/java"

  // api invoker package - we got rid of the invoker but the variable name is inherited
  override def invokerPackage = Some(invokerPackageOverride)

  // package for models
  override def modelPackage = Some(modelPackageOverride)

  // package for api classes
  override def apiPackage = Some(apiPackageOverride)


  additionalParams ++= Map(
    "groupId" -> "com.wotif"
  )

  override def toDefaultValue(dataType: String, obj: ModelProperty) = {
    dataType match {
      case "BigDecimal" => "null"
      case "Boolean" => "null"
      case "Integer" => "null"
      case "Long" => "null"
      case "Short" => "null"
      case "Float" => "null"
      case "Double" => "null"
      case "BigDecimal" => "null"
      case "List" => {
        val inner = {
          obj.items match {
            case Some(items) => items.ref.getOrElse(items.`type`)
            case None => {
              println("*** WARNING ***: No inner type declared for " + dataType)
              null
            }
          }
        }
        if(inner == null) {
          "new ArrayList()"
        } else {
          "new ArrayList<" + toDeclaredType(inner) + ">" + "()"
        }
      }
      case "Map<String,String>" => "new HashMap<String,String>()"
      case "Map<String,Integer>" => "new HashMap<String,Integer>()"
      case "List<Map<String,String>>" => "new ArrayList<Map<String,String>>()"
      case "Map" => "new HashMap()"
      case "Map<String,Node>" => "new HashMap<String,Node>()"
      case _ => "null"
    }
  }

  override def toDeclaration(obj: ModelProperty) = {
    var declaredType = toDeclaredType(obj.`type`)
    declaredType match {
      case "Array" => declaredType = "List"
      case e: String => e
    }

    val defaultValue = toDefaultValue(declaredType, obj)
    declaredType match {
      case "List" => {
        val inner = {
          obj.items match {
            case Some(items) => items.ref.getOrElse(items.`type`)
            case None => {
              println("*** WARNING ***: No inner type declared for " + declaredType)
              null
            }
          }
        }
        if(inner == null) {
            declaredType
        } else {
          declaredType += "<" + toDeclaredType(inner) + ">"
        }
      }
      case "Set" => {
        val inner = {
          obj.items match {
            case Some(items) => items.ref.getOrElse(items.`type`)
            case _ => {
              println("failed on " + declaredType + ", " + obj)
              throw new Exception("no inner type defined")
            }
          }
        }
        declaredType += "<" + toDeclaredType(inner) + ">"
      }
      case "Map" => {
        println("*** WARNING ***: No inner types declared for " + declaredType)
        declaredType
      }
      case _ =>
    }
    (declaredType, defaultValue)
  }


   // supporting classes
  override def supportingFiles =
    List(
      ("postBodyEncoder.mustache", destinationDir + java.io.File.separator + invokerPackage.get.replace(".", java.io.File.separator) + java.io.File.separator, "PostBodyEncoder.java"),
      ("apiException.mustache", destinationDir + java.io.File.separator + invokerPackage.get.replace(".", java.io.File.separator) + java.io.File.separator, "ApiException.java"),
      ("pom.mustache", "generated-code/java", "pom.xml"))
      
}