package com.wotif.swagger.codegen

import com.wordnik.swagger.codegen.BasicAndroidJavaGenerator

object WotifAndroidJavaClient extends WotifAndroidJavaGenerator {
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

class WotifAndroidJavaGenerator extends BasicAndroidJavaGenerator {
  var invokerPackageOverride = "com.wotif.client.common"
  var modelPackageOverride = "com.wotif.client.model"
  var apiPackageOverride = "com.wotif.client.api"

  // location of templates
  override def templateDir = "Wotif/android-java"

  // api invoker package - we got rid of the invoker but the variable name is inherited
  override def invokerPackage = Some(invokerPackageOverride)

  // package for models
  override def modelPackage = Some(modelPackageOverride)

  // package for api classes
  override def apiPackage = Some(apiPackageOverride)


  additionalParams ++= Map(
    "groupId" -> "com.wotif"
  )

  // supporting classes
  override def supportingFiles = List(
    ("apiInvoker.mustache", destinationDir + java.io.File.separator + invokerPackage.get.replace(".", java.io.File.separator) + java.io.File.separator, "ApiInvoker.java"),
    ("jsonUtil.mustache", destinationDir + java.io.File.separator + invokerPackage.get.replace(".", java.io.File.separator) + java.io.File.separator, "JsonUtil.java"),
    ("apiException.mustache", destinationDir + java.io.File.separator + invokerPackage.get.replace(".", java.io.File.separator) + java.io.File.separator, "ApiException.java"),
    ("pom.mustache", destinationDir, "pom.xml")
  )
}