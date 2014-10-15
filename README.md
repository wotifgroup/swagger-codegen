# Swagger Codegen 

* See ORIGINAL_README.md for overall details.

* This version includes Wotif-specific code to generate swagger clients for Edge Services.

* WotifGroovyGenerator extends WotifJavaGenerator. If you need to add List or Map types, add them in WotifJavaGenerator copying the existing pattern.

* Some small extensions have been added to the wordnik Codegen.scala class to assist code generation. There are some unit tests to check that these extensions
don't get clobbered when updating the base Swagger classes.

## Example Usage:

./bin/runscala.sh com.wotif.swagger.codegen.WotifGroovyGenerator http://localhost:23180/service/api-docs/platform/web nokey greenedge

### October 2014 Update

Updated to Version 2.0.17 (https://github.com/wordnik/swagger-codegen/tree/2.0.17)

**Note:** the updated code generator requires Scala 2.11 (previously was 2.10)