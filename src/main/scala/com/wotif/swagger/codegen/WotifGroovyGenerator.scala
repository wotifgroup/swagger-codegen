package com.wotif.swagger.codegen

import com.wotif.swagger.codegen.WotifJavaGenerator

object WotifGroovyGenerator extends WotifJavaGenerator {

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

  // location of templates
  override def templateDir = "Wotif/Groovy"

  // where to write generated code
  override def destinationDir = "generated-code/groovy/src/main/groovy"

  // template used for models
  modelTemplateFiles += "model.mustache" -> ".groovy"

  // template used for models
  apiTemplateFiles += "api.mustache" -> ".groovy"


  // file suffix
  override def fileSuffix = ".groovy"

  override def supportingFiles =
    List(
      ("postBodyEncoder.mustache", destinationDir + java.io.File.separator + invokerPackage.get.replace(".", java.io.File.separator) + java.io.File.separator, "PostBodyEncoder.groovy"),
      ("build.gradle.mustache", "generated-code/groovy", "build.gradle"))

}
