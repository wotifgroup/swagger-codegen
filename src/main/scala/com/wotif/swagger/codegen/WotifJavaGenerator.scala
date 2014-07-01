package com.wotif.swagger.codegen

import com.wordnik.swagger.codegen.BasicJavaGenerator
import com.wordnik.swagger.model._

object WotifJavaGenerator extends BasicJavaGenerator {

  var invokerPackageOverride = "com.wotif.client.common"
  var modelPackageOverride = "com.wotif.client.model"
  var apiPackageOverride = "com.wotif.client.api"

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

  override def typeMapping = Map(
    "Array" -> "List",
    "array" -> "List",
    "List" -> "List",
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
    "Map<string,string>" -> "Map<String,String>")

  // import/require statements for specific datatypes
  override def importMapping = Map(
    "File" -> "java.io.File",
    "Date" -> "java.util.Date",
    "Array" -> "java.util.*",
    "ArrayList" -> "java.util.*",
    "List" -> "java.util.*",
    "DateTime" -> "org.joda.time.*",
    "LocalDateTime" -> "org.joda.time.*",
    "LocalDate" -> "org.joda.time.*",
    "LocalTime" -> "org.joda.time.*",
    "Map[string,string]" -> "java.util.*"
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
      case "Boolean" => "null"
      case "Integer" => "null"
      case "Long" => "null"
      case "Short" => "null"
      case "Float" => "null"
      case "Double" => "null"
      case "List" => {
        val inner = {
          obj.items match {
            case Some(items) => items.ref.getOrElse(items.`type`)
            case _ => {
              println("failed on " + dataType + ", " + obj)
              throw new Exception("no inner type defined")
            }
          }
        }
        "new ArrayList<" + toDeclaredType(inner) + ">" + "()"
      }
      case "Map<String,String>" => "new HashMap<String,String>()"
      case _ => "null"
    }
  }

   // supporting classes
  override def supportingFiles =
    List(
      ("postBodyEncoder.mustache", destinationDir + java.io.File.separator + invokerPackage.get.replace(".", java.io.File.separator) + java.io.File.separator, "PostBodyEncoder.java"),
      ("apiException.mustache", destinationDir + java.io.File.separator + invokerPackage.get.replace(".", java.io.File.separator) + java.io.File.separator, "ApiException.java"),
      ("pom.mustache", "generated-code/java", "pom.xml"))
      
}