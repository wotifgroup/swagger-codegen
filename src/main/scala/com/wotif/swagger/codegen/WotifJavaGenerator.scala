package com.wotif.swagger.codegen

import com.wordnik.swagger.codegen.BasicJavaGenerator

object WotifJavaGenerator extends BasicJavaGenerator {

  var invokerPackageOverride = "com.wotif.client"
  var modelPackageOverride = "com.wotif.client.model"
  var apiPackageOverride = "com.wotif.client.api"

  def main(args: Array[String]) = {
    if (args.length < 3) {
      throw new RuntimeException("Need to specify target API name")
    }
    val packageName = args(2)
    invokerPackageOverride = "com.wotif." + packageName + ".client"
    modelPackageOverride = "com.wotif." + packageName + ".client.model"
    apiPackageOverride = "com.wotif." + packageName + ".client.api"
    generateClient(args)
  }

  // location of templates
  override def templateDir = "Wotif/Java"

  // where to write generated code
  override def destinationDir = "generated-code/java/src/main/java"

  // // api invoker package
  override def invokerPackage = Some(invokerPackageOverride)

  // // package for models
  override def modelPackage = Some(modelPackageOverride)

  // // package for api classes
  override def apiPackage = Some(apiPackageOverride)


  additionalParams ++= Map(
    "artifactId" -> "Greenedge",
    "artifactVersion" -> "1.0.0",
    "groupId" -> "com.wotif"
  )

   // supporting classes
  override def supportingFiles =
    List(
      ("apiException.mustache", destinationDir + java.io.File.separator + invokerPackage.get.replace(".", java.io.File.separator) + java.io.File.separator, "ApiException.java"),
      ("pom.mustache", "generated-code/java", "pom.xml"))
}