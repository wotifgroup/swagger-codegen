package com.wotif.swagger.codegen

import com.wordnik.swagger.codegen.BasicObjcGenerator

object WotifObjcGenerator extends BasicObjcGenerator {

  var invokerPackageOverride = "com.wotif.client"
  var modelPackageOverride = "com.wotif.client.model"
  var apiPackageOverride = "com.wotif.client.api"

  def main(args: Array[String]) = {
    if (args.length < 3) {
      throw new RuntimeException("Need to specify target API name")
    }
    val packageName = args(2)
    invokerPackageOverride = "com." + packageName + ".client"
    modelPackageOverride = "com." + packageName + ".client.model"
    apiPackageOverride = "com." + packageName + ".client.api"
    generateClient(args)
  }

  // location of templates
  override def templateDir = "Wotif/objc"

  // where to write generated code
  override def destinationDir = "generated-code/objc"

  // api invoker package
  override def invokerPackage = Some(invokerPackageOverride)

  // package for models
  override def modelPackage = Some(modelPackageOverride)

  // package for api classes
  override def apiPackage = Some(apiPackageOverride)

  def supportingFilePath = destinationDir + java.io.File.separator + invokerPackage.get.replace(".", java.io.File.separator) + java.io.File.separator

  // supporting classes
  override def supportingFiles =
    List("SWGApiClient", "SWGDate", "SWGFile", "SWGObject")
      .flatMap {
        f => List((f + ".h", supportingFilePath, f + ".h"), (f + ".m", supportingFilePath, f + ".m"))
      }

}