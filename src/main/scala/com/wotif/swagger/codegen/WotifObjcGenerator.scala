package com.wotif.swagger.codegen

import com.wordnik.swagger.codegen.BasicObjcGenerator

object WotifObjcGenerator extends BasicObjcGenerator {

  var apiName = "Wotif"

  def main(args: Array[String]) = {
    if (args.length < 3) {
      throw new RuntimeException("Need to specify target API name")
    }
    apiName = args(2)    
    generateClient(args)
  }

  // location of templates
  override def templateDir = "Wotif/objc"

  // where to write generated code
  override def destinationDir = "generated-code/objc/" + apiName

  // api invoker package
  override def invokerPackage = None

  // package for models
  override def modelPackage = None

  // package for api classes
  override def apiPackage = None

  // supporting classes
  override def supportingFiles =
    ("Podfile.mustache", destinationDir, "Podfile") ::
    List("SWGApiClient", "SWGDate", "SWGFile", "SWGObject")
      .flatMap {
        f => List((f + ".h", destinationDir, f + ".h"), (f + ".m", destinationDir, f + ".m"))
      }   
    
}